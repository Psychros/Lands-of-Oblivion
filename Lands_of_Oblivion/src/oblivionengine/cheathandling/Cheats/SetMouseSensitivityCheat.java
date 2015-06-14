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
    private static final String identifier = "Set mouse sensitivity";
    
    public SetMouseSensitivityCheat(){
        //doNothing()
    }

    @Override
    public String getIdentification() {
        return this.identifier;
    }
    
    @Override
    public boolean doCheat(Game game, String cheatText) {
        double[] params = super.checkCheat(identifier, cheatText, 1);
        boolean returned;
        if (params != null){
            Player.setMouseSensitivity((float) params[0]);
            System.out.println("New mouse sensitivity: " + params[0]);
            returned = true;
        } else {
            returned = false;
        }
        return returned;
    }
}
