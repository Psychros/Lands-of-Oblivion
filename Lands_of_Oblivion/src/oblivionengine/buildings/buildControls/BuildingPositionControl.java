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
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.terrain.geomipmap.TerrainPatch;
import oblivionengine.Game;
import oblivionengine.buildings.Building;
import oblivionengine.buildings.waren.BuildingFischer;

/**
 * Dieser Control aktuallisiert die Position eines zu bauenden Gebäudes, das noch nicht gesetzt wurde auf
 * die Cursorposition
 *
 * @author To
 */
public class BuildingPositionControl extends AbstractControl {
    
    protected Vector3f oldPos;
    
    @Override
    protected void controlUpdate(float tpf) {
        //Alte Position des Spatials zwischenspeichern
        oldPos = spatial.getLocalTranslation();
        
        //Position der neuen Position des Buldings feststellen
        CollisionResults results = new CollisionResults();
        Vector2f click2d = new Vector2f(960, 540);
        Vector3f click3d = Game.game.getCam().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
        Vector3f dir = Game.game.getCam().getDirection();
        Ray ray = new Ray(click3d, dir);
        Game.game.mapState.getMap().collideWith(ray, results);

        //Neue Position setzen
        if(results.size() != 0 && results.getClosestCollision().getDistance() > 10){
            //Gebäude darf nicht platziert werden, wenn ein Baum ausgewählt ist
            if (results.getClosestCollision().getGeometry() instanceof TerrainPatch) {
                Vector2f pos = new Vector2f((int)results.getClosestCollision().getContactPoint().x, (int)results.getClosestCollision().getContactPoint().z);

                //Gebäude können nicht im Wasser platziert werden                           
                if(Game.game.mapState.getMap().getTerrain().getHeight(pos) > 6){
                    spatial.setLocalTranslation((int)pos.x, Game.game.mapState.getMap().getTerrain().getHeight(pos), (int)pos.y);
                    Game.game.mapState.getPlayer().canBeBuild = true;
                }
            } 
            else if((results.getClosestCollision().getGeometry().getParent() instanceof Building && results.getClosestCollision().getGeometry().getParent() != spatial)|| results.getClosestCollision().getGeometry().getName().equals("Tree")){
                Game.game.mapState.getPlayer().canBeBuild = false;
            }
        } 
        
        
        //Gebäude wieder auf alte Position setzen, wenn es mit einem anderen kollidiert
        Gebäude:
        if(Game.game.mapState.getMap().getBuildings() != null && Game.game.mapState.getMap().getBuildings().getChildren().size() > 0){
            for (Spatial building : Game.game.mapState.getMap().getBuildings().getChildren()) {
                if(spatial.collideWith(building.getWorldBound(), results) > 0){
                    spatial.setLocalTranslation(oldPos);
                    break Gebäude;
                }
            }
        }
        
        //Der Fischer darf nur am Wasser stehen
        if(spatial instanceof BuildingFischer && spatial.getLocalTranslation().getY() > 15){
            Game.game.mapState.getPlayer().canBeBuild = false;
        }
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        //Only needed for rendering-related operations,
        //not called when spatial is culled.
    }
}
