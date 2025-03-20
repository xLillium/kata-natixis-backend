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

## Endpoints disponibles

| Méthode   | Endpoint                                   | Description                                        |
|-----------|--------------------------------------------|----------------------------------------------------|
| **GET**   | `/api/books`                               | Liste tous les livres                              |
| **GET**   | `/api/books?title={title}&author={author}` | Recherche un livre par titre et/ou auteur          |
| **POST**  | `/api/books`                               | Ajoute un nouveau livre                            |
| **PATCH** | `/api/books/{bookId}`                      | Emprunte ou retourne un livre (modifie `borrowed`) |

### **Exemples d'utilisation**

#### **Rechercher un livre par titre**

   ```bash
   curl -X GET "http://localhost:8080/api/books?title=1984"
   ``` 

#### **Emprunter un livre**

   ```bash
   curl -X PATCH "http://localhost:8080/api/books/1" -H "Content-Type: application/json" -d '{"borrowed": true}'   ``` 
   ```

#### **Ajouter un livre**

   ```bash
   curl -X POST "http://localhost:8080/api/books" -H "Content-Type: application/json" -d '{
     "title": "Le Petit Prince",
     "author": "Antoine de Saint-Exupéry",
     "isbn": "9782070408504",
      "borrowed": false
   }'
   ```

## Base de données
La base de données est en mémoire (H2) et se réinitialise à chaque redémarrage.


## Postman Collection

Pour faciliter les tests de l'API, vous pouvez importer une collection Postman préconfigurée.

### **Téléchargement**
📌 **[Télécharger la collection Postman](https://github.com/xLillium/kata-natixis-backend/raw/main/Kata%20Natixis.postman_collection.json)**

### **Importer dans Postman**
1.  **Ouvrir Postman**  
2. Aller dans **Fichier → Importer**  
3. Sélectionner le fichier `Kata-Natixis.postman_collection.json`  
4. Tester les requêtes 