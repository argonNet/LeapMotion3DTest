package leapmotion3dtest.leapmotion.gestures;

/**
 * Information about a swipe gesture :
 * - Swipe direction (right or left)
 */
public class SwipeGestureInformation extends GestureInformation{

    private SwipeGestureDetector.Side direction;

    public SwipeGestureDetector.Side getDirection(){return direction;}

    public SwipeGestureInformation(SwipeGestureDetector.Side direction){
        this.direction = direction;
    }



}
