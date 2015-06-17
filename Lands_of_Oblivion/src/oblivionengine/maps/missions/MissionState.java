/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oblivionengine.maps.missions;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import de.lessvoid.nifty.controls.Console;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import oblivionengine.Game;

/**
 *
 * @author To
 */
public class MissionState extends AbstractAppState{
    private String path;
    private int index = 0;
    private boolean isMouseclick = false;
    private boolean firstMouseclick = false; //Handelt es sich ums reindrücken oder loslassen?
    private Console console;
    
    private ArrayList<String> text = new ArrayList();
    
    public MissionState(String path) {
        super();
        this.path = path;
        
        Game.game.getStateManager().attach(this);
        Game.game.screens.goToMenu("missions");
        
        //Missionskonsole initiieren
        console = Game.game.screens.getNifty().getScreen("missions").findNiftyControl("console", Console.class);
    
        /*
         * Text laden
         */
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);

            //Alle Zeilen in ArrayList laden
            String t = "";
            do{
                t = br.readLine();
                text.add(t);
            }while(!t.equals(""));
            
            br.close();
        } catch(Exception e){
            System.err.println("Datei konnte nicht gefunden werden!");
        }
    }
    
    
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }
    
    @Override
    public void update(float tpf) {
        //Wenn geklickt wurde wird die nächste Zeile angezeigt
        if(isMouseclick){
            nextLine();
        }
        
        isMouseclick = false;
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
    }
    
    //Nächste Zeile anzeigen
    public void nextLine(){
        if(text.size() > index){
            //Seitenumbruch
            if(text.equals("/s")){
                console.clear();
                nextLine();
            } else if(text.equals("/n")){
                console.output("");
                nextLine();
            }else {
                console.output(text.get(index));
            }
            index++;
        } else{
            //Ins Spiel springen
            Game.game.getStateManager().detach(this);
            Game.game.screens.goToGame();
            Game.game.mapState.resumeGame();
        }
    }
}
