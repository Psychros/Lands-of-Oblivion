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
public class SetJumpForceCheat extends Cheat{
    
    public SetJumpForceCheat(){
        super.paramNumber = 1;
        super.identifier = "Set jump force";
    }
    
    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable{
        game.mapState.getPlayer().setJumpForce(new Vector3f(0, (float) params[0], 0));
        System.out.println("New jumpf force: " + params[0]);
    }
}
