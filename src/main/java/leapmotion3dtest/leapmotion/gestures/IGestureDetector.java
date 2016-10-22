package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Frame;

/**
 * Interface that describe the way listener should be implemented.
 */
public interface IGestureDetector {

    void registerFrame(Frame newFrame);
    void addListener(IGestureListener listener);
    void removeListener(IGestureListener listener);
}
