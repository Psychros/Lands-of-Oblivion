package mygame;

import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;
import oblivionengine.Game;
import oblivionengine.appstates.Screens;

/**
 * test
 * @author normenhansen
 */
public class Main extends Game {
    
    //Objektvariablen
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden

    public static void main(String[] args) {
       Main game = new Main();
       game.setDisplayStatView(false);
       Logger.getLogger("").setLevel(Level.SEVERE);
       
       //Appsettings anpassen
       AppSettings settings = new AppSettings(true);
       settings.setFullscreen(false);
       settings.setTitle("Lands of Oblivion");
       settings.setSettingsDialogImage("Interface/SplashScreen2.png");
       settings.setResolution(1920, 1080);
       game.setSettings(settings);
       
       game.start();
    }

    @Override
    public void simpleInitApp() { 
        screens = new Screens();
        stateManager.attach(screens);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}