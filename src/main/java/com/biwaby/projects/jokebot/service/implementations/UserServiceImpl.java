package com.biwaby.projects.jokebot.service.implementations;

import com.biwaby.projects.jokebot.exceptions.*;
import com.biwaby.projects.jokebot.model.DTO.UserDTO;
import com.biwaby.projects.jokebot.model.DTO.UserRegistrationDTO;
import com.biwaby.projects.jokebot.model.Role;
import com.biwaby.projects.jokebot.model.User;
import com.biwaby.projects.jokebot.repository.RoleRepository;
import com.biwaby.projects.jokebot.repository.UserRepository;
import com.biwaby.projects.jokebot.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAllUsers(int page) {
        return userRepository.findAll(PageRequest.of(page, 5));
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public UserDTO registerUser(UserRegistrationDTO dto) {
        if (dto == null) {
            throw new IncorrectUserException();
        }
        if (dto.getUsername() == null || dto.getPassword() == null) {
            throw new IncorrectUserException();
        }
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        User user = new User();
        Role role = roleRepository.findByAuthority("USER").get();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setRoles(new HashSet<>(List.of(role)));
        userRepository.save(user);
        return new UserDTO(user.getId(), user.getUsername(), user.isEnabled(), user.getRoles());
    }

    @Override
    public UserDTO grantRole(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalUser.isPresent()) {
            if (optionalRole.isPresent()) {
                User user = optionalUser.get();
                Role role = optionalRole.get();
                user.getRoles().add(role);

                userRepository.save(user);
                return new UserDTO(user.getId(), user.getUsername(), user.isEnabled(), user.getRoles());
            }
            else {
                throw new RoleNotFoundException();
            }
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public UserDTO revokeRole(Long userId, Long roleId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalUser.isPresent()) {
            if (optionalRole.isPresent()) {
                User user = optionalUser.get();
                Role role = optionalRole.get();

                if (!user.getRoles().contains(role)) {
                    throw new UserDoesNotHaveRoleException();
                }

                user.getRoles().remove(role);
                userRepository.save(user);
                return new UserDTO(user.getId(), user.getUsername(), user.isEnabled(), user.getRoles());
            }
            else {
                throw new RoleNotFoundException();
            }
        }
        else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
