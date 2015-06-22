/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling.Cheats;

import oblivionengine.Game;
import oblivionengine.charakter.npc.NPCManager;
import oblivionengine.cheathandling.Cheat;

/**
 *
 * @author Tobi
 */
public class ForbidMoralDecrementCheat extends Cheat{
    
    public ForbidMoralDecrementCheat(){
        super.identifier = "Forbid moral decrement";
        super.paramNumber = 0;
    }

    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable {
        NPCManager.switchMoralDecrementForbidden();
        System.out.println("Moral decrement forbidden switched from " + !NPCManager.getMoralDecrementForbidden() +
                " to " + NPCManager.getMoralDecrementForbidden());
        System.out.println("Using this cheat again will switch it again");
    }
    
    
}
