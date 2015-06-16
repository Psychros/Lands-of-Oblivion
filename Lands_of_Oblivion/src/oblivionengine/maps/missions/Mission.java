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
    private String[] missinText = {"Ich: Was ist passiert?",
                                   "     Was mache ich hier?",
                                   "     Ich erinnere mich nur noch an einen Sturm.",
                                   ""};

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
