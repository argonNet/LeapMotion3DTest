package leapmotion3dtest.leapmotion;

import com.leapmotion.leap.*;
import javafx.application.Platform;
import javafx.geometry.Point3D;
import leapmotion3dtest.view3d.View3DController;
import org.apache.commons.math.stat.regression.SimpleRegression;

import java.util.LinkedList;

/**
 * Created by Argon on 13.10.16.
 */
public class LineListener extends Listener{


        //region Constants / Variables

        public enum HandSide{
            Left,
            Right
        }

        private final static int WAY_LENGTH = 250;

        private HandSide handSide;
        private Finger.Type fingerType;
        private LinkedList<Point3D> point3DList;
        private SimpleRegression regression;



        //endregion

        public LineListener(HandSide side, Finger.Type finger){
            handSide = side;
            fingerType = finger;

            point3DList = new LinkedList<>();
            regression = new SimpleRegression();
        }

        //region Constructor


        //endregion

        //region Methods

        public void registerFrame(Frame newFrame){
            Finger finger;
            if(handSide == HandSide.Left){
                finger = newFrame.hands().leftmost().fingers().fingerType(fingerType).get(0);
            }else if(handSide == handSide.Right){
                finger = newFrame.hands().rightmost().fingers().fingerType(fingerType).get(0);
            }else{
                throw new UnsupportedOperationException("HandSide not supported !");
            }

            if(finger.isValid()){
                point3DList.add(new Point3D(
                        finger.stabilizedTipPosition().getX(),
                        finger.stabilizedTipPosition().getY(),
                        finger.stabilizedTipPosition().getZ()));

                regression.addData(
                        finger.stabilizedTipPosition().getX(),
                        finger.stabilizedTipPosition().getY());
            }

            if(point3DList.size() >= WAY_LENGTH){
                regression.removeData(point3DList.getFirst().getX(),point3DList.getFirst().getY());
                point3DList.removeFirst();

                checkLine();
            }
        }

        private void checkLine(){
            System.out.println("Slope : " + regression.getSlope());
        }
        //endregion

        //region Getter / Setter
        //endregion

}
