package com.maxit.maxit221.model;

public enum StatutDemandeAnnulation {
    EN_ATTENTE("En Attente"),
    APPROUVEE("Approuvée"),
    REJETEE("Rejetée"),
    TRAITEE("Traitée");
    
    private final String libelle;
    
    StatutDemandeAnnulation(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public boolean estFinale() {
        return this == TRAITEE || this == REJETEE;
    }
}

