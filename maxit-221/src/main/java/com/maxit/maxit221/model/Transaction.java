package com.maxit.maxit221.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull(message = "La référence de transaction est obligatoire")
    @Column(name = "reference", nullable = false, unique = true)
    private String reference;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_transaction", nullable = false)
    private TypeTransaction typeTransaction;
    
    @DecimalMin(value = "0.01", message = "Le montant doit être positif")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal montant;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal frais = BigDecimal.ZERO;
    
    @Column(name = "montant_total", precision = 15, scale = 2)
    private BigDecimal montantTotal;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compte_source_id")
    private Compte compteSource;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compte_destination_id")
    private Compte compteDestination;


    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutTransaction statut = StatutTransaction.EN_ATTENTE;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "motif")
    private String motif;
    
    @Column(name = "frais_pris_en_charge")
    private Boolean fraisPrisEnCharge = false;
    
    @Column(name = "date_execution")
    private LocalDateTime dateExecution;
    
    @Column(name = "date_planifiee")
    private LocalDateTime datePlanifiee;
    
    @Column(name = "is_recurrent")
    private Boolean isRecurrent = false;
    
    @Column(name = "frequence_recurrence")
    private String frequenceRecurrence;
    
    @Column(name = "prochaine_execution")
    private LocalDateTime prochaineExecution;
    
    @Column(name = "code_woyofal")
    private String codeWoyofal;
    
    @Column(name = "statut_woyofal")
    private String statutWoyofal;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Méthodes utilitaires
    public void calculerMontantTotal() {
        this.montantTotal = this.montant.add(this.frais);
    }
    
    public boolean isTransfertInterne() {
        return compteSource != null && compteDestination != null;
    }
    
}

