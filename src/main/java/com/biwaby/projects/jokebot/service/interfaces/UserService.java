package com.biwaby.projects.jokebot.service.interfaces;

import com.biwaby.projects.jokebot.model.DTO.UserDTO;
import com.biwaby.projects.jokebot.model.DTO.UserRegistrationDTO;
import com.biwaby.projects.jokebot.model.Role;
import com.biwaby.projects.jokebot.model.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    Page<User> getAllUsers(int page);
    List<Role> getAllRoles();
    UserDTO registerUser(UserRegistrationDTO dto);
    UserDTO grantRole(Long userId, Long roleId);
    UserDTO revokeRole(Long userId, Long roleId);
    void deleteUser(Long userId);
}
