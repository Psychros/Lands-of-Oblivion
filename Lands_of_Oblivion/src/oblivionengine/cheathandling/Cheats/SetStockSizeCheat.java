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
public class SetStockSizeCheat extends Cheat{
    
    public SetStockSizeCheat(){
        super.identifier = "Set stock size";
        super.paramNumber = 1;
    }

    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable {
        int newSize = Integer.MAX_VALUE;
        if (params[0] < Integer.MAX_VALUE) newSize = (int) params[0];
        else System.out.println("Stock size can't be bigger than " + Integer.MAX_VALUE);
        
        Player.lager.setGröße(newSize);
        
        System.out.println("New stock size: " + newSize);
    }
    
}
