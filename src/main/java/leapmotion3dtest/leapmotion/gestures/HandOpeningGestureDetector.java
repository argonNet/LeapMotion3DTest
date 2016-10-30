package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

/**
 * Created by Argon on 30.10.16.
 */
public class HandOpeningGestureDetector extends BaseGestureDetector {

    //region Enum / Constants / Variables

    private final static double FINGER_ANGLE_LIMIT_FOR_CLOSED_HAND = 0.5;
    private final static double FINGER_ANGLE_LIMIT_FOR_OPEN_HAND = 0.5;

    //endregion

    /**
     * Constructor in which we need to precise which end to detect
     */
    public HandOpeningGestureDetector(Side handSide){
        super(handSide);
        this.handSide = handSide;
    }

    //region Constructor


    //endregion

    //region Methods


    /**
     * Method that get the frame and detect the Gesture
     * @param newFrame frame to take care of.
     */
    @Override
    public void registerFrame(Frame newFrame){
        super.registerFrame(newFrame);

        isFistClosed(selectedHand);
        isFistOpen(selectedHand);
    }


    private boolean isFistClosed(Hand hand){
        if(Math.abs(hand.grabAngle()) <= FINGER_ANGLE_LIMIT_FOR_CLOSED_HAND){
            System.out.println("Fist closed");
        }
        return true;
    }


    private boolean isFistOpen(Hand hand){
        if( Math.PI - FINGER_ANGLE_LIMIT_FOR_OPEN_HAND <= Math.abs(hand.grabAngle())  &&
            Math.abs(hand.grabAngle()) <=  Math.PI + FINGER_ANGLE_LIMIT_FOR_OPEN_HAND){
            System.out.println("Fist Open");
        }
        return true;
    }

    //endregion


    //region Getter / Setter

    //endregion
}
