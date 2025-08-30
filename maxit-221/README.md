# Maxit-221 - Application de Simulation Bancaire

## Description

Maxit-221 est une application de simulation bancaire développée avec Spring Boot et PostgreSQL. Elle permet de gérer des comptes clients, effectuer des transactions, gérer des transferts avec calcul de frais, et traiter des demandes d'annulation.

## Fonctionnalités Principales

### Gestion des Clients
- Création de compte client (nom, prénom, téléphone, NCI, adresse)
- Recherche de numéros dans GesClient pour récupérer les photos
- Recherche de NCI dans AppDAF pour récupérer les pièces d'identité
- Génération et vérification de codes d'authentification
- Stockage des photos et pièces d'identité via Cloudinary

### Gestion des Comptes
- Création de comptes principaux
- Création de sous-comptes
- Blocage/déblocage de comptes (permanent ou temporaire)
- Changement de sous-compte en compte principal
- Gestion des soldes

### Transactions
- Dépôts et retraits
- Transferts internes (gratuits vers les sous-comptes)
- Transferts externes (avec frais de 0.08%)
- Transferts planifiés et récurrents
- Demandes d'achat de codes Woyofal
- Historique des transactions avec filtrage

### Demandes d'Annulation
- Création de demandes d'annulation de transferts
- Validation des demandes (vérification des fonds disponibles)
- Traitement des annulations avec remboursement

## Architecture Technique

### Technologies Utilisées
- **Backend**: Spring Boot 3.2.0
- **Base de données**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Sécurité**: Spring Security + JWT
- **Stockage fichiers**: Cloudinary
- **Build**: Maven
- **Tests**: JUnit 5

### Structure du Projet
```
src/
├── main/
│   ├── java/com/maxit/maxit221/
│   │   ├── config/          # Configuration (CORS, sécurité)
│   │   ├── controller/      # Contrôleurs REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── external/       # Services externes (GesClient, AppDAF, Cloudinary)
│   │   ├── model/          # Entités JPA
│   │   ├── repository/     # Repositories Spring Data
│   │   ├── service/        # Services métier
│   │   └── Maxit221Application.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/maxit/maxit221/
        └── Maxit221ApplicationTests.java
```

## Configuration

### Base de Données
Configurez PostgreSQL dans `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/maxit221
spring.datasource.username=maxit_user
spring.datasource.password=maxit_password
```

### Services Externes
```properties
# GesClient
gesclient.base-url=http://localhost:8081/api

# AppDAF
appdaf.base-url=http://localhost:8082/api

# Cloudinary
cloudinary.cloud-name=your-cloud-name
cloudinary.api-key=your-api-key
cloudinary.api-secret=your-api-secret
```

### Frais de Transfert
```properties
transfer.external.fee-rate=0.08
```

## Installation et Démarrage

### Prérequis
- Java 11 ou supérieur
- Maven 3.6+
- PostgreSQL 12+

### Installation
1. Cloner le projet
2. Configurer la base de données PostgreSQL
3. Modifier `application.properties` avec vos paramètres
4. Compiler le projet:
   ```bash
   mvn clean compile
   ```
5. Lancer les tests:
   ```bash
   mvn test
   ```
6. Démarrer l'application:
   ```bash
   mvn spring-boot:run
   ```

L'application sera accessible sur `http://localhost:8080`

## API Endpoints

### Clients
- `GET /api/clients` - Liste tous les clients
- `POST /api/clients` - Créer un nouveau client
- `GET /api/clients/{id}` - Récupérer un client par ID
- `GET /api/clients/telephone/{telephone}` - Rechercher par téléphone
- `GET /api/clients/nci/{nci}` - Rechercher par NCI
- `POST /api/clients/{id}/generer-code` - Générer un code d'authentification
- `POST /api/clients/verifier-code` - Vérifier un code d'authentification

### Comptes
- `GET /api/comptes` - Liste tous les comptes
- `POST /api/comptes/principal` - Créer un compte principal
- `POST /api/comptes/sous-compte` - Créer un sous-compte
- `GET /api/comptes/client/{clientId}` - Comptes d'un client
- `PUT /api/comptes/{id}/bloquer` - Bloquer un compte
- `PUT /api/comptes/{id}/debloquer` - Débloquer un compte
- `PUT /api/comptes/{id}/changer-en-principal` - Changer en compte principal

### Transactions
- `GET /api/transactions` - Liste toutes les transactions
- `POST /api/transactions/depot` - Effectuer un dépôt
- `POST /api/transactions/retrait` - Effectuer un retrait
- `POST /api/transactions/transfert-interne` - Transfert interne
- `POST /api/transactions/transfert-externe` - Transfert externe
- `POST /api/transactions/transfert-planifie` - Planifier un transfert
- `GET /api/transactions/compte/{compteId}` - Transactions d'un compte
- `GET /api/transactions/compte/{compteId}/dernieres` - 10 dernières transactions

### Services Externes
- `GET /api/external/gesclient/rechercher/{telephone}` - Rechercher dans GesClient
- `GET /api/external/appdaf/rechercher/{nci}` - Rechercher dans AppDAF
- `POST /api/external/upload/photo-client/{clientId}` - Upload photo client
- `POST /api/external/upload/piece-identite/{clientId}` - Upload pièce d'identité

## Modèle de Données

### Entités Principales
- **Client**: Informations client (nom, prénom, téléphone, NCI, adresse)
- **Compte**: Comptes bancaires (principal/sous-compte, solde, statut)
- **Transaction**: Transactions (dépôt, retrait, transfert, paiement)
- **DemandeAnnulation**: Demandes d'annulation de transferts

### Relations
- Un Client peut avoir plusieurs Comptes
- Un Compte principal peut avoir plusieurs Sous-comptes
- Un Compte peut avoir plusieurs Transactions (source ou destination)
- Une Transaction peut avoir une DemandeAnnulation

## Règles Métier

### Frais de Transfert
- Transferts vers sous-comptes du même client: **Gratuit**
- Transferts internes entre clients: **1% du montant**
- Transferts externes: **0.08% du montant**
- Les frais peuvent être pris en charge par l'expéditeur

### Annulations
- Seul le propriétaire du compte source peut demander l'annulation
- L'annulation n'est possible que si les fonds sont disponibles sur le compte destination
- Les frais ne sont pas remboursés lors de l'annulation
- Les transferts externes ne peuvent être annulés que dans les 24h

### Blocage de Comptes
- Un compte bloqué ne peut effectuer aucune transaction
- Le blocage peut être permanent ou temporaire
- Un sous-compte bloqué ne peut pas être changé en compte principal

## Tests

L'application inclut des tests unitaires et d'intégration. Pour les exécuter:
```bash
mvn test
```

## Sécurité

- Authentification par JWT
- Validation des données d'entrée
- Gestion des erreurs sécurisée
- CORS configuré pour les API

## Déploiement

Pour déployer en production:
1. Configurer une base de données PostgreSQL
2. Mettre à jour les paramètres de connexion
3. Configurer les services externes (GesClient, AppDAF, Cloudinary)
4. Générer le JAR:
   ```bash
   mvn clean package
   ```
5. Déployer le JAR généré

## Support

Pour toute question ou problème, veuillez consulter la documentation ou contacter l'équipe de développement.

