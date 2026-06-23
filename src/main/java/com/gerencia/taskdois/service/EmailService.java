package com.gerencia.taskdois.service;

import com.gerencia.taskdois.model.Receita;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.email.to:admin@example.com}")
    private String emailTo;

    @Value("${app.email.from:noreply@taskdois.com}")
    private String emailFrom;

    public void enviarEmailReceitaCriada(final Receita receita) {
        try {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailTo);
            message.setSubject("Nova Receita Criada: " + receita.getNome());
            message.setText(construirMensagemCriacao(receita));
            mailSender.send(message);
        } catch (MailException e) {
            log.error("Erro ao enviar email: {}", e.getMessage(), e);
        }
    }

    public void enviarEmailReceitaAtualizada(final Receita receita) {
        try {
            final SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailTo);
            message.setSubject("Receita Atualizada: " + receita.getNome());
            message.setText(construirMensagemAtualizacao(receita));
            mailSender.send(message);
        } catch (MailException e) {
            // Log error but don't break the flow
            log.error("Erro ao enviar email: {}", e.getMessage(), e);
        }
    }

    private String construirMensagemCriacao(final Receita receita) {
        return String.format("""
            Uma nova receita foi criada no sistema:

            Nome: %s
            Descrição: %s
            Tipo: %s
            Custo: R$ %s
            Data de Registro: %s
            Status: %s

            Acesse o sistema para mais detalhes.
            """,
            receita.getNome(),
            receita.getDescricao(),
            receita.getTipoReceita(),
            receita.getCusto(),
            receita.getDataRegistro(),
            receita.getAtivo() ? "ativa" : "inativa"
        );
    }

    private String construirMensagemAtualizacao(final Receita receita) {
        return String.format("""
            Uma receita foi atualizada no sistema:

            Nome: %s
            Descrição: %s
            Tipo: %s
            Custo: R$ %s
            Data de Registro: %s
            Status: %s

            Acesse o sistema para mais detalhes.
            """,
            receita.getNome(),
            receita.getDescricao(),
            receita.getTipoReceita(),
            receita.getCusto(),
            receita.getDataRegistro(),
            receita.getAtivo() ? "ativa" : "inativa"
        );
    }
}
