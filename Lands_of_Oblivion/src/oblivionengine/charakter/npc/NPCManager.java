/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.animation.AnimControl;
import com.jme3.scene.Node;
import java.util.ArrayList;
import oblivionengine.Game;
import oblivionengine.buildings.Building;
import oblivionengine.buildings.workbuildings.WorkBuilding;

/**
 *
 * @author To
 */
public class NPCManager {
    private static ArrayList<NPCControl> freeNPCs = new ArrayList<NPCControl>();    //Nicht arbeitende NPCs
    private static ArrayList<WorkerControl> workingNPCs = new ArrayList<WorkerControl>(); //Arbeitende NPCs
    public static int numberNPCs = 0;
    
    private static ArrayList<WorkBuilding> freeBuildings = new ArrayList<WorkBuilding>();   //Gebäude, die noch eine Arbeitskraft brauchen
    private static ArrayList<WorkBuilding> workingBuildings = new ArrayList<WorkBuilding>();   //Gebäude, die eine Arbeitskraft besitzen
    public static int numberBuildings = 0;

    

    //--------------------------------------------------------------------------
    //Konstruktoren

    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public static void addFreeNPC(NPCControl npc) {
         freeNPCs.add(npc);
         workingNPCs.remove(npc);
         
         referNPCToBuilding();
         actualizeText();
   }
    
    public static void removeFreeNPC(NPCControl npc) {
         freeNPCs.remove(npc);
         actualizeText();
         freeNPCs.trimToSize();
    }

    public static void addWorkingNPCs(WorkerControl npc) {
        workingNPCs.add(npc);
        actualizeText();
    }
    
    public static void removeWorkingNPCs(NPCControl npc) {
        workingNPCs.remove(npc);
        actualizeText();
        workingNPCs.trimToSize();
    }

    public static void addFreeBuildings(WorkBuilding building) {
        freeBuildings.add(building);
        workingBuildings.remove(building);
        
        referNPCToBuilding();
    }
    
    public static void removeFreeBuildings(Building building) {
        freeBuildings.remove(building);
        freeBuildings.trimToSize();
    }

    public static void addWorkingBuildings(WorkBuilding building) {
        workingBuildings.add(building);
        freeBuildings.remove(building);
    }
    
    public static void removeWorkingBuildings(Building building) {
        workingBuildings.remove(building);
        workingBuildings.trimToSize();
    }
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden 
    public static void referNPCToBuilding(){
        if(freeNPCs.size()>0 && freeBuildings.size()>0){
            WorkerControl npc;
            WorkBuilding building = freeBuildings.get(0);           
            
            
            //NPCControl austauschen
            Node node = freeNPCs.get(0).getNode();
            npc = new WorkerControl(node.getControl(NPCControl.class).getHome(), building);
            npc.setAnimControl(freeNPCs.get(0).getNode().getControl(AnimControl.class));
            node.addControl(npc);
            node.removeControl(NPCControl.class);
            npc.setSpatial(node);        
            
            //Intere Listen neu organisieren
            addWorkingNPCs(npc);
            removeFreeNPC(freeNPCs.get(0));
            addWorkingBuildings(building);
            
            //NPC zum Arbeitsplatz laufen lassen
            npc.goToWorkPlace();
            
            System.out.println(freeNPCs.size() + "/" + workingNPCs.size() + " : " + freeBuildings.size() + "/" + workingBuildings.size());
        }
    }
    
    
    //Aktualisiert die Einwohneranzeige
    public static void actualizeText(){
        int freePeople = freeNPCs.size();
        int workingPeople = workingNPCs.size();
        
        String text = freePeople + "/" + workingPeople;
        Game.game.screens.setText("inGame", "Einwohner", text);
    }
}
