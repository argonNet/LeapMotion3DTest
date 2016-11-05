package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import javafx.geometry.Point3D;
import leapmotion3dtest.leapmotion.gestures.information.PinchGestureInformation;

/**
 * Class that should detect Index and Thumb clipping status. Not working yet.
 */
public class PinchGestureDetector extends BaseGestureDetector {


    //region Constants / Variables

    private final static int INDEX_THUMB_TOUCH = 30;

    private boolean rightIndexAndTumbClipped;

    //endregion


    //region Constructor

    public PinchGestureDetector(Side handSide){
        super( handSide);
    }

    //endregion


    //region Methods

    @Override
    protected void onFrameRegistered(Hand selectedHand) {
        //Test index and thumb touch
        Finger indexRight = selectedHand.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger thumbRight = selectedHand.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);

        Point3D indexTipPos = new Point3D(indexRight.tipPosition().getX(), indexRight.tipPosition().getY(), indexRight.tipPosition().getZ());
        Point3D thumbTipPos = new Point3D(thumbRight.tipPosition().getX(), thumbRight.tipPosition().getY(), thumbRight.tipPosition().getZ());

        boolean previousRightIndexAndTumbClipped = rightIndexAndTumbClipped;
        rightIndexAndTumbClipped = indexRight.isValid() && thumbRight.isValid() && indexTipPos.distance(thumbTipPos) <= INDEX_THUMB_TOUCH;

        if(!previousRightIndexAndTumbClipped && rightIndexAndTumbClipped){
            //Start clipping
            listeners.forEach((x) -> x.gestureDetected(new PinchGestureInformation(true)));
        }else if(previousRightIndexAndTumbClipped && !rightIndexAndTumbClipped){
            //Stop clipping
            listeners.forEach((x) -> x.gestureDetected(new PinchGestureInformation(false)));
        }
    }

    //endregion


    //region Getter / Setter

    //endregion

}
