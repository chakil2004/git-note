package com.example.servlet;

import com.example.model.Prof;
import com.example.service.ProfService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/profs", "/profs/create", "/profs/edit", "/profs/delete"})
public class ProfServlet extends HttpServlet {

    private final ProfService service = new ProfService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/profs":
                    list(req, resp);
                    break;
                case "/profs/create":
                    showForm(req, resp);
                    break;
                case "/profs/edit":
                    showEdit(req, resp);
                    break;
                case "/profs/delete":
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
            if ("/profs/create".equals(path)) {
                create(req, resp);
            } else if ("/profs/edit".equals(path)) {
                update(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Prof> items = service.listAll();
        req.setAttribute("profs", items);
        req.getRequestDispatcher("/profs.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/editProf.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/profs");
            return;
        }
        Prof item = service.findById(Integer.parseInt(id));
        if (item == null) {
            resp.sendRedirect(req.getContextPath() + "/profs");
            return;
        }
        req.setAttribute("prof", item);
        req.getRequestDispatcher("/editProf.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String nom = req.getParameter("nom");
        if (nom != null && !nom.isBlank()) {
            service.create(new Prof(nom));
        }
        resp.sendRedirect(req.getContextPath() + "/profs");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        String nom = req.getParameter("nom");
        if (id != null && !id.isBlank() && nom != null) {
            Prof item = new Prof(Integer.parseInt(id), nom);
            service.update(item);
        }
        resp.sendRedirect(req.getContextPath() + "/profs");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            service.delete(Integer.parseInt(id));
        }
        resp.sendRedirect(req.getContextPath() + "/profs");
    }
}
