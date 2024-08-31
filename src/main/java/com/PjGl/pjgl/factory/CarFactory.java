package com.PjGl.pjgl.factory;


import com.PjGl.pjgl.Model.Voiture;

import com.PjGl.pjgl.Model.EconomyCar;
import com.PjGl.pjgl.Model.LuxuryCar;
import com.PjGl.pjgl.Model.SuvCar;

public class CarFactory {

    public Voiture createCar(String type) {
        switch (type.toLowerCase()) {
            case "economy":
                return new EconomyCar();
            case "luxury":
                return new LuxuryCar();
            case "suv":
                return new SuvCar();
            default:
                throw new IllegalArgumentException("Type de voiture inconnu : " + type);
        }
    }
}

