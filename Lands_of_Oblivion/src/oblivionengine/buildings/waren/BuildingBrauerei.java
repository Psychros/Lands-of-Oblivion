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
import static oblivionengine.buildings.Building.PRICE_GETREIDEFARM;
import static oblivionengine.buildings.Building.SIZE_GETREIDEFARM;
import static oblivionengine.buildings.Building.testRessources;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.buildings.WorkBuilding;

/**
 *
 * @author To
 */
public class BuildingBrauerei extends WorkBuilding{
    //Objektvariablen
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingBrauerei() {
        super();
        
         if(testRessources(PRICE_BRAUEREI)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_BRAUEREI);
            setPRICE(PRICE_BRAUEREI);
            
            Box boxMesh = new Box(10f,13f,8f); 
            Geometry boxGeo = new Geometry("Colored Box", boxMesh); 
            Material boxMat = new Material(Game.game.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md"); 
            boxMat.setBoolean("UseMaterialColors", true); 
            boxMat.setColor("Ambient", ColorRGBA.Yellow); 
            boxMat.setColor("Diffuse", ColorRGBA.Yellow); 
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
        
        control.setRessource(Ressourcen.Bier);
        control.addPrice(Ressourcen.Wasser, 1);
        control.addPrice(Ressourcen.Getreide, 1);
        control.addPrice(Ressourcen.Hopfen, 1);
    }
}
