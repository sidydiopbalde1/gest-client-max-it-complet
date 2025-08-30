package com.maxit.maxit221.controller;

import com.maxit.maxit221.dto.CompteDtoResponse;
import com.maxit.maxit221.dto.CompteRequest;
import com.maxit.maxit221.helper.CompteHelper;
import com.maxit.maxit221.model.Client;
import com.maxit.maxit221.model.Compte;
import com.maxit.maxit221.service.ClientService;
import com.maxit.maxit221.service.CompteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comptes")
@CrossOrigin(origins = "*")
public class CompteController {
//    private final CompteHelper compteHelper;
//
//    public CompteController(CompteHelper compteHelper) {
//        this.compteHelper = compteHelper;
//    }
//
//    @GetMapping
//    public @ResponseBody List<CompteDtoResponse> all(){
//        return compteHelper.findAll();
//    }
//
//    @PostMapping
//    public @ResponseBody CompteDtoResponse save(@RequestBody @Valid CompteRequest compteRequest){
//        return compteHelper.creerComptePrincipal(compteRequest);
//    }
}

