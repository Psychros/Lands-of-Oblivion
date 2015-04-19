/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

/**
 *
 * @author To
 */
public abstract class BuildingHaus extends Building{
    //Objektvariablen
    int numberpeople;   //Zahl der Einwohner

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingHaus() {
        
    }
   

    //--------------------------------------------------------------------------
    //Getter und Setter
    public int getNumberpeople() {
        return numberpeople;
    }

    public void setNumberpeople(int numberpeople) {
        this.numberpeople = numberpeople;
    }
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
}
