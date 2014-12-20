/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blöcke;

import static blöcke.Block.BLOCK;
import static mygame.Spiel.bulletAppState;

/**
 *
 * @author To
 */
public class Eichenbrettschräge extends Block{
    
    public Eichenbrettschräge(int x, int y, int z){
        super(x, y, z);
        setMesh(BLOCK.getMesh()); 
        scale(0.5f);
        setMaterial(EICHENBRETTER);
        addControl(blockPhy);
        bulletAppState.getPhysicsSpace().add(getControl(0));
    }
}
