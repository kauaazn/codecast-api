package com.code.codecast.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice // Intercepta exceções de todos os controllers
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class) // Captura todos os RuntimeException
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        // Define o status baseado na mensagem do erro
        HttpStatus status = ex.getMessage().contains("não encontrado")
                ? HttpStatus.NOT_FOUND       // 404 se o recurso não existir
                : HttpStatus.CONFLICT;       // 409 se houver conflito de horário

        return ResponseEntity.status(status).body(
                ErrorResponse.builder()
                        .status(status.value())
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Captura erros de validação (@NotBlank, @NotNull...)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        // Junta todas as mensagens de erro de validação em uma só string
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value()) // 400
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}