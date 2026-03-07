<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.NoteFinale" %>
<%@ page import="com.example.model.Etudiant" %>
<%@ page import="com.example.model.Matiere" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Notes finales</title>
    <link rel="stylesheet" href="css/style.css" />
</head>
<body>
<div class="container">
<h1>Notes finales</h1>

<%!
    // Fonction pour trouver le nom d'un étudiant par son ID
    private String findEtudiantName(int etudiantId, List<Etudiant> etudiants) {
        if (etudiants == null) return String.valueOf(etudiantId);
        for (Etudiant e : etudiants) {
            if (e.getId() == etudiantId) {
                return e.getNom();
            }
        }
        return String.valueOf(etudiantId);
    }
    
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
%>

<%
    // Récupérer les listes depuis la requête
    List<NoteFinale> notes = (List<NoteFinale>) request.getAttribute("notes");
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
    List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
%>

<table border="1" cellpadding="6" cellspacing="0">
    <thead>
    <tr>
        <th>Étudiant</th>
        <th>Matière</th>
        <th>Note finale</th>
    </tr>
    </thead>
    <tbody>
<%
    if (notes != null) {
        for (NoteFinale note : notes) {
%>
        <tr>
            <td><%= findEtudiantName(note.getEtudiantId(), etudiants) %></td>
            <td><%= findMatiereName(note.getMatiereId(), matieres) %></td>
            <td><%= note.getValeur() %></td>
        </tr>
<%
        }
    }
%>
    </tbody>
</table>

<p><a href="index.jsp">Retour</a></p>
</div>
</body>
</html>
