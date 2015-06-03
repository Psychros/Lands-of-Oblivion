/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import oblivionengine.buildings.einwohner.BuildingHaus;
import oblivionengine.buildings.WorkBuilding;
import oblivionengine.charakter.npc.pathfinding.Node;
import oblivionengine.charakter.npc.pathfinding.PathFinder;

/**
 *
 * @author To
 */
public class WorkerControl extends NPCControl{
    //Objektvariablen
    private Job job;
    private WorkBuilding workPlace = null;
    private boolean isGoingToWorkplace = true;
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public WorkerControl(BuildingHaus home, WorkBuilding workPlace, Spatial spatial) { 
        super(home);
        this.workPlace = workPlace;
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public WorkBuilding getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkBuilding workPlace) {
        this.workPlace = workPlace;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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
                setPath(null);
                isGoingToWorkplace = false;
                workPlace.setHasWorker(true);
                
                //Job einstellen
                setJob(workPlace.getJob());
                if(job != null)
                    job.setWorker(this);
            }
        }
        
        //Job ausführen
        if(job != null)
            job.update(tpf);
    }
    
    
    //Der NPC läuft in Richtung Arbeitsplatz
    public void goToWorkPlace(){
        //Pfad generieren
        PathFinder pF = new PathFinder(new Vector2f(spatial.getLocalTranslation().x, spatial.getLocalTranslation().z), new Vector2f(workPlace.getLocalTranslation().x, workPlace.getLocalTranslation().z), this);
        Node n = pF.generatePath();
        if(n != null)
            setPath(pF.makeListFromPath(n));
        else{
            NPCManager.removeNPCFromBuilding(this);
            System.out.println("Der NPC ist eingesperrt!");
        }
    }
}
