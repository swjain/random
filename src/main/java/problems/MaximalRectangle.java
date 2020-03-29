package problems;

import java.text.MessageFormat;
import java.util.Arrays;

public class MaximalRectangle {

	public static void main(String args[]) {
		int[][] a = {
				{1, 1, 1},
				{0, 1, 1},
				{1, 0, 0}
		};
		System.out.println(maximalRectangle(a));

		a = new int[][]{
			{1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1},
			{1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
			{1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0},
			{1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1},
			{1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
			{1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1}		
		};
		System.out.println(maximalRectangle(a));

		a = new int[][]{
			{0, 1},
			{1, 0},
		};
		System.out.println(maximalRectangle(a));

		a = new int[][]{
			{1, 1}
		};
		System.out.println(maximalRectangle(a));

		a = new int[][]{
			{1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 0},
			{1, 1, 1, 1, 1, 1, 1, 0},
			{1, 1, 1, 1, 1, 0, 0, 0},
			{0, 1, 1, 1, 1, 0, 0, 0}
		};
		System.out.println(maximalRectangle(a));

	}

	public static int maximalRectangle(int[][] a) {
		// aggregate consecutive 1s from both left and right for each node.
		Node[][] b = new Node[a.length][];
		boolean hasConsecutiveOnes = false;
		boolean hasOne = false;

		for(int i=0; i<a.length; i++) {
			b[i] = new Node[a[i].length];
			for(int j=0; j<a[i].length; j++) {
				Node node = new Node();

				if (a[i][j] == 1) {
					if (i>0 && a[i-1][j] == 1) {
						node.tCount = b[i-1][j].tCount + 1;

						if (!hasConsecutiveOnes) hasConsecutiveOnes = true;
					} else {
						node.tCount = 1;
					}

					if (!hasOne) hasOne = true;
				}

				if (a[i][j] == 1) {
					if(j>0 && a[i][j-1] == 1) {
						node.lCount = b[i][j-1].lCount + 1;

						if (!hasConsecutiveOnes) hasConsecutiveOnes = true;
					} else {
						node.lCount = 1;
					}

					if (!hasOne) hasOne = true;
				}

				b[i][j] = node;
			}
		}

		if (!hasConsecutiveOnes) {
			return hasOne ? 1 : 0;
		}

		int[][] max = new int[a.length][];
		// traverse again the matrix, look at only the corners to confirm if it's a rectangle.
		for(int i=0; i<b.length; i++) {
			max[i] = new int[b[i].length];
			for(int j=0; j<b[i].length; j++) {

				for(int m=b.length-1; m>=i; m--) {
					for(int n=b[i].length -1; n>=j; n--) {
						findMax(b, max, m, n, i, j);
					}
				}

				for(int n=b[i].length -1; n>=j; n--) {
					for(int m=b.length-1; m>=i; m--) {
						findMax(b, max, m, n, i, j);
					}
				}
			}
		}

		return Arrays.stream(max).map(x -> Arrays.stream(x).max().getAsInt())
				.max((x, y) -> Integer.compare(x, y)).get();
	}

	private static void findMax(Node[][] b, int[][] max, int m, int n, int i, int j) {
		Node tRight = b[i][n];
		Node bRight = b[m][n];
		Node bLeft = b[m][j];

		//check corners
		if (tRight.lCount > n-j && tRight.tCount > 0
				&& bRight.lCount > n-j && bRight.tCount > m-i
				&& bLeft.lCount > 0 && bLeft.tCount > m-i) {

			outer: {
			// check the boundaries
			for(int k=i; k<=m; k++) if (b[k][n].lCount <= n-j) break outer;
			for(int k=j; k<=n; k++) if (b[m][k].tCount <= m-i) break outer;

			int candidate = (m-i + 1) * (n-j + 1);
			max[i][j] = Math.max(max[i][j], candidate);
		}
		}
	}

	private static class Node {
		int lCount = 0; // consecutive ones from left of the node to itself.
		int tCount = 0; // consecutive ones from top of the node to itself.

		public String toString() {
			return MessageFormat.format("[{0},{1}]", lCount, tCount);
		}
	}

}
