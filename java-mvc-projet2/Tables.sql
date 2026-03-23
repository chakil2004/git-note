CREATE DATABASE IF NOT EXISTS forage;
USE forage;

CREATE TABLE client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    contact VARCHAR(150) NOT NULL
);

CREATE TABLE demande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT NOT NULL,
    date_demande DATETIME NOT NULL,
    description TEXT NOT NULL,
    lieu VARCHAR(255) NOT NULL,
    CONSTRAINT fk_demande_client
        FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE type_devis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE statut (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE devis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    demande_id INT NOT NULL,
    type_devis_id INT NOT NULL,
    date_devis DATETIME NOT NULL,
    statut_id INT NOT NULL,
    CONSTRAINT fk_devis_demande
        FOREIGN KEY (demande_id) REFERENCES demande(id),
    CONSTRAINT fk_devis_type
        FOREIGN KEY (type_devis_id) REFERENCES type_devis(id),
    CONSTRAINT fk_devis_statut
        FOREIGN KEY (statut_id) REFERENCES statut(id)
);

CREATE TABLE detail_devis (
    id INT AUTO_INCREMENT PRIMARY KEY,
    devis_id INT NOT NULL,
    libelle VARCHAR(255) NOT NULL,
    montant DECIMAL(12,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_detail_devis_devis
        FOREIGN KEY (devis_id) REFERENCES devis(id)
);

CREATE TABLE statut_travaux (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE travaux (
    id INT AUTO_INCREMENT PRIMARY KEY,
    demande_id INT NOT NULL,
    statut_travaux_id INT NOT NULL,
    -- date_debut DATETIME,
    -- date_fin DATETIME,
    CONSTRAINT fk_travaux_demande
        FOREIGN KEY (demande_id) REFERENCES demande(id),
    CONSTRAINT fk_travaux_statut
        FOREIGN KEY (statut_travaux_id) REFERENCES statut_travaux(id)
);

INSERT INTO type_devis (libelle) VALUES
('Etude'),
('Forage');

INSERT INTO statut (libelle) VALUES
('En attente'),
('Accepté'),
('Refusé');

INSERT INTO statut_travaux (libelle) VALUES
('Début'),
('Récupération eau'),
('Test sanitaire'),
('Terminé');