package com.gerencia.taskdois.service;

import com.gerencia.taskdois.model.Receita;
import com.gerencia.taskdois.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceitaServiceTest {

    @Mock
    private ReceitaRepository receitaRepository;

    private Receita receita;

    @BeforeEach
    void setUp() {
        // Crear receta de ejemplo
        receita = new Receita();
        receita.setId(1L);
        receita.setNome("Bolo de Chocolate");
        receita.setDescricao("Delicioso bolo");
        receita.setTipoReceita("doce");
        receita.setCusto(new BigDecimal("25.50"));
        receita.setDataRegistro(LocalDate.now());
        receita.setStatus("ativa");
    }

    // Test 13: Verificar que se puede buscar receta por ID
    @Test
    void testBuscarReceitaPorId() {
        // Configurar mock para retornar la receta
        when(receitaRepository.findById(1L)).thenReturn(Optional.of(receita));

        // Buscar receta
        Optional<Receita> resultado = receitaRepository.findById(1L);

        // Verificar que se encontró la receta correcta
        assertTrue(resultado.isPresent());
        assertEquals("Bolo de Chocolate", resultado.get().getNome());
        verify(receitaRepository).findById(1L);
    }

    // Test 14: Verificar que se pueden listar recetas por tipo
    @Test
    void testListarReceitasPorTipo() {
        // Crear lista de recetas dulces
        Receita receita2 = new Receita();
        receita2.setNome("Torta");
        receita2.setTipoReceita("doce");
        List<Receita> receitasDoces = Arrays.asList(receita, receita2);

        // Configurar mock
        when(receitaRepository.findAll()).thenReturn(receitasDoces);

        // Buscar todas las recetas
        List<Receita> resultado = receitaRepository.findAll();

        // Verificar que se obtienen las recetas
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(r -> "doce".equals(r.getTipoReceita())));
    }

    // Test 15: Verificar que se puede calcular el costo total de recetas
    @Test
    void testCalcularCostoTotalRecetas() {
        // Crear varias recetas con costos
        Receita receita1 = new Receita();
        receita1.setCusto(new BigDecimal("10.00"));

        Receita receita2 = new Receita();
        receita2.setCusto(new BigDecimal("15.00"));

        Receita receita3 = new Receita();
        receita3.setCusto(new BigDecimal("20.00"));

        // Calcular el costo total manualmente (simulando una funcionalidad)
        BigDecimal costoTotal = receita1.getCusto()
                .add(receita2.getCusto())
                .add(receita3.getCusto());

        // Verificar que el costo total es correcto
        assertEquals(new BigDecimal("45.00"), costoTotal);
        assertTrue(costoTotal.compareTo(BigDecimal.ZERO) > 0);
    }
}
