package leapmotion3dtest.leapmotion.gestures.information;

import javafx.beans.property.BooleanProperty;
import leapmotion3dtest.leapmotion.gestures.BaseGestureDetector;

/**
 * Created by Argon on 04.11.2016.
 */
public class PinchGestureInformation extends BaseGestureInformation{

    private boolean isPinched;
    public boolean getIsPinched(){return this.isPinched;}

    public PinchGestureInformation(boolean isPinched){
        this.isPinched = isPinched;
    }
}
