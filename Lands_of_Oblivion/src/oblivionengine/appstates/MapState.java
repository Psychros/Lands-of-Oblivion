/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.filters.BloomFilter.GlowMode;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.post.filters.FogFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import oblivionengine.CharakterControl;
import oblivionengine.Game;
import oblivionengine.Map;

/**
 *
 * @author To
 */
public class MapState extends AbstractAppState implements ActionListener, AnalogListener{
    
    //Mappings
    public enum InputMapping{
        RotateLeft, RotateRight, LookUp, LookDown, StrafeLeft, StrafeRight, MoveForward, MoveBackward, Jump, Run, CutTree, Cheatmenü;
    }
    
    //--------------------------------------------------------------------------
    //Objektvariablen
    private InputManager inputManager;
    private CharakterControl player;
    private Node playerNode;
    
    private Map map;    //Ist eine Referenz auf activeMap in der Klasse Game
    private Picture cursor;
    
    
    //Filter
    private FilterPostProcessor effects;
    private DepthOfFieldFilter dofFilter;   //Fokussierung der Kamera
    private SSAOFilter ssaoFilter;          //weiche Schatten
    private BloomFilter bloomFilter;
    private FogFilter fogFilter;
    private DirectionalLightShadowFilter dlsf;
    private DirectionalLightShadowRenderer dlsr;
    private BasicShadowRenderer bsr;
    private PssmShadowRenderer pssm;
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public MapState() {       
        //FilterPostProcessor initialisieren
        effects = new FilterPostProcessor(Game.game.getAssetManager());
        Game.game.getViewPort().addProcessor(effects);
    }
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    
    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        Game.game.getRootNode().detachChild(this.map);
        this.map = map;
        Game.game.setActiveMap(map);
    }  

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    @Override
    public void initialize(AppStateManager stateManager, Application app){ 
        super.initialize(stateManager, app);
        
        //Map erstellen
        Node a = (Node)Game.game.getAssetManager().loadModel("Scenes/Insel1.j3o");
        map = new Map((TerrainQuad)a.getChild(0), "Startinsel");
        Game.game.setActiveMap(map);
        
        //Himmel
        Node b = (Node)Game.game.getAssetManager().loadModel("Scenes/Himmel.j3o");
        map.attachChild(b);
        
        //Wasser initialisieren
        effects = (FilterPostProcessor)Game.game.getAssetManager().loadFilter("Effects/Wasser.j3f");
        
        //Schatten initialisieren
        activateShadowFilter(true);
        
        //Effekte(Wasser und Schatten) aktivieren
        Game.game.getViewPort().addProcessor(effects);
        
        
        //Verhindern, dass gezoomt werden kann
        Game.game.getFlyCam().setZoomSpeed(0);
        
        //Player initialisieren
        Node playerNode = new Node("Player");
        Node node = (Node)(Game.game.getAssetManager().loadModel("Models/Player.j3o"));
        node.scale(2.6f);
        playerNode.attachChild(node);
        map.attachChild(playerNode);
        
        player = new CharakterControl(0.5f, 2.5f, 8);
        player.setCamera(Game.game.getCamera());
        player.addSpatial(node);
        playerNode.addControl(player);
        
        map.getBulletAppState().getPhysicsSpace().add(player);
        
        //InputManager initialisieren
        inputManager = Game.game.getInputManager();
        addInputMappings();
        
        //Spieler zum Startpunkt warpen
        player.warp(new Vector3f(0, map.getTerrain().getHeight(Vector2f.ZERO), 0));
    }
    
    @Override
    public void update(float tpf){
        
        Camera cam = Game.game.getCam();
        
        //Fokussierung der Kamera auf ein Objekt aktualisieren
        if(dofFilter != null){
            //Per RayCasting das anvisierte Objekt ermitteln
            Ray ray = new Ray(cam.getLocation(), cam.getDirection());
            CollisionResults results = new CollisionResults();
            int numCollisions = map.collideWith(ray, results);
            if(numCollisions > 0){
                CollisionResult hit = results.getClosestCollision();
                dofFilter.setFocusDistance(hit.getDistance() / 10);
            }
        }
        
        
        //Bewegung der Sonne
        map.getSunLight().setDirection(map.getSunLight().getDirection().add(tpf, -10f*tpf, 0));
    }
    
    /*
     * wichtige Tasten aktivieren und deaktivieren
     */
    private void addInputMappings(){
        //Mappings erstellen
        inputManager.addMapping(InputMapping.RotateLeft.name(), new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping(InputMapping.RotateRight.name(), new MouseAxisTrigger(MouseInput.AXIS_X, false));
        inputManager.addMapping(InputMapping.LookUp.name(), new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addMapping(InputMapping.LookDown.name(), new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        
        inputManager.addMapping(InputMapping.StrafeLeft.name(), new KeyTrigger(KeyInput.KEY_A), new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(InputMapping.StrafeRight.name(), new KeyTrigger(KeyInput.KEY_D), new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(InputMapping.MoveForward.name(), new KeyTrigger(KeyInput.KEY_W), new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping(InputMapping.MoveBackward.name(), new KeyTrigger(KeyInput.KEY_S), new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addMapping(InputMapping.Run.name(), new KeyTrigger(KeyInput.KEY_LSHIFT), new KeyTrigger(KeyInput.KEY_RCONTROL));
        inputManager.addMapping(InputMapping.Jump.name(), new KeyTrigger(KeyInput.KEY_SPACE));
   
        inputManager.addMapping(InputMapping.CutTree.name(), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        inputManager.addMapping(InputMapping.Cheatmenü.name(), new KeyTrigger(KeyInput.KEY_J));
        //Listener aktivieren
        for(InputMapping i: InputMapping.values()){
            inputManager.addListener(this, i.name());
        }
    }
    
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (parseKeys(name)) return;
        if(player != null){
            player.onAction(name, isPressed, tpf);
        }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if(player != null){
            player.onAnalog(name, value, tpf);
        }
    }
    
    

    @Override
    public void cleanup() {
        super.cleanup(); 
        for (InputMapping i : InputMapping.values()) {
            if(inputManager.hasMapping(i.name())){
                inputManager.deleteMapping(i.name());
            }
        }
        inputManager.removeListener(this);
    }
    
    
    
    /*
     * Fokussierung der Kamera auf ein Objekt aktivieren
     */
    public void activateDepthOfFieldFilter(boolean value){
        if(value){
            if(dofFilter == null){
                dofFilter = new DepthOfFieldFilter();
                effects.addFilter(dofFilter);
            }
        } else{
            if(dofFilter != null){
                effects.removeFilter(dofFilter);
                dofFilter = null;
            }
        }
    }
    
    
    public void activateDepthOfFieldFilter(DepthOfFieldFilter dofFilter){
        if(this.dofFilter == null){
            this.dofFilter = dofFilter;
            effects.addFilter(this.dofFilter);
        }
    }
    
    /*
     * Aktiviert weiche Schatten
     */
    public void activateSSAOFilter(boolean value){
        if(value){
            if(ssaoFilter == null){
                ssaoFilter = new SSAOFilter(1f, 1.5f, 0.1f, 0.1f);
                effects.addFilter(ssaoFilter);
            }
        } else{
            if(ssaoFilter != null){
                effects.removeFilter(ssaoFilter);
                ssaoFilter = null;
            }
        }
    }
    
    public void activateSSAOFilter(){
        if(ssaoFilter == null){
            SSAOFilter ssaoFilter = new SSAOFilter(12.940201f, 43.928635f, 0.32999992f, 0.6059958f);
            effects.addFilter(ssaoFilter);
        }
    }
    
    /*
     * Lässt die Szene leuchten
     */
    public void activateBloomFilter(boolean value){
        if(value){
            if(bloomFilter == null){
                bloomFilter = new BloomFilter();
                effects.addFilter(bloomFilter);
            }
        } else{
            if(bloomFilter != null){
                effects.removeFilter(bloomFilter);
                bloomFilter = null;
            }
        }
    }
    
    public void activateBloomFilter(GlowMode glowMode){
        if(bloomFilter == null){
            bloomFilter = new BloomFilter(glowMode);
            effects.addFilter(bloomFilter);
        }
    }
    
    public void activateBloomFilter(BloomFilter bloomFilter){
        if(this.bloomFilter == null){
            this.bloomFilter = bloomFilter;
            effects.addFilter(this.bloomFilter);
        }
    }
    
    //Schatten für ein Sonnenlicht
    public void activateShadowRenderer(boolean value){
        if(value){
            if(dlsr == null){
                dlsr = new DirectionalLightShadowRenderer(Game.game.getAssetManager(), 1024, 2);
                dlsr.setLight(map.getSunLight());
                Game.game.getViewPort().addProcessor(dlsr); 
            }
        } else{
            if(dlsr != null){
                Game.game.getViewPort().removeProcessor(dlsr);
                dlsr = null;
            }
        }
    }
    
    public void activateBasicShadowRenderer(boolean value){
        if(value){
            if(bsr == null){
                bsr = new BasicShadowRenderer(Game.game.getAssetManager(), 1024);
                bsr.setDirection(new Vector3f(.3f, -0.5f, -0.5f));
                Game.game.getViewPort().addProcessor(bsr); // add one or more sceneprocessor to viewport

            }
        } else{
            if(dlsr != null){
                Game.game.getViewPort().removeProcessor(dlsr);
                dlsr = null;
            }
        }
    }   
    
    
    public void activatePssmShadowRenderer(boolean value){
        if(value){
            if(pssm == null){
                pssm = new PssmShadowRenderer(Game.game.getAssetManager(), 1024, 2);
                Game.game.getViewPort().addProcessor(pssm); 
            }
        } else{
            if(pssm != null){
                Game.game.getViewPort().removeProcessor(pssm);
                pssm = null;
            }
        }
    }
    
    public void activateShadowFilter(boolean value){
        if(value){
            if(dlsf == null && map.getSunLight() != null){
                dlsf = new DirectionalLightShadowFilter(Game.game.getAssetManager(), 1024, 1);
                dlsf.setLight(map.getSunLight());
                dlsf.setShadowZExtend(300);
                effects.addFilter(dlsf);
            }
        } else{
            if(dlsf != null){
                effects.removeFilter(dlsf);
                dlsf = null;
            }
        }
    }
    
    /*
     * Fadenkreuz anzeigen
     */
    public void activateCursor(boolean value) {
        if(value){
            if(cursor == null){
                cursor = new Picture("Cursor");
                cursor.setImage(Game.game.getAssetManager(), "Interface/Cursor.png", true);
                cursor.move(Game.game.getSettings().getWidth()/2-30, Game.game.getSettings().getHeight()/2-30, 0);
                cursor.setWidth(60);
                cursor.setHeight(60);
                Game.game.getGuiNode().attachChild(cursor);
            }
        } else{
            if(cursor != null){
                Game.game.getGuiNode().detachChild(cursor);
                cursor = null;
            }
        }
    }
    
    public void activateCursor(String path) {
        if(cursor == null){
            cursor = new Picture("Cursor");
            cursor.setImage(Game.game.getAssetManager(), path, true);
            cursor.move(Game.game.getSettings().getWidth()/2-30, 0, Game.game.getSettings().getHeight()/2-30);
            cursor.setWidth(60);
            cursor.setHeight(60);
            Game.game.getGuiNode().attachChild(cursor);
        }
    }
    
    private boolean parseKeys(String name){
        boolean returned = false;
        if (name.equals(InputMapping.Cheatmenü.name())){
            Game.game.getScreens().switchToCheatmenü();
            returned = true;
        }
        return returned;
    }
}
