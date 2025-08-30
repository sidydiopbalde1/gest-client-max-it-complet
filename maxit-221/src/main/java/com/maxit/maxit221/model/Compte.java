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
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Compte {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotNull(message = "Le numéro de compte est obligatoire")
    @Column(name = "numero_compte", nullable = false, unique = true)
    private String numeroCompte;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type_compte", nullable = false)
    private TypeCompte typeCompte;
    
    @DecimalMin(value = "0.0", message = "Le solde ne peut pas être négatif")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal solde = BigDecimal.ZERO;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "is_blocked")
    private Boolean isBlocked = false;
    
    @Column(name = "blocked_until")
    private LocalDateTime blockedUntil;
    
    @Column(name = "blocked_reason")
    private String blockedReason;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compte_principal_id")
    private Compte comptePrincipal;
    
    @OneToMany(mappedBy = "comptePrincipal", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Compte> sousComptes = new ArrayList<>();
    
    @OneToMany(mappedBy = "compteSource", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactionsSource = new ArrayList<>();
    
    @OneToMany(mappedBy = "compteDestination", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactionsDestination = new ArrayList<>();
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    

    // Méthodes utilitaires
    public boolean isPrincipal() {
        return typeCompte == TypeCompte.PRINCIPAL;
    }
    
    public boolean isSousCompte() {
        return typeCompte == TypeCompte.SOUS_COMPTE;
    }
    
    public boolean isBloqueTemporal() {
        return isBlocked && blockedUntil != null && LocalDateTime.now().isBefore(blockedUntil);
    }
    public boolean peutEffectuerTransaction() {
        return isActive && !isBlocked && !isBloqueTemporal();
    }
}

