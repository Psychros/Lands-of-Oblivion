/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.maps.missions;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author To
 */
public class MissionQueue<Mission> extends ArrayList{
    //Objektvariablen

    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public MissionQueue(int initialCapacity) {
        super(initialCapacity);
    }

    public MissionQueue() {
    }

    public MissionQueue(Collection c) {
        super(c);
    }

    
    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    public void nextMission(){
        if(size() > 0){
            remove(0);
            trimToSize();
        }
    }
}
