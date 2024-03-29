package com.example.scbook.repositories;

import com.example.scbook.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
