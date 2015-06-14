/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling.Cheats;

import com.jme3.math.Vector3f;
import oblivionengine.Game;
import oblivionengine.cheathandling.Cheat;

/**
 *
 * @author Tobi
 */
public class SetGravityCheat extends Cheat{
    private static final String identifier = "Set gravity";
    
    public SetGravityCheat(){
        //doNothiing();
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
            game.mapState.getPlayer().setGravity(new Vector3f(0, -(float) params[0], 0));
            System.out.println("New gravity force: " + params[0]);
            returned = true;
        } else {
            returned = false;
        }
        return returned;
    }
}
