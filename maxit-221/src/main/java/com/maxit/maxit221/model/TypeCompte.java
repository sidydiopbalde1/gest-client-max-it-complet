package com.maxit.maxit221.model;

public enum TypeCompte {
    PRINCIPAL("Compte Principal"),
    SOUS_COMPTE("Sous Compte");
    
    private final String libelle;
    
    TypeCompte(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
}

