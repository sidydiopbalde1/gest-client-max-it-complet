package com.maxit.maxit221.dto;

import com.maxit.maxit221.model.TypeCompte;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompteDtoResponse {
    private long id;
    @NotNull(message = "Le numéro de compte est obligatoire")
    private String numeroCompte;
    private TypeCompte typeCompte;
    @DecimalMin(value = "0.0", message = "Le solde ne peut pas être négatif")
    private BigDecimal solde;

    private Boolean isActive;
    private Boolean isBlocked;
    private LocalDateTime blockedUntil;
    private String blockedReason;
    private ClientDtoResponse client;
    private long comptePrincipalId;
    private String comptePrincipalNumero;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
