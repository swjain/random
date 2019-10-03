package problems;

public class TraverseLinkedList {

	public static void main(String[] args) {
		int[] A = { 1, 4, 5, 3, 2, -1};
		int result = new Solution().solution(A);
		System.out.println(result);

	}

}

class Solution {

	public int solution(int[] A) {
		if(A[0] < 0) {
			return 0;
		}
		return traverse(A, 0);

	}

	private int traverse(int[] A, int x) {
		int next = A[x];
		if (next < 0) {
			return 1;
		}
		return 1 + traverse(A, next);
	}

}

