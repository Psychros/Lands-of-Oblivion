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
    private static final String identifier = "Set jump force";
    
    public SetJumpForceCheat(){
        //doNothing();
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
            game.mapState.getPlayer().setJumpForce(new Vector3f(0, (float) params[0], 0));
            System.out.println("New jumpf force: " + params[0]);
            returned = true;
        } else {
            returned = false;
        }
        return returned;
    }
}
