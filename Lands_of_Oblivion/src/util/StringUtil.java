/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Tobi
 */
public class StringUtil {
    
    /**
     * This method checks wether paramString1 starts with paramString2 ignoring the case
     * @param paramString1 String to be checked wether it starts with paramString2
     * @param paramString2 
     * @return boolean
     */
    public static boolean startsWithIgnoreCase(String paramString1, String paramString2){
        paramString1 = paramString1.toLowerCase();
        paramString2 = paramString2.toLowerCase();
        
        System.out.println(paramString1 + " -->" + paramString2);
        
        return paramString1.startsWith(paramString2);
    }
}
