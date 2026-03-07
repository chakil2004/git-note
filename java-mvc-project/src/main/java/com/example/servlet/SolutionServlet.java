package com.example.servlet;

import com.example.model.Solution;
import com.example.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/solutions", "/solutions/create", "/solutions/edit", "/solutions/delete"})
public class SolutionServlet extends HttpServlet {

    private final SolutionService service = new SolutionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/solutions":
                    list(req, resp);
                    break;
                case "/solutions/create":
                    showForm(req, resp);
                    break;
                case "/solutions/edit":
                    showEdit(req, resp);
                    break;
                case "/solutions/delete":
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
            if ("/solutions/create".equals(path)) {
                create(req, resp);
            } else if ("/solutions/edit".equals(path)) {
                update(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Solution> items = service.listAll();
        req.setAttribute("solutions", items);
        req.getRequestDispatcher("/solutions.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/editSolution.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/solutions");
            return;
        }
        Solution item = service.findById(Integer.parseInt(id));
        if (item == null) {
            resp.sendRedirect(req.getContextPath() + "/solutions");
            return;
        }
        req.setAttribute("solution", item);
        req.getRequestDispatcher("/editSolution.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String stringValeur = req.getParameter("stringValeur");
        String ref = req.getParameter("ref");
        if (stringValeur != null && ref != null) {
            service.create(new Solution(stringValeur, ref));
        }
        resp.sendRedirect(req.getContextPath() + "/solutions");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        String stringValeur = req.getParameter("stringValeur");
        String ref = req.getParameter("ref");
        if (id != null && !id.isBlank() && stringValeur != null && ref != null) {
            service.update(new Solution(Integer.parseInt(id), stringValeur, ref));
        }
        resp.sendRedirect(req.getContextPath() + "/solutions");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            service.delete(Integer.parseInt(id));
        }
        resp.sendRedirect(req.getContextPath() + "/solutions");
    }
}
