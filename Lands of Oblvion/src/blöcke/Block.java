/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blöcke;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import mygame.Main;

/**
 *
 * @author To
 */
public class Block extends Geometry{
    
    /*
     * Meshes für die Blöcke
     */
    //Standartblock, welcher schon mit UV-Mapping für sechs Seiten ausgestattet ist
    public static final Geometry BLOCK = (Geometry)Main.main.getAssetManager().loadModel("Models/block.j3o");         
    
    
    /*
     * Materialien für die unterschiedlichen Blöcke
     */
    public static final Material EICHENSTAMM = Main.main.getAssetManager().loadMaterial("Materials/Blöcke/Eichenstamm.j3m");
    public static final Material EICHENBRETTER = Main.main.getAssetManager().loadMaterial("Materials/Blöcke/Eichenbretter.j3m");
    
    /*
     * Physic für alle Blöcke
     */
    public RigidBodyControl blockPhy = new RigidBodyControl(0);
    
    public Block(){
        
    }
}
