package com.example.scbook.services.Impl;

import com.example.scbook.models.Role;
import com.example.scbook.repositories.RoleRepository;
import com.example.scbook.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
