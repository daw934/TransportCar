package dawid.transport;

import dawid.Controller;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javax.xml.stream.*;
import javax.xml.stream.events.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class TransportStore {

    private ObservableList<Journey> journeys ;

    private static TransportStore instance = new TransportStore();
    private static String filename = "Company.txt";

    private String nameOfDriver;
    private LocalDate dateOfDeparture;
    private String placeDestination;
    private double exportPrice;
    private double importPrice;
    private double sum;
    private double costs;
    private double salary;
    private double profit;
    private String numberOfCar;
    private int departureKm;
    private int arrivalKm;
    private int km;
    private double profitKm;
    private double profitKmEuro;
    private double fuel;
    private double fuelUsage;
    private double euro;


    private DateTimeFormatter formatter = Controller.getFormatter();

    public static TransportStore getInstance() {
        return instance;
    }

    private TransportStore() {

        journeys = FXCollections.observableArrayList();
    }


    public ObservableList<Journey> getJourneys() {

        return journeys;
    }

    public void addJourney(Journey journey) {
        journeys.add(journey);

    }

    public void removeJourney(Journey journey){
 journeys.remove(journey);
}
    public void loadJourneys()  {


    try {
        Path path = Paths.get(filename);
        BufferedReader br = Files.newBufferedReader(path);

        String input;

        try {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");


                dateOfDeparture = LocalDate.parse(itemPieces[0], formatter);
                nameOfDriver = itemPieces[1];
                placeDestination = itemPieces[2];
                exportPrice = Double.parseDouble(itemPieces[3]);
                importPrice = Double.parseDouble(itemPieces[4]);
                sum = Double.parseDouble(itemPieces[5]);
                costs = Double.parseDouble(itemPieces[6]);
                salary = Double.parseDouble(itemPieces[7]);
                profit = Double.parseDouble(itemPieces[8]);
                numberOfCar = itemPieces[9];
                departureKm = Integer.parseInt(itemPieces[10]);
                arrivalKm = Integer.parseInt(itemPieces[11]);
                km = Integer.parseInt(itemPieces[12]);
                profitKm = Double.parseDouble(itemPieces[13]);
                profitKmEuro = Double.parseDouble(itemPieces[14]);
                fuel = Double.parseDouble(itemPieces[15]);
                fuelUsage = Double.parseDouble(itemPieces[16]);
                euro = Double.parseDouble(itemPieces[17]);

                Journey journey = new Journey(dateOfDeparture,nameOfDriver,placeDestination ,exportPrice,importPrice,sum,costs,salary,profit,
                        numberOfCar,departureKm,arrivalKm,km,profitKm,profitKmEuro,fuel,fuelUsage,euro);

                  journeys.add(journey);


            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
    }catch (IOException e){
        System.out.println("blad");
    }
    }

    public void storeJourneys() throws IOException {

        Path path = Paths.get(filename);
        BufferedWriter bw = Files.newBufferedWriter(path);
        try {
            Iterator<Journey> iter = journeys.iterator();
            while(iter.hasNext()) {
                Journey item = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                        item.getDateOfDeparture().format(formatter),
                        item.getNameOfDriver(),
                        item.getPlaceDestination(),
                        item.getExportPrice(),
                        item.getImportPrice(),
                        item.getSum(),
                        item.getCosts(),
                        item.getDriverSalary(),
                        item.getProfit(),
                        item.getNumberOfCar(),
                        item.getDepartureKm(),
                        item.getArrivalKm(),
                        item.getKm(),
                        item.getProfitKm(),
                        item.getProfitKmEuro(),
                        item.getFuel(),
                        item.getFuelUsage(),
                        item.getEuro()));


                bw.newLine();
            }

        } finally {
            if(bw != null) {
                bw.close();
            }
        }








    }


}
