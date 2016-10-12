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

    //endregion

    //region Constructor

    public LeapMotionListener(View3DController view3d){
        this.view3d = view3d;
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


        Platform.runLater(() -> {

            if(handRight.isValid()){
                view3d.rightHand.setHandsPosition(handRight);
            }

            if(handLeft.isValid()){
                view3d.leftHand.setHandsPosition(handLeft);
            }

            //Test index and thumb touch
            Finger index = handRight.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
            Finger thumb = handRight.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);

            if((new Point3D(index.tipPosition().getX(),
                            index.tipPosition().getY(),
                            index.tipPosition().getZ())).distance(
                    thumb.tipPosition().getX(),
                    thumb.tipPosition().getY(),
                    thumb.tipPosition().getZ()) <= INDEX_THUMB_TOUCH){
                System.out.println("TOUCH");
            }else{
                System.out.println("NOT TOUCH");
            }

            System.out.println("Distance : " +
            (new Point3D(index.tipPosition().getX(),
                    index.tipPosition().getY(),
                    index.tipPosition().getZ())).distance(
                    thumb.tipPosition().getX(),
                    thumb.tipPosition().getY(),
                    thumb.tipPosition().getZ()));

        });

    }

    //endregion

    //region Getter / Setter
    //endregion


}
