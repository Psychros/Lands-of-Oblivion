/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.workbuildings;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.charakter.npc.NPCManager;
import oblivionengine.charakter.player.Player;

/**
 *
 * @author To
 */
public class WorkBuildingControl extends AbstractControl{
    //Objektvariablen
    private int time = 15;
    private float timer = 0;
    private Ressourcen ressource = null; //Zu produzierende Ressource

    //--------------------------------------------------------------------------
    //Konstruktoren
    public WorkBuildingControl() {
        
    }
    
    
    public WorkBuildingControl(Ressourcen ressource, int time) {
        this.ressource = ressource;
        this.time = time;
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Ressourcen getRessource() {
        return ressource;
    }

    public void setRessource(Ressourcen ressource) {
        this.ressource = ressource;
    }
    

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    protected void controlUpdate(float tpf) {
        WorkBuilding building = (WorkBuilding)spatial;
        
        if(building.getWorker() != null){
            timer += tpf;
            if(timer >= time){
                timer = 0 - (time * (1- NPCManager.getMoral()));
                Player.lager.addRessourcen(ressource, 1);
            }
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
