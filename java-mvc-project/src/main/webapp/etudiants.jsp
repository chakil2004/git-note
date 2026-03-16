<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Etudiant" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Étudiants");
%>
<jsp:include page="includes/header.jsp"/>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<div class="page-actions">
    <a href="${pageContext.request.contextPath}/etudiants/create" class="btn btn-primary">
        + Ajouter un étudiant
    </a>
</div>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="etudiant" items="${etudiants}">
        <tr>
            <td>${etudiant.id}</td>
            <td>${etudiant.nom}</td>
            <td>
                <div class="table-actions">
                    <a href="${pageContext.request.contextPath}/etudiants/edit?id=${etudiant.id}" class="btn btn-secondary">
                        ✏️ Modifier
                    </a>
                    <a href="${pageContext.request.contextPath}/etudiants/delete?id=${etudiant.id}" 
                       class="btn btn-danger" 
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet étudiant ?');">
                        🗑️ Supprimer
                    </a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty etudiants}">
    <div class="text-center mt-20">
        <p class="text-muted">Aucun étudiant trouvé.</p>
        <a href="${pageContext.request.contextPath}/etudiants/create" class="btn btn-primary">
            + Ajouter le premier étudiant
        </a>
    </div>
</c:if>

<jsp:include page="includes/footer.jsp"/>
