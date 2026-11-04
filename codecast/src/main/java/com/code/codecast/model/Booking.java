package com.code.codecast.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID gerado automaticamente
    private Long id;

    @ManyToOne // Muitos agendamentos podem ser do mesmo estúdio
    @JoinColumn(name = "studio_id", nullable = false) // Coluna de ligação com a tabela studios
    private Studio studio;

    @ManyToOne // Muitos agendamentos podem ser do mesmo host
    @JoinColumn(name = "host_id", nullable = false) // Coluna de ligação com a tabela hosts
    private Host host;

    @Column(nullable = false)
    private LocalDate recordingDate; // Data da gravação (ex: 2026-06-21)

    @Column(nullable = false)
    private LocalTime startTime; // Horário de início (ex: 14:00)

    @Column(nullable = false)
    private LocalTime endTime; // Horário de término (ex: 16:00)

    @Column(nullable = false)
    private String timezone; // Fuso horário do host (ex: "America/Sao_Paulo", "America/New_York")
}