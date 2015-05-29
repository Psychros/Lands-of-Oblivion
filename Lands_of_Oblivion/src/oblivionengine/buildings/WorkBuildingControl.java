/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import oblivionengine.charakter.npc.NPCManager;
import oblivionengine.charakter.player.Player;

/**
 *
 * @author To
 */
public class WorkBuildingControl extends AbstractControl{
    //Objektvariablen
    private int time = 15;
    private float timer = 0;
    
    private Ressourcen ressource = null;         //Zu produzierende Ressource
    private ArrayList<Ressourcen> ressourcePrice = new ArrayList<Ressourcen>();      //Kosten, um den Rohstoff zu produzieren
    private ArrayList<Integer> price = new ArrayList<Integer>();                                       //Anzahl der benötigten Rohstoffe

    //--------------------------------------------------------------------------
    //Konstruktoren
    public WorkBuildingControl() {
        
    }
    
    
    public WorkBuildingControl(Ressourcen ressource, int time) {
        this.ressource = ressource;
        this.time = time;
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Ressourcen getRessource() {
        return ressource;
    }

    public void setRessource(Ressourcen ressource) {
        this.ressource = ressource;
    }
    
    //Am besten diese Methode für den Preis verwenden
    public void addPrice(Ressourcen ressource, int price){
        ressourcePrice.add(ressource);
        this.price.add(price);
    }
    

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    protected void controlUpdate(float tpf) {
        WorkBuilding building = (WorkBuilding)spatial;
        
        //Produkt herstellen
        if(building.hasWorker()){
            timer += tpf;
            
            if(timer >= time){
                //Testen, ob andere Ressourcen zur Herstellung gebraucht werden
                if(!ressourcePrice.isEmpty()){
                    
                    //Testen, ob genug Ressourcen im Lager sind
                    boolean areEnoughRessources = true;
                    for (int i = 0; i < ressourcePrice.size(); i++) {
                        if(Player.lager.getAnzahlRessourcen(ressourcePrice.get(i)) < price.get(i))
                            areEnoughRessources = false;
                    }
                    
                    //Nur wenn genug Ressourcen vorhanden sind, wird das Produkt hergestellt
                    if(areEnoughRessources){
                        //Ressourcen bezahlen
                        for (int i = 0; i < ressourcePrice.size(); i++) {
                            Player.lager.addRessourcen(ressourcePrice.get(i), -price.get(i));
                        }

                        //Produkt herstellen
                        Player.lager.addRessourcen(ressource, 1);
                    }
                    
                    //Moral beeinflusst die Arbeitsgeschwindigkeit
                    timer = 0 - (time * (1- NPCManager.getMoral()));
                }
                else{
                    timer = 0 - (time * (1- NPCManager.getMoral()));
                    Player.lager.addRessourcen(ressource, 1);
                }
            }
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
