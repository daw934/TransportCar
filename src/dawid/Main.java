package dawid;


import dawid.transport.TransportStore;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Transport");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();


    }

    @Override
    public void stop() throws Exception {
   //     super.stop();
         TransportStore.getInstance().storeJourneys();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
