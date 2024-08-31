package com.PjGl.pjgl.Model;

import java.util.Base64;

import org.springframework.data.annotation.Id;

public class Admin {
	
	@Id
	private String id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private byte[] image; // Image stockée en tant que tableau de bytes

    private String role; // Assurez-vous que cet attribut existe et est bien mappé
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImageBase64() {
        return this.image != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(this.image) : null;
    }
    public void setImage(byte[] image) {
	        this.image = image;
	}
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	 public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }
	public Admin() {
		
	}
    
    

}
