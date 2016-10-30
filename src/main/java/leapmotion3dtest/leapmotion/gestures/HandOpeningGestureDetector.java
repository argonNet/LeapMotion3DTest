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
    protected void onFrameRegisterd(Frame newFrame){

        System.out.println("1");

        isFistClosed(selectedHand);

        System.out.println("2");

        isFistOpen(selectedHand);

        System.out.println("3");
    }


    private boolean isFistClosed(Hand hand){
        try{
            if(hand.isValid() &&
                    Math.abs(hand.grabAngle()) <= FINGER_ANGLE_LIMIT_FOR_CLOSED_HAND){
                System.out.println("Fist closed");
            }

        }catch(Exception e){
            System.out.println(e.getMessage());

        }

        return true;
    }


    private boolean isFistOpen(Hand hand){
        if(hand.isValid() &&
           Math.PI - FINGER_ANGLE_LIMIT_FOR_OPEN_HAND <= Math.abs(hand.grabAngle())  &&
           Math.abs(hand.grabAngle()) <=  Math.PI + FINGER_ANGLE_LIMIT_FOR_OPEN_HAND){

            System.out.println("Fist Open");

        }
        return true;
    }

    //endregion


    //region Getter / Setter

    //endregion
}
