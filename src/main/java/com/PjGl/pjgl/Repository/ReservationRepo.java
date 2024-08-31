package com.PjGl.pjgl.Repository;

import com.PjGl.pjgl.Model.Voiture;
import com.PjGl.pjgl.Model.reservation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends MongoRepository<reservation, String> {
	
	 // Trouver des réservations par statut
    List<reservation> findByStatus(String status);
 // Trouver des réservations par statut
    List<reservation> findByNomVoiture(String nomVoiture);
    
    // Trouver des réservations dans une plage de dates
    
    // Trouver des réservations pour un client spécifique
    List<reservation> findByClientId(String clientId);
    
	List<reservation> findOneByNomVoiture(String nomVoiture);

	

	
}
