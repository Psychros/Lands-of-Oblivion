package oblivionengine.cheathandling;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oblivionengine.Game;
import oblivionengine.cheathandling.Cheats.SetGravityCheat;
import oblivionengine.cheathandling.Cheats.SetJumpForceCheat;
import oblivionengine.cheathandling.Cheats.SetMouseSensitivityCheat;
import oblivionengine.cheathandling.Cheats.SetPlayerMassCheat;
import oblivionengine.cheathandling.Cheats.SetMoveSpeedCheat;

/**
 *
 * @author Tobi
 */
public class Cheatmanager extends Thread implements Runnable{
    private static final String identifierCheatmanager = "This is Cheatmanager";
    private final Object syncObj;
    private ArrayList<Cheat> cheats;
    private ArrayList<GameCheatstringContainer> cheatQueue;
    
    public Cheatmanager(){
        this.cheats = new ArrayList<Cheat>();
        this.cheatQueue = new ArrayList<GameCheatstringContainer>();
        syncObj = new Object();
        loadCheats();
        this.setName("CheatThread");
        this.start();
    }
    
    @Override
    public void run(){
        while (!isInterrupted()){
            doTasks();
            try {
                synchronized(syncObj){
                    syncObj.wait();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Cheatmanager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getIdentification(){
        return identifierCheatmanager;
    }

    
    public boolean doCheat(Game game, String cheatText){
        synchronized(syncObj){
            cheatQueue.add(new GameCheatstringContainer(game, cheatText));
            syncObj.notifyAll();
        }
        return true;
    }
    
    private void doTasks(){
        while(cheatQueue.size() > 0){
            GameCheatstringContainer tempCont;
            synchronized(syncObj){
                tempCont = cheatQueue.get(0);
                cheatQueue.remove(0);
            }
            if (!executeCheat(tempCont)){
                System.out.println("No suitable cheat found");
            }
        }
    }
    
    private boolean executeCheat(GameCheatstringContainer localCont){
        for (Cheat tempCheat : cheats){
            return tempCheat.doCheat(localCont.getGame(), localCont.getCheatString());
        }
        return false;
    }
    
    private void loadCheats(){
      cheats.add(new SetMoveSpeedCheat());
      cheats.add(new SetMouseSensitivityCheat());
      cheats.add(new SetGravityCheat());
      cheats.add(new SetJumpForceCheat());
      cheats.add(new SetPlayerMassCheat());
    }
}
