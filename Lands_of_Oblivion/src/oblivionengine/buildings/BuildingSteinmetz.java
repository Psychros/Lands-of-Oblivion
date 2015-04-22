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
import oblivionengine.charakter.NPCManager;

/**
 *
 * @author To
 */
public class BuildingSteinmetz extends Building{
    //Objektvariablen
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingSteinmetz() {
        super();
        
         if(testRessources(PRICE_STEINMETZ)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_STEINMETZ);
            setPRICE(PRICE_STEINMETZ);
            NPCManager.getFreeBuildings().add(this);

            Box boxMesh = new Box(10f,8f,10f); 
            Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
            Material boxMat = new Material(Game.game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
            boxMat.setBoolean("UseMaterialColors", true); 
            boxMat.setColor("Ambient", ColorRGBA.Gray); 
            boxMat.setColor("Diffuse", ColorRGBA.Gray); 
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
        addControl(new SteinmetzControl());
    }
    
}
