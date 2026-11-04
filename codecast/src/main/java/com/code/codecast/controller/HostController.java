package com.code.codecast.controller;

import com.code.codecast.dto.HostRequest;
import com.code.codecast.dto.HostResponse;
import com.code.codecast.service.HostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Responde requisições HTTP e devolve JSON
@RequestMapping("/hosts") // Todas as rotas começam com /hosts
@RequiredArgsConstructor
public class HostController {

    private final HostService hostService;

    @PostMapping // POST /hosts → cria um host, retorna 201
    public ResponseEntity<HostResponse> create(@RequestBody @Valid HostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hostService.create(request));
    }

    @GetMapping // GET /hosts → lista todos os hosts, retorna 200
    public ResponseEntity<List<HostResponse>> findAll() {
        return ResponseEntity.ok(hostService.findAll());
    }

    @GetMapping("/{id}") // GET /hosts/1 → busca um host pelo id
    public ResponseEntity<HostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(hostService.findById(id));
    }

    @PutMapping("/{id}") // PUT /hosts/1 → atualiza um host
    public ResponseEntity<HostResponse> update(@PathVariable Long id, @RequestBody @Valid HostRequest request) {
        return ResponseEntity.ok(hostService.update(id, request));
    }

    @DeleteMapping("/{id}") // DELETE /hosts/1 → deleta um host, retorna 204
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hostService.delete(id);
        return ResponseEntity.noContent().build();
    }
}