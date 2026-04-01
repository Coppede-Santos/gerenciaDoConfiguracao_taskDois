package com.gerencia.taskdois.controller;

import com.gerencia.taskdois.model.Receita;
import com.gerencia.taskdois.repository.ReceitaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping({"/", "/receitas"})
public class ReceitaController {

    private final ReceitaRepository receitaRepository;

    public ReceitaController(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    private boolean isLogado(HttpSession session) {
        return session.getAttribute("usuarioLogado") != null;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("receitas", receitaRepository.findAll());
        return "receitas/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("receita", new Receita());
        return "receitas/form";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Receita receita, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        receitaRepository.save(receita);
        return "redirect:/receitas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        model.addAttribute("receita", receitaRepository.findById(id).orElse(new Receita()));
        return "receitas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (!isLogado(session)) return "redirect:/login";
        receitaRepository.deleteById(id);
        return "redirect:/receitas";
    }
}