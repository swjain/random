package problems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GasStation {
	
	public static void main(String[] args) {
		int[] A = {959, 329, 987, 951, 942, 410, 282, 376, 581, 507, 546, 299, 564, 114, 474, 163, 953, 481, 337, 395, 679, 21, 335, 846, 878, 961, 663, 413, 610, 937, 32, 831, 239, 899, 659, 718, 738, 7, 209 };
		int[] B = {862, 783, 134, 441, 177, 416, 329, 43, 997, 920, 289, 117, 573, 672, 574, 797, 512, 887, 571, 657, 420, 686, 411, 817, 185, 326, 891, 122, 496, 905, 910, 810, 226, 462, 759, 637, 517, 237, 884};
		System.out.println(canCompleteCircuit(A, B));
		
		A = new int[] {1, 2};
		B = new int[] {2, 1};
		System.out.println(canCompleteCircuit(A, B));
		
		A = new int[] {1, 1, 1, 1, 1, 3, 1, 1};
		B = new int[] {1, 1, 3, 1, 1, 1, 1, 1};
		System.out.println(canCompleteCircuit(A, B));
		
		A = new int[] { 872, 102 };
		B = new int[] { 984, 241 };
		System.out.println(canCompleteCircuit(A, B));
	}

	public static int canCompleteCircuit(final int[] A, final int[] B) {
		if (A.length == 0) {
			return -1;
		}

		int start = 0;
		int avlGas = 0;
		int reqGas;
		int i = start;
		int range = A.length-1;
		int prevStart = -1;

		Map<Integer, DestinationAndCost> commutablePaths = new HashMap<>();
		HashSet<Integer> triedStarts = new HashSet<>();
		triedStarts.add(start);
		
		while( start > prevStart ) {
			DestinationAndCost path = commutablePaths.get(i);
			if (path != null) {
				i = path.dest;
				avlGas += path.remainingGas;
			}
			avlGas = avlGas + A[i];
			reqGas = B[i];
			
			if(avlGas < reqGas) {
				if (start != i) {
					commutablePaths.put(start, new DestinationAndCost(i, avlGas - A[i]));
				}
				i = incrementCyclic(i, range);
				prevStart = start;
				start = i;
				triedStarts.add(start);
				avlGas = 0;
				continue;
			}
			
			avlGas -= reqGas;
			i = incrementCyclic(i, range);
			if(i == start) {
				return start;
			}
		}

		return -1;
	}

	private static int incrementCyclic(int i, int range) {
		if (++i > range) {
			return 0;
		}
		return i;
	}

	@SuppressWarnings("unused")
	private static int decrementCyclic(int i, int range) {
		if (--i < 0) {
			return range;
		}
		return i;
	}

	static class DestinationAndCost {
		int dest;
		int remainingGas;

		DestinationAndCost(int dest, int remainingGas) {
			this.dest = dest;
			this.remainingGas = remainingGas;
		}
		
		public String toString() {
			return "{dest: " + dest + ", remainingGas: " + remainingGas + "}";
		}
	}

}
