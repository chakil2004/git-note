package com.example.servlet;

import com.example.config.DatabaseConfig;
import com.example.controller.DeliberationService;
import com.example.model.Etudiant;
import com.example.model.Matiere;
import com.example.model.NoteFinale;
import com.example.service.EtudiantService;
import com.example.service.MatiereService;
import com.example.service.NoteFinaleService;
import jakarta.servlet.ServletException;
import java.math.BigDecimal;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/deliberation")
public class DeliberationServlet extends HttpServlet {

    private final DeliberationService deliberationService = new DeliberationService();
    private final EtudiantService etudiantService = new EtudiantService();
    private final MatiereService matiereService = new MatiereService();
    private final NoteFinaleService noteFinaleService = new NoteFinaleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Etudiant> etudiants = etudiantService.listAll();
            List<Matiere> matieres = matiereService.listAll();
            List<NoteFinale> notesFinales = noteFinaleService.listAll();
            
            req.setAttribute("etudiants", etudiants);
            req.setAttribute("matieres", matieres);
            req.setAttribute("notesFinales", notesFinales);
            req.getRequestDispatcher("/deliberation.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String etudiantId = req.getParameter("etudiantId");
        String matiereId = req.getParameter("matiereId");
        
        if (etudiantId != null && matiereId != null && !etudiantId.isBlank() && !matiereId.isBlank()) {
            try (Connection conn = DatabaseConfig.getConnection()) {
                BigDecimal finalValue = deliberationService.delibererPourEtudiantMatiere(
                    conn, 
                    Integer.parseInt(etudiantId), 
                    Integer.parseInt(matiereId)
                );
                
                if (finalValue != null) {
                    req.setAttribute("message", "Délibération réussie! Note finale: " + finalValue);
                } else {
                    req.setAttribute("message", "Aucune note trouvée pour cet étudiant/matière");
                }
            } catch (SQLException e) {
                req.setAttribute("error", "Erreur lors de la délibération: " + e.getMessage());
            }
        }
        
        // Recharger les données pour l'affichage
        doGet(req, resp);
    }
}
