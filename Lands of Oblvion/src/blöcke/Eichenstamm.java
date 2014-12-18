/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package blöcke;

/**
 *
 * @author To
 */
public class Eichenstamm extends Block{
    
    public Eichenstamm(){
        setMesh(BLOCK.getMesh()); 
        scale(0.5f);
        setMaterial(EICHENSTAMM);
        addControl(Block.blockPhy);
    }
}
