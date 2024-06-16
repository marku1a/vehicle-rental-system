package com.marko.vehiclerentalsystem;

import java.math.BigDecimal;

public class Vehicle {
    private final VehicleType type;
    private final String brand;
    private final String model;
    private final BigDecimal value;

    public Vehicle(VehicleType type, String brand, String model, BigDecimal value) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.value = value;
    }

    public VehicleType getType() {
        return this.type;
    }

    public BigDecimal getValue() {
        return this.value;
    }


    @Override
    public String toString() {
        return brand + " " + model;
    }
}