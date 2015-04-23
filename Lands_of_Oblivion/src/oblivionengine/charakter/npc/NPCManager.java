/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.scene.Node;
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
    public static void addFreeNPC(NPCControl npc) {
         freeNPCs.add(npc);
         workingNPCs.remove(npc);
         
         referNPCToBuilding();
    }
    
    public static void removeFreeNPC(NPCControl npc) {
         freeNPCs.remove(npc);
    }

    public static void addWorkingNPCs(NPCControl npc) {
        workingNPCs.add(npc);
        freeNPCs.remove(npc);
    }
    
    public static void removeWorkingNPCs(NPCControl npc) {
        workingNPCs.remove(npc);
    }

    public static void addFreeBuildings(Building building) {
        freeBuildings.add(building);
        workingBuildings.remove(building);
        
        referNPCToBuilding();
    }
    
    public static void removeFreeBuildings(Building building) {
        freeBuildings.remove(building);
    }

    public static void addWorkingBuildings(Building building) {
        workingBuildings.add(building);
        freeBuildings.remove(building);
    }
    
    public static void removeWorkingBuildings(Building building) {
        workingBuildings.remove(building);
    }
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden 
    public static void referNPCToBuilding(){
        System.out.println(freeNPCs.size() + ", " + freeBuildings.size());
        
        if(freeNPCs.size()>0 && freeBuildings.size()>0){
            WorkerControl npc;
            Building building = freeBuildings.get(0);           
            
            //NPCControl austauschen
            Node node = freeNPCs.get(0).getNode();
            npc = new WorkerControl(node.getControl(NPCControl.class).getHome(), building);
            node.addControl(npc);
            node.removeControl(NPCControl.class);
            npc.setSpatial(node);
            
            //Intere Listen neu organisieren
            workingNPCs.add(npc);
            workingBuildings.add(building);
            
            
            building.setWorker(npc);
        }
    }
}
