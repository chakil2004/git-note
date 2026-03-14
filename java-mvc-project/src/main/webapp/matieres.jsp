<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Matiere" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Matières");
%>
<jsp:include page="includes/header.jsp"/>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<div class="page-actions">
    <a href="${pageContext.request.contextPath}/matieres/create" class="btn btn-primary">
        + Ajouter une matière
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
    <c:forEach var="matiere" items="${matieres}">
        <tr>
            <td>${matiere.id}</td>
            <td>${matiere.nom}</td>
            <td>
                <div class="table-actions">
                    <a href="${pageContext.request.contextPath}/matieres/edit?id=${matiere.id}" class="btn btn-secondary">
                        ✏️ Modifier
                    </a>
                    <a href="${pageContext.request.contextPath}/matieres/delete?id=${matiere.id}" 
                       class="btn btn-danger" 
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette matière ?');">
                        🗑️ Supprimer
                    </a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty matieres}">
    <div class="text-center mt-20">
        <p class="text-muted">Aucune matière trouvée.</p>
        <a href="${pageContext.request.contextPath}/matieres/create" class="btn btn-primary">
            + Ajouter la première matière
        </a>
    </div>
</c:if>

<jsp:include page="includes/footer.jsp"/>
