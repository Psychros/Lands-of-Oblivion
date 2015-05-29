/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.player;

import com.jme3.bullet.control.RigidBodyControl;
import oblivionengine.buildings.GlobalesLager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import oblivionengine.Game;
import oblivionengine.TreeCutControl;
import oblivionengine.buildings.Building;
import oblivionengine.buildings.baumaterial.BuildingHolzfäller;
import oblivionengine.buildings.einwohner.BuildingHolzhaus;
import oblivionengine.buildings.waren.BuildingLager;
import oblivionengine.buildings.einwohner.BuildingSteinhaus;
import oblivionengine.buildings.baumaterial.BuildingSteinmetz;
import oblivionengine.buildings.buildControls.BuildingPositionControl;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.buildings.WorkBuilding;
import oblivionengine.buildings.buildControls.DemolishBuildingControl;
import oblivionengine.buildings.waren.BuildingFischer;
import oblivionengine.buildings.gesellschaft.BuildingKirche;
import oblivionengine.buildings.waren.BuildingBrauerei;
import oblivionengine.buildings.waren.BuildingBrunnen;
import oblivionengine.buildings.waren.BuildingBäcker;
import oblivionengine.buildings.waren.BuildingGetreidefarm;
import oblivionengine.buildings.waren.BuildingHopfenfarm;
import oblivionengine.buildings.waren.BuildingMühle;
import oblivionengine.charakter.npc.NPCManager;

/**
 *
 * @author To
 */
public class Player extends CharakterControl{
    
    //Globales GlobalesLager
    public static GlobalesLager lager = new GlobalesLager();
    
    //Aktuell ausgewähltest Gebäude
    public static String selectedBuildingID;
    public static Building selectedBuilding;
    public static boolean isBuildingSelected = false;
    public boolean canBeBuild = false;
    
    //Wird ein Gebäude abgerissen?
    private int deleteCount = 0;

    //--------------------------------------------------------------------------
    //Konstruktoren
    
    public Player(float radius, float height, float mass) {
        super(radius, height, mass);
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    public Spatial getSpatial() {
        return spatial;
    }
    

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        super.onAction(name, isPressed, tpf);
        
        if(name.equals("CutTree")){
            cutTree();
        } else if(name.equals("Build")){
            build();
        } else if(name.equals("DeleteBuilding")){
            deleteBuilding();
        } else if(name.equals("CancelDeleteBuilding"))
            deleteCount = 0;
        
    }
    
    //Baum fällen
    public void cutTree(){
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(Game.game.getCam().getLocation(), Game.game.getCam().getDirection());
        Game.game.mapState.getMap().getTrees().collideWith(ray, results);
        
        if(results.size() != 0){
            Geometry tree = results.getClosestCollision().getGeometry();
            if(tree != null && tree.getParent().getControl(RigidBodyControl.class).getMass()==0 && tree.getParent().getName().equals("Tree") && results.getClosestCollision().getDistance() < 10){          
                
                //Den Baum umfallen lassen
                TreeCutControl treeControl = new TreeCutControl(tree);
                tree.addControl(treeControl);
                treeControl.fallDown();
                
                
                //Das GlobalesLager mit Holz füllen
                lager.addRessourcen(Ressourcen.Holz, 1);
                
                //Text ändern
                Game.game.screens.setText("inGame", "Baumstämme", lager.getAnzahlRessourcen(Ressourcen.Holz));
            }
        }
    }
    
    /*
     * Gebäude bauen
     */
    public void build(){
        if(selectedBuildingID != null){   //Nur wenn im Baumenü ein Gebäude ausgewählt wurde
            if(!isBuildingSelected){
                
                //ID des zu bauenden Gebäudes überprüfen
                switch(selectedBuildingID){
                    //Lager
                    case "Lager":       if(Building.testRessources(Building.PRICE_LAGER))selectedBuilding = new BuildingLager(); break;
                    
                    //Arbeitsgebäude
                    case "Bäcker":      if(Building.testRessources(Building.PRICE_BÄCKER))selectedBuilding = new BuildingBäcker(); break;
                    case "Brauerei":    if(Building.testRessources(Building.PRICE_BRAUEREI))selectedBuilding = new BuildingBrauerei(); break;
                    case "Brunnen":     if(Building.testRessources(Building.PRICE_BRUNNEN))selectedBuilding = new BuildingBrunnen(); break;  
                    case "Fischer":     if(Building.testRessources(Building.PRICE_FISCHER))selectedBuilding = new BuildingFischer(); break;   
                    case "Getreidefarm":if(Building.testRessources(Building.PRICE_GETREIDEFARM))selectedBuilding = new BuildingGetreidefarm(); break;       
                    case "Holzfäller":  if(Building.testRessources(Building.PRICE_HOLZFÄLLER))selectedBuilding = new BuildingHolzfäller(); break;
                    case "Hopfenfarm":  if(Building.testRessources(Building.PRICE_HOPFENFARM))selectedBuilding = new BuildingHopfenfarm(); break;
                    case "Kirche":      if(Building.testRessources(Building.PRICE_KIRCHE))selectedBuilding = new BuildingKirche(); break;      
                    case "Mühle":       if(Building.testRessources(Building.PRICE_MÜHLE))selectedBuilding = new BuildingMühle(); break;
                    case "Steinmetz":   if(Building.testRessources(Building.PRICE_STEINMETZ))selectedBuilding = new BuildingSteinmetz(); break;                
                
                    //Einwohner
                    case "Holzhaus":    if(Building.testRessources(Building.PRICE_HOLZHAUS))selectedBuilding = new BuildingHolzhaus(); break;
                    case "Steinhaus":   if(Building.testRessources(Building.PRICE_STEINHAUS))selectedBuilding = new BuildingSteinhaus(); break;
                }
                
                //Dafür sorgen, dass das Building der Mausposition folgt
                if(selectedBuilding != null)
                    selectedBuilding.addControl(new BuildingPositionControl());
                
                isBuildingSelected = true;
            }
            else{
                //Das Gebäude endgültig bauen
                if(selectedBuilding != null){
                    Vector2f v = new Vector2f(selectedBuilding.getLocalTranslation().x, selectedBuilding.getLocalTranslation().z);
                    
                    if(canBeBuild && !v.equals(Vector2f.ZERO)){
                        selectedBuilding.build();
                        selectedBuilding = null;
                    }
                    else{
                        selectedBuilding.removeFromParent();
                        selectedBuilding.removeControl(BuildingPositionControl.class);
                    }
                }
                
                isBuildingSelected = false;
            }     
        }
    }
    
    
    //Gebäude abreißen
    public void deleteBuilding(){
        if(selectedBuildingID == null || selectedBuildingID.equals("")){
            if(deleteCount <3)
                deleteCount++;
            else{
                CollisionResults results = new CollisionResults();
                Ray ray = new Ray(Game.game.getCam().getLocation(), Game.game.getCam().getDirection());
                Game.game.mapState.getMap().getBuildings().collideWith(ray, results);

                Abreißen:
                if(results.size() != 0){
                    Node n = (Node)results.getClosestCollision().getGeometry().getParent();

                    //Oberste Node des Gebäudes herausfinden
                    int i = 100;
                    Schleife:
                    while((n instanceof Building) == false){
                        n = n.getParent();

                        if(i == 0)
                            break Abreißen;
                        i--;
                    }

                    //Node in ein Gebäude umwandeln
                    Building b = (Building)n;

                    //Physikalischen Körper entfernen
                    Game.game.mapState.getMap().getBulletAppState().getPhysicsSpace().remove(b.getControl(RigidBodyControl.class));

                    //Falls es sich um ein Arbeitsgebäude handelt, muss der Arbeiter entfernt werden
                    if(b instanceof WorkBuilding){
                        WorkBuilding wB = (WorkBuilding)b;

                        //Gebäude entfernen
                        NPCManager.removeWorkingBuildings(b);
                        
                        //NPC zum Arbeitslosen machensssss
                        if(wB.getWorker() != null)
                            NPCManager.removeNPCFromBuilding(wB.getWorker());                       
                    }
                    else{
                        //Gebäude entfernen
                        NPCManager.removeFreeBuildings(b);
                    }
                    
                    //Der Gebäude zähler wird zurückgesetzt
                    NPCManager.numberBuildings--;
                    deleteCount = 0;
                    
                    //Gebäude von der Map entfernen
                    b.addControl(new DemolishBuildingControl(b));
                }
            }
        }
    }
}
