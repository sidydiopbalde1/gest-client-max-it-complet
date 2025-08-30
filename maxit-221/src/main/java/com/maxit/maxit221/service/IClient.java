package com.maxit.maxit221.service;

import com.maxit.maxit221.model.Client;

import java.util.List;
import java.util.Optional;

public interface IClient {
     List<Client> findAll();
     Client findById(long id);
     Client findByTelephone(String telephone);
     Client findByNci(String nci);
     Client findActiveByTelephone(String telephone);
     Client findActiveByNci(String nci);
     boolean existsByTelephone(String telephone);
     boolean existsByNci(String nci);
     Client save(Client client);
}
