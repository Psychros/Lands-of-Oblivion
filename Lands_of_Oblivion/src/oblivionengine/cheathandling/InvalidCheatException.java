/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.cheathandling;

/**
 *
 * @author Tobi
 */
class InvalidCheatException extends Exception {
    
    public InvalidCheatException(){
        super("The cheat you are trying to trigger is invalid");
    }
    
}
