package problems;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CountTriplets {

	public static void main(String[] args) throws IOException {
		List<Long> arr = Arrays.asList((long) 1, (long) 2, (long) 2, (long) 4);
		//    	System.out.println(countTriplets(arr, 2)); // expected 2;

		arr = Arrays.asList(1L, 3L, 9L, 9L, 27L, 81L);
		System.out.println(countTriplets(arr, 3)); // expected 6;

		arr = Arrays.asList(1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L);
		System.out.println(countTriplets(arr, 1)); // expected 161700;
		
	}

	static long countTriplets(List<Long> arr, long r) {
		Map<Long, Long> left = new HashMap<>();
		Map<Long, Long> right = new HashMap<>();
		for (int i=0; i<arr.size(); i++) {
			long num = arr.get(i);
			Long count = Optional.ofNullable(right.get(num)).orElse(0L);
			right.put(num, ++count);
		}
		
		long tripletsCount = 0;

		for (int i=0; i<arr.size(); i++) {
			long num = arr.get(i);
			
			Optional.ofNullable(right.get(num)).ifPresent(x -> {
				if (x > 0)
					right.put(num, --x);
			});

			if (num % r == 0) {
				long c1 = Optional.ofNullable(left.get(num / r)).orElse(0L);
				long c2 = Optional.ofNullable(right.get(num * r)).orElse(0L);
				tripletsCount += c1 * c2;
			}
			
			long aCount = Optional.ofNullable(left.get(num)).orElse(0L);
			left.put(num, ++aCount);
		}
		
		return tripletsCount;
	}

}
