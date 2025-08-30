#!/bin/bash

# Script de déploiement pour Maxit-221
# Auteur: Manus AI
# Version: 1.0

set -e

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration par défaut
APP_NAME="maxit-221"
APP_VERSION="0.0.1-SNAPSHOT"
JAVA_OPTS="-Xms512m -Xmx1024m"
PROFILE="prod"
PORT="8080"

# Fonctions utilitaires
log_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

log_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

log_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Fonction d'aide
show_help() {
    cat << EOF
Usage: $0 [OPTIONS]

Options:
    -h, --help          Afficher cette aide
    -p, --profile       Profil Spring (dev|test|prod) [défaut: prod]
    -j, --java-opts     Options JVM [défaut: -Xms512m -Xmx1024m]
    --port              Port d'écoute [défaut: 8080]
    --build-only        Compiler seulement, ne pas démarrer
    --stop              Arrêter l'application
    --status            Vérifier le statut de l'application
    --logs              Afficher les logs de l'application

Exemples:
    $0                          # Déploiement standard
    $0 -p dev                   # Déploiement en mode développement
    $0 --build-only             # Compilation seulement
    $0 --stop                   # Arrêter l'application
    $0 --status                 # Vérifier le statut

EOF
}

# Vérifier les prérequis
check_prerequisites() {
    log_info "Vérification des prérequis..."
    
    # Vérifier Java
    if ! command -v java &> /dev/null; then
        log_error "Java n'est pas installé"
        exit 1
    fi
    
    # Vérifier Maven
    if ! command -v mvn &> /dev/null; then
        log_error "Maven n'est pas installé"
        exit 1
    fi
    
    # Vérifier PostgreSQL (optionnel)
    if command -v psql &> /dev/null; then
        log_success "PostgreSQL détecté"
    else
        log_warning "PostgreSQL non détecté - assurez-vous qu'il est accessible"
    fi
    
    log_success "Prérequis vérifiés"
}

# Compiler l'application
build_application() {
    log_info "Compilation de l'application..."
    
    # Nettoyer et compiler
    mvn clean compile -q
    if [ $? -eq 0 ]; then
        log_success "Compilation réussie"
    else
        log_error "Échec de la compilation"
        exit 1
    fi
    
    # Exécuter les tests
    log_info "Exécution des tests..."
    mvn test -q
    if [ $? -eq 0 ]; then
        log_success "Tests réussis"
    else
        log_error "Échec des tests"
        exit 1
    fi
    
    # Générer le JAR
    log_info "Génération du JAR..."
    mvn package -DskipTests -q
    if [ $? -eq 0 ]; then
        log_success "JAR généré: target/${APP_NAME}-${APP_VERSION}.jar"
    else
        log_error "Échec de la génération du JAR"
        exit 1
    fi
}

# Vérifier le statut de l'application
check_status() {
    PID_FILE="$APP_NAME.pid"
    
    if [ -f "$PID_FILE" ]; then
        PID=$(cat "$PID_FILE")
        if ps -p "$PID" > /dev/null 2>&1; then
            log_success "Application en cours d'exécution (PID: $PID)"
            return 0
        else
            log_warning "Fichier PID trouvé mais processus non actif"
            rm -f "$PID_FILE"
            return 1
        fi
    else
        log_info "Application non démarrée"
        return 1
    fi
}

# Arrêter l'application
stop_application() {
    log_info "Arrêt de l'application..."
    
    PID_FILE="$APP_NAME.pid"
    
    if [ -f "$PID_FILE" ]; then
        PID=$(cat "$PID_FILE")
        if ps -p "$PID" > /dev/null 2>&1; then
            kill "$PID"
            
            # Attendre l'arrêt
            for i in {1..30}; do
                if ! ps -p "$PID" > /dev/null 2>&1; then
                    break
                fi
                sleep 1
            done
            
            # Forcer l'arrêt si nécessaire
            if ps -p "$PID" > /dev/null 2>&1; then
                log_warning "Arrêt forcé de l'application"
                kill -9 "$PID"
            fi
            
            rm -f "$PID_FILE"
            log_success "Application arrêtée"
        else
            log_warning "Processus non trouvé, suppression du fichier PID"
            rm -f "$PID_FILE"
        fi
    else
        log_info "Application non démarrée"
    fi
}

# Démarrer l'application
start_application() {
    log_info "Démarrage de l'application..."
    
    # Vérifier si l'application est déjà en cours
    if check_status > /dev/null 2>&1; then
        log_error "L'application est déjà en cours d'exécution"
        exit 1
    fi
    
    # Créer le répertoire de logs
    mkdir -p logs
    
    # Variables d'environnement
    export SPRING_PROFILES_ACTIVE="$PROFILE"
    export SERVER_PORT="$PORT"
    
    # Démarrer l'application en arrière-plan
    JAR_FILE="target/${APP_NAME}-${APP_VERSION}.jar"
    
    if [ ! -f "$JAR_FILE" ]; then
        log_error "Fichier JAR non trouvé: $JAR_FILE"
        log_info "Exécutez d'abord la compilation avec --build-only"
        exit 1
    fi
    
    nohup java $JAVA_OPTS -jar "$JAR_FILE" > logs/application.log 2>&1 &
    PID=$!
    
    # Sauvegarder le PID
    echo $PID > "$APP_NAME.pid"
    
    # Attendre le démarrage
    log_info "Attente du démarrage de l'application..."
    for i in {1..60}; do
        if curl -s "http://localhost:$PORT/actuator/health" > /dev/null 2>&1; then
            log_success "Application démarrée avec succès (PID: $PID)"
            log_info "URL: http://localhost:$PORT"
            log_info "Logs: tail -f logs/application.log"
            return 0
        fi
        sleep 2
    done
    
    log_error "Échec du démarrage de l'application"
    stop_application
    exit 1
}

# Afficher les logs
show_logs() {
    LOG_FILE="logs/application.log"
    
    if [ -f "$LOG_FILE" ]; then
        tail -f "$LOG_FILE"
    else
        log_error "Fichier de logs non trouvé: $LOG_FILE"
        exit 1
    fi
}

# Créer la base de données (optionnel)
setup_database() {
    log_info "Configuration de la base de données..."
    
    # Vérifier si PostgreSQL est accessible
    if ! command -v psql &> /dev/null; then
        log_warning "PostgreSQL non disponible, ignoré"
        return 0
    fi
    
    # Variables de base de données
    DB_NAME="maxit221"
    DB_USER="maxit_user"
    DB_PASSWORD="maxit_password"
    
    # Créer la base de données si elle n'existe pas
    if ! psql -lqt | cut -d \| -f 1 | grep -qw "$DB_NAME"; then
        log_info "Création de la base de données $DB_NAME..."
        createdb "$DB_NAME"
        log_success "Base de données créée"
    else
        log_info "Base de données $DB_NAME existe déjà"
    fi
}

# Fonction de déploiement complète
deploy() {
    log_info "Début du déploiement de $APP_NAME..."
    
    check_prerequisites
    setup_database
    build_application
    
    # Arrêter l'ancienne version si elle existe
    if check_status > /dev/null 2>&1; then
        log_info "Arrêt de l'ancienne version..."
        stop_application
    fi
    
    start_application
    
    log_success "Déploiement terminé avec succès!"
}

# Traitement des arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            show_help
            exit 0
            ;;
        -p|--profile)
            PROFILE="$2"
            shift 2
            ;;
        -j|--java-opts)
            JAVA_OPTS="$2"
            shift 2
            ;;
        --port)
            PORT="$2"
            shift 2
            ;;
        --build-only)
            check_prerequisites
            build_application
            exit 0
            ;;
        --stop)
            stop_application
            exit 0
            ;;
        --status)
            check_status
            exit 0
            ;;
        --logs)
            show_logs
            exit 0
            ;;
        *)
            log_error "Option inconnue: $1"
            show_help
            exit 1
            ;;
    esac
done

# Déploiement par défaut
deploy

