/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.buildings;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import oblivionengine.charakter.Player;

/**
 *
 * @author To
 */
public class HolzfällerControl extends AbstractControl {
    private int time = 30;
    private float timer = 0;

    @Override
    protected void controlUpdate(float tpf) {
        timer += tpf;
        if(timer >= time){
            timer = 0;
            Player.lager.addRessourcen(Ressourcen.Wood, 1);
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
