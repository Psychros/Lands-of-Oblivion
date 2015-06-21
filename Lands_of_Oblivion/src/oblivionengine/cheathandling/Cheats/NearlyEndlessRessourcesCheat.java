/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling.Cheats;

import oblivionengine.Game;
import oblivionengine.buildings.Ressourcen;
import oblivionengine.charakter.player.Player;
import oblivionengine.cheathandling.Cheat;

/**
 *
 * @author Tobi
 */
public class NearlyEndlessRessourcesCheat extends Cheat{
    
    public NearlyEndlessRessourcesCheat(){
        super.paramNumber = 0;
        super.identifier = "Nearly endless Ressources";
    }

    @Override
    protected void executeCheat(Game game, double[] params) throws Throwable {//TODO
        Player.lager.setGröße(Integer.MAX_VALUE);
        for (Ressourcen res : Ressourcen.values()){
            Player.lager.addRessourcen(res.ordinal(), Integer.MAX_VALUE);
        }
        System.out.println("Every ressources amount: " + Integer.MAX_VALUE);
    }
    
}
