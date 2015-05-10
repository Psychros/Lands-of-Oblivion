/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import oblivionengine.Game;
import oblivionengine.TreeCutControl;
import oblivionengine.buildings.workbuildings.BuildingHolzfäller;

/**
 *
 * @author To
 */
public class JobHolzfäller extends Job{
    //Objektvariablen
    private BuildingHolzfäller building;
    private int numberTree = 0;     //Nummer des zu fällenden Baumes
    private int task = 0;
    private Vector3f walkDirection = Vector3f.ZERO;
    
    //Position des Baumes zwischenspeichern
    private Vector3f pos;
    
    //Timer, der darauf wartet, dass der Baum umgefallen ist
    private float timerTime = 4;
    private float timer = 0;
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public JobHolzfäller(){
    }

    public JobHolzfäller(WorkerControl worker) {
        super(worker);
    }
    

    //--------------------------------------------------------------------------
    //Getter und Setter
    public BuildingHolzfäller getBuilding(){
        return building;
    }

    public void setBuilding(BuildingHolzfäller building) {
        this.building = building;
    }
    

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    @Override
    public void update(float tpf){
        super.update(tpf);
        
        //Den Arbeiter zu einem beliebigen Baum laufen lassen
        if(task == 0 ){
            if(walkDirection.equals(Vector3f.ZERO)){
               walkDirection = building.getTrees()[numberTree].getLocalTranslation().subtract(worker.getSpatial().getLocalTranslation());
               worker.setWalkDirection(new Vector2f(walkDirection.x, walkDirection.z));
            }
            //Arbeiter am Baum stehen lassen
            if(worker.getSpatial().getLocalTranslation().distance(building.getTrees()[numberTree].getLocalTranslation()) < 2){
                walkDirection = Vector3f.ZERO;
                worker.setWalkDirection(Vector2f.ZERO);
                task++;
            }
        }
        
        //Baum fällen
        else if(task == 1){
            pos = new Vector3f(building.getTrees()[numberTree].getLocalTranslation());
            TreeCutControl control = new TreeCutControl((Geometry)building.getTrees()[numberTree].getChild(0));
            building.getTrees()[numberTree].addControl(control);
            control.fallDown();
            
            task++;
        }
        
        //Warten, bis der Baum umgefallen
        else if(task == 2){
            timer += tpf;
            
            if(timer >= timerTime){
                timer = 0;
                createNewTree();
                //Nächsten baum auswählen
                numberTree++;
                if(numberTree == 8)
                    numberTree = 0;
                
                task++;
            }
        }
        
        //Zurück zum Holzfällerhaus laufen
        else if(task == 3){
            if(walkDirection.equals(Vector3f.ZERO)){
               walkDirection = building.getLocalTranslation().subtract(worker.getSpatial().getLocalTranslation());
               worker.setWalkDirection(new Vector2f(walkDirection.x, walkDirection.z));
            }
            //Arbeiter am Haus stehen lassen
            if(worker.getSpatial().getLocalTranslation().distance(building.getLocalTranslation()) < 2){
                walkDirection = Vector3f.ZERO;
                worker.setWalkDirection(Vector2f.ZERO);
                task = 0;
            }
        }
    }
    
    //Neuen Baum an die alte Position setzen
    public void createNewTree(){
        Node tree = (Node)Game.game.getAssetManager().loadModel("Models/Landschaft/Baum.j3o");
        tree.setName("Tree");
        tree.setLocalTranslation(pos);
        tree.scale((float)Math.random()+1);
        int rotation = (int)(Math.random()*360);
        tree.rotate(0, rotation* FastMath.DEG_TO_RAD, 0);
        Game.game.mapState.getMap().getTrees().attachChild(tree);
        building.getTrees()[numberTree] = tree;

        //Schatten einstellen
        tree.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        
        //Zylinderförmiges Kollisionsmodell erstellen
        RigidBodyControl control = new RigidBodyControl(new CapsuleCollisionShape(1.6f, 30), 0);
        control.setFriction(4);
        control.setGravity(new Vector3f(0, -98.1f, 0));
        tree.addControl(control);
        Game.game.mapState.getMap().getBulletAppState().getPhysicsSpace().add(control);
    }
}
