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
import com.jme3.math.FastMath;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import oblivionengine.Game;
import oblivionengine.appstates.MapState.InputMapping;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.charakter.Player;
import static oblivionengine.charakter.Player.lager;

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
    String name; NiftyImage img;   //Ausgewählter Button und dessen Name
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
    
    //Setzt den Text eines Elements
    public void setText(String element, String text){
         Element e = Game.game.screens.getNifty().getCurrentScreen().findElementByName(element);
         TextRenderer label = e.getRenderer(TextRenderer.class);
         label.setText(text);
    }
    
    public void setText(String element, int text){
         setText(element, String.valueOf(text));
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
        inputManager.deleteMapping(InputMapping.Build.name());
        
        //Den Spieler anhalten, wenn er sich bewegt
        Game.game.mapState.getPlayer().stopPlayer();
    }
    
    
    //Der Spieler befindet sich im Spiel und soll den Player wieder bewegen können
    public void goToGame(){
        Game.game.mapState.addInputMappings();
        
        nifty.gotoScreen("inGame");
        Game.game.getInputManager().setCursorVisible(false);
        nifty.setIgnoreKeyboardEvents(true);
        nifty.setIgnoreMouseEvents(true);
    }
    
    //Ausgewählten Button wieder entfärben, wenn der Cursor ihn nicht mehr auswählt
    public void resetMouseOver(){
        Element niftyElement4 = nifty.getCurrentScreen().findElementByName(name);
        niftyElement4.getRenderer(ImageRenderer.class).setImage(img);
    }
    
    //Ausgewählten Button einfärben
    public void mouseOver(String value){
        /*
         * Alle Bilder der Buttons zurücksetzen
         */             
        //Bild des ausgewählten buttons ändern
        String[] values = value.trim().split(". ");
        
        img = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), values[2], false);
        name = values[0];
        
        NiftyImage img4 = nifty.getRenderEngine().createImage(nifty.getCurrentScreen(), values[1], false);
        Element niftyElement4 = nifty.getCurrentScreen().findElementByName(values[0]);
        niftyElement4.getRenderer(ImageRenderer.class).setImage(img4);
    }
    
    
    
    /*
     * Hauptmenü
     */
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
     * Optionsmenü
     */ 
    public void backToStartScreen(){
        nifty.gotoScreen("start");
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
        //ID des Gebäudes auswählen
        if(buildingID.equals("Nichts"))
            Player.selectedBuilding = null;
        else
            Player.selectedBuilding = buildingID;
        
        //Zum Spiel zurückkehren
        baumenüControl = 0;
        goToGame();
    }
}
