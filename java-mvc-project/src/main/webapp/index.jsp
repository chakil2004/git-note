<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.NoteFinale" %>
<%@ page import="com.example.service.NoteService" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.config.DatabaseConfig" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Accueil");
    
    // Récupérer quelques statistiques
    int totalEtudiants = 0;
    int totalNotes = 0;
    int totalParametres = 0;
    
    try (Connection conn = DatabaseConfig.getConnection()) {
        // Compter les étudiants
        java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as total FROM Etudiant");
        java.sql.ResultSet rs = stmt.executeQuery();
        if (rs.next()) totalEtudiants = rs.getInt("total");
        
        // Compter les notes
        stmt = conn.prepareStatement("SELECT COUNT(*) as total FROM Note");
        rs = stmt.executeQuery();
        if (rs.next()) totalNotes = rs.getInt("total");
        
        // Compter les paramètres
        stmt = conn.prepareStatement("SELECT COUNT(*) as total FROM Parametre");
        rs = stmt.executeQuery();
        if (rs.next()) totalParametres = rs.getInt("total");
        
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<jsp:include page="includes/header.jsp"/>

<div class="dashboard">
    <h1>Bienvenue dans le Système de Délibération</h1>
    <p class="text-muted">Gérez les étudiants, les notes et les paramètres de délibération</p>
    
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon">👥</div>
            <div class="stat-number">${totalEtudiants}</div>
            <div class="stat-label">Étudiants</div>
        </div>
        
        <div class="stat-card">
            <div class="stat-icon">📝</div>
            <div class="stat-number">${totalNotes}</div>
            <div class="stat-label">Notes</div>
        </div>
        
        <div class="stat-card">
            <div class="stat-icon">⚙️</div>
            <div class="stat-number">${totalParametres}</div>
            <div class="stat-label">Paramètres</div>
        </div>
    </div>
    


<jsp:include page="includes/footer.jsp"/>
