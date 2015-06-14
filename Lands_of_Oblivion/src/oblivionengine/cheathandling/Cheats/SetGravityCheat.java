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
    //private static final String identifier = "Set gravity";
    
    public SetGravityCheat(){
        this.paramNumber = 1;
        this.identifier = "Set gravity";
    }

    @Override
    public String getIdentification() {
        return identifier;
    }

    
    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable{
        game.mapState.getPlayer().setGravity(new Vector3f(0, -(float) params[0], 0));
        System.out.println("New gravity force: " + params[0]);
    }
}
