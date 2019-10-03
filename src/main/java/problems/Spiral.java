package problems;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class Spiral {

	public static void main(String[] args) throws IOException {
		
		System.out.println(((Integer)1).equals((Long)1L));
		
		int[][] in = new int[][] {
			new int[] {1,2,3,4},
			new int[] {5,6,7,8},
			new int[] {9,10,11,12},
			new int[] {13,14,15,16}
		};
		
		List<Integer> actual = spiral(in, new ArrayList<>(), in.length * in[0].length, 0, 0, in.length-1, in[0].length-1);
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10);
		System.out.println(actual);
		
		if (actual.size() != expected.size()) {
			System.out.println("failed");
			System.exit(1);
		}
		
		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i) != expected.get(i)) {
				System.out.println("failed");
				System.exit(1);
			}
		}
		System.out.println("passed");
		
		in = new int[][] {
			new int[] {1,2,3,4,5,6},
			new int[] {7,8,9,10,11,12},
			new int[] {13,14,15,16,17,18},
		};
		
		actual = spiral(in, new ArrayList<>(), in.length * in[0].length, 0, 0, in.length-1, in[0].length-1);
		expected = Arrays.asList(1, 2, 3, 4, 5, 6, 12, 18, 17, 16, 15, 14, 13, 7, 8, 9, 10, 11);
		System.out.println(actual);
		
		if (actual.size() != expected.size()) {
			System.out.println("failed");
			System.exit(1);
		}
		
		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i) != expected.get(i)) {
				System.out.println("failed");
				System.exit(1);
			}
		}
		System.out.println("passed");
	}

	private static List<Integer> spiral(int[][] a, List<Integer> acc, int limit, int i, int j, int m, int n) {
		if (acc.size() >= limit) {
			return acc;
		}
		
		int initiali = i, initialj = j;
		Consumer<Integer> consumer = x -> { if (acc.size() < limit) acc.add(x); };
		
		for(; j < n; j++) consumer.accept(a[i][j]);
		for(; i < m; i++) consumer.accept(a[i][j]);
		for(; j > initialj; j--) consumer.accept(a[i][j]);
		for(; i > initiali; i--) consumer.accept(a[i][j]);
		
		return spiral(a, acc, limit, ++i, ++j, --m, --n);
	}
}
