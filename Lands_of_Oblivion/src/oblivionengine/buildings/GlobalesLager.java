package oblivionengine.buildings;

import oblivionengine.Game;

/**
 *
 * @author To
 */
public class GlobalesLager {
    
    //Objektvariablen
    
    //Anzahl aller Ressourcen
    private int[] ressourcen = new int[Ressourcen.values().length]; 
    
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
        addRessourcen(Ressourcen.Holz, 10);
        addRessourcen(Ressourcen.Stein, 10);
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
        Game.game.screens.setText("lager", "Lager", größe);
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
        if(id == Ressourcen.Holz.ordinal()){
            Game.game.screens.setText("inGame", "Baumstämme", ressourcen[id]);
            Game.game.screens.setText("lager",  "Holz", ressourcen[id]);
        }
        else if(id == Ressourcen.Stein.ordinal()){
            Game.game.screens.setText("inGame", "Stein", ressourcen[id]);
            Game.game.screens.setText("lager", "Stein", ressourcen[id]);
        }
        else if(id == Ressourcen.Fisch.ordinal()){
            Game.game.screens.setText("lager", "Fisch", ressourcen[id]);
        }
        else if(id == Ressourcen.Wasser.ordinal()){
            Game.game.screens.setText("lager", "Wasser", ressourcen[id]);
        }
        else if(id == Ressourcen.Getreide.ordinal()){
            Game.game.screens.setText("lager", "Getreide", ressourcen[id]);
        }
        else if(id == Ressourcen.Hopfen.ordinal()){
            Game.game.screens.setText("lager", "Hopfen", ressourcen[id]);
        }
        else if(id == Ressourcen.Bier.ordinal()){
            Game.game.screens.setText("lager", "Bier", ressourcen[id]);
        }
        else if(id == Ressourcen.Mehl.ordinal()){
            Game.game.screens.setText("lager", "Mehl", ressourcen[id]);
        }
        else if(id == Ressourcen.Brot.ordinal()){
            Game.game.screens.setText("lager", "Brot", ressourcen[id]);
        }
    }
}
