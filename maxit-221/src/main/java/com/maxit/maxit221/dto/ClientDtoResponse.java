package com.maxit.maxit221.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDtoResponse {
    private long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String nci;
    private String adresse;
    private CompteDtoResponse compte;
    private String photoUrl;
    private String pieceIdentiteUrl;
    private Boolean isActive;
    private Boolean isVerified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
