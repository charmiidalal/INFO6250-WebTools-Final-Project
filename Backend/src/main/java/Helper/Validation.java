/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dedhi
 */
public class Validation {
    
    public boolean passwordPatternCorrect(String input){
        Pattern p = Pattern.compile("(?=^.{6,10}$)(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&amp;*()_+}{&quot;:;'?/&gt;.&lt;,])(?!.*\\s).*$");
        Matcher m = p.matcher(input);
        boolean b = m.matches();
        return b;
    }
   
}
