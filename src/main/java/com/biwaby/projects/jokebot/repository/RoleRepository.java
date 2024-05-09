package com.biwaby.projects.jokebot.repository;

import com.biwaby.projects.jokebot.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByAuthority(String role);
}
