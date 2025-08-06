package com.ifg.residIFG.repository;

import com.ifg.residIFG.domain.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    // Altere o tipo de Long para String para combinar com o tipo do id do User
    List<Pet> findAllByDono_Id(String donoId);
}
