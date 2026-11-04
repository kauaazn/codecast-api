package com.code.codecast.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    // Objeto padrão de erro que a API devolve quando algo dá errado
    private int status;        // Código HTTP (400, 404, 409...)
    private String message;    // Mensagem explicando o erro
    private LocalDateTime timestamp; // Quando o erro aconteceu
}