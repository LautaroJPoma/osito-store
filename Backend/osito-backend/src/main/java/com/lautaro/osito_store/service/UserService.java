package com.lautaro.osito_store.service;

import java.util.List;

import com.lautaro.osito_store.dto.UserDTO;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, Long id);

    void deleteUser(Long id);

    boolean existsById(Long id);

}
