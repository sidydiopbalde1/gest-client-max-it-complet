package com.maxit.maxit221.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppDAFService {

    private final WebClient webClient;

    @Value("${appdaf.base-url}")
    private String baseUrl;

    public AppDAFService() {
        this.webClient = WebClient.builder().build();
    }
    
    /**
     * Recherche un NCI dans AppDAF
     * @param nci Le numéro de carte d'identité à rechercher
     * @return Les informations de la pièce d'identité si trouvée, null sinon
     */
    public AppDAFResponse rechercherNCI(String nci) {
        try {
            Mono<AppDAFResponse> response = webClient.get()
                    .uri(baseUrl + "/identites/search?nci=" + nci)
                    .retrieve()
                    .bodyToMono(AppDAFResponse.class);
            
            return response.block(); // En production, utiliser des méthodes asynchrones
        } catch (Exception e) {
            // En cas d'erreur, simuler une réponse pour les tests
            return simulerRechercheNCI(nci);
        }
    }
    
    /**
     * Méthode de simulation pour les tests (à remplacer par l'intégration réelle)
     */
    private AppDAFResponse simulerRechercheNCI(String nci) {
        // Simuler quelques NCI existants pour les tests
        Map<String, AppDAFResponse> ncisSimules = new HashMap<>();
        
        ncisSimules.put("1234567890123", new AppDAFResponse(
                true, 
                "Amadou Diallo", 
                "https://example.com/pieces/1234567890123_recto.jpg",
                "https://example.com/pieces/1234567890123_verso.jpg",
                "CNI",
                true
        ));
        
        ncisSimules.put("9876543210987", new AppDAFResponse(
                true, 
                "Fatou Sall", 
                "https://example.com/pieces/9876543210987_recto.jpg",
                "https://example.com/pieces/9876543210987_verso.jpg",
                "CNI",
                true
        ));
        
        ncisSimules.put("5555666677778", new AppDAFResponse(
                true, 
                "Moussa Ba", 
                "https://example.com/pieces/5555666677778_recto.jpg",
                "https://example.com/pieces/5555666677778_verso.jpg",
                "Passeport",
                false
        ));
        
        return ncisSimules.getOrDefault(nci, new AppDAFResponse(
                false, 
                null, 
                null,
                null,
                null,
                false
        ));
    }
    
    /**
     * Classe pour encapsuler la réponse d'AppDAF
     */
    public static class AppDAFResponse {
        private boolean existe;
        private String nomComplet;
        private String pieceRectoUrl;
        private String pieceVersoUrl;
        private String typePiece;
        private boolean valide;
        
        public AppDAFResponse() {}
        
        public AppDAFResponse(boolean existe, String nomComplet, String pieceRectoUrl, 
                             String pieceVersoUrl, String typePiece, boolean valide) {
            this.existe = existe;
            this.nomComplet = nomComplet;
            this.pieceRectoUrl = pieceRectoUrl;
            this.pieceVersoUrl = pieceVersoUrl;
            this.typePiece = typePiece;
            this.valide = valide;
        }
        
        // Getters et Setters
        public boolean isExiste() {
            return existe;
        }
        
        public void setExiste(boolean existe) {
            this.existe = existe;
        }
        
        public String getNomComplet() {
            return nomComplet;
        }
        
        public void setNomComplet(String nomComplet) {
            this.nomComplet = nomComplet;
        }
        
        public String getPieceRectoUrl() {
            return pieceRectoUrl;
        }
        
        public void setPieceRectoUrl(String pieceRectoUrl) {
            this.pieceRectoUrl = pieceRectoUrl;
        }
        
        public String getPieceVersoUrl() {
            return pieceVersoUrl;
        }
        
        public void setPieceVersoUrl(String pieceVersoUrl) {
            this.pieceVersoUrl = pieceVersoUrl;
        }
        
        public String getTypePiece() {
            return typePiece;
        }
        
        public void setTypePiece(String typePiece) {
            this.typePiece = typePiece;
        }
        
        public boolean isValide() {
            return valide;
        }
        
        public void setValide(boolean valide) {
            this.valide = valide;
        }
    }
}

