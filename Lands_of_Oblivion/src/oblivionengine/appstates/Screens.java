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
    Nifty nifty;
    
    Node tree;
    
    
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
        mapState.activateKeys(true);
        mapState.activateCursor(true);
        
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
     * Optionsmenü im Hauptmenü Methoden
     */ 
    public void backToStartScreen(){
        nifty.gotoScreen("start");
    }
}
