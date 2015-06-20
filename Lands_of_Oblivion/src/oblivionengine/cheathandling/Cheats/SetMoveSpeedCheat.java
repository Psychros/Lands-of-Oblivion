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
    
    public SetMoveSpeedCheat(){
        this.identifier = "Set move speed";
        this.paramNumber = 1;
    }

    @Override
    public String getIdentification() {
        return identifier;
    }
    
    @Override
    protected void executeCheat(Game game, double params[]) throws Throwable{
        game.mapState.getPlayer().setMoveSpeed(((float) params[0]));
        System.out.println("New move speed: " + params[0]);
    }
}
