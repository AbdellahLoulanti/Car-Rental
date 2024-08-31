package com.PjGl.pjgl.Model;

public class SuvCar extends Voiture {
    private boolean fourWheelDrive;

    public SuvCar() {
        super();
        this.fourWheelDrive = true; // Par défaut, les SUV ont une transmission intégrale
    }

    public boolean isFourWheelDrive() {
        return fourWheelDrive;
    }

    public void setFourWheelDrive(boolean fourWheelDrive) {
        this.fourWheelDrive = fourWheelDrive;
    }
}
