package dawid.transport;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Journey {
    private LocalDate dateOfDeparture ;
    private SimpleStringProperty nameOfDriver = new SimpleStringProperty("");
    private SimpleStringProperty placeDestination= new SimpleStringProperty("");
    private SimpleDoubleProperty exportPrice = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty importPrice= new SimpleDoubleProperty(0);;
    private SimpleDoubleProperty sum= new SimpleDoubleProperty(0);
    private SimpleDoubleProperty costs= new SimpleDoubleProperty(0);
    private SimpleDoubleProperty driverSalary= new SimpleDoubleProperty(0);
    private SimpleIntegerProperty departureKm = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty arrivalKm = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty km= new SimpleIntegerProperty(0);
    private SimpleDoubleProperty profit=new SimpleDoubleProperty(0);
    private SimpleStringProperty numberOfCar = new SimpleStringProperty("");
    private SimpleDoubleProperty euro = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty profitKm = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty profitKmEuro = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty fuel = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty fuelUsage = new SimpleDoubleProperty(0);




    //String dateOfDeparture,
    public Journey( LocalDate dateOfDeparture ,String nameOfDriver, String placeDestination,
                    double exportPrice, double importPrice, double sum, double costs, double driverSalary,
                    double profit,String numberOfCar, int departureKm, int arrivalKm, int km, double profitKm,double profitKmEuro, double fuel,double fuelUsage, double euro) {

        this.dateOfDeparture= dateOfDeparture;
        this.nameOfDriver.set(nameOfDriver);
        this.placeDestination.set(placeDestination);
        this.exportPrice.set(exportPrice);
        this.importPrice.set(importPrice);
        this.sum.set(sum);
        this.costs.set(costs);
        this.driverSalary.set(driverSalary);
        this.departureKm.set(departureKm);
        this.arrivalKm.set(arrivalKm);
        this.km.set(km);
        this.profit.set(profit);
        this.numberOfCar.set(numberOfCar);
        this.euro.set(euro);
        this.profitKm.set(profitKm);
        this.profitKmEuro.set(profitKmEuro);
        this.fuel.set(fuel);
        this.fuelUsage.set(fuelUsage);
    }

    public double getEuro() {
        return euro.get();
    }

    public SimpleDoubleProperty euroProperty() {
        return euro;
    }

    public void setEuro(double euro) {
        this.euro.set(euro);
    }

    public double getProfitKm() {
        return profitKm.get();
    }

    public SimpleDoubleProperty profitKmProperty() {
        return profitKm;
    }

    public void setProfitKm(double profitKm) {
        this.profitKm.set(profitKm);
    }

    public double getProfitKmEuro() {
        return profitKmEuro.get();
    }

    public SimpleDoubleProperty profitKmEuroProperty() {
        return profitKmEuro;
    }

    public void setProfitKmEuro(double profitKmEuro) {
        this.profitKmEuro.set(profitKmEuro);
    }

    public double getFuel() {
        return fuel.get();
    }

    public SimpleDoubleProperty fuelProperty() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel.set(fuel);
    }

    public double getFuelUsage() {
        return fuelUsage.get();
    }

    public SimpleDoubleProperty fuelUsageProperty() {
        return fuelUsage;
    }

    public void setFuelUsage(double fuelUsage) {
        this.fuelUsage.set(fuelUsage);
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public String getNameOfDriver() {
        return nameOfDriver.get();
    }

    public SimpleStringProperty nameOfDriverProperty() {
        return nameOfDriver;
    }

    public void setNameOfDriver(String nameOfDriver) {
        this.nameOfDriver.set(nameOfDriver);
    }

    public String getPlaceDestination() {
        return placeDestination.get();
    }

    public SimpleStringProperty placeDestinationProperty() {
        return placeDestination;
    }

    public void setPlaceDestination(String placeDestination) {
        this.placeDestination.set(placeDestination);
    }

    public double getExportPrice() {
        return exportPrice.get();
    }

    public SimpleDoubleProperty exportPriceProperty() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice.set(exportPrice);
    }

    public double getImportPrice() {
        return importPrice.get();
    }

    public SimpleDoubleProperty importPriceProperty() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice.set(importPrice);
    }

    public double getSum() {
        return sum.get();
    }

    public SimpleDoubleProperty sumProperty() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum.set(sum);
    }

    public double getCosts() {
        return costs.get();
    }

    public SimpleDoubleProperty costsProperty() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs.set(costs);
    }

    public double getDriverSalary() {
        return driverSalary.get();
    }

    public SimpleDoubleProperty driverSalaryProperty() {
        return driverSalary;
    }

    public void setDriverSalary(double driverSalary) {
        this.driverSalary.set(driverSalary);
    }

    public int getDepartureKm() {
        return departureKm.get();
    }

    public SimpleIntegerProperty departureKmProperty() {
        return departureKm;
    }

    public void setDepartureKm(int departureKm) {
        this.departureKm.set(departureKm);
    }

    public int getArrivalKm() {
        return arrivalKm.get();
    }

    public SimpleIntegerProperty arrivalKmProperty() {
        return arrivalKm;
    }

    public void setArrivalKm(int arrivalKm) {
        this.arrivalKm.set(arrivalKm);
    }

    public int getKm() {
        return km.get();
    }

    public SimpleIntegerProperty kmProperty() {
        return km;
    }

    public void setKm(int km) {
        this.km.set(km);
    }

    public double getProfit() {
        return profit.get();
    }

    public SimpleDoubleProperty profitProperty() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit.set(profit);
    }


    public String getNumberOfCar() {
        return numberOfCar.get();
    }

    public SimpleStringProperty numberOfCarProperty() {
        return numberOfCar;
    }

    public void setNumberOfCar(String numberOfCar) {
        this.numberOfCar.set(numberOfCar);
    }

}
