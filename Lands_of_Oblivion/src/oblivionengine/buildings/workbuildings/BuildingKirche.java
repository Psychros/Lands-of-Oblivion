/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.workbuildings;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import oblivionengine.Game;
import static oblivionengine.buildings.Building.testRessources;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.charakter.npc.NPCManager;

/**
 *
 * @author To
 */
public class BuildingKirche extends WorkBuilding{
    //Objektvariablen
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingKirche() {
        super();
        
         if(testRessources(PRICE_KIRCHE)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_KIRCHE);
            setPRICE(PRICE_KIRCHE);
            
            Box boxMesh = new Box(10f,20f,20f); 
            Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
            Material boxMat = new Material(Game.game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
            boxMat.setBoolean("UseMaterialColors", true); 
            boxMat.setColor("Ambient", ColorRGBA.DarkGray); 
            boxMat.setColor("Diffuse", ColorRGBA.DarkGray); 
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
        
        control.setRessource(Ressourcen.Belief);
        control.setTime(3);
        NPCManager.setIsChurch(true);
        System.out.println(NPCManager.getMaxMoral());
    }
    
}
