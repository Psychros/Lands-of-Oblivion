/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.buildControls;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import oblivionengine.buildings.Building;
import oblivionengine.charakter.player.Player;

/**
 * Gebäude abreißen und langsam im Boden versinken lassen
 * @author To
 */
public class DemolishBuildingControl extends AbstractControl{
    
    public static final int timePerRessource = 3; //Zeit in Sekunden, die zum Verbrauch einer Ressource gebraucht wird
    private int time = 0;   //Gesamtzet, welche zum Bauen des Gebäudes gebraucht wird
    private float timer = 0;
    private int [][] price; int index = 0;  //Preis des Gebäude und der Index der aktuell verwendeten Ressource
    private Building building;
    private float step = 0;             //Schrittweite pro Sekunde
    private boolean isBuild = true;
    
    public DemolishBuildingControl(Building building){
        this.building = building;
        
        //Anzahl der benötigten Ressourcen ermitteln
        price = building.getPRICE();
        
        //Gesamtzeit ermitteln
        for (int i = 0; i < price.length; i++) {
            time += timePerRessource * price[i][1];
        }
        
        step = building.getHeight()/time;
        
        //Falls noch gebaut wird wird der Bauvorgang abgebrochen
        if(building.getControl(BuildBuildingControl.class) != null){
            building.removeControl(BuildBuildingControl.class);
            isBuild = false;
        }
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {    

        //Nach dem Versinken die Gebäudespeziefischen Abreißvorgänge starten
        if(timer >= time){
            //Nur Gebäudespezifische Abreißvorgänge starten, wenn es schon fertig gebau tist
            if(isBuild)
                building.demolish();
            spatial.removeFromParent();
            spatial.removeControl(this);
            
            return;
        }
        
        //Building langsam aus dem Boden kommen lassen, bis die richtige Höhe erreicht ist
        spatial.setLocalTranslation(spatial.getLocalTranslation().add(0, -step*tpf, 0));
        timer += tpf;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
