# Projet de Location de Voitures

## Description du Projet

Ce projet est une application de gestion de location de voitures développée avec Spring Boot. L'application permet de gérer les voitures disponibles à la location, de gérer les utilisateurs (managers, admins, clients), et de traiter les réservations. Trois catégories de voitures sont disponibles : `Economy`, `Luxury`, et `SUV`.

## Fonctionnalités

### Pour les Managers
- Ajouter, modifier, ou supprimer des voitures.
- Ajouter, modifier, ou supprimer des clients.
- Accepter ou refuser les réservations des clients.
- Générer les contrats de location.

### Pour les Admins
- Ajouter, modifier, ou supprimer des managers.
- Accéder à toutes les fonctionnalités des managers.

### Pour les Clients
- Rechercher des voitures disponibles.
- Consulter les détails des voitures.
- Consulter et modifier leur profil.
- Réserver des voitures disponibles.

## Design Patterns Utilisés

### 1. **Chain of Responsibility** pour le Login
Ce design pattern est utilisé pour gérer le processus de connexion des utilisateurs. Il permet de définir une chaîne de traitements (managers, admins, etc.) qui peuvent tenter de se connecter.

### 2. **Observer** pour les Notifications par Email
Le design pattern Observer est utilisé pour envoyer des emails aux clients lorsqu'une nouvelle voiture est ajoutée à la liste des voitures disponibles. Les clients intéressés par les nouveautés peuvent s'abonner pour recevoir ces notifications.

### 3. **Factory** pour la Création des Voitures
Le design pattern Factory est utilisé pour créer des instances de voitures selon leur type (`Economy`, `Luxury`, `SUV`). Cela permet de centraliser la logique de création des objets voiture dans une classe dédiée.

## Structure du Projet
#### * src/main/java : Contient le code source Java.
- Chain : Package pour le design pattern Chain of Responsibility.
- com.PjGl.pjgl.config : Configuration de l'application.
- com.PjGl.pjgl.Controller : Contrôleurs REST pour gérer les requêtes HTTP.
- com.PjGl.pjgl.factory : Implémentation du design pattern Factory pour la création des voitures.
- com.PjGl.pjgl.Model : Modèles de données pour les entités (Voiture, EconomyCar, LuxuryCar, SuvCar).
- com.PjGl.pjgl.Observer : Implémentation du design pattern Observer pour les notifications par email.
- com.PjGl.pjgl.Repository : Interfaces pour accéder aux données stockées en base.
- com.PjGl.pjgl.service : Services métiers pour la logique applicative.
- com.PjGl.pjgl.startup : Classes de démarrage et de configuration initiale.
#### * src/main/resources : Ressources statiques et fichiers de configuration.
#### * static : Fichiers statiques (CSS, JS).
#### * templates : Templates Thymeleaf pour le rendu des vues.
#### * application.properties : Fichier de configuration de l'application.
#### * src/test/java : Tests unitaires et d'intégration.
#### * frontend : Contient les fichiers front-end de l'application (si applicable).
#### * Conception : Documents de conception du projet.

## Prérequis
- Java 17 (JDK)
- Maven pour la gestion des dépendances
- Spring Boot pour l'exécution de l'application
- MySQL ou autre base de données relationnelle pour stocker les données

## Installation et Lancement
1. Clonez le dépôt du projet :
```
git clone https://votre-repo.git
```
2.Accédez au répertoire du projet :
```
cd nom-du-projet

```
3. Compilez et lancez l'application :
```
mvn clean install
mvn spring-boot:run
```
4.L'application sera accessible à l'adresse suivante : http://localhost:8080

## Contribution
Les contributions sont les bienvenues ! Veuillez ouvrir une pull request pour toute nouvelle fonctionnalité ou correction de bug.

## License
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.
