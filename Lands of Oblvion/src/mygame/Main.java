package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    @Override
    public void simpleInitApp() {
        Spiel spiel = new Spiel();
        stateManager.attach(spiel);
        
        flyCam.setMoveSpeed(20);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
