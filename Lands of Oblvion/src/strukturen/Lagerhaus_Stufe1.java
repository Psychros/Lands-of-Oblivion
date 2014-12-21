/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package strukturen;

import blöcke.Eichenbretter;
import blöcke.Eichenbrettschräge;
import blöcke.Eichenstamm;
import blöcke.Fackel;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static strukturen.Struktur.strukturenPfad;

/**
 *
 * @author To
 */
public class Lagerhaus_Stufe1 extends Struktur{
    
    //Pfad zum Bauplan
    public static final String pfad = strukturenPfad + "\\Lagerhaus_Stufe1.struktur";
   
    public Lagerhaus_Stufe1(){
        //Einlesen des Bauplanes
        String bauplan = "";
        try {
           BufferedReader in = new BufferedReader(new FileReader(pfad));
           String zeile = null;
           while ((zeile = in.readLine()) != null) {
               if(zeile.trim().equals(""))
                   bauplan += " ";
               else
                   bauplan += zeile + ";";
           }
	} catch (IOException e) {
            e.printStackTrace();
	}
        
        //Bauplan in ein 3dimensionales String-Array umformen
        String[] ebenen = bauplan.split(" ");
        String[][]zeilen = new String[ebenen.length][ebenen[0].split(";").length];
        for (int y = 0; y < ebenen.length; y++) {
            zeilen[y] = ebenen[y].split(";");
        }
        String[][][] spalten = new String[ebenen.length][zeilen[0].length][zeilen[0][0].split(",").length];
        for (int y = 0; y < ebenen.length; y++) {
            for (int z = 0; z < zeilen[0].length; z++) {
                spalten[y][z] = zeilen[y][z].split(",");
            }
        }
        
        //String-array in int-Array konvertieren
        blöcke = new int[spalten.length][spalten[0].length][spalten[0][0].length];
        for (int y = 0; y < spalten.length; y++) {
            for (int z = 0; z < spalten[y].length; z++) {
                for (int x = 0; x < spalten[y][z].length; x++) {
                    blöcke[y][z][x] = Integer.parseInt(spalten[y][z][x]);
                }
            }
        }
    }
   
     
     @Override
     public void buildStructure(Node rootNode, int posX, int posZ){
         for (int y = 0; y < blöcke.length; y++) {
             for (int z = 0; z < blöcke[y].length; z++) {
                 for (int x = 0; x < blöcke[y][z].length; x++) {
                     switch(blöcke[y][z][x]){
                         case 1: Eichenstamm eichenStamm = new Eichenstamm(posX+x, y+1, posZ+z); rootNode.attachChild(eichenStamm); break;
                         case 2: Eichenbretter eichenBretter = new Eichenbretter(posX+x, y+1, posZ+z); rootNode.attachChild(eichenBretter); break;
                         case 3: Fackel fackel = new Fackel(posX+x, y+1, posZ+z); rootNode.attachChild(fackel); break;
                         case 4: Eichenbrettschräge eichenBretterSchräge = new Eichenbrettschräge(posX+x, y+1, posZ+z, 0); rootNode.attachChild(eichenBretterSchräge); break;
                         case 5: Eichenbrettschräge eichenBretterSchräge2 = new Eichenbrettschräge(posX+x, y+1, posZ+z, 2); rootNode.attachChild(eichenBretterSchräge2); break;   
                     }
                 }
             }
         }
     }
}
