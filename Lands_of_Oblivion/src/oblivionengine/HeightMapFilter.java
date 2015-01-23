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
                heightMap[(int)(i*aheightMap.getSize()+j)] += 60-i*2;
            }
        }
        //Hinten
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < aheightMap.getSize(); j++) {
                heightMap[(int)(heightMap.length-((i+1)*aheightMap.getSize())+j)] += 60-i*2;
            }
        }
    }
}
