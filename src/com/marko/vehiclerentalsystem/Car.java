package com.marko.vehiclerentalsystem;

import java.math.BigDecimal;

public class Car extends Vehicle {
    private final Byte safetyRating;

    public Car(String brand, String model, BigDecimal value, Byte safetyRating) {
        super(VehicleType.CAR, brand, model, value);
        if (safetyRating == null || safetyRating < 1 || safetyRating > 5) {
            throw new IllegalArgumentException("Safety rating must be between 1 and 5");
        }
        this.safetyRating = safetyRating;
    }

    public Byte getSafetyRating() {
        return safetyRating;
    }

}
