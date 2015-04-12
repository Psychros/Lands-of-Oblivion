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

/**
 *
 * @author To
 */
public class BuildingLager extends Building{
    //Objektvariablen

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingLager() {
        super();
        if(payPrice(PRICE_LAGER)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            Box boxMesh = new Box(7f,6f,4f); 
            Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
            Material boxMat = new Material(Game.game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
            boxMat.setBoolean("UseMaterialColors", true); 
            boxMat.setColor("Ambient", ColorRGBA.Green); 
            boxMat.setColor("Diffuse", ColorRGBA.Green); 
            boxGeo.setMaterial(boxMat); 
            building.attachChild(boxGeo);
        }
    }

    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
}
