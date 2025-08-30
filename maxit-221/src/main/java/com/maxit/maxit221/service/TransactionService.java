package com.maxit.maxit221.service;

import com.maxit.maxit221.dto.TransactionDto;
import com.maxit.maxit221.exception.MaxItNotFoundException;
import com.maxit.maxit221.model.*;
import com.maxit.maxit221.exception.MaxItCompleteException;
import com.maxit.maxit221.repository.CompteRepository;
import com.maxit.maxit221.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CompteRepository compteRepository;

    // 1. Liste des 10 dernières transactions (principal uniquement)
    public List<Transaction> getLast10Transactions(long compteId) {
        Pageable pageable = PageRequest.of(0, 10);
        return transactionRepository.findTop10ByCompteIdOrderByCreatedAtDesc(compteId,pageable);
    }

    // 2. Historique filtré par type
    public List<Transaction> getTransactionsByType(long compteId, TypeTransaction type) {
        return transactionRepository.findByCompteIdAndTypeTransactionOrderByDateTransactionDesc(compteId, type);
    }

    // 3. Solde d’un compte principal
    public BigDecimal getSoldePrincipal(long compteId) {
        Compte compte = getCompteOrThrow(compteId);
        return compte.getSolde();
    }

    // 4. Création d’un sous-compte
    public Compte creerSousCompte(Compte sousCompte) {
        sousCompte.setTypeCompte(TypeCompte.SOUS_COMPTE);
        return compteRepository.save(sousCompte);
    }

    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }


    public Transaction save(Transaction transaction){
        return transactionRepository.save(transaction);
    }
    public Transaction findByReference(String reference){
        return transactionRepository.findByReference(reference);
    }


    // 6. Transfert entre comptes
//    public Transaction transfert(Long idSource, Long idDest, BigDecimal montant, boolean fraisPriseEnCharge) {
//        Compte source = getCompteOrThrow(idSource);
//        Compte dest = getCompteOrThrow(idDest);
//
//        if (source.getSolde().compareTo(montant) < 0) {
//            throw new MaxItCompleteException("Solde insuffisant !");
//        }
//
//        BigDecimal frais = BigDecimal.ZERO;
//
//        if (dest.getTypeCompte() == TypeCompte.SOUS_COMPTE && source.getTypeCompte() == TypeCompte.PRINCIPAL) {
//            // Transfert interne gratuit
//            frais = BigDecimal.ZERO;
//        } else if (!source.getId().equals(dest.getId())) {
//            // Transfert externe payant
//            frais = montant.multiply(BigDecimal.valueOf(0.08));
//        }
//
//        // Application des frais
//        if (fraisPriseEnCharge) {
//            source.setSolde(source.getSolde().subtract(montant.add(frais)));
//            dest.setSolde(dest.getSolde().add(montant));
//        } else {
//            source.setSolde(source.getSolde().subtract(montant));
//            dest.setSolde(dest.getSolde().add(montant.subtract(frais)));
//        }
//
//        compteRepository.save(source);
//        compteRepository.save(dest);
//
//        Transaction transaction = new Transaction();
//        transaction.setCompteSource(source);
//        transaction.setCompteDestination(dest);
//        transaction.setMontant(montant);
//        transaction.setFrais(frais);
//        transaction.setTypeTransaction(TypeTransaction.TRANSFERT_INTERNE);
//        transaction.setDateExecution(LocalDateTime.now());
//
//        return transactionRepository.save(transaction);
//    }

    // 7. Retrait
//    public Transaction retrait(Long idCompte, BigDecimal montant) {
//        Compte compte = getCompteOrThrow(idCompte);
//
//        if (compte.getSolde().compareTo(montant) < 0) {
//            throw new MaxItCompleteException("Solde insuffisant pour retrait !");
//        }
//
//        compte.setSolde(compte.getSolde().subtract(montant));
//        compteRepository.save(compte);
//
//        Transaction transaction = new Transaction();
//        transaction.setCompteSource(compte);
//        transaction.setMontant(montant);
//        transaction.setTypeTransaction(TypeTransaction.RETRAIT);
//        transaction.setDateTransaction(LocalDateTime.now());
//
//        return transactionRepository.save(transaction);
//    }
//
//    // 8. Dépôt
//    public Transaction depot(Long idCompte, BigDecimal montant) {
//        Compte compte = getCompteOrThrow(idCompte);
//
//        compte.setSolde(compte.getSolde().add(montant));
//        compteRepository.save(compte);
//
//        Transaction transaction = new Transaction();
//        transaction.setCompteDestination(compte);
//        transaction.setMontant(montant);
//        transaction.setTypeTransaction(TypeTransaction.DEPOT);
//        transaction.setDateTransaction(LocalDateTime.now());
//
//        return transactionRepository.save(transaction);
//    }
//
//    // 9. Demande d'annulation de transfert
//    public Transaction demandeAnnulation(Long transactionId) {
//        Transaction t = transactionRepository.findById(transactionId)
//                .orElseThrow(() -> new MaxItNotFoundException("Transaction introuvable"));
//
//        t.setDemandeAnnulation(true);
//        return transactionRepository.save(t);
//    }
//
//    // 10. Transfert planifié
//    public Transaction transfertPlanifie(Long idSource, Long idDest, BigDecimal montant, LocalDateTime dateExecution) {
//        Compte source = getCompteOrThrow(idSource);
//        Compte dest = getCompteOrThrow(idDest);
//
//        Transaction transaction = new Transaction();
//        transaction.setCompteSource(source);
//        transaction.setCompteDestination(dest);
//        transaction.setMontant(montant);
//        transaction.setTypeTransaction(TypeTransaction.DEPOT);
//        transaction.setDatePlanifiee(dateExecution);
//        transaction.setStatut(StatutTransaction.PLANIFIEE);
//
//        return transactionRepository.save(transaction);
//    }
//
//    // 11. Achat de code Woyofal
//    public Transaction demandeAchatWoyofal(Long idCompte, BigDecimal montant) {
//        Compte compte = getCompteOrThrow(idCompte);
//
//        if (compte.getSolde().compareTo(montant) < 0) {
//            throw new MaxItCompleteException("Solde insuffisant pour achat Woyofal !");
//        }
//
//        Transaction transaction = new Transaction();
//        transaction.setCompteSource(compte);
//        transaction.setMontant(montant);
//        transaction.setTypeTransaction(TypeTransaction.ACHAT_WOYOFAL);
//        transaction.setStatut(StatutTransaction.EN_ATTENTE);
//
//        return transactionRepository.save(transaction);
//    }
//
//    // 12. Récupération achat Woyofal (après traitement externe)
//    public Transaction recupererAchatWoyofal(Long transactionId, String codeRecharge) {
//        Transaction transaction = transactionRepository.findById(transactionId)
//                .orElseThrow(() -> new MaxItNotFoundException("Achat introuvable"));
//
//        transaction.setStatut("VALIDE");
//        transaction.setCodeRecharge(codeRecharge);
//
//        return transactionRepository.save(transaction);
//    }
//
//
//    // Helpers
//    private Compte getCompteOrThrow(Long id) {
//        return compteRepository.findById(id)
//                .orElseThrow(() -> new MaxItNotFoundException("Compte introuvable"));
//    }
//
//    private Transaction getTransactionOrThrow(Long id) {
//        return transactionRepository.findById(id)
//                .orElseThrow(() -> new MaxItNotFoundException("Transaction introuvable"));
//    }
}
