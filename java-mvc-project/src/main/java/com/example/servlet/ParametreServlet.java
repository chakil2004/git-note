package com.example.servlet;

import com.example.model.Parametre;
import com.example.model.Matiere;
import com.example.model.Methode;
import com.example.model.Solution;
import com.example.service.ParametreService;
import com.example.service.MatiereService;
import com.example.service.MethodeService;
import com.example.service.SolutionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/parametres", "/parametres/create", "/parametres/edit", "/parametres/delete"})
public class ParametreServlet extends HttpServlet {

    private final ParametreService service = new ParametreService();
    private final MatiereService matiereService = new MatiereService();
    private final MethodeService methodeService = new MethodeService();
    private final SolutionService solutionService = new SolutionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            switch (path) {
                case "/parametres":
                    list(req, resp);
                    break;
                case "/parametres/create":
                    showForm(req, resp);
                    break;
                case "/parametres/edit":
                    showEdit(req, resp);
                    break;
                case "/parametres/delete":
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
            if ("/parametres/create".equals(path)) {
                create(req, resp);
            } else if ("/parametres/edit".equals(path)) {
                update(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<Parametre> items = service.listAll();
        List<Matiere> matieres = matiereService.listAll();
        List<Methode> methodes = methodeService.listAll();
        List<Solution> solutions = solutionService.listAll();
        
        req.setAttribute("parametres", items);
        req.setAttribute("matieres", matieres);
        req.setAttribute("methodes", methodes);
        req.setAttribute("solutions", solutions);
        req.getRequestDispatcher("/parametres.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/editParametre.jsp").forward(req, resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            resp.sendRedirect(req.getContextPath() + "/parametres");
            return;
        }
        Parametre item = service.findById(Integer.parseInt(id));
        if (item == null) {
            resp.sendRedirect(req.getContextPath() + "/parametres");
            return;
        }
        req.setAttribute("parametre", item);
        req.getRequestDispatcher("/editParametre.jsp").forward(req, resp);
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String matiereId = req.getParameter("matiereId");
        String methodeId = req.getParameter("methodeId");
        String solutionId = req.getParameter("solutionId");
        String seuil = req.getParameter("seuil");
        if (matiereId != null && methodeId != null && solutionId != null && seuil != null) {
            service.create(new com.example.model.Parametre(
                    Integer.parseInt(matiereId),
                    Integer.parseInt(methodeId),
                    Integer.parseInt(solutionId),
                    new java.math.BigDecimal(seuil)
            ));
        }
        resp.sendRedirect(req.getContextPath() + "/parametres");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        String matiereId = req.getParameter("matiereId");
        String methodeId = req.getParameter("methodeId");
        String solutionId = req.getParameter("solutionId");
        String seuil = req.getParameter("seuil");
        if (id != null && !id.isBlank() && matiereId != null && methodeId != null && solutionId != null && seuil != null) {
            service.update(new com.example.model.Parametre(
                    Integer.parseInt(id),
                    Integer.parseInt(matiereId),
                    Integer.parseInt(methodeId),
                    Integer.parseInt(solutionId),
                    new java.math.BigDecimal(seuil)
            ));
        }
        resp.sendRedirect(req.getContextPath() + "/parametres");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            service.delete(Integer.parseInt(id));
        }
        resp.sendRedirect(req.getContextPath() + "/parametres");
    }
}
