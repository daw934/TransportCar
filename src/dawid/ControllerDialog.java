package dawid;

import dawid.transport.Journey;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.text.*;
import java.time.LocalDate;
import java.util.Calendar;
public class ControllerDialog {

    @FXML
    private TextField nameOfDriverField;
    @FXML
    private DatePicker dateOfDepartureField;
    @FXML
    private TextField placeDestinationField;
    @FXML
    private TextField exportPriceField;
    @FXML
    private TextField importPriceField;
    @FXML
    private TextField costsField;
    @FXML
    private TextField numberOfCarField;
    @FXML
    private TextField departureKmField;
    @FXML
    private TextField arrivalKmField;
    @FXML
    private TextField driverSalaryField;
    @FXML
    private TextField fuelField;
    @FXML
    private TextField euroField;

    public static BigDecimal bd = new BigDecimal(0.00f);


    public void initialize() {
        doubleFormater(euroField);
        doubleFormater(exportPriceField);
        doubleFormater(importPriceField);
        doubleFormater(costsField);
        decimalFormater(departureKmField);
        decimalFormater(arrivalKmField);
        doubleFormater(driverSalaryField);
        doubleFormater(fuelField);
    }
    public boolean fieldIsEmpty(){
        Calendar calendar = Calendar.getInstance();
        if (nameOfDriverField.getText().trim().isEmpty() || (dateOfDepartureField.getValue() == null) || placeDestinationField.getText().trim().isEmpty() ||
                exportPriceField.getText().isEmpty() || importPriceField.getText().isEmpty() || exportPriceField.getText().isEmpty() || costsField.getText().isEmpty()
                || driverSalaryField.getText().isEmpty() || numberOfCarField.getText().trim().isEmpty() || arrivalKmField.getText().isEmpty()
                || fuelField.getText().isEmpty() || euroField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Puste miejsca");
            alert.setHeaderText(null);
            alert.setContentText("Wprowadz dane do kazdego pola");
            alert.showAndWait();
            return true;
        }
            return false;
    }

    public Journey processView() {
        String nameOfDriver = nameOfDriverField.getText().trim();
        LocalDate dateOfDeparture = dateOfDepartureField.getValue();
        String placeDestination = placeDestinationField.getText().trim();
        double exportPrice = Double.parseDouble(exportPriceField.getText());
        double importPrice = Double.parseDouble(importPriceField.getText());
        //double sum = exportPrice + importPrice;
        double sum =new BigDecimal(exportPrice + importPrice).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
        double costs = Double.parseDouble(costsField.getText());
        double driverSalary = Double.parseDouble(driverSalaryField.getText());
        double profit =new BigDecimal(sum - costs - driverSalary).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
                String numberOfCar = numberOfCarField.getText().trim();
        int departureKm = Integer.parseInt(departureKmField.getText());
        int arrivalKm = Integer.parseInt(arrivalKmField.getText());
        int km = arrivalKm - departureKm;
        double profitKm = new BigDecimal(sum/km).setScale(4,BigDecimal.ROUND_DOWN).doubleValue();
        double euro = Double.parseDouble(euroField.getText());
        double profitKmEuro = new BigDecimal(profitKm/euro).setScale(4,BigDecimal.ROUND_DOWN).doubleValue();
        double fuel = Double.parseDouble(fuelField.getText());
        double fuelUsage = new BigDecimal((fuel/km)*100).setScale(4,BigDecimal.ROUND_DOWN).doubleValue();

        return new Journey(dateOfDeparture, nameOfDriver, placeDestination, exportPrice, importPrice, sum, costs, driverSalary, profit, numberOfCar, departureKm, arrivalKm, km, profitKm,profitKmEuro,fuel,fuelUsage,euro);
    }

    public void processEdit(Journey journey) {
        nameOfDriverField.setText(journey.getNameOfDriver());
        dateOfDepartureField.setValue(journey.getDateOfDeparture());
        placeDestinationField.setText(journey.getPlaceDestination());
        exportPriceField.setText(Double.toString(journey.getExportPrice()));
        importPriceField.setText(Double.toString(journey.getImportPrice()));
        costsField.setText(Double.toString(journey.getCosts()));
        driverSalaryField.setText(Double.toString(journey.getDriverSalary()));
        numberOfCarField.setText(journey.getNumberOfCar());
        departureKmField.setText(Integer.toString(journey.getDepartureKm()));
        arrivalKmField.setText(Integer.toString(journey.getArrivalKm()));
        fuelField.setText(Double.toString(journey.getFuel()));
        euroField.setText(Double.toString(journey.getEuro()));
    }

    public void updateJourney(Journey journey) {
        journey.setNameOfDriver(nameOfDriverField.getText());
        journey.setDateOfDeparture(dateOfDepartureField.getValue());
        journey.setPlaceDestination(placeDestinationField.getText());
        journey.setExportPrice(Double.parseDouble(exportPriceField.getText()));
        journey.setImportPrice(Double.parseDouble(importPriceField.getText()));
        journey.setCosts(Double.parseDouble(costsField.getText()));
        journey.setDriverSalary(Double.parseDouble(driverSalaryField.getText()));
        journey.setNumberOfCar(numberOfCarField.getText());
        journey.setDepartureKm(Integer.parseInt(departureKmField.getText()));
        journey.setArrivalKm(Integer.parseInt(arrivalKmField.getText()));
        journey.setSum(new BigDecimal(journey.getExportPrice()+journey.getImportPrice()).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        journey.setProfit(new BigDecimal(journey.getSum() - journey.getDriverSalary()-journey.getCosts()).setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
        journey.setKm(journey.getArrivalKm() - journey.getDepartureKm());
        journey.setFuel(Double.parseDouble(fuelField.getText()));
        journey.setFuelUsage(new BigDecimal(journey.getFuel()/journey.getKm()*100).setScale(4,BigDecimal.ROUND_DOWN).doubleValue());
        journey.setEuro(Double.parseDouble(euroField.getText()));
        journey.setProfitKm(new BigDecimal(journey.getSum()/journey.getKm()).setScale(4,BigDecimal.ROUND_DOWN).doubleValue()); //sum/km
        journey.setProfitKmEuro(new BigDecimal(journey.getProfitKm()/journey.getEuro()).setScale(4,BigDecimal.ROUND_DOWN).doubleValue()); //sum/km
    }

    public void doubleFormater(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,15}([\\.]\\d{0,2})?")) {
                    textField.setText(oldValue);
                }
            }
        });
    }

    public void decimalFormater(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,15}")) {
                    textField.setText(oldValue);
                }
            }
        });
    }
}



