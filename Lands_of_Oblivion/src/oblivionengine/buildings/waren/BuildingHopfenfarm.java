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
import static oblivionengine.buildings.Building.PRICE_GETREDEFARM;
import static oblivionengine.buildings.Building.SIZE_GETREIDEFARM;
import static oblivionengine.buildings.Building.testRessources;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.buildings.WorkBuilding;

/**
 *
 * @author To
 */
public class BuildingHopfenfarm extends WorkBuilding{
    //Objektvariablen
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingHopfenfarm() {
        super();
        
         if(testRessources(PRICE_HOPFENFARM)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_HOPFENFARM);
            setPRICE(PRICE_HOPFENFARM);
            
            Box boxMesh = new Box(15f,8f,15f); 
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
        
        control.setRessource(Ressourcen.Hopfen);
        control.setPrice(Ressourcen.Wasser, 2);
    }
}
