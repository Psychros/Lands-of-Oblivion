/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc.pathfinding;

import com.jme3.math.Vector2f;
import java.util.ArrayList;
import java.util.Collections;
import oblivionengine.Game;
import oblivionengine.charakter.npc.NPCControl;

/**
 *
 * @author To
 */
public class PathFinder {
    //Objektvariablen
    private boolean[][] fields;
    private Node[][] nodes;
    private Node selectedNode;
    private Vector2f start, goal;
    private boolean isSearching = true;
    private NPCControl npc;

    //--------------------------------------------------------------------------
    //Konstruktoren
    public PathFinder(Vector2f start, Vector2f goal, NPCControl npc) {
        this.start = new Vector2f(goal.x + 500, goal.y+500);
        this.goal = new Vector2f(start.x+500, start.y+500);
        this.fields = Game.game.mapState.getMap().getFields();
        this.npc = npc;
        
        nodes = new Node[fields.length][fields[0].length];
    }

    //--------------------------------------------------------------------------
    //Getter und Setter

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    public Node generatePath(){
        //Startknoten erstellen
        nodes[(int)start.x][(int)start.y] = new Node(null, 0, start);
        selectedNode = nodes[(int)start.x][(int)start.y];
        
        Suchen:
        while (isSearching) {  
            generateCostsForNodesAround(selectedNode);
           /* System.err.println(selectedNode.getPos());
            for (Node n : selectedNode.getChilds()) {
                System.out.println(n.getPos());
            }*/
            
            //Beste Node auswählen
            if(selectedNode.getChilds().size() > 0){
                selectedNode = selectedNode.getChilds().get(0);
                selectedNode.getParentNode().getChilds().remove(selectedNode);
                selectedNode.getParentNode().getChilds().trimToSize();
                
                if(selectedNode.getPos().equals(goal))
                    return selectedNode;
            }  else {
                if(selectedNode.getParentNode() != null)
                    selectedNode = selectedNode.getParentNode();
                else{
                    return null;
                }
            }
        }
        
        return null;
    }
    
    public void generateCostsForNodesAround(Node n){
        ArrayList<Node> nodes = new ArrayList();
        
        //8 Nodes generieren
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(-1, -1)));
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(0, 1)));
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(0, 1)));
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(1, -2)));
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(0, 2)));  
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(1, -2)));
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(0, 1)));
        nodes.add(generateNode(new Vector2f(selectedNode.getPos()).addLocal(0, 1)));
        nodes.removeAll(Collections.singleton(null));
        
        //Nur die Nodes dürfen geprüft werden, welche begehbar sind
        for (Node node : nodes) {
            if(!node.isPossible())
                nodes.remove(node);
        }
        nodes.trimToSize();
        
        //Nodes nach Kosten sortieren mittels Bubblesort
        Collections.sort(nodes, new NodeCostComparator());
        nodes.removeAll(Collections.singleton(null));
        nodes.trimToSize();
        
        //Die sortierten Nodes als Childs dem parentNode hinzufügen
        for (Node node : nodes) {
            selectedNode.getChilds().add(node);
        }
    }
    
    //Neue Node generieren, sofern das Feld begehbar ist
    public Node generateNode(Vector2f pos){
        
        if(nodes[(int)pos.x][(int)pos.y] == null){
            //Kosten = Distanz zu Oberknoten + Distanz zu Ziel + Oberknoten.cost
            float cost = selectedNode.getPos().distance(pos) + pos.distance(goal) + selectedNode.getCost();
            Node n = new Node(selectedNode, cost, pos);
            nodes[(int)pos.x][(int)pos.y] = n;
            
            if(fields[(int)pos.x][(int)pos.y] == true){
                n.setIsPossible(true);
                return n;
            } else{
                n.setIsPossible(false);
            }
        }
        
        return null;
    }
    
    //Den Pfad in eine Arraylist packen
    public ArrayList<Vector2f> makeListFromPath(Node goal){
        ArrayList<Vector2f> list = new ArrayList();
        Node selectedNode = goal;
        
        while (selectedNode.getParentNode() != null) {            
            list.add(selectedNode.getPos().subtract(500, 500));
            selectedNode = selectedNode.getParentNode();
        }
        
        //Zielvektor hinzufügen
        list.add(start.subtract(500, 500));
        //Startvektor entfernen
        list.remove(0);
        list.trimToSize();
        
        //Ziel und Start wurden am Anfang vertauscht
        System.out.println("Start: " +  this.goal.subtract(500, 500) + "  Ziel: " + this.start.subtract(500, 500));
        
        for (Vector2f v : list) {
            System.out.println(v);
        }
        
        return list;
    }
}
