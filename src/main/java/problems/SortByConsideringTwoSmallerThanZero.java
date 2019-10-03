package problems;
import java.util.ArrayList;
import java.util.Collection;

public abstract class SortByConsideringTwoSmallerThanZero {
	
	public static void main(String args[]) throws Exception {
	
		int[] N = {1, 2, 2, 2, 1, 1, 0, 0, 1, 2, 2, 2, 0};
		for (int i = 0; i < N.length; i++) {
			for (int j = 0; j < i; j++) {
				if(isGreater(N[j], N[i])) {
					swap(N, i, j);
				}
			}
		}
		
		Collection<Integer> map = new ArrayList<>();
		
		for(int n: N) {
			map.add(n);
		}
		
		System.out.println(map);
		
	}
	
	private static boolean isGreater(int a, int b) throws Exception {
		
		if (a == 1) {
			return true;
		}
		
		if (a == 2) {
			return false;
		}
		
		if (a == 0) {
			return b == 2 ? true : false;
		}
		
		throw new Exception("Invalid value!");
	}
	
	private static void swap(int[] N, int i, int j) {
		int temp = N[i];
		N[i] = N[j];
		N[j] = temp;		
	}
	
}