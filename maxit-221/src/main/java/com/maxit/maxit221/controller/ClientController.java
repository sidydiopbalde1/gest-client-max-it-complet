package com.maxit.maxit221.controller;

import com.maxit.maxit221.dto.ClientDtoResponse;
import com.maxit.maxit221.dto.ClientRequest;
import com.maxit.maxit221.helper.ClientHelper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    private final ClientHelper clientHelper;

    public ClientController(ClientHelper clientHelper) {
        this.clientHelper = clientHelper;
    }

    @PostMapping
    public @ResponseBody ClientDtoResponse save(@RequestBody @Valid ClientRequest clientRequest){
        return clientHelper.save(clientRequest);
    }

    @GetMapping
    public @ResponseBody List<ClientDtoResponse> all(){
        return clientHelper.findAll();
    }

}

