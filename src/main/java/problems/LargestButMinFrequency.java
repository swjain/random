package problems;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class LargestButMinFrequency {

	public static int LargButMinFreq(int arr[], int n) {
		HashMap<Integer, Integer> frequenciesByNumber = new HashMap<>();
        HashMap<Integer, Set<Integer>> numbersByFrequency = new HashMap<>();
        
        for(int i=0; i<arr.length; i++) {
            Integer frequency = frequenciesByNumber.get(arr[i]);
            if (frequency == null) {
                frequency = 1;
            } else {
                frequency++;
            }
            frequenciesByNumber.put(arr[i], frequency);
        }
        
        Integer lowestFrequency = null;
        for(Entry<Integer, Integer> entry: frequenciesByNumber.entrySet()) {
            Integer frequency = entry.getValue();
            Set<Integer> numbers = numbersByFrequency.get(frequency);
            if (numbers == null) {
                numbers = new HashSet<>();
                numbersByFrequency.put(frequency, numbers);
            }
            numbers.add(entry.getKey());
        
            
            if (lowestFrequency == null || frequency < lowestFrequency) {
                lowestFrequency = frequency;
            }
        }
        
        return Collections.max(numbersByFrequency.get(lowestFrequency));
	}
	
}
