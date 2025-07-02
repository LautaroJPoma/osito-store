package com.lautaro.osito_store.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lautaro.osito_store.dto.UserDTO;
import com.lautaro.osito_store.entity.PurchaseOrder;
import com.lautaro.osito_store.entity.User;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        if (user.getPurchaseOrders() != null) {
            List<Long> purchaseOrderIds = user.getPurchaseOrders().stream()
                    .map(PurchaseOrder::getId)
                    .toList();
            userDto.setPurchaseOrderIds(purchaseOrderIds);
        } else {
            userDto.setPurchaseOrderIds(new ArrayList<>());
        }

        return userDto;
    }

    public User toEntity(UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

}
