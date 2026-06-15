package com.gerencia.taskdois.controller;

import com.gerencia.taskdois.model.Usuario;
import com.gerencia.taskdois.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private LoginController loginController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Crear un usuario de ejemplo para los tests
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setLogin("testuser");
        usuario.setSenha("password");
        usuario.setSituacao("ativo");
    }

    // Test 9: Verificar login exitoso
    @Test
    void testLoginSuccess() {
        // Configurar mock para retornar el usuario
        when(usuarioRepository.findByLoginAndSenha("testuser", "password"))
                .thenReturn(Optional.of(usuario));

        // Ejecutar login
        String view = loginController.login("testuser", "password", model, session);

        // Verificar redirección correcta y sesión guardada
        assertEquals("redirect:/receitas", view);
        verify(session).setAttribute("usuarioLogado", usuario);
    }

    // Test 10: Verificar login con credenciales incorrectas
    @Test
    void testLoginCredencialesInvalidas() {
        // Configurar mock para no encontrar usuario
        when(usuarioRepository.findByLoginAndSenha("testuser", "wrongpassword"))
                .thenReturn(Optional.empty());

        // Ejecutar login con contraseña incorrecta
        String view = loginController.login("testuser", "wrongpassword", model, session);

        // Verificar que regresa a login con mensaje de error
        assertEquals("login", view);
        verify(model).addAttribute("erro", "Login ou senha inválidos!");
    }

    // Test 11: Verificar que logout invalida la sesión
    @Test
    void testLogout() {
        // Ejecutar logout
        String view = loginController.logout(session);

        // Verificar redirección y que se invalidó la sesión
        assertEquals("redirect:/login", view);
        verify(session).invalidate();
    }
}
