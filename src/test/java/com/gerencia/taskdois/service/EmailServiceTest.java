package com.gerencia.taskdois.service;

import com.gerencia.taskdois.model.Receita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    private Receita receita;

    @BeforeEach
    void setUp() {
        // Configurar campos de email para el servicio
        ReflectionTestUtils.setField(emailService, "emailTo", "test@example.com");
        ReflectionTestUtils.setField(emailService, "emailFrom", "noreply@test.com");

        // Crear una receta de ejemplo para usar en los tests
        receita = new Receita();
        receita.setId(1L);
        receita.setNome("Bolo de Chocolate");
        receita.setTipoReceita("doce");
        receita.setCusto(new BigDecimal("25.50"));
        receita.setStatus("ativa");
    }

    // Test 1: Verificar que se envía email cuando se crea una receta
    @Test
    void testEnviarEmailReceitaCriada() {
        emailService.enviarEmailReceitaCriada(receita);

        // Verificar que el metodo send fue llamado una vez
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    // Test 2: Verificar que se envía email cuando se actualiza una receta
    @Test
    void testEnviarEmailReceitaAtualizada() {
        emailService.enviarEmailReceitaAtualizada(receita);

        // Verificar que el metodo send fue llamado una vez
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
