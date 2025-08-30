# Documentation Technique API Maxit-221

## Vue d'ensemble

L'API Maxit-221 est une interface RESTful développée avec Spring Boot qui fournit des services bancaires complets incluant la gestion des clients, des comptes, des transactions et des demandes d'annulation. Cette documentation détaille tous les endpoints disponibles, leurs paramètres, et les réponses attendues.

## Base URL

```
http://localhost:8080/api
```

## Authentification

L'API utilise l'authentification JWT (JSON Web Token). Pour accéder aux endpoints protégés, incluez le token dans l'en-tête Authorization :

```
Authorization: Bearer <votre-jwt-token>
```

## Format des Réponses

Toutes les réponses de l'API sont au format JSON. Les réponses d'erreur suivent le format standard :

```json
{
  "error": "Message d'erreur descriptif"
}
```

Les réponses de succès varient selon l'endpoint mais incluent généralement les données demandées ou un message de confirmation.

## Codes de Statut HTTP

- `200 OK` - Requête réussie
- `201 Created` - Ressource créée avec succès
- `400 Bad Request` - Paramètres invalides
- `404 Not Found` - Ressource non trouvée
- `500 Internal Server Error` - Erreur serveur

---

## Endpoints - Gestion des Clients

### GET /api/clients

Récupère la liste de tous les clients.

**Réponse :**
```json
[
  {
    "id": 1,
    "nom": "Diallo",
    "prenom": "Amadou",
    "telephone": "+221771234567",
    "nci": "1234567890123",
    "adresse": "Dakar, Sénégal",
    "email": "amadou.diallo@example.com",
    "photoUrl": "https://cloudinary.com/photo1.jpg",
    "pieceIdentiteUrl": "https://cloudinary.com/piece1.jpg",
    "isActive": true,
    "isVerified": true,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

### GET /api/clients/{id}

Récupère un client spécifique par son ID.

**Paramètres :**
- `id` (path) - ID du client (Long)

**Réponse :** Objet client ou 404 si non trouvé.

### GET /api/clients/telephone/{telephone}

Recherche un client par son numéro de téléphone.

**Paramètres :**
- `telephone` (path) - Numéro de téléphone (String)

**Exemple :** `/api/clients/telephone/+221771234567`

### GET /api/clients/nci/{nci}

Recherche un client par son NCI (Numéro de Carte d'Identité).

**Paramètres :**
- `nci` (path) - Numéro de carte d'identité (String)

**Exemple :** `/api/clients/nci/1234567890123`

### POST /api/clients

Crée un nouveau client.

**Corps de la requête :**
```json
{
  "nom": "Sall",
  "prenom": "Fatou",
  "telephone": "+221776543210",
  "nci": "9876543210987",
  "adresse": "Thiès, Sénégal",
  "email": "fatou.sall@example.com"
}
```

**Validation :**
- `nom` : Obligatoire, non vide
- `prenom` : Obligatoire, non vide
- `telephone` : Obligatoire, format valide, unique
- `nci` : Obligatoire, non vide, unique
- `adresse` : Obligatoire, non vide
- `email` : Format email valide (optionnel)

**Réponse :** Objet client créé avec code 201.

### POST /api/clients/{id}/generer-code

Génère un code d'authentification pour un client.

**Paramètres :**
- `id` (path) - ID du client (Long)

**Réponse :**
```json
{
  "message": "Code d'authentification généré avec succès",
  "code": "123456",
  "clientId": 1
}
```

**Note :** En production, le code ne devrait pas être retourné dans la réponse mais envoyé par SMS.

### POST /api/clients/verifier-code

Vérifie un code d'authentification.

**Corps de la requête :**
```json
{
  "code": "123456"
}
```

**Réponse :**
```json
{
  "message": "Code d'authentification valide",
  "valide": true
}
```

### PUT /api/clients/{id}

Met à jour les informations d'un client.

**Paramètres :**
- `id` (path) - ID du client (Long)

**Corps de la requête :** Objet client avec les champs à modifier.

### PUT /api/clients/{id}/activer

Active un client.

**Paramètres :**
- `id` (path) - ID du client (Long)

### PUT /api/clients/{id}/desactiver

Désactive un client.

**Paramètres :**
- `id` (path) - ID du client (Long)

### DELETE /api/clients/{id}

Supprime un client.

**Paramètres :**
- `id` (path) - ID du client (Long)

---

## Endpoints - Gestion des Comptes

### GET /api/comptes

Récupère la liste de tous les comptes.

**Réponse :**
```json
[
  {
    "id": 1,
    "numeroCompte": "MAX0001234567",
    "typeCompte": "PRINCIPAL",
    "solde": 50000.00,
    "isActive": true,
    "isBlocked": false,
    "blockedUntil": null,
    "blockedReason": null,
    "clientId": 1,
    "clientNomComplet": "Amadou Diallo",
    "comptePrincipalId": null,
    "comptePrincipalNumero": null,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

### GET /api/comptes/{id}

Récupère un compte spécifique par son ID.

**Paramètres :**
- `id` (path) - ID du compte (Long)

### GET /api/comptes/numero/{numeroCompte}

Recherche un compte par son numéro.

**Paramètres :**
- `numeroCompte` (path) - Numéro du compte (String)

### GET /api/comptes/client/{clientId}

Récupère tous les comptes d'un client.

**Paramètres :**
- `clientId` (path) - ID du client (Long)

### GET /api/comptes/client/{clientId}/principal

Récupère le compte principal d'un client.

**Paramètres :**
- `clientId` (path) - ID du client (Long)

### POST /api/comptes/principal

Crée un nouveau compte principal.

**Corps de la requête :**
```json
{
  "clientId": 1
}
```

**Règles métier :**
- Un client ne peut avoir qu'un seul compte principal
- Le numéro de compte est généré automatiquement

### POST /api/comptes/sous-compte

Crée un nouveau sous-compte.

**Corps de la requête :**
```json
{
  "clientId": 1,
  "comptePrincipalId": 1
}
```

**Règles métier :**
- Le compte principal doit appartenir au même client
- Le compte principal doit être actif

### PUT /api/comptes/{id}/bloquer

Bloque un compte de manière permanente.

**Paramètres :**
- `id` (path) - ID du compte (Long)

**Corps de la requête :**
```json
{
  "raison": "Activité suspecte détectée"
}
```

### PUT /api/comptes/{id}/bloquer-periode

Bloque un compte pour une période déterminée.

**Paramètres :**
- `id` (path) - ID du compte (Long)

**Corps de la requête :**
```json
{
  "raison": "Maintenance programmée",
  "finBlocage": "2024-02-01T00:00:00"
}
```

### PUT /api/comptes/{id}/debloquer

Débloque un compte.

**Paramètres :**
- `id` (path) - ID du compte (Long)

### PUT /api/comptes/{id}/changer-en-principal

Change un sous-compte en compte principal.

**Paramètres :**
- `id` (path) - ID du sous-compte (Long)

**Règles métier :**
- Le compte ne doit pas être bloqué
- Le client ne doit pas avoir déjà un compte principal

### PUT /api/comptes/{id}/crediter

Crédite un compte.

**Paramètres :**
- `id` (path) - ID du compte (Long)

**Corps de la requête :**
```json
{
  "montant": 10000.00
}
```

### PUT /api/comptes/{id}/debiter

Débite un compte.

**Paramètres :**
- `id` (path) - ID du compte (Long)

**Corps de la requête :**
```json
{
  "montant": 5000.00
}
```

**Règles métier :**
- Le solde doit être suffisant
- Le compte doit être actif et non bloqué

---

## Endpoints - Gestion des Transactions

### GET /api/transactions

Récupère la liste de toutes les transactions.

### GET /api/transactions/{id}

Récupère une transaction spécifique par son ID.

**Paramètres :**
- `id` (path) - ID de la transaction (Long)

### GET /api/transactions/reference/{reference}

Recherche une transaction par sa référence.

**Paramètres :**
- `reference` (path) - Référence de la transaction (String)

### GET /api/transactions/compte/{compteId}

Récupère les transactions d'un compte avec pagination.

**Paramètres :**
- `compteId` (path) - ID du compte (Long)
- `page` (query) - Numéro de page (défaut: 0)
- `size` (query) - Taille de page (défaut: 10)
- `type` (query) - Type de transaction (optionnel)

**Exemple :** `/api/transactions/compte/1?page=0&size=20&type=TRANSFERT_INTERNE`

**Réponse :**
```json
{
  "content": [
    {
      "id": 1,
      "reference": "TXN1642234567001",
      "typeTransaction": "TRANSFERT_INTERNE",
      "montant": 25000.00,
      "frais": 0.00,
      "montantTotal": 25000.00,
      "statut": "EXECUTEE",
      "description": "Transfert vers sous-compte épargne",
      "fraisPrisEnCharge": false,
      "dateExecution": "2024-01-15T14:30:00",
      "compteSourceId": 1,
      "compteSourceNumero": "MAX0001234567",
      "compteDestinationId": 2,
      "compteDestinationNumero": "MAX0001234568",
      "createdAt": "2024-01-15T14:30:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### GET /api/transactions/compte/{compteId}/dernieres

Récupère les 10 dernières transactions d'un compte.

**Paramètres :**
- `compteId` (path) - ID du compte (Long)

### GET /api/transactions/client/{clientId}

Récupère les transactions d'un client avec pagination et filtrage.

**Paramètres :**
- `clientId` (path) - ID du client (Long)
- `page` (query) - Numéro de page (défaut: 0)
- `size` (query) - Taille de page (défaut: 10)
- `type` (query) - Type de transaction (optionnel)

### POST /api/transactions/depot

Effectue un dépôt sur un compte.

**Corps de la requête :**
```json
{
  "compteId": 1,
  "montant": 15000.00,
  "description": "Dépôt en espèces"
}
```

**Réponse :** Transaction créée avec statut EXECUTEE.

### POST /api/transactions/retrait

Effectue un retrait sur un compte.

**Corps de la requête :**
```json
{
  "compteId": 1,
  "montant": 8000.00,
  "description": "Retrait DAB"
}
```

**Règles métier :**
- Le solde doit être suffisant
- Le compte doit être actif et non bloqué

### POST /api/transactions/transfert-interne

Effectue un transfert entre comptes internes.

**Corps de la requête :**
```json
{
  "compteSourceId": 1,
  "compteDestinationId": 2,
  "montant": 20000.00,
  "description": "Transfert vers épargne",
  "fraisPrisEnCharge": false
}
```

**Calcul des frais :**
- Transferts vers sous-comptes du même client : **Gratuit**
- Transferts entre comptes du même client : **Gratuit**
- Autres transferts internes : **1% du montant**

### POST /api/transactions/transfert-externe

Effectue un transfert vers un compte externe.

**Corps de la requête :**
```json
{
  "compteSourceId": 1,
  "numeroExterne": "+221771234567",
  "nomBeneficiaire": "Moussa Ba",
  "montant": 30000.00,
  "description": "Transfert familial",
  "fraisPrisEnCharge": false
}
```

**Calcul des frais :**
- Frais fixes : **0.08% du montant transféré**

### POST /api/transactions/transfert-planifie

Planifie un transfert récurrent.

**Corps de la requête :**
```json
{
  "compteSourceId": 1,
  "compteDestinationId": 2,
  "montant": 5000.00,
  "description": "Épargne mensuelle",
  "prochainExecution": "2024-02-01T09:00:00",
  "frequence": "MENSUEL"
}
```

**Fréquences supportées :**
- `QUOTIDIEN` - Tous les jours
- `HEBDOMADAIRE` - Toutes les semaines
- `MENSUEL` - Tous les mois
- `TRIMESTRIEL` - Tous les 3 mois

### POST /api/transactions/woyofal/demande

Crée une demande d'achat de code Woyofal.

**Corps de la requête :**
```json
{
  "compteSourceId": 1,
  "montant": 1000.00,
  "description": "Achat crédit téléphone"
}
```

**Réponse :** Transaction avec statut EN_ATTENTE.

### PUT /api/transactions/woyofal/{transactionId}/traiter

Traite une demande Woyofal et génère le code.

**Paramètres :**
- `transactionId` (path) - ID de la transaction (Long)

**Réponse :**
```json
{
  "message": "Demande Woyofal traitée avec succès",
  "transaction": { /* objet transaction */ },
  "codeWoyofal": "WYF123456789012"
}
```

---

## Endpoints - Services Externes

### GET /api/external/gesclient/rechercher/{telephone}

Recherche un numéro de téléphone dans le système GesClient.

**Paramètres :**
- `telephone` (path) - Numéro de téléphone (String)

**Réponse :**
```json
{
  "existe": true,
  "actif": true,
  "nomComplet": "Amadou Diallo",
  "photoUrl": "https://gesclient.com/photos/amadou.jpg"
}
```

### GET /api/external/appdaf/rechercher/{nci}

Recherche un NCI dans le système AppDAF.

**Paramètres :**
- `nci` (path) - Numéro de carte d'identité (String)

**Réponse :**
```json
{
  "existe": true,
  "valide": true,
  "nomComplet": "Amadou Diallo",
  "typePiece": "CNI",
  "pieceRectoUrl": "https://appdaf.com/pieces/123_recto.jpg",
  "pieceVersoUrl": "https://appdaf.com/pieces/123_verso.jpg"
}
```

### POST /api/external/upload/photo-client/{clientId}

Upload la photo d'un client vers Cloudinary.

**Paramètres :**
- `clientId` (path) - ID du client (Long)
- `file` (form-data) - Fichier image

**Réponse :**
```json
{
  "message": "Photo uploadée avec succès",
  "photoUrl": "https://cloudinary.com/maxit221/clients/photos/client_1_photo.jpg"
}
```

### POST /api/external/upload/piece-identite/{clientId}

Upload une pièce d'identité vers Cloudinary.

**Paramètres :**
- `clientId` (path) - ID du client (Long)
- `file` (form-data) - Fichier image
- `type` (form-data) - Type de pièce ("recto" ou "verso")

**Réponse :**
```json
{
  "message": "Pièce d'identité uploadée avec succès",
  "pieceUrl": "https://cloudinary.com/maxit221/clients/pieces/client_1_piece_recto.jpg",
  "type": "recto"
}
```

---

## Gestion des Erreurs

### Erreurs de Validation

```json
{
  "error": "Le nom est obligatoire"
}
```

### Erreurs Métier

```json
{
  "error": "Solde insuffisant pour effectuer le transfert"
}
```

### Erreurs Système

```json
{
  "error": "Erreur lors de la connexion à la base de données"
}
```

---

## Exemples d'Usage

### Scénario Complet : Création d'un Client et Transfert

1. **Créer un client :**
```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Diallo",
    "prenom": "Amadou",
    "telephone": "+221771234567",
    "nci": "1234567890123",
    "adresse": "Dakar, Sénégal"
  }'
```

2. **Créer un compte principal :**
```bash
curl -X POST http://localhost:8080/api/comptes/principal \
  -H "Content-Type: application/json" \
  -d '{"clientId": 1}'
```

3. **Effectuer un dépôt :**
```bash
curl -X POST http://localhost:8080/api/transactions/depot \
  -H "Content-Type: application/json" \
  -d '{
    "compteId": 1,
    "montant": 50000.00,
    "description": "Dépôt initial"
  }'
```

4. **Créer un sous-compte :**
```bash
curl -X POST http://localhost:8080/api/comptes/sous-compte \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1,
    "comptePrincipalId": 1
  }'
```

5. **Effectuer un transfert interne :**
```bash
curl -X POST http://localhost:8080/api/transactions/transfert-interne \
  -H "Content-Type: application/json" \
  -d '{
    "compteSourceId": 1,
    "compteDestinationId": 2,
    "montant": 15000.00,
    "description": "Épargne",
    "fraisPrisEnCharge": false
  }'
```

---

## Limites et Contraintes

### Limites Techniques
- Taille maximum des fichiers uploadés : 10 MB
- Timeout des requêtes : 30 secondes
- Nombre maximum de transactions par page : 100

### Contraintes Métier
- Un client ne peut avoir qu'un seul compte principal
- Les transferts externes sont limités à 1,000,000 FCFA par jour
- Les codes d'authentification expirent après 5 minutes
- Les demandes d'annulation ne peuvent être traitées que dans les 7 jours

### Sécurité
- Toutes les communications doivent utiliser HTTPS en production
- Les mots de passe doivent respecter la politique de sécurité
- Les tentatives de connexion sont limitées à 5 par minute

---

Cette documentation couvre tous les aspects techniques de l'API Maxit-221. Pour des questions spécifiques ou des cas d'usage particuliers, consultez l'équipe de développement.

