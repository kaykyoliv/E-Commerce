package com.ecommerce.project.service;

import com.ecommerce.project.dto.OrderDTO;
import com.ecommerce.project.dto.OrderItemDTO;
import com.ecommerce.project.dto.OrderRequestDTO;
import com.ecommerce.project.dto.ProductDTO;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.*;
import com.ecommerce.project.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public OrderDTO placeOrder(String emailId, String paymentMethod, OrderRequestDTO orderRequestDTO) {
        Cart cart = getCartByEmail(emailId);
        Address address = getAddressById(orderRequestDTO.getAddressId());

        if (cart.getCartItems().isEmpty()) {
            throw new APIException("Cart is empty");
        }

        Order order = createOrder(emailId, cart, address);
        Payment payment = createPayment(paymentMethod, orderRequestDTO, order);

        order.setPayment(payment);
        order = orderRepository.save(order);

        List<OrderItem> orderItems = createOrderItems(cart.getCartItems(), order);
        updateProductStock(cart.getCartItems());
        clearCart(cart);

        return mapToOrderDTO(order, orderItems, orderRequestDTO.getAddressId());
    }

    private Cart getCartByEmail(String email) {
        Cart cart = cartRepository.findCartByEmail(email);
        if(cart == null){
            throw new ResourceNotFoundException("Cart", "email", email);
        }
        return cart;
    }

    private Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
    }

    private Order createOrder(String email, Cart cart, Address address) {
        Order order = new Order();
        order.setEmail(email);
        order.setOrderDate(LocalDate.now());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderStatus("Order Accepted !");
        order.setAddress(address);
        return order;
    }

    private List<OrderItem> createOrderItems(List<CartItems> cartItems, Order order) {
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDiscount(cartItem.getDiscount());
            orderItem.setOrderedProductPrice(cartItem.getProductPrice());
            orderItem.setOrder(order);
            return orderItem;
        }).collect(Collectors.toList());

        return orderItemRepository.saveAll(orderItems);
    }

    private void updateProductStock(List<CartItems> cartItems) {
        List<Product> updatedProducts = cartItems.stream().map(item -> {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
            return product;
        }).collect(Collectors.toList());

        productRepository.saveAll(updatedProducts);
    }

    private void clearCart(Cart cart) {
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    private OrderDTO mapToOrderDTO(Order order, List<OrderItem> orderItems, Long addressId) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

        List<OrderItemDTO> orderItemDTOs = orderItems.stream().map(item -> {
            OrderItemDTO orderItemDTO = modelMapper.map(item, OrderItemDTO.class);
            orderItemDTO.getProduct().setQuantity(item.getQuantity());
            return orderItemDTO;
        }).collect(Collectors.toList());

        orderDTO.setOrderItems(orderItemDTOs);
        orderDTO.setAddressId(addressId);

        return orderDTO;
    }

    private Payment createPayment(String paymentMethod, OrderRequestDTO orderRequestDTO, Order order) {
        Payment payment = new Payment(
                paymentMethod,
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage(),
                orderRequestDTO.getPgName());

        payment.setOrder(order);
        return paymentRepository.save(payment);
    }
}
