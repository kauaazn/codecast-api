package com.code.codecast.service;

import com.code.codecast.dto.StudioRequest;
import com.code.codecast.dto.StudioResponse;
import com.code.codecast.model.Studio;
import com.code.codecast.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioService {

    private final StudioRepository studioRepository;

    //Monta um Studio com os dados do Request e salva no banco
    public StudioResponse create(StudioRequest request) {
        Studio studio = Studio.builder()
                .name(request.getName())
                .maxCapacity(request.getMaxCapacity())
                .equipments(request.getEquipments())
                .build();

        Studio saved = studioRepository.save(studio);
        return toResponse(saved);
    }

    //Busca todos do banco e converte pra lista de Response
    public List<StudioResponse> findAll() {
        return studioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //Busca pelo id, lança erro se não encontrar
    public StudioResponse findById(Long id) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estúdio não encontrado"));
        return toResponse(studio);
    }


    //Busca, atualiza os campos e salva de novo
    public StudioResponse update(Long id, StudioRequest request) {
        Studio studio = studioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estúdio não encontrado"));

        studio.setName(request.getName());
        studio.setMaxCapacity(request.getMaxCapacity());
        studio.setEquipments(request.getEquipments());

        return toResponse(studioRepository.save(studio));
    }

    //Verifica se existe e deleta
    public void delete(Long id) {
        if (!studioRepository.existsById(id)) {
            throw new RuntimeException("Estúdio não encontrado");
        }
        studioRepository.deleteById(id);
    }

    //Converte Studio (entidade do banco) em StudioResponse (saída da API)
    private StudioResponse toResponse(Studio studio) {
        return StudioResponse.builder()
                .id(studio.getId())
                .name(studio.getName())
                .maxCapacity(studio.getMaxCapacity())
                .equipments(studio.getEquipments())
                .build();
    }
}