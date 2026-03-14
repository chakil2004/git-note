<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Prof" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Professeurs");
%>
<jsp:include page="includes/header.jsp"/>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<div class="page-actions">
    <a href="${pageContext.request.contextPath}/profs/create" class="btn btn-primary">
        + Ajouter un professeur
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
    <c:forEach var="prof" items="${profs}">
        <tr>
            <td>${prof.id}</td>
            <td>${prof.nom}</td>
            <td>
                <div class="table-actions">
                    <a href="${pageContext.request.contextPath}/profs/edit?id=${prof.id}" class="btn btn-secondary">
                        ✏️ Modifier
                    </a>
                    <a href="${pageContext.request.contextPath}/profs/delete?id=${prof.id}" 
                       class="btn btn-danger" 
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce professeur ?');">
                        🗑️ Supprimer
                    </a>
                </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty profs}">
    <div class="text-center mt-20">
        <p class="text-muted">Aucun professeur trouvé.</p>
        <a href="${pageContext.request.contextPath}/profs/create" class="btn btn-primary">
            + Ajouter le premier professeur
        </a>
    </div>
</c:if>

<jsp:include page="includes/footer.jsp"/>
