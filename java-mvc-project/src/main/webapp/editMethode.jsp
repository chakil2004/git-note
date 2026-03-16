<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.example.model.Methode" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Méthodes");
%>
<jsp:include page="includes/header.jsp"/>

<c:set var="isNew" value="${empty methode}" />
<h1>${isNew ? 'Ajouter une méthode' : 'Modifier méthode'}</h1>

<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>

<form action="${pageContext.request.contextPath}/methodes/${isNew ? 'create' : 'edit'}" method="post">
    <c:if test="${not isNew}">
        <input type="hidden" name="id" value="${methode.id}" />
    </c:if>
    
    <div class="form-group">
        <label for="stringValeur">Valeur de la méthode</label>
        <input type="text" id="stringValeur" name="stringValeur" value="${methode.stringValeur}" required 
               placeholder="Entrez la valeur de la méthode"/>
    </div>
    
    <div class="form-group">
        <label for="ref">Référence</label>
        <input type="text" id="ref" name="ref" value="${methode.ref}" required 
               placeholder="Entrez la référence"/>
    </div>
    
    <div class="form-actions">
        <a href="${pageContext.request.contextPath}/methodes" class="btn btn-secondary">
            ❌ Annuler
        </a>
        <button type="submit" class="btn btn-primary">
            ${isNew ? '+ Ajouter' : '💾 Enregistrer'}
        </button>
    </div>
</form>

<jsp:include page="includes/footer.jsp"/>
