package com.example.servlet;

import com.example.model.Etudiant;
import com.example.service.EtudiantService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/etudiants", "/etudiants/create", "/etudiants/edit", "/etudiants/delete"})
public class EtudiantServlet extends HttpServlet {

    private final EtudiantService service = new EtudiantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/etudiants":
                    list(req, resp);
                    break;
                case "/etudiants/create":
                    showCreate(req, resp);
                    break;
                case "/etudiants/edit":
                    showEdit(req, resp);
                    break;
                case "/etudiants/delete":
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
            if ("/etudiants/create".equals(path)) {
                create(req, resp);
            } else if ("/etudiants/edit".equals(path)) {
                update(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Etudiant> etudiants = service.listAll();
        req.setAttribute("etudiants", etudiants);
        req.getRequestDispatcher("/etudiants.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/editEtudiant.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/etudiants");
            return;
        }
        Etudiant e = service.findById(Integer.parseInt(id));
        if (e == null) {
            resp.sendRedirect(req.getContextPath() + "/etudiants");
            return;
        }
        req.setAttribute("etudiant", e);
        req.getRequestDispatcher("/editEtudiant.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String nom = req.getParameter("nom");
        if (nom != null && !nom.isBlank()) {
            service.create(new Etudiant(nom));
        }
        resp.sendRedirect(req.getContextPath() + "/etudiants");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        String nom = req.getParameter("nom");
        if (id != null && !id.isBlank() && nom != null) {
            Etudiant e = new Etudiant(Integer.parseInt(id), nom);
            service.update(e);
        }
        resp.sendRedirect(req.getContextPath() + "/etudiants");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            service.delete(Integer.parseInt(id));
        }
        resp.sendRedirect(req.getContextPath() + "/etudiants");
    }
}
