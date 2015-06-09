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
public class ChangeMouseSensitivityCheat implements Cheat{
    private final String identifier = "Change mouse sensitivity";
    
    public ChangeMouseSensitivityCheat(){
        //doNothing()
    }

    @Override
    public String getIdentification() {
        return this.identifier;
    }
    
    @Override
    public boolean doCheat(Game game, String cheatText) {
        String tempStr;
        double tempDbl;
        if (util.StringUtil.startsWithIgnoreCase(cheatText, identifier)){
            System.out.println("Cheat " + identifier + " is to be executed");
            try{
                tempStr = cheatText.substring(identifier.length() + 1);
                tempDbl = Double.parseDouble(tempStr);
            } catch (Throwable e){
                System.out.println("Execution failed");
                return false;
            }
            Player.setMouseSensitivity((float) tempDbl);
            System.out.println("New Mouse sensitivity: " + tempDbl);
            return true;
        } else {
            return false;
        }
    }
    
}
