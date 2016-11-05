package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Hand;
import leapmotion3dtest.leapmotion.gestures.information.BaseGestureInformation;
import leapmotion3dtest.leapmotion.gestures.information.HandUpDownGestureInformation;
import leapmotion3dtest.leapmotion.gestures.information.PinchGestureInformation;

/**
 * Detect when the hand goes up or down. We raise the event if the hand did a minimum distance !
 * This distance is precised in the DISTANCE_TO_RAISE_DETECTION constant.
 */
public class HandUpDownGestureDetector extends BaseGestureDetector implements IGestureListener {

    //region Enum / Constants / Variables

    private final static double DISTANCE_TO_RAISE_DETECTION = 1.5;

    private double lastFrameYPosition;
    private double currentProgression;

    //endregion

    /**
     * Constructor in which we need to precise which end to detect
     */
    public HandUpDownGestureDetector(Side handSide){
        super(handSide);

        lastFrameYPosition = 0;
        currentProgression = 0;
    }

    //region Constructor


    //endregion

    //region Methods
    @Override
    protected void onFrameRegistered(Hand selectedHand){

        double tmpShifting = selectedHand.palmPosition().getY() - lastFrameYPosition;

        if(Math.abs(currentProgression) >= DISTANCE_TO_RAISE_DETECTION) {

            if (selectedHand.palmVelocity().getY() > 0) { //Up
                listeners.forEach(l -> l.gestureDetected(new HandUpDownGestureInformation(
                        HandUpDownGestureInformation.UpDownStatus.Up)));
            } else { //Down
                listeners.forEach(l -> l.gestureDetected(new HandUpDownGestureInformation(
                        HandUpDownGestureInformation.UpDownStatus.Down)));
            }
            currentProgression = 0;
        }

        currentProgression += tmpShifting;
        lastFrameYPosition = selectedHand.palmPosition().getY();
    }


    @Override
    public void gestureDetected(BaseGestureInformation gestureInfo) {
     if(gestureInfo instanceof PinchGestureInformation) {
            if (((PinchGestureInformation) gestureInfo).getIsPinched()) {
                startDetection();
            } else {
                stopDetection();
            }
        }
    }


    //endregion


    //region Getter / Setter

    //endregion
}
