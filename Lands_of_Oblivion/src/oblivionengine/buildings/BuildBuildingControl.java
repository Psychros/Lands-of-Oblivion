/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.buildings;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import oblivionengine.charakter.Player;

/**
 * Das Gebäude bauen und es langsam aus dem Boden aufsteigen lassen
 *
 * @author To
 */
public class BuildBuildingControl extends AbstractControl {

    public static final int timePerRessource = 3; //Zeit in Sekunden, die zum Verbrauch einer Ressource gebraucht wird
    private int time = 0;   //Gesamtzet, welche zum Bauen des Gebäudes gebraucht wird
    private float timer = 0;
    private int [][] price; int index = 0;  //Preis des Gebäude und der Index der aktuell verwendeten Ressource
    private Building building;
    
    public BuildBuildingControl(Building building){
        this.building = building;
        building.setLocalTranslation(new Vector3f(building.getLocalTranslation().x, building.getLocalTranslation().y-building.getHeight(), building.getLocalTranslation().z));
        
        //Anzahl der benötigten Ressourcen ermitteln
        price = building.getPRICE();
        
        //Gesamtzeit ermitteln
        for (int i = 0; i < price.length; i++) {
            time += timePerRessource * price[i][1];
        }
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        timer += tpf;
        if(timer >= timePerRessource){
            timer = 0;
            
            //Nächste Ressource verwenden, wenn die aktuelle Ressource schon komplett verwendet wurde
            if(price[index][1] == 0){
                
                //Wenn das Gebäude fertig ist, kann dieser Control entfernt werden und es wird ein Kollisionsmodell erzeugt
                if(index >= price.length-1){
                    building.finishBuilding();
                    spatial.removeControl(this);
                    return;
                } else{
                    index++;
                }
                    
            } else{
                price[index][1]--;
                Player.lager.addRessourcen(price[index][0], -1);
            }
        }
        
        //Building langsam aus dem Boden kommen lassen
        float newHeight = spatial.getLocalTranslation().y + ((float)building.getHeight()/(float)time)*tpf;
        spatial.setLocalTranslation(new Vector3f(spatial.getLocalTranslation().x, newHeight, spatial.getLocalTranslation().z));
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
