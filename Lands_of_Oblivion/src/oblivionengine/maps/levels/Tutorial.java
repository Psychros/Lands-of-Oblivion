/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.maps.levels;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainQuad;
import oblivionengine.Game;
import oblivionengine.maps.Map;
import oblivionengine.maps.MapState;
import oblivionengine.maps.missions.MissionState;

/**
 *
 * @author To
 */
public class Tutorial extends MapState{
    //Objektvariablen

    //--------------------------------------------------------------------------
    //Konstruktoren
    public Tutorial() {
        super("Tutorial");
        
        
    }

    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    public void initialize(AppStateManager stateManager, Application app){ 
        super.initialize(stateManager, app);
        
        //Kurze Vorgeschichte zeigen
        pauseGame();
        MissionState mission = new MissionState(Game.PATH + "Missions\\Tutorial.txt");
    }
}
