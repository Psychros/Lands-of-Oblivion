/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blöcke;

import static blöcke.Block.BLOCK;
import com.jme3.math.FastMath;
import static mygame.Spiel.bulletAppState;

/**
 *
 * @author To
 */
public class Eichenbrettschräge extends Block{
    
    public Eichenbrettschräge(int x, int y, int z, int rotation){
        super(x+0.83f, y-0.16f, z-0.55f);
        setMesh(SCHRÄGE.getMesh()); 
        setMaterial(EICHENBRETTER); 
        setCullHint(CullHint.Never);
        //Rotiert die Schräge rotation mal nach um 90°
        //rotation = 0 bedeutet keine Rotation
        rotate(0, FastMath.DEG_TO_RAD*90*rotation, 0);  
        
        addControl(blockPhy);
        bulletAppState.getPhysicsSpace().add(getControl(0));
    }
}
