package com.gerencia.taskdois.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    // Test 12: Verificar getters y setters de Usuario
    @Test
    void testGettersSetters() {
        // Crear y configurar usuario
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setLogin("joao");
        usuario.setSenha("senha123");
        usuario.setSituacao("ativo");

        // Verificar que todos los valores se guardaron correctamente
        assertEquals(1L, usuario.getId());
        assertEquals("João Silva", usuario.getNome());
        assertEquals("joao", usuario.getLogin());
        assertEquals("senha123", usuario.getSenha());
        assertEquals("ativo", usuario.getSituacao());
    }
}
