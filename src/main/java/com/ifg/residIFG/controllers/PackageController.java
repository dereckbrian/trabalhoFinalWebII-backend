package com.ifg.residIFG.controllers;

import com.ifg.residIFG.domain.pacotes.Pacotes;
import com.ifg.residIFG.dto.AddPackageDTO;
import com.ifg.residIFG.repository.PackageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor //Serva para não precisar colocar o @Autowired em todos os private final
public class PackageController {

    private final PackageRepository packageRepository;

    // Adicionar um novo pacote
    @PostMapping("/add")
    public ResponseEntity addPackage(@RequestBody AddPackageDTO body) {
        Optional<Pacotes> pacotes = this.packageRepository.findByCodigoRastreio(body.codigoRastreio());

        if (pacotes.isEmpty()) {
            Pacotes pacotes1 = new Pacotes();
            pacotes1.setNome(body.nome());
            pacotes1.setData(body.data());
            pacotes1.setDestinatario(body.destinatario());
            pacotes1.setRemetente(body.remetente());
            pacotes1.setCodigoRastreio(body.codigoRastreio());

            // Salva o pacote no banco de dados
            this.packageRepository.save(pacotes1);

            return ResponseEntity.ok(pacotes1.getNome()); // Retorna o nome do pacote
        }

        return ResponseEntity.badRequest().build(); // Caso o código de rastreio já exista
    }

    // Listar todos os pacotes
    @GetMapping("/all")
    public ResponseEntity<List<Pacotes>> getAllPackages() {
        return ResponseEntity.ok(packageRepository.findAll()); // Retorna todos os pacotes
    }

    // Buscar um pacote pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Pacotes> getPackageById(@PathVariable String id) {
        Optional<Pacotes> pacotes = packageRepository.findById(id);
        
        if (pacotes.isPresent()) {
            return ResponseEntity.ok(pacotes.get()); // Retorna o pacote encontrado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 caso o pacote não seja encontrado
        }
    }

    // Atualizar um pacote
    @PutMapping("/update/{id}")
    public ResponseEntity<Pacotes> updatePackage(@PathVariable String id, @RequestBody AddPackageDTO body) {
        Optional<Pacotes> pacotesOptional = packageRepository.findById(id);
        
        if (pacotesOptional.isPresent()) {
            Pacotes pacotes = pacotesOptional.get();
            pacotes.setNome(body.nome());
            pacotes.setData(body.data());
            pacotes.setDestinatario(body.destinatario());
            pacotes.setRemetente(body.remetente());
            pacotes.setCodigoRastreio(body.codigoRastreio());

            // Salva o pacote atualizado
            packageRepository.save(pacotes);

            return ResponseEntity.ok(pacotes); // Retorna o pacote atualizado
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 caso o pacote não seja encontrado
        }
    }

    // Deletar um pacote
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        Optional<Pacotes> pacotesOptional = packageRepository.findById(id);
        
        if (pacotesOptional.isPresent()) {
            packageRepository.deleteById(id); // Deleta o pacote
            return ResponseEntity.ok().build(); // Retorna 200 OK após deletar
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 caso o pacote não seja encontrado
        }
    }
}
