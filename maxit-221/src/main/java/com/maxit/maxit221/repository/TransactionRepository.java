package com.maxit.maxit221.repository;

import com.maxit.maxit221.model.StatutTransaction;
import com.maxit.maxit221.model.Transaction;
import com.maxit.maxit221.model.TypeTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Transaction findByReference(String reference);

    boolean existsByReference(String reference);

    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.compteSource.id = :compteId OR t.compteDestination.id = :compteId) " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findByCompteId(@Param("compteId") long compteId, Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.compteSource.id = :compteId OR t.compteDestination.id = :compteId) " +
            "AND t.typeTransaction = :typeTransaction " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findByCompteIdAndType(@Param("compteId") Long compteId,
                                            @Param("typeTransaction") TypeTransaction typeTransaction,
                                            Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.compteSource.id = :compteId OR t.compteDestination.id = :compteId) " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findTop10ByCompteIdOrderByCreatedAtDesc(@Param("compteId") long compteId, Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.compteSource.client.id = :clientId " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findByClientId(@Param("clientId") Long clientId, Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.compteSource.client.id = :clientId " +
            "AND t.typeTransaction = :typeTransaction " +
            "ORDER BY t.createdAt DESC")
    List<Transaction> findByClientIdAndType(@Param("clientId") Long clientId,
                                            @Param("typeTransaction") TypeTransaction typeTransaction,
                                            Pageable pageable);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.statut = :statut AND t.datePlanifiee <= :maintenant")
    List<Transaction> findTransactionsPlanifieesAExecuter(@Param("statut") StatutTransaction statut,
                                                          @Param("maintenant") LocalDateTime maintenant);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.isRecurrent = true AND t.prochaineExecution <= :maintenant")
    List<Transaction> findTransactionsRecurrentesAExecuter(@Param("maintenant") LocalDateTime maintenant);

    @Query("SELECT t FROM Transaction t " +
            "WHERE t.typeTransaction = 'ACHAT_WOYOFAL' AND t.statutWoyofal = 'EN_ATTENTE'")
    List<Transaction> findDemandesWoyofalEnAttente();

    @Query("SELECT COUNT(t) FROM Transaction t " +
            "WHERE t.compteSource.id = :compteId AND t.createdAt >= :dateDebut")
    long countTransactionsByCompteIdSince(@Param("compteId") Long compteId,
                                          @Param("dateDebut") LocalDateTime dateDebut);

    @Query("SELECT t FROM Transaction t "+
    "WHERE t.typeTransaction = :type" + " AND t.compteSource = :compteId" + "ORDER BY t.createdAt DESC")
    List<Transaction> findByCompteIdAndTypeTransactionOrderByDateTransactionDesc(long compteId, TypeTransaction type);
}



