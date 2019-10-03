package problems;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class MajorityElement {

	public static void main(String[] args) {
		int[] A = {2,1,2,1}; 

		System.out.println(majorityElement(A));
	}

	public static int majorityElement(final int[] A) {
		if (A.length > 1) {
			HashMap<Integer, Integer> frequencyByElements = new HashMap<>();
			for(int i=0; i<A.length; i++) {
				Integer frequency = Optional.ofNullable(frequencyByElements.get(A[i])).orElse(0);
				frequencyByElements.put(A[i], ++frequency);
			}

			Map<Integer, Integer> elementsByFrequencySorted = frequencyByElements.entrySet().stream()
					.sorted((e1, e2) -> e2.getValue() - e1.getValue())
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (u, v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); }, LinkedHashMap::new));

			Entry<Integer, Integer> candidate = elementsByFrequencySorted.entrySet().stream().findFirst().get();
			if (candidate.getValue() > A.length / 2) {
				return candidate.getKey();
			} 
		}
		return -1;
	}
}
