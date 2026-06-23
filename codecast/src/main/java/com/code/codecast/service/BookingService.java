package com.code.codecast.service;

import com.code.codecast.dto.BookingRequest;
import com.code.codecast.dto.BookingResponse;
import com.code.codecast.model.Booking;
import com.code.codecast.model.Host;
import com.code.codecast.model.Studio;
import com.code.codecast.repository.BookingRepository;
import com.code.codecast.repository.HostRepository;
import com.code.codecast.repository.StudioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final StudioRepository studioRepository;
    private final HostRepository hostRepository;

    public BookingResponse create(BookingRequest request) {
        // Busca o estúdio pelo id, lança erro se não existir
        Studio studio = studioRepository.findById(request.getStudioId())
                .orElseThrow(() -> new RuntimeException("Estúdio não encontrado"));

        // Busca o host pelo id, lança erro se não existir
        Host host = hostRepository.findById(request.getHostId())
                .orElseThrow(() -> new RuntimeException("Host não encontrado"));

        // Valida se o fuso horário enviado é válido (ex: "America/Sao_Paulo")
        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(request.getTimezone());
        } catch (Exception e) {
            throw new RuntimeException("Fuso horário inválido: " + request.getTimezone());
        }

        // Converte os horários do fuso do host para UTC antes de salvar
        // Isso garante que não haja conflitos entre hosts de fusos diferentes
        ZonedDateTime startZoned = ZonedDateTime.of(request.getRecordingDate(), request.getStartTime(), zoneId);
        ZonedDateTime endZoned = ZonedDateTime.of(request.getRecordingDate(), request.getEndTime(), zoneId);

        LocalDate utcDate = startZoned.withZoneSameInstant(ZoneId.of("UTC")).toLocalDate();
        LocalTime utcStart = startZoned.withZoneSameInstant(ZoneId.of("UTC")).toLocalTime();
        LocalTime utcEnd = endZoned.withZoneSameInstant(ZoneId.of("UTC")).toLocalTime();

        // Verifica se já existe algum agendamento conflitante no banco
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                studio.getId(), utcDate, utcStart, utcEnd);

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Conflito de horário: o estúdio já está reservado nesse período");
        }

        // Monta e salva o agendamento com os horários convertidos para UTC
        Booking booking = Booking.builder()
                .studio(studio)
                .host(host)
                .recordingDate(utcDate)
                .startTime(utcStart)
                .endTime(utcEnd)
                .timezone(request.getTimezone())
                .build();

        return toResponse(bookingRepository.save(booking));
    }

    public List<BookingResponse> findAll() {
        return bookingRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BookingResponse findById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        return toResponse(booking);
    }

    public void delete(Long id) {
        if (!bookingRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        bookingRepository.deleteById(id);
    }

    // Converte a entidade Booking em BookingResponse
    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .studioId(booking.getStudio().getId())
                .studioName(booking.getStudio().getName())
                .hostId(booking.getHost().getId())
                .hostName(booking.getHost().getName())
                .recordingDate(booking.getRecordingDate())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .timezone(booking.getTimezone())
                .build();
    }
}