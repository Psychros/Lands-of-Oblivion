/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import oblivionengine.Game;
import static oblivionengine.buildings.Building.PRICE_LAGER;
import static oblivionengine.buildings.Building.testRessources;
import oblivionengine.charakter.player.Player;

/**
 *
 * @author To
 */
public class BuildingLager extends Building{
    //Objektvariablen
    public static final int SIZE = 20; //Größe der neuen Lagerkapazität

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingLager() {
        super();
         if(testRessources(PRICE_LAGER)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_LAGER);
            setPRICE(PRICE_LAGER);

            Box boxMesh = new Box(7f,6f,4f); 
            Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
            Material boxMat = new Material(Game.game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
            boxMat.setBoolean("UseMaterialColors", true); 
            boxMat.setColor("Ambient", ColorRGBA.Green); 
            boxMat.setColor("Diffuse", ColorRGBA.Green); 
            boxGeo.setMaterial(boxMat); 
            attachChild(boxGeo);
         }
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    public void finishBuilding() {
        super.finishBuilding(); 
        Player.lager.setGröße(Player.lager.getGröße()+SIZE);
    }
    
}
