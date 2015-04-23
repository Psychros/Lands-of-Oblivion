/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
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
    private Node node;
    
    //Laufrichtung & Geschwindigkeit
    private int moveSpeed = 15;
    protected Vector2f walkDirection = new Vector2f(0, 0);
    private float timeChangeDirection = 1;
    private float timer = 0;
    
    private BuildingHaus home = null;   //Zuhause des NPCs
    
    //Animation
    private AnimControl animControl;
    private AnimChannel animChannel;
    private static final String ANIM_WALK = "my_animation";
    private static final String ANIM_IDLE = "";
    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public NPCControl(BuildingHaus home) { 
        this.home = home;
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

    public BuildingHaus getHome() {
        return home;
    }

    public void setHome(BuildingHaus home) {
        this.home = home;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    public void generateModell(){
        node = (Node)(Game.game.getAssetManager().loadModel("Models/Player.j3o"));
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
            timeChangeDirection = (float)(Math.random()*8);
            
            
            //Stehen bleiben oder in zufällige Richtung laufen
            int i = (int)(Math.random()*2);
            if(i == 0){
                changeWalkDirection();
                
            } else if(i == 1){
                this.walkDirection.set(0, 0);
            }
            
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
    }
    
    
    public void changeWalkDirection(){
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
    
    
    //Rotiert das Spatial so, dass es immer nach vorne läuft
    public void rotateSpatialToWalkDirection(Vector2f walkDirection){
        Vector2f pos = new Vector2f(spatial.getLocalTranslation().x, spatial.getLocalTranslation().z);
        Vector2f us = new Vector2f(0, 1);           //Startrichtung
        Vector2f ue   = walkDirection.normalize();  //Endrichtung
        
        //Winkel bestimmen auf den das Spatial gedreht werden soll
        double cosAlpha = (us.x*ue.x+us.y*ue.y)/(us.length()*ue.length());
        float alpha = (float)(Math.cos(cosAlpha) * FastMath.RAD_TO_DEG);
        
        if(ue.y < 0)
            alpha *= -1;
        
        
        
        //Spatial rotieren
        float[] angles = {0, alpha, 0};
        Quaternion quat = new Quaternion(angles);
        spatial.setLocalRotation(quat);
    }
}
