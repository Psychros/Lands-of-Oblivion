package oblivionengine.cheats;

import java.util.ArrayList;
import oblivionengine.Game;

/**
 *
 * @author Tobi
 */
public class Cheatmanager implements Cheat {
    ArrayList<Cheat> cheats = new ArrayList<Cheat>();
    private byte cheatcounter;
    
    public Cheatmanager(){
        this.cheatcounter = 0;
        loadCheats();
    }

    
    public String getIdentification(){
        return "This is Cheatmanager";
    }

    @Override
    public boolean doCheat(Game game, String cheatText) {
        for (Cheat curCheat : cheats){
            if (curCheat.doCheat(game, cheatText)) return false;
        }
        return false;
    }
    
    private void loadCheats(){
        
    }
}
