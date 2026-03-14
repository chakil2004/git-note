package com.example.servlet;

import com.example.model.Note;
import com.example.model.Etudiant;
import com.example.model.Prof;
import com.example.model.Matiere;
import com.example.service.NoteCrudService;
import com.example.service.EtudiantService;
import com.example.service.ProfService;
import com.example.service.MatiereService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/notes", "/notes/create", "/notes/edit", "/notes/delete"})
public class NoteServlet extends HttpServlet {

    private final NoteCrudService service = new NoteCrudService();
    private final EtudiantService etudiantService = new EtudiantService();
    private final ProfService profService = new ProfService();
    private final MatiereService matiereService = new MatiereService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/notes":
                    list(req, resp);
                    break;
                case "/notes/create":
                    showForm(req, resp);
                    break;
                case "/notes/edit":
                    showEdit(req, resp);
                    break;
                case "/notes/delete":
                    delete(req, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            if ("/notes/create".equals(path)) {
                create(req, resp);
            } else if ("/notes/edit".equals(path)) {
                update(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Note> items = service.listAll();
        req.setAttribute("notes", items);
        req.getRequestDispatcher("/notes.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        // Charger les listes pour les menus déroulants
        List<Etudiant> etudiants = etudiantService.listAll();
        List<Prof> profs = profService.listAll();
        List<Matiere> matieres = matiereService.listAll();
        
        req.setAttribute("etudiants", etudiants);
        req.setAttribute("profs", profs);
        req.setAttribute("matieres", matieres);
        
        req.getRequestDispatcher("/editNote.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String etudiantId = req.getParameter("etudiantId");
        String profId = req.getParameter("profId");
        String matiereId = req.getParameter("matiereId");
        if (etudiantId == null || profId == null || matiereId == null) {
            resp.sendRedirect(req.getContextPath() + "/notes");
            return;
        }
        Note item = service.findById(Integer.parseInt(etudiantId), Integer.parseInt(profId), Integer.parseInt(matiereId));
        if (item == null) {
            resp.sendRedirect(req.getContextPath() + "/notes");
            return;
        }
        req.setAttribute("note", item);
        
        // Charger les listes pour les menus déroulants
        List<Etudiant> etudiants = etudiantService.listAll();
        List<Prof> profs = profService.listAll();
        List<Matiere> matieres = matiereService.listAll();
        
        req.setAttribute("etudiants", etudiants);
        req.setAttribute("profs", profs);
        req.setAttribute("matieres", matieres);
        
        req.getRequestDispatcher("/editNote.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String etudiantId = req.getParameter("etudiantId");
        String profId = req.getParameter("profId");
        String matiereId = req.getParameter("matiereId");
        String valeur = req.getParameter("valeur");
        if (etudiantId != null && profId != null && matiereId != null && valeur != null) {
            service.create(new Note(
                    Integer.parseInt(etudiantId),
                    Integer.parseInt(profId),
                    Integer.parseInt(matiereId),
                    new BigDecimal(valeur)
            ));
        }
        resp.sendRedirect(req.getContextPath() + "/notes");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String etudiantId = req.getParameter("etudiantId");
        String profId = req.getParameter("profId");
        String matiereId = req.getParameter("matiereId");
        String valeur = req.getParameter("valeur");
        if (etudiantId != null && profId != null && matiereId != null && valeur != null) {
            service.update(new Note(
                    Integer.parseInt(etudiantId),
                    Integer.parseInt(profId),
                    Integer.parseInt(matiereId),
                    new BigDecimal(valeur)
            ));
        }
        resp.sendRedirect(req.getContextPath() + "/notes");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String etudiantId = req.getParameter("etudiantId");
        String profId = req.getParameter("profId");
        String matiereId = req.getParameter("matiereId");
        if (etudiantId != null && profId != null && matiereId != null) {
            service.delete(Integer.parseInt(etudiantId), Integer.parseInt(profId), Integer.parseInt(matiereId));
        }
        resp.sendRedirect(req.getContextPath() + "/notes");
    }
}
