/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author To
 */
public class TreePlantControl extends AbstractControl{
    //Objektvariablen
    private float timer = 0;
    private float timerTime = 2f;
    private int steps = 0;          //Anzahl der geleisteten Wachstumsschritte
    private int numberSteps = 20;   //Anzahl der zu leistenden Wachstumsschritte

    //--------------------------------------------------------------------------
    //Konstruktoren

    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    protected void controlUpdate(float tpf) {
        timer += tpf;
        if(timer>=timerTime){
            timer = 0;
            steps++;
            
            if(steps<=numberSteps){
                spatial.scale(1.1f); 
            } else {
                spatial.removeControl(this);
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
}
