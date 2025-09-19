package com.maxit.maxit221.controller;

import com.maxit.maxit221.dto.TransactionDto;
import com.maxit.maxit221.dto.TransactionDtoRequest;
import com.maxit.maxit221.dto.TransactionDtoResponse;
import com.maxit.maxit221.helper.TransactionHelper;
import com.maxit.maxit221.model.StatutTransaction;
import com.maxit.maxit221.model.Transaction;
import com.maxit.maxit221.model.TypeTransaction;
import com.maxit.maxit221.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    private final TransactionHelper transactionHelper;

    public TransactionController(TransactionHelper transactionHelper) {
        this.transactionHelper = transactionHelper;
    }

    @GetMapping
    public List<TransactionDtoResponse> findAll(){
        return transactionHelper.findAll();
    }
    @GetMapping("/last/10/{compteId}")
    public List<TransactionDtoResponse> getLast10Transactions(long compteId) {
        return transactionHelper.getLast10Transactions(compteId);
    }
    @GetMapping("/type/{compteId}/{type}")
    public List<TransactionDtoResponse> getTransactionsByType(long compteId, TypeTransaction type) {
        return transactionHelper.getTransactionsByType(compteId,type);
    }
//    @PostMapping
//    public TransactionDtoResponse save(@RequestBody @Valid TransactionDtoRequest transactionDtoRequest){
//        return transactionHelper.save(transactionDtoRequest);
//    }



//    @GetMapping("/{id}")
//    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id) {
//        Transaction transaction = transactionService.findById(id);
////        if (transaction.isPresent()) {
////            return ResponseEntity.ok(convertToDto(transaction.get()));
////        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/reference/{reference}")
//    public ResponseEntity<TransactionDto> getTransactionByReference(@PathVariable String reference) {
//        Transaction transaction = transactionService.findByReference(reference);
////        if (transaction.isPresent()) {
////            return ResponseEntity.ok(convertToDto(transaction.get()));
////        }
//        return ResponseEntity.notFound().build();
//    }
//
////    @GetMapping("/compte/{compteId}")
////    public ResponseEntity<Page<TransactionDto>> getTransactionsByCompteId(
////            @PathVariable Long compteId,
////            @RequestParam(defaultValue = "0") int page,
////            @RequestParam(defaultValue = "10") int size,
////            @RequestParam(required = false) String type) {
////
////        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
////        Transaction transactions;
////
////        if (type != null && !type.isEmpty()) {
////            TypeTransaction typeTransaction = TypeTransaction.valueOf(type.toUpperCase());
////            transactions = transactionService.findByCompteIdAndType(compteId, typeTransaction, pageable);
////        } else {
////            transactions = transactionService.findByCompteId(compteId, pageable);
////        }
////
////        TransactionDto transactionDtos = transactions.map(this::convertToDto);
////        return ResponseEntity.ok(transactionDtos);
////    }
//
//    @GetMapping("/compte/{compteId}/dernieres")
//    public ResponseEntity<List<TransactionDto>> getDernieresTransactions(@PathVariable Long compteId) {
//        Pageable pageable = PageRequest.of(0, 10);
//        List<Transaction> transactions = transactionService.findTop10ByCompteId(compteId, pageable);
//        List<TransactionDto> transactionDtos = transactions.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(transactionDtos);
//    }
    
//    @GetMapping("/client/{clientId}")
//    public ResponseEntity<Page<TransactionDto>> getTransactionsByClientId(
//            @PathVariable Long clientId,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(required = false) String type) {
//
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
//        Transaction transactions;
//
////        if (type != null && !type.isEmpty()) {
////            TypeTransaction typeTransaction = TypeTransaction.valueOf(type.toUpperCase());
////            transactions = transactionService.findByClientIdAndType(clientId, typeTransaction, pageable);
////        } else {
////            transactions = transactionService.findByClientId(clientId, pageable);
////        }
//
//        TransactionDto transactionDtos = transactions.map(this::convertToDto);
//        return ResponseEntity.ok(transactionDtos);
//    }
    
//    @PostMapping("/depot")
//    public ResponseEntity<?> effectuerDepot(@RequestBody Map<String, Object> request) {
//        try {
//            Long compteId = Long.valueOf(request.get("compteId").toString());
//            BigDecimal montant = new BigDecimal(request.get("montant").toString());
//            String description = (String) request.get("description");
//
//            Transaction transaction = transactionService.effectuerDepot(compteId, montant, description);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(transaction));
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
    
//    @PostMapping("/retrait")
//    public ResponseEntity<?> effectuerRetrait(@RequestBody Map<String, Object> request) {
//        try {
//            Long compteId = Long.valueOf(request.get("compteId").toString());
//            BigDecimal montant = new BigDecimal(request.get("montant").toString());
//            String description = (String) request.get("description");
//
//            Transaction transaction = transactionService.effectuerRetrait(compteId, montant, description);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(transaction));
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
//
//    @PostMapping("/transfert-interne")
//    public ResponseEntity<?> effectuerTransfertInterne(@RequestBody Map<String, Object> request) {
//        try {
//            Long compteSourceId = Long.valueOf(request.get("compteSourceId").toString());
//            Long compteDestinationId = Long.valueOf(request.get("compteDestinationId").toString());
//            BigDecimal montant = new BigDecimal(request.get("montant").toString());
//            String description = (String) request.get("description");
//            Boolean fraisPrisEnCharge = (Boolean) request.getOrDefault("fraisPrisEnCharge", false);
//
//            Transaction transaction = transactionService.effectuerTransfertInterne(
//                    compteSourceId, compteDestinationId, montant, description, fraisPrisEnCharge);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(transaction));
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
    
//    @PostMapping("/transfert-externe")
//    public ResponseEntity<?> effectuerTransfertExterne(@RequestBody Map<String, Object> request) {
//        try {
//            Long compteSourceId = Long.valueOf(request.get("compteSourceId").toString());
//            String numeroExterne = (String) request.get("numeroExterne");
//            String nomBeneficiaire = (String) request.get("nomBeneficiaire");
//            BigDecimal montant = new BigDecimal(request.get("montant").toString());
//            String description = (String) request.get("description");
//            Boolean fraisPrisEnCharge = (Boolean) request.getOrDefault("fraisPrisEnCharge", false);
//
//            Transaction transaction = transactionService.effectuerTransfertExterne(
//                    compteSourceId, numeroExterne, nomBeneficiaire, montant, description, fraisPrisEnCharge);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(transaction));
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
//
//    @PostMapping("/transfert-planifie")
//    public ResponseEntity<?> planifierTransfert(@RequestBody Map<String, Object> request) {
//        try {
//            Long compteSourceId = Long.valueOf(request.get("compteSourceId").toString());
//            Long compteDestinationId = Long.valueOf(request.get("compteDestinationId").toString());
//            BigDecimal montant = new BigDecimal(request.get("montant").toString());
//            String description = (String) request.get("description");
//            String prochainExecutionStr = (String) request.get("prochainExecution");
//            String frequence = (String) request.get("frequence");
//
//            LocalDateTime prochainExecution = LocalDateTime.parse(prochainExecutionStr);
//
//            Transaction transaction = transactionService.planifierTransfertRecurrent(
//                    compteSourceId, compteDestinationId, montant, description, prochainExecution, frequence);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(transaction));
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
    
//    @PostMapping("/woyofal/demande")
//    public ResponseEntity<?> creerDemandeWoyofal(@RequestBody Map<String, Object> request) {
//        try {
//            Long compteSourceId = Long.valueOf(request.get("compteSourceId").toString());
//            BigDecimal montant = new BigDecimal(request.get("montant").toString());
//            String description = (String) request.get("description");
//
//            Transaction transaction = transactionService.creerDemandeWoyofal(compteSourceId, montant, description);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(transaction));
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
    
//    @PutMapping("/woyofal/{transactionId}/traiter")
//    public ResponseEntity<?> traiterDemandeWoyofal(@PathVariable Long transactionId) {
//        try {
//            Transaction transaction = transactionService.traiterDemandeWoyofal(transactionId);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "Demande Woyofal traitée avec succès");
//            response.put("transaction", convertToDto(transaction));
//            response.put("codeWoyofal", transaction.getCodeWoyofal());
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(error);
//        }
//    }
    
    // Méthode utilitaire pour convertir Transaction en TransactionDto
//    private TransactionDto convertToDto(Transaction transaction) {
//        TransactionDto dto = new TransactionDto();
//        dto.setId(transaction.getId());
//        dto.setReference(transaction.getReference());
//        dto.setTypeTransaction(transaction.getTypeTransaction());
//        dto.setMontant(transaction.getMontant());
//        dto.setFrais(transaction.getFrais());
//        dto.setMontantTotal(transaction.getMontantTotal());
//        dto.setStatut(transaction.getStatut());
//        dto.setDescription(transaction.getDescription());
//        dto.setMotif(transaction.getMotif());
//        dto.setFraisPrisEnCharge(transaction.getFraisPrisEnCharge());
//        dto.setDateExecution(transaction.getDateExecution());
//        dto.setDatePlanifiee(transaction.getDatePlanifiee());
//        dto.setIsRecurrent(transaction.getIsRecurrent());
//        dto.setFrequenceRecurrence(transaction.getFrequenceRecurrence());
//        dto.setProchaineExecution(transaction.getProchaineExecution());
//        dto.setCodeWoyofal(transaction.getCodeWoyofal());
//        dto.setStatutWoyofal(transaction.getStatutWoyofal());
//        dto.setCreatedAt(transaction.getCreatedAt());
//        dto.setUpdatedAt(transaction.getUpdatedAt());
//
//        if (transaction.getCompteSource() != null) {
//            dto.setCompteSourceId(transaction.getCompteSource().getId());
//            dto.setCompteSourceNumero(transaction.getCompteSource().getNumeroCompte());
//        }
//
//        if (transaction.getCompteDestination() != null) {
//            dto.setCompteDestinationId(transaction.getCompteDestination().getId());
//            dto.setCompteDestinationNumero(transaction.getCompteDestination().getNumeroCompte());
//        }
//
//        dto.setNumeroExterne(transaction.getNumeroExterne());
//        dto.setNomBeneficiaire(transaction.getNomBeneficiaire());
//
//        return dto;
//    }
}

