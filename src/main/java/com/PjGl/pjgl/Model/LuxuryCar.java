package com.PjGl.pjgl.Model;

public class LuxuryCar extends Voiture {
    private String luxuryLevel;

    public LuxuryCar() {
        super();
        this.luxuryLevel = "High"; // Par défaut, le niveau de luxe est élevé
    }

    public String getLuxuryLevel() {
        return luxuryLevel;
    }

    public void setLuxuryLevel(String luxuryLevel) {
        this.luxuryLevel = luxuryLevel;
    }
}
