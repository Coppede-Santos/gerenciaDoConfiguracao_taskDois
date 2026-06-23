package com.gerencia.taskdois.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReceitaTest {

    // Test 7: Verificar que prePersist asigna fecha automáticamente
    @Test
    void testPrePersist() {
        // Crear receta sin fecha
        Receita receita = new Receita();
        receita.setNome("Bolo");

        // Llamar al método prePersist
        receita.prePersist();

        // Verificar que se asignó la fecha actual
        assertNotNull(receita.getDataRegistro());
        assertEquals(LocalDate.now(), receita.getDataRegistro());
    }

    // Test 8: Verificar que los getters y setters funcionan correctamente
    @Test
    void testGettersSetters() {
        // Crear y configurar receta
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Pizza");
        receita.setTipoReceita("salgada");
        receita.setCusto(new BigDecimal("30.00"));
        receita.setAtivo(true);

        // Verificar que los valores se guardaron correctamente
        assertEquals(1L, receita.getId());
        assertEquals("Pizza", receita.getNome());
        assertEquals("salgada", receita.getTipoReceita());
        assertEquals(new BigDecimal("30.00"), receita.getCusto());
        assertEquals(true, receita.getAtivo());
    }
}
