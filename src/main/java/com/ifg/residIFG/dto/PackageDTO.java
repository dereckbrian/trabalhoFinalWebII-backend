package com.ifg.residIFG.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PackageDTO {
    private String nome;
    private String codigoRastreio;
    private String data;
    private String remetente ;
    private String destinatario;

    public PackageDTO(String id, String nome, String codigoRastreio, String data, String destinatario, String remetente) {
    }
}
