/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author To
 */
public class Inventar {
    
    private SimpleApplication app;
    private InventarSlot[] inventarSlots = new InventarSlot[24];
    private boolean isOffen = false;
    
    //Diese Variable wird benötigt, da auch beim loslassen der Taste e eine Aktion ausgelöst wird
    private boolean akzeptiereTaste = true;
    
    //Anzeige des Inventars
    private Node inventarNode;
    private Picture inventarHintergrund;
    
    /*
     * Konstruktor
     */
    public Inventar(SimpleApplication app){
        this.app = app;
        
        inventarNode = new Node("Inventar");
        
        //Einstellen des Hintergrundes
        inventarHintergrund = new Picture("Inventarhintergrund");
        inventarHintergrund.setImage(app.getAssetManager(), "Interface/Inventar.png", true);
        inventarHintergrund.move(Main.main.getSettings().getWidth()/4, 0, 0);
        inventarHintergrund.setWidth(Main.main.getSettings().getWidth()/2);
        inventarHintergrund.setHeight(Main.main.getSettings().getHeight()/2);
        inventarNode.attachChild(inventarHintergrund);
    }

    
    public boolean isOffen() {
        return isOffen;
    }
    
    /*
     * Öffnen des Inventars
     */
    public void öffnen(){
        if(akzeptiereTaste){
            isOffen = true;
            
            //Anzeigen des Inventars
            app.getGuiNode().attachChild(inventarNode);
            
            akzeptiereTaste = false;
        }
        else{
            akzeptiereTaste = true;
        }
    }
    
    
    /*
     * Schließen des Inventars
     */
    public void schließen(){
        if(akzeptiereTaste){
            isOffen = false;       
            
            //Anzeige des Inventars aus der GuiNode entfernen
            app.getGuiNode().detachChild(inventarNode);
            
            akzeptiereTaste = false;
        }
        else{
             akzeptiereTaste = true;
        }
    }
}
