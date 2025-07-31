package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // Endpoint para obter todos os usuários
    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();  // Retorna todos os usuários do banco de dados
    }
}
