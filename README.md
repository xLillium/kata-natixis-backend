# Kata - Système de gestion de bibliothèque

Ce projet est le **backend** d'un Kata **fullstack** réalisé dans le cadre d'un entretien chez **Natixis**.

## Contexte & Objectif

L'objectif est de développer une petite application de gestion de bibliothèque, avec les fonctionnalités suivantes :

- **Lister tous les livres** (titre, auteur, ISBN, disponibilité).
- **Rechercher un livre** par titre ou auteur.
- **Emprunter un livre** (changer son statut de disponible à emprunté).
- **Retourner un livre** (changer son statut d'emprunté à disponible).
- **Ajouter un nouveau livre**.

## Prérequis

- **Java 17** 
- **Maven** 
 
## Lancement du projet

1. **Cloner** le dépôt :
   ```bash
   git clone https://github.com/xLillium/kata-natixis-backend.git
   cd kata-natixis-backend

2. **Compiler** et **lancer** l'application :
    ```bash
    mvn clean install
    mvn spring-boot:run

L'application démarre par défaut sur http://localhost:8080
