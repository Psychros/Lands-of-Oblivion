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
public class Eichenbretter extends Block{

    public Eichenbretter(int x, int y, int z){
        super(x, y, z);
        setMaterial(EICHENBRETTER);
        addControl(blockPhy);
        bulletAppState.getPhysicsSpace().add(getControl(0));
    }
}
