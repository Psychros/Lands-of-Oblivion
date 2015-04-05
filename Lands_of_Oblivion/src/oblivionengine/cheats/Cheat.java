
package oblivionengine.cheats;

import oblivionengine.Game;

/**
 * Please name every Cheat after its task + Cheat so for example HealthCheat
 * @author Tobi
 */
public interface Cheat {
    public boolean doCheat(Game game, String cheat);
}
