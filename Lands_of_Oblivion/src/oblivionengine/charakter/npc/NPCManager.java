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
import oblivionengine.buildings.WorkBuilding;
import oblivionengine.charakter.bedürfnisse.Bedürfnis;

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
    
    //Moral beeinflusst das Arbeitverhalten der NPCs
    //Anfangs beträgt sie 100%
    private static float moral = 1.00f;  
    private static float maxMoral = 1.50f;
    
    //Wurde ein Gebäude schon gebaut?
    private static boolean isChurch = false;
    
    //Bedürfnisse
    private static ArrayList<Bedürfnis> bedürfnisseSek = new ArrayList<Bedürfnis>();  //Diese Bedürfnisse steigern Moral und können diese senkem
    private static ArrayList<Bedürfnis> bedürfnissePrim = new ArrayList<Bedürfnis>();    //Diese Bedürfnisse können Moral nur senken
    
    //Alle Anfangsbedürfnisse
    static{
        addBedürfnis(Bedürfnis.FISCH, bedürfnissePrim);
        addBedürfnis(Bedürfnis.GLAUBE, bedürfnisseSek);
    }
    
    
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

    public static float getMoral() {
        return moral;
    }

    //Moral kann nur einen Wert zwischen 0 und 1.5 einnehmen
    public static void addMoral(float moral) {
        NPCManager.moral += moral;
        
        if(NPCManager.moral > maxMoral)
            NPCManager.moral = maxMoral;
        else if(NPCManager.moral < 0)
            NPCManager.moral = 0;
        
        actualizeText();
    }

    public static float getMaxMoral() {
        return maxMoral;
    }

    public static void setMaxMoral(float maxMoral) {
        NPCManager.maxMoral = maxMoral;
    }

    public static boolean isIsChurch() {
        return isChurch;
    }

    //Ist eine Kirche gebaut?
    public static void setIsChurch(boolean isChurch) {
        if(isChurch && !NPCManager.isChurch){
            maxMoral += 0.2f;
        }
        else if(!isChurch && NPCManager.isChurch){
            maxMoral -= 0.2f;
            addMoral(0);    //Moral wird auf den neuen Maximalwert angepasst
        }
           
        NPCManager.isChurch = isChurch;
    } 

    public static ArrayList<Bedürfnis> getBedürfnisseSek() {
        return bedürfnisseSek;
    }

    public static ArrayList<Bedürfnis> getBedürfnissePrim() {
        return bedürfnissePrim;
    }
    
    //Umbedingt nutzen, um ein Bedürfnis hinzuzufürgen, da dadurch die Timer der NPCs aktualisiert werden
    public static void addBedürfnis(Bedürfnis b, ArrayList list){
        list.add(b);
        
        //Timer aktualisieren
        for (NPCControl npc : freeNPCs) {
            npc.getTimerBedürfnisse().add(new Float(0));
        }
        for (WorkerControl npc : workingNPCs) {
            npc.getTimerBedürfnisse().add(new Float(0));
        }
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
        }
    }
    
    
    //Aktualisiert die Einwohneranzeige
    public static void actualizeText(){
        int freePeople = freeNPCs.size();
        int workingPeople = workingNPCs.size();
        
        String text = freePeople + "/" + workingPeople + " (" + (int)(moral*100) + "%)";
        Game.game.screens.setText("inGame", "Einwohner", text);
    }
}
