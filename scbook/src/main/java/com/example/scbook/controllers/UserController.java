package com.example.scbook.controllers;

import com.example.scbook.dtos.UserDTO;
import com.example.scbook.dtos.UserLoginDTO;
import com.example.scbook.models.User;
import com.example.scbook.responses.LoginResponse;
import com.example.scbook.responses.RegisterResponse;
import com.example.scbook.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
//@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    UserController(IUserService userService){
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ){
        try {
            if(result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(RegisterResponse.builder()
                                .message("Dang ky khong thanh cong")
                        .build());
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body(RegisterResponse.builder()
                                .message("Mat khau khong trung khop")
                        .build());
            }
            User user = userService.createUser(userDTO);
            return ResponseEntity.ok(RegisterResponse.builder()
                            .message("Dang ky thanh cong!!!")
                            .user(user)
                    .build());
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RegisterResponse.builder()
                            .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        try {
            String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
            return ResponseEntity.ok(LoginResponse.builder()
                            .message("Dang nhap thanh cong!!!")
                            .token(token)
                    .build());
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponse.builder()
                             .message(e.getMessage())
                     .build());
        }
    }
}
