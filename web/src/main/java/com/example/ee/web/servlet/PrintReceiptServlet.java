package com.example.ee.web.servlet;

import com.example.ee.core.model.TransferReceiptDetails;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/printReceipt")
public class PrintReceiptServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No active session.");
            return;
        }

        TransferReceiptDetails receiptDetails = (TransferReceiptDetails) session.getAttribute("receiptDetails");

        if (receiptDetails == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No receipt details found in session.");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"transaction_receipt_" + receiptDetails.getFormattedTransactionDate().replace(", ", "_").replace(":", "") + ".pdf\"");

        try (OutputStream os = response.getOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, os);
            document.open();

            // Fonts
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.BLUE);
            Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.DARK_GRAY);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);

            // Title
            Paragraph title = new Paragraph("DrodX-PulseBank", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subtitle = new Paragraph("Transaction Receipt", new Font(Font.FontFamily.HELVETICA, 16, Font.ITALIC));
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20f);
            document.add(subtitle);


            document.add(new Paragraph("Transaction Details", sectionFont));
            document.add(Chunk.NEWLINE);

            addDetailRow(document, "Transfer Type:", receiptDetails.getTransactionType(), textFont, valueFont);
            addDetailRow(document, "Transaction Date:", receiptDetails.getFormattedTransactionDate(), textFont, valueFont);
            addDetailRow(document, "Amount:", "$" + receiptDetails.getFormattedAmount(), textFont, valueFont);
            addDetailRow(document, "Description:", receiptDetails.getDescription() != null && !receiptDetails.getDescription().isEmpty() ? receiptDetails.getDescription() : "N/A", textFont, valueFont);

            document.add(Chunk.NEWLINE);


            document.add(new Paragraph("Sender Details", sectionFont));
            document.add(Chunk.NEWLINE);

            addDetailRow(document, "Sender Name:", receiptDetails.getSenderName(), textFont, valueFont);
            addDetailRow(document, "Sender Account No:", receiptDetails.getSenderAccountNo(), textFont, valueFont);
            addDetailRow(document, "Sender Account Type:", receiptDetails.getSenderAccountType(), textFont, valueFont);

            document.add(Chunk.NEWLINE);


            document.add(new Paragraph("Recipient Details", sectionFont));
            document.add(Chunk.NEWLINE);

            addDetailRow(document, "Recipient Account No:", receiptDetails.getRecipientAccountNo(), textFont, valueFont);


            Paragraph footer = new Paragraph("Thank you for banking with DrodX-PulseBank.", new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30f);
            document.add(footer);

            document.close();


            session.removeAttribute("receiptDetails");

        } catch (DocumentException e) {
            System.err.println("Error generating PDF: " + e.getMessage());
            e.printStackTrace();
            throw new ServletException("Error generating PDF", e);
        }
    }


    private void addDetailRow(Document document, String label, String value, Font labelFont, Font valueFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Chunk(label, labelFont));
        p.add(new Chunk(" ", labelFont));
        p.add(new Chunk(value, valueFont));
        p.setSpacingBefore(5f);
        document.add(p);
    }
}