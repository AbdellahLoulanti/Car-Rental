package com.PjGl.pjgl.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reservations")
public class reservation {

    @Id
    private String id;
    private String clientId; // L'ID du client qui a fait la réservation
    private String voitureId; // L'ID de la voiture réservée
    private String nomClient;
    private String nomVoiture;
    private String statut = "Afficher";

    private LocalDateTime dateDebut; // Date et heure du début de la location
    private LocalDateTime dateFin; // Date et heure de la fin de la location
    private String status; // Statut de la réservation (ex. "En attente", "Confirmée", "Annulée")
    
    // Ajout des nouveaux champs
    private double remise;

    public reservation() {
        // Constructeur par défaut nécessaire pour Spring Data
    }

    // Getters et setters pour les nouveaux champs
   

   

   

    public double getRemise() {
        return remise;
    }

    public void setRemise(double remise) {
        this.remise = remise;
    }

  

    // Autres getters et setters existants
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getVoitureId() {
        return voitureId;
    }

    public void setVoitureId(String voitureId) {
        this.voitureId = voitureId;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNomVoiture() {
        return nomVoiture;
    }

    public void setNomVoiture(String nomVoiture) {
        this.nomVoiture = nomVoiture;
    }
}
