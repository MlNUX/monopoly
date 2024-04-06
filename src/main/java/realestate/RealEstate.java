package realestate;

import playground.Street;

public abstract class RealEstate {

    private final Street street;

    RealEstate(Street street){
        this.street = street;
    }

    public Street getStreet() {
        return street;
    }
}
