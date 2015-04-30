/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
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
        
        setIsWalkingRandom(false);
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
        super.controlUpdate(tpf);
        
        //Am Arbeitsplatz anhalten
        if(spatial.getLocalTranslation().distance(workPlace.getLocalTranslation()) < 3){
           setWalkDirection(Vector2f.ZERO);
        }
    }
    
    
    //Der NPC läuft in Richtung Arbeitsplatz
    public void goToWorkPlace(){
        Vector3f walkDirection = new Vector3f(workPlace.getLocalTranslation().x, 0, workPlace.getLocalTranslation().z).subtract(new Vector3f(this.spatial.getLocalTranslation().x, 0, this.spatial.getLocalTranslation().z));
        setWalkDirection(new Vector2f(walkDirection.x, walkDirection.z));

        //NPC zur WalkDirection drehen
        rotateSpatialToWalkDirection(new Vector2f(walkDirection.x, walkDirection.z));
    }
}
