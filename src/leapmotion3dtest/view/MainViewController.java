package leapmotion3dtest.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import leapmotion3dtest.view3d.View3DController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 10.10.16.
 */
public class MainViewController implements Initializable {

    public View3DController view3DController;

    @FXML AnchorPane anchorPaneFor3DZone;
    @FXML private SubScene view3D;

    @FXML private Slider cameraPositionSlider1;
    @FXML private Slider cameraPositionSlider2;
    @FXML private Slider cameraZoomSlider;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        view3D.heightProperty().bind(anchorPaneFor3DZone.heightProperty());
        view3D.widthProperty().bind(anchorPaneFor3DZone.widthProperty());

        view3DController = new View3DController(this.view3D);

        view3DController.cameraPositionAlphaAngle.bindBidirectional(cameraPositionSlider1.valueProperty());
        view3DController.cameraPositionBetaAngle.bindBidirectional(cameraPositionSlider2.valueProperty());
        view3DController.cameraZoom.bindBidirectional(cameraZoomSlider.valueProperty());

    }

}
