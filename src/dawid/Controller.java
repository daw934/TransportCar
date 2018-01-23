package dawid;

import dawid.transport.Journey;
import dawid.transport.TransportStore;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    private TransportStore transportStore;
    @FXML
    private TableView<Journey> tableView;
    @FXML
    private BorderPane mainWindow;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private DatePicker datePickerOd;
    @FXML
    private DatePicker datePickerDo;
    @FXML
    private Button podsumowanie;
    @FXML
    private TextField searchField;
    @FXML
    private CheckBox onOfButton;
    @FXML
    private TextField sumProfitField;
    @FXML
    private TextField sumDriverSalaryField;
    @FXML
    private TextField sumKmField;


    private SortedList<Journey> sortedList;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter getFormatter() {
        return formatter;
    }



    public void initialize(){

        contextMenu = new ContextMenu();                                        // otwiera po nacisnieciu prawego przycisku
        MenuItem deleteMenuItem = new MenuItem("delete");                   // jeden kwadrat
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Journey item = tableView.getSelectionModel().getSelectedItem();
                deleteRecord(item);
            }
        });
        contextMenu.getItems().addAll(deleteMenuItem);
        tableView.setContextMenu(contextMenu);

        searchField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER))
                    sortRecord();
            }
        });

        datePickerOd.setValue(LocalDate.of(2012,11,12));
        datePickerDo.setValue(LocalDate.now());

        transportStore = TransportStore.getInstance();
        transportStore.loadJourneys();
        sortRecord();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.getSelectionModel().selectFirst();


        onOfButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(onOfButton.isSelected()) {
                    podsumowanie.setDisable(false);
                    datePickerDo.setDisable(false);
                    datePickerOd.setDisable(false);

                }else{
                    tableView.setItems(transportStore.getJourneys());
                    podsumowanie.setDisable(true);
                    datePickerOd.setDisable(true);
                    datePickerDo.setDisable(true);

                }
                sortRecord();
            }
        });
        updateField();
    }
    @FXML
    private void updateField(){

        double sumProfit=0;
        double sumDriverSalary=0;
        double sumKm=0;
        for(Journey journey: tableView.getItems()) {
            sumProfit += journey.getProfit();
            sumDriverSalary += journey.getDriverSalary();
            sumKm += journey.getKm();
        }
        sumProfitField.setText(Double.toString(sumProfit));
        sumDriverSalaryField.setText(Double.toString(sumDriverSalary));
        sumKmField.setText(Double.toString(sumKm));
    }

    @FXML
    public void save(){
        try {
            TransportStore.getInstance().storeJourneys();
        }catch (IOException e){
            System.out.println("Błąd zapisu");
        }
    }

    @FXML
    public void sortRecord(){

        FilteredList<Journey> journeysSearch= new FilteredList<Journey>(transportStore.getJourneys(), new Predicate<Journey>() {
            @Override
            public boolean test(Journey journey) {
                if(searchField.getText().isEmpty() )
                    return true;
                return journey.getNameOfDriver().equalsIgnoreCase(searchField.getText().trim());
            }
        });
        if(journeysSearch.isEmpty())
            journeysSearch= new FilteredList<Journey>(transportStore.getJourneys(), new Predicate<Journey>() {
                @Override
                public boolean test(Journey journey) {
                    return journey.getNumberOfCar().equalsIgnoreCase(searchField.getText().trim());
                }
            });

        FilteredList<Journey> journeys = new FilteredList<Journey>(journeysSearch, new Predicate<Journey>() {
            @Override
            public boolean test(Journey journey) {
                if(onOfButton.isSelected())
                    return journey.getDateOfDeparture().compareTo(datePickerOd.getValue()) >=0 && journey.getDateOfDeparture().compareTo(datePickerDo.getValue()) <=0;

                return true;
            }
        });
        sortedList = new SortedList<Journey>(journeys);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
        updateField();
    }

    @FXML
    public void editJourney(){

        if(tableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Nie wybrano recordu");
            alert.setHeaderText(null);
            alert.setContentText("wybierz trase która chcesz edytować");
            alert.showAndWait();
            return;
        }

        Journey selectJourney = tableView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("transportDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("editproblem");
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        ControllerDialog controller =  fxmlLoader.getController();
        controller.processEdit(selectJourney);


        Optional<ButtonType> result; //pokazuje okienko i czeka
        while (true){
            result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.CANCEL) {
                return;
            }else {
                 controller = fxmlLoader.getController();
                if (!controller.fieldIsEmpty()) {
                    controller.updateJourney(selectJourney);
                    sortRecord();
                    tableView.refresh();
                    return;
                }
            }
        }
    }

    @FXML
    public void showAddJourney(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());              //wyskakujace okienko
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("transportDialog.fxml"));

        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println("blad transportDialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result;                                    //pokazuje okienko i czeka
        while (true){
            result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.CANCEL) {
                System.out.println("cancel pressed");
                return;
            }else {
                ControllerDialog controller = fxmlLoader.getController();
                System.out.println("OK pressed");
                if (!controller.fieldIsEmpty()) {
                    TransportStore.getInstance().addJourney(controller.processView());
                    updateField();
                    tableView.refresh();
                    return;
                }
            }
        }
    }

    private void deleteRecord(Journey item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Usuwanie trasy");
        alert.setHeaderText("Usuwanie recordu: "+item.getNameOfDriver()+" "+item.getPlaceDestination()+" "+item.getDateOfDeparture());
        Optional<ButtonType> result = alert.showAndWait(); //pokazuje i czeka na nacisniecie przycisku
        if(result.isPresent() && (result.get() == ButtonType.OK)){
            TransportStore.getInstance().removeJourney(item);
            updateField();
        }
    }
}
