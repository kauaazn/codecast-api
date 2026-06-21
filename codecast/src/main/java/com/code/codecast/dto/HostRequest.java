package com.code.codecast.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HostRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido") // Valida se o formato do email é válido
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String phone;
}