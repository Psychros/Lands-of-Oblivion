/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.buildings.baumaterial;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import oblivionengine.Game;
import oblivionengine.buildings.WorkBuilding;
import static oblivionengine.buildings.Building.testRessources;
import oblivionengine.charakter.npc.Job;
import oblivionengine.charakter.npc.JobHolzfäller;
import oblivionengine.charakter.npc.NPCManager;

/**
 *
 * @author To
 */
public class BuildingHolzfäller extends WorkBuilding{
    //Objektvariablen
    private Node[] trees = new Node[8];

    //--------------------------------------------------------------------------
    //Konstruktoren
    public BuildingHolzfäller() {
        super();
         if(testRessources(PRICE_HOLZFÄLLER)){  //Das Gebäude kann nur gebaut werden, wenn genug Ressourcen zur Verfügung stehen
            setSize(SIZE_HOLZFÄLLER);
            setPRICE(PRICE_HOLZFÄLLER);

            Geometry haus = (Geometry)Game.game.getAssetManager().loadModel("Models/Buildings/Holzfäller.j3o");      
            attachChild(haus);
            scale(3.5f);
         }
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    public Node[] getTrees() {
        return trees;
    }
   
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    public void finish() {
        super.finish(); 
        
        //8 Bäume um den Holzfäller herum generieren
        for (int i = 0; i < 8; i++) {
            Node tree = (Node)Game.game.getAssetManager().loadModel("Models/Landschaft/Baum.j3o");
            tree.setName("Tree");
            tree.scale((float)Math.random()+1);
            int rotation = (int)(Math.random()*360);
            tree.rotate(0, rotation* FastMath.DEG_TO_RAD, 0);
            Game.game.mapState.getMap().attachChild(tree);
            trees[i] = tree;

            //Schatten einstellen
            tree.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        }
        
        //Position der 8 Bäume festlegen
        Vector2f v = new Vector2f(getLocalTranslation().x+15, getLocalTranslation().z+15);
        trees[0].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        v = new Vector2f(getLocalTranslation().x+15, getLocalTranslation().z);
        trees[1].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        v = new Vector2f(getLocalTranslation().x+15, getLocalTranslation().z-15);
        trees[2].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        v = new Vector2f(getLocalTranslation().x, getLocalTranslation().z-15);
        trees[3].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        v = new Vector2f(getLocalTranslation().x-15, getLocalTranslation().z-15);
        trees[4].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        v = new Vector2f(getLocalTranslation().x-15, getLocalTranslation().z);
        trees[5].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        v = new Vector2f(getLocalTranslation().x-15, getLocalTranslation().z+15);
        trees[6].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        v = new Vector2f(getLocalTranslation().x, getLocalTranslation().z+15);
        trees[7].setLocalTranslation(v.x, Game.game.mapState.getMap().getTerrain().getHeight(v), v.y);
        
        //Den 8 Bäumen ein zylinderförmiges kollisionsmodell hinzufügen
        for (int i = 0; i < 8; i++) {
            RigidBodyControl control = new RigidBodyControl(new CapsuleCollisionShape(1.6f, 30), 0);
            control.setFriction(4);
            control.setGravity(new Vector3f(0, -98.1f, 0));
            trees[i].addControl(control);
            Game.game.mapState.getMap().getBulletAppState().getPhysicsSpace().add(control);
        }
        
        NPCManager.addZiviisationsPunkte(1);
    }   

    //Dem Worker eine Tätigkeit geben
    @Override
    public Job getJob() {
        JobHolzfäller job = new JobHolzfäller();
        job.setBuilding(this);
        return job;
    }

    @Override
    public void demolish() {
        super.demolish(); //To change body of generated methods, choose Tools | Templates.
        
        //Bäume können wieder gefällt werden
        for (Node tree : trees) {
            Game.game.mapState.getMap().getTrees().attachChild(tree);
        }
    }
    
    
}
