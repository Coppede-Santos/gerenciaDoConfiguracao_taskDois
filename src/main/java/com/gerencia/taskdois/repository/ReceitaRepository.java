package com.gerencia.taskdois.repository;

import com.gerencia.taskdois.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}