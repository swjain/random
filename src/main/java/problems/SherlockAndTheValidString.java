package problems;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class SherlockAndTheValidString {

    // Complete the isValid function below.
    static String isValid(String s) {

        char[] a = s.toCharArray();

        if (a.length < 2) {
            return "YES";
        }

        Map<Character, Integer> frequencyByCharacter = new HashMap<>();
        for(int i=0; i<a.length; i++) {
            int frequency = Optional.ofNullable(frequencyByCharacter.get(a[i])).orElse(0);
            frequencyByCharacter.put(a[i], ++frequency);
        }

        Map<Character, Integer> frequencyByCharacterSorted = frequencyByCharacter.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue() - e1.getValue())
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

        List<Integer> frequencies = new ArrayList<>(frequencyByCharacterSorted.values());
        
        
        
        if(new HashSet<>(frequencies).size() == 1
		  || ((frequencies.get(0) - frequencies.get(1)) == 1
		    && new HashSet<>(frequencies.subList(1, frequencies.size())).size() == 1)
		  || (frequencies.get(frequencies.size()-1) == 1
		    && new HashSet<>(frequencies.subList(0, frequencies.size()-1)).size() == 1)
        ) {
        	return "YES";
        }

        return "NO";
    }

    public static void main(String[] args) throws IOException {
    	
    	System.out.println(isValid("xxxaabbccrry"));
    	
    }
}
