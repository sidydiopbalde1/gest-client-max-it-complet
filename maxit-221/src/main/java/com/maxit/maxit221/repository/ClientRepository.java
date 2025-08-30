package com.maxit.maxit221.repository;

import com.maxit.maxit221.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Client findByTelephone(String telephone);
    
    Client findByNci(String nci);

    boolean existsByTelephone(String telephone);
    
    boolean existsByNci(String nci);
    

    @Query("SELECT c FROM Client c WHERE c.telephone = :telephone AND c.isActive = true")
     Client findActiveByTelephone(@Param("telephone") String telephone);
    
    @Query("SELECT c FROM Client c WHERE c.nci = :nci AND c.isActive = true")
    Client findActiveByNci(@Param("nci") String nci);
    
    @Query("SELECT c FROM Client c WHERE c.codeAuthentification = :code AND c.codeExpiration > CURRENT_TIMESTAMP")
    Client findByValidAuthCode(@Param("code") String code);
}

