package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all the gesture
 */
public abstract class BaseGestureDetector implements IGestureDetector {

    //region Enum / Constants / Variables

    public enum Side{
        Left,
        Right
    }

    protected Side handSide;

    protected List<IGestureListener> listeners;

    private boolean detectionActivated;

    //endregion


    //region Constructor

    /**
     *
     */
    public BaseGestureDetector(Side handSide){
        listeners = new ArrayList<>();
        this.handSide = handSide;

        startDetection();
    }

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
    public void registerFrame(Frame newFrame) {

        if(detectionActivated) {
            Hand selectedHand;
            if (handSide == Side.Left) {
                selectedHand = newFrame.hands().leftmost();
            } else if (handSide == Side.Right) {
                selectedHand = newFrame.hands().rightmost();
            } else {
                throw new UnsupportedOperationException("HandSide not supported !");
            }

            onFrameRegistered(selectedHand);
        }
    }

    protected abstract void onFrameRegistered(Hand selectedHand);

    protected void startDetection(){
        detectionActivated = true;
    }

    protected  void stopDetection(){
        detectionActivated = false;
    }

    //endregion


    //region Getter / Setter

    //endregion

}
