package leapmotion3dtest;


import com.leapmotion.leap.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import leapmotion3dtest.leapmotion.LeapMotionListener;
import leapmotion3dtest.view.MainViewController;

public class Main extends Application {


    private LeapMotionListener listener;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("view/mainView.fxml"));
        Parent mainView = mainViewLoader.load();
        Scene mainScene = new Scene(mainView, 1000, 800, true);


        primaryStage.setTitle("3D JavaFX");
        primaryStage.setScene(mainScene);
        primaryStage.show();


        // Create a sample listener and assign it to a controller to receive events
        listener = new LeapMotionListener(((MainViewController)mainViewLoader.getController()).view3DController);

        controller = new Controller(listener);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
