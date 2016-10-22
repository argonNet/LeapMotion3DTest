package leapmotion3dtest.leapmotion;


import com.leapmotion.leap.*;
import javafx.application.Platform;
import leapmotion3dtest.leapmotion.gestures.IGestureDetector;
import leapmotion3dtest.leapmotion.gestures.SwipeGestureDetector;
import leapmotion3dtest.view3d.View3DController;


/**
 * Created by Argon on 12.10.16.
 */
public class MainLeapMotionListener extends Listener {

    //region Constants / Variables

    private View3DController view3d;

    private IMonitorListener monitorListener;
    private IGestureDetector swipeDetector;

    //endregion

    //region Constructor

    public MainLeapMotionListener(View3DController view3d, IMonitorListener monitorListener){
        this.view3d = view3d;
        this.monitorListener = monitorListener;

        this.swipeDetector = new SwipeGestureDetector(SwipeGestureDetector.Side.Right);
    }

    //endregion

    //region Methods

    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    public void onFrame(Controller controller) {

        swipeDetector.registerFrame(controller.frame());

        Hand handRight = controller.frame().hands().rightmost();
        Hand handLeft = controller.frame().hands().leftmost();

        Finger indexRight = handRight.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger thumbRight = handRight.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);

        Platform.runLater(() -> {

            if(handRight.isValid()){
                view3d.rightHand.setHandsPosition(handRight);
            }

            if(handLeft.isValid()){
                view3d.leftHand.setHandsPosition(handLeft);
            }

            fireMonitoringEvent(controller.frame());
        });

    }

    private void fireMonitoringEvent(Frame newFrame){
        monitorListener.newFrameArrived(newFrame);
    }

    //endregion


    //region Getter / Setter

    public IGestureDetector getSwipeGestureDetector(){
        return swipeDetector;
    }

    //endregion


}
