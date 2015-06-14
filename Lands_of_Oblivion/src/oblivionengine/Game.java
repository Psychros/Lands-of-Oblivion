/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine;

import oblivionengine.maps.Map;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.JoyInput;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.TouchInput;
import com.jme3.renderer.Camera;
import com.jme3.system.AppSettings;
import oblivionengine.maps.MapState;
import oblivionengine.cheathandling.Cheatmanager;

/**
 *
 * @author To
 */
abstract public class Game extends SimpleApplication{
    
    //Objektvariablen
    
    private Map activeMap;   
    public static Game game;
    public Screens screens;
    public MapState mapState;
    public Cheatmanager cheatmanager;
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    
    public Screens getScreens() {
        return this.screens;
    }
    
    public Map getActiveMap() {
        return activeMap;
    }

    //Die aktuelle Map wird durch eine neue ausgetauscht
    public void setActiveMap(Map activeMap) {
        if(activeMap != null)
            rootNode.detachChild(activeMap);
        this.activeMap = activeMap;
        rootNode.attachChild(activeMap);
    }

    public BitmapText getFpsText() {
        return fpsText;
    }

    public void setFpsText(BitmapText fpsText) {
        this.fpsText = fpsText;
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    public void setGuiFont(BitmapFont guiFont) {
        this.guiFont = guiFont;
    }

    public FlyByCamera getFlyCam() {
        return flyCam;
    }

    public void setFlyCam(FlyByCamera flyCam) {
        this.flyCam = flyCam;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }

    public boolean isInputEnabled() {
        return inputEnabled;
    }

    public void setInputEnabled(boolean inputEnabled) {
        this.inputEnabled = inputEnabled;
    }

    public boolean isPauseOnFocus() {
        return pauseOnFocus;
    }

    public void setPauseOnFocus(boolean pauseOnFocus) {
        this.pauseOnFocus = pauseOnFocus;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public void setMouseInput(MouseInput mouseInput) {
        this.mouseInput = mouseInput;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public void setKeyInput(KeyInput keyInput) {
        this.keyInput = keyInput;
    }

    public JoyInput getJoyInput() {
        return joyInput;
    }

    public void setJoyInput(JoyInput joyInput) {
        this.joyInput = joyInput;
    }

    public TouchInput getTouchInput() {
        return touchInput;
    }

    public void setTouchInput(TouchInput touchInput) {
        this.touchInput = touchInput;
    }

    public AppSettings getSettings() {
        return settings;
    }
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    @Override
    public void start(){
        super.start();
        game = this;
        cheatmanager = new Cheatmanager();
    } 
   
    //Initialisiert einen MapState, welcher den Ablauf des Spieles verwaltet
    public void initMapState(MapState mapState){
        this.mapState = mapState;
        stateManager.attach(mapState);
    }
}
