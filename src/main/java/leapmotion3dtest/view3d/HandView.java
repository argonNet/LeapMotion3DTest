package leapmotion3dtest.view3d;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Hand;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.shape.Sphere;

import java.util.ArrayList;

/**
 * Represent a hand in the 3d World
 */
public class HandView {

    //region Constants / Variables

    private final static int PALM_SIZE = 30;
    private final static int FINGER_TIP_SIZE = 10;




    private Sphere palm;

    private ArrayList<ArrayList<Sphere>> articulationAndTips;
//
//    private Sphere thumbTip;
//    private Sphere indexTip;
//    private Sphere middleTip;
//    private Sphere ringTip;
//    private Sphere babyTip;

    //endregion

    //region Constructor

    public HandView(){

        palm = new Sphere(PALM_SIZE);

//        thumbTip = new Sphere(FINGER_TIP_SIZE);
//        indexTip = new Sphere(FINGER_TIP_SIZE);
//        middleTip  = new Sphere(FINGER_TIP_SIZE);
//        ringTip = new Sphere(FINGER_TIP_SIZE);
//        babyTip = new Sphere(FINGER_TIP_SIZE);

        articulationAndTips = new ArrayList<>();

        //Creating 20 sphere for each articulation;
        //for(int i = 0; i < 20;i++) articulationAndTips.add(new Sphere(FINGER_TIP_SIZE));

        for(int f = 0; f < 5; f++){
            articulationAndTips.add(new ArrayList<>());
            for(int j = 0; j < 4; j++){
                articulationAndTips.get(f).add(new Sphere(FINGER_TIP_SIZE));
            }
        }


        displayFingers = new SimpleBooleanProperty();

//        displayFingers.bindBidirectional(thumbTip.visibleProperty());
//        displayFingers.bindBidirectional(indexTip.visibleProperty());
//        displayFingers.bindBidirectional(middleTip.visibleProperty());
//        displayFingers.bindBidirectional(ringTip.visibleProperty());
//        displayFingers.bindBidirectional(babyTip.visibleProperty());

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

//        articulationAndTips.forEach((x) -> root.getChildren().add(x));

        for(int f = 0; f < 5; f++){
            for(int j = 0; j < 4; j++){
                root.getChildren().addAll(articulationAndTips.get(f).get(j));
            }
        }


        root.getChildren().addAll(
            palm/*,
            thumbTip,
            indexTip,
            middleTip,
            ringTip,
            babyTip*/);
    }

    public void setHandsPosition(Hand hand){
        palm.setTranslateX(hand.stabilizedPalmPosition().getX());
        palm.setTranslateY(-1 * hand.stabilizedPalmPosition().getY());
        palm.setTranslateZ(-1 * hand.stabilizedPalmPosition().getZ());


        for(int f = 0; f < 5; f++){
            //for(int j = 0; j < 4; j++){

            System.out.println(hand.finger(f).jointPosition(Finger.Joint.JOINT_DIP).getX());


            articulationAndTips.get(f).get(0).setTranslateX( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_DIP).getX());
            articulationAndTips.get(f).get(0).setTranslateY(  -1*hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_DIP).getY());
            articulationAndTips.get(f).get(0).setTranslateZ( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_DIP).getZ());

            articulationAndTips.get(f).get(1).setTranslateX( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_MCP).getX());
            articulationAndTips.get(f).get(1).setTranslateY( -1*hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_MCP).getY());
            articulationAndTips.get(f).get(1).setTranslateZ( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_MCP).getZ());

            articulationAndTips.get(f).get(2).setTranslateX( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_PIP).getX());
            articulationAndTips.get(f).get(2).setTranslateY( -1*hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_PIP).getY());
            articulationAndTips.get(f).get(2).setTranslateZ( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_PIP).getZ());

            articulationAndTips.get(f).get(3).setTranslateX( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_TIP).getX());
            articulationAndTips.get(f).get(3).setTranslateY( -1*hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_TIP).getY());
            articulationAndTips.get(f).get(3).setTranslateZ( hand.fingers().get(f).jointPosition(Finger.Joint.JOINT_TIP).getZ());




        }



//        Point3D thumpPos = setFingerTipPos(thumbTip,hand, Finger.Type.TYPE_THUMB);
//        Point3D indexPos = setFingerTipPos(indexTip,hand, Finger.Type.TYPE_INDEX);
//        Point3D middlePos = setFingerTipPos(middleTip,hand, Finger.Type.TYPE_MIDDLE);
//        Point3D ringPos = setFingerTipPos(ringTip,hand, Finger.Type.TYPE_RING);
//        Point3D pinkyPos = setFingerTipPos(babyTip,hand, Finger.Type.TYPE_PINKY);

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


