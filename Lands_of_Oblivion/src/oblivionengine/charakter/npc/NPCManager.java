/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.animation.AnimControl;
import com.jme3.scene.Node;
import de.lessvoid.nifty.tools.SizeValue;
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
    //NPCs
    private static ArrayList<NPCControl> freeNPCs = new ArrayList<NPCControl>();    //Nicht arbeitende NPCs
    private static ArrayList<WorkerControl> workingNPCs = new ArrayList<WorkerControl>(); //Arbeitende NPCs
    public static int numberNPCs = 0;
    
    //Gebäude
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
    
    //Zivilisationspunkte
    private static int zivilisationsPunkte = 0;
    private static int zivilisationsStufe  = 0;
    
    //Alle Anfangsbedürfnisse
    static{
        addBedürfnis(Bedürfnis.FISCH, bedürfnissePrim);
        addBedürfnis(Bedürfnis.GLAUBE, bedürfnisseSek);
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public static void addFreeNPC(NPCControl npc) {
         freeNPCs.add(npc);
         
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

    public static int getZivilisationsPunkte() {
        return zivilisationsPunkte;
    }
    
    public static void addZiviisationsPunkte(int p){
        zivilisationsPunkte += p;
        
        //Neue Gebäude freischalten
        testZivilisationsPunkte();
    }
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden 
    public static void referNPCToBuilding(){
        if(freeNPCs.size()>0 && freeBuildings.size()>0){
            WorkerControl npc;
            WorkBuilding building = freeBuildings.get(0);           
            
            
            //NPCControl austauschen
            Node node = (Node)freeNPCs.get(0).getSpatial();
            npc = new WorkerControl(freeNPCs.get(0).getHome(), building, node);
            npc.setAnimControl(freeNPCs.get(0).getSpatial().getControl(AnimControl.class));
            node.addControl(npc);
            node.removeControl(NPCControl.class);
            npc.setSpatial(node);        
            
            //NPCControl im Wohnhaus austauschen
            freeNPCs.get(0).getHome().getNpcs()[freeNPCs.get(0).getHome().getIndex(freeNPCs.get(0))] = npc;
            
            //Dem Gebäude einen Arbeiter zuteilen
            building.setWorker(npc);
            
            //Intere Listen neu organisieren
            addWorkingNPCs(npc);
            removeFreeNPC(freeNPCs.get(0));
            addWorkingBuildings(building);
            
            //NPC zum Arbeitsplatz laufen lassen
            npc.goToWorkPlace();
        }
    }
    
    //Arbeitenden NPC wieder entfernen
    public static void removeNPCFromBuilding(WorkerControl npc){
        NPCControl freeNPC = new NPCControl(npc.getHome());
        freeNPC.setIsWalkingRandom(true);


        //NPCControl austauschen
        Node node = (Node)npc.getSpatial();
        freeNPC.setAnimControl(npc.getAnimControl());
        freeNPC.setHome(npc.getHome());
        freeNPC.setSpatial(node);
        node.addControl(freeNPC);
        node.removeControl(WorkerControl.class);     
        
        //NPCControl im Wohnhaus austauschen
        npc.getHome().getNpcs()[npc.getHome().getIndex(npc)] = freeNPC;

        //Arbeit einstellen
        npc.getWorkPlace().setHasWorker(false);
        
        //Intere Listen neu organisieren
        addFreeNPC(freeNPC);
        removeWorkingNPCs(npc);
    }
    
    
    //Aktualisiert die Einwohneranzeige
    public static void actualizeText(){
        int freePeople = freeNPCs.size();
        int workingPeople = workingNPCs.size();
        
        String text = freePeople + "/" + workingPeople + " (" + (int)(moral*100) + "%)";
        Game.game.screens.setText("inGame", "Einwohner", text);
    }
    
    //Testen, ob die Zivilisationspunkte neue Gebäude und Bedürfniss freischalten
    public static void testZivilisationsPunkte(){
        switch(zivilisationsStufe){
            case 0:  if(zivilisationsPunkte >= 10){
                        addBedürfnis(Bedürfnis.BROT, bedürfnisseSek);
                        enableBuilding("bier");
                     } break;
            case 1:  if(zivilisationsPunkte >= 20){
                        addBedürfnis(Bedürfnis.BIER, bedürfnissePrim);
                        enableBuilding("brot");
                     } break;
        }
    }
    
    //Eine Zivilisationsstufe aufsteigen und Gebäude freischalten
    public static void enableBuilding(String name){
        Game.game.screens.getNifty().getScreen("baumenü").findElementByName(name).setConstraintX(new SizeValue("5%"));
        Game.game.screens.getNifty().getScreen("baumenü").layoutLayers();
        
        zivilisationsStufe++;
        testZivilisationsPunkte();
    }
}
