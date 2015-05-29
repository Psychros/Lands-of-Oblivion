/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.einwohner;

import oblivionengine.buildings.Building;
import oblivionengine.charakter.npc.NPCControl;
import oblivionengine.charakter.npc.NPCManager;
import oblivionengine.charakter.npc.WorkerControl;

/**
 *
 * @author Tod
 * 
 */
public abstract class BuildingHaus extends Building{
    //Objektvariablen
    protected int numberpeople;   //Zahl der maximalen Einwohner
    protected NPCControl[] npcs= null;

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingHaus() {
        
    }
   

    //--------------------------------------------------------------------------
    //Getter und Setter
    public int getNumberpeople() {
        return numberpeople;
    }

    public void setNumberpeople(int numberpeople) {
        this.numberpeople = numberpeople;
        npcs = new NPCControl[numberpeople];
        
        generateNPCs();
    }

    public NPCControl[] getNpcs() {
        return npcs;
    }

    public void setNpcs(NPCControl[] npcs) {
        this.npcs = npcs;
    }
    
    //Den Index des Bewohners in seinem Haus herausfinden
    public int getIndex(NPCControl npc){
        for (int i = 0; i < npcs.length; i++) {
            if(npcs[i] == npc)
                return i;
        }
        
        return -1;
    }
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    //Neue NPCs fürs Haus erstellen
    public void generateNPCs(){
        for (int i = 0; i < numberpeople; i++) {
            npcs[i] = new NPCControl(this);
            npcs[i].generateModell();
            NPCManager.addFreeNPC(npcs[i]);
        }
    }

    @Override
    public void demolish() {
        super.demolish(); 
        
        //Alle NPCs des Gebäudes entfernen
        for (NPCControl npc : npcs) {
            
            //Falls der NPC einem Arbeitsgebäude zugeteilt wurde muss er aufhören zu arbeiten
            if(npc instanceof WorkerControl){
                WorkerControl wC =(WorkerControl)npc;
                wC.getWorkPlace().setWorker(null);
                 
                //Abrietsgebäude zu freiem Gebäude machen, damit ein neuer Arbeiter zugewiesen werden kann
                NPCManager.addFreeBuildings(wC.getWorkPlace());
                NPCManager.removeWorkingBuildings(wC.getWorkPlace());
                
                //NPC aus der Liste streichen
                NPCManager.removeWorkingNPCs(npc);
            }
            else{
                NPCManager.removeFreeNPC(npc);
            }
            
            //NPC aus der Welt entfernen
            npc.getSpatial().removeFromParent();
            npc.getSpatial().removeControl(npc);
        }
    }
    
    
}
