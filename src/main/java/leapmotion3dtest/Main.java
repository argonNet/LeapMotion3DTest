package leapmotion3dtest;


import com.leapmotion.leap.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import leapmotion3dtest.leapmotion.MainLeapMotionListener;
import leapmotion3dtest.view.MainViewController;

public class Main extends Application {

    private MainLeapMotionListener listener;
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        Parent mainView = mainViewLoader.load();
        Scene mainScene = new Scene(mainView, 1000, 800, true);

        primaryStage.setTitle("3D JavaFX");
        primaryStage.setScene(mainScene);
        primaryStage.show();


        // Create a sample listener and assign it to a controller to receive events
        listener = new MainLeapMotionListener(((MainViewController)mainViewLoader.getController()).view3DController,
                                              mainViewLoader.getController());

        controller = new Controller(listener);

        listener.getSwipeGestureDetector().addListener((MainViewController)mainViewLoader.getController());
        listener.getHandOpenCloseDetector().addListener((MainViewController)mainViewLoader.getController());
        listener.getHandUpDownDetector().addListener((MainViewController)mainViewLoader.getController());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
