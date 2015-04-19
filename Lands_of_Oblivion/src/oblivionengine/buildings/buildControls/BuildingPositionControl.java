/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.buildings.buildControls;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.terrain.geomipmap.TerrainPatch;
import javax.vecmath.Vector2d;
import oblivionengine.Game;

/**
 * Dieser Control aktuallisiert die Position eines zu bauenden Gebäudes, das noch nicht gesetzt wurde auf
 * die Cursorposition
 *
 * @author To
 */
public class BuildingPositionControl extends AbstractControl {

    @Override
    protected void controlUpdate(float tpf) {
        
            //Position der neuen Position des Buldings feststellen
            CollisionResults results = new CollisionResults();
            Vector2f click2d = new Vector2f(960, 540);
            Vector3f click3d = Game.game.getCam().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
            Vector3f dir = Game.game.getCam().getDirection();
            Ray ray = new Ray(click3d, dir);
            Game.game.mapState.getMap().collideWith(ray, results);
            
            //Neue Position setzen
            if(results.size() != 0 && results.getClosestCollision().getGeometry() instanceof TerrainPatch 
                    && results.getClosestCollision().getDistance() > 10){
                Vector2f pos = new Vector2f((int)results.getClosestCollision().getContactPoint().x, (int)results.getClosestCollision().getContactPoint().z);
                spatial.setLocalTranslation((int)pos.x, Game.game.mapState.getMap().getTerrain().getHeight(pos), (int)pos.y);
            }        
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
