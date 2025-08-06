package com.ifg.residIFG.domain.user;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ifg.residIFG.domain.pet.Pet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column (name = "role")
    private String role;
    @Column(name = "password")
    private String password;

    @Column(name = "profilePicture")
    private String profilePicture;

    @OneToMany(mappedBy = "dono", fetch = FetchType.EAGER)
     @JsonManagedReference 
    private List<Pet> pets;
    public User(){

    }
}
