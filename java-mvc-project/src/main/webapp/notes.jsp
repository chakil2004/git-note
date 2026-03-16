<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Note" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Notes");
%>
<jsp:include page="includes/header.jsp"/>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<div class="page-actions">
    <a href="${pageContext.request.contextPath}/notes/create" class="btn btn-primary">
        + Ajouter une note
    </a>
</div>

<table>
    <thead>
    <tr>
        <th>Étudiant</th>
        <th>Professeur</th>
        <th>Matière</th>
        <th>Valeur</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="note" items="${notes}">
        <tr>
            <td>${note.etudiantId}</td>
            <td>${note.profId}</td>
            <td>${note.matiereId}</td>
            <td>
                <span class="badge-value">${note.valeur}</span>
            </td>
            <td>
                <div class="table-actions">
                    <a href="${pageContext.request.contextPath}/notes/edit?etudiantId=${note.etudiantId}&profId=${note.profId}&matiereId=${note.matiereId}" 
                       class="btn btn-secondary">
                        ✏️ Modifier
                    </a>
                    <a href="${pageContext.request.contextPath}/notes/delete?etudiantId=${note.etudiantId}&profId=${note.profId}&matiereId=${note.matiereId}" 
                       class="btn btn-danger" 
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette note ?');">
                        🗑️ Supprimer
                    </a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty notes}">
    <div class="text-center mt-20">
        <p class="text-muted">Aucune note trouvée.</p>
        <a href="${pageContext.request.contextPath}/notes/create" class="btn btn-primary">
            + Ajouter la première note
        </a>
    </div>
</c:if>

<jsp:include page="includes/footer.jsp"/>
