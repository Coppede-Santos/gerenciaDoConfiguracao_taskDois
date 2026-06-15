package com.gerencia.taskdois.repository;

import com.gerencia.taskdois.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByStatus(String status);

    List<Receita> findByDataRegistroBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT r FROM Receita r WHERE " +
           "(:status IS NULL OR r.status = :status) AND " +
           "(:dataInicio IS NULL OR r.dataRegistro >= :dataInicio) AND " +
           "(:dataFim IS NULL OR r.dataRegistro <= :dataFim)")
    List<Receita> findByFiltros(@Param("status") String status,
                                 @Param("dataInicio") LocalDate dataInicio,
                                 @Param("dataFim") LocalDate dataFim);
}
