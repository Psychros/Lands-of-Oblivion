/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oblivionengine.charakter.npc;

/**
 * Einem WorkerControl kann ein Job zugeteilt werden
 * @author To
 */
public class Job {
    //Objektvariablen
    protected WorkerControl worker;

    //--------------------------------------------------------------------------
    //Konstruktoren
    public Job(){
        
    }
    
    public Job(WorkerControl worker){
        this.worker = worker;
    }

    //--------------------------------------------------------------------------
    //Getter und Setter
    public WorkerControl getWorker() {
        return worker;
    }

    public void setWorker(WorkerControl worker) {
        this.worker = worker;
    }
    

    //--------------------------------------------------------------------------
    //Klasseninterne Methoden
    public void update(float tpf){
        
    }
}
