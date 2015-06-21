/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling.Cheats;

import oblivionengine.Game;
import oblivionengine.charakter.player.Player;
import oblivionengine.cheathandling.Cheat;

/**
 *
 * @author Tobi
 */
public class SetMouseSensitivityCheat extends Cheat{
    
    public SetMouseSensitivityCheat(){
        super.paramNumber = 1;
        super.identifier = "Set mouse sensitivity";
    }
    
    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable{
        Player.setMouseSensitivity((float) params[0]);
        System.out.println("New mouse sensitivity: " + params[0]);
    }
}
