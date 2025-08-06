package com.ifg.residIFG.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifg.residIFG.domain.pet.Pet;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.PetDTO;
import com.ifg.residIFG.infra.security.PetService;
import com.ifg.residIFG.repository.PetRepository;
import com.ifg.residIFG.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetService petService;

    // Adicionar um novo pet
    @PostMapping("/add")
    public Pet addPet(@RequestParam("pet") String petJson, @RequestParam("imagem") MultipartFile imagemFile)
            throws IOException {
        // Converte o JSON do pet para o DTO (ou Pet se necessário)
        ObjectMapper objectMapper = new ObjectMapper();
        PetDTO petDTO = objectMapper.readValue(petJson, PetDTO.class);

        // Salva a imagem e obtém o caminho
        String imagePath = saveImage(imagemFile);
                
        // Converte o DTO para a entidade Pet
        Pet pet = new Pet();
        pet.setNome(petDTO.getNome());
        pet.setRaca(petDTO.getRaca());
        pet.setTamanho(petDTO.getTamanho());
        pet.setCor(petDTO.getCor());
        pet.setImagem(imagePath); // Atribui o caminho da imagem ao pet

        // Atribui o dono ao pet
        User dono = userRepository.findById(petDTO.getDono().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        pet.setDono(dono);

        // Salva o pet no banco de dados
        return petRepository.save(pet);
    }

    @GetMapping("/all")
    public List<PetDTO> getAllPets() {
        // Obtém todos os pets e os mapeia para PetDTO
        return petService.findAllPets();
    }

    @GetMapping("/{id}")
public ResponseEntity<PetDTO> getPetById(@PathVariable Long id) {
    Optional<Pet> pet = petRepository.findById(id);

    if (pet.isPresent()) {
        PetDTO petDTO = new PetDTO(
            pet.get().getId(),
            pet.get().getNome(),
            pet.get().getRaca(),
            pet.get().getTamanho(),
            pet.get().getCor(),
            pet.get().getDono(), 
            pet.get().getImagem()
        );
        return ResponseEntity.ok(petDTO); // Retorna o pet com status 200 (OK)
    } else {
        throw new RuntimeException("Pet não encontrado com o id: " + id); // Exceção personalizada
    }
}

    // Listar pets de um usuário específico (com base no dono)
    @GetMapping("/user/{userId}")
    public List<Pet> getPetsByUser(@PathVariable String userId) {
        return petRepository.findAllByDono_Id(userId); // Isso agora deve funcionar
    }

    // Atualizar um pet
    @PutMapping("/update/{id}")
    public Pet updatePet(@PathVariable Long id, @RequestBody Pet petDetails) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"));
        pet.setNome(petDetails.getNome());
        pet.setRaca(petDetails.getRaca());
        pet.setTamanho(petDetails.getTamanho());
        pet.setCor(petDetails.getCor());

        // Atribui o dono ao pet
    if (petDetails.getDono() != null && petDetails.getDono().getId() != null) {
        User dono = userRepository.findById(petDetails.getDono().getId())
                .orElseThrow(() -> new RuntimeException("Dono não encontrado"));
        pet.setDono(dono);
    }
        return petRepository.save(pet);
    }

    // Deletar um pet
    @DeleteMapping("/delete/{id}")
    public void deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
    if (imageFile.isEmpty()) {
        throw new RuntimeException("A imagem não pode estar vazia");
    }
    String uploadDir = "src/main/resources/static/images";
    String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    Path filePath = uploadPath.resolve(fileName);
    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    return "/images/" + fileName;
}

}
