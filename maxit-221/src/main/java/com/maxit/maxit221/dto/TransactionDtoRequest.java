package com.maxit.maxit221.dto;

import com.maxit.maxit221.model.TypeTransaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class TransactionDtoRequest {
        private TypeTransaction typeTransaction;
        private BigDecimal montant;
        private long compteSourceId;
        private long compteDestinationId;// Enum en String
        private String description;
        private Boolean fraisPrisEnCharge;
    }


