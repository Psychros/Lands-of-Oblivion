/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings;

import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import javax.vecmath.Vector2d;
import oblivionengine.Game;

/**
 *
 * @author To
 */
public class Building {
    //Objektvariablen
    protected Node building;  //Alles sichtbare, was zum Building gehört
    
    public static enum IDs{
        Lager
    }

    //--------------------------------------------------------------------------
    //Konstruktoren
    //--------------------------------------------------------------------------
    //Konstruktoren
    public Building(Vector2f pos) {
        building = new Node("Building");
        Game.game.mapState.getMap().attachChild(building);
        building.setLocalTranslation(pos.x, Game.game.mapState.getMap().getTerrain().getHeight(pos), pos.y);
    }

    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
}
