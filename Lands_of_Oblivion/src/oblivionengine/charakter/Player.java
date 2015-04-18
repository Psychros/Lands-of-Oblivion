/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter;

import com.jme3.bullet.control.RigidBodyControl;
import oblivionengine.buildings.GlobalesLager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import oblivionengine.Game;
import oblivionengine.TreeControl;
import oblivionengine.buildings.Building;
import oblivionengine.buildings.BuildingHolzfäller;
import oblivionengine.buildings.BuildingLager;
import oblivionengine.buildings.buildControls.BuildingPositionControl;
import oblivionengine.buildings.Ressourcen;

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
        
        //Baum fällen
        if(name.equals("CutTree")){
            cutTree();
        } else if(name.equals("Build"))
            build();
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
                TreeControl treeControl = new TreeControl(tree);
                tree.addControl(treeControl);
                treeControl.fallDown();
                
                
                //Das GlobalesLager mit Holz füllen
                lager.addRessourcen(Ressourcen.Wood, 1);
                
                //Text ändern
                Game.game.screens.setText("inGame", "Baumstämme", lager.getAnzahlRessourcen(Ressourcen.Wood));
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
                    case "Lager":      if(Building.testRessources(Building.PRICE_LAGER))selectedBuilding = new BuildingLager(); break;
                    case "Holzfäller": if(Building.testRessources(Building.PRICE_HOLZFÄLLER))selectedBuilding = new BuildingHolzfäller(); break;
                }
                
                //Dafür sorgen, dass das Building der Mausposition folgt
                if(selectedBuilding != null)
                    selectedBuilding.addControl(new BuildingPositionControl());
                
                isBuildingSelected = true;
            }
            else{
                //Das Gebäude endgültig bauen
                if(selectedBuilding != null){
                    selectedBuilding.build();
                    selectedBuilding = null;
                }
                isBuildingSelected = false;
            }
            
        }
        
    }
}
