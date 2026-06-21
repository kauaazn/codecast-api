package com.code.codecast.repository;

import com.code.codecast.model.Studio; // Importa a entidade Studio que criamos
import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface do Spring
import org.springframework.stereotype.Repository; // Marca essa interface como um repositório

@Repository // Diz pro Spring que essa interface é responsável por acessar o banco
public interface StudioRepository extends JpaRepository<Studio, Long> {
}