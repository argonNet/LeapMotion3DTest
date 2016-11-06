package leapmotion3dtest.leapmotion.gestures.information;

import leapmotion3dtest.leapmotion.gestures.BaseGestureDetector;

/**
 * Information about a swipe gesture :
 * - Swipe direction (right or left)
 */
public class SwipeGestureInformation extends BaseGestureInformation {

    private BaseGestureDetector.Side direction;
    public BaseGestureDetector.Side getDirection(){return direction;}

    private double maxVelocityDetected;
    public  double getMaxVelocityDetected(){return this.maxVelocityDetected;}

    private double minVelocityDetected;
    public  double getMinVelocityDetected(){return this.minVelocityDetected;}

    private double distance;
    public double getDistance(){return this.distance;}

    public SwipeGestureInformation(
            BaseGestureDetector.Side direction,
            double minVelocityDetected,
            double maxVelocityDetected,
            double distance){
        this.direction = direction;
        this.minVelocityDetected = minVelocityDetected;
        this.maxVelocityDetected = maxVelocityDetected;
        this.distance = distance;
    }



}
