/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import oblivionengine.buildings.Building;
import oblivionengine.buildings.BuildingHaus;
import oblivionengine.buildings.workbuildings.WorkBuilding;

/**
 *
 * @author To
 */
public class WorkerControl extends NPCControl{
    //Objektvariablen
    private WorkBuilding workPlace = null;
    private boolean isGoingToWorkplace = true;
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public WorkerControl(BuildingHaus home, WorkBuilding workPlace) { 
        super(home);
        this.workPlace = workPlace;
        
        setIsWalkingRandom(false);
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public Building getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkBuilding workPlace) {
        this.workPlace = workPlace;
    }

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        
        //Am Arbeitsplatz anhalten
        if(isGoingToWorkplace){
            Vector2f v1 = new Vector2f(spatial.getLocalTranslation().x, spatial.getLocalTranslation().z);
            Vector2f v2 = new Vector2f(workPlace.getLocalTranslation().x, workPlace.getLocalTranslation().z);
            
            if(v1.distance(v2) < 1){
                setWalkDirection(Vector2f.ZERO);
                isGoingToWorkplace = false;

                workPlace.setWorker(this);
            }
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
