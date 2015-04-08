/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter;

import com.jme3.bullet.control.RigidBodyControl;
import oblivionengine.buildings.GlobalesLager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import oblivionengine.Game;
import oblivionengine.TreeControl;
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
        }
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
                Element e = Game.game.screens.getNifty().getCurrentScreen().findElementByName("Baumstämme");
                TextRenderer label = e.getRenderer(TextRenderer.class);
                label.setText(String.valueOf(lager.getAnzahlRessourcen(Ressourcen.Wood)));
            }
        }
    }
}
