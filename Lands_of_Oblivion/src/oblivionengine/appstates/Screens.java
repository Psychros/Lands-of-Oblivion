/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import oblivionengine.Game;

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
    
    Node tree;

    public NiftyJmeDisplay getNiftyDisplay() {
        return niftyDisplay;
    }

    public Nifty getNifty() {
        return nifty;
    }
    
    
    /*
     * Überschriebene Methoden
     */
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
    
    /*
     * Hauptmenü Methoden
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
    
    public void switchToCheatmenü(){
        if (cheatmenüControl == 0){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){
                cheatmenüControl++;
            }
        } else if (cheatmenüControl == 1){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){   
                cheatmenüControl++;
                nifty.gotoScreen("cheatmenü");
                Game.game.getInputManager().setCursorVisible(true);
                nifty.setIgnoreKeyboardEvents(false);
                nifty.setIgnoreMouseEvents(false);
            }
        } else if (cheatmenüControl == 2){
            if (nifty.getCurrentScreen().getScreenId().equals("cheatmenü")){
                cheatmenüControl++;
            }
        } else if (cheatmenüControl == 3){
            if (nifty.getCurrentScreen().getScreenId().equals("cheatmenü")){
                cheatmenüControl = 0;
                nifty.gotoScreen("inGame");
                Game.game.getInputManager().setCursorVisible(false);
                nifty.setIgnoreKeyboardEvents(true);
                nifty.setIgnoreMouseEvents(true);
            }
        }
    }
    
    /*
     * Bild des Buttons ändern,  wenn die Maus über diesen fährt
     */
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
    
    
    /*
     * Optionsmenü im Hauptmenü Methoden
     */ 
    public void backToStartScreen(){
        nifty.gotoScreen("start");
    }
    
    
    public void doCheat(String cheat){
        
    }
}
