/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.workbuildings;

import oblivionengine.buildings.Building;
import oblivionengine.buildings.Ressourcen;
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
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
     @Override
    public void finishBuilding() {
        super.finishBuilding(); 
        
        //Controll hinzufügen, der Waren produziert
        control = new WorkBuildingControl(Ressourcen.Wood, 30);
        addControl(control);
        
        NPCManager.addFreeBuildings(this);
    }
}
