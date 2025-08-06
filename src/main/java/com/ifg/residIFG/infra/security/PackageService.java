package com.ifg.residIFG.infra.security;

import com.ifg.residIFG.domain.pacotes.Pacotes;
import com.ifg.residIFG.domain.user.User;
import com.ifg.residIFG.dto.PackageDTO;
import com.ifg.residIFG.dto.UserDTO;
import com.ifg.residIFG.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PackageService {

    @Autowired
    private PackageRepository pacoteRpository;

    public PackageDTO findUserByEmail(String email) {
        Pacotes pacotes = pacoteRpository.findByCodigoRastreio(email)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
        return new PackageDTO(pacotes.getId(), pacotes.getNome(), pacotes.getCodigoRastreio(), pacotes.getData(), pacotes.getDestinatario(), pacotes.getRemetente());
    }
}
