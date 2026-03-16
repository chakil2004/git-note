<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Note" %>
<%@ page import="com.example.model.Etudiant" %>
<%@ page import="com.example.model.Prof" %>
<%@ page import="com.example.model.Matiere" %>
<%@ page import="java.util.List" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Notes");
%>
<jsp:include page="includes/header.jsp"/>

<c:set var="isNew" value="${empty note}" />
<h1>${isNew ? 'Ajouter une note' : 'Modifier note'}</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/${isNew ? 'notes/create' : 'notes/edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="etudiantId" value="${note.etudiantId}" />
        <input type="hidden" name="profId" value="${note.profId}" />
        <input type="hidden" name="matiereId" value="${note.matiereId}" />
    </c:if>
    
    <div class="form-group">
        <label for="etudiantId">Étudiant</label>
        <select name="etudiantId" id="etudiantId" required>
            <option value="">-- Choisir un étudiant --</option>
            <%
            List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
            if (etudiants != null) {
                Note note = (Note) request.getAttribute("note");
                int selectedEtudiantId = (note != null) ? note.getEtudiantId() : -1;
                for (Etudiant etudiant : etudiants) {
            %>
                <option value="<%= etudiant.getId() %>" <%= (etudiant.getId() == selectedEtudiantId) ? "selected" : "" %>>
                    <%= etudiant.getNom() %>
                </option>
            <%
                }
            }
            %>
        </select>
    </div>
    
    <div class="form-group">
        <label for="profId">Professeur</label>
        <select name="profId" id="profId" required>
            <option value="">-- Choisir un professeur --</option>
            <%
            List<Prof> profs = (List<Prof>) request.getAttribute("profs");
            if (profs != null) {
                Note note = (Note) request.getAttribute("note");
                int selectedProfId = (note != null) ? note.getProfId() : -1;
                for (Prof prof : profs) {
            %>
                <option value="<%= prof.getId() %>" <%= (prof.getId() == selectedProfId) ? "selected" : "" %>>
                    <%= prof.getNom() %>
                </option>
            <%
                }
            }
            %>
        </select>
    </div>
    
    <div class="form-group">
        <label for="matiereId">Matière</label>
        <select name="matiereId" id="matiereId" required>
            <option value="">-- Choisir une matière --</option>
            <%
            List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
            if (matieres != null) {
                Note note = (Note) request.getAttribute("note");
                int selectedMatiereId = (note != null) ? note.getMatiereId() : -1;
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
        <label for="valeur">Valeur de la note</label>
        <input type="number" id="valeur" name="valeur" step="0.01" value="${note.valeur}" required 
               placeholder="Entrez la valeur de la note (ex: 15.5)"/>
    </div>
    
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/notes" class="btn btn-secondary">
            ❌ Annuler
        </a>
        <button type="submit" class="btn btn-primary">
            ${isNew ? '+ Ajouter' : '💾 Enregistrer'}
        </button>
    </div>
</form>

<jsp:include page="includes/footer.jsp"/>
