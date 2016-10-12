package leapmotion3dtest.view3d;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/**
 * Create the 3D environnment for a given SubScene object
 */
public class View3DController{

    //region Constants / Variables

    private final static double INTERACTION_BOX_CENTER_X = 0;
    private final static double INTERACTION_BOX_CENTER_Y = -200;
    private final static double INTERACTION_BOX_CENTER_Z = 0;

    private final static double INTERACTION_BOX_WIDTH = 235;
    private final static double INTERACTION_BOX_HEIGHT = 235;
    private final static double INTERACTION_BOX_DEPTH = 147 ;

    private final static double GROUND_WIDTH = 10000;
    private final static double GROUND_DEPTH = 10000;


    public DoubleProperty cameraZoom;
    public DoubleProperty cameraPositionAlphaAngle;
    public DoubleProperty cameraPositionBetaAngle;

    public HandView rightHand;
    public HandView leftHand;

    private SubScene subScene;

    private Group root;
    private Box interactionBox;
    private PerspectiveCamera camera;

    private Rotate cameraLookAtXRotate;
    private Rotate cameraLookAtYRotate;


    //endregion

    //region Constructor

    /**
     * Constructor, this method initialize the 3D environment
     * @param subScene
     */
    public  View3DController(SubScene subScene){
        this.subScene = subScene;

        cameraZoom = new SimpleDoubleProperty();
        cameraPositionAlphaAngle = new SimpleDoubleProperty();
        cameraPositionBetaAngle = new SimpleDoubleProperty();

        cameraZoom.addListener((ObservableValue<? extends Number> observable, Number o, Number n) -> {setDefaultCameraPosition();});
        cameraPositionAlphaAngle.addListener((ObservableValue<? extends Number> observable, Number o, Number n) -> {setDefaultCameraPosition();});
        cameraPositionBetaAngle.addListener((ObservableValue<? extends Number> observable, Number o, Number n) -> {setDefaultCameraPosition();});

        //Rotate transformation for the camera lookat direction
        cameraLookAtYRotate = new Rotate();
        cameraLookAtYRotate.setAxis(Rotate.Y_AXIS);

        cameraLookAtXRotate = new Rotate();
        cameraLookAtXRotate.setAxis(Rotate.X_AXIS);

        // Add the Shapes and the Light to the Group
        root = new Group();

        initAxis();
        initLight();
        initInteractionBox();
        initHand();
        initCamera();
    }

    //endregion

    //region Methods

    /**
     * Calculate the position and direction of the camera regarding rotation angle
     * @param angleAlpha Rotation angle around the Y axis
     * @param angleBeta Rotation angle from the plan (made of axis X ans Z)
     */
    private void setCameraPosition(double angleAlpha, double angleBeta){
        double camPosAngleAlphaRad = Math.toRadians(cameraPositionAlphaAngle.get());
        double camPosAngleBetaRad = Math.toRadians(180 - cameraPositionBetaAngle.get());

        this.camera.setTranslateX(
                Math.round(Math.sin(camPosAngleAlphaRad) * Math.cos(camPosAngleBetaRad) * getCameraZoom()) + INTERACTION_BOX_CENTER_X);

        this.camera.setTranslateY(
                Math.round(-1 * Math.sin(camPosAngleBetaRad) * getCameraZoom()) + INTERACTION_BOX_CENTER_Y);

        this.camera.setTranslateZ(
                Math.round(Math.cos(camPosAngleAlphaRad) * Math.cos(camPosAngleBetaRad) * getCameraZoom()) + INTERACTION_BOX_CENTER_Z);

        this.cameraLookAtYRotate.setAngle(cameraPositionAlphaAngle.get());
        this.cameraLookAtXRotate.setAngle(-1 * cameraPositionBetaAngle.get());
    }

    private void setDefaultCameraPosition(){
        setCameraPosition(getCameraPositionAlphaAngle(),getCameraPositionBetaAngle());
    }

    private void initCamera(){
        camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(cameraLookAtYRotate,cameraLookAtXRotate);

        this.setCameraPositionAlphaAngle(0);
        this.setCameraPositionBetaAngle(0);
        this.setCameraZoom(900);

        camera.setNearClip(0);
        camera.setFarClip(1000);
        subScene.setCamera(camera);
    }

    private void initLight(){
        PointLight light = new PointLight();
        light.setTranslateX(100);
        light.setTranslateY(-100);
        light.setTranslateZ(0);
        root.getChildren().addAll(light);
    }

    private void initAxis(){
        /*PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);*/

        Box xAxis = new Box(500, 1, 1);
        Box yAxis = new Box(1, -1 * INTERACTION_BOX_CENTER_Y, 1);
        yAxis.setTranslateY(INTERACTION_BOX_CENTER_Y / 2);
        Box zAxis = new Box(1, 1, 500);

        /*xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);*/

        root.getChildren().addAll(xAxis,yAxis,zAxis);
    }

    private void initInteractionBox(){

        interactionBox = new Box(INTERACTION_BOX_WIDTH,INTERACTION_BOX_HEIGHT,INTERACTION_BOX_DEPTH);
        interactionBox.setTranslateY(INTERACTION_BOX_CENTER_Y);
        interactionBox.setDrawMode(DrawMode.LINE);

        Sphere center = new Sphere(5);

        Sphere interactionBoxCenter = new Sphere(5);
        interactionBoxCenter.setTranslateX(INTERACTION_BOX_CENTER_X);
        interactionBoxCenter.setTranslateY(INTERACTION_BOX_CENTER_Y);
        interactionBoxCenter.setTranslateZ(INTERACTION_BOX_CENTER_Z);

        subScene.setRoot(root);
        root.getChildren().addAll(interactionBox,center,interactionBoxCenter);

    }

    private void initHand(){
        rightHand  = new HandView();
        rightHand.addToGroup(root);

        leftHand = new HandView();
        leftHand.addToGroup(root);
    }

    //endregion

    //region Getter / Setter

    public double getCameraPositionAlphaAngle(){
        return cameraPositionAlphaAngle.get();
    }

    public void setCameraPositionAlphaAngle(double value){
        this.cameraPositionAlphaAngle.set(value);
    }

    public double getCameraPositionBetaAngle(){
        return cameraPositionBetaAngle.get();
    }

    public void setCameraPositionBetaAngle(double value){
        this.cameraPositionBetaAngle.set(value);

    }

    public double getCameraZoom(){ return this.cameraZoom.get();}

    public  void setCameraZoom(double value){
        this.cameraZoom.set(value);
    }

    //endregion

}


