/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import oblivionengine.Game;
import oblivionengine.buildings.Building;
import oblivionengine.buildings.BuildingHaus;

/**
 *
 * @author To
 */
public class WorkerControl extends NPCControl{
    //Objektvariablen
    private Building workPlace = null;
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public WorkerControl(BuildingHaus home, Building workPlace) { 
        super(home);
        this.workPlace = workPlace;
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public Building getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(Building workPlace) {
        this.workPlace = workPlace;
    }

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden

    @Override
    protected void controlUpdate(float tpf) {
        //Zum Arbeitsplatz bewegen
        if(!spatial.getLocalTranslation().equals(workPlace.getLocalTranslation())){
            Vector3f walkDirection = new Vector3f(workPlace.getLocalTranslation().x, 0, workPlace.getLocalTranslation().z).subtract(new Vector3f(this.spatial.getLocalTranslation().x, 0, this.spatial.getLocalTranslation().z));
            spatial.move(walkDirection.normalize().mult(tpf));
            spatial.getLocalTranslation().setY(Game.game.mapState.getMap().getTerrain().getHeight(new Vector2f(spatial.getLocalTranslation().x, spatial.getLocalTranslation().z)));
        }
    }
}
