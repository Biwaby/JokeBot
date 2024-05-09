package com.biwaby.projects.jokebot.controller;

import com.biwaby.projects.jokebot.model.DTO.UserDTO;
import com.biwaby.projects.jokebot.model.DTO.UserRegistrationDTO;
import com.biwaby.projects.jokebot.model.Role;
import com.biwaby.projects.jokebot.model.User;
import com.biwaby.projects.jokebot.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // GET /users/getAll?page=num_page - получение всех пользователей с пагинацией
    @GetMapping("/getAll")
    ResponseEntity<Page<User>> getAllUsers(@RequestParam int page) {
        return ResponseEntity.ok(userService.getAllUsers(page));
    }

    // GET /users/getAllRoles - получение всех ролей
    @GetMapping("/getAllRoles")
    ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    // POST /users/register - регистрация нового пользователя
    @PostMapping("/register")
    ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO dto) {
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    // PUT /users/grant?userId=id&roleId=id - выдать роль пользователю
    @PutMapping("/grant")
    ResponseEntity<UserDTO> grantRole(@RequestParam Long userId, @RequestParam Long roleId) {
        return ResponseEntity.ok(userService.grantRole(userId, roleId));
    }

    // PUT /users/revoke?userId=id&roleId=id - отозвать роль у пользователя
    @PutMapping("/revoke")
    ResponseEntity<UserDTO> revokeRole(@RequestParam Long userId, @RequestParam Long roleId) {
        return ResponseEntity.ok(userService.revokeRole(userId, roleId));
    }

    // DELETE /users/userId - удаление пользователя
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
