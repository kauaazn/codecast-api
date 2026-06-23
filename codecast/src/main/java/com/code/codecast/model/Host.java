package com.code.codecast.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // Representa uma tabela no banco
@Table(name = "hosts")
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID gerado automaticamente
    private Long id;

    @Column(nullable = false) // Campo obrigatório
    private String name;

    @Column(nullable = false, unique = true) // Obrigatório e único — não pode ter dois hosts com o mesmo email
    private String email;

    @Column(nullable = false)
    private String phone;
}