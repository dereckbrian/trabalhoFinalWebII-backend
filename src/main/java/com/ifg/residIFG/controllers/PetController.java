package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.pet.Pet;
import com.ifg.residIFG.dto.PetDTO;
import com.ifg.residIFG.infra.security.PetService;
import com.ifg.residIFG.repository.PetRepository;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Pet addPet(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @GetMapping("/all")
    public List<PetDTO> getAllPets() {
        // Obtém todos os pets e os mapeia para PetDTO
         return petService.findAllPets();  
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
    pet.setDono(petDetails.getDono());  // Atualiza o dono
    return petRepository.save(pet);
}


    // Deletar um pet
    @DeleteMapping("/delete/{id}")
    public void deletePet(@PathVariable Long id) {
        petRepository.deleteById(id);
    }
}
