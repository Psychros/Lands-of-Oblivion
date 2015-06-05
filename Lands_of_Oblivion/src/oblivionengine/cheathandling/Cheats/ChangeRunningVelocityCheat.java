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
public class ChangeRunningVelocityCheat implements Cheat{
    private static final String identifier = "Change running velocity";
    
    public ChangeRunningVelocityCheat(){
        //doNothing()
    }

    @Override
    public String getIdentification() {
        return identifier;
    }

    @Override
    public boolean doCheat(Game game, String cheatText) {
        String tempStr;
        double tempDbl;
        if (cheatText.startsWith(identifier)){
            tempStr = cheatText.substring(identifier.length() + 1);
            try{
                tempDbl = Double.parseDouble(tempStr);
            } catch (Exception e){
                return false;
            }
            game.mapState.getPlayer().setMoveSpeed(((float) tempDbl));
            return true;
        } else {
            return false;
        }
    }
    
    
}
