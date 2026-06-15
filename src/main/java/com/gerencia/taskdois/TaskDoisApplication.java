package com.gerencia.taskdois;

import com.gerencia.taskdois.model.Usuario;
import com.gerencia.taskdois.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TaskDoisApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TaskDoisApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(final UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                final Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setLogin("messi");
                admin.setSenha("melhorquepele");
                admin.setSituacao("ativo");
                usuarioRepository.save(admin);
            }
        };
    }

}
