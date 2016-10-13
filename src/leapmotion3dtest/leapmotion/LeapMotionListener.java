package leapmotion3dtest.leapmotion;


import com.leapmotion.leap.*;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import leapmotion3dtest.view3d.View3DController;


/**
 * Created by Argon on 12.10.16.
 */
public class LeapMotionListener extends Listener {

    //region Constants / Variables

    private final static int INDEX_THUMB_TOUCH = 30;

    private View3DController view3d;

    private LineListener lineListener;

    private boolean rightIndexAndTumbClipped;

    //endregion

    //region Constructor

    public LeapMotionListener(View3DController view3d){
        this.view3d = view3d;

        this.rightIndexAndTumbClipped = false;
        this.lineListener = new LineListener(LineListener.HandSide.Right, Finger.Type.TYPE_INDEX);
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

            checkedClippedStatus(controller);

            if(rightIndexAndTumbClipped){
                view3d.way.addWayPoint(
                        indexRight.stabilizedTipPosition().getX(),
                        indexRight.stabilizedTipPosition().getY(),
                        indexRight.stabilizedTipPosition().getZ());

                lineListener.registerFrame(controller.frame());
            }
        });

    }

    private void checkedClippedStatus(Controller controller){

        Hand handRight = controller.frame().hands().rightmost();
        Hand handLeft = controller.frame().hands().leftmost();

        //Test index and thumb touch
        Finger indexRight = handRight.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger thumbRight = handRight.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);

        Point3D indexTipPos = new Point3D(indexRight.tipPosition().getX(), indexRight.tipPosition().getY(), indexRight.tipPosition().getZ());
        Point3D thumbTipPos = new Point3D(thumbRight.tipPosition().getX(), thumbRight.tipPosition().getY(), thumbRight.tipPosition().getZ());

        boolean previousRightIndexAndTumbClipped = rightIndexAndTumbClipped;
        rightIndexAndTumbClipped = indexRight.isValid() && thumbRight.isValid() && indexTipPos.distance(thumbTipPos) <= INDEX_THUMB_TOUCH;

        if(!previousRightIndexAndTumbClipped && rightIndexAndTumbClipped){
            //Start clipping
            System.out.println("Start Clipping ...");
            startClipping();
        }else if(previousRightIndexAndTumbClipped && !rightIndexAndTumbClipped){
            //Stop clipping
            System.out.println("Stop Clipping ...");
            stopClipping();
        }
    }


    private void startClipping(){
        lineListener.clearFrames();
    }

    private void stopClipping(){
        lineListener.checkLine();
    }

    //endregion

    //region Getter / Setter
    //endregion


}
