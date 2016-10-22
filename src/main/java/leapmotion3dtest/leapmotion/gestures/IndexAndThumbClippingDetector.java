package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import javafx.geometry.Point3D;

/**
 * Class that should detect Index and Thumb clipping status. Not working yet.
 */
public class IndexAndThumbClippingDetector{



    //region Constants / Variables

    private final static int INDEX_THUMB_TOUCH = 30;

    private boolean rightIndexAndTumbClipped;

    //endregion


    //region Constructor

    //endregion


    //region Methods

    public void registerFrame(Frame newFrame){

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
    }

    private void stopClipping(){

    }

    //endregion


    //region Getter / Setter

    //endregion

}
