package com.maxit.maxit221.helper;

import com.maxit.maxit221.dto.*;
import com.maxit.maxit221.exception.MaxItCompleteException;
import com.maxit.maxit221.exception.MaxItNotFoundException;
import com.maxit.maxit221.mapper.ClientMapper;
import com.maxit.maxit221.mapper.CompteMapper;
import com.maxit.maxit221.model.Client;
import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.proxy.AppDafProxy;
import com.maxit.maxit221.proxy.GesClientProxy;
import com.maxit.maxit221.service.IClient;
import com.maxit.maxit221.service.ICompte;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class ClientHelper {

    private final ClientMapper clientMapper;
    private final IClient iClient;
    private final ICompte iCompte;
    private final CompteMapper compteMapper;
    private final GesClientProxy gesClientProxy;
    private final AppDafProxy appDafProxy;
    public ClientHelper(ClientMapper clientMapper, IClient iClient, ICompte iCompte, CompteMapper compteMapper, GesClientProxy gesClientProxy, AppDafProxy appDafProxy) {
        this.clientMapper = clientMapper;
        this.iClient = iClient;
        this.iCompte = iCompte;

        this.compteMapper = compteMapper;
        this.gesClientProxy = gesClientProxy;
        this.appDafProxy = appDafProxy;
    }

    public void genererCodeAuthentification(Client client) {
        // Générer un code à 6 chiffres
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(1000000));

        client.setCodeAuthentification(code);
        client.setCodeExpiration(LocalDateTime.now().plusMinutes(5)); // Code valide 5 minutes

    }

//    public boolean verifierCodeAuthentification(String code) {
//        Client clientOpt = clientRepository.findByValidAuthCode(code);
//        if (clientOpt.getIsActive()) {
//            Client client = clientOpt;
//            client.setIsVerified(true);
//            client.setCodeAuthentification(null);
//            client.setCodeExpiration(null);
//            save(client);
//            return true;
//        }
//        return false;
//    }

    public String genererNumeroCompte() {
        String timestamp = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String random = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        return "COMP" + timestamp + random;
    }

    public ClientDtoResponse save(ClientRequest clientRequest) {
        GesClientDto gesClientDto = gesClientProxy.findClientByNumero(clientRequest.getTelephone());
        CitoyenDto citoyenDto = appDafProxy.findCitoyenByNci(clientRequest.getNci());
        if(gesClientDto==null){
            throw new MaxItNotFoundException("Le numéro de téléphone  n'est pas valide");
        }
        if(citoyenDto==null){
            throw new MaxItNotFoundException("Ce numéro n'est pas valide");
        }
        if(iClient.findByNci(clientRequest.getNci())!=null){
            throw new MaxItCompleteException("Ce client existe dèjà !! ");
        }
        if(iClient.findByTelephone(clientRequest.getTelephone())!=null){
            throw new MaxItCompleteException("Ce client existe dèjà");
        }
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(1000000));

        // Sauvegarder le client

        Client clientEntity = clientMapper.CreateClientRequestToClientEntity(clientRequest);
        clientEntity.setId(null);
        clientEntity.setCodeAuthentification(code);
        clientEntity.setCodeExpiration(LocalDateTime.now().plusMinutes(5));
        Client savedClient = iClient.save(clientEntity);

        // Générer un numéro de compte
        String numeroCompte = genererNumeroCompte();

        // Créer et associer le compte principal
        CompteRequest compteRequest = new CompteRequest();
        compteRequest.setNumeroCompte(numeroCompte);
        compteRequest.setClientId(savedClient.getId());

        Compte comptePrincipal = compteMapper.CreateCompteRequestToAchatEntity(compteRequest);
        iCompte.save(comptePrincipal); // Assure-toi que le compte est bien sauvegardé

        // Générer et enregistrer le code d'authentification
      //  genererCodeAuthentification(savedClient);

        // Retourner la réponse
        return clientMapper.ClientEntityToCreateClientResponse(savedClient);
    }

    public List<ClientDtoResponse> findAll() {
        return iClient.findAll()
                .stream()
                .map(clientMapper::ClientEntityToCreateClientResponse)
                .toList();
    }

}
