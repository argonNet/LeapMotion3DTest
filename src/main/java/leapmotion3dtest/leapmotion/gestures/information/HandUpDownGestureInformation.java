package leapmotion3dtest.leapmotion.gestures.information;

/**
 * Created by Argon on 30.10.2016.
 */
public class HandUpDownGestureInformation  extends  BaseGestureInformation{

    public enum UpDownStatus{
        Up,
        Down
    }

    private UpDownStatus upDownStatus;
    public UpDownStatus getUpDownStatus(){
        return upDownStatus;
    }

    public HandUpDownGestureInformation(UpDownStatus upDownStatus){
        this.upDownStatus = upDownStatus;
    }


}
