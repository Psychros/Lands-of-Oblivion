/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.maps.missions;

import oblivionengine.Game;

/**
 *
 * @author To
 */
public class Mission {
    //Objektvariablen

    //--------------------------------------------------------------------------
    //Konstruktoren
    public Mission(){
        
    }

    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    //Mission anzeigen
    public void showMissionDetails(){
        Game.game.mapState.pauseGame();
    }
}
