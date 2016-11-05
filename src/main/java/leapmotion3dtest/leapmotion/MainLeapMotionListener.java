package leapmotion3dtest.leapmotion;


import com.leapmotion.leap.*;
import javafx.application.Platform;
import leapmotion3dtest.leapmotion.gestures.*;
import leapmotion3dtest.view3d.View3DController;


/**
 * Created by Argon on 12.10.16.
 */
public class MainLeapMotionListener extends Listener {

    //region Constants / Variables

    private View3DController view3d;

    private IMonitorListener monitorListener;

    private IGestureDetector swipeDetector;
    private IGestureDetector handOpenCloseDetector;
    private IGestureDetector pinchGestureDetector;
    private HandUpDownGestureDetector handUpDownDetector;

    //endregion

    //region Constructor0

    public MainLeapMotionListener(View3DController view3d, IMonitorListener monitorListener){
        this.view3d = view3d;
        this.monitorListener = monitorListener;

        this.swipeDetector = new SwipeGestureDetector(SwipeGestureDetector.Side.Right);
        this.handOpenCloseDetector = new HandOpenCloseGestureDetector(SwipeGestureDetector.Side.Right);
        this.handUpDownDetector = new HandUpDownGestureDetector(BaseGestureDetector.Side.Right);
        this.pinchGestureDetector = new PinchGestureDetector(SwipeGestureDetector.Side.Right);

        this.pinchGestureDetector.addListener(handUpDownDetector);
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
        handOpenCloseDetector.registerFrame(controller.frame());
        handUpDownDetector.registerFrame(controller.frame());
        pinchGestureDetector.registerFrame(controller.frame());

        Hand handRight = controller.frame().hands().rightmost();
        Hand handLeft = controller.frame().hands().leftmost();

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

    public IGestureDetector getHandOpenCloseDetector(){
        return handOpenCloseDetector;
    }

    public IGestureDetector getPinchGestureDetector () { return pinchGestureDetector;}

    public IGestureDetector getHandUpDownDetector(){
        return handUpDownDetector;
    }

    //endregion


}
