package com.maxit.maxit221.repository;

import com.maxit.maxit221.model.DemandeAnnulation;
import com.maxit.maxit221.model.StatutDemandeAnnulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeAnnulationRepository extends JpaRepository<DemandeAnnulation, Long> {
    
//    DemandeAnnulation findByReference(String reference);
//
//    boolean existsByReference(String reference);
//
//  DemandeAnnulation findByTransactionId(Long transactionId);
//
//    boolean existsByTransactionId(Long transactionId);
//
//    List<DemandeAnnulation> findByStatut(StatutDemandeAnnulation statut);
//
//    @Query("SELECT da FROM DemandeAnnulation da WHERE da.statut = 'EN_ATTENTE' ORDER BY da.createdAt ASC")
//    List<DemandeAnnulation> findDemandesEnAttente();
//
//    @Query("SELECT da FROM DemandeAnnulation da WHERE da.demandeur.id = :clientId ORDER BY da.createdAt DESC")
//    DemandeAnnulation findByDemandeurId(@Param("clientId") Long clientId);
//
//    @Query("SELECT da FROM DemandeAnnulation da WHERE da.demandeur.id = :clientId AND da.statut = :statut " +
//           "ORDER BY da.createdAt DESC")
//    DemandeAnnulation findByDemandeurIdAndStatut(@Param("clientId") Long clientId,
//                                                       @Param("statut") StatutDemandeAnnulation statut,
//                                                       Pageable pageable);
//
//    @Query("SELECT COUNT(da) FROM DemandeAnnulation da WHERE da.statut = 'EN_ATTENTE'")
//    long countDemandesEnAttente();
}

