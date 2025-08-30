package com.maxit.maxit221.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class GesClientService {
    
    private final WebClient webClient;
    
    @Value("${gesclient.base-url}")
    private String baseUrl;
    
    public GesClientService() {
        this.webClient = WebClient.builder().build();
    }
    
    /**
     * Recherche un numéro de téléphone dans GesClient
     * @param telephone Le numéro de téléphone à rechercher
     * @return Les informations du client si trouvé, null sinon
     */
    public GesClientResponse rechercherNumero(String telephone) {
        try {
            Mono<GesClientResponse> response = webClient.get()
                    .uri(baseUrl + "/clients/search?telephone=" + telephone)
                    .retrieve()
                    .bodyToMono(GesClientResponse.class);
            
            return response.block(); // En production, utiliser des méthodes asynchrones
        } catch (Exception e) {
            // En cas d'erreur, simuler une réponse pour les tests
            return simulerRechercheNumero(telephone);
        }
    }
    
    /**
     * Méthode de simulation pour les tests (à remplacer par l'intégration réelle)
     */
    private GesClientResponse simulerRechercheNumero(String telephone) {
        // Simuler quelques numéros existants pour les tests
        Map<String, GesClientResponse> numerosSimules = new HashMap<>();
        
        numerosSimules.put("+221771234567", new GesClientResponse(
                true, 
                "Amadou Diallo", 
                "https://example.com/photos/amadou.jpg",
                true
        ));
        
        numerosSimules.put("+221776543210", new GesClientResponse(
                true, 
                "Fatou Sall", 
                "https://example.com/photos/fatou.jpg",
                true
        ));
        
        numerosSimules.put("+221779876543", new GesClientResponse(
                true, 
                "Moussa Ba", 
                "https://example.com/photos/moussa.jpg",
                false
        ));
        
        return numerosSimules.getOrDefault(telephone, new GesClientResponse(
                false, 
                null, 
                null,
                false
        ));
    }
    
    /**
     * Classe pour encapsuler la réponse de GesClient
     */
    public static class GesClientResponse {
        private boolean existe;
        private String nomComplet;
        private String photoUrl;
        private boolean actif;
        
        public GesClientResponse() {}
        
        public GesClientResponse(boolean existe, String nomComplet, String photoUrl, boolean actif) {
            this.existe = existe;
            this.nomComplet = nomComplet;
            this.photoUrl = photoUrl;
            this.actif = actif;
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
        
        public String getPhotoUrl() {
            return photoUrl;
        }
        
        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
        
        public boolean isActif() {
            return actif;
        }
        
        public void setActif(boolean actif) {
            this.actif = actif;
        }
    }
}

