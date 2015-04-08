package oblivionengine.buildings;

/**
 *
 * @author To
 */
public class GlobalesLager {
    
    //Objektvariablen
    
    //Anzahl aller Ressourcen
    private int[] ressourcen = new int[1]; 
    
    //Größe des Lagers
    private int größe;

    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public GlobalesLager() {
        this(200);
    }
    
    public GlobalesLager(int größe) {
        this.größe = größe;
        
        //Startwert für die Ressourcen setzen
        for (int i = 0; i < ressourcen.length; i++) {
            ressourcen[i] = 0;
        }
    }

    
    //--------------------------------------------------------------------------
    //Getter und Setter
    
    //Gibt den aktuellen Lagerstand der als Argument übergebenen Ressource zurück
    public int getAnzahlRessourcen(Ressourcen id) {
        return ressourcen[id.ordinal()];
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
     * @param
     * id: Ressourcentyp
     * anzahl: Menge der einzulagernden Ressourcen. Auch ein negativer Wert ist möglich
     */
    public void addRessourcen(Ressourcen id, int anzahl){
        ressourcen[id.ordinal()] += anzahl;
        
        //Die Anzahl einer Ressource darf nicht kleiner als 0 und nicht größer als die Lagergröße sein
        if(ressourcen[id.ordinal()] < 0)
            ressourcen[id.ordinal()] = 0;
        else if(ressourcen[id.ordinal()] > größe){
            ressourcen[id.ordinal()] = größe;
            System.out.println(größe);
        }
    }
}
