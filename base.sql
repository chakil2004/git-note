CREATE DATABASE deliberation_notes;
USE deliberation_notes;

CREATE TABLE Etudiant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE Prof (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE Matiere (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE Solution (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stringValeur VARCHAR(20) NOT NULL,
    ref VARCHAR(20) NOT NULL
);

CREATE TABLE Methode (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stringValeur VARCHAR(5) NOT NULL,
    ref VARCHAR(20) NOT NULL
);

CREATE TABLE Note (
    etudiant_id INT NOT NULL,
    prof_id INT NOT NULL,
    matiere_id INT NOT NULL,
    valeur DECIMAL(5,2) NOT NULL,
    PRIMARY KEY (etudiant_id, prof_id, matiere_id),
    FOREIGN KEY (etudiant_id) REFERENCES Etudiant(id),
    FOREIGN KEY (prof_id) REFERENCES Prof(id),
    FOREIGN KEY (matiere_id) REFERENCES Matiere(id)
);

CREATE TABLE Parametre (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matiere_id INT NOT NULL,
    methode_id INT NOT NULL,
    solution_id INT NOT NULL,
    seuil DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (matiere_id) REFERENCES Matiere(id),
    FOREIGN KEY (methode_id) REFERENCES Methode(id),
    FOREIGN KEY (solution_id) REFERENCES Solution(id)
);

CREATE TABLE NoteFinale (
    etudiant_id INT NOT NULL,
    matiere_id INT NOT NULL,
    valeur DECIMAL(5,2) NOT NULL,
    PRIMARY KEY (etudiant_id, matiere_id),
    FOREIGN KEY (etudiant_id) REFERENCES Etudiant(id),
    FOREIGN KEY (matiere_id) REFERENCES Matiere(id)
);

DELETE FROM NoteFinale;
TRUNCATE TABLE NoteFinale;

INSERT INTO Solution (stringValeur, ref) VALUES
('Min', 'Minimal'),
('Max', 'Maximum'),
('Moyenne', 'Average');

INSERT INTO Methode (stringValeur, ref) VALUES
('<', 'Inferieur'),
('>', 'Superieur');

INSERT INTO Matiere (nom) VALUES
('Mathematiques'),
('Physique'),
('Informatique');

INSERT INTO Etudiant (nom) VALUES
('Alice'),
('Bob'),
('Charlie');

INSERT INTO Prof (nom) VALUES
('Dr. Smith'),
('Dr. Johnson'),
('Dr. Williams');

INSERT INTO Note (etudiant_id, prof_id, matiere_id, valeur) VALUES
(1, 1, 1, 15.5),
(1, 2, 1, 14.0),
(2, 1, 1, 12.0),
(2, 2, 1, 13.5),
(3, 1, 1, 10.0),
(3, 2, 1, 11.0);    

INSERT INTO Parametre (matiere_id, methode_id, solution_id, seuil) VALUES
(1, 1, 1, 12.0), -- Mathematiques, Inferieur, Minimal
(1, 2, 2, 14.0), -- Mathematiques, Superieur, Maximum
(2, 1, 3, 10.0), -- Physique, Inferieur, Average
(2, 2, 1, 11.0), -- Physique, Superieur, Minimal
(3, 1, 2, 13.0), -- Informatique, Inferieur, Maximum
(3, 2, 3, 14.0); -- Informatique, Superieur, Average

INSERT INTO Note (etudiant_id, prof_id, matiere_id, valeur) VALUES
(1,1,3,10),
(1,2,3,11),
(1,3,3,11.75);