package com.gerencia.taskdois.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.math.BigDecimal;

@Data
@Entity
public class Receita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private LocalDate dataRegistro;
    private BigDecimal custo;
    private String tipoReceita; // doce ou salgada
    
    @PrePersist
    public void prePersist() {
        if (dataRegistro == null) {
            dataRegistro = LocalDate.now();
        }
    }
}