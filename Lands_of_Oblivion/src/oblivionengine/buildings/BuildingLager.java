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
import oblivionengine.charakter.Player;

/**
 *
 * @author To
 */
public class BuildingLager extends Building{
    //Objektvariablen
    public static final int SIZE = 100; //Größe der neuen Lagerkapazität

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingLager() {
        super();
        if(payPrice(PRICE_LAGER)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            //Größe des Gebäudes 
            setSize(SIZE_LAGER);
            
            Box boxMesh = new Box(7f,6f,4f); 
            Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
            Material boxMat = new Material(Game.game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
            boxMat.setBoolean("UseMaterialColors", true); 
            boxMat.setColor("Ambient", ColorRGBA.Green); 
            boxMat.setColor("Diffuse", ColorRGBA.Green); 
            boxGeo.setMaterial(boxMat); 
            building.attachChild(boxGeo);
            
            //Größe des globalen Lagers erhöhen und die Anzeige aktualisieren
            Player.lager.setGröße(Player.lager.getGröße()+SIZE);
            Game.game.screens.setText("Lager", Player.lager.getGröße());
        }
    }

    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
}
