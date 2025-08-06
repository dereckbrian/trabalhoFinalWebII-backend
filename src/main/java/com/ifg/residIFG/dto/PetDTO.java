package com.ifg.residIFG.dto;

import com.ifg.residIFG.domain.user.User;

public class PetDTO {
    private String id;
    private String nome;
    private String raca;
    private String tamanho;
    private String cor;
    private User dono;  // ID do dono (referência ao usuário)

    // Construtores, getters e setters
    public PetDTO(Long id, String nome, String raca, String tamanho, String cor, User dono) {
        this.id = String.valueOf(id);  // Certifique-se de que o id seja convertido para String
        this.nome = nome;
        this.raca = raca;
        this.tamanho = tamanho;
        this.cor = cor;
        this.dono = dono;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public User getDono() {
        return dono;
    }

    public void setDono(User dono) {
        this.dono = dono;
    }
}
