package com.servlet.vote.controller;

import java.io.IOException;
import java.awt.Color;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.servlet.vote.dao.VoterDAO;
import com.servlet.vote.dao.VoterDAOImpl;
import com.servlet.vote.dto.Voter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DownloadVoterID")
public class DownloadVoterIDServlet extends HttpServlet {

    private VoterDAO voterDAO = new VoterDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("voterId") == null) {
            resp.sendRedirect("VoterLogin.jsp");
            return;
        }

        Integer voterId = (Integer) session.getAttribute("voterId");
        Voter voter = voterDAO.getVoterById(voterId);

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition",
                "attachment; filename=VoterID-" + voter.getVoterUid() + ".pdf");

        PDDocument doc = new PDDocument();
        PDRectangle cardSize = new PDRectangle(242, 153);
        PDPage page = new PDPage(cardSize);
        doc.addPage(page);

        PDPageContentStream cs = new PDPageContentStream(doc, page);

        // Background
        cs.setNonStrokingColor(new Color(204, 232, 228));
        cs.addRect(0, 0, cardSize.getWidth(), cardSize.getHeight());
        cs.fill();

        // Header
        cs.setNonStrokingColor(new Color(245, 226, 204));
        cs.addRect(0, 120, cardSize.getWidth(), 33);
        cs.fill();

        writeCentered(cs, "ELECTION COMMISSION OF INDIA",
                PDType1Font.HELVETICA_BOLD, 7, Color.BLACK, 138);
        writeCentered(cs, "ELECTOR PHOTO IDENTITY CARD",
                PDType1Font.HELVETICA, 6, Color.BLACK, 129);

        // Emblem Placeholder
        cs.setNonStrokingColor(Color.DARK_GRAY);
        cs.addRect(5, 130, 10, 18);
        cs.fill();

        // PHOTO
        String photo = voter.getPhoto();
        if (photo != null && !photo.isBlank()) {
            if (photo.contains("/"))
                photo = photo.substring(photo.lastIndexOf("/") + 1);

            File photoFile = new File("C:/UniVoteUploads/" + photo);

            if (photoFile.exists()) {
                PDImageXObject img = PDImageXObject.createFromFile(photoFile.getAbsolutePath(), doc);
                cs.drawImage(img, 18, 63, 55, 55);
            } else insertTextNoPhoto(cs);

        } else insertTextNoPhoto(cs);

        // DETAILS
        int x = 80;
        int y = 107;

        // ⭐ FIRST LINE: Voter ID, Bigger font
        cs.setFont(PDType1Font.HELVETICA_BOLD, 8);
        cs.setNonStrokingColor(Color.BLACK);
        cs.beginText();
        cs.newLineAtOffset(x, y);
        cs.showText("Voter ID: " + voter.getVoterUid());
        cs.endText();

        // Other Details (shift down)
        y -= 12;
        textRow(cs, "Name", voter.getName(), x, y);
        textRow(cs, "Email", voter.getEmail(), x, y - 12);
        textRow(cs, "DOB", voter.getDob(), x, y - 24);

        String masked = "XXXX-XXXX-" + voter.getAadhar().substring(8);
        textRow(cs, "Aadhaar", masked, x, y - 36);

        // FOOTER
        writeCentered(cs, "System Generated | Uni-Vote",
                PDType1Font.HELVETICA_OBLIQUE, 5.5f, Color.BLACK, 5);

        cs.close();
        doc.save(resp.getOutputStream());
        doc.close();
    }

    private void textRow(PDPageContentStream cs, String label,
                         String value, int x, int y) throws IOException {
        cs.setFont(PDType1Font.HELVETICA_BOLD, 6.5f);
        cs.beginText();
        cs.newLineAtOffset(x, y);
        cs.showText(label + ": ");
        cs.endText();

        cs.setFont(PDType1Font.HELVETICA, 6.5f);
        cs.beginText();
        cs.newLineAtOffset(x + 38, y);
        cs.showText(value != null ? value : "N/A");
        cs.endText();
    }

    private void writeCentered(PDPageContentStream cs,
            String text, PDType1Font font,
            float size, Color color, float y) throws IOException {

        float width = font.getStringWidth(text) / 1000 * size;
        float posX = (242 - width) / 2;

        cs.setFont(font, size);
        cs.setNonStrokingColor(color);
        cs.beginText();
        cs.newLineAtOffset(posX, y);
        cs.showText(text);
        cs.endText();
    }

    private void insertTextNoPhoto(PDPageContentStream cs) throws IOException {
        cs.setNonStrokingColor(Color.RED);
        cs.setFont(PDType1Font.HELVETICA_BOLD, 6);
        cs.beginText();
        cs.newLineAtOffset(30, 90);
        cs.showText("NO PHOTO");
        cs.endText();
    }
}
