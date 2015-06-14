/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine;

import oblivionengine.maps.MapState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.scrollbar.ScrollbarControl;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.ImageRenderer;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import oblivionengine.Game;
import oblivionengine.charakter.player.CharakterControl;
import oblivionengine.charakter.player.Player;

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
    private byte menüControl   = 0;
    
    Node tree;
    
    //Ausgewählte Kategorie im Baumenü
    private String category = "";

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
        
        //Alle vorgefertigten KeyMappings entfernen
        Game.game.getInputManager().clearMappings();
        
        //Niftygui initialisieren
        niftyDisplay = new NiftyJmeDisplay(assetManager, app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/Menüs/Screen.xml", "start", this);
        app.getGuiViewPort().addProcessor(niftyDisplay);
        Game.game.getFlyCam().setEnabled(false);
        
        initHauptmenü();
    }
    
    @Override
    public void update(float tpf) {
        //Baum rotieren lassen
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
    public void setText(String screen, String element, String text){
         Element e = Game.game.screens.getNifty().getScreen(screen).findElementByName(element);
         TextRenderer label = e.getRenderer(TextRenderer.class);
         label.setText(text);
    }
    
    public void setText(String screen, String element, int text){
         setText(screen, element, String.valueOf(text));
    }
    
    
    
    //Der Spieler befindet sich in einem Menü und soll den Player nicht mehr bewegen können
    public void goToMenu(String menu){
        nifty.gotoScreen(menu);
        Game.game.getInputManager().setCursorVisible(true);
        nifty.setIgnoreKeyboardEvents(false);
        nifty.setIgnoreMouseEvents(false);
        
        //Mappings entfernen, die eine Aktion des Players darstellen
        InputManager inputManager = Game.game.getInputManager();
        inputManager.clearMappings();
        Game.game.mapState.addMenuInputMappings();
        
        //Den Spieler anhalten, wenn er sich bewegt
        Game.game.mapState.getPlayer().stopPlayer();
    }
    
    
    //Zu einem Menü wechseln
    public void switchToMenu(String menu){
        if (menüControl == 0){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){
                menüControl++;
            }
        } else if (menüControl == 1){
            if (nifty.getCurrentScreen().getScreenId().equals("inGame")){   
                menüControl++;
                goToMenu(menu);
            }
        } else if (menüControl == 2){
            if (nifty.getCurrentScreen().getScreenId().equals(menu)){
                menüControl++;
            }
        } else if (menüControl == 3){
            if (nifty.getCurrentScreen().getScreenId().equals(menu)){
                menüControl = 0;
                goToGame();
            }
        }
    }
    
    
    //Der Spieler befindet sich im Spiel und soll den Player wieder bewegen können
    public void goToGame(){
        Game.game.mapState.addInputMappings();
        resetMouseOver();
        
        nifty.gotoScreen("inGame");
        Game.game.getInputManager().setCursorVisible(false);
        nifty.setIgnoreKeyboardEvents(true);
        nifty.setIgnoreMouseEvents(true);
        
        menüControl=0;
    }
    
    //Ausgewählten Button wieder entfärben, wenn der Cursor ihn nicht mehr auswählt
    public void resetMouseOver(){
        if(name != null){
            Element niftyElement4 = nifty.getCurrentScreen().findElementByName(name);
            if(niftyElement4 != null)
                niftyElement4.getRenderer(ImageRenderer.class).setImage(img);
        }
    }
    
    //Ausgewählten Button einfärben
    public void mouseOver(String value){
        //Der alte Button darf nicht mehr ausgewählt sein
        resetMouseOver();
            
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
    public void startGame(String map){
        //zum inGame Screen wechseln
        nifty.gotoScreen("inGame");
        
        //Innerhalb des eigentlichen Spiels darf man per Tastendruck nicht die GUI bedienen
        nifty.setIgnoreKeyboardEvents(true);
        nifty.setIgnoreMouseEvents(true);
        
        //Den MapState initialisieren und Tastendrücke aktivieren
        MapState mapState = new MapState(map);
        Game.game.initMapState(mapState);
        
        
        //Kamera kann sich wieder bewegen und der Mauszeiger wird entfernt
        Game.game.getFlyCam().setEnabled(true);
        Game.game.getInputManager().setCursorVisible(false);
        
        //Alle Objekte entfernen
        Game.game.getRootNode().detachAllChildren();
    }
    
    public void loadGame(){
        
    }
    
    public void stopGame(){
        Game.game.stop();
        System.exit(0);
    }
    
    public void options(){
        nifty.gotoScreen("optionen");
    }
    
    //Hauptmenü initialisieren
    public void initHauptmenü(){
        Game.game.getRootNode().detachAllChildren();
        
        //Baum im Hauptmenü erstellen
        tree = (Node)Game.game.getAssetManager().loadModel("Models/Landschaft/Baum.j3o");
        tree.scale(0.5f);
        tree.setLocalTranslation(-3.5f, -4, 0);
        Game.game.getRootNode().attachChild(tree);
        
        //Ins Hauptmenü wechseln
        nifty.gotoScreen("start");
        
        //Den AppState wechseln
        if(Game.game.getStateManager().hasState(Game.game.mapState))    
            Game.game.getStateManager().detach(Game.game.mapState);
        Game.game.getStateManager().attach(this);
    }
    
    
    
    /*
     * Optionsmenü
     */ 
    public void backToStartScreen(){
        //Maussensitivität einstellen
        ScrollbarControl e = nifty.getCurrentScreen().findElementByName("Mausempfindlichkeit").findControl("Mausempfindlichkeit", ScrollbarControl.class);
        float v = e.getValue();
        CharakterControl.setMouseSensitivity(v);
        
        nifty.gotoScreen("start");
    }
    
    
        
    
    /*
     * Pausemenü
     */
    public void goToHauptmenü(){
        //Die Map von der rootNode entfernen
        Game.game.mapState.getMap().removeFromParent();
        
        //Viewport neu einstellen
        Game.game.getViewPort().clearProcessors();
        Game.game.getViewPort().setBackgroundColor(ColorRGBA.Black);
        
        //Kameraposition ändern
        Game.game.getCamera().setLocation(new Vector3f(-6.5f, 0, -10));
        Game.game.getCamera().lookAtDirection(Vector3f.UNIT_Z, Vector3f.UNIT_Y);
        
        initHauptmenü();
        menüControl = 0;
    }
    
    public void speichern(){
        
    }
    
    
    
    /*
     * Cheatmenü
     */  
    public void cheat(String cheat){
        
    }
    
 
    
    /*
     * Baumenü
     */ 
    //Gebäude auswählen
    public void chooseBuilding(String buildingID){
        //ID des Gebäudes auswählen
        if(buildingID.equals("Abreißen"))
            Player.selectedBuildingID = null;
        else
            Player.selectedBuildingID = buildingID;
        
        //Zum Obermenü zurückkehren
        if(category != null && !category.equals("") && !category.equals("baumenü"))
            switchBack(category);
        
        //Zum Spiel zurückkehren
        menüControl = 0;
        goToGame();
    }
    
    //Wechsel in ein Untermenü des Baumenüs
    public void switchCategory(String name){
        //Position des Untermenüs wechseln
        nifty.getCurrentScreen().findElementByName(name).setVisible(true);
        
        //Position des Obermenüs wechseln
        nifty.getCurrentScreen().findElementByName("buttons").setVisible(false);
        
        nifty.getCurrentScreen().layoutLayers();
        category = name;
    }
    
    //Zurück zum Obermenü wechseln
    public void switchBack(String name){
        //Position des Obermenüs wechseln
        nifty.getCurrentScreen().findElementByName("buttons").setVisible(true);
        
        //Position des Untermenüs wechseln
        nifty.getCurrentScreen().findElementByName(name).setVisible(false);
        
        nifty.getCurrentScreen().layoutLayers();
        category = "";
    }
    
    //Zu einer Kategorie wechseln
    //names[1]: Aufrufendes Menü
    //names[0]: Zielmenü
    public void switchTo(String name){
        
        String[] names = name.split(". ");
        //Position des Obermenüs wechseln
        nifty.getCurrentScreen().findElementByName(names[1]).setVisible(true);
        
        //Position des Untermenüs wechseln
        nifty.getCurrentScreen().findElementByName(names[0]).setVisible(false);
        
        nifty.getCurrentScreen().layoutLayers();
        
        category = names[1];
    }
    
    public void test(String test){
        System.out.println(test);
    }
}
