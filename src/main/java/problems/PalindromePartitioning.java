package problems;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PalindromePartitioning {

	public static void main(String args[]) {
		System.out.println(minCut("abadsaba"));
		System.out.println(minCut("aaaac"));
		System.out.println(minCut("baanana"));
	}
	
	public static int minCut(String s) {
		if (s == null || s.isEmpty()) {
			return 0;
		}
		
		LinkedList<Range> maximalsFromLeft = findMaximalPalindromes(s);
		LinkedList<Range> maximalsFromRight = findMaximalPalindromes(new StringBuilder(s).reverse().toString());
		
		int minCutFromLeft = maximalsFromLeft.stream().collect(Collectors.counting()).intValue() - 1;
		int minCutFromRight = maximalsFromRight.stream().collect(Collectors.counting()).intValue() - 1;
		
		return Math.min(minCutFromLeft, minCutFromRight);
	}
	
	public static String longestPalindrome(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		
		LinkedList<Range> maximalsFromLeft = findMaximalPalindromes(s);
		LinkedList<Range> maximalsFromRight = findMaximalPalindromes(new StringBuilder(s).reverse().toString());
		return Stream.concat(maximalsFromLeft.stream(), maximalsFromRight.stream())
	            .max((x, y) -> (x.end - x.start) - (y.end - y.start)).get().toString();
		
	}
	
	private static LinkedList<Range> findMaximalPalindromes(String s) {
		
		char[] a = s.toCharArray();
		LinkedList<Range> maximals = new LinkedList<>();
		merge(maximals, a, 0);
		return maximals;
		
	}

	private static void merge(LinkedList<Range> stack, char[] a, int i) {
		int stackIdx = stack.size();

		while (stackIdx-- > 0) {
			Range r1 = new Range(a, stack.get(stackIdx).start, stack.peekFirst().end);
			Range r2 = new Range(a, i, i + 1 + r1.end - r1.start);
			
			while(r2.end >= i) {
				if (r2.end < a.length && isPalindromic(a, r1.start, r2.end)) {
					while (stackIdx-- >= 0) stack.pop();
					stack.push(new Range(a, r1.start, r2.end));
					merge(stack, a, r2.end + 1);
					return;
				};
				r2.end--;
			}
		}

		if (i < a.length) {
			stack.push(new Range(a, i, i));
			merge(stack, a, ++i);
		}
	}

	private static boolean isPalindromic(char[] a, int start, int end) {
		if (start > end) return false;
		
		for(int i=start, j=end; i<j; i++, j--) {
			if (a[i] != a[j]) return false;
		}
		return true;
	}

	static class Range {
		char[] a;
		int start;
		int end;

		public Range(char[] a, int start, int end) {
			this.a = a;
			this.start = start;
			this.end = end;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			for(int i=start; i<=end; i++) sb.append(a[i]);
			return MessageFormat.format("{0}", sb.toString());
//			return MessageFormat.format("[{0},{1}]", start, end);
		}
	}

}
