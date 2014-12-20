/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blöcke;

import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.shadow.PointLightShadowRenderer;
import mygame.Main;
import static mygame.Spiel.bulletAppState;

/**
 *
 * @author To
 */
public class Fackel extends Block{
    public Fackel(int x, int y, int z){
        super(x+0.85f, y, z-0.8f);
        setMesh(STOCK.getMesh()); 
        setMaterial(EICHENBRETTER);
        rotate(FastMath.DEG_TO_RAD*45, 0, 0);
        addControl(blockPhy);
        bulletAppState.getPhysicsSpace().add(getControl(0));
        
        //Effekt
        Node fackel = (Node)Main.main.getAssetManager().loadModel("Scenes/Fackeleffekt.j3o");
        fackel.setLocalTranslation(x+1.35f, y, z);
        Main.main.getRootNode().attachChild(fackel);
    }
}
