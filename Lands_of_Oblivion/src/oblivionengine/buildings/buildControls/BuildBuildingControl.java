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
    private boolean isBuild = false;
    private float step = 0;             //Schrittweite pro Sekunde
    private float step2 = 0;
    
    public BuildBuildingControl(Building building){
        this.building = building;
        
        //Anzahl der benötigten Ressourcen ermitteln
        price = building.getPRICE();
        
        //Gesamtzeit ermitteln
        for (int i = 0; i < price.length; i++) {
            time += timePerRessource * price[i][1];
        }
        
        step = building.getHeight()/time;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        if(!isBuild){
            spatial.move(0, -building.getHeight(), 0);
            isBuild = true;
        }
        
        //Nur wenn genug Ressourcen vorhanden sind kann weitergebaut werden
        if(Player.lager.getAnzahlRessourcen(index)!=0 || price[index][1]==0){
            if(timer >= timePerRessource){
                timer = 0;

                //Nächste Ressource verwenden, wenn die aktuelle Ressource schon komplett verwendet wurde
                if(price[index][1] == 0){

                    //Wenn das Gebäude fertig ist, kann dieser Control entfernt werden und es wird ein Kollisionsmodell erzeugt
                    if(index >= price.length-1){
                        building.finish();
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

            timer += tpf;
            
            //Building langsam aus dem Boden kommen lassen, bis die richtige Höhe erreicht ist
            if(step2 < building.getHeight()){
                spatial.move(0, step*tpf, 0);
                step2 += step*tpf;
            }
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
