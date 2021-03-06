package problems;

import java.util.*;

public class FindLongestConsecutiveSubsequence {

	public static void main(String[] args) {
		int[] a = { 153, 48, 103, 104, 105, 133, 90, 154, 167, 146, 168 };
		System.out.println(findLongestConseqSubseq(a));
	}

	static int findLongestConseqSubseq(int[] a) {
		a = Arrays.stream(a).sorted().distinct().toArray();
		int longestConseqSubseqCount = 0;

		for(int i=1; i<a.length; i++) {

			int currConseqSubseqCount = 1;
			int diff;
			do {
				diff = a[i] - a[i-1];
				if (diff == 1) currConseqSubseqCount++;
				i++;
			} while(i<a.length && diff<2);

			if (currConseqSubseqCount > longestConseqSubseqCount) {
				longestConseqSubseqCount = currConseqSubseqCount;
			}
		}

		return longestConseqSubseqCount;
	}

}
