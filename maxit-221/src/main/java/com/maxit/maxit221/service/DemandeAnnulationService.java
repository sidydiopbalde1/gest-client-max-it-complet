package com.maxit.maxit221.service;

import com.maxit.maxit221.model.*;
import com.maxit.maxit221.repository.DemandeAnnulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class DemandeAnnulationService {
    
    @Autowired
    private DemandeAnnulationRepository demandeAnnulationRepository;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private CompteService compteService;
    
//    public List<DemandeAnnulation> findAll() {
//        return demandeAnnulationRepository.findAll();
//    }
//
//    public DemandeAnnulation findById(Long id) {
//        return demandeAnnulationRepository.findById(id).orElse(null);
//    }
//
//    public DemandeAnnulation findByReference(String reference) {
//        return demandeAnnulationRepository.findByReference(reference);
//    }
//
//    public DemandeAnnulation findByTransactionId(Long transactionId) {
//        return demandeAnnulationRepository.findByTransactionId(transactionId);
//    }
//
//    public List<DemandeAnnulation> findDemandesEnAttente() {
//        return demandeAnnulationRepository.findDemandesEnAttente();
//    }
//
//    public DemandeAnnulation findByDemandeurId(Long clientId) {
//        return demandeAnnulationRepository.findByDemandeurId(clientId);
//    }
//
//    public long countDemandesEnAttente() {
//        return demandeAnnulationRepository.countDemandesEnAttente();
//    }
//
//    public DemandeAnnulation save(DemandeAnnulation demandeAnnulation) {
//        return demandeAnnulationRepository.save(demandeAnnulation);
//    }
//
//    public String genererReference() {
//        String reference;
//        do {
//            Random random = new Random();
//            reference = "ANN" + System.currentTimeMillis() + String.format("%03d", random.nextInt(1000));
//        } while (demandeAnnulationRepository.existsByReference(reference));
//
//        return reference;
//    }
//
//    /**
//     * Créer une demande d'annulation de transfert
//     */
//    public DemandeAnnulation creerDemandeAnnulation(Long transactionId, Long demandeurId, String motif) {
//        // Vérifier que la transaction existe
//        Transaction transactionOpt = transactionService.findById(transactionId);
//        if (transactionOpt == null) {
//            throw new RuntimeException("Transaction non trouvée");
//        }
//
//        Transaction transaction = transactionOpt;
//
//        // Vérifier que la transaction peut être annulée
//        if (!transaction.getStatut().peutEtreAnnulee()) {
//            throw new RuntimeException("Cette transaction ne peut pas être annulée");
//        }
//
//        // Vérifier qu'il n'y a pas déjà une demande d'annulation
//        if (demandeAnnulationRepository.existsByTransactionId(transactionId)) {
//            throw new RuntimeException("Une demande d'annulation existe déjà pour cette transaction");
//        }
//
//        // Vérifier que le demandeur est le propriétaire du compte source
////        if (!transaction.getCompteSource().getClient().getId().equals(demandeurId)) {
////            throw new RuntimeException("Seul le propriétaire du compte source peut demander l'annulation");
////        }
////
//        String reference = genererReference();
//        DemandeAnnulation demande = new DemandeAnnulation(reference, transaction,
//                                                         transaction.getCompteSource().getClient(), motif);
//
//        // Marquer la transaction comme en cours d'annulation
//        transaction.setStatut(StatutTransaction.EN_COURS_ANNULATION);
//        transactionService.save(transaction);
//
//        return save(demande);
//    }
//
//    /**
//     * Approuver une demande d'annulation
//     */
//    public DemandeAnnulation approuverDemande(Long demandeId, Long approbateurId) {
//        Optional<DemandeAnnulation> demandeOpt = findById(demandeId);
//        if (demandeOpt.isEmpty()) {
//            throw new RuntimeException("Demande d'annulation non trouvée");
//        }
//
//        DemandeAnnulation demande = demandeOpt.get();
//        if (!demande.peutEtreTraitee()) {
//            throw new RuntimeException("Cette demande a déjà été traitée");
//        }
//
//        Transaction transaction = demande.getTransaction();
//
//        // Vérifier si l'annulation est possible
//        if (!peutAnnulerTransaction(transaction)) {
//            demande.setStatut(StatutDemandeAnnulation.REJETEE);
//            demande.setMotifRejet("L'argent n'est plus disponible sur le compte du récepteur");
//            demande.setDateTraitement(LocalDateTime.now());
//
//            // Remettre la transaction en état normal
//            transaction.setStatut(StatutTransaction.EXECUTEE);
//            transactionService.save(transaction);
//
//            return save(demande);
//        }
//
//        // Effectuer l'annulation
//        effectuerAnnulation(transaction);
//
//        demande.setStatut(StatutDemandeAnnulation.TRAITEE);
//        demande.setDateTraitement(LocalDateTime.now());
//
//        return save(demande);
//    }
//
//    /**
//     * Rejeter une demande d'annulation
//     */
//    public DemandeAnnulation rejeterDemande(Long demandeId, String motifRejet, Long rejeteurId) {
//        Optional<DemandeAnnulation> demandeOpt = findById(demandeId);
//        if (demandeOpt.isEmpty()) {
//            throw new RuntimeException("Demande d'annulation non trouvée");
//        }
//
//        DemandeAnnulation demande = demandeOpt.get();
//        if (!demande.peutEtreTraitee()) {
//            throw new RuntimeException("Cette demande a déjà été traitée");
//        }
//
//        demande.setStatut(StatutDemandeAnnulation.REJETEE);
//        demande.setMotifRejet(motifRejet);
//        demande.setDateTraitement(LocalDateTime.now());
//
//        // Remettre la transaction en état normal
//        Transaction transaction = demande.getTransaction();
//        transaction.setStatut(StatutTransaction.EXECUTEE);
//        transactionService.save(transaction);
//
//        return save(demande);
//    }
//
//    /**
//     * Vérifier si une transaction peut être annulée
//     */
//    private boolean peutAnnulerTransaction(Transaction transaction) {
//        // Pour les transferts internes, vérifier que le compte destination a suffisamment de fonds
//        if (transaction.getTypeTransaction() == TypeTransaction.TRANSFERT_INTERNE) {
//            Compte compteDestination = transaction.getCompteDestination();
//            return compteDestination.getSolde().compareTo(transaction.getMontant()) >= 0;
//        }
//
//        // Pour les transferts externes, l'annulation dépend de la politique de l'institution
//        if (transaction.getTypeTransaction() == TypeTransaction.TRANSFERT_EXTERNE) {
//            // Ici, on pourrait vérifier avec un service externe
//            // Pour la simulation, on considère que c'est possible si la transaction a moins de 24h
//            return transaction.getDateExecution().isAfter(LocalDateTime.now().minusHours(24));
//        }
//
//        return false;
//    }
//
//    /**
//     * Effectuer l'annulation d'une transaction
//     */
//    private void effectuerAnnulation(Transaction transaction) {
//        switch (transaction.getTypeTransaction()) {
//            case TRANSFERT_INTERNE:
//                annulerTransfertInterne(transaction);
//                break;
//            case TRANSFERT_EXTERNE:
//                annulerTransfertExterne(transaction);
//                break;
//            default:
//                throw new RuntimeException("Type de transaction non supporté pour l'annulation");
//        }
//
//        transaction.setStatut(StatutTransaction.ANNULEE);
//        transactionService.save(transaction);
//    }
//
//    /**
//     * Annuler un transfert interne
//     */
//    private void annulerTransfertInterne(Transaction transaction) {
//        Compte compteSource = transaction.getCompteSource();
//        Compte compteDestination = transaction.getCompteDestination();
//
//        // Débiter le compte destination
//        compteService.debiterCompte(compteDestination.getId(), transaction.getMontant());
//
//        // Créditer le compte source (montant - frais si les frais n'étaient pas pris en charge)
//        BigDecimal montantACrediter = transaction.getMontant();
//        if (!transaction.getFraisPrisEnCharge() && transaction.getFrais().compareTo(BigDecimal.ZERO) > 0) {
//            montantACrediter = transaction.getMontant(); // On ne rembourse que le montant, pas les frais
//        }
//
//        compteService.crediterCompte(compteSource.getId(), montantACrediter);
//    }
//
//    /**
//     * Annuler un transfert externe
//     */
//    private void annulerTransfertExterne(Transaction transaction) {
//        Compte compteSource = transaction.getCompteSource();
//
//        // Créditer le compte source (montant - frais si les frais n'étaient pas pris en charge)
//        BigDecimal montantACrediter = transaction.getMontant();
//        if (!transaction.getFraisPrisEnCharge() && transaction.getFrais().compareTo(BigDecimal.ZERO) > 0) {
//            montantACrediter = transaction.getMontant(); // On ne rembourse que le montant, pas les frais
//        }
//
//        compteService.crediterCompte(compteSource.getId(), montantACrediter);
//    }
//
//    public void delete(Long id) {
//        demandeAnnulationRepository.deleteById(id);
//    }
}

