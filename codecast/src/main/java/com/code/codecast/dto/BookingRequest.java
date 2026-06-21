package com.code.codecast.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingRequest {

    @NotNull(message = "ID do estúdio é obrigatório")
    private Long studioId;

    @NotNull(message = "ID do host é obrigatório")
    private Long hostId;

    @NotNull(message = "Data da gravação é obrigatória")
    private LocalDate recordingDate; // Formato: 2026-06-21

    @NotNull(message = "Horário de início é obrigatório")
    private LocalTime startTime; // Formato: 14:00

    @NotNull(message = "Horário de término é obrigatório")
    private LocalTime endTime; // Formato: 16:00

    @NotBlank(message = "Fuso horário é obrigatório")
    private String timezone; // Formato: "America/Sao_Paulo"
}