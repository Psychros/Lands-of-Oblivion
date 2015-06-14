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
    private static final String identifier = "Set player mass";
    
    public SetPlayerMassCheat(){
        //doNothing();
    }
    
    @Override
    public String getIdentification() {
        return identifier;
    }
    
    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable{ //TODO Fehler finden
        game.mapState.getPlayerNode().getControl(RigidBodyControl.class).setMass((float) params[0]);
        System.out.println("New player mass: " + params[0]);
    }
}
