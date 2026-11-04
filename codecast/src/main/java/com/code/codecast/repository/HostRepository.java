package com.code.codecast.repository;

import com.code.codecast.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca como repositório de acesso ao banco
public interface HostRepository extends JpaRepository<Host, Long> {
    // O Spring já fornece: save, findAll, findById, deleteById, existsById
}