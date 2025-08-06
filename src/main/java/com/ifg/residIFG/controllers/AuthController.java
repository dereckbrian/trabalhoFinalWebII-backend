package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.pacotes.Pacotes;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.LoginResquestDTO;
import com.ifg.residIFG.dto.ResponseDTO;
import com.ifg.residIFG.repository.UserRepository;
import com.ifg.residIFG.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import com.ifg.residIFG.dto.AddPackageDTO;
import  com.ifg.residIFG.repository.PackageRepository;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor //Serva para não precisar colocar o @Autowired em todos os private final
public class AuthController {

    private final UserRepository userRepository;
    private final PackageRepository packageRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginResquestDTO body){
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not Found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return  ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity register(@RequestParam("name") String name,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password,
                                   @RequestParam("role") String role,
                                   @RequestPart(value = "profileImage", required = false)MultipartFile profilePicture){

        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("A senha não pode ser nula ou vazia.");
        }
        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {

            String profilePictureUrl = null;
            if (profilePicture != null && !profilePicture.isEmpty()){
                try{
                    profilePictureUrl = saveImage(profilePicture);
                }catch (Exception e){
                    return ResponseEntity.status(500).body("Erro ao salvar a imagem");
                }
            }

            User user2 = new User();
            user2.setPassword(passwordEncoder.encode(password));
            user2.setEmail(email);
            user2.setName(name);
            user2.setRole(role);
            user2.setProfilePicture(profilePictureUrl);
             // TESTE,DPS EXCLUA
            this.userRepository.save(user2);

            String token = this.tokenService.generateToken(user2);

            return ResponseEntity.ok(new ResponseDTO(user2.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/addPackage")
    public ResponseEntity addPackage(@RequestBody AddPackageDTO body){

        Optional<Pacotes> pacotes = this.packageRepository.findByCodigoRastreio(body.codigoRastreio());
        if (pacotes.isEmpty()) {

            Pacotes pacotes1 = new Pacotes();
            pacotes1.setNome(body.nome());
            pacotes1.setData(body.data());
            pacotes1.setDestinatario(body.destinatario());
            pacotes1.setRemetente(body.remetente());
            pacotes1.setCodigoRastreio(body.codigoRastreio());
            // TESTE,DPS EXCLUA
            this.packageRepository.save(pacotes1);

            return ResponseEntity.ok(pacotes1.getNome());
        }

        return ResponseEntity.badRequest().build();
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String uploadDir = "src/main/resources/static/images";

        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();

        Path upladoPath = Paths.get(uploadDir);
        if (!Files.exists(upladoPath)){
            Files.createDirectories(upladoPath);
        }
        Path filePath = upladoPath.resolve(fileName);

        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/images" + fileName;
    }

}

