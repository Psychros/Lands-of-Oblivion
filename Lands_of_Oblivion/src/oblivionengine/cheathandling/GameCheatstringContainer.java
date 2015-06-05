/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling;

import oblivionengine.Game;

/**
 *
 * @author Tobi
 */
public class GameCheatstringContainer {
    private Game game;
    private String cheatString;
    
    public GameCheatstringContainer(Game game, String cheatString){
        this.game = game;
        this.cheatString = cheatString;
    }
    
    public Game getGame(){
        return this.game;
    }
    
    public String getCheatString(){
        return this.cheatString;
    }
}
