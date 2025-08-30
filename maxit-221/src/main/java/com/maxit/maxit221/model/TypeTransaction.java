package com.maxit.maxit221.model;

public enum TypeTransaction {
    DEPOT("Dépôt"),
    RETRAIT("Retrait"),
    TRANSFERT_INTERNE("Transfert Interne"),
    TRANSFERT_EXTERNE("Transfert Externe"),
    PAIEMENT("Paiement"),
    ACHAT_WOYOFAL("Achat Woyofal");
    
    private final String libelle;
    
    TypeTransaction(String libelle) {
        this.libelle = libelle;
    }
    
    public String getLibelle() {
        return libelle;
    }
    
    public boolean isTransfert() {
        return this == TRANSFERT_INTERNE || this == TRANSFERT_EXTERNE;
    }
}

