/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine;

import com.jme3.terrain.heightmap.AbstractHeightMap;

/**
 *
 * @author To
 */
public class HeightMapFilter {
    
    private AbstractHeightMap aheightMap;
    private float[] heightMap;
    
    public HeightMapFilter(AbstractHeightMap heightMap){
        this.aheightMap = heightMap;
        this.heightMap = aheightMap.getHeightMap();
    }
    
    public float[] getHeightMap(){
        return heightMap;
    }
    
    //Schräge am Rand erstellen
    public void manipulateEdge(){
        //Vorne
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < aheightMap.getSize(); j++) {
                heightMap[(int)((30-i)*aheightMap.getSize()+j)] += (i/2)*(i/2);
            }
        }
        //Hinten
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < aheightMap.getSize(); j++) {
                heightMap[(int)(heightMap.length-((30-i+1)*aheightMap.getSize())+30-j)] += (i/2)*(i/2);
            }
        }
        //Rechts
        for (int i = 0; i < aheightMap.getSize()-1; i++) {
            for (int j = 0; j < 30; j++) {
                heightMap[(int)(aheightMap.getSize()*i+30-j)] += (j/2)*(j/2);
            }
        }
        //Links
        for (int i = 0; i < aheightMap.getSize()-1; i++) {
            for (int j = 0; j < 30; j++) {
                heightMap[(int)(aheightMap.getSize()*(i+1)-30+j)] += (j/2)*(j/2);
            }
        }
    }
}
