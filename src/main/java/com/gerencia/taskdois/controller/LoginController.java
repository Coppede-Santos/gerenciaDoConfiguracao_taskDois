package com.gerencia.taskdois.controller;

import com.gerencia.taskdois.model.Usuario;
import com.gerencia.taskdois.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UsuarioRepository usuarioRepository;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String senha, Model model, HttpSession session) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByLoginAndSenha(login, senha);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if ("ativo".equalsIgnoreCase(usuario.getSituacao())) {
                session.setAttribute("usuarioLogado", usuario);
                return "redirect:/receitas";
            } else {
                model.addAttribute("erro", "Usuário inativo!");
                return "login";
            }
        } else {
            model.addAttribute("erro", "Login ou senha inválidos!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}