package com.ifg.residIFG.dto;

import org.springframework.web.multipart.MultipartFile;

public record RegisterRequestDTO(String name, String email, String password, String role, MultipartFile profilePicture) {
}
