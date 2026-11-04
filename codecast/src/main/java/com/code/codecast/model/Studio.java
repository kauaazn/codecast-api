package com.code.codecast.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Gera automaticamente getters, setters, equals, hashCode e toString
@Builder // Permite criar objetos com Studio.builder().name("x").build()
@NoArgsConstructor // Gera um construtor vazio — new Studio()
@AllArgsConstructor // Gera um construtor com todos os campos
@Entity // Diz pro Spring que essa classe representa uma tabela no banco
@Table(name = "studios") // Define o nome da tabela no banco como "studios"
public class Studio {

    @Id // Esse campo é a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco gera o id automaticamente (1, 2, 3...)
    private Long id;

    @Column(nullable = false) // Campo obrigatório, não aceita valor nulo
    private String name; // Nome do estúdio

    @Column(nullable = false) // Campo obrigatório, não aceita valor nulo
    private Integer maxCapacity; // Capacidade máxima de pessoas no estúdio

    @ElementCollection // Salva uma lista de strings no banco
    @CollectionTable(name = "studio_equipments", joinColumns = @JoinColumn(name = "studio_id"))
    // Cria uma tabela separada chamada "studio_equipments" ligada pelo "studio_id"
    @Column(name = "equipment") // Cada item da lista vai na coluna "equipment"
    private List<String> equipments; // Lista de equipamentos do estúdio 
}