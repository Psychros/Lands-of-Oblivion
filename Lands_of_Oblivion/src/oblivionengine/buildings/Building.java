/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import oblivionengine.Game;
import static oblivionengine.charakter.Player.lager;

/**
 *
 * @author To
 */
public class Building {
    //Objektvariablen
    protected Node building;  //Alles sichtbare, was zum Building gehört
    protected int sizeX, sizeZ;
    
    //Rand, der um ein Gebäude herum ist
    public static final int RAND = 6;
    
    /*
     * Größen aller Gebäude
     */
    public static final int[] SIZE_LAGER = {7, 4};
    
    /*
     * Alle Baukosten
     * Baukosten werden in 2dimensionalen int-Arrays festgehalten
     * Der erste Wert ist die Rohstoff-ID und der zweite Wert die Anzahl
     */
    public static final int[][] PRICE_LAGER = {{Ressourcen.Wood.ordinal(), 10}};  
    
    public static enum IDs{
        Lager
    }

    
    //--------------------------------------------------------------------------
    //Konstruktoren 
    public Building(){
        building = new Node("Building");
        Game.game.mapState.getMap().attachChild(building);
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    public void setLocalTranslation(Vector2f pos){
        building.setLocalTranslation(pos.x, Game.game.mapState.getMap().getTerrain().getHeight(pos), pos.y);
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
        
        Game.game.screens.setText("Baumstämme", lager.getAnzahlRessourcen(Ressourcen.Wood));
        return true;
    }
    
    
    
    //Boden plätten und an das Gebäude anpassen
    public void plainGround(int[] sizeOfBuilding){
        //Höhe, auf die der Boden geebnet werden soll
        float height = Game.game.mapState.getMap().getTerrain().getHeight(new Vector2f(building.getLocalTranslation().x, building.getLocalTranslation().z));
        TerrainQuad terrain = Game.game.mapState.getMap().getTerrain();
        
        for (int x = (int)(building.getLocalTranslation().x-sizeOfBuilding[0]/2 - 2*RAND); x < (int)(building.getLocalTranslation().x+sizeOfBuilding[0]/2 + RAND); x++) {
           for (int y = (int)(building.getLocalTranslation().z-sizeOfBuilding[1]/2 - RAND/2f); y < (int)(building.getLocalTranslation().z+sizeOfBuilding[1]/2 + RAND/1.5f); y++) {
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
}
