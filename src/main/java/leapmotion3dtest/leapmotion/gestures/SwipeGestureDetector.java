package leapmotion3dtest.leapmotion.gestures;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import javafx.geometry.Point3D;
import org.apache.commons.math.stat.regression.SimpleRegression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class detect swipe and inform her client.
 * */
public class SwipeGestureDetector implements IGestureDetector {

        //region Enum / Constants / Variables

        public enum HandSide{
            Left,
            Right
        }

        private final static int WAY_LENGTH = 100;

        private HandSide handSide;
        private Finger.Type fingerType;
        private LinkedList<Point3D> point3DList;
        private SimpleRegression regression;
        private List<IGestureListener> listeners;


        //endregion

        public SwipeGestureDetector(HandSide side, Finger.Type finger){
            handSide = side;
            fingerType = finger;

            point3DList = new LinkedList<>();
            regression = new SimpleRegression();

            listeners = new ArrayList<>();
        }

        //region Constructor


        //endregion

        //region Methods

        public void addListener(IGestureListener listener){
            listeners.add(listener);
        }

        public void removeListener(IGestureListener listener){
            if(listeners.contains(listener)) {
                listeners.remove(listener);
            }
        }

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

        public void clearFrames(){
            point3DList.clear();
            regression.clear();
        }

        public void checkLine(){
            System.out.println("Slope : " + regression.getSlope());
            System.out.println("Error : " + regression.getR());
        }

        private void fireGestureDetected(){
            listeners.forEach(l -> l.gestureDetected());
        }

        //endregion


        //region Getter / Setter

        //endregion

}
