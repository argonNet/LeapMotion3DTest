package leapmotion3dtest.leapmotion.gestures.information;

/**
 * Created by Argon on 30.10.2016.
 */
public class HandOpenCloseGestureInformation extends BaseGestureInformation {

    public enum CloseOpenStatus{
        Opening,
        Closing
    }

    private CloseOpenStatus closeOpenStatus;
    public CloseOpenStatus getCloseOpenStatus(){
        return closeOpenStatus;
    }

    public HandOpenCloseGestureInformation(CloseOpenStatus closeOpenStatus){
        this.closeOpenStatus = closeOpenStatus;
    }

}
