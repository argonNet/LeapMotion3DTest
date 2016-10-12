package leapmotion3dtest.view3d;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import javafx.scene.Group;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;

/**
 * Represent a hand in the 3d World
 */
public class HandView {

    //region Constants / Variables

    private final static int PALM_SIZE = 30;
    private final static int FINGER_TIP_SIZE = 10;

    private Sphere palm;

    private Sphere thumbTip;
    private Sphere indexTip;
    private Sphere middleTip;
    private Sphere ringTip;
    private Sphere babyTip;

    //endregion

    //region Constructor

    public HandView(){
        palm = new Sphere(PALM_SIZE);

        thumbTip = new Sphere(FINGER_TIP_SIZE);
        indexTip = new Sphere(FINGER_TIP_SIZE);
        middleTip  = new Sphere(FINGER_TIP_SIZE);
        ringTip = new Sphere(FINGER_TIP_SIZE);
        babyTip = new Sphere(FINGER_TIP_SIZE);
    }

    //endregion

    //region Methods

    public void addToGroup(Group root){
        root.getChildren().addAll(
            palm,
            thumbTip,
            indexTip,
            middleTip,
            ringTip,
            babyTip);
    }

    public void setHandsPosition(Hand hand){
        palm.setTranslateX(hand.stabilizedPalmPosition().getX());
        palm.setTranslateY(-1 * hand.stabilizedPalmPosition().getY());
        palm.setTranslateZ(-1 * hand.stabilizedPalmPosition().getZ());

        setFingerTipPos(thumbTip,hand, Finger.Type.TYPE_THUMB);
        setFingerTipPos(indexTip,hand, Finger.Type.TYPE_INDEX);
        setFingerTipPos(middleTip,hand, Finger.Type.TYPE_MIDDLE);
        setFingerTipPos(ringTip,hand, Finger.Type.TYPE_RING);
        setFingerTipPos(babyTip,hand, Finger.Type.TYPE_PINKY);
    }

    private void setFingerTipPos(Sphere tipView, Hand hand, Finger.Type type){
        tipView.setTranslateX(hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getX());
        tipView.setTranslateY(-1 * hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getY());
        tipView.setTranslateZ(-1 * hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getZ());
    }

    //endregion

    //region Accessor / Gettor

    //endregion

}


