package com.PjGl.pjgl.Model;


public class EconomyCar extends Voiture {
    private boolean ecoFriendly;

    public EconomyCar() {
        super();
        this.ecoFriendly = true; // Par défaut, les voitures économiques sont respectueuses de l'environnement
    }

    public boolean isEcoFriendly() {
        return ecoFriendly;
    }

    public void setEcoFriendly(boolean ecoFriendly) {
        this.ecoFriendly = ecoFriendly;
    }
}

