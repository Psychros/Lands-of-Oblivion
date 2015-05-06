/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.bedürfnisse;

import oblivionengine.buildings.Ressourcen;

/**
 *
 * @author To
 */
public class Bedürfnis {
    //Objektvariablen
    private Ressourcen ressource;
    private int time;   //Zeit in Sekunden, nach der die Ressource benötigt wird
    
    //Vorgefertigte Bedürfnisse
    public static final Bedürfnis FOOD = new Bedürfnis(Ressourcen.Food, 60);
    public static final Bedürfnis BELIEf = new Bedürfnis(Ressourcen.Belief, 60);

    
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public Bedürfnis(Ressourcen ressource, int time) {
        this.ressource = ressource;
        this.time = time;
    }

    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public Ressourcen getRessource() {
        return ressource;
    }

    public void setRessource(Ressourcen ressource) {
        this.ressource = ressource;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
}
