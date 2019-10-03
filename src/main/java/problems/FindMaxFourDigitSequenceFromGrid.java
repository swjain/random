package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FindMaxFourDigitSequenceFromGrid {

	public static void main(String[] args) {
		int[][] A = {
				{9, 1, 1, 0, 7},
				{1, 0, 2, 1, 0},
				{1, 9, 1, 1, 0}
		};
		int result = new Solution().solution(A);
		System.out.println(result);

		A = new int[][] {
			{1, 1, 1},
			{1, 3, 4},
			{1, 4, 3}
		};
		result = new Solution().solution(A);
		System.out.println(result);

		A = new int[][] {
			{0, 1, 5, 0, 0}
		};
		result = new Solution().solution(A);
		System.out.println(result);
	}

	static class Solution {
		int[][] directions = new int[][] {{0, 1}, {-1,0}, {0, -1}, {1, 0}};

		public int solution(int[][] a) {
			int max = 0;
			for(int i=0; i<a.length; i++) {
				for(int j=0; j<a[i].length; j++) {
					int currMax = getMaxNeighbours(a, new LinkedList<>(), i, j);
					
					if (currMax > max) {
						max = currMax;
					}
				}
			}
			return max;
		}

		private int getMaxNeighbours(int[][] a, LinkedList<Node> visited, int i, int j) {
			int max = a[i][j];
			visited.add(new Node(i, j));
			
			if (visited.size() < 4) {
				int val = getMultiplier(visited.size()) * a[i][j];
				List<Integer> candidates = new ArrayList<>();
				
				for(int[] dir: directions) {
					int nextI = i + dir[0];
					int nextJ = j + dir[1];
					
					if(isWithinBounds(a, nextI, nextJ) && !visited.contains(new Node(nextI, nextJ))) {
						int nextMax = getMaxNeighbours(a, visited, nextI, nextJ);
						if (nextMax != -1) {
							candidates.add(val + nextMax);
						}
					}
				}
				
				if (candidates.isEmpty()) {
					max = -1;	
				} else {
					max = Collections.max(candidates);
				}
			}
			
			visited.removeLast();
			return max;
		}

		private int getMultiplier(int depth) {
			switch(depth) {
				case 1: return 1000;
				case 2: return 100;
				case 3: return 10;
			}
			return 0;
		}

		private boolean isWithinBounds(int[][] a, int i, int j) {
			return i >= 0 && i < a.length && j >= 0 && j < a[i].length;
		}

		class Node {
			int i;
			int j;

			Node(int i, int j) {
				this.i = i;
				this.j = j;
			}

			@Override
			public boolean equals(Object other) {
				return this.i == ((Node) other).i && this.j == ((Node) other).j;
			}
		}
	}
}
