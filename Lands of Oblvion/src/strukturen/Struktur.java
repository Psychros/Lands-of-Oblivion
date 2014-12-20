/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package strukturen;

import com.jme3.scene.Node;
import java.awt.Point;
import java.io.File;

/**
 *  @author To
 */
public abstract class Struktur {  
     
    public int[][][] blöcke = null; //[y][z][x] oder [Ebene][Zeile][Spalte]
    
    //Alle Strukturen
    public static File strukturenPfad = new File(System.getProperty("user.home") + "\\Lands of Oblivion\\Strukturen");
    
    /*
     * Generiert in der Welt die Struktur, welche in der Variable blöcke gespeichert ist
     * @param position: Hinterer linker Block der Struktur
     */
    abstract void buildStructure(Node rootNode, int posX, int posZ);
}
