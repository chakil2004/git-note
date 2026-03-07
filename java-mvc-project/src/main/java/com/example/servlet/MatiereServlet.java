package com.example.servlet;

import com.example.model.Matiere;
import com.example.service.MatiereService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/matieres", "/matieres/create", "/matieres/edit", "/matieres/delete"})
public class MatiereServlet extends HttpServlet {

    private final MatiereService service = new MatiereService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/matieres":
                    list(req, resp);
                    break;
                case "/matieres/create":
                    showForm(req, resp);
                    break;
                case "/matieres/edit":
                    showEdit(req, resp);
                    break;
                case "/matieres/delete":
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
            if ("/matieres/create".equals(path)) {
                create(req, resp);
            } else if ("/matieres/edit".equals(path)) {
                update(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Matiere> items = service.listAll();
        req.setAttribute("matieres", items);
        req.getRequestDispatcher("/matieres.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/editMatiere.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/matieres");
            return;
        }
        Matiere item = service.findById(Integer.parseInt(id));
        if (item == null) {
            resp.sendRedirect(req.getContextPath() + "/matieres");
            return;
        }
        req.setAttribute("matiere", item);
        req.getRequestDispatcher("/editMatiere.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String nom = req.getParameter("nom");
        if (nom != null && !nom.isBlank()) {
            service.create(new Matiere(nom));
        }
        resp.sendRedirect(req.getContextPath() + "/matieres");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        String nom = req.getParameter("nom");
        if (id != null && !id.isBlank() && nom != null) {
            Matiere item = new Matiere(Integer.parseInt(id), nom);
            service.update(item);
        }
        resp.sendRedirect(req.getContextPath() + "/matieres");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            service.delete(Integer.parseInt(id));
        }
        resp.sendRedirect(req.getContextPath() + "/matieres");
    }
}
