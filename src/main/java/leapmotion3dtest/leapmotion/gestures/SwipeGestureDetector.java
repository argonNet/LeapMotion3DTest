package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import org.apache.commons.math.stat.regression.SimpleRegression;

import java.util.ArrayList;
import java.util.List;

/**
 * This class detect swipe and inform her client.
 *
 * Description
 * -----------
 *
 * The swipe detection is base on several parameter :
 * - The length of the gesture, measured in frame number,
 * - The minimum velocity on the X axis to detect frame,
 * - The maximum velocity to reach to validate the gesture,
 * - The slope of the gesture with  the X axis,
 * - The R coefficient of the the linear regression done on the gesture (we only take care of X and Y axis)
 *
 * All these parameter are checked to detect the gesture.
 *
 * /!\ Only work for on hand /!\
 * */
public class SwipeGestureDetector implements IGestureDetector {

    //region Enum / Constants / Variables

    public enum Side{
        Left,
        Right
    }

    private final static int GESTURE_LENGTH = 15;
    private final static int MIN_GESTURE_VELOCITY_X_FRAME_DECTECTION = 500;
    private final static int MAX_GESTURE_VELOCITY_X_VALIDATION = 1000;
    private final static double MIN_R = 0.5;
    private final static double MIN_SLOPE  = 0.5;

    private Side handSide;

    private int frameGestureCount;
    private double xVelocityMax;
    private Side currentGestureDirection;

    private SimpleRegression regression;
    private List<IGestureListener> listeners;


    //endregion

    /**
     * Constructor in which we need to precise which end to detect
     * @param handSide Hand to detect (right or left one)
     */
    public SwipeGestureDetector(Side handSide){
        this.handSide = handSide;

        regression = new SimpleRegression();

        listeners = new ArrayList<>();
        xVelocityMax = 0;
    }

    //region Constructor


    //endregion

    //region Methods

    /**
     * Add a listener to the swipe gesture
     * @param listener listener to add for the gesture
     */
    @Override
    public void addListener(IGestureListener listener){
        listeners.add(listener);
    }

    /**
     * Remove a listener to the swipe gesture
     * @param listener listener to remove for the gesture
     */
    @Override
    public void removeListener(IGestureListener listener){
        if(listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    /**
     * Method that get the frame and detect the Gesture
     * @param newFrame frame to take care of.
     */
    @Override
    public void registerFrame(Frame newFrame){

            Hand selectedHand;

            if(handSide == Side.Left){
                selectedHand = newFrame.hands().leftmost();
            }else if(handSide == Side.Right){
                selectedHand = newFrame.hands().rightmost();
            }else{
                throw new UnsupportedOperationException("HandSide not supported !");
            }

            //Gesture detection start here ...

            //We only detect frame with a minimum of velocitiy in the palm gesture.
            if(selectedHand.isValid() && Math.abs(selectedHand.palmVelocity().getX()) >= MIN_GESTURE_VELOCITY_X_FRAME_DECTECTION) {
                frameGestureCount++;

                //Determine the direction of the initiated gesture
                currentGestureDirection = selectedHand.palmVelocity().getX() > 0 ? Side.Right : Side.Left;

                regression.addData(selectedHand.stabilizedPalmPosition().getX(),
                                   selectedHand.stabilizedPalmPosition().getY());

                //Checking max velocity on the gesture
                //Abs use for compatibility for both gesture (right and left)
                xVelocityMax =  Math.max(Math.abs(selectedHand.palmVelocity().getX()),xVelocityMax);


                if(frameGestureCount >= GESTURE_LENGTH && //We reach the end of the gesture
                   xVelocityMax >= MAX_GESTURE_VELOCITY_X_VALIDATION && //The max velocity is OK
                    (regression.getR() >= MIN_R || regression.getR() <= -1 * MIN_R) &&
                   (-1 * MIN_SLOPE <= regression.getSlope() && regression.getSlope() <= MIN_R)){

//                    System.out.println("!!!! RIGHT SWIPE DETECTED !!!! -> Max Velo : " + xVelocityMax);
//                    System.out.println("Slope : " + regression.getSlope());

                    //fire the event
                    listeners.forEach(l -> l.gestureDetected(new SwipeGestureInformation(currentGestureDirection)));
                }


                //If the gesture change the direction it started with we clear it.
                if ((currentGestureDirection == Side.Right && selectedHand.palmVelocity().getX() < 0) ||
                    (currentGestureDirection == Side.Left && selectedHand.palmVelocity().getX() > 0) ||
                     frameGestureCount >= GESTURE_LENGTH) { //The gesture ended we have to check several things

                    regression.clear();

                    frameGestureCount = 0;
                    xVelocityMax = 0;
                }

            }
        }

        //endregion


        //region Getter / Setter

        //endregion

}
