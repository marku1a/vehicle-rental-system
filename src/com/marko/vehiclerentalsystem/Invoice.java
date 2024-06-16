package com.marko.vehiclerentalsystem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.marko.vehiclerentalsystem.VehicleType.MOTORCYCLE;

public class Invoice {
    private final LocalDate rentDate;
    private final LocalDate expectedReturnDate;
    private final LocalDate actualReturnDate;
    private final String userFullName;
    private final RentalPrice rentalPrice;
    private final Vehicle vehicle;
    private final byte rentalDays;
    private final Byte additionalVar;

    public Invoice(String userFullName, RentalPrice rentalPrice, Vehicle vehicle, LocalDate rentDate, LocalDate expectedReturnDate, LocalDate actualReturnDate, Byte additionalVar) {
        this.userFullName = userFullName;
        this.rentalPrice = rentalPrice;
        this.vehicle = vehicle;
        this.rentDate = rentDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.rentalDays = (byte) ChronoUnit.DAYS.between(rentDate, expectedReturnDate);
        this.additionalVar = additionalVar != null ? additionalVar : ((Car) vehicle).getSafetyRating();
    }

    public Invoice(String userFullName, RentalPrice rentalPrice, Vehicle vehicle, LocalDate rentDate, LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        this(userFullName, rentalPrice, vehicle, rentDate, expectedReturnDate, actualReturnDate, null);
    }


    public void printInvoice() {
        byte remainingDays = (byte) ChronoUnit.DAYS.between(actualReturnDate, expectedReturnDate);
        byte elapsedDays = (byte) ChronoUnit.DAYS.between(rentDate, actualReturnDate);
        BigDecimal fullPrice = rentalPrice.calcRentalPrice(vehicle, rentDate, expectedReturnDate, actualReturnDate, additionalVar);
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" +
                "\nDate: " + actualReturnDate +
                "\nCustomer name: " + userFullName +
                "\nRented Vehicle: " + vehicle +
                "\n\nReservation start date: " + rentDate +
                "\nReservation end date: " + expectedReturnDate +
                "\nReserved rental days: " + rentalDays +
                "\n\nActual return date: " + actualReturnDate +
                "\nActual rental days: " + elapsedDays +
                "\n\nRental cost per day: $" + rentalPrice.getRentalRate() +
                "\nInitial insurance per day: $" + rentalPrice.getInsurancePerDay());

        if (rentalPrice.getInsuranceVar() != 0) {
            System.out.println("Insurance " + (vehicle.getType() == MOTORCYCLE ? "addition" : "discount")
                    + " per day: $" + rentalPrice.getInsuranceAdjPerDay().abs() +
                    "\nNew insurance per day: $" + rentalPrice.getInsuranceCostPerDay());
        }
        if (remainingDays != 0) {
            System.out.println("\nEarly return discount for rent: $" + rentalPrice.getRentalDiscount() +
                    "\nEarly return discount for insurance: $" + rentalPrice.getInsuranceDiscount());
        }
        System.out.println("\nTotal rent: $" + rentalPrice.getRentCost() +
                "\nTotal Insurance: $" + rentalPrice.getInsuranceCost() +
                "\nTotal cost: $" + fullPrice +
                "\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
