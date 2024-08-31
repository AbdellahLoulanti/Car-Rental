package com.PjGl.pjgl.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PjGl.pjgl.Model.Client;
import com.PjGl.pjgl.Model.reservation;

public interface ClientRepo extends MongoRepository<Client, String> {
    
    Client findByEmailAndPassword(String email, String password);
    
    Client findOneByCin(String cin);
    
    List<Client> findByCin(String cin);

    List<Client> findByNom(String nom);
    
    List<Client> findByPrenom(String prenom);
}
