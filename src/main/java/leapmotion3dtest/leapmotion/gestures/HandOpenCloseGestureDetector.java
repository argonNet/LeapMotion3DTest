package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Hand;
import leapmotion3dtest.leapmotion.gestures.information.HandOpenCloseGestureInformation;

/**
 * Created by Argon on 30.10.16.
 */
public class HandOpenCloseGestureDetector extends BaseGestureDetector {

    //region Enum / Constants / Variables

    private final static double FINGER_ANGLE_LIMIT_FOR_CLOSED_HAND = 0.1;
    private final static double FINGER_ANGLE_LIMIT_FOR_OPEN_HAND = 0.1;

    private final static long OPEN_CLOSE_GESTURE_DELAY = 1000;

    private final static double ROLL_HAND_ANGLE_LIMIT = 0.6;

    private boolean wasOpen;
    private boolean wasClose;

    private long lastHandOpenStatus;
    private long lastHandCloseStatus;

    //endregion

    /**
     * Constructor in which we need to precise which end to detect
     */
    public HandOpenCloseGestureDetector(Side handSide){
        super(handSide);
        this.handSide = handSide;

        wasOpen = false;
        wasClose = false;

        lastHandOpenStatus = 0;
        lastHandCloseStatus = 0;
    }

    //region Constructor


    //endregion

    //region Methods

    @Override
    protected void onFrameRegistered(Hand selectedHand){

        if(Math.abs(selectedHand.palmNormal().roll()) <= ROLL_HAND_ANGLE_LIMIT){

            boolean isClose = isHandClosed(selectedHand);
            boolean isOpen = isHandOpen(selectedHand);

            if(isOpen) lastHandOpenStatus = System.currentTimeMillis();
            if(isClose) lastHandCloseStatus = System.currentTimeMillis();

            //Detecting an open status modification
            if(!wasOpen && isOpen &&
               System.currentTimeMillis() - lastHandCloseStatus <= OPEN_CLOSE_GESTURE_DELAY){
                //fire the event
                listeners.forEach(l -> l.gestureDetected(new HandOpenCloseGestureInformation(
                        HandOpenCloseGestureInformation.CloseOpenStatus.Opening)));
            }

            if(!wasClose && isClose &&
               System.currentTimeMillis() - lastHandOpenStatus <= OPEN_CLOSE_GESTURE_DELAY){
                //fire the event
                listeners.forEach(l -> l.gestureDetected(new HandOpenCloseGestureInformation(
                        HandOpenCloseGestureInformation.CloseOpenStatus.Closing)));
            }

            wasOpen = isOpen;
            wasClose = isClose;
        }
    }

    private boolean isHandOpen(Hand hand){
        return (hand.isValid() &&
                Math.abs(hand.grabAngle()) <= FINGER_ANGLE_LIMIT_FOR_CLOSED_HAND);
    }

    private boolean isHandClosed(Hand hand){
        return (hand.isValid() &&
                Math.PI - FINGER_ANGLE_LIMIT_FOR_OPEN_HAND <= Math.abs(hand.grabAngle())  &&
                Math.abs(hand.grabAngle()) <=  Math.PI + FINGER_ANGLE_LIMIT_FOR_OPEN_HAND);
    }

    //endregion


    //region Getter / Setter

    //endregion
}
