package com.maxit.maxit221.mapper;

import com.maxit.maxit221.dto.ClientRequest;
import com.maxit.maxit221.dto.ClientDtoResponse;
import com.maxit.maxit221.model.Client;

import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client CreateClientRequestToClientEntity(ClientRequest compteRequest){
        return Client.builder()
                .adresse(compteRequest.getAdresse())
                .nci(compteRequest.getNci())
                .nom(compteRequest.getNom())
                .prenom(compteRequest.getPrenom())
                .telephone(compteRequest.getTelephone())
                .build();
    }
    public ClientDtoResponse ClientEntityToCreateClientResponse(Client client){
        return ClientDtoResponse.builder()
                .nci(client.getNci())
                .prenom(client.getPrenom())
                .nom(client.getNom())
                .telephone(client.getTelephone())
                .adresse(client.getAdresse())
                .isActive(client.getIsActive())
                .build();

    }
}
