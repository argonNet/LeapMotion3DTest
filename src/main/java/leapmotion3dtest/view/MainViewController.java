package leapmotion3dtest.view;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import leapmotion3dtest.leapmotion.IMonitorListener;
import leapmotion3dtest.leapmotion.gestures.GestureInformation;
import leapmotion3dtest.leapmotion.gestures.IGestureListener;
import leapmotion3dtest.leapmotion.gestures.SwipeGestureDetector;
import leapmotion3dtest.leapmotion.gestures.SwipeGestureInformation;
import leapmotion3dtest.view3d.View3DController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 10.10.16.
 */
public class MainViewController implements Initializable, IMonitorListener, IGestureListener {

    private int frameCount;
    public View3DController view3DController;

    @FXML AnchorPane anchorPaneFor3DZone;
    @FXML private SubScene view3D;

    @FXML private Slider cameraPositionSlider1;
    @FXML private Slider cameraPositionSlider2;
    @FXML private Slider cameraZoomSlider;

    @FXML private CheckBox cbxDisplayFingers;
    @FXML private CheckBox cbxDisplayPalm;
    @FXML private CheckBox cbxDisplayInteractionBox;

    @FXML private CheckBox cbxActivateMonitoring;
    @FXML private TextArea txtMonitoringBox;
    @FXML private Spinner<Integer> spnFrameMonitor;
    @FXML private Button btnClearMonitoring;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        view3D.heightProperty().bind(anchorPaneFor3DZone.heightProperty());
        view3D.widthProperty().bind(anchorPaneFor3DZone.widthProperty());

        frameCount = 0;
        spnFrameMonitor.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20));

        view3DController = new View3DController(this.view3D);

        view3DController.cameraPositionAlphaAngle.bindBidirectional(cameraPositionSlider1.valueProperty());
        view3DController.cameraPositionBetaAngle.bindBidirectional(cameraPositionSlider2.valueProperty());
        view3DController.cameraZoom.bindBidirectional(cameraZoomSlider.valueProperty());

        view3DController.displayFingers.bindBidirectional(cbxDisplayFingers.selectedProperty());
        view3DController.displayPalm.bindBidirectional(cbxDisplayPalm.selectedProperty());
        view3DController.displayInteractionBox.bindBidirectional(cbxDisplayInteractionBox.selectedProperty());

        //Setting default comfortable view param
        view3DController.cameraZoom.set(1000);
        view3DController.cameraPositionBetaAngle.set(15);
        view3DController.displayFingers.set(true);
        view3DController.displayPalm.set(true);
        view3DController.displayInteractionBox.set(false);

    }

    @Override
    public void newFrameArrived(Frame newFrame) {
        if(cbxActivateMonitoring.isSelected()){
            if(frameCount >= spnFrameMonitor.getValue()) {

                Hand rightHand = newFrame.hands().rightmost();
                Hand leftHand = newFrame.hands().leftmost();

                if(rightHand.palmVelocity().getX() != 0 &&
                   rightHand.palmVelocity().getY() != 0 &&
                   rightHand.palmVelocity().getZ() != 0 &&
                   leftHand.palmVelocity().getX() != 0 &&
                   leftHand.palmVelocity().getY() != 0 &&
                   leftHand.palmVelocity().getZ() != 0) {
                    txtMonitoringBox.textProperty().setValue(
                            " R ->" +
                            " X " + rightHand.stabilizedPalmPosition().getX() +
                            " Y " + rightHand.stabilizedPalmPosition().getY() +
                            " Z " + rightHand.stabilizedPalmPosition().getZ() +
                            " | " +
                            " vX " + rightHand.palmVelocity().getX() +
                            " vY " + rightHand.palmVelocity().getY() +
                            " vZ " + rightHand.palmVelocity().getZ() + "\n" +
                            " L -> " +
                            " X " + leftHand.stabilizedPalmPosition().getX() +
                            " Y " + leftHand.stabilizedPalmPosition().getY() +
                            " Z " + leftHand.stabilizedPalmPosition().getZ() +
                            " | " +
                            " vX " + leftHand.palmVelocity().getX() +
                            " vY " + leftHand.palmVelocity().getY() +
                            " vZ " + leftHand.palmVelocity().getZ() + "\n###########################\n" +
                            txtMonitoringBox.textProperty().get()
                    );
                }
                frameCount=0;
            }

            frameCount++;
        }
    }

    @FXML private void btnClearMonitoringAction(ActionEvent event){
        txtMonitoringBox.textProperty().set("");
    }

    @Override
    public void gestureDetected(GestureInformation gestureInfo) {

        Platform.runLater(() -> {

            if (gestureInfo instanceof SwipeGestureInformation) {
                if (((SwipeGestureInformation) gestureInfo).getDirection() == SwipeGestureDetector.Side.Left) {

                    System.out.println("LEFT");

                } else if (((SwipeGestureInformation) gestureInfo).getDirection() == SwipeGestureDetector.Side.Right) {

                    System.out.println("RIGHT");
                }
            }
        });
    }
}
