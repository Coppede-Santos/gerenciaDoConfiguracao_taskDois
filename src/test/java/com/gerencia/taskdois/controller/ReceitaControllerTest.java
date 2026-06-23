package com.gerencia.taskdois.controller;

import com.gerencia.taskdois.model.Receita;
import com.gerencia.taskdois.repository.ReceitaRepository;
import com.gerencia.taskdois.service.EmailService;
import com.gerencia.taskdois.service.PdfService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceitaControllerTest {

    @Mock
    private ReceitaRepository receitaRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PdfService pdfService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private ReceitaController receitaController;

    @BeforeEach
    void setUp() {
        // Simular que hay un usuario logueado
        when(session.getAttribute("usuarioLogado")).thenReturn(new Object());
    }

    // Test 4: Verificar que listar muestra todas las recetas
    @Test
    void testListar() {
        // Crear lista de recetas
        List<Receita> receitas = new ArrayList<>();
        when(receitaRepository.findAll()).thenReturn(receitas);

        // Ejecutar el método listar
        String view = receitaController.listar(null, null, null, model, session);

        // Verificar que se llama al repositorio y se retorna la vista correcta
        assertEquals("receitas/lista", view);
        verify(receitaRepository).findAll();
        verify(model).addAttribute("receitas", receitas);
    }

    // Test 5: Verificar que listar con filtros usa el método correcto
    @Test
    void testListarConFiltros() {
        // Crear lista vacía
        List<Receita> receitas = new ArrayList<>();
        when(receitaRepository.findByFiltros(any(), any(), any())).thenReturn(receitas);

        // Ejecutar listar con filtros
        String view = receitaController.listar(true, LocalDate.now(), LocalDate.now(), model, session);

        // Verificar que se usa el método de filtros
        assertEquals("receitas/lista", view);
        verify(receitaRepository).findByFiltros(any(), any(), any());
        verify(receitaRepository, never()).findAll();
    }

    // Test 6: Verificar que formulario de nueva receta carga correctamente
    @Test
    void testNova() {
        // Ejecutar método nova
        String view = receitaController.nova(model, session);

        // Verificar vista correcta y que se agrega receta vacía al modelo
        assertEquals("receitas/form", view);
        verify(model).addAttribute(eq("receita"), any(Receita.class));
    }

    // Test 7: Verificar que guardar nueva receta envía email
    @Test
    void testSalvarNovaReceita() {
        // Crear receta nueva (sin ID)
        Receita receita = new Receita();
        receita.setNome("Nueva Receita");
        when(receitaRepository.save(any(Receita.class))).thenReturn(receita);

        // Ejecutar salvar
        String view = receitaController.salvar(receita, session);

        // Verificar que se guarda y se envía email de creación
        assertEquals("redirect:/receitas", view);
        verify(receitaRepository).save(receita);
        verify(emailService).enviarEmailReceitaCriada(receita);
    }

    // Test 8: Verificar que actualizar receta existente envía email de actualización
    @Test
    void testSalvarReceitaExistente() {
        // Crear receta existente (con ID)
        Receita receita = new Receita();
        receita.setId(1L);
        receita.setNome("Receita Existente");
        when(receitaRepository.save(any(Receita.class))).thenReturn(receita);

        // Ejecutar salvar
        String view = receitaController.salvar(receita, session);

        // Verificar que se envía email de actualización
        assertEquals("redirect:/receitas", view);
        verify(emailService).enviarEmailReceitaAtualizada(receita);
        verify(emailService, never()).enviarEmailReceitaCriada(any());
    }

    // Test 9: Verificar que editar carga la receta correcta
    @Test
    void testEditar() {
        // Crear receta
        Receita receita = new Receita();
        receita.setId(1L);
        when(receitaRepository.findById(1L)).thenReturn(Optional.of(receita));

        // Ejecutar editar
        String view = receitaController.editar(1L, model, session);

        // Verificar que se carga la receta correcta
        assertEquals("receitas/form", view);
        verify(receitaRepository).findById(1L);
        verify(model).addAttribute("receita", receita);
    }

    // Test 10: Verificar que eliminar borra la receta
    @Test
    void testExcluir() {
        // Ejecutar excluir
        String view = receitaController.excluir(1L, session);

        // Verificar que se elimina y redirige
        assertEquals("redirect:/receitas", view);
        verify(receitaRepository).deleteById(1L);
    }

    // Test 11: Verificar que exportar PDF genera el archivo correctamente
    @Test
    void testExportarPdf() {
        // Configurar datos de prueba
        List<Receita> receitas = new ArrayList<>();
        byte[] pdfBytes = new byte[]{1, 2, 3};
        when(receitaRepository.findAll()).thenReturn(receitas);
        when(pdfService.gerarPdfReceitas(receitas)).thenReturn(pdfBytes);

        // Ejecutar exportar PDF
        ResponseEntity<byte[]> response = receitaController.exportarPdf(null, null, null, session);

        // Verificar que se genera el PDF correctamente
        assertEquals(200, response.getStatusCodeValue());
        assertArrayEquals(pdfBytes, response.getBody());
        verify(pdfService).gerarPdfReceitas(receitas);
    }

    // Test 12: Verificar que sin sesión redirige a login
    @Test
    void testListarSinSesion() {
        // Simular que no hay sesión
        when(session.getAttribute("usuarioLogado")).thenReturn(null);

        // Ejecutar listar sin sesión
        String view = receitaController.listar(null, null, null, model, session);

        // Verificar que redirige a login
        assertEquals("redirect:/login", view);
        verify(receitaRepository, never()).findAll();
    }
}
