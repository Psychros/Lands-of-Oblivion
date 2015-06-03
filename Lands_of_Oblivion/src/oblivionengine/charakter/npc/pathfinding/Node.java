/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc.pathfinding;

import com.jme3.math.Vector2f;
import java.util.ArrayList;

/**
 *
 * @author To
 */
public class Node {
    //Objektvariablen
    private Node parentNode; //VorgängerNode
    private float cost;
    private Vector2f pos;
    private boolean isPossible = true;
    private ArrayList<Node> childs = new ArrayList();
    

    //--------------------------------------------------------------------------
    //Konstruktoren
    public Node(Node parentNode, float cost, Vector2f pos) {    
        this.parentNode = parentNode;
        this.cost = cost;
        this.pos = pos;
    }
    
    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public Node getParentNode() {    
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }
    
    public float getCost() {
        return cost;
    }

    public Vector2f getPos() {
        return pos;
    }

    public boolean isPossible() {
        return isPossible;
    }

    public void setIsPossible(boolean isPossible) {
        this.isPossible = isPossible;
    }

    public ArrayList<Node> getChilds() {
        return childs;
    }
}
