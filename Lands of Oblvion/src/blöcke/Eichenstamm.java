/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blöcke;

import static mygame.Spiel.bulletAppState;

/**
 *
 * @author To
 */
public class Eichenstamm extends Block{
    
    public Eichenstamm(int x, int y, int z){
        super(x, y, z);
        setMaterial(EICHENSTAMM);
        addControl(blockPhy);
        bulletAppState.getPhysicsSpace().add(getControl(0));
    }
}
