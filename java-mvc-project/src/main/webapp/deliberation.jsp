<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Etudiant" %>
<%@ page import="com.example.model.Matiere" %>
<%@ page import="com.example.model.NoteFinale" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Délibération");
%>
<jsp:include page="includes/header.jsp"/>

<%
    // Récupérer les listes depuis la requête
    List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
    List<Matiere> matieres = (List<Matiere>) request.getAttribute("matieres");
    List<NoteFinale> notesFinales = (List<NoteFinale>) request.getAttribute("notesFinales");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
%>

<% if (message != null) { %>
    <div class="alert alert-success"><%= message %></div>
<% } %>

<% if (error != null) { %>
    <div class="alert alert-error"><%= error %></div>
<% } %>

<div class="deliberation-form">
    <h2>🎯 Lancer une délibération</h2>
    <p class="text-muted">Sélectionnez un étudiant et une matière pour calculer la note finale</p>
    
    <form method="post" action="${pageContext.request.contextPath}/deliberation">
        <div class="form-group">
            <label for="etudiantId">Étudiant</label>
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
            <label for="matiereId">Matière</label>
            <select name="matiereId" id="matiereId" required>
                <option value="">-- Sélectionner une matière --</option>
                <% if (matieres != null) {
                    for (Matiere m : matieres) { %>
                        <option value="<%= m.getId() %>"><%= m.getNom() %></option>
                <%  }
                } %>
            </select>
        </div>
        
        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                🚀 Lancer la délibération
            </button>
        </div>
    </form>
</div>

<div class="deliberation-results">
    <h2>📊 Notes finales existantes</h2>
    
    <% if (notesFinales != null && !notesFinales.isEmpty()) { %>
        <table>
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
                    <td>
                        <span class="badge-final-note"><%= note.getValeur() %></span>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    <% } else { %>
        <div class="text-center mt-20">
            <p class="text-muted">Aucune note finale n'a encore été calculée.</p>
            <p class="text-muted">Lancez votre première délibération pour voir les résultats ici.</p>
        </div>
    <% } %>
</div>

<jsp:include page="includes/footer.jsp"/>
