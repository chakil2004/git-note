<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Définir le titre de la page
    request.setAttribute("pageTitle", "Accueil");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - Système de Gestion de Forage</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h1>PROJET2 - Système de Gestion de Forage</h1>
            <p>Gestion complète du processus de forage</p>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/" class="active">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="dashboard">
                <h2>Bienvenue dans le système de gestion de forage</h2>
                <p>Cette application permet de gérer le processus complet de forage, de la demande client jusqu'à la finalisation des travaux.</p>
                
                <div class="actions">
                    <h3>Navigation rapide</h3>
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/client" class="btn btn-primary">
                            👥 Clients
                        </a>
                        <a href="${pageContext.request.contextPath}/demande" class="btn btn-success">
                            📝 Demandes de Forage
                        </a>
                        <a href="${pageContext.request.contextPath}/devis" class="btn btn-info">
                            📋 Devis
                        </a>
                        <a href="${pageContext.request.contextPath}/travaux" class="btn btn-warning">
                            🔧 Travaux
                        </a>
                        <a href="${pageContext.request.contextPath}/typeDevis" class="btn btn-secondary">
                            🏷️ Types Devis
                        </a>
                        <a href="${pageContext.request.contextPath}/statut" class="btn btn-dark">
                            📊 Statuts
                        </a>
                        <a href="${pageContext.request.contextPath}/statutTravaux" class="btn btn-outline">
                            ✅ Statuts Travaux
                        </a>
                    </div>
                </div>
                
                <div class="process-info">
                    <h3>Processus de Forage</h3>
                    <div class="process-steps">
                        <div class="step">
                            <div class="step-number">1</div>
                            <div class="step-content">
                                <h4>Demande Client</h4>
                                <p>Le client fait une demande de forage</p>
                            </div>
                        </div>
                        <div class="step">
                            <div class="step-number">2</div>
                            <div class="step-content">
                                <h4>Étude & Devis</h4>
                                <p>L'entreprise réalise une étude et émet un devis</p>
                            </div>
                        </div>
                        <div class="step">
                            <div class="step-number">3</div>
                            <div class="step-content">
                                <h4>Validation</h4>
                                <p>Le client accepte ou refuse le devis</p>
                            </div>
                        </div>
                        <div class="step">
                            <div class="step-number">4</div>
                            <div class="step-content">
                                <h4>Travaux</h4>
                                <p>Réalisation des travaux de forage</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        
        <footer class="footer">
            <p>&copy; ETU003653</p>
        </footer>
    </div>
    
    <style>
        .action-buttons {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
            margin-top: 1rem;
        }
        
        .btn {
            display: inline-block;
            padding: 1rem 1.5rem;
            text-decoration: none;
            border-radius: 8px;
            text-align: center;
            font-weight: 600;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
        
        .btn-primary {
            background: #007bff;
            color: white;
        }
        
        .btn-success {
            background: #28a745;
            color: white;
        }
        
        .btn-info {
            background: #17a2b8;
            color: white;
        }
        
        .btn-warning {
            background: #ffc107;
            color: #212529;
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        
        .btn-dark {
            background: #343a40;
            color: white;
        }
        
        .btn-outline {
            background: transparent;
            color: #007bff;
            border: 2px solid #007bff;
        }
        
        .btn-outline:hover {
            background: #007bff;
            color: white;
        }
        
        .process-info {
            margin-top: 3rem;
            padding: 2rem;
            background: #f8f9fa;
            border-radius: 8px;
        }
        
        .process-steps {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
            margin-top: 1rem;
        }
        
        .step {
            display: flex;
            align-items: flex-start;
            gap: 1rem;
            padding: 1rem;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        
        .step-number {
            width: 40px;
            height: 40px;
            background: #007bff;
            color: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            flex-shrink: 0;
        }
        
        .step-content h4 {
            margin: 0 0 0.5rem 0;
            color: #333;
        }
        
        .step-content p {
            margin: 0;
            color: #666;
            font-size: 0.9rem;
        }
        
        .actions h3 {
            margin-bottom: 1rem;
            color: #333;
        }
    </style>
</body>
</html>
