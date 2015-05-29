/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import oblivionengine.charakter.npc.Job;
import oblivionengine.charakter.npc.NPCManager;
import oblivionengine.charakter.npc.WorkerControl;

/**
 *
 * @author To
 */
public class WorkBuilding extends Building{
    //Objektvariablen
    private WorkerControl worker = null;
    protected WorkBuildingControl control = null;
    private boolean hasWorker = false;

    
    //--------------------------------------------------------------------------
    //Konstruktoren
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public WorkerControl getWorker() {    
        return worker;
    }

    public void setWorker(WorkerControl worker) {
        this.worker = worker;
    } 

    public WorkBuildingControl getControl() {
        return control;
    }

    public boolean hasWorker() {
        return hasWorker;
    }

    public void setHasWorker(boolean hasWorker) {
        this.hasWorker = hasWorker;
    }
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
     @Override
    public void finish() {
        super.finish(); 
        
        //Controll hinzufügen, der Waren produziert
        control = new WorkBuildingControl(Ressourcen.Holz, 30);
        addControl(control);
        
        NPCManager.addFreeBuildings(this);
    }

    @Override
    public void build() {
        super.build(); //To change body of generated methods, choose Tools | Templates.
    }  
    
    public Job getJob(){
        return null;
    }
}
