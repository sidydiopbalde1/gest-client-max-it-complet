package com.maxit.maxit221.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandeAnnulation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull(message = "La référence de demande est obligatoire")
    @Column(name = "reference", nullable = false, unique = true)
    private String reference;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "demandeur_id", nullable = false)
    private Client demandeur;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutDemandeAnnulation statut = StatutDemandeAnnulation.EN_ATTENTE;
    
    @Column(name = "motif_demande")
    private String motifDemande;
    
    @Column(name = "motif_rejet")
    private String motifRejet;
    
    @Column(name = "date_traitement")
    private LocalDateTime dateTraitement;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "traite_par_id")
    private Client traiteParId;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    

}

