package oblivionengine;

/**
 *
 * @author To
 */
public class Lager {
    
    //Objektvariablen
    
    //Anzahl aller Ressourcen und deren Index
    private int[] ressourcen = new int[1];
    public enum Ressourcen{Wood}
    
    //Größe des Lagers
    private int größe;

    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public Lager() {
        this(200);
    }
    
    public Lager(int größe) {
        this.größe = größe;
        
        //Startwert für die Ressourcen setzen
        for (int i = 0; i < ressourcen.length; i++) {
            ressourcen[i] = 0;
        }
    }

    
    //--------------------------------------------------------------------------
    //Getter und Setter
    public int[] getRessourcen() {
        return ressourcen;
    }

    public int getGröße() {
        return größe;
    }

    public void setGröße(int größe) {
        this.größe = größe;
    } 
    
    
    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    
    /*
     * Ressourcen auffüllen oder entfernen
     */
    public void addRessourcen(int index, int number){
        ressourcen[index] += number;
        
        //Die Anzahl einer Ressource darf nicht kleiner als 0 und nicht größer als die Lagergröße sein
        if(ressourcen[index] < 0)
            ressourcen[index] = 0;
        else if(ressourcen[index] > größe)
            ressourcen[index] = größe;
    }
}
