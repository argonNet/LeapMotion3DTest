package leapmotion3dtest.leapmotion.gestures;

import leapmotion3dtest.leapmotion.gestures.information.BaseGestureInformation;

/**
 * Interface to implement to be notified from Gesture
 */
public interface IGestureListener {
    void gestureDetected(BaseGestureInformation gestureInfo);
}
