package problems;

import java.util.*;

public class NumberFollowingIAndDPattern {

	public static void main (String[] args) {
		System.out.println(numberFollowingIAndDPattern("D"));
		System.out.println(numberFollowingIAndDPattern("I"));
		System.out.println(numberFollowingIAndDPattern("DD"));
		System.out.println(numberFollowingIAndDPattern("IIDDD"));
		System.out.println(numberFollowingIAndDPattern("DDIDDIID"));
	}
	
	public static int numberFollowingIAndDPattern(String str) {
		
		char[] a = str.toCharArray();
		LinkedList<Integer> r = new LinkedList<>();
		
		for(int i=0; i<=a.length; i++) {
			r.add(i+1);
		}
				
		int decreaseSeqStartPos = 0;
		for(int i=0; i<a.length; i++) {
			if (a[i] == 'D') {
				r.add(decreaseSeqStartPos, r.remove(i+1));
				continue;
			}
			decreaseSeqStartPos = i+1;
		}
		
		char[] digitsAsChar = new char[r.size()];
		for (int i = 0; i < digitsAsChar.length; i++) {
			digitsAsChar[i] = (char) (r.get(i).intValue() + 48);
		}
		
		return Integer.parseInt(new String(digitsAsChar));
	}
	
}
