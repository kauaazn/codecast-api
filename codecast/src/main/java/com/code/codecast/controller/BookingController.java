package com.code.codecast.controller;

import com.code.codecast.dto.BookingRequest;
import com.code.codecast.dto.BookingResponse;
import com.code.codecast.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Responde requisições HTTP e devolve JSON
@RequestMapping("/bookings") // Todas as rotas começam com /bookings
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping // POST /bookings → cria um agendamento, retorna 201
    public ResponseEntity<BookingResponse> create(@RequestBody @Valid BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(request));
    }

    @GetMapping // GET /bookings → lista todos os agendamentos, retorna 200
    public ResponseEntity<List<BookingResponse>> findAll() {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{id}") // GET /bookings/1 → busca um agendamento pelo id
    public ResponseEntity<BookingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @DeleteMapping("/{id}") // DELETE /bookings/1 → cancela um agendamento, retorna 204
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}