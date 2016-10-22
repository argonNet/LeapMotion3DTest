package leapmotion3dtest.leapmotion;

import com.leapmotion.leap.Frame;

/**
 * Interface to implement to monitor LeapMotion data.
 */
public interface IMonitorListener {

    void newFrameArrived(Frame newFrame);

}
