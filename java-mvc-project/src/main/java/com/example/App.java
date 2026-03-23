// package com.example;

// import com.example.config.DatabaseConfig;
// import com.example.service.NoteService;

// import java.math.BigDecimal;
// import java.sql.Connection;

// /**
//  * Temporary main class to test database connectivity and the deliberation logic.
//  */
// public class App {

//     public static void main(String[] args) {
//         try (Connection conn = DatabaseConfig.getConnection()) {
//             System.out.println("Connected to database.");

//             NoteService noteService = new NoteService();
//             int etudiantId = 1;
//             int matiereId = 1;

//             BigDecimal noteFinale = noteService.getNoteFinale(conn, etudiantId, matiereId);
//             if (noteFinale != null) {
//                 System.out.println("Note finale pour l'étudiant " + etudiantId + " / matière " + matiereId + " : " + noteFinale);
//             } else {
//                 System.out.println("Aucune note finale trouvée pour l'étudiant " + etudiantId + " / matière " + matiereId);
//             }

//         } catch (Exception e) {
//             System.err.println("Erreur pendant le test : " + e.getMessage());
//             e.printStackTrace();
//         }
//     }
// }
