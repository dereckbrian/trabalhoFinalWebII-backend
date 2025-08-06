package com.ifg.residIFG.repository;

import com.ifg.residIFG.domain.pacotes.Pacotes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageRepository extends JpaRepository<Pacotes, String> {
    Optional<Pacotes> findByCodigoRastreio(String codigoRastreio);

}
