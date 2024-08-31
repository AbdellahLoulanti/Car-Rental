package com.PjGl.pjgl.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PjGl.pjgl.Model.Manager;

public interface ManagerRepo extends MongoRepository<Manager, String>{

	Manager findByEmailAndPassword(String email, String password);
}

