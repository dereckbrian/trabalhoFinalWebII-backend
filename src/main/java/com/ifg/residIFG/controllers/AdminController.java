package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.UserDTO;
import com.ifg.residIFG.repository.UserRepository;
import com.ifg.residIFG.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import com.ifg.residIFG.infra.security.UserService;
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
     @Autowired
    private UserService userService;

    // Endpoint para obter todos os usuários
     @GetMapping("/users")
    public List<UserDTO> getUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return users.stream()
                    .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                    .collect(Collectors.toList());
    }
    @GetMapping("/users/{id}")
public ResponseEntity<User> getUserById(@PathVariable String id) {
    return userRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}
@PutMapping("/users/{id}")
public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
    return userRepository.findById(id)
            .map(user -> {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setRole(updatedUser.getRole());
                userRepository.save(user);
                return ResponseEntity.ok(user);
            })
            .orElse(ResponseEntity.notFound().build());
}

}
