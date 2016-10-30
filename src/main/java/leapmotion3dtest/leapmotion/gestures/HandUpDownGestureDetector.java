package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Hand;
import leapmotion3dtest.leapmotion.gestures.information.BaseGestureInformation;
import leapmotion3dtest.leapmotion.gestures.information.HandOpenCloseGestureInformation;
import leapmotion3dtest.leapmotion.gestures.information.HandUpDownGestureInformation;

/**
 * Created by Argon on 30.10.2016.
 */
public class HandUpDownGestureDetector extends BaseGestureDetector implements IGestureListener {

    //region Enum / Constants / Variables

    private final static double FINGER_ANGLE_LIMIT_FOR_CLOSED_HAND = 0.2;

    private final static int NUMBER_OF_FRAME_TO_IGNORE = 10;

    private int frameCountBeforeIgnore;

    //endregion

    /**
     * Constructor in which we need to precise which end to detect
     */
    public HandUpDownGestureDetector(Side handSide){
        super(handSide);

        frameCountBeforeIgnore = 0;

    }

    //region Constructor


    //endregion

    //region Methods


    /**
     *
     * @param selectedHand
     */
    @Override
    protected void onFrameRegistered(Hand selectedHand){
        if(frameCountBeforeIgnore == NUMBER_OF_FRAME_TO_IGNORE) {

            if (selectedHand.palmVelocity().getY() > 0) { //Up
                listeners.forEach(l -> l.gestureDetected(new HandUpDownGestureInformation(
                        HandUpDownGestureInformation.UpDownStatus.Up)));
            } else { //Down
                listeners.forEach(l -> l.gestureDetected(new HandUpDownGestureInformation(
                        HandUpDownGestureInformation.UpDownStatus.Down)));
            }
            frameCountBeforeIgnore = 0;
        }
        frameCountBeforeIgnore++;
    }


    @Override
    public void gestureDetected(BaseGestureInformation gestureInfo) {
        if(gestureInfo instanceof HandOpenCloseGestureInformation) {
            if (((HandOpenCloseGestureInformation) gestureInfo).getCloseOpenStatus() ==
                    HandOpenCloseGestureInformation.CloseOpenStatus.Closing) {
                stopDetection();
            } else if (((HandOpenCloseGestureInformation) gestureInfo).getCloseOpenStatus() ==
                    HandOpenCloseGestureInformation.CloseOpenStatus.Opening) {
                startDetection();
            }
        }
    }


    //endregion


    //region Getter / Setter

    //endregion
}
