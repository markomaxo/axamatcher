package axa.ma.axamatcher.service;

 

import java.text.Normalizer;
import java.util.regex.Pattern;

 

import org.springframework.stereotype.Service;

 

@Service
public class Sanitizer {
    
    public String clean(String input) {
        
        // mettre en miniscule et eléminé les espaces en début et fin
        input = input.toLowerCase().trim();
        
        
        // remplacer les accents
        String strTemp = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        input = pattern.matcher(strTemp).replaceAll("");
        
        // autres vérifications
        
        return input
                .replace(" & "," et ")
                .replaceAll("l'"," ")
                .replaceAll("[^\\p{Alnum} ]", " ") // update
                .replaceAll("^societe ","")
                .replaceAll(" ste ","")
                .replaceAll(" sarl ","")
                .replaceAll(" sarl au ","")
                .replaceAll(" sarlau ","")
                .replaceAll(" sarl au$","")
                .replaceAll(" sarlau$","")
                .replaceAll(" sarl$","")
                .replaceAll(" au$","")
                .replaceAll(" sas$","")
                .replaceAll(" scs$","")
                .replaceAll(" sca$","")
                .replaceAll(" snc$","")
                .replaceAll(" gie$","")
                .replaceAll(" of "," ")
                .replaceAll("^ste ","")
                .replaceAll("^ste ","")
                .replaceAll("^ste ","")
                .replaceAll("^ste ","")
                .replaceAll(" ste$","") 
                .replaceAll("^sa ","")
                .replaceAll(" sa$","")    
                .replaceAll("^les ","")
                .replaceAll(" le "," ")
                .replaceAll(" la "," ")
                .replaceAll(" de "," ")
                .replaceAll(" des "," ")
                .replaceAll(" de "," ")
                .trim().replaceAll(" +", " ") // remove one or more white space
                ;
                //.replace(" ","");

 

        
    }
    
}