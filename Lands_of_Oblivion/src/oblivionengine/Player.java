/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Quaternion;
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
    private Vector3f newWalkDirection = new Vector3f(0, 0, 0);
    private float speed = 8; 

    //--------------------------------------------------------------------------
    //Konstruktoren
    
    public Player(float radius, float height, float mass){
        super(radius, height, mass);      
        playerNode = new Node("Player");
        setJumpForce(new Vector3f(0, 2000, 0));
        setSpeed(10);
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
    
    public void setLocalTranslation(Vector3f location){
        playerNode.move(location.add(0, 3, 0));
        playerNode.addControl(this);
    }
}
