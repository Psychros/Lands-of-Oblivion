/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import oblivionengine.buildings.buildControls.BuildBuildingControl;
import oblivionengine.buildings.buildControls.BuildingPositionControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import oblivionengine.Game;
import static oblivionengine.charakter.Player.lager;
import static oblivionengine.charakter.Player.selectedBuilding;

/**
 *
 * @author To
 */
public class Building extends Node{
    //Objektvariablen
    
    //Rand, der um ein Gebäude herum ist
    public static final int RAND = 6;
    
    //Größe des Gebäudes
    private int[] size ={0, 0, 0};
    private int height = 0;
    
    //Preis des Gebäudes
    private int[][] PRICE = {}; 
    
    /*
     * Größen aller Gebäude
     * //x, z, höhe
     */
    public static final int[] SIZE_LAGER      = {7, 4, 7};   
    public static final int[] SIZE_HOLZFÄLLER = {10, 10, 8};   
    public static final int[] SIZE_STEINMETZ  = {10, 10, 8};   
    
    public static final int[] SIZE_HOLZHAUS   = {5, 5, 5};
    public static final int[] SIZE_STEINHAUS  = {5, 5, 10};
    
    
    /*
     * Alle Baukosten
     * Baukosten werden in 2dimensionalen int-Arrays festgehalten
     * Der erste Wert ist die Rohstoff-ID und der zweite Wert die Anzahl
     */
    public static final int[][] PRICE_LAGER      = {{Ressourcen.Wood.ordinal(), 10}};  
    public static final int[][] PRICE_HOLZFÄLLER = {{Ressourcen.Wood.ordinal(), 10}}; 
    public static final int[][] PRICE_STEINMETZ  = {{Ressourcen.Wood.ordinal(), 20}};
    
    public static final int[][] PRICE_HOLZHAUS   = {{Ressourcen.Wood.ordinal(), 5}};
    public static final int[][] PRICE_STEINHAUS  = {{Ressourcen.Wood.ordinal(), 5},
                                                    {Ressourcen.Stone.ordinal(), 8}};
    
    public static enum IDs{
        Lager, Holzfäller, Steinmetz
    }

    
    //--------------------------------------------------------------------------
    //Konstruktoren 
    public Building(){
        Game.game.mapState.getMap().attachChild(this);
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    public void setLocalTranslation(Vector2f pos){
        setLocalTranslation(pos.x, Game.game.mapState.getMap().getTerrain().getHeight(pos), pos.y);
    }
    
    public void setSize(int[] size){
        this.size = size;
    }

    public int[] getSize() {
        return size;
    } 
    
    public int getHeight(){
        return size[2];
    }

    public int[][] getPRICE() {
        return PRICE;
    }

    public void setPRICE(int[][] PRICE) {
        //Klonen des Preises, damit nicht der Originalpreis verändert werden kann
        int[][] price = new int[PRICE.length][2];
        for (int i = 0; i < PRICE.length; i++) {
            price[i][0] = PRICE[i][0];
            price[i][1] = PRICE[i][1];
        }
        
        this.PRICE = price;
    }
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    //Übergebene Baukosten bezahlen
    public boolean payPrice(int[][] price){
        //Ressourcen aus dem Lager entfernen
        for (int i = 0; i < price.length; i++) {
            if(lager.getAnzahlRessourcen(price[i][0]) >= price[i][1])
                lager.addRessourcen(price[i][0], -price[i][1]);
            else
                return false;
        }
        
        Game.game.screens.setText("inGame", "Baumstämme", lager.getAnzahlRessourcen(Ressourcen.Wood));
        return true;
    }
    
    //testen, ob genug Ressourcen zum Bauen vorhanden sind
    //Übergebene Baukosten bezahlen
    public static boolean testRessources(int[][] price){
        //Ressourcen aus dem Lager entfernen
        for (int i = 0; i < price.length; i++) {
            if(!(lager.getAnzahlRessourcen(price[i][0]) >= price[i][1]))
                return false;
        }
        return true;
    }
    
    
    
    //Boden plätten und an das Gebäude anpassen
    public void plainGround(){
        //Höhe, auf die der Boden geebnet werden soll
        float height = Game.game.mapState.getMap().getTerrain().getHeight(new Vector2f(getLocalTranslation().x, getLocalTranslation().z));
        TerrainQuad terrain = Game.game.mapState.getMap().getTerrain();
        
        for (int x = (int)(getLocalTranslation().x-size[0]/2 - 2*RAND); x < (int)(getLocalTranslation().x+size[0]/2 + RAND); x++) {
           for (int y = (int)(getLocalTranslation().z-size[1]/2 - RAND/2f); y < (int)(getLocalTranslation().z+size[1]/2 + RAND/1.5f); y++) {
               terrain.setHeight(new Vector2f(x, y), height);
           } 
        }
        terrain.recalculateAllNormals();
        
        //Physikalischen Körper aktualisieren
        terrain.getControl(RigidBodyControl.class).destroy();
        RigidBodyControl undergroundPhysic = new RigidBodyControl(0);
        terrain.addControl(undergroundPhysic);  
        Game.game.mapState.getMap().getBulletAppState().getPhysicsSpace().add(undergroundPhysic);
        undergroundPhysic.setFriction(3f);
    }
    
    //Gebäude endgültig bauen
    public void build(){
        if(testRessources(PRICE)){
            //RigidBodyControl hinzufügen
            RigidBodyControl control = new RigidBodyControl(0);
            addControl(control);
            Game.game.mapState.getMap().getBulletAppState().getPhysicsSpace().add(control);
            
            //Boden Ebenen und BuildingPositionControl entfernen
            selectedBuilding.removeControl(BuildingPositionControl.class);
            selectedBuilding.plainGround();

            //Bauvorgang starten
            selectedBuilding.addControl(new BuildBuildingControl(this));
        } else{
            removeFromParent();
        }
    }
    
    //Gebäude fertigstellen und den Bauprozess beenden
    public void finishBuilding(){
        
    }
}
