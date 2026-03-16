<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ETU003653 - Système de Délibération</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/modern-style.css">
</head>
<body>
    <header class="header">
        <div class="header-content">
            <div class="logo">
                <span class="logo-dot"></span>
                <span>ETU003653</span>
            </div>
            <nav class="nav">
                <a href="${pageContext.request.contextPath}/" class="${pageTitle == 'Accueil' ? 'active' : ''}">Accueil</a>
                <a href="${pageContext.request.contextPath}/etudiants" class="${pageTitle == 'Étudiants' ? 'active' : ''}">Étudiants</a>
                <a href="${pageContext.request.contextPath}/profs" class="${pageTitle == 'Professeurs' ? 'active' : ''}">Professeurs</a>
                <a href="${pageContext.request.contextPath}/matieres" class="${pageTitle == 'Matières' ? 'active' : ''}">Matières</a>
                <a href="${pageContext.request.contextPath}/notes" class="${pageTitle == 'Notes' ? 'active' : ''}">Notes</a>
                <a href="${pageContext.request.contextPath}/parametres" class="${pageTitle == 'Paramètres' ? 'active' : ''}">Paramètres</a>
                <a href="${pageContext.request.contextPath}/deliberation" class="${pageTitle == 'Délibération' ? 'active' : ''}">Délibération</a>
            </nav>
        </div>
    </header>

    <div class="container">
        <div class="main-content">
