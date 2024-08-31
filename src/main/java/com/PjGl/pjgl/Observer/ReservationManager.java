package com.PjGl.pjgl.Observer;

import com.PjGl.pjgl.Model.Voiture;
import org.springframework.stereotype.Component;

@Component
public class ReservationManager extends Subject {
    // Ajoute une nouvelle voiture et notifie tous les observateurs
    public void addNewCar(Voiture voiture) {
        // Supposons ici que la logique d'ajout au système ou à la base de données a été implémentée
        notifyObservers("Une nouvelle voiture " + voiture.getMarque() + " modèle " + voiture.getModele() + " est maintenant disponible pour la location!");
    }
}
