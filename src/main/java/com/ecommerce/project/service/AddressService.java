package com.ecommerce.project.service;

import com.ecommerce.project.dto.AddressDTO;
import com.ecommerce.project.model.User;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);
}
