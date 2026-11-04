package com.code.codecast.service;

import com.code.codecast.dto.HostRequest;
import com.code.codecast.dto.HostResponse;
import com.code.codecast.model.Host;
import com.code.codecast.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hostRepository; // Repository injetado automaticamente

    public HostResponse create(HostRequest request) {
        Host host = Host.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build(); // Monta o objeto Host com os dados do request
        return toResponse(hostRepository.save(host)); // Salva no banco e converte pra response
    }

    public List<HostResponse> findAll() {
        return hostRepository.findAll()
                .stream()
                .map(this::toResponse) // Converte cada Host em HostResponse
                .toList();
    }

    public HostResponse findById(Long id) {
        Host host = hostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Host não encontrado")); // Lança erro se não existir
        return toResponse(host);
    }

    public HostResponse update(Long id, HostRequest request) {
        Host host = hostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Host não encontrado"));
        host.setName(request.getName());
        host.setEmail(request.getEmail());
        host.setPhone(request.getPhone());
        return toResponse(hostRepository.save(host)); // Atualiza e salva
    }

    public void delete(Long id) {
        if (!hostRepository.existsById(id)) {
            throw new RuntimeException("Host não encontrado"); // Erro se não existir
        }
        hostRepository.deleteById(id);
    }

    private HostResponse toResponse(Host host) {
        return HostResponse.builder()
                .id(host.getId())
                .name(host.getName())
                .email(host.getEmail())
                .phone(host.getPhone())
                .build(); // Converte entidade do banco em objeto de saída
    }
}