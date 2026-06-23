package com.gerencia.taskdois.service;

import com.gerencia.taskdois.model.Receita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PdfServiceTest {

    private PdfService pdfService;
    private List<Receita> receitas;

    @BeforeEach
    void setUp() {
        pdfService = new PdfService();
        receitas = new ArrayList<>();

        // Crear recetas de ejemplo
        Receita receita1 = new Receita();
        receita1.setId(1L);
        receita1.setNome("Bolo de Chocolate");
        receita1.setTipoReceita("doce");
        receita1.setCusto(new BigDecimal("25.50"));
        receita1.setAtivo(true);
        receitas.add(receita1);
    }

    // Test 3: Verificar que se genera un PDF correctamente
    @Test
    void testGerarPdfReceitas() {
        // Generar PDF con la lista de recetas
        byte[] pdfBytes = pdfService.gerarPdfReceitas(receitas);

        // Verificar que el PDF no es nulo y tiene contenido
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }
}
