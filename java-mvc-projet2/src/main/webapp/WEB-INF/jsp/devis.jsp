<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Demande" %>
<%@ page import="com.example.model.TypeDevis" %>
<%
    List<Demande> demandesList = (List<Demande>) request.getAttribute("demandes");
    List<TypeDevis> typesList = (List<TypeDevis>) request.getAttribute("types");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PROJET2 - Devis</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <div class="header-content">
                <div>
                    <h1>Gestion des Devis</h1>
                    <p>Rechercher une demande par ID</p>
                </div>
                <div class="header-actions">
                    <a href="${pageContext.request.contextPath}/devis/list" class="btn btn-primary">Voir liste des devis</a>
                </div>
            </div>
        </header>
        
        <nav class="nav">
            <a href="${pageContext.request.contextPath}/">Accueil</a>
            <a href="${pageContext.request.contextPath}/client">Clients</a>
            <a href="${pageContext.request.contextPath}/demande">Demandes</a>
            <a href="${pageContext.request.contextPath}/devis" class="active">Devis</a>
            <a href="${pageContext.request.contextPath}/travaux">Travaux</a>
            <a href="${pageContext.request.contextPath}/typeDevis">Types Devis</a>
            <a href="${pageContext.request.contextPath}/statut">Statuts</a>
            <a href="${pageContext.request.contextPath}/statutTravaux">Statuts Travaux</a>
        </nav>
        
        <main class="main">
            <div class="search-container">
                <h2>Rechercher une demande</h2>
                <div class="form-group">
                    <label for="demandeId">ID de la demande:</label>
                    <input type="number" id="demandeId" name="demandeId" class="form-control" placeholder="Entrez l'ID de la demande">
                </div>
            </div>

            <div id="demandeDetails" class="demande-details" style="display: none;">
                <h2>Détails de la demande</h2>
                <div id="demandeContent"></div>
            </div>
            
            
            
            <div class="type-container">
                <h2>Type de devis</h2>
                <div class="form-group">
                    <label for="typeDevisId">Type de devis:</label>
                    <select id="typeDevisId" name="typeDevisId" class="form-control">
                        <option value="">Sélectionner un type...</option>
                        <% if (typesList != null) { 
                            for (TypeDevis type : typesList) { %>
                                <option value="<%= type.getId() %>"><%= type.getLibelle() %></option>
                            <% }
                        } %>
                    </select>
                </div>
            </div>

            <div class="details-container">
                <h2>Détails du devis</h2>
                <div class="form-group">
                    <button type="button" onclick="ajouterLigneDetail()" class="btn btn-primary">Ajouter une ligne</button>
                </div>
                
                <div class="table-wrapper">
                    <table id="detailsTable" class="table">
                        <thead>
                            <tr>
                                <th>Description</th>
                                <th>Quantité</th>
                                <th>Prix unitaire</th>
                                <th>Total</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody id="detailsBody">
                            <!-- Les lignes seront ajoutées dynamiquement -->
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" class="text-right"><strong>Total général:</strong></td>
                                <td id="totalGeneral">0.00</td>
                                <td></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                
                <div class="form-group">
                    <button type="button" onclick="validerDevis()" class="btn btn-success">Valider et insérer</button>
                    <button type="button" onclick="testerRoute()" class="btn btn-primary">Tester route</button>
                </div>
            </div>
            

        </main>
        
        <footer class="footer">
            <p>&copy; ETU003653</p>
        </footer>
    </div>
  
    <!-- stock les donne des id recuperer dans m imput -->
    <div id="demandeData" style="display: none;">
    <% if (demandesList != null && !demandesList.isEmpty()) { 
        for (Demande demande : demandesList) { %>
            <div class="demande-item" 
                 data-id="<%= demande.getId() %>" 
                 data-description="<%= demande.getDescription() != null ? demande.getDescription().replace("\"", "&quot;") : "" %>" 
                 data-client="<%= demande.getClientId() %>" 
                 data-date="<%= demande.getDateDemande() != null ? demande.getDateDemande() : "" %>">
            </div>
        <% }
    } %>
</div>

<script>
    // Variables globales pour les détails du devis
    var detailsDevis = [];
    var ligneCounter = 0;
    
    // Extraction des données depuis les attributs data lit le HTML et le convertit en données JS utilisables
    var demandes = [];
    var demandeElements = document.querySelectorAll('.demande-item');
    
    for (var i = 0; i < demandeElements.length; i++) {
        var element = demandeElements[i];
        demandes.push({
            id: parseInt(element.getAttribute('data-id')),
            description: element.getAttribute('data-description'),
            clientId: parseInt(element.getAttribute('data-client')),
            dateDemande: element.getAttribute('data-date')
        });
    }
    
    console.log('Demandes chargées:', demandes);
    
    // Fonction pour ajouter une ligne de détail
    function ajouterLigneDetail() {
        ligneCounter++;
        var ligneId = 'ligne_' + ligneCounter;
        
        var ligne = {
            id: ligneId,
            description: '',
            quantite: 1,
            prixUnitaire: 0,
            total: 0
        };
        
        detailsDevis.push(ligne);
        
        var tbody = document.getElementById('detailsBody');
        var nouvelleLigne = tbody.insertRow();
        nouvelleLigne.id = ligneId;
        
        nouvelleLigne.innerHTML = 
            '<td><input type="text" class="form-control description-input" data-ligne="' + ligneId + '" placeholder="Description" onchange="mettreAJourLigne(\'' + ligneId + '\')"></td>' +
            '<td><input type="number" class="form-control quantite-input" data-ligne="' + ligneId + '" value="1" min="1" onchange="mettreAJourLigne(\'' + ligneId + '\')"></td>' +
            '<td><input type="number" class="form-control prix-input" data-ligne="' + ligneId + '" value="0" min="0" step="0.01" onchange="mettreAJourLigne(\'' + ligneId + '\')"></td>' +
            '<td class="total-cell">0.00</td>' +
            '<td><button type="button" class="btn btn-sm btn-danger" onclick="supprimerLigne(\'' + ligneId + '\')">Supprimer</button></td>';
    }
    
    // Fonction pour mettre à jour une ligne
    function mettreAJourLigne(ligneId) {
        var ligne = detailsDevis.find(function(d) { return d.id === ligneId; });
        if (!ligne) return;
        
        var description = document.querySelector('.description-input[data-ligne="' + ligneId + '"]').value;
        var quantite = parseFloat(document.querySelector('.quantite-input[data-ligne="' + ligneId + '"]').value) || 0;
        var prixUnitaire = parseFloat(document.querySelector('.prix-input[data-ligne="' + ligneId + '"]').value) || 0;
        
        ligne.description = description;
        ligne.quantite = quantite;
        ligne.prixUnitaire = prixUnitaire;
        ligne.total = quantite * prixUnitaire;
        
        // Mettre à jour l'affichage du total de la ligne
        var totalCell = document.querySelector('#' + ligneId + ' .total-cell');
        totalCell.textContent = ligne.total.toFixed(2);
        
        // Mettre à jour le total général
        mettreAJourTotalGeneral();
    }
    
    // Fonction pour supprimer une ligne
    function supprimerLigne(ligneId) {
        // Supprimer du tableau
        detailsDevis = detailsDevis.filter(function(d) { return d.id !== ligneId; });
        
        // Supprimer du DOM
        var ligneElement = document.getElementById(ligneId);
        if (ligneElement) {
            ligneElement.remove();
        }
        
        // Mettre à jour le total général
        mettreAJourTotalGeneral();
    }
    
    // Fonction pour mettre à jour le total général
    function mettreAJourTotalGeneral() {
        var totalGeneral = detailsDevis.reduce(function(total, ligne) {
            return total + ligne.total;
        }, 0);
        
        document.getElementById('totalGeneral').textContent = totalGeneral.toFixed(2);
    }
    
    // Fonction pour tester la route
    function testerRoute() {
        console.log('Test de la route...');
        fetch('${pageContext.request.contextPath}/devis/test', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
        .then(function(response) {
            console.log('Response status:', response.status);
            return response.text();
        })
        .then(function(data) {
            console.log('Response data:', data);
            alert('Test route: ' + data);
        })
        .catch(function(error) {
            console.error('Erreur test:', error);
            alert('Erreur test: ' + error.message);
        });
    }
    
    // Fonction pour valider et insérer le devis
    function validerDevis() {
        var demandeId = document.getElementById('demandeId').value.trim();
        var typeDevisId = document.getElementById('typeDevisId').value;
        
        // Validation des champs obligatoires
        if (!demandeId) {
            alert('Veuillez sélectionner une demande (entrer un ID)');
            return;
        }
        
        if (!typeDevisId) {
            alert('Veuillez sélectionner un type de devis');
            return;
        }
        
        if (detailsDevis.length === 0) {
            alert('Veuillez ajouter au moins une ligne de détail');
            return;
        }
        
        // Vérifier que toutes les lignes ont une description
        var lignesInvalides = detailsDevis.filter(function(ligne) {
            return !ligne.description.trim();
        });
        
        if (lignesInvalides.length > 0) {
            alert('Toutes les lignes doivent avoir une description');
            return;
        }
        
        // Préparer les données pour l'envoi
        var devisData = {
            demandeId: parseInt(demandeId),
            typeDevisId: parseInt(typeDevisId),
            details: detailsDevis.map(function(ligne) {
                return {
                    description: ligne.description,
                    quantite: ligne.quantite,
                    prixUnitaire: ligne.prixUnitaire,
                    total: ligne.total
                };
            })
        };
        
        console.log('Données du devis à insérer:', devisData);
        
        // Envoyer les données au serveur via AJAX
        fetch('${pageContext.request.contextPath}/devis/createWithDetails', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify(devisData)
        })
        .then(function(response) {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Erreur lors de l\'insertion');
            }
        })
        .then(function(data) {
            console.log('Succès:', data);
            alert('Devis et détails insérés avec succès!');
            
            // Rediriger vers la liste des devis après succès
            window.location.href = '${pageContext.request.contextPath}/devis/list';
        })
        .catch(function(error) {
            console.error('Erreur:', error);
            alert('Erreur lors de l\'insertion du devis: ' + error.message);
        });
    }
    
    document.getElementById('demandeId').addEventListener('blur', function() {
        var demandeId = this.value.trim();
        console.log('ID recherché:', demandeId);
        
        if (demandeId === '') {
            document.getElementById('demandeDetails').style.display = 'none';
            return;
        }
        
        var demande = demandes.find(function(d) { return d.id == demandeId; });
        console.log('Demande trouvée:', demande);
        
        if (demande) {
            var content = '<div class="detail-item"><strong>ID:</strong> ' + demande.id + '</div>' +
                          '<div class="detail-item"><strong>Description:</strong> ' + demande.description + '</div>' +
                          '<div class="detail-item"><strong>ID Client:</strong> ' + demande.clientId + '</div>' +
                          '<div class="detail-item"><strong>Date de demande:</strong> ' + demande.dateDemande + '</div>';
            
            document.getElementById('demandeContent').innerHTML = content;
            document.getElementById('demandeDetails').style.display = 'block';
        } else {
            document.getElementById('demandeContent').innerHTML = '<div class="alert alert-error">Demande non trouvée (ID: ' + demandeId + ')</div>';
            document.getElementById('demandeDetails').style.display = 'block';
        }
    });
</script>
    
    <style>
        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            width: 100%;
        }
        
        .header-actions {
            display: flex;
            gap: 1rem;
        }
        
        .search-container, .type-container, .details-container {
            margin-bottom: 2rem;
            padding: 2rem;
            background: #f8f9fa;
            border-radius: 8px;
        }
        
        .search-container h2, .type-container h2, .details-container h2 {
            margin-bottom: 1rem;
            color: #333;
        }
        
        .form-group {
            margin-bottom: 1rem;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #333;
        }
        
        .form-control {
            width: 100%;
            max-width: 300px;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }
        
        .form-control:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 2px rgba(0,123,255,0.25);
        }
        
        .demande-details {
            margin-top: 2rem;
            padding: 2rem;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .demande-details h2 {
            margin-bottom: 1.5rem;
            color: #333;
        }
        
        .detail-item {
            margin-bottom: 1rem;
            padding: 0.75rem;
            background: #f8f9fa;
            border-radius: 4px;
        }
        
        .detail-item strong {
            color: #007bff;
            margin-right: 0.5rem;
        }
        
        .alert {
            padding: 1rem;
            border-radius: 4px;
            margin: 1rem 0;
        }
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .table-wrapper {
            overflow-x: auto;
            margin-top: 1rem;
        }
        
        .table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .table th,
        .table td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        .table th {
            background: #f8f9fa;
            font-weight: 600;
            color: #333;
        }
        
        .table tbody tr:hover {
            background: #f8f9fa;
        }
        
        .table input.form-control {
            width: 100%;
            max-width: none;
            padding: 0.5rem;
            margin: 0;
        }
        
        .total-cell {
            font-weight: bold;
            color: #007bff;
        }
        
        .text-right {
            text-align: right;
        }
        
        .btn {
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            font-size: 1rem;
            margin-right: 0.5rem;
        }
        
        .btn-primary {
            background: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background: #0056b3;
        }
        
        .btn-success {
            background: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background: #1e7e34;
        }
        
        .btn-danger {
            background: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background: #c82333;
        }
        
        .btn-sm {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }
        
        #totalGeneral {
            font-weight: bold;
            font-size: 1.2rem;
            color: #007bff;
        }
    </style>
</body>
</html>
