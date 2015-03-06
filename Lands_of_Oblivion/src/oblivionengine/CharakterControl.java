/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;

/**
 *
 * @author To
 */
public class CharakterControl extends BetterCharacterControl implements ActionListener, AnalogListener{

    //Objektvariablen
    private boolean forward, backward, leftRotate, rightRotate, leftStrafe, rightStrafe;
    private float moveSpeed = 10;;
    private Node head = new Node();
    private float yaw;
    
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    
    public CharakterControl(float radius, float height, float mass) {
        super(radius, height, mass);
        
        head.setLocalTranslation(0, 1.8f, 0);
    }
    

    //--------------------------------------------------------------------------
    //Getter und Setter

    public void setSpatial(Spatial spatial){
        super.setSpatial(spatial);
        //Die Position des Kopfes nur dann festlegen, wenn das Spatial eine Node ist
        if(spatial instanceof Node)
            ((Node)spatial).attachChild(head);
    }
    
    public void setCamera(Camera cam){
        CameraNode camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        head.attachChild(camNode);
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden 
    
    @Override
    public void update(float tpf) {
        super.update(tpf); 
        
        //Bestimmen, wo vorne und wo links vom NPC aus gesehen sind
        Vector3f modelForwardDir = spatial.getWorldRotation().mult(Vector3f.UNIT_Z);
        Vector3f modelLeftDir = spatial.getWorldRotation().mult(Vector3f.UNIT_X);
        walkDirection.set(0, 0, 0);
        
        //NPC bewegen
        if(forward)
            walkDirection.addLocal(modelForwardDir.mult(moveSpeed));
        else if(backward)
            walkDirection.addLocal(modelForwardDir.negate().multLocal(moveSpeed));
        
        if(leftStrafe)
            walkDirection.addLocal(modelLeftDir.mult(moveSpeed));
        else if(rightStrafe)
            walkDirection.addLocal(modelLeftDir.negate().multLocal(moveSpeed));
        
        setWalkDirection(walkDirection);
    }
    
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        //Testen, welche Tastenbefehle ausgeführt werden sollen
        if(name.equals("StrafeLeft"))
            leftStrafe = isPressed;
        else if(name.equals("StrafeRight"))
            rightStrafe = isPressed;
        
        else if(name.equals("MoveForward"))
            forward = isPressed;
        else if(name.equals("MoveBackward"))
            backward = isPressed;
        
        else if(name.equals("Jump"))
            jump();
        else if(name.equals("Duck"))
            setDucked(isPressed);
        
        if(name.equals("Sprinten"))
            Game.game.mapState.getMap().getPlayer().setMoveSpeed(30);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        //Testen, welche Tastenbefehle ausgeführt werden sollen
        if(name.equals("RotateLeft"))
            rotate(tpf * value);
        else if(name.equals("RotateRight"))
            rotate(-tpf * value);
        
        if(name.equals("LookUp"))
            lookUpDown(value * tpf);
        else if(name.equals("LookDownt"))
            lookUpDown(-tpf * value);
    }
    
    public void rotate(float value){
        Quaternion rotate = new Quaternion().fromAngleAxis(FastMath.PI * value, Vector3f.UNIT_Y);
        rotate.multLocal(viewDirection);
        setViewDirection(viewDirection);
    }
    
    public void lookUpDown(float value){
        yaw += value;
        yaw = FastMath.clamp(yaw, -FastMath.HALF_PI, FastMath.HALF_PI);
        head.setLocalRotation(new Quaternion().fromAngles(yaw, 0, 0));
    }
}