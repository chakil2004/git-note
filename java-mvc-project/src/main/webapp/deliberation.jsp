<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Etudiant" %>
<%@ page import="com.example.model.Matiere" %>
<%@ page import="com.example.model.NoteFinale" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Délibération</title>
    <link rel="stylesheet" href="css/style.css" />
    <style>
        .form-section {
            background: #f5f5f5;
            padding: 20px;
            margin: 20px 0;
            border-radius: 5px;
        }
        .form-group {
            margin: 10px 0;
        }
        label {
            display: inline-block;
            width: 100px;
            font-weight: bold;
        }
        select, button {
            padding: 8px;
            margin: 5px;
        }
        button {
            background: #007cba;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background: #005a87;
        }
        .message {
            padding: 10px;
            margin: 10px 0;
            border-radius: 3px;
        }
        .success {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
        }
        .error {
            background: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
        }
        .results-section {
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="container">
<h1>Délibération des Notes (ETU003605)</h1>

<%
    // Récupérer les listes depuis la requête
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
    List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
    List<NoteFinale> notesFinales = (List<NoteFinale>) request.getAttribute("notesFinales");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
%>

<% if (message != null) { %>
    <div class="message success"><%= message %></div>
<% } %>

<% if (error != null) { %>
    <div class="message error"><%= error %></div>
<% } %>

<div class="form-section">
    <h2>Lancer une délibération</h2>
    <form method="post" action="deliberation">
        <div class="form-group">
            <label for="etudiantId">Étudiant:</label>
            <select name="etudiantId" id="etudiantId" required>
                <option value="">-- Sélectionner un étudiant --</option>
                <% if (etudiants != null) {
                    for (Etudiant e : etudiants) { %>
                        <option value="<%= e.getId() %>"><%= e.getNom() %></option>
                <%  }
                } %>
            </select>
        </div>
        
        <div class="form-group">
            <label for="matiereId">Matière:</label>
            <select name="matiereId" id="matiereId" required>
                <option value="">-- Sélectionner une matière --</option>
                <% if (matieres != null) {
                    for (Matiere m : matieres) { %>
                        <option value="<%= m.getId() %>"><%= m.getNom() %></option>
                <%  }
                } %>
            </select>
        </div>
        
        <div class="form-group">
            <button type="submit">Lancer la délibération</button>
        </div>
    </form>
</div>

<div class="results-section">
    <h2>Notes finales existantes</h2>
    
    <% if (notesFinales != null && !notesFinales.isEmpty()) { %>
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
                // Fonctions pour trouver les noms
                for (NoteFinale note : notesFinales) {
                    String etudiantNom = "ID: " + note.getEtudiantId();
                    String matiereNom = "ID: " + note.getMatiereId();
                    
                    if (etudiants != null) {
                        for (Etudiant e : etudiants) {
                            if (e.getId() == note.getEtudiantId()) {
                                etudiantNom = e.getNom();
                                break;
                            }
                        }
                    }
                    
                    if (matieres != null) {
                        for (Matiere m : matieres) {
                            if (m.getId() == note.getMatiereId()) {
                                matiereNom = m.getNom();
                                break;
                            }
                        }
                    }
            %>
                <tr>
                    <td><%= etudiantNom %></td>
                    <td><%= matiereNom %></td>
                    <td><%= note.getValeur() %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    <% } else { %>
        <p>Aucune note finale n'a encore été calculée.</p>
    <% } %>
</div>

<p><a href="index.jsp">Retour à l'accueil</a></p>
</div>
</body>
</html>
