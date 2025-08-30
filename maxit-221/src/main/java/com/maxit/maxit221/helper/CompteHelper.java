package com.maxit.maxit221.helper;

import com.maxit.maxit221.dto.CompteDtoResponse;
import com.maxit.maxit221.dto.CompteRequest;
import com.maxit.maxit221.exception.MaxItCompleteException;
import com.maxit.maxit221.mapper.CompteMapper;
import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.model.TypeCompte;
import com.maxit.maxit221.service.CompteService;
import com.maxit.maxit221.service.ICompte;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class CompteHelper {


    private final CompteService compteService;
    private final ICompte iCompte;
    private final CompteMapper compteMapper;

    public CompteHelper(CompteService compteService, ICompte iCompte, CompteMapper compteMapper) {
        this.compteService = compteService;
        this.iCompte = iCompte;
        this.compteMapper = compteMapper;
    }

//    public CompteDtoResponse creerComptePrincipal(@Valid CompteRequest compteRequest) {
//        if(iCompte.findByNumeroCompte(compteRequest.getNumeroCompte())!=null){
//            throw new MaxItCompleteException("Ce compte existe dèjà");
//        }
//
//    }

    public Compte creerSousCompte(Compte comptePrincipal) {
        Compte sousCompte = new Compte();
        sousCompte.setComptePrincipal(comptePrincipal);
        sousCompte.setTypeCompte(TypeCompte.SOUS_COMPTE);
        sousCompte.setNumeroCompte(genererNumeroCompte());
        return compteService.save(sousCompte);
    }

    public void bloquerCompte(Long compteId, String raison) {
        Compte compte = compteService.findById(compteId);
        if (compte == null) throw new RuntimeException("Compte introuvable");
        compte.setIsBlocked(true);
        compte.setBlockedReason(raison);
        compteService.save(compte);
    }

    public void bloquerComptePourPeriode(Long compteId, LocalDateTime finBlocage, String raison) {
        Compte compte = compteService.findById(compteId);
        if (compte == null) throw new RuntimeException("Compte introuvable");
        compte.setIsBlocked(true);
        compte.setBlockedUntil(finBlocage);
        compte.setBlockedReason(raison);
        compteService.save(compte);
    }

    public void debloquerCompte(Long compteId) {
        Compte compte = compteService.findById(compteId);
        if (compte == null) throw new RuntimeException("Compte introuvable");
        compte.setIsBlocked(false);
        compte.setBlockedUntil(null);
        compte.setBlockedReason(null);
        compteService.save(compte);
    }

    public Compte changerSousCompteEnPrincipal(Long sousCompteId) {
        Compte sousCompte = compteService.findById(sousCompteId);
        if (sousCompte == null) throw new RuntimeException("Compte non trouvé");

        if (!sousCompte.isSousCompte()) throw new RuntimeException("Le compte n'est pas un sous-compte");
        if (sousCompte.getIsBlocked()) throw new RuntimeException("Impossible de changer un compte bloqué");

        long nbPrincipal = compteService.countComptePrincipalByClientId(sousCompte.getClient().getId());
        if (nbPrincipal > 0) throw new RuntimeException("Le client a déjà un compte principal");

        sousCompte.setTypeCompte(TypeCompte.PRINCIPAL);
        sousCompte.setComptePrincipal(null);
        return compteService.save(sousCompte);
    }

    public void crediterCompte(Long compteId, BigDecimal montant) {
        Compte compte = compteService.findById(compteId);
        if (compte == null) throw new RuntimeException("Compte introuvable");

        compte.setSolde(compte.getSolde().add(montant));
        compteService.save(compte);
    }

    public void debiterCompte(Long compteId, BigDecimal montant) {
        Compte compte = compteService.findById(compteId);
        if (compte == null) throw new RuntimeException("Compte introuvable");

        BigDecimal nouveauSolde = compte.getSolde().subtract(montant);
        if (nouveauSolde.compareTo(BigDecimal.ZERO) < 0) throw new RuntimeException("Solde insuffisant");

        compte.setSolde(nouveauSolde);
        compteService.save(compte);
    }
    public String genererNumeroCompte() {
        String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "COMP" + timestamp + random;
    }

    public List<CompteDtoResponse> findAll() {
        return iCompte.findAll().stream().map(compteMapper::CompteEntityToCreateCompteResponse).toList();
    }


}
