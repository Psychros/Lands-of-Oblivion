
package oblivionengine.cheathandling;

import oblivionengine.Game;

/**
 * Please name every Cheat after its task + Cheat so for example FullHealthCheat
 * @author Tobi
 */
public abstract class Cheat {
    protected int paramNumber;
    protected String identifier;
    
    protected abstract void executeCheat(Game game, double[] params) throws Throwable;
    
    protected final boolean doCheat(Game game, String cheatString){
        System.out.println(identifier + paramNumber);
        
        boolean returned = false;
        if (util.StringUtil.startsWithIgnoreCase(cheatString, identifier)){
            System.out.println("Cheat " + identifier + " is to be executed");
            try{
                if (paramNumber > 0){
                    executeCheat(game, getParams(cheatString));
                } else {
                    executeCheat(game, null);
                }
                returned = true;
            } catch (Throwable t){
                System.out.println("Execution failed: " + t.getMessage());
            }
        }
        return returned;
    }
    
    public String getIdentification() {
        return identifier;
    }
    
    private double[] getParams(String cheatString) throws Throwable{
        double[] params = new double[paramNumber];
        String[] paramStrs;
        
        paramStrs = cheatString.substring(identifier.length() + 1).split(" ");
        
        for (int i = 0; i < paramNumber; i++){
            params[i] = Double.parseDouble(paramStrs[i]);
        }
        return params;
    }
}
