package com.PjGl.pjgl.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.PjGl.pjgl.Model.Admin;

public interface AdminRepo extends MongoRepository<Admin, String>{

	Admin findByEmailAndPassword(String email, String password);
}
