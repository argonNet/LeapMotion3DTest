package leapmotion3dtest.leapmotion.gestures;

/**
 * Information about a swipe gesture :
 * - Swipe direction (right or left)
 */
public class SwipeGestureInformation extends GestureInformation{

    private SwipeGestureDetector.Side direction;
    public SwipeGestureDetector.Side getDirection(){return direction;}

    private double maxVelocityDetected;
    public  double getMaxVelocityDetected(){return this.maxVelocityDetected;}

    private double minVelocityDetected;
    public  double getMinVelocityDetected(){return this.minVelocityDetected;}

    public SwipeGestureInformation(
            SwipeGestureDetector.Side direction,
            double minVelocityDetected,
            double maxVelocityDetected){
        this.direction = direction;
        this.minVelocityDetected = minVelocityDetected;
        this.maxVelocityDetected = maxVelocityDetected;
    }



}
