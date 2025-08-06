package com.ifg.residIFG.infra.security;

import com.ifg.residIFG.dto.PetDTO;
import com.ifg.residIFG.domain.pet.Pet;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.repository.PetRepository;
import com.ifg.residIFG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    // Buscar todos os pets
    public List<PetDTO> findAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream()
                   .map(pet -> new PetDTO(pet.getId(), pet.getNome(), pet.getRaca(), pet.getTamanho(), pet.getCor(), pet.getDono()))
                   .collect(Collectors.toList());
    }

    // Buscar um pet por ID
    public PetDTO findPetById(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        return new PetDTO(pet.getId(), pet.getNome(), pet.getRaca(), pet.getTamanho(), pet.getCor(), pet.getDono());
    }

    // Adicionar um novo pet
    /* public PetDTO savePet(Pet pet) {
        User dono = userRepository.findById(pet.getDono().getId()).orElseThrow(() -> new RuntimeException("Dono não encontrado"));
        pet.setDono(dono);
        Pet savedPet = petRepository.save(pet);
        String donoId = (savedPet.getDono() != null) ? savedPet.getDono().getId() : null;  // Verifica se dono não é null
        return new PetDTO(savedPet.getId(), savedPet.getNome(), savedPet.getRaca(), savedPet.getTamanho(), savedPet.getCor(), donoId);
    }*/
}
