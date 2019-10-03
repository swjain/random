package problems;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindCentCombinations {

	private static LinkedList<LinkedList<Integer>> findCentsCombination(int N, int[] S) {
		TreeSet<Integer> cents = IntStream.of(S).boxed().collect(Collectors.toCollection(TreeSet::new));
		S = cents.stream().mapToInt(x -> x).toArray();
		
		@SuppressWarnings("unchecked")
		LinkedList<LinkedList<Integer>>[] a = new LinkedList[N+1];
		for (int i = 0; i < a.length; i++) {
			a[i] = new LinkedList<>();
		}

		for(int cent: S) {
			for(int i=S[0]; i<a.length; i++) {
				int total = i;
				if (cent <= total) {
					int floorIndex = total - cent;
					if(floorIndex == 0) {
						LinkedList<Integer> newList = new LinkedList<>();
						newList.add(cent);
						a[i].add(newList);
					} 
					
					if(a[floorIndex].isEmpty()) {
						continue;
					}
					
					for(List<Integer> li: a[floorIndex]) {
						LinkedList<Integer> newList = new LinkedList<>(li);
						newList.add(cent);
						a[i].add(newList);
					}
				}
			}
		}

		return a[N];
	}

	public static void main(String args[]){
		LinkedList<LinkedList<Integer>> combinations;

		combinations = findCentsCombination(150, new int[]{1, 2, 3});
		System.out.println(combinations.size());
		System.out.println(combinations);
		System.out.println();

//		combinations = findCentsCombination(10, new int[]{2, 5, 3, 6});
//		System.out.println(combinations.size());
//		System.out.println(combinations);
//		System.out.println();

	}
}
