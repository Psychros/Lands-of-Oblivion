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
public class ChangeJumpForceCheat implements Cheat{
    private final String identifier = "Change jump force";
    
    public ChangeJumpForceCheat(){
        //doNothing();
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
            } catch (Throwable t){
                System.out.println("Execution failed");
                return false;
            }
            game.mapState.getPlayer().setJumpForce(new Vector3f(0, (float) tempDbl, 0));
            System.out.println("New jump force: " + tempDbl);
            return true;
        } else {
            return false;
        }
    }
    
}
