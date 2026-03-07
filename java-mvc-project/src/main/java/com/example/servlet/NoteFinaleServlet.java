package com.example.servlet;

import com.example.model.NoteFinale;
import com.example.model.Etudiant;
import com.example.model.Matiere;
import com.example.service.NoteFinaleService;
import com.example.service.EtudiantService;
import com.example.service.MatiereService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/noteFinale")
public class NoteFinaleServlet extends HttpServlet {

    private final NoteFinaleService noteFinaleService = new NoteFinaleService();
    private final EtudiantService etudiantService = new EtudiantService();
    private final MatiereService matiereService = new MatiereService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<NoteFinale> notes = noteFinaleService.listAll();
            List<Etudiant> etudiants = etudiantService.listAll();
            List<Matiere> matieres = matiereService.listAll();
            
            req.setAttribute("notes", notes);
            req.setAttribute("etudiants", etudiants);
            req.setAttribute("matieres", matieres);
            req.getRequestDispatcher("/noteFinale.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
