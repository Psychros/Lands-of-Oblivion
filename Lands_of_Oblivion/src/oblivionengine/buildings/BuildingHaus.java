/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import oblivionengine.charakter.npc.NPCControl;
import oblivionengine.charakter.npc.NPCManager;

/**
 *
 * @author To
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
}
