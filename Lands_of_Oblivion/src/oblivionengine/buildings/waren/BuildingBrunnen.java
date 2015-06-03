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
public class BuildingBrunnen extends WorkBuilding{
    //Objektvariablen
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingBrunnen() {
        super();
        
         if(testRessources(PRICE_BRUNNEN)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_BRUNNEN);
            setPRICE(PRICE_BRUNNEN);
            
            Geometry haus = (Geometry)Game.game.getAssetManager().loadModel("Models/Buildings/Brunnen.j3o");      
            attachChild(haus);
            scale(3.5f);
         }
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    public void finish() {
        super.finish(); 
        
        control.setRessource(Ressourcen.Wasser);
        control.setTime(6);
        NPCManager.addZiviisationsPunkte(2);
    }
}
