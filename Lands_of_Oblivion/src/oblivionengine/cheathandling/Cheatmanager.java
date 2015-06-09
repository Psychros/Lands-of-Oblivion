package oblivionengine.cheathandling;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import oblivionengine.Game;
import oblivionengine.cheathandling.Cheats.ChangeRunningVelocityCheat;

/**
 *
 * @author Tobi
 */
public class Cheatmanager extends Thread implements Runnable, Cheat {
    public static final String identifierCheatmanager = "This is Cheatmanager";
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
    
    private void doTasks(){
        while(cheatQueue.size() > 0){
            GameCheatstringContainer tempCont;
            synchronized(syncObj){
                tempCont = cheatQueue.get(0);
                cheatQueue.remove(0);
            }
            executeCheat(tempCont);
        }
    }
    
    private void executeCheat(GameCheatstringContainer localCont){
        for (Cheat tempCheat : cheats){
            if (tempCheat.doCheat(localCont.getGame(), localCont.getCheatString())) break;
        }
    }

    @Override
    public String getIdentification(){
        return identifierCheatmanager;
    }

    @Override
    public boolean doCheat(Game game, String cheatText){
        synchronized(syncObj){
            cheatQueue.add(new GameCheatstringContainer(game, cheatText));
            syncObj.notifyAll();
        }
        return true;
    }
    
    private void loadCheats(){
      cheats.add(new ChangeRunningVelocityCheat());
    }
}
