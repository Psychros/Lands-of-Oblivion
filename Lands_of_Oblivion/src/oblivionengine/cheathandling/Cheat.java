
package oblivionengine.cheathandling;

import oblivionengine.Game;

/**
 * Please name every Cheat after its task + Cheat so for example HealthCheat
 * @author Tobi
 */
public abstract class Cheat {
    
    public abstract String getIdentification();
    public abstract boolean doCheat(Game game, String cheatText);
    
    protected double[] checkCheat(String identifier, String cheatString, int paramNumber){
        double[] params = new double[paramNumber];
        String paramStr;
        String[] paramStrs;
        
        if (util.StringUtil.startsWithIgnoreCase(cheatString, identifier)){
            System.out.println("Cheat " + identifier + " is to be executed");
            if (paramNumber > 0){
                try{
                    paramStr = cheatString.substring(identifier.length() + 1);
                    paramStrs = paramStr.split(" ");
                    
                    for (int i = 0; i < paramNumber; i++){
                        params[i] = Double.parseDouble(paramStrs[i]);
                    }
                } catch (Throwable t){
                    System.out.println("Execution failed");
                    return null;
                }
            } else {
                return new double[]{Double.MIN_VALUE};
            }
            return params;
        } else {
            return null;
        }
        
    }
}
