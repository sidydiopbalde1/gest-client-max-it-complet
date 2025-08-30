package com.maxit.maxit221.service;

import com.maxit.maxit221.model.Client;
import com.maxit.maxit221.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ClientService implements IClient{
    
    @Autowired
    private ClientRepository clientRepository;
    
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
    
    public Client findByTelephone(String telephone) {
        return clientRepository.findByTelephone(telephone);
    }
    
    public Client findByNci(String nci) {
        return clientRepository.findByNci(nci);
    }
    
    public Client findActiveByTelephone(String telephone) {
        return clientRepository.findActiveByTelephone(telephone);
    }
    
    public Client findActiveByNci(String nci) {
        return clientRepository.findActiveByNci(nci);
    }
    
    public boolean existsByTelephone(String telephone) {
        return clientRepository.existsByTelephone(telephone);
    }
    
    public boolean existsByNci(String nci) {
        return clientRepository.existsByNci(nci);
    }
    

    
    public Client save(Client client) {
        return clientRepository.save(client);
    }


    
    public void mettreAJourPieceIdentite(Long clientId, String pieceIdentiteUrl) {
        Client clientOpt = findById(clientId);
        if (clientOpt.getIsActive()) {
            Client client = clientOpt;
            client.setPieceIdentiteUrl(pieceIdentiteUrl);
            save(client);
        }
    }
    
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}

