package problems;

import java.util.*; 

public class PrintAllPalindrome 
{ 
	// Driver program 
	public static void main(String[] args) 
	{ 
		String input = "adabdcaebdcebdcacaaaadbbcadabcbeabaadcbcaaddebdbddcbdacdbbaedbdaaecabdceddccbdeeddccdaabbabbdedaaabcdadbdabeacbeadbaddcbaacdbabcccbaceedbcccedbeecbccaecadccbdbdccbcbaacccbddcccbaedbacdbcaccdcaadcbaebebcceabbdcdeaabdbabadeaaaaedbdbcebcbddebccacacddebecabccbbdcbecbaeedcdacdcbdbebbacddddaabaedabbaaabaddcdaadcccdeebcabacdadbaacdccbeceddeebbbdbaaaaabaeecccaebdeabddacbedededebdebabdbcbdcbadbeeceecdcdbbdcbdbeeebcdcabdeeacabdeaedebbcaacdadaecbccbededceceabdcabdeabbcdecdedadcaebaababeedcaacdbdacbccdbcece"; 

		System.out.println("All possible palindrome" + 
				"partions for " + input 
				+ " are :"); 

		System.out.println(partition(input)); 
	} 

	public static List<List<String>> partition(String s) {
		// al: decrease and conquer
		// 0-i if palindrome, conquer the rest. Then connect
		List<List<String>> palindromeList = new ArrayList<>();
		for (int i = 0; i < s.length(); i++) {
			if (palindrome(s, i)) {
				List<List<String>> rest = partition(s.substring(i + 1));
				if (rest.size() != 0) {
					for (List<String> list : rest) {
						list.add(0, s.substring(0, i + 1));
						palindromeList.add(list);
					}
				} else {
					List<String> singlePalindrome = new ArrayList<>();
					singlePalindrome.add(s.substring(0, i+1));
					palindromeList.add(singlePalindrome);
				}

			}
		}
		return palindromeList;
	}

	static  boolean palindrome(String s, int i) {
		int low = 0, end = i;
		while (low < end) {
			if (s.charAt(low) != s.charAt(end)) return false;
			low++;
			end--;
		}
		return true;
	}
} 
