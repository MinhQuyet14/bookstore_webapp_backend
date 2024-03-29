package com.example.scbook.services.Impl;

import com.example.scbook.dtos.UserDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Role;
import com.example.scbook.models.User;
import com.example.scbook.repositories.RoleRepository;
import com.example.scbook.repositories.UserRepository;
import com.example.scbook.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow( ()-> new DataNotFoundException("Role not found"));
        newUser.setRole(role);
        //check social account ID
//        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0){
//            String password = userDTO.getPassword();
//            String encodedPassword = passwordEncoder.encode(password);
//            newUser.setPassword(encodedPassword);
//        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        //security
        return null;
    }
}
