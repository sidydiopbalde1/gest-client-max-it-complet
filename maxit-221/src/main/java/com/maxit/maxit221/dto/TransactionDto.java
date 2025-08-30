package com.maxit.maxit221.dto;

import com.maxit.maxit221.model.StatutTransaction;
import com.maxit.maxit221.model.TypeTransaction;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    
    private long id;
    
    @NotNull(message = "La référence de transaction est obligatoire")
    private String reference;
    private TypeTransaction typeTransaction;
    @DecimalMin(value = "0.01", message = "Le montant doit être positif")
    private BigDecimal montant;
    private BigDecimal frais;
    private BigDecimal montantTotal;
    private StatutTransaction statut;
    private String description;
    private String motif;
    private Boolean fraisPrisEnCharge;
    private LocalDateTime dateExecution;
    private LocalDateTime datePlanifiee;
    private Boolean isRecurrent;
    private String frequenceRecurrence;
    private LocalDateTime prochaineExecution;
    private String codeWoyofal;
    private String statutWoyofal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Informations des comptes
    private Long compteSourceId;
    private String compteSourceNumero;
    private Long compteDestinationId;
    private String compteDestinationNumero;
    private String numeroExterne;
    private String nomBeneficiaire;

}

