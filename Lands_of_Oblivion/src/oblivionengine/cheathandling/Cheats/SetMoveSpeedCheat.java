/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling.Cheats;

import oblivionengine.Game;
import oblivionengine.cheathandling.Cheat;

/**
 *
 * @author Tobi
 */
public class SetMoveSpeedCheat extends Cheat{
    private static final String identifier = "Set move speed";
    
    public SetMoveSpeedCheat(){
        //doNothing()
    }

    @Override
    public String getIdentification() {
        return identifier;
    }

    @Override
    public boolean doCheat(Game game, String cheatText) {
        double[] params = super.checkCheat(identifier, cheatText, 1);
        boolean returned;
        if (params != null){
            game.mapState.getPlayer().setMoveSpeed(((float) params[0]));
            System.out.println("New move speed: " + params[0]);
            returned = true;
        } else {
            returned = false;
        }
        return returned;
    }
}
