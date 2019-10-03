package problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class FindTwoLargest {

	public static void main(String[] args) {
		Collection<Integer> a = new ArrayList<>();
		a.add(153);
		a.add(22);
		a.add(23);
		a.add(99);
		
		a = new LargestTwoFinderImpl<Integer>().findTwoLargest(a);
		System.out.println(a);
	}
	
}


interface ILargestFinder<T extends Comparable<T>> {
	public Collection<T> findTwoLargest(Collection<T> input);
}

class LargestTwoFinderImpl<T extends Comparable<T>> implements ILargestFinder<T> {
	
	@SuppressWarnings("unchecked")
	public Collection<T> findTwoLargest(Collection<T> input) {
		Object[] acc = new Object[2];
		int top = 0;
		
		for(T e: input) {
			if (top < acc.length) {
				acc[top++] = e;
				continue;
			}
			
			for (int i=0; i<acc.length; i++) {
				if (e.compareTo((T) acc[i]) > 0) {
					acc[i] = e;
					break;
				}
			}
		}
		
		return (Collection<T>) Arrays.stream(acc).collect(Collectors.toList());
	}
}