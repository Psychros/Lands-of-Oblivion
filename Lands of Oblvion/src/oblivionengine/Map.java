/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.util.ArrayList;

/**
 *
 * @author To
 */
public class Map extends Node{
    
    //Objektvariablen
    
    private Geometry underground;
    private float undergroundSizeX, undergroundSizeZ;   //Werden nur mit Werten belegt, wenn man eine ebene Fläche im Kostruktor erzeugt
    private boolean isUndergroundTextureRepetition = false;
    private ArrayList<Building> buildings;
    private ArrayList<Structure> structures;
    private AmbientLight ambientLight;
    private DirectionalLight sunLight;
    private float gravity;
    private Player player;
    private BulletAppState bulletAppState;

    //--------------------------------------------------------------------------
    //Konstruktoren
    
    private Map(String name){
        super(name);
        
        //Physik
        bulletAppState = new BulletAppState();
        activatePhysics(true);
        
        //Player
        player = new Player(1f, 2, 80);
        attachChild(player.getPlayerNode());
        bulletAppState.getPhysicsSpace().add(player);
        
        setAmbientLight(true);
        setSunLight(true);
        setSkyColor(ColorRGBA.Cyan);
        setGravity(-9.81f);
        
        Geometry block = (Geometry)Game.game.getAssetManager().loadModel("Models/block.j3o"); 
        Material eichenstamm = Game.game.getAssetManager().loadMaterial("Materials/Eichenstamm.j3m");
        block.setMaterial(eichenstamm);
        block.setLocalTranslation(0, 0.5f, 0);
        attachChild(block);
        
        Geometry block2 = (Geometry)Game.game.getAssetManager().loadModel("Models/block.j3o"); 
        block2.setMaterial(eichenstamm);
        block2.setLocalTranslation(10, 0.5f, 10);
        attachChild(block2);
    }
    
    public Map(Geometry underground) {
        this(underground, "Map");
    }

    public Map(Geometry underground, String name) {
        this(name);
        this.underground = underground;
        this.attachChild(underground);
        
        RigidBodyControl undergroundPhysic = new RigidBodyControl(0);
        this.underground.addControl(undergroundPhysic);  
        bulletAppState.getPhysicsSpace().add(undergroundPhysic);
    }
    
    //Generiert direkt eine ebene Fläche
    public Map(float posX, float posZ, float sizeX, float sizeZ, String material, String name){
        this(name);
        this.undergroundSizeX = sizeX;
        this.undergroundSizeZ = sizeZ;
        
        //Rechteckigen flachen Boden erstellen
        Quad rect = new Quad(sizeX, sizeZ);
        Geometry rectGeom = new Geometry("Boden", rect);
        rectGeom.setMaterial(Game.game.getAssetManager().loadMaterial(material));
        attachChild(rectGeom);
        this.underground = rectGeom;
        rectGeom.setLocalTranslation(posX-(sizeX/2), 0, posZ+(sizeZ/2));
        rectGeom.rotate(-FastMath.DEG_TO_RAD*90, 0, 0);       
        
        //Physik
        RigidBodyControl undergroundPhysic = new RigidBodyControl(0);
        this.underground.addControl(undergroundPhysic);  
        bulletAppState.getPhysicsSpace().add(undergroundPhysic);
    }
    
    //Generiert eine ebene Fläche am Koordinatenursprung
    public Map(float sizeX, float sizeZ, String material, String name){
        this(0, 0, sizeX, sizeZ, material, name);
    }
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    
    public Geometry getUnderground() {
        return underground;
    }

    public void setUnderground(Geometry underground) {
        this.underground = underground;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void addBuildings(Building... buildings) {
        for (Building building : buildings) {
            this.buildings.add(building);
            this.attachChild(building);
        }
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void addStructures(Structure... structures) {
        for (Structure structure : structures) {
            this.structures.add(structure);
            this.attachChild(structure);
        }
    }

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }
    
    public void setAmbientLight(boolean isAmbientLight){
        if(ambientLight == null && isAmbientLight){
            ambientLight = new AmbientLight();
            ambientLight.setColor(ColorRGBA.White);
            addLight(ambientLight); 
        } else if(ambientLight != null && !isAmbientLight){
            removeLight(ambientLight);
            ambientLight = null;
        }
    }
    
    public void setAmbientLightColor(ColorRGBA color){
        ambientLight.setColor(color);
    }
    
    public DirectionalLight getSunLight() {
        return sunLight;
    }
    
    public void setSunLight(boolean isSunLight){
        if(sunLight == null && isSunLight){
            sunLight = new DirectionalLight();
            sunLight.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
            sunLight.setColor(ColorRGBA.Yellow);
            addLight(sunLight); 
        } else if(sunLight != null && !isSunLight){
            removeLight(sunLight);
            sunLight = null;
        }
    }
    
    public void setSunLightColor(ColorRGBA color){
        sunLight.setColor(color);
    }
    
    public void setSkyColor(ColorRGBA color){
        Game.game.getViewPort().setBackgroundColor(color);
    }
    
    public ColorRGBA getSkyColor(){
        return Game.game.getViewPort().getBackgroundColor();
    }
    
    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }
    
    /*
     * Legt fest, ob die Untergrundtextur wiederholt werden soll
     */
    public void setUndergroundTextureRepetition(boolean isRepetition){
       if(!isRepetition){
           if(isUndergroundTextureRepetition){
               this.underground.getMesh().scaleTextureCoordinates(new Vector2f(1f/undergroundSizeX, 1f/undergroundSizeZ));
               this.underground.getMaterial().getTextureParam("DiffuseMap").getTextureValue().setWrap(Texture.WrapMode.Clamp);
               isUndergroundTextureRepetition = false;
           }
       } else{
           if(!isUndergroundTextureRepetition){
               this.underground.getMesh().scaleTextureCoordinates(new Vector2f(undergroundSizeX, undergroundSizeZ));
               this.underground.getMaterial().getTextureParam("DiffuseMap").getTextureValue().setWrap(Texture.WrapMode.Repeat);
               isUndergroundTextureRepetition = true;
           }
       }
    }
    
    public boolean setUndergroundTextureRepetition(){
        return isUndergroundTextureRepetition;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden   
 
    public void activatePhysics(boolean value){
        if(value){
            Game.game.getStateManager().attach(bulletAppState);
        } else{
            Game.game.getStateManager().detach(bulletAppState);
        }
    }
    
     public void activatePhysicsForPlayer(boolean value){
        if(value){
            bulletAppState.getPhysicsSpace().add(player);
        } else{
            bulletAppState.getPhysicsSpace().remove(player);
        }
    }
}
