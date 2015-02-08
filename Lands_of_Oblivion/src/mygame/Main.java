package mygame;

import com.jme3.renderer.RenderManager;
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
       game.setDisplayFps(false);
       game.start();
    }

    @Override
    public void simpleInitApp() { 
        Screens menü = new Screens();
        stateManager.attach(menü);
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}