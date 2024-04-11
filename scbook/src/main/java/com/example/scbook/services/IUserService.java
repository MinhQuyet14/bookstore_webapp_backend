package com.example.scbook.services;

import com.example.scbook.dtos.UserDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
}
