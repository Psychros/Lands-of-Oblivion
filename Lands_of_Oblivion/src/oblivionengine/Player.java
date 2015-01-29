/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine;

import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 *
 * @author To
 */
public class Player extends BetterCharacterControl{
    
    //Objektvariablen
    private Node playerNode;
    private Geometry player;
    private Vector3f newWalkDirection = new Vector3f(0, 0, 0);
    private float speed = 8; 
    
    private AnimControl animControl;

    //--------------------------------------------------------------------------
    //Konstruktoren
    
    public Player(float radius, float height, float mass){
        super(radius, height, mass);      
        playerNode = new Node("Player");
        setJumpForce(new Vector3f(0, 2000, 0));
        setSpeed(10);
        
        //Modell laden
        Node node = (Node)(Game.game.getAssetManager().loadModel("Models/Player.j3o"));
        player = (Geometry)node.getChild(0);
        player.setLocalTranslation(0, 4, 0);
        player.scale(1.8f);        
        player.setMaterial(Game.game.getAssetManager().loadMaterial("Materials/Player.j3m"));
        playerNode.attachChild(player);
        
        animControl = player.getControl(AnimControl.class);
        
        //BetterCharakterControl hinzufügen
        playerNode.addControl(this);
    }
    
    public Player(float radius, float height, float mass, Geometry playerGeom){
        this(radius, height, mass);      
        this.playerNode.attachChild(playerGeom);
    }

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden

    //--------------------------------------------------------------------------
    //Getter und Setter
    
    public float getRadius() {
        return radius;
    }

    public float getHeight() {
        return height;
    }

    public float getMass() {
        return mass;
    }

    public boolean isJump() {
        return jump;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }   

    public Node getPlayerNode() {
        return playerNode;
    }
    
    
    @Override
    public void update(float tpf){
        super.update(tpf);
        
        //Blivkrichtung/RichtungVorne festlegen
        setViewDirection(Game.game.getCam().getDirection());
    }
}
