package com.PjGl.pjgl.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PjGl.pjgl.Model.Voiture;
import com.PjGl.pjgl.Model.reservation;

public interface VoitureRepo  extends MongoRepository<Voiture, String>{

	Voiture findOneByMatricule(String matricule);
	
    List<Voiture> findByMatricule(String matricule);

    Optional<Voiture> findByMarque(String marque);
    
    Optional<Voiture> findByMarqueAndModele(String marque, String modele);


}

