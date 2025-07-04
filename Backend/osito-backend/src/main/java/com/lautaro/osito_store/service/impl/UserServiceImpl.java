package com.lautaro.osito_store.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lautaro.osito_store.dto.UserDTO;
import com.lautaro.osito_store.entity.User;
import com.lautaro.osito_store.mapper.UserMapper;
import com.lautaro.osito_store.repository.UserRepository;
import com.lautaro.osito_store.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
      User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {
       User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
       
        
        return userMapper.toDTO(userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        userRepository.delete(user);
    }

}
