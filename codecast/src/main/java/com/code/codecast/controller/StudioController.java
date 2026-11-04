package com.code.codecast.controller;

import com.code.codecast.dto.StudioRequest;
import com.code.codecast.dto.StudioResponse;
import com.code.codecast.service.StudioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Classe que responde requisições HTTP e devolve JSON
@RequestMapping("/studios") // Todas as rotas começam com /studios
@RequiredArgsConstructor // Injeta o Service automaticamente
public class StudioController {

    private final StudioService studioService; // Service que contém as regras de negócio

    @PostMapping // Responde POST /studios → cria um estúdio
    public ResponseEntity<StudioResponse> create(
            @RequestBody @Valid StudioRequest request) { // @RequestBody = pega o JSON da requisição | @Valid = valida os campos
        return ResponseEntity.status(HttpStatus.CREATED) // Retorna status 201 (criado com sucesso)
                .body(studioService.create(request));
    }

    @GetMapping // Responde GET /studios → lista todos os estúdios
    public ResponseEntity<List<StudioResponse>> findAll() {
        return ResponseEntity.ok(studioService.findAll()); // Retorna status 200 com a lista
    }

    @GetMapping("/{id}") // Responde GET /studios/1 → busca um estúdio pelo id
    public ResponseEntity<StudioResponse> findById(
            @PathVariable Long id) { // @PathVariable = pega o id que vem na URL
        return ResponseEntity.ok(studioService.findById(id));
    }

    @PutMapping("/{id}") // Responde PUT /studios/1 → atualiza um estúdio
    public ResponseEntity<StudioResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid StudioRequest request) {
        return ResponseEntity.ok(studioService.update(id, request));
    }

    @DeleteMapping("/{id}") // Responde DELETE /studios/1 → deleta um estúdio
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studioService.delete(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 (sem conteúdo)
    }
}