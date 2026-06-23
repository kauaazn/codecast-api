package com.code.codecast.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class BookingResponse {

    private Long id;
    private Long studioId;
    private String studioName;  // Nome do estúdio para facilitar a leitura
    private Long hostId;
    private String hostName;    // Nome do host para facilitar a leitura
    private LocalDate recordingDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String timezone;
}