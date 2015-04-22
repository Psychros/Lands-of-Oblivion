/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter;

import java.util.ArrayList;
import oblivionengine.buildings.Building;

/**
 *
 * @author To
 */
public class NPCManager {
    private static ArrayList<NPCControl> freeNPCs = new ArrayList<NPCControl>();    //Nicht arbeitende NPCs
    private static ArrayList<NPCControl> workingNPCs = new ArrayList<NPCControl>(); //Arbeitende NPCs
    public static int numberNPCs = 0;
    
    private static ArrayList<Building> freeBuildings = new ArrayList<Building>();   //Gebäude, die noch eine Arbeitskraft brauchen
    private static ArrayList<Building> workingBuildings = new ArrayList<Building>();   //Gebäude, die eine Arbeitskraft besitzen
    public static int numberBuildings = 0;

    

    //--------------------------------------------------------------------------
    //Konstruktoren

    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public static ArrayList<NPCControl> getFreeNPCs() {    
        return freeNPCs;
    }

    public static void setFreeNPCs(ArrayList<NPCControl> freeNPCs) {
        NPCManager.freeNPCs = freeNPCs;
    }

    public static ArrayList<NPCControl> getWorkingNPCs() {
        return workingNPCs;
    }

    public static void setWorkingNPCs(ArrayList<NPCControl> workingNPCs) {
        NPCManager.workingNPCs = workingNPCs;
    }

    public static ArrayList<Building> getFreeBuildings() {
        return freeBuildings;
    }

    public static void setFreeBuildings(ArrayList<Building> freeBuildings) {
        NPCManager.freeBuildings = freeBuildings;
    }

    public static ArrayList<Building> getWorkingBuildings() {
        return workingBuildings;
    }

    public static void setWorkingBuildings(ArrayList<Building> workingBuildings) {
        NPCManager.workingBuildings = workingBuildings;
    }
}
