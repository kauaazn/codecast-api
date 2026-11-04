package com.code.codecast.repository;

import com.code.codecast.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Busca agendamentos que conflitam com o horário solicitado
    // Um conflito acontece quando um agendamento existente no mesmo estúdio
    // no mesmo dia tem horário que se sobrepõe ao novo agendamento
    @Query("SELECT b FROM Booking b WHERE b.studio.id = :studioId " +
            "AND b.recordingDate = :date " +
            "AND b.startTime < :endTime " +   // O agendamento existente começa antes do novo terminar
            "AND b.endTime > :startTime")      // O agendamento existente termina depois do novo começar
    List<Booking> findConflictingBookings(
            @Param("studioId") Long studioId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );
}