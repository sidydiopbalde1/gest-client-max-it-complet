package com.maxit.maxit221.model;

public enum StatutTransaction {
    EN_ATTENTE("En Attente"),
    VALIDEE("Validée"),
    EXECUTEE("Exécutée"),
    ECHOUEE("Échouée"),
    ANNULEE("Annulée"),
    PLANIFIEE("Planifiée"),
    EN_COURS_ANNULATION("En Cours d'Annulation");
    
    private final String libelle;
    
    StatutTransaction(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public boolean peutEtreAnnulee() {
        return this == VALIDEE || this == PLANIFIEE;
    }
    
    public boolean estFinale() {
        return this == EXECUTEE || this == ECHOUEE || this == ANNULEE;
    }
}

