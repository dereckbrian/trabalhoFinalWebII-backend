package com.ifg.residIFG.infra.security;

import com.ifg.residIFG.dto.UserDTO;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Método para buscar todos os usuários
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll(); // Obtém todos os usuários
        return users.stream()
                    .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole()))
                    .collect(Collectors.toList()); // Mapeia para DTO
    }

    // Método para buscar um usuário por ID
    public UserDTO findUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
    public UserDTO findUserByEmail(String email) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
}
    // Método para salvar um novo usuário
    public UserDTO saveUser(User user) {
        User savedUser = userRepository.save(user); // Salva o usuário no banco de dados
        return new UserDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(), savedUser.getRole());
    }
}
