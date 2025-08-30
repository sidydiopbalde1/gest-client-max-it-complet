package com.maxit.maxit221.service;

import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.model.TypeCompte;
import com.maxit.maxit221.repository.ClientRepository;
import com.maxit.maxit221.repository.CompteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompteService implements ICompte {

    private final CompteRepository compteRepository;

    public CompteService(CompteRepository compteRepository, ClientRepository clientRepository) {
        this.compteRepository = compteRepository;
    }

    public List<Compte> findAll() {
        return compteRepository.findAll();
    }

    public Compte findById(long id) {
        return compteRepository.findById(id).orElse(null);
    }

    public Compte findByNumeroCompte(String numeroCompte) {
        return compteRepository.findByNumeroCompte(numeroCompte);
    }

    public Compte findActiveAndUnblockedByNumeroCompte(String numeroCompte) {
        return compteRepository.findActiveAndUnblockedByNumeroCompte(numeroCompte);
    }


    public List<Compte> findByClientId(long clientId) {
        return compteRepository.findByClientIdAndIsActiveTrue(clientId);
    }


    @Override
    public Compte findComptePrincipalByClientId(long clientId) {
        return compteRepository.findComptePrincipalByClientId(clientId);
    }

    @Override
    public List<Compte> findSousComptesByComptePrincipalId(long comptePrincipalId) {
        return compteRepository.findSousComptesByComptePrincipalId(comptePrincipalId);
    }

    @Override
    public List<Compte> findUnblockedSousComptesByClientId(long clientId) {
        return compteRepository.findUnblockedSousComptesByClientId(clientId);
    }

    public List<Compte> findByClientId(Long clientId) {
        return compteRepository.findByClientIdAndIsActiveTrue(clientId);
    }

    public Compte findComptePrincipalByClientId(Long clientId) {
        return compteRepository.findComptePrincipalByClientId(clientId);
    }

    public List<Compte> findSousComptesByComptePrincipalId(Long comptePrincipalId) {
        return compteRepository.findSousComptesByComptePrincipalId(comptePrincipalId);
    }

    public List<Compte> findUnblockedSousComptesByClientId(Long clientId) {
        return compteRepository.findUnblockedSousComptesByClientId(clientId);
    }

    public Compte save(Compte compte) {
        return compteRepository.save(compte);
    }

    @Override
    public String genererNumeroCompte() {
        return "";
    }

    @Override
    public Compte creerComptePrincipal(Compte compte_principal) {
        return null;
    }

    @Override
    public Compte creerSousCompte(Compte comptePrincipal) {
        return null;
    }

    @Override
    public void bloquerCompte(long compteId, String raison) {

    }

    @Override
    public void bloquerComptePourPeriode(long compteId, LocalDateTime finBlocage, String raison) {

    }

    @Override
    public void debloquerCompte(long compteId) {

    }

    public void bloquerCompte(Long compteId, String raison) {

    }

    public void delete(Long id) {
        compteRepository.deleteById(id);
    }

    public long countComptePrincipalByClientId(long id) {
        return compteRepository.countComptePrincipalByClientId(id);
    }

    public void bloquerComptePourPeriode(Long id, LocalDateTime finBlocage, String raison) {
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Compte introuvable"));
        compte.setIsBlocked(true);
        compte.setBlockedUntil(finBlocage);
        compte.setBlockedReason(raison);
        compteRepository.save(compte);
    }

    public void debloquerCompte(Long id) {
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Compte introuvable"));
        compte.setIsBlocked(false);
        compte.setBlockedUntil(null);
        compte.setBlockedReason(null);
        compteRepository.save(compte);
    }

    public Compte changerSousCompteEnPrincipal(Long id) {
        Compte sousCompte = compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Compte introuvable"));

        if (sousCompte.isPrincipal()) {
            throw new RuntimeException("Ce compte est déjà principal");
        }
        // on désactive l'ancien compte principal
        Compte ancienPrincipal = compteRepository.findComptePrincipalByClientId(sousCompte.getClient().getId());
        if (ancienPrincipal != null) {
            ancienPrincipal.setTypeCompte(TypeCompte.PRINCIPAL);
            compteRepository.save(ancienPrincipal);
        }
        // on rend ce sous-compte principal
        sousCompte.setTypeCompte(TypeCompte.PRINCIPAL);
        return compteRepository.save(sousCompte);
    }

    @Override
    public void crediterCompte(long id, BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro");
        }
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Compte introuvable"));
        compte.setSolde(compte.getSolde().add(montant));
        compteRepository.save(compte);
    }

    public void debiterCompte(long id, BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être supérieur à zéro");
        }
        Compte compte = compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Compte introuvable"));
        if (compte.getSolde().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant");
        }
        compte.setSolde(compte.getSolde().subtract(montant));
        compteRepository.save(compte);
    }

    @Override
    public void delete(long id) {
        compteRepository.deleteById(id);
    }

}
