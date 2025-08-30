package com.maxit.maxit221.controller;

import com.maxit.maxit221.external.AppDAFService;
import com.maxit.maxit221.external.CloudinaryService;
import com.maxit.maxit221.external.GesClientService;
import com.maxit.maxit221.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/external")
@CrossOrigin(origins = "*")
public class ExternalController {
    
    @Autowired
    private GesClientService gesClientService;
    
    @Autowired
    private AppDAFService appDAFService;
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
    @Autowired
    private ClientService clientService;
    
    @GetMapping("/gesclient/rechercher/{telephone}")
    public ResponseEntity<?> rechercherTelephone(@PathVariable String telephone) {
        try {
            GesClientService.GesClientResponse response = gesClientService.rechercherNumero(telephone);
            
            Map<String, Object> result = new HashMap<>();
            result.put("existe", response.isExiste());
            result.put("actif", response.isActif());
            
            if (response.isExiste()) {
                result.put("nomComplet", response.getNomComplet());
                result.put("photoUrl", response.getPhotoUrl());
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la recherche dans GesClient");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @GetMapping("/appdaf/rechercher/{nci}")
    public ResponseEntity<?> rechercherNCI(@PathVariable String nci) {
        try {
            AppDAFService.AppDAFResponse response = appDAFService.rechercherNCI(nci);
            
            Map<String, Object> result = new HashMap<>();
            result.put("existe", response.isExiste());
            result.put("valide", response.isValide());
            
            if (response.isExiste()) {
                result.put("nomComplet", response.getNomComplet());
                result.put("typePiece", response.getTypePiece());
                result.put("pieceRectoUrl", response.getPieceRectoUrl());
                result.put("pieceVersoUrl", response.getPieceVersoUrl());
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la recherche dans AppDAF");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
//    @PostMapping("/upload/photo-client/{clientId}")
//    public ResponseEntity<?> uploadPhotoClient(@PathVariable Long clientId,
//                                             @RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Fichier requis");
//            return ResponseEntity.badRequest().body(error);
//        }
//
//        try {
//            String photoUrl = cloudinaryService.uploadPhotoClient(file, clientId);
//            clientService.mettreAJourPhoto(clientId, photoUrl);
//
//            Map<String, String> result = new HashMap<>();
//            result.put("message", "Photo uploadée avec succès");
//            result.put("photoUrl", photoUrl);
//
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            Map<String, String> error = new HashMap<>();
//            error.put("error", "Erreur lors de l'upload de la photo");
//            return ResponseEntity.internalServerError().body(error);
//        }
//    }
    
    @PostMapping("/upload/piece-identite/{clientId}")
    public ResponseEntity<?> uploadPieceIdentite(@PathVariable Long clientId,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("type") String type) {
        if (file.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Fichier requis");
            return ResponseEntity.badRequest().body(error);
        }
        
        if (!"recto".equals(type) && !"verso".equals(type)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Type doit être 'recto' ou 'verso'");
            return ResponseEntity.badRequest().body(error);
        }
        
        try {
            String pieceUrl = cloudinaryService.uploadPieceIdentite(file, clientId, type);
            clientService.mettreAJourPieceIdentite(clientId, pieceUrl);
            
            Map<String, String> result = new HashMap<>();
            result.put("message", "Pièce d'identité uploadée avec succès");
            result.put("pieceUrl", pieceUrl);
            result.put("type", type);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de l'upload de la pièce d'identité");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @PostMapping("/upload/document")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file,
                                          @RequestParam("folder") String folder,
                                          @RequestParam("publicId") String publicId) {
        if (file.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Fichier requis");
            return ResponseEntity.badRequest().body(error);
        }
        
        try {
            String documentUrl = cloudinaryService.uploadDocument(file, folder, publicId);
            
            Map<String, String> result = new HashMap<>();
            result.put("message", "Document uploadé avec succès");
            result.put("documentUrl", documentUrl);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de l'upload du document");
            return ResponseEntity.internalServerError().body(error);
        }
    }
    
    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<?> deleteFile(@PathVariable String publicId) {
        try {
            boolean deleted = cloudinaryService.deleteFile(publicId);
            
            Map<String, Object> result = new HashMap<>();
            if (deleted) {
                result.put("message", "Fichier supprimé avec succès");
                result.put("deleted", true);
            } else {
                result.put("message", "Erreur lors de la suppression");
                result.put("deleted", false);
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur lors de la suppression");
            return ResponseEntity.internalServerError().body(error);
        }
    }
}

