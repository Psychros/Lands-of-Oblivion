/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import oblivionengine.buildings.buildControls.BuildBuildingControl;
import oblivionengine.buildings.buildControls.BuildingPositionControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import oblivionengine.Game;
import oblivionengine.charakter.npc.NPCManager;
import static oblivionengine.charakter.player.Player.lager;
import static oblivionengine.charakter.player.Player.selectedBuilding;

/**
 *
 * @author To
 */
public class Building extends Node{

    protected float[] size ={0, 0, 0};      //Größe des Gebäudes
    protected int[][] PRICE = {};         //Preis des Gebäudes
    
    public static final int RAND = 6;   //Rand, der um ein Gebäude herum ist
    
    /*
     * Größen aller Gebäude
     * //x, z, höhe
     */
    //Lager
    public static final float[] SIZE_LAGER          = {7, 4, 7};   
    
    //Arbeitsgebäude
    public static final float[] SIZE_BÄCKER         = {8, 10, 8}; 
    public static final float[] SIZE_BRAUEREI       = {10, 8, 13}; 
    public static final float[] SIZE_BRUNNEN        = {5, 5, 6}; 
    public static final float[] SIZE_FISCHER        = {5, 10, 10}; 
    public static final float[] SIZE_GETREIDEFARM   = {15, 15, 8}; 
    public static final float[] SIZE_HOLZFÄLLER     = {10, 10, 8}; 
    public static final float[] SIZE_HOPFENFARM     = {15, 15, 8}; 
    public static final float[] SIZE_KIRCHE         = {10, 20, 20}; 
    public static final float[] SIZE_MÜHLE          = {8, 8, 16}; 
    public static final float[] SIZE_STEINMETZ      = {10, 10, 8};
    
    //Einwohnergebäude
    public static final float[] SIZE_HOLZHAUS       = {3f, 2f, 7.5f};
    public static final float[] SIZE_STEINHAUS      = {5, 5, 10};
    
    
    /*
     * Alle Baukosten
     * Baukosten werden in 2dimensionalen int-Arrays festgehalten
     * Der erste Wert ist die Rohstoff-ID und der zweite Wert die Anzahl
     */
    //Lager
    public static final int[][] PRICE_LAGER       = {{Ressourcen.Holz.ordinal(), 10}}; 
    
    //Arbeitsgebäude
    public static final int[][] PRICE_BÄCKER      = {{Ressourcen.Holz.ordinal(), 1}, {Ressourcen.Stein.ordinal(), 1}};
    public static final int[][] PRICE_BRAUEREI    = {{Ressourcen.Holz.ordinal(), 1}, {Ressourcen.Stein.ordinal(), 1}};
    public static final int[][] PRICE_BRUNNEN     = {{Ressourcen.Stein.ordinal(), 6}};
    public static final int[][] PRICE_FISCHER     = {{Ressourcen.Holz.ordinal(), 10}};
    public static final int[][] PRICE_GETREIDEFARM= {{Ressourcen.Holz.ordinal(), 1}, {Ressourcen.Stein.ordinal(), 1}};
    public static final int[][] PRICE_HOLZFÄLLER  = {{Ressourcen.Holz.ordinal(), 10}};
    public static final int[][] PRICE_HOPFENFARM  = {{Ressourcen.Holz.ordinal(), 1}, {Ressourcen.Stein.ordinal(), 1}};
    public static final int[][] PRICE_KIRCHE      = {{Ressourcen.Holz.ordinal(), 1}, {Ressourcen.Stein.ordinal(), 1}};
    public static final int[][] PRICE_MÜHLE       = {{Ressourcen.Holz.ordinal(), 1}, {Ressourcen.Stein.ordinal(), 1}};
    public static final int[][] PRICE_STEINMETZ   = {{Ressourcen.Holz.ordinal(), 15}};
    
    //Einwohnergebäude
    public static final int[][] PRICE_HOLZHAUS    = {{Ressourcen.Holz.ordinal(), 5}};
    public static final int[][] PRICE_STEINHAUS   = {{Ressourcen.Holz.ordinal(), 5}, {Ressourcen.Stein.ordinal(), 8}};

    
    //--------------------------------------------------------------------------
    //Konstruktoren 
    public Building(){
        Game.game.mapState.getMap().getBuildings().attachChild(this);
        
        NPCManager.numberBuildings++;
        
        //Schatten aktivieren
        setShadowMode(RenderQueue.ShadowMode.Receive);
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    public void setLocalTranslation(Vector2f pos){
        setLocalTranslation(pos.x, Game.game.mapState.getMap().getTerrain().getHeight(pos), pos.y);
    }
    
    public void setSize(float[] size){
        this.size = size;
    }

    public float[] getSize() {
        return size;
    } 
    
    public float getHeight(){
        return size[2];
    }

    public int[][] getPRICE() {
        //Klonen des Preises, damit nicht der Originalpreis verändert werden kann
        int[][] price = new int[PRICE.length][2];
        for (int i = 0; i < PRICE.length; i++) {
            price[i][0] = PRICE[i][0];
            price[i][1] = PRICE[i][1];
        }
        
        return price;
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
        
        Game.game.screens.setText("inGame", "Baumstämme", lager.getAnzahlRessourcen(Ressourcen.Holz));
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
        Game.game.mapState.getMap().getBulletAppState().getPhysicsSpace().remove(terrain.getControl(RigidBodyControl.class));
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
    public void finish(){
        
        /*
         * Es dürfen keine NPCs durch Wände gehen
         */
        Vector3f pos;
        //Vorne und hinten
        for (int x = 0; x < getSize()[0]; x++) {
            //Position kopiere, damit die Werte immer vom Originalvektor aus berechnet werden
            pos = new Vector3f(getLocalTranslation().x, getLocalTranslation().y, getLocalTranslation().z);
            //Vorne
            Game.game.mapState.getMap().setField((int)(pos.subtract(getSize()[0]/2, 0, 0).x)+x, (int)(pos.subtract(0, 0, -getSize()[1]/2).z), false);
            
            //Position kopiere, damit die Werte immer vom Originalvektor aus berechnet werden
            pos = new Vector3f(getLocalTranslation().x, getLocalTranslation().y, getLocalTranslation().z);
            
            //Hinten
            Game.game.mapState.getMap().setField((int)(pos.subtract(getSize()[0]/2, 0, 0).x)+x, (int)(pos.subtract(0, 0, getSize()[1]/2).z), false);
        }
        
        //Position kopiere, damit die Werte immer vom Originalvektor aus berechnet werden
        pos = new Vector3f(getLocalTranslation().x, getLocalTranslation().y, getLocalTranslation().z);
        
        //Rechts und Links
        for (int z = 0; z < getSize()[1]; z++) {
            //Position kopiere, damit die Werte immer vom Originalvektor aus berechnet werden
            pos = new Vector3f(getLocalTranslation().x, getLocalTranslation().y, getLocalTranslation().z);
            
            //Rechts
            Game.game.mapState.getMap().setField((int)(pos.subtract(0, 0, -getSize()[0]/2).x), (int)(pos.subtract(getSize()[0]/2, 0, 0).z)+z, false);
           
            //Position kopiere, damit die Werte immer vom Originalvektor aus berechnet werden
            pos = new Vector3f(getLocalTranslation().x, getLocalTranslation().y, getLocalTranslation().z);
            
            //Links
            Game.game.mapState.getMap().setField((int)(pos.subtract(0, 0, getSize()[0]/2).x), (int)(pos.subtract(getSize()[0]/2, 0, 0).z)+z, false);
        }
        pos = new Vector3f(getLocalTranslation().x, getLocalTranslation().y, getLocalTranslation().z);
        
        //Tür setzen
        Game.game.mapState.getMap().setField((int)pos.x, (int)(pos.subtract(0, 0, -getSize()[0]/2).z), false);
    }
    
    //Gebäude abreißen
    public void demolish(){
        
        /*
         * NPCs dürfen wieder an durch die abgerissenen Gebäude laufen
         */
        //Vorne und hinten
        for (int x = 0; x < getSize()[0]; x++) {
            //Vorne
            Game.game.mapState.getMap().setField((int)(getLocalTranslation().subtract(getSize()[0]/2, 0, 0).x)+x, (int)(getLocalTranslation().subtract(0, 0, -getSize()[1]/2).z), true);
            //Hinten
            Game.game.mapState.getMap().setField((int)(getLocalTranslation().subtract(getSize()[0]/2, 0, 0).x)+x, (int)(getLocalTranslation().subtract(0, 0, getSize()[1]/2).z), true);
        }
        
        //Rechts und Links
        for (int z = 0; z < getSize()[1]; z++) {
            //Rechts
            Game.game.mapState.getMap().setField((int)(getLocalTranslation().subtract(getSize()[0]/2, 0, 0).z)+z, (int)(getLocalTranslation().subtract(0, 0, -getSize()[0]/2).x), true);
            //Links
            Game.game.mapState.getMap().setField((int)(getLocalTranslation().subtract(getSize()[0]/2, 0, 0).z)+z, (int)(getLocalTranslation().subtract(0, 0, getSize()[0]/2).x), true);
        }
    }
}
