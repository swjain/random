package problems;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MinimumSwaps {

	static int minimumSwaps(int[] a) {

		Map<Integer, Integer> prevElementsByPosition = new HashMap<>();
		for(int i=0; i<a.length; i++) {
			prevElementsByPosition.put(i, a[i]);
		}

		Arrays.sort(a);

		Map<Integer, Integer> currentPositionsByElement = new HashMap<>();
		for(int i=0; i<a.length; i++) {
			currentPositionsByElement.put(a[i], i);
		}

		int cycleCount = 0;

		boolean[] visited = new boolean[a.length];
		for(int i=0; i<a.length; i++) {
			int prevElemAtCurrPos = prevElementsByPosition.get(i);
			if (prevElemAtCurrPos != a[i] && !visited[i]) {
				int next = a[i];
				int currPos = i;

				do {
					next = prevElementsByPosition.get(currPos);
					currPos = currentPositionsByElement.get(next);
					visited[currPos] = true;
					cycleCount++;
				} while(next != a[i]);

				cycleCount--;
			}
		}

		return cycleCount;

	}

	public static void main(String[] args) throws IOException {
		
		int[] a = { 4, 3, 1, 2 };
		System.out.println(minimumSwaps(a)); // answer 3
		
		a = new int[]{ 2, 3, 4, 1, 5 };
		System.out.println(minimumSwaps(a)); // answer 3

		
		a = new int[]{ 1, 3, 5, 2, 4, 6, 7 };
		System.out.println(minimumSwaps(a)); // answer 3
	}
}
