/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import oblivionengine.Game;
import oblivionengine.appstates.MapState.InputMapping;
import oblivionengine.charakter.Player;

/**
 *
 * @author To
 */
public class Screens extends AbstractAppState implements ScreenController{
    //Application
    Application app;
    AppStateManager stateManager;
    AssetManager assetManager;
    //NiftyGui
    NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;
    private byte cheatmenüControl = 0;
    private byte baumenüControl   = 0;
    
    Node tree;

    public NiftyJmeDisplay getNiftyDisplay() {
        return niftyDisplay;
    }

    public Nifty getNifty() {
        return nifty;
    }
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
        this.stateManager = stateManager;
        this.assetManager = app.getAssetManager();
        
        //Niftygui initialisieren
        niftyDisplay = new NiftyJmeDisplay(assetManager, app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/Menüs/Screen.xml", "start", this);
        app.getGuiViewPort().addProcessor(niftyDisplay);
        Game.game.getFlyCam().setEnabled(false);
        
        tree = (Node)Game.game.getAssetManager().loadModel("Models/Landschaft/Baum.j3o");
        tree.scale(0.5f);
        tree.setLocalTranslation(-3.5f, -4, 0);
        Game.game.getRootNode().attachChild(tree);
    }
    
    @Override
    public void update(float tpf) {
        tree.rotate(0, 5 * FastMath.DEG_TO_RAD*tpf, 0);
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
      // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStartScreen() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEndScreen() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    } 
    
    
    
    //Der Spieler befindet sich in einem Menü und soll den Player nicht mehr bewegen können
    public void goToMenu(String menü){
        nifty.gotoScreen(menü);
        Game.game.getInputManager().setCursorVisible(true);
        nifty.setIgnoreKeyboardEvents(false);
        nifty.setIgnoreMouseEvents(false);
        
        //Mappings entfernen, die eine Aktion des Players darstellen
        InputManager inputManager = Game.game.getInputManager();
        
        inputManager.deleteMapping(InputMapping.RotateLeft.name());
        inputManager.deleteMapping(InputMapping.RotateRight.name());
        inputManager.deleteMapping(InputMapping.LookDown.name());
        inputManager.deleteMapping(InputMapping.LookUp.name());
        inputManager.deleteMapping(InputMapping.StrafeLeft.name());
        inputManager.deleteMapping(InputMapping.StrafeRight.name());
        inputManager.deleteMapping(InputMapping.MoveBackward.name());
        inputManager.deleteMapping(InputMapping.MoveForward.name());       
        inputManager.deleteMapping(InputMapping.Jump.name());
        inputManager.deleteMapping(InputMapping.Run.name());
        inputManager.deleteMapping(InputMapping.CutTree.name());
        
        //Den Spieler anhalten, wenn er sich bewegt
        Game.game.mapState.getPlayer().stopPlayer();
    }
    
    
    //Der Spieler befindet sich im Spiel und soll den Player wieder bewegen können
    public void goToGame(){
        //Mappings erstellen
        InputManager inputManager = Game.game.getInputManager();
        
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
    
        //Listener aktivieren
        for(InputMapping i: InputMapping.values()){
            inputManager.addListener(Game.game.mapState, i.name());
        }
        
        nifty.gotoScreen("inGame");
        Game.game.getInputManager().setCursorVisible(false);
        nifty.setIgnoreKeyboardEvents(true);
        nifty.setIgnoreMouseEvents(true);
    }
    
    
    
    /*
     * Hauptmenü
     */
    // Bild des Buttons ändern,  wenn die Maus über diesen fährt
    public void mouseOver(String value){
        //Alle Bilder der Buttons zurücksetzen
        //Startbutton
        NiftyImage img = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), "Interface/Menüs/Hauptmenü/Startbildschirm/ButtonStart.png", false);
        Element niftyElement = nifty.getCurrentScreen().findElementByName("start");
        niftyElement.getRenderer(ImageRenderer.class).setImage(img);
        
        //Optionenbutton
        NiftyImage img2 = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), "Interface/Menüs/Hauptmenü/Startbildschirm/ButtonOptionen.png", false);
        Element niftyElement2 = nifty.getCurrentScreen().findElementByName("optionen");
        niftyElement2.getRenderer(ImageRenderer.class).setImage(img2);
        
        //Endebutton
        NiftyImage img3 = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), "Interface/Menüs/Hauptmenü/Startbildschirm/ButtonEnde.png", false);
        Element niftyElement3 = nifty.getCurrentScreen().findElementByName("ende");
        niftyElement3.getRenderer(ImageRenderer.class).setImage(img3);
        
        
        //Bild des ausgewählten buttons ändern
        String[] values = value.trim().split(". ");
        NiftyImage img4 = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), values[1], false);
        Element niftyElement4 = nifty.getCurrentScreen().findElementByName(values[0]);
        niftyElement4.getRenderer(ImageRenderer.class).setImage(img4);
    }
    
    public void startGame(){
        //zum inGame Screen wechseln
        nifty.gotoScreen("inGame");
        
        //Innerhalb des eigentlichen Spiels darf man per Tastendruck nicht die GUI bedienen
        nifty.setIgnoreKeyboardEvents(true);
        nifty.setIgnoreMouseEvents(true);
        
        //Den MapState initialisieren und Tastendrücke aktivieren
        MapState mapState = new MapState();
        Game.game.initMapState(mapState);
        
        
        //Kamera kann sich wieder bewegen und der Mauszeiger wird entfernt
        Game.game.getFlyCam().setEnabled(true);
        Game.game.getInputManager().setCursorVisible(false);
        
        //Alle Objekte entfernen
        Game.game.getRootNode().detachAllChildren();
    }
    
    public void stopGame(){
        Game.game.stop();
    }
    
    public void options(){
        nifty.gotoScreen("optionen");
    }
    
    
    
    /*
     * Cheatmenü
     */
    public void switchToCheatmenü(){
        if (cheatmenüControl == 0){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){
                cheatmenüControl++;
            }
        } else if (cheatmenüControl == 1){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){   
                cheatmenüControl++;
                goToMenu("cheatmenü");
            }
        } else if (cheatmenüControl == 2){
            if (nifty.getCurrentScreen().getScreenId().equals("cheatmenü")){
                cheatmenüControl++;
            }
        } else if (cheatmenüControl == 3){
            if (nifty.getCurrentScreen().getScreenId().equals("cheatmenü")){
                cheatmenüControl = 0;
                goToGame();
            }
        }
    }
    
    public void cheat(String cheat){
        
    }
    
    
    /*
     * Optionsmenü
     */ 
    public void backToStartScreen(){
        nifty.gotoScreen("start");
    }
    
    
    /*
     * Baumenü
     */
    public void switchToBaumenü(){
        if (baumenüControl == 0){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){
                baumenüControl++;
            }
        } else if (baumenüControl == 1){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){   
                baumenüControl++;
                goToMenu("baumenü");
            }
        } else if (baumenüControl == 2){
            if (nifty.getCurrentScreen().getScreenId().equals("baumenü")){
                baumenüControl++;
            }
        } else if (baumenüControl == 3){
            if (nifty.getCurrentScreen().getScreenId().equals("baumenü")){
                baumenüControl = 0;
                goToGame();
            }
        }
    }
    
    //Gebäude auswählen
    public void chooseBuilding(String buildingID){
        Player.selectedBuilding = buildingID;
        goToGame();
        baumenüControl = 0;
    }
    
    // Bild des Buttons ändern,  wenn die Maus über diesen fährt
    public void mouseOver2(String value){
        //Alle Bilder der Buttons zurücksetzen
        //Startbutton
        NiftyImage img = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), "Interface/Menüs/Baumenü/ButtonLager.png", false);
        Element niftyElement = nifty.getCurrentScreen().findElementByName("lager");
        niftyElement.getRenderer(ImageRenderer.class).setImage(img);
        
        
        //Bild des ausgewählten buttons ändern
        String[] values = value.trim().split(". ");
        NiftyImage img4 = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), values[1], false);
        Element niftyElement4 = nifty.getCurrentScreen().findElementByName(values[0]);
        niftyElement4.getRenderer(ImageRenderer.class).setImage(img4);
    }
}
