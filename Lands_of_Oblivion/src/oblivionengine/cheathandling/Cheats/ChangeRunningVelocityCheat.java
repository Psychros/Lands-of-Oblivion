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
        if (util.StringUtil.startsWithIgnoreCase(cheatText, identifier)){
            System.out.println("Cheat is to be executed");
            try{
                tempStr = cheatText.substring(identifier.length() + 1);
                tempDbl = Double.parseDouble(tempStr);
            } catch (Exception e){
                return false;
            }
            game.mapState.getPlayer().setMoveSpeed(((float) tempDbl));
            System.out.println(game.mapState.getPlayer().getMoveSpeed());
            return true;
        } else {
            return false;
        }
    }
}
