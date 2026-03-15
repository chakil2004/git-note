<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Parametre" %>
<%@ page import="com.example.model.Matiere" %>
<%@ page import="com.example.model.Methode" %>
<%@ page import="com.example.model.Solution" %>
<%@ page import="java.util.Collections" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Paramètres");
%>
<jsp:include page="includes/header.jsp"/>

<%!
    // Fonction pour trouver le nom d'une matière par son ID
    private String findMatiereName(int matiereId, List<Matiere> matieres) {
        if (matieres == null) return String.valueOf(matiereId);
        for (Matiere m : matieres) {
            if (m.getId() == matiereId) {
                return m.getNom();
            }
        }
        return String.valueOf(matiereId);
    }
    
    // Fonction pour trouver le nom d'une méthode par son ID
    private String findMethodeName(int methodeId, List<Methode> methodes) {
        if (methodes == null) return String.valueOf(methodeId);
        for (Methode m : methodes) {
            if (m.getId() == methodeId) {
                return m.getStringValeur();
            }
        }
        return String.valueOf(methodeId);
    }
    
    // Fonction pour trouver le nom d'une solution par son ID
    private String findSolutionName(int solutionId, List<Solution> solutions) {
        if (solutions == null) return String.valueOf(solutionId);
        for (Solution s : solutions) {
            if (s.getId() == solutionId) {
                return s.getStringValeur();
            }
        }
        return String.valueOf(solutionId);
    }
%>

<%
    // Récupérer les listes depuis la requête
    List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
    List<Methode> methodes = (List<Methode>) request.getAttribute("methodes");
    List<Solution> solutions = (List<Solution>) request.getAttribute("solutions");
%>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<div class="page-actions">
    <a href="${pageContext.request.contextPath}/parametres/create" class="btn btn-primary">
        ➕ Ajouter un paramètre
    </a>
</div>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Matière</th>
        <th>Méthode</th>
        <th>Solution</th>
        <th>Seuil</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
    List<Parametre> parametres = (List<Parametre>) request.getAttribute("parametres");
    if (parametres != null) {
        for (Parametre param : parametres) {
%>
        <tr>
            <td><%= param.getId() %></td>
            <td><%= findMatiereName(param.getMatiereId(), matieres) %></td>
            <td>
                <span class="badge-method"><%= findMethodeName(param.getMethodeId(), methodes) %></span>
            </td>
            <td>
                <span class="badge-solution"><%= findSolutionName(param.getSolutionId(), solutions) %></span>
            </td>
            <td>
                <span class="badge-seuil"><%= param.getSeuil() %></span>
            </td>
            <td>
                <div class="table-actions">
                    <a href="${pageContext.request.contextPath}/parametres/edit?id=<%= param.getId() %>" class="btn btn-secondary">
                        ✏️ Modifier
                    </a>
                    <a href="${pageContext.request.contextPath}/parametres/delete?id=<%= param.getId() %>" 
                       class="btn btn-danger" 
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce paramètre ?');">
                        🗑️ Supprimer
                    </a>
                </div>
            </td>
        </tr>
<%
        }
    }
%>
    </tbody>
</table>

<%
if (parametres == null || parametres.isEmpty()) {
%>
    <div class="text-center mt-20">
        <p class="text-muted">Aucun paramètre trouvé.</p>
        <a href="${pageContext.request.contextPath}/parametres/create" class="btn btn-primary">
            ➕ Ajouter le premier paramètre
        </a>
    </div>
<%
}
%>

<jsp:include page="includes/footer.jsp"/>
