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
public class ChangeGravityCheat implements Cheat{
    private final String identifier = "Change gravity";
    
    public ChangeGravityCheat(){
        //doNothiing();
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
            System.out.println("Cheat: " + identifier + " is to be executed");
            try{
                tempStr = cheatText.substring(identifier.length() + 1);
                tempDbl = Double.parseDouble(tempStr);
            } catch (Throwable e){
                System.out.println("Execution failed");
                return false;
            }
            game.mapState.getPlayer().setGravity(new Vector3f(0, -(float) tempDbl, 0));
            System.out.println("New gravity force: " + tempDbl);
            return true;
        } else {
            return false;
        }
    }
    
}
