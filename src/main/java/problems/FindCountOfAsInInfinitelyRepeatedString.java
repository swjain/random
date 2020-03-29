package problems;

import java.util.stream.IntStream;

public class FindCountOfAsInInfinitelyRepeatedString {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	static long repeatedString(String s, long n) {

		char[] a = s.toCharArray();
		long count = IntStream.range(0, a.length).filter(i -> a[i] == 'a').count();
		count = (n/a.length) * count;

		for(int i=0; i<(n % a.length); i++) {
			if (a[i] == 'a') count++;
		}

		return count;
	}

}
