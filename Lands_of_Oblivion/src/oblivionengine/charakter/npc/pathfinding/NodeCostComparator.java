/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc.pathfinding;

import java.util.Comparator;


/**
 *
 * @author To
 */
public class NodeCostComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
       Node n1 = (Node) o1;
       Node n2 = (Node) o2;
       
       if(n1 == null && n2 != null)
           return 1;
       if(n2 == null && n1 != null)
           return -1;
       if(n1 == null && n2 == null)
           return 0;
       
       if(n1.getCost() < n2.getCost())
           return -1;
        if(n1.getCost() > n2.getCost())
           return 1;
        return 0;
    }
}
