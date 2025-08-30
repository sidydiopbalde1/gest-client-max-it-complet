package com.maxit.maxit221.helper;

import com.maxit.maxit221.dto.TransactionDtoRequest;
import com.maxit.maxit221.dto.TransactionDtoResponse;
import com.maxit.maxit221.mapper.TransactionMapper;
import com.maxit.maxit221.model.Transaction;
import com.maxit.maxit221.model.TypeTransaction;
import com.maxit.maxit221.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TransactionHelper {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    public TransactionHelper(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    public List<TransactionDtoResponse> findAll() {
        return transactionService.findAll().stream().map(transactionMapper::TransactionEntityToTransactionResponse).toList();
    }

    public List<TransactionDtoResponse> getLast10Transactions(long compteId) {
        return transactionService.getLast10Transactions(compteId).stream().map(transactionMapper::TransactionEntityToTransactionResponse).toList();
    }

    public List<TransactionDtoResponse> getTransactionsByType(long compteId, TypeTransaction type) {
        return transactionService.getTransactionsByType(compteId,type).stream().map(transactionMapper::TransactionEntityToTransactionResponse).toList();
    }

    public TransactionDtoResponse save(TransactionDtoRequest transactionDtoRequest) {
        if(transactionDtoRequest.getFraisPrisEnCharge()){

        }
                BigDecimal frais = source.getEstSousCompte() || destination.getEstSousCompte()
                ? BigDecimal.ZERO
                : montant.multiply(new BigDecimal("0.08"));

        if (!fraisPrisEnCharge) {
            montant = montant.subtract(frais);
        }
    }
}
