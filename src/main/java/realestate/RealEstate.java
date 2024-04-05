package main.java.realestate;


import main.java.playground.Street;

public abstract class RealEstate {

    private final Street street;

    RealEstate(Street street){
        this.street = street;
    }

    public Street getStreet() {
        return street;
    }
}
