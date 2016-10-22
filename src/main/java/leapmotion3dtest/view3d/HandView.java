package leapmotion3dtest.view3d;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
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

        displayFingers = new SimpleBooleanProperty();
        displayFingers.bindBidirectional(thumbTip.visibleProperty());
        displayFingers.bindBidirectional(indexTip.visibleProperty());
        displayFingers.bindBidirectional(middleTip.visibleProperty());
        displayFingers.bindBidirectional(ringTip.visibleProperty());
        displayFingers.bindBidirectional(babyTip.visibleProperty());

        displayPalm = new SimpleBooleanProperty();
        displayPalm.bindBidirectional(palm.visibleProperty());
    }

    //endregion

    //region Properties

    public BooleanProperty displayFingers;
    public BooleanProperty displayPalm;

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

        Point3D thumpPos = setFingerTipPos(thumbTip,hand, Finger.Type.TYPE_THUMB);
        Point3D indexPos = setFingerTipPos(indexTip,hand, Finger.Type.TYPE_INDEX);
        Point3D middlePos = setFingerTipPos(middleTip,hand, Finger.Type.TYPE_MIDDLE);
        Point3D ringPos = setFingerTipPos(ringTip,hand, Finger.Type.TYPE_RING);
        Point3D pinkyPos = setFingerTipPos(babyTip,hand, Finger.Type.TYPE_PINKY);

        Point3D palmPos = new Point3D(hand.palmPosition().getX(),-1* hand.palmPosition().getY(),-1*hand.palmPosition().getZ());
    }

    private Point3D setFingerTipPos(Sphere tipView, Hand hand, Finger.Type type){
        tipView.setTranslateX(hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getX());
        tipView.setTranslateY(-1 * hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getY());
        tipView.setTranslateZ(-1 * hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getZ());

        return new Point3D(
                hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getX(),
                -1 * hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getY(),
                -1 * hand.fingers().fingerType(type).get(0).stabilizedTipPosition().getZ());
    }

    //endregion

    //region Accessor / Gettor

    //endregion

}


