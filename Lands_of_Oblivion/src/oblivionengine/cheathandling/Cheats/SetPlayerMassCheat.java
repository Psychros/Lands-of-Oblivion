/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling.Cheats;

import com.jme3.bullet.control.RigidBodyControl;
import oblivionengine.Game;
import oblivionengine.cheathandling.Cheat;

/**
 *
 * @author Tobi
 */
public class SetPlayerMassCheat extends Cheat{
    
    public SetPlayerMassCheat(){
        super.paramNumber = 1;
        super.identifier = "Set player mass";
    }
    
    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable{ //TODO
        game.mapState.getPlayer().setMass((float) params[0]);
        System.out.println("New player mass: " + params[0]);
    }
}
