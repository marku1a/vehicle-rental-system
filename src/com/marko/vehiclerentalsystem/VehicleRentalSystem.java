package com.marko.vehiclerentalsystem;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.marko.vehiclerentalsystem.VehicleType.CARGO_VAN;
import static com.marko.vehiclerentalsystem.VehicleType.MOTORCYCLE;


public class VehicleRentalSystem {

    private final RentalPrice rentalPrice;

    public VehicleRentalSystem(RentalPrice rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public static void main(String[] args) {
        RentalPrice rentalPrice = new RentalPrice();
        VehicleRentalSystem vehicleRentalSystem = new VehicleRentalSystem(rentalPrice);
        vehicleRentalSystem.init();

    }

    void init() {
        Vehicle vehicle1 = new Car("Mitsubishi", "Mirage", BigDecimal.valueOf(15000.00), (byte) 3);
        Vehicle vehicle2 = new Vehicle(CARGO_VAN, "Citroen", "Jumper", BigDecimal.valueOf(20000.00));
        Vehicle vehicle3 = new Vehicle(MOTORCYCLE, "Yamaha", "MT-07", BigDecimal.valueOf(10000.00));
        Vehicle vehicle4 = new Car("Toyota", "Corolla", BigDecimal.valueOf(25235.00), (byte) 5);


        LocalDate rentDate1 = LocalDate.of(2024, 6, 3);
        LocalDate expectedReturnDate1 = LocalDate.of(2024, 6, 13);
        LocalDate actualReturnDate1 = LocalDate.of(2024, 6, 13);
        LocalDate rentDate2 = LocalDate.of(2024, 6, 3);
        LocalDate expectedReturnDate2 = LocalDate.of(2024, 6, 18);
        LocalDate actualReturnDate2 = LocalDate.of(2024, 6, 13);
        LocalDate rentDate3 = LocalDate.of(2024, 6, 3);
        LocalDate expectedReturnDate3 = LocalDate.of(2024, 6, 13);
        LocalDate actualReturnDate3 = LocalDate.of(2024, 6, 13);
        LocalDate rentDate4 = LocalDate.of(2024, 6, 3);
        LocalDate expectedReturnDate4 = LocalDate.of(2024, 6, 13);
        LocalDate actualReturnDate4 = LocalDate.of(2024, 6, 10);

        System.out.println("\nCar value: 15000, with safety rating of 3: \n");
        Invoice invoice1 = new Invoice("Michael Myers", rentalPrice, vehicle1, rentDate1, expectedReturnDate1, actualReturnDate1);
        invoice1.printInvoice();
        System.out.println("\nCargo van value: 20000, with driver experience 8 years: \n");
        Invoice invoice2 = new Invoice("Michael Scott", rentalPrice, vehicle2, rentDate2, expectedReturnDate2, actualReturnDate2, (byte) 8);
        invoice2.printInvoice();
        System.out.println("\nMotorcycle value: 10000, with raider age of 20: \n");
        Invoice invoice3 = new Invoice("Peter Parker", rentalPrice, vehicle3, rentDate3, expectedReturnDate3, actualReturnDate3, (byte) 20);
        invoice3.printInvoice();
        System.out.println("\nCar value: 25235, with safety rating of 5 and early return: \n");
        Invoice invoice4 = new Invoice("John Wick", rentalPrice, vehicle4, rentDate4, expectedReturnDate4, actualReturnDate4);
        invoice4.printInvoice();
    }
}