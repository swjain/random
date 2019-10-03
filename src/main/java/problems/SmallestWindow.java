package problems;

import java.util.*;

public class SmallestWindow {
	public static void main (String[] args) {
		String res = smallestWindow("timetopractice", "toc");
		System.out.println(res);
		
		res = smallestWindow("zoomlazapzo", "oza");
		System.out.println(res);
	}

	public static String smallestWindow(String s, String t){
		char[] a = s.toCharArray();
		char[] b = t.toCharArray();
		char[] smallest = new char[s.length() + 1];
		
		int i=0, j=0;
		CharConsumer charConsumer = new CharConsumer(b);
		
		for (; i<a.length; i++) {
			charConsumer.reset(b);
			
			for (j=i; j<a.length && charConsumer.canConsume(); j++) {
				charConsumer.consume(a[j]);
			}
			
			if (j==a.length && charConsumer.canConsume()) {
				break;
			} 
			
			if (j-i < smallest.length) {
				smallest = Arrays.copyOfRange(a, i, j);
			}
		}
		
		if (smallest.length == s.length() + 1) {
			return "-1";
		}
		
		return new String(smallest);
	}
	
	private static class CharConsumer  {
		HashMap<Character, Integer> charMap = new HashMap<>();
		
		CharConsumer(char[] characters) {
			reset(characters);
		}
		
		void reset(char[] characters) {
			charMap.clear();
			for(char x: characters) {
				if (charMap.containsKey(x)) {
					charMap.put(x, charMap.get(x) + 1);
				} else {
					charMap.put(x, 1);
				}
			}
		}
		
		void consume(char x) {
			if (charMap.containsKey(x)) {
				int occurences = charMap.get(x);
				if (occurences > 1) { 
					charMap.put(x, occurences - 1);
				} else {
					charMap.remove(x);
				};
			}
		}
		
		boolean canConsume() {
			return !charMap.isEmpty();
		}
	}
}