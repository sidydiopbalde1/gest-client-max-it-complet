package com.maxit.maxit221.repository;

import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.model.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {

    Compte findByNumeroCompte(String numeroCompte);

    boolean existsByNumeroCompte(String numeroCompte);

    List<Compte> findByClientIdAndIsActiveTrue(long clientId);

    List<Compte> findByClientIdAndTypeCompte(Long clientId, TypeCompte typeCompte);

    @Query("SELECT c FROM Compte c WHERE c.client.id = :clientId AND c.typeCompte = 'PRINCIPAL' AND c.isActive = true")
    Compte findComptePrincipalByClientId(@Param("clientId") Long clientId);

    @Query("SELECT c FROM Compte c WHERE c.comptePrincipal.id = :comptePrincipalId AND c.isActive = true")
    List<Compte> findSousComptesByComptePrincipalId(@Param("comptePrincipalId") Long comptePrincipalId);

    @Query("SELECT c FROM Compte c WHERE c.numeroCompte = :numeroCompte AND c.isActive = true AND c.isBlocked = false")
    Compte findActiveAndUnblockedByNumeroCompte(@Param("numeroCompte") String numeroCompte);

    @Query("SELECT c FROM Compte c WHERE c.client.id = :clientId AND c.typeCompte = 'SOUS_COMPTE' AND c.isBlocked = false")
    List<Compte> findUnblockedSousComptesByClientId(@Param("clientId") Long clientId);

    @Query("SELECT COUNT(c) FROM Compte c WHERE c.client.id = :clientId AND c.typeCompte = 'PRINCIPAL'")
    long countComptePrincipalByClientId(@Param("clientId") Long clientId);

    //  Méthodes ajoutées
    Compte findByIdAndIsActiveTrue(Long id);

    Compte findByIdAndIsActiveTrueAndIsBlockedFalse(Long id);

}
