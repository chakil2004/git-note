# PROJET2 - Système de Gestion des Appels Clients

## Description
Application Java MVC pour la gestion des appels clients et leurs choix.

## Structure du projet

```
java-mvc-projet2/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── config/          # Configuration
│   │   │           ├── controller/      # Contrôleurs
│   │   │           ├── model/           # Modèles
│   │   │           ├── service/         # Services métier
│   │   │           └── servlet/         # Servlets
│   │   ├── resources/
│   │   │   └── db.properties           # Configuration BDD
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml            # Configuration web
│   │       ├── css/
│   │       │   └── style.css          # Styles
│   │       ├── appels.jsp             # Page des appels
│   │       └── index.jsp              # Page d'accueil
├── pom.xml                            # Configuration Maven
└── README.md                          # Documentation
```

## Base de données

### Tables requises

```sql
CREATE TABLE AppelClient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT NOT NULL,
    date_appel DATETIME NOT NULL
);

CREATE TABLE detailAppel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appel_id INT NOT NULL,
    lieu VARCHAR(255) NOT NULL,
    distance FLOAT NOT NULL
);

CREATE TABLE choixType (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(255) NOT NULL
);

CREATE TABLE choixClient (
    id INT PRIMARY KEY AUTO_INCREMENT,
    detail_appel INT NOT NULL,
    choix BOOLEAN,
    choixType INT NOT NULL,
    date_choixClient DATETIME NOT NULL,
    FOREIGN KEY (detail_appel) REFERENCES detailAppel(id),
    FOREIGN KEY (choixType) REFERENCES choixType(id)
);

CREATE TABLE statutTravailFini (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nom_travail VARCHAR(250)
);
```

## Configuration

1. **Base de données** : Modifier `src/main/resources/db.properties`
2. **Port du serveur** : Configuré sur 8080 dans `pom.xml`

## Lancement du projet

```bash
# Compiler le projet
mvn clean compile

# Lancer le serveur Jetty
mvn jetty:run

# Accéder à l'application
http://localhost:8080
```

## Fonctionnalités

- ✅ Page d'accueil avec navigation
- ✅ Liste des appels clients
- ✅ Ajout de nouveaux appels
- ✅ Architecture MVC propre
- ✅ Design responsive moderne

## Technologies utilisées

- **Java 11**
- **Jakarta Servlet 5.0**
- **JSP/JSTL**
- **MySQL 8.0**
- **Maven**
- **Jetty Server**
- **CSS3/HTML5**

## Architecture

- **Model** : Classes Java représentant les entités
- **View** : Pages JSP avec CSS moderne
- **Controller** : Servlets gérant les requêtes HTTP
- **Service** : Logique métier et accès aux données
- **Config** : Configuration de la base de données
