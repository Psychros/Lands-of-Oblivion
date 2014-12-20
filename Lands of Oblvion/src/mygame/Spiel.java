/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import blöcke.Eichenbretter;
import blöcke.Eichenstamm;
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
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.texture.Texture.WrapMode;
import strukturen.Lagerhaus_Stufe1;

/**
 *
 * @author To
 */
public class Spiel extends AbstractAppState implements ActionListener{
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
    private Vector3f walkDirection;
    private boolean vorwärts = false, rückwärts = false, links = false, rechts = false;
    private float speed = 8;
    Inventar inventar;
    
    //Physik
    public static BulletAppState bulletAppState;
    
    
    //Mappings
    public static final String LINKS         = "Links";
    public static final String RECHTS        = "Rechts";
    public static final String VORWÄRTS      = "Vorwärts";
    public static final String RÜCKWÄRTS     = "Rückwärts";
    public static final String SPRINGEN      = "Springen";
    public static final String INVENTAR      = "Inventar öffnen oder schließen";

    
    
    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public void setBulletAppState(BulletAppState bulletAppState) {
        this.bulletAppState = bulletAppState;
    }
    
    
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
        this.app.setDisplayStatView(false);
        
        //Farbe des Himmels einstellen
        this.app.getViewPort().setBackgroundColor(ColorRGBA.Cyan);
        
        //Physik
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        
        initBoden();
        initPlayer();
        initMappings();
        initLight();
        
        //Den Mauszeiger unsichtbar machen
        inputManager.setCursorVisible(false);
        
        Lagerhaus_Stufe1 lager = new Lagerhaus_Stufe1();
        lager.buildStructure(rootNode, 0, 0);
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
        
        /*Eichenstamm block = new Eichenstamm(0, 1, 0);
        rootNode.attachChild(block);
        Eichenbretter block2 = new Eichenbretter(0, 2, 0);
        rootNode.attachChild(block2);
        Eichenstamm block3 = new Eichenstamm(1, 1, 0);
        rootNode.attachChild(block3);
        Eichenstamm block4 = new Eichenstamm(0, 3, 0);
        rootNode.attachChild(block4);
        Eichenstamm block5 = new Eichenstamm(0, 4, 0);
        rootNode.attachChild(block5);
        Eichenstamm block6 = new Eichenstamm(0, 5, 0);
        rootNode.attachChild(block6);*/
    }
    
    
    /*
     * Initialisieren des Spielers und das ersetzen der FlyCam durch eine eigene Kamera
     */
    public void initPlayer(){
        //Der eigentliche Spieler
        playerNode = new Node("Player");
        playerNode.setLocalTranslation(0, 3, 0);
        rootNode.attachChild(playerNode);
        
        //Initialisierung des BetterPlayerControls
        playerControl = new BetterCharacterControl(1.5f, 3, 80);
        playerControl.setJumpForce(new Vector3f(0, 600, 0));
        playerControl.setGravity(new Vector3f(9, -9.81f, 0));
        playerNode.addControl(playerControl);
        bulletAppState.getPhysicsSpace().add(playerControl);
        
        //Laufrichtung initialisieren
        walkDirection = new Vector3f(0, 0, 0);
        
        //Inventar initialisieren
        inventar = new Inventar(app);
    }
    
    
    /*
     * Initialisiere alle Tastendrücke
     */
    public void initMappings(){
        //Spieler bewegen
        inputManager.addMapping(LINKS, new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping(RECHTS, new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping(VORWÄRTS, new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping(RÜCKWÄRTS, new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping(SPRINGEN, new KeyTrigger(KeyInput.KEY_SPACE));
        
        //Menüs
        inputManager.addMapping(INVENTAR, new KeyTrigger(KeyInput.KEY_E));
        
        //Listener registrieren
        inputManager.addListener(this, LINKS, RECHTS, VORWÄRTS, RÜCKWÄRTS, SPRINGEN);
        inputManager.addListener(this, INVENTAR);
    }
    
    
    /*
     * Lichter intialisieren
     */
    public void initLight(){
        //Licht
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun); 
        
        //Schatten
        DirectionalLightShadowRenderer dlsr = new DirectionalLightShadowRenderer(assetManager, 10000, 1);
        dlsr.setLight(sun);
        app.getViewPort().addProcessor(dlsr);
        rootNode.setShadowMode(ShadowMode.CastAndReceive);
    }
    
    
    @Override
    public void update(float tpf){
        /*
         * Spieler bewegen oder rotieren
         */
        //Bestimmen, wo vorne und wo links liegt
        Vector3f vorwärtsRichtung = new Vector3f(cam.getDirection().getX(), 0, cam.getDirection().getZ());
        Vector3f linksRichtung = new Vector3f(cam.getLeft().getX(), 0, cam.getLeft().getZ());
        
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
        
        //Spieler rotieren
        cam.setLocation(new Vector3f(playerNode.getLocalTranslation().getX(), playerNode.getLocalTranslation().getY()+3, playerNode.getLocalTranslation().getZ()));
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
            case SPRINGEN     : playerControl.jump()   ; break;
            case INVENTAR     : if(!inventar.isOffen())inventar.öffnen(); 
                                else inventar.schließen();
                                break;
        }
    }
}