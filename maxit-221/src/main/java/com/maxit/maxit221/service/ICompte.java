package com.maxit.maxit221.service;

import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.model.TypeCompte;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public interface ICompte {
    public List<Compte> findAll();

    public Compte findById(long id);

    public Compte findByNumeroCompte(String numeroCompte);

    public Compte findActiveAndUnblockedByNumeroCompte(String numeroCompte) ;


    public Compte findComptePrincipalByClientId(long clientId) ;

    public List<Compte> findSousComptesByComptePrincipalId(long comptePrincipalId) ;

    public List<Compte> findUnblockedSousComptesByClientId(long clientId) ;

    public Compte save(Compte compte) ;

    public String genererNumeroCompte();

    public Compte creerComptePrincipal(Compte compte_principal) ;

    public Compte creerSousCompte(Compte comptePrincipal) ;

    public void bloquerCompte(long compteId, String raison) ;

    public void bloquerComptePourPeriode(long compteId, LocalDateTime finBlocage, String raison) ;

    public void debloquerCompte(long compteId) ;

    public Compte changerSousCompteEnPrincipal(Long sousCompteId);

    public void crediterCompte(long compteId, BigDecimal montant);

    public void debiterCompte(long compteId, BigDecimal montant);

    public void delete(long id);
}
