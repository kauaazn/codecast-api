package com.code.codecast.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class StudioRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Capacidade máxima é obrigatória")
    private Integer maxCapacity;

    private List<String> equipments;
}