package com.example.servlet;

import com.example.model.Methode;
import com.example.service.MethodeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/methodes", "/methodes/create", "/methodes/edit", "/methodes/delete"})
public class MethodeServlet extends HttpServlet {

    private final MethodeService service = new MethodeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/methodes":
                    list(req, resp);
                    break;
                case "/methodes/create":
                    showForm(req, resp);
                    break;
                case "/methodes/edit":
                    showEdit(req, resp);
                    break;
                case "/methodes/delete":
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
            if ("/methodes/create".equals(path)) {
                create(req, resp);
            } else if ("/methodes/edit".equals(path)) {
                update(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Methode> items = service.listAll();
        req.setAttribute("methodes", items);
        req.getRequestDispatcher("/methodes.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/editMethode.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/methodes");
            return;
        }
        Methode item = service.findById(Integer.parseInt(id));
        if (item == null) {
            resp.sendRedirect(req.getContextPath() + "/methodes");
            return;
        }
        req.setAttribute("methode", item);
        req.getRequestDispatcher("/editMethode.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String stringValeur = req.getParameter("stringValeur");
        String ref = req.getParameter("ref");
        if (stringValeur != null && ref != null) {
            service.create(new Methode(stringValeur, ref));
        }
        resp.sendRedirect(req.getContextPath() + "/methodes");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        String stringValeur = req.getParameter("stringValeur");
        String ref = req.getParameter("ref");
        if (id != null && !id.isBlank() && stringValeur != null && ref != null) {
            service.update(new Methode(Integer.parseInt(id), stringValeur, ref));
        }
        resp.sendRedirect(req.getContextPath() + "/methodes");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            service.delete(Integer.parseInt(id));
        }
        resp.sendRedirect(req.getContextPath() + "/methodes");
    }
}
