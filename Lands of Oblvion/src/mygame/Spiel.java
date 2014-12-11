/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture.WrapMode;

/**
 *
 * @author To
 */
public class Spiel extends AbstractAppState implements ActionListener, AnalogListener{
    //Standart variablen, die von der SimpleApplicaton kommen
    private SimpleApplication app;
    private AssetManager assetManager;
    private InputManager inputManager;
    private Camera cam;
    private FlyByCamera flyCam;
    private Node rootNode;
    private Node guiNode;
    
    //Spieler
    private Node playerNode;
    private BetterCharacterControl playerControl;
    private CameraNode camNode;
    private Vector3f walkDirection;
    private Vector3f viewDirection;
    private boolean rotateLeft = false, rotateRight = false, rotateUp = false, rotateDown = false,
                    vorwärts = false, rückwärts = false, links = false, rechts = false;
    private float speed = 8;
    
    //Physik
    BulletAppState bulletAppState;
    
    //Mappings
    public static final String LINKS         = "Links";
    public static final String RECHTS        = "Rechts";
    public static final String VORWÄRTS      = "Vorwärts";
    public static final String RÜCKWÄRTS     = "Rückwärts";
    public static final String SPRINGEN      = "Springen";
    public static final String KAMERA_LINKS  = "Kamera nach links";
    public static final String KAMERA_RECHTS = "Kamera nach rechts";
    public static final String KAMERA_UNTEN  = "Kamera nach unten";
    public static final String KAMERA_OBEN   = "Kamera nach oben";
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){ 
        //Initialisieren des AppStates
        super.initialize(stateManager, app);
        this.app          = (SimpleApplication) app;
        this.cam          = this.app.getCamera();
        this.flyCam       = this.app.getFlyByCamera();
        this.rootNode     = this.app.getRootNode();
        this.guiNode      = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.inputManager = this.app.getInputManager();
        
        this.app.getViewPort().setBackgroundColor(ColorRGBA.Cyan);
        
        //Physik
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        initBoden();
        initPlayer();
        initMappings();
        initLight();
    }
    
    
    /*
     * Initialisieren des Bodens der Welt, bei dem es sich um eine flache Ebene handelt
     */
    public void initBoden(){
        Quad quad = new Quad(100, 100);
        quad.scaleTextureCoordinates(new Vector2f(100, 100));
        Geometry geom = new Geometry("Boden", quad);
        geom.setLocalTranslation(-50, 0, 50);
        geom.rotate(-FastMath.DEG_TO_RAD*90, 0, 0);
        
        Material gras = assetManager.loadMaterial("Materials/Boden.j3m");
        gras.getTextureParam("DiffuseMap").getTextureValue().setWrap(WrapMode.Repeat);
        geom.setMaterial(gras);
        
        RigidBodyControl bodenPhy = new RigidBodyControl(0);
        geom.addControl(bodenPhy);
        bulletAppState.getPhysicsSpace().add(bodenPhy);
        
        rootNode.attachChild(geom);
    }
    
    
    /*
     * Initialisieren des Spielers und das ersetzen der FlyCam durch eine eigene Kamera
     */
    public void initPlayer(){
        //Der eigentliche Spieler
        playerNode = new Node("Player");
        playerNode.setLocalTranslation(0, 5, 0);
        rootNode.attachChild(playerNode);
        
        //Initialisierung des BetterPlayerControls
        playerControl = new BetterCharacterControl(1.5f, 4, 80);
        playerControl.setJumpForce(new Vector3f(0, 600, 0));
        playerControl.setGravity(new Vector3f(9, -9.81f, 0));
        playerNode.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
        
        //Kamera initialisieren
        camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(0, 4, 0);
        Quaternion quat = new Quaternion();
        quat.lookAt(Vector3f.UNIT_Z, Vector3f.UNIT_Z);
        camNode.setLocalRotation(quat);
        playerNode.attachChild(camNode);
        camNode.setEnabled(true);
        flyCam.setEnabled(false);
        
        walkDirection = new Vector3f(0, 0, 0);
        viewDirection = new Vector3f(0, 0, 0);
    }
    
    
    /*
     * Initialisiere alle Tastendrücke
     */
    public void initMappings(){
        //Bewegen
        inputManager.addMapping(LINKS, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(RECHTS, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(VORWÄRTS, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(RÜCKWÄRTS, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(SPRINGEN, new KeyTrigger(KeyInput.KEY_SPACE));
        
        //Kamera rotieren
        inputManager.addMapping(KAMERA_LINKS , new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(KAMERA_RECHTS, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(KAMERA_OBEN  , new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(KAMERA_UNTEN , new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        
        //Listener registrieren
        inputManager.addListener(this, LINKS, RECHTS, VORWÄRTS, RÜCKWÄRTS, SPRINGEN);
        inputManager.addListener(this, KAMERA_LINKS, KAMERA_RECHTS, KAMERA_OBEN, KAMERA_UNTEN);
    }
    
    
    /*
     * Lichter intialisieren
     */
    public void initLight(){
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
    }
    
    
    @Override
    public void update(float tpf){
        /*
         * Spieler bewegen oder rotieren
         */
        //Bestimmen, wo vorne und wo links liegt
        Vector3f vorwärtsRichtung = playerNode.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f linksRichtung = playerNode.getWorldRotation().mult(Vector3f.UNIT_X);
        
        //Spieler nach vorne, hinten, links, oder rechts bewegen
        walkDirection.set(0, 0, 0);
        if(vorwärts){
            walkDirection.addLocal(vorwärtsRichtung.mult(speed));
        } 
        if(rückwärts){
            walkDirection.addLocal(vorwärtsRichtung.mult(speed).negate());
        } 
        if(links){
            walkDirection.addLocal(linksRichtung.mult(speed));
        } 
        if(rechts){
            walkDirection.addLocal(linksRichtung.mult(speed).negate());
        }
        playerControl.setWalkDirection(walkDirection);
    }
    
    
     /*
     * Aktionen, die bei einem Tastendruck ausgeführt werden
     */
    @Override
    public void onAction(String name, boolean isPressed, float tpf){
        switch(name){
            case VORWÄRTS     : vorwärts    = isPressed; break;
            case RÜCKWÄRTS    : rückwärts   = isPressed; break;
            case LINKS        : links       = isPressed; break;
            case RECHTS       : rechts      = isPressed; break;
            case KAMERA_LINKS : rotateLeft  = isPressed; break;
            case KAMERA_OBEN  : rotateUp    = isPressed; break;
            case KAMERA_RECHTS: rotateRight = isPressed; break;
            case KAMERA_UNTEN : rotateDown  = isPressed; break;
            case SPRINGEN     : playerControl.jump()   ; break;
        }
    }
    
    @Override
    public void onAnalog(String name, float value, float tpf) {
        
    }
}
