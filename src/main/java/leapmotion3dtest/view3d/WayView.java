package leapmotion3dtest.view3d;

import javafx.scene.Group;
import javafx.scene.shape.Sphere;

import java.util.LinkedList;

/**
 * Created by Argon on 12.10.16.
 */
public class WayView {

    //region Constants / Variables

    private final static int WAY_LENGTH = 250;
    private final static int POINT_SIZE = 3;

    private Group root;

    private LinkedList<Sphere> way;

    //endregion

    //region Constructor

    public WayView(Group root){
        way = new LinkedList<>();

        this.root = root;
    }

    //endregion

    //region Methods

    public void addWayPoint(double x, double y, double z){
        Sphere newSphere = new Sphere(POINT_SIZE);

        newSphere.setTranslateX(x);
        newSphere.setTranslateY(-1 * y);
        newSphere.setTranslateZ(-1 * z);

        root.getChildren().add(newSphere);

        way.addFirst(newSphere);
        if(way.size() >= 50){
            root.getChildren().remove(way.getLast());
            way.removeLast();
        }

    }

    //endregion

    //region Accessor / Gettor
    //endregion

}



