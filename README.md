# Vehicle Rental System


The Vehicle Rental System is a Java application designed to manage vehicle rentals, calculate rental prices, and generate invoices for customers.

## Features
- **Vehicle Management**: Add and manage various types of vehicles.
- **Rental Price Calculation**: Calculate rental prices based on vehicle type, rental duration, and additional variables such as safety ratings, rider age, and driver experience.
- **Invoice Generation**: Generate detailed invoices for customers, including rental costs, insurance costs, and any applicable discounts or additions.

## Project Structure
The project consists of the following classes:

- `VehicleRentalSystem`: The main class that initializes and runs the example rentals.
- `Vehicle`: Represents a vehicle with fields for vehicle type, brand, mode and value.
- `Car`: A subclass of `Vehicle` representing a car, with an additional attribute for safety rating.
- `VehicleType`: Enum for vehicle types.
- `Invoice`: Handles the creation and printing of invoices for rentals.
- `RentalPrice`: Contains the logic for calculating rental and insurance prices based on various variables.


## How to Run
1. Clone the repository
2. Navigate to file and run with IDE
OR
Open cmd:
3. cd /path/to/project/src
4. javac com/marko/vehiclerentalsystem/*.java
5. java com.marko.vehiclerentalsystem.VehicleRentalSystem
