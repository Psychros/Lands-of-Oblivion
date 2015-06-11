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
        return paramString1.toLowerCase().startsWith(paramString2.toLowerCase());
    }
}
