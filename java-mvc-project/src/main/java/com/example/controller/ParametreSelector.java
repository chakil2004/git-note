package com.example.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service pour trouver le meilleur paramètre de délibération
 * basé sur la plus petite différence entre l'écart calculé et les seuils
 */
public class ParametreSelector {

    private static final String READ_PARAMETRES_WITH_DETAILS_SQL =
            "SELECT p.id, p.seuil, m.stringValeur AS methode, s.stringValeur AS solution, " +
            "m.ref AS methode_ref, s.ref AS solution_ref " +
            "FROM Parametre p " +
            "JOIN Methode m ON p.methode_id = m.id " +
            "JOIN Solution s ON p.solution_id = s.id " +
            "WHERE p.matiere_id = ?";

    /**
     * Trouve le meilleur paramètre pour la délibération
     * en comparant l'écart calculé avec les seuils disponibles
     *
     * @param conn connexion JDBC
     * @param matiereId ID de la matière
     * @param ecartCalcule écart total calculé des notes
     * @return le meilleur paramètre ou null si aucun trouvé
     * @throws SQLException
     */
    public static ParametreDetail trouverMeilleurParametre(Connection conn, int matiereId, BigDecimal ecartCalcule) throws SQLException {
        List<ParametreDetail> parametres = lireParametres(conn, matiereId);
        
        if (parametres.isEmpty()) {
            System.out.println("Aucun paramètre trouvé pour la matière " + matiereId);
            return null;
        }

        System.out.println("=== ANALYSE DES PARAMÈTRES ===");
        System.out.println("Écart calculé: " + ecartCalcule);
        System.out.println("Paramètres disponibles:");
        
        ParametreDetail meilleurParametre = null;
        BigDecimal plusPetiteDifference = null;

        for (ParametreDetail param : parametres) {
            BigDecimal difference = ecartCalcule.subtract(param.getSeuil()).abs();
            
            System.out.println("  ID: " + param.getId() + 
                             ", Seuil: " + param.getSeuil() + 
                             ", Méthode: " + param.getMethode() + 
                             ", Solution: " + param.getSolution() + 
                             ", Différence: " + difference);

            // Premier paramètre trouvé
            if (meilleurParametre == null) {
                meilleurParametre = param;
                plusPetiteDifference = difference;
            } else {
                // Comparaison pour trouver la plus petite différence
                int comparaison = difference.compareTo(plusPetiteDifference);
                
                if (comparaison < 0) {
                    // Différence plus petite trouvée
                    meilleurParametre = param;
                    plusPetiteDifference = difference;
                } else if (comparaison == 0) {
                    // Égalité des différences : choisir le seuil le plus petit
                    if (param.getSeuil().compareTo(meilleurParametre.getSeuil()) < 0) {
                        meilleurParametre = param;
                        System.out.println("    -> Égalité des différences, seuil plus petit choisi");
                    }
                }
            }
        }

        System.out.println("Meilleur paramètre sélectionné: ID " + meilleurParametre.getId() + 
                         " (différence: " + plusPetiteDifference + ")");
        System.out.println("=============================");

        return meilleurParametre;
    }

    /**
     * Lit tous les paramètres pour une matière donnée
     */
    private static List<ParametreDetail> lireParametres(Connection conn, int matiereId) throws SQLException {
        List<ParametreDetail> parametres = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(READ_PARAMETRES_WITH_DETAILS_SQL)) {
            stmt.setInt(1, matiereId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ParametreDetail param = new ParametreDetail(
                        rs.getInt("id"),
                        rs.getBigDecimal("seuil"),
                        rs.getString("methode"),
                        rs.getString("solution"),
                        rs.getString("methode_ref"),
                        rs.getString("solution_ref")
                    );
                    parametres.add(param);
                }
            }
        }
        
        return parametres;
    }

    /**
     * Classe interne pour stocker les détails d'un paramètre
     */
    public static class ParametreDetail {
        private final int id;
        private final BigDecimal seuil;
        private final String methode;
        private final String solution;
        private final String methodeRef;
        private final String solutionRef;

        public ParametreDetail(int id, BigDecimal seuil, String methode, String solution, 
                             String methodeRef, String solutionRef) {
            this.id = id;
            this.seuil = seuil;
            this.methode = methode;
            this.solution = solution;
            this.methodeRef = methodeRef;
            this.solutionRef = solutionRef;
        }

        // Getters
        public int getId() { return id; }
        public BigDecimal getSeuil() { return seuil; }
        public String getMethode() { return methode; }
        public String getSolution() { return solution; }
        public String getMethodeRef() { return methodeRef; }
        public String getSolutionRef() { return solutionRef; }
    }
}
