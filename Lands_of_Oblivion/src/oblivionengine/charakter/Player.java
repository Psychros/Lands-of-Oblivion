/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter;

import com.jme3.bullet.control.RigidBodyControl;
import oblivionengine.buildings.GlobalesLager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainPatch;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import oblivionengine.Game;
import oblivionengine.TreeControl;
import oblivionengine.buildings.Building;
import static oblivionengine.buildings.Building.SIZE_LAGER;
import oblivionengine.buildings.BuildingLager;
import oblivionengine.buildings.Ressourcen;

/**
 *
 * @author To
 */
public class Player extends CharakterControl{
    
    //Globales GlobalesLager
    public static GlobalesLager lager = new GlobalesLager();
    
    //Aktuell ausgewähltest Gebäude
    public static String selectedBuilding;

    //--------------------------------------------------------------------------
    //Konstruktoren
    
    public Player(float radius, float height, float mass) {
        super(radius, height, mass);
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    public Spatial getSpatial() {
        return spatial;
    }
    

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        super.onAction(name, isPressed, tpf);
        
        //Baum fällen
        if(name.equals("CutTree")){
            cutTree();
        } else if(name.equals("Build"))
            build();
    }
    
    //Baum fällen
    public void cutTree(){
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(Game.game.getCam().getLocation(), Game.game.getCam().getDirection());
        Game.game.mapState.getMap().getTrees().collideWith(ray, results);
        
        if(results.size() != 0){
            Geometry tree = results.getClosestCollision().getGeometry();
            if(tree != null && tree.getParent().getControl(RigidBodyControl.class).getMass()==0 && tree.getParent().getName().equals("Tree") && results.getClosestCollision().getDistance() < 10){          
                
                //Den Baum umfallen lassen
                TreeControl treeControl = new TreeControl(tree);
                tree.addControl(treeControl);
                treeControl.fallDown();
                
                
                //Das GlobalesLager mit Holz füllen
                lager.addRessourcen(Ressourcen.Wood, 1);
                
                //Text ändern
                Game.game.screens.setText("Baumstämme", lager.getAnzahlRessourcen(Ressourcen.Wood));
            }
        }
    }
    
    /*
     * Gebäude bauen
     */
    public void build(){
        if(selectedBuilding != null){
            
            //Position des Gebäudes per MousePicking feststellen
            CollisionResults results = new CollisionResults();
            Vector2f click2d = Game.game.getInputManager().getCursorPosition();
            Vector3f click3d = Game.game.getCam().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
            Vector3f dir = Game.game.getCam().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalize();
            Ray ray = new Ray(click3d, dir);
            Game.game.mapState.getMap().collideWith(ray, results);
            
            if(results.size() != 0 && results.getClosestCollision().getGeometry() instanceof TerrainPatch){
                Building b = null;
                
                //ID des Gebäudes überprüfen
                switch(selectedBuilding){
                    case "Lager": b = new BuildingLager();
                }
                
                //Position des Gebäudes setzen
                if(b != null){
                    b.setLocalTranslation(new Vector2f((int)results.getClosestCollision().getContactPoint().x, (int)results.getClosestCollision().getContactPoint().z));
                    b.plainGround(SIZE_LAGER);
                    System.out.println("Hallo");
                }
            }
        }
    }
}
