package com.PjGl.pjgl.Model;

import java.util.Base64;

public class Voiture {
	
	private String id;
	private String numero;
    private String marque;
    private String modele;
    private String anneeFab;
    private String matricule;
    private double prixLocation;
    private String dispo;
    private String statut = "Afficher";
    private byte[] image; // Image stockée en tant que tableau de bytes

    // Getters et setters
  
    
    public Voiture() {
    	
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public String getAnneeFab() {
		return anneeFab;
	}

	public void setAnneeFab(String anneeFab) {
		this.anneeFab = anneeFab;
	}
	
	public String getImageBase64() {
        return this.image != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(this.image) : null;
    }
    public void setImage(byte[] image) {
	        this.image = image;
	}
	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public double getPrixLocation() {
		return prixLocation;
	}

	public void setPrixLocation(double prixLocation) {
		this.prixLocation = prixLocation;
	}

	public String getDispo() {
		return dispo;
	}

	public void setDispo(String dispo) {
		this.dispo = dispo;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}


	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
    
    

}
