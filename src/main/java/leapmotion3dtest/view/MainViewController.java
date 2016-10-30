package leapmotion3dtest.view;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import leapmotion3dtest.leapmotion.IMonitorListener;
import leapmotion3dtest.leapmotion.gestures.*;
import leapmotion3dtest.leapmotion.gestures.information.BaseGestureInformation;
import leapmotion3dtest.leapmotion.gestures.information.HandOpenCloseGestureInformation;
import leapmotion3dtest.leapmotion.gestures.information.HandUpDownGestureInformation;
import leapmotion3dtest.leapmotion.gestures.information.SwipeGestureInformation;
import leapmotion3dtest.view3d.View3DController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Argon on 10.10.16.
 */
public class MainViewController implements Initializable, IMonitorListener, IGestureListener {


    private final static int VOLUME_SIZE_INC = 5;
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

    @FXML private CheckBox cbxActivateFrameMonitoring;
    @FXML private CheckBox cbxActivateGestureMonitoring;
    @FXML private TextArea txtMonitoringBox;
    @FXML private Spinner<Integer> spnFrameMonitor;
    @FXML private Button btnClearMonitoring;

    @FXML private Label lblGestureDetected;

    @FXML private AnchorPane volumeBackground;
    @FXML private Rectangle volumeStatus;

    private FadeTransition fadeInLblGestureDetected;

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

        lblGestureDetected.setVisible(false);

        fadeInLblGestureDetected = new FadeTransition(Duration.millis(1000));
        fadeInLblGestureDetected.setNode(lblGestureDetected);
        fadeInLblGestureDetected.setFromValue(0.0);
        fadeInLblGestureDetected.setToValue(1.0);
        fadeInLblGestureDetected.setCycleCount(2);
        fadeInLblGestureDetected.setAutoReverse(true);
        fadeInLblGestureDetected.setOnFinished((ActionEvent event) -> {
            lblGestureDetected.setVisible(false);
        });

        volumeBackground.visibleProperty().set(false);
    }

    @Override
    public void newFrameArrived(Frame newFrame) {
        if(cbxActivateFrameMonitoring.isSelected()){
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
    public void gestureDetected(BaseGestureInformation gestureInfo) {

        Platform.runLater(() -> {

            if (gestureInfo instanceof SwipeGestureInformation) {

                displaySwipeInformation((SwipeGestureInformation) gestureInfo);

            }else if(gestureInfo instanceof HandOpenCloseGestureInformation) {

                displayOpenCloseGestureInformation((HandOpenCloseGestureInformation) gestureInfo);

            }else if(gestureInfo instanceof HandUpDownGestureInformation){

                displayUpDownGestureInformation((HandUpDownGestureInformation) gestureInfo);

            }
        });
    }

    private void displayAndHideGesture(String text){
        lblGestureDetected.setVisible(true);
        lblGestureDetected.textProperty().set(text);
        fadeInLblGestureDetected.playFromStart();
    }

    private void displaySwipeInformation(SwipeGestureInformation gestureInformation){

        String direction = "";
        if(gestureInformation.getDirection() == SwipeGestureDetector.Side.Right){
            direction = "Right";
        }else if(gestureInformation.getDirection() == SwipeGestureDetector.Side.Left){
            direction = "Left";
        }

        if(cbxActivateGestureMonitoring.isSelected()){
            txtMonitoringBox.textProperty().setValue(
                direction + " Swipe -> min V : " + gestureInformation.getMinVelocityDetected() +
                " | max V : " + gestureInformation.getMaxVelocityDetected() + " Time : " + System.currentTimeMillis() +
                "\n" + txtMonitoringBox.textProperty().get());
        }

        displayAndHideGesture(direction + " Swipe ");
    }

    private void displayOpenCloseGestureInformation(HandOpenCloseGestureInformation gestureInformation){
        if(gestureInformation.getCloseOpenStatus() == HandOpenCloseGestureInformation.CloseOpenStatus.Opening){
            displayAndHideGesture("Hand Opening");

            volumeBackground.visibleProperty().set(true);

        }else if(gestureInformation.getCloseOpenStatus() == HandOpenCloseGestureInformation.CloseOpenStatus.Closing){
            displayAndHideGesture("Hand Closing");

            volumeBackground.visibleProperty().set(false);

        }
    }

    private void displayUpDownGestureInformation(HandUpDownGestureInformation gestureInformation){
        if(gestureInformation.getUpDownStatus() == HandUpDownGestureInformation.UpDownStatus.Down){

            volumeStatus.setHeight(volumeStatus.getHeight() - VOLUME_SIZE_INC);

        }else if(gestureInformation.getUpDownStatus() == HandUpDownGestureInformation.UpDownStatus.Up){

            volumeStatus.setHeight(volumeStatus.getHeight() + VOLUME_SIZE_INC);


        }
    }

}
