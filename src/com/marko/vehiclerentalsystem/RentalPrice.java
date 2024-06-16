package com.marko.vehiclerentalsystem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalPrice {
    //static final rates for easier price updating
    //rental rates
    public static final byte DAILY_RATE_CAR = 20;
    public static final byte WEEKLY_RATE_CAR = 15;
    public static final byte DAILY_RATE_MOTO = 15;
    public static final byte WEEKLY_RATE_MOTO = 10;
    public static final byte DAILY_RATE_VAN = 50;
    public static final byte WEEKLY_RATE_VAN = 40;
    // Insurance rates in %
    public static final BigDecimal INSURANCE_CAR = BigDecimal.valueOf(0.01);
    public static final BigDecimal INSURANCE_MOTO = BigDecimal.valueOf(0.02);
    public static final BigDecimal INSURANCE_VAN = BigDecimal.valueOf(0.03);
    public static final byte HIGH_SAFETY_RATING = -10;
    public static final byte LOW_RIDER_AGE = 20;
    public static final byte HIGH_DRIVER_YOE = -15;

    private BigDecimal rentalRate;          //rent per day
    private byte insuranceVar;              //safety rating/rider age/driver yoe
    private BigDecimal insurancePerDay;     //insurance rate x days
    private BigDecimal insuranceAdjPerDay;  //adjustment per day
    private BigDecimal insuranceCost;
    private BigDecimal insuranceDiscount;
    private BigDecimal insuranceCostPerDay;
    private BigDecimal rentCost;
    private BigDecimal rentalDiscount;


    public BigDecimal calcRentalPrice(Vehicle vehicle, LocalDate rentDate, LocalDate expectedReturnDate, LocalDate actualReturnDate, Byte additionalVar) {
        byte rentalDays = (byte) ChronoUnit.DAYS.between(rentDate, expectedReturnDate);
        byte elapsedDays = (byte) ChronoUnit.DAYS.between(rentDate, actualReturnDate);
        byte remainingDays = (byte) ChronoUnit.DAYS.between(actualReturnDate, expectedReturnDate);

        rentalRate = BigDecimal.valueOf(getRentRate(vehicle.getType(), rentalDays)).setScale(2, RoundingMode.HALF_UP); //daily rent rate for vehicle type
        BigDecimal insuranceRate = getInsuranceRate(vehicle.getType());    //daily insurance rate for vehicle type
        insuranceVar = getInsuranceVar(vehicle, additionalVar);       //insurance modifier depending on safety rating/rider age/driver yoe

        rentCost = calcRentCost(rentalRate, elapsedDays, remainingDays); //total with discount=full price for elapsed, half remaining
        rentalDiscount = calcRentDiscount(rentalRate, rentalDays, rentCost); //full price - rentCost
        insuranceCost = calcInsuranceCost(vehicle, insuranceRate, insuranceVar, elapsedDays); //price only for elapsed
        insuranceCostPerDay = insuranceCost.divide(BigDecimal.valueOf(elapsedDays), 2, RoundingMode.HALF_UP); //insurance cost per day WITH discount
        insuranceDiscount = calcInsuranceDiscount(insuranceCostPerDay, insuranceCost, rentalDays); // full cost - elapsed

        return rentCost.add(insuranceCost).setScale(2, RoundingMode.HALF_UP);
    }

    protected byte getRentRate(VehicleType vehicleTypeRate, byte rentalDays) {
        return switch (vehicleTypeRate) {
            case CAR -> rentalDays > 7 ? WEEKLY_RATE_CAR : DAILY_RATE_CAR;
            case MOTORCYCLE -> rentalDays > 7 ? WEEKLY_RATE_MOTO : DAILY_RATE_MOTO;
            case CARGO_VAN -> rentalDays > 7 ? WEEKLY_RATE_VAN : DAILY_RATE_VAN;
        };
    }

    protected BigDecimal getInsuranceRate(VehicleType vehicleType) {
        return switch (vehicleType) {
            case CAR -> INSURANCE_CAR;
            case MOTORCYCLE -> INSURANCE_MOTO;
            case CARGO_VAN -> INSURANCE_VAN;
        };
    }

    private byte getInsuranceVar(Vehicle vehicle, Byte additionalVar) {
        return switch (vehicle.getType()) {
            case CAR -> ((Car) vehicle).getSafetyRating() >= 4 ? HIGH_SAFETY_RATING : 0;
            case MOTORCYCLE -> additionalVar != null && additionalVar < 25 ? LOW_RIDER_AGE : 0;
            case CARGO_VAN -> additionalVar != null && additionalVar > 5 ? HIGH_DRIVER_YOE : 0;
        };
    }

    private BigDecimal calcRentCost(BigDecimal rentRate, byte elapsedDays, byte remainingDays) {
        BigDecimal rateElapsed = rentRate.multiply(BigDecimal.valueOf(elapsedDays));
        BigDecimal rateRemaining = rentRate.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(remainingDays));
        return rateElapsed.add(rateRemaining).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcRentDiscount(BigDecimal rentalRate, byte rentDays, BigDecimal rentCostTotal) {
        return rentalRate.multiply(BigDecimal.valueOf(rentDays)).subtract(rentCostTotal).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcInsuranceCost(Vehicle vehicle, BigDecimal insuranceRate, byte insuranceVar, byte elapsedDays) {
        insurancePerDay = vehicle.getValue().multiply(insuranceRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        insuranceAdjPerDay = (insurancePerDay.multiply(BigDecimal.valueOf(insuranceVar)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
        return insurancePerDay.add(insuranceAdjPerDay).multiply(BigDecimal.valueOf(elapsedDays));
    }

    private BigDecimal calcInsuranceDiscount(BigDecimal insuranceCostPerDay, BigDecimal insuranceCost, byte rentalDays) {
        return insuranceCostPerDay.multiply(BigDecimal.valueOf(rentalDays)).subtract(insuranceCost);
    }

    public BigDecimal getRentalRate() {
        return rentalRate;
    }

    public byte getInsuranceVar() {
        return insuranceVar;
    }

    public BigDecimal getInsurancePerDay() {
        return insurancePerDay;
    }

    public BigDecimal getInsuranceAdjPerDay() {
        return insuranceAdjPerDay;
    }

    public BigDecimal getRentalDiscount() {
        return rentalDiscount;
    }

    public BigDecimal getRentCost() {
        return rentCost;
    }

    public BigDecimal getInsuranceCost() {
        return insuranceCost;
    }

    public BigDecimal getInsuranceDiscount() {
        return insuranceDiscount;
    }

    public BigDecimal getInsuranceCostPerDay() {
        return insuranceCostPerDay;
    }
}