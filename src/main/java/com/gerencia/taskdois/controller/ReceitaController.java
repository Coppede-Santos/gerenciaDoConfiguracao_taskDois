package com.gerencia.taskdois.controller;

import com.gerencia.taskdois.model.Receita;
import com.gerencia.taskdois.repository.ReceitaRepository;
import com.gerencia.taskdois.service.EmailService;
import com.gerencia.taskdois.service.PdfService;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping({"/", "/receitas"})
public class ReceitaController {

    private final ReceitaRepository receitaRepository;
    private final EmailService emailService;
    private final PdfService pdfService;
    private static String redLogin = "redirect:/login";

    public ReceitaController(final ReceitaRepository receitaRepository,final EmailService emailService,final PdfService pdfService) {
        this.receitaRepository = receitaRepository;
        this.emailService = emailService;
        this.pdfService = pdfService;
    }

    private boolean isLogado(final HttpSession session) {
        return session.getAttribute("usuarioLogado") != null;
    }

    @GetMapping
    public String listar(
            final @RequestParam(required = false) String status,
            final @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            final @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            final Model model,
            final HttpSession session) {
        if (!isLogado(session)) {
            return redLogin;
        }

        List<Receita> receitas;
        if (status != null || dataInicio != null || dataFim != null) {
            receitas = receitaRepository.findByFiltros(status, dataInicio, dataFim);
        } else {
            receitas = receitaRepository.findAll();
        }

        model.addAttribute("receitas", receitas);
        model.addAttribute("statusFiltro", status);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        return "receitas/lista";
    }

    @GetMapping("/nova")
    public String nova(final Model model,final HttpSession session) {
        if (!isLogado(session)) {
            return redLogin;
        }
        model.addAttribute("receita", new Receita());
        return "receitas/form";
    }

    @PostMapping("/salvar")
    public String salvar(final @ModelAttribute Receita receita,final HttpSession session) {
        if (!isLogado(session)) {
            return redLogin;
        }

        final boolean isNew = receita.getId() == null;
        final Receita savedReceita = receitaRepository.save(receita);

        if (isNew) {
            emailService.enviarEmailReceitaCriada(savedReceita);
        } else {
            emailService.enviarEmailReceitaAtualizada(savedReceita);
        }

        return "redirect:/receitas";
    }

    @GetMapping("/editar/{id}")
    public String editar(final @PathVariable Long id,final Model model,final HttpSession session) {
        if (!isLogado(session)) {
            return redLogin;
        }
        model.addAttribute("receita", receitaRepository.findById(id).orElse(new Receita()));
        return "receitas/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(final @PathVariable Long id, final HttpSession session) {
        if (!isLogado(session)) {
            return redLogin;
        }
        receitaRepository.deleteById(id);
        return "redirect:/receitas";
    }

    @GetMapping("/exportar-pdf")
    public ResponseEntity<byte[]> exportarPdf(
            final @RequestParam(required = false) String status,
            final @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            final @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            final HttpSession session) {
        if (!isLogado(session)) {
            return ResponseEntity.status(401).build();
        }

        List<Receita> receitas;
        if (status != null || dataInicio != null || dataFim != null) {
            receitas = receitaRepository.findByFiltros(status, dataInicio, dataFim);
        } else {
            receitas = receitaRepository.findAll();
        }

        final byte[] pdfBytes = pdfService.gerarPdfReceitas(receitas);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "receitas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
