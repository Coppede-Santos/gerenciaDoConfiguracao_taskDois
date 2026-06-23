package com.gerencia.taskdois.service;

import com.gerencia.taskdois.model.Receita;
import com.itextpdf.kernel.exceptions.PdfException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfService {

    public byte[] gerarPdfReceitas(final List<Receita> receitas) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            final PdfWriter writer = new PdfWriter(baos);
            final PdfDocument pdf = new PdfDocument(writer);
            final Document document = new Document(pdf);

            document.add(new Paragraph("Relatório de Receitas")
                    .setFontSize(20)
                    .setBold());
            document.add(new Paragraph("\n"));

            final float[] columnWidths = {1, 3, 2, 2, 2};
            final Table table = new Table(columnWidths);

            table.addHeaderCell("ID");
            table.addHeaderCell("Nome");
            table.addHeaderCell("Tipo");
            table.addHeaderCell("Custo");
            table.addHeaderCell("Ativo");

            for (final Receita receita : receitas) {
                table.addCell(String.valueOf(receita.getId()));
                table.addCell(receita.getNome());
                table.addCell(receita.getTipoReceita() != null ? receita.getTipoReceita() : "");
                table.addCell(receita.getCusto() != null ? "R$ " + receita.getCusto() : "");
                table.addCell(receita.getAtivo() ? "ativa" : "inativa");
            }

            document.add(table);
            document.close();
        } catch (PdfException e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage(), e);
        }

        return baos.toByteArray();
    }
}
