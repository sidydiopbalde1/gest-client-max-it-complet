package com.maxit.maxit221.dto;

import com.maxit.maxit221.model.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDtoResponse {
    private long id;
    private String reference;
    private TypeTransaction typeTransaction; // Enum en String
    private BigDecimal montant;
    private BigDecimal frais;
    private BigDecimal montantTotal;
    private CompteDtoResponse compteSource;
    private CompteDtoResponse compteDestination;
    private String numeroExterne;
    private String statut; // Enum en String
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
}
