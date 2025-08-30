package com.maxit.maxit221.mapper;

import com.maxit.maxit221.dto.ClientDtoResponse;
import com.maxit.maxit221.dto.CompteDtoResponse;
import com.maxit.maxit221.dto.CompteRequest;
import com.maxit.maxit221.model.Client;
import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.model.TypeCompte;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CompteMapper {
    public Compte CreateCompteRequestToAchatEntity(CompteRequest compteDto){
        return Compte.builder()
                .numeroCompte(compteDto.getNumeroCompte())
                .solde(BigDecimal.ZERO)
                .isActive(true)
                .isBlocked(false)
                .typeCompte(TypeCompte.PRINCIPAL)
                .client(Client.builder()
                        .id(compteDto.getClientId())
                        .build())
                .build();
    }
    public CompteDtoResponse CompteEntityToCreateCompteResponse(Compte compte){
        return CompteDtoResponse.builder()
                .id(compte.getId())
                .solde(compte.getSolde())
                .numeroCompte(compte.getNumeroCompte())
                .typeCompte(compte.getTypeCompte())
                .createdAt(compte.getCreatedAt())
                .comptePrincipalId(compte.getId())
                .blockedReason(compte.getBlockedReason())
                .blockedUntil(compte.getBlockedUntil())
                .client(ClientDtoResponse.builder()
                        .id(compte.getClient().getId())
                        .nom(compte.getClient().getNom())
                        .prenom(compte.getClient().getPrenom())
                        .nci(compte.getClient().getNci())
                        .build())
                .build();

    }
}
