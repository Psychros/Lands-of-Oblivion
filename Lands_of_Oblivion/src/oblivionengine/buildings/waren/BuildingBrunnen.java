/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.waren;

import com.jme3.scene.Node;
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
    
    static Node model;  
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingBrunnen() {
        super();
        
         if(testRessources(PRICE_BRUNNEN)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_BRUNNEN);
            setPRICE(PRICE_BRUNNEN);
            
            long time = System.nanoTime();
            Node haus = model.clone(true);
            attachChild(haus);
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
    
    
    public static void loadModel(){
        model = (Node)Game.game.getAssetManager().loadModel("Models/Buildings/Brunnen.j3o");
    }
}
