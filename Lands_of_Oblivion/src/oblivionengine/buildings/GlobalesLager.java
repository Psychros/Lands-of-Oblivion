package oblivionengine.buildings;

import oblivionengine.Game;

/**
 *
 * @author To
 */
public class GlobalesLager {
    
    //Objektvariablen
    
    //Anzahl aller Ressourcen
    private int[] ressourcen = new int[2]; 
    
    //Größe des Lagers
    private int größe;

    
    //--------------------------------------------------------------------------
    //Konstruktoren
    public GlobalesLager() {
        this(10);
    }
    
    public GlobalesLager(int größe) {
        setGröße(größe);
        
        //Startwert für die Ressourcen setzen
        for (int i = 0; i < ressourcen.length; i++) {
            addRessourcen(i, 10); //Startwert für alle Ressourcen
        }
    }

    
    //--------------------------------------------------------------------------
    //Getter und Setter
    
    //Gibt den aktuellen Lagerstand der als Argument übergebenen Ressource zurück
    public int getAnzahlRessourcen(Ressourcen id) {
        return ressourcen[id.ordinal()];
    }
    
    public int getAnzahlRessourcen(int id) {
        return ressourcen[id];
    }

    public int getGröße() {
        return größe;
    }

    public void setGröße(int größe) {
        this.größe = größe;
        Game.game.screens.setText("inGame", "Lager", größe);
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
        addRessourcen(id.ordinal(), anzahl);
    }
    
    public void addRessourcen(int id, int anzahl){
        ressourcen[id] += anzahl;
        
        //Die Anzahl einer Ressource darf nicht kleiner als 0 und nicht größer als die Lagergröße sein
        if(ressourcen[id] < 0)
            ressourcen[id] = 0;
        else if(ressourcen[id] > größe){
            ressourcen[id] = größe;
        }
        
        //Anzeige aktualisieren
        actualizeText(id);
    }
    
    //Aktualisiere die Anzeige
    public void actualizeText(int id){
        if(id == Ressourcen.Wood.ordinal())
            Game.game.screens.setText("inGame", "Baumstämme", ressourcen[id]);
        else if(id == Ressourcen.Stone.ordinal())
            Game.game.screens.setText("inGame", "Stein", ressourcen[id]);
    }
}
