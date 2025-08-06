package com.ifg.residIFG.domain.pacotes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "pacotes")
public class Pacotes {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "codigoRastreio")
    private String codigoRastreio;

    @Column(name = "data")
    private String data;

    @Column(name = "remetente")
    private String remetente;

    @Column(name = "destinatario")
    private String destinatario;

    public Pacotes() {

    }
}
