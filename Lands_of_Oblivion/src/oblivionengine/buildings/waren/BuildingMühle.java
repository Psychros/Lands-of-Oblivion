/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.waren;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import oblivionengine.Game;
import static oblivionengine.buildings.Building.testRessources;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.buildings.WorkBuilding;
import oblivionengine.charakter.npc.NPCManager;

/**
 *
 * @author To
 */
public class BuildingMühle extends WorkBuilding{
    //Objektvariablen
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingMühle() {
        super();
        
         if(testRessources(PRICE_MÜHLE)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_MÜHLE);
            setPRICE(PRICE_MÜHLE);
            
            Box boxMesh = new Box(8f,16f,8f); 
            Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
            Material boxMat = new Material(Game.game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
            boxMat.setBoolean("UseMaterialColors", true); 
            boxMat.setColor("Ambient", ColorRGBA.Brown); 
            boxMat.setColor("Diffuse", ColorRGBA.Brown); 
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
        
        control.setRessource(Ressourcen.Mehl);
        control.addPrice(Ressourcen.Getreide, 1);
        control.setTime(10);
        
        NPCManager.addZiviisationsPunkte(3);
    }
}
