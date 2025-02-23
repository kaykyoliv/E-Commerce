package com.ecommerce.project.controller;

import com.ecommerce.project.dto.AddressDTO;
import com.ecommerce.project.model.User;
import com.ecommerce.project.service.AddressService;
import com.ecommerce.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO){
        User user = authUtil.loggedInUser();
        addressDTO = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(addressDTO, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> createAddress(){
        List<AddressDTO> addressDTO = addressService.getAllAddress();
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId){
        AddressDTO addressDTO = addressService.getAddressById(addressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }
}
