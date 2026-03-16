<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Parametre" %>
<%@ page import="com.example.model.Matiere" %>
<%@ page import="com.example.model.Methode" %>
<%@ page import="com.example.model.Solution" %>
<%@ page import="java.util.List" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Paramètres");
%>
<jsp:include page="includes/header.jsp"/>

<c:set var="isNew" value="${empty parametre}" />
<h1>${isNew ? 'Ajouter un paramètre' : 'Modifier paramètre'}</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/${isNew ? 'parametres/create' : 'parametres/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${parametre.id}" />
    </c:if>
    
    <div class="form-group">
        <label for="matiereId">Matière</label>
        <select name="matiereId" id="matiereId" required>
            <option value="">-- Choisir une matière --</option>
            <%
            List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
            if (matieres != null) {
                Parametre param = (Parametre) request.getAttribute("parametre");
                int selectedMatiereId = (param != null) ? param.getMatiereId() : -1;
                for (Matiere matiere : matieres) {
            %>
                <option value="<%= matiere.getId() %>" <%= (matiere.getId() == selectedMatiereId) ? "selected" : "" %>>
                    <%= matiere.getNom() %>
                </option>
            <%
                }
            }
            %>
        </select>
    </div>
    
    <div class="form-group">
        <label for="methodeId">Méthode</label>
        <select name="methodeId" id="methodeId" required>
            <option value="">-- Choisir une méthode --</option>
            <%
            List<Methode> methodes = (List<Methode>) request.getAttribute("methodes");
            if (methodes != null) {
                Parametre param = (Parametre) request.getAttribute("parametre");
                int selectedMethodeId = (param != null) ? param.getMethodeId() : -1;
                for (Methode methode : methodes) {
            %>
                <option value="<%= methode.getId() %>" <%= (methode.getId() == selectedMethodeId) ? "selected" : "" %>>
                    <%= methode.getStringValeur() %> (<%= methode.getRef() %>)
                </option>
            <%
                }
            }
            %>
        </select>
    </div>
    
    <div class="form-group">
        <label for="solutionId">Solution</label>
        <select name="solutionId" id="solutionId" required>
            <option value="">-- Choisir une solution --</option>
            <%
            List<Solution> solutions = (List<Solution>) request.getAttribute("solutions");
            if (solutions != null) {
                Parametre param = (Parametre) request.getAttribute("parametre");
                int selectedSolutionId = (param != null) ? param.getSolutionId() : -1;
                for (Solution solution : solutions) {
            %>
                <option value="<%= solution.getId() %>" <%= (solution.getId() == selectedSolutionId) ? "selected" : "" %>>
                    <%= solution.getStringValeur() %> (<%= solution.getRef() %>)
                </option>
            <%
                }
            }
            %>
        </select>
    </div>
    
    <div class="form-group">
        <label for="seuil">Seuil</label>
        <input type="number" id="seuil" name="seuil" step="0.01" value="${parametre.seuil}" required 
               placeholder="Entrez le seuil"/>
    </div>
    
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/parametres" class="btn btn-secondary">
            ❌ Annuler
        </a>
        <button type="submit" class="btn btn-primary">
            ${isNew ? '➕ Ajouter' : '💾 Enregistrer'}
        </button>
    </div>
</form>

<jsp:include page="includes/footer.jsp"/>
