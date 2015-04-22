/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import oblivionengine.Game;
import oblivionengine.buildings.Building;
import oblivionengine.buildings.BuildingHaus;

/**
 *
 * @author To
 */
public class NPCControl extends AbstractControl{
    //Objektvariablen
    private RigidBodyControl rigidBody;
    
    //Laufrichtung & Geschwindigkeit
    private int moveSpeed = 15;
    private Vector2f walkDirection = new Vector2f(0, 0);
    private float timeChangeDirection = 5;
    private float timer = 0;
    
    private BuildingHaus home = null;   //Zuhause des NPCs
    private Building workPlace = null;
    
    //Animation
    private AnimControl animControl;
    private AnimChannel animChannel;
    private static final String ANIM_WALK = "my_animation";
    private static final String ANIM_IDLE = "";
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public NPCControl(BuildingHaus home) { 
        this.home = home;
        
        NPCManager.getFreeNPCs().add(this);
        NPCManager.numberNPCs++;
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public Spatial getSpatial(){
        return spatial;
    }
    
    public void setLocalTranslation(Vector3f pos){
        spatial.setLocalTranslation(pos);
    }

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    public void generateModell(){
        Node node = (Node)(Game.game.getAssetManager().loadModel("Models/Player.j3o"));
        node.setLocalTranslation(home.getLocalTranslation().add(0, 0, 7));
        node.getLocalTranslation().setY(Game.game.mapState.getMap().getTerrain().getHeight(new Vector2f(node.getLocalTranslation().x, node.getLocalTranslation().z)));
        node.scale(2.6f);
        Game.game.mapState.getMap().attachChild(node);
        node.addControl(this);
                
        //Animation vorbereiten
        animControl = spatial.getControl(AnimControl.class);
        animChannel = animControl.createChannel();
        
        //Schatten
        node.setShadowMode(ShadowMode.CastAndReceive);  
    }

    @Override
    protected void controlUpdate(float tpf) {
        //Nur bewegen, wenn eine Bewegungsrichtung defniert wurde
        if(!walkDirection.equals(Vector2f.ZERO)){
            //Spatial bewegen
            Vector3f walkDirection = new Vector3f(this.walkDirection.x, 0, this.walkDirection.y);
            spatial.move(walkDirection.normalize().mult(tpf));
            spatial.getLocalTranslation().setY(Game.game.mapState.getMap().getTerrain().getHeight(new Vector2f(spatial.getLocalTranslation().x, spatial.getLocalTranslation().z)));
        }
        
        //Bewegungsrichtung in einem festen Intervall zufällig ändern
        timer += tpf;
        if(timer >= timeChangeDirection){
            timer = 0;
            
            float x = (float)Math.random();
            float y = (float)Math.random();
            //Werte per Zufall negieren
            if((int)(Math.random()*2) == 1)
                x *= -1;
            if((int)(Math.random()*2) == 1)
                y *= -1;
            
            walkDirection.setX(x);
            walkDirection.setY(y);
            
            //Spatial rotieren
            rotateSpatialToWalkDirection(this.walkDirection);
            
            
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
    
    //Rotiert das Spatial so, dass es immer nach vorne läuft
    public void rotateSpatialToWalkDirection(Vector2f walkDirection){
        Vector2f pos = new Vector2f(spatial.getLocalTranslation().x, spatial.getLocalTranslation().z);
        Vector2f us = new Vector2f(0, 1);           //Startrichtung
        Vector2f ue   = walkDirection.normalize();  //Endrichtung
        
        //Winkel bestimmen auf den das Spatial gedreht werden soll
        double cosAlpha = (us.x*ue.x+us.y*ue.y)/(us.length()*ue.length());
        double alpha = Math.toRadians(cosAlpha);
        float phi = (float)Math.cos(alpha);
        
        //Spatial rotieren
        float[] angles = {0, -phi, 0};
        Quaternion quat = new Quaternion(angles);
        spatial.setLocalRotation(quat);
    }
}
