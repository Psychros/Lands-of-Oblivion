package oblivionengine.cheats;

import java.util.ArrayList;
import oblivionengine.Game;

/**
 *
 * @author Tobi
 */
public class Cheatmanager implements Cheat {
    ArrayList<Cheat> cheats = new ArrayList<Cheat>();
    
    public Cheatmanager(){
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public boolean doCheat(Game game, String cheat) {
        return true;
    }
}
