package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import javafx.geometry.Point3D;
import leapmotion3dtest.leapmotion.gestures.information.PinchGestureInformation;

/**
 * Class that should detect Index and Thumb clipping status. Not working yet.
 */
public class PinchGestureDetector extends BaseGestureDetector {


    //region Constants / Variables

    private final static int INDEX_THUMB_TOUCH = 25;

    private final static double FINGER_ANGLE_LIMIT_FOR_OPEN_HAND = 0.3;

    private boolean indexAndTumbClipped;

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
        Finger index = selectedHand.fingers().fingerType(Finger.Type.TYPE_INDEX).get(0);
        Finger thumb = selectedHand.fingers().fingerType(Finger.Type.TYPE_THUMB).get(0);


        Point3D indexTipPos = new Point3D(index.tipPosition().getX(), index.tipPosition().getY(), index.tipPosition().getZ());
        Point3D thumbTipPos = new Point3D(thumb.tipPosition().getX(), thumb.tipPosition().getY(), thumb.tipPosition().getZ());

        boolean previousIndexAndTumbClipped = indexAndTumbClipped;
        indexAndTumbClipped = index.isValid() && thumb.isValid() && indexTipPos.distance(thumbTipPos) <= INDEX_THUMB_TOUCH;

    if (!previousIndexAndTumbClipped && indexAndTumbClipped && selectedHand.grabAngle() < Math.PI - FINGER_ANGLE_LIMIT_FOR_OPEN_HAND)
    {
        //Start clipping
        listeners.forEach((x) -> x.gestureDetected(new PinchGestureInformation(true)));
    } else if (previousIndexAndTumbClipped && !indexAndTumbClipped) {
        //Stop clipping
        listeners.forEach((x) -> x.gestureDetected(new PinchGestureInformation(false)));
    }

    }

    //endregion


    //region Getter / Setter

    //endregion

}
