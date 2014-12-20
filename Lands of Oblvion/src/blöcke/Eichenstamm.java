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
        setMesh(BLOCK.getMesh()); 
        scale(0.5f);
        setMaterial(EICHENSTAMM);
        setLocalTranslation(x+0.5f, y-0.3f, z+0.5f);
        addControl(blockPhy);
        bulletAppState.getPhysicsSpace().add(getControl(0));
    }
}
