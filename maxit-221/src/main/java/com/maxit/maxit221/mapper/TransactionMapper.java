package com.maxit.maxit221.mapper;

import com.maxit.maxit221.dto.*;
import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction CreateTransactionRequestToTransactionEntity(TransactionDtoRequest transactionDtoRequest){
        return Transaction.builder()
                .montant(transactionDtoRequest.getMontant())
                .typeTransaction(transactionDtoRequest.getTypeTransaction())
                .compteDestination(Compte.builder()
                        .id(transactionDtoRequest.getCompteDestinationId())
                        .build())
                .compteSource(Compte.builder()
                        .id(transactionDtoRequest.getCompteSourceId())
                        .build())
                .description(transactionDtoRequest.getDescription())
                .fraisPrisEnCharge(transactionDtoRequest.getFraisPrisEnCharge())
                .build();
    }
    public  TransactionDtoResponse TransactionEntityToTransactionResponse(Transaction transaction){
        return TransactionDtoResponse.builder()
                .id(transaction.getId())
                .frais(transaction.getFrais())
                .typeTransaction(transaction.getTypeTransaction())
                .compteSource(CompteDtoResponse.builder()
                        .client(ClientDtoResponse.builder()
                                .id(transaction.getCompteSource().getId())
                                .nom(transaction.getCompteSource().getClient().getNom())
                                .prenom(transaction.getCompteSource().getClient().getPrenom())
                                .nci(transaction.getCompteSource().getClient().getNci())
                                .build())
                        .build())
                .build();
    }
}
