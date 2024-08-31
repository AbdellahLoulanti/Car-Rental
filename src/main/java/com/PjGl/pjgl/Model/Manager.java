package com.PjGl.pjgl.Model;

import java.util.Base64;

import com.vaadin.flow.component.template.Id;

public class Manager {
	
	@Id
	private String id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    
    private String statut = "Afficher";
    private byte[] image; // Image stockée en tant que tableau de bytes

    
    public Manager() {
    	
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", password="
				+ password + ", statut=" + statut + "]";
	}
	
	

}
