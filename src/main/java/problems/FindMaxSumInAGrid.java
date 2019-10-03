package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindMaxSumInAGrid {

	public static void main(String args[]) {
		int[][] grid = {
			{3, 1, 7, 4, 2},
			{2, 1, 3, 1, 1},
			{1, 2, 2, 1, 8},
			{2, 2, 1, 5, 3},
			{2, 1, 4, 4, 4},
			{5, 2, 7, 5, 1}
		};
		
		findMaxSum(grid);
	}

	private static int findMaxSum(int[][] a) {
		if (a.length > 0) {
			
			Path[][] maxPaths = new Path[a.length][];
			for(int i=0; i<a.length; i++) {
				maxPaths[i] =  new Path[a[i].length];
			}
			
			int directions[][] = {{1, -1}, {1, 0}, {1, 1}};	

			List<Path> possiblePaths = new ArrayList<>();
			for(int j=0; j<a[0].length; j++) {
				computeCost(a, maxPaths, directions, 0, j);
				possiblePaths.add(maxPaths[0][j]);
			}

			Path maxPath = Collections.max(possiblePaths);
			printMaxPathAndCost(a, maxPath);
			return maxPath.getCost();
		}
		
		return 0;
	}

	private static void computeCost(int[][] a, Path[][] maxPaths, int[][] directions, int i, int j) {
		if (maxPaths[i][j] == null) {
			
			List<Path> nextPaths = new ArrayList<>();
			for(int[] dir: directions) {
				int nextI = i + dir[0];
				int nextJ = j + dir[1];

				if (nextI>=0 && nextI<a.length && nextJ>=0 && nextJ<a[nextI].length) {
					computeCost(a, maxPaths, directions, nextI, nextJ);
					nextPaths.add(maxPaths[nextI][nextJ]);
				}
			}

			Path path = new Path();
			path.add(i, j, a[i][j]);
			if (!nextPaths.isEmpty()) {
				Path maxNextPath = Collections.max(nextPaths);
				path.append(maxNextPath);
			}
			maxPaths[i][j] = path;
		}
	}

	private static void printMaxPathAndCost(int[][] a, Path p) {
		StringBuilder sb = new StringBuilder();
		for(Node n: p.getNodes()) {
			sb.append(a[n.i][n.j] + "+");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append("=" + p.getCost());
		System.out.println(sb.toString());
	}

	private static class Path implements Comparable<Path> {
		private List<Node> nodes = new ArrayList<>();
		private int cost = 0;

		public void add(int i, int j, int cost) {
			this.nodes.add(new Node(i, j));
			this.cost += cost;
		}

		public void append(Path nextPath) {
			this.nodes.addAll(nextPath.getNodes());
			this.cost += nextPath.getCost();
		}

		public List<Node> getNodes() {
			return this.nodes;
		}

		public int getCost() {
			return this.cost;
		}

		@Override
		public int compareTo(Path other) {
			return this.cost - other.cost;
		}
	}

	private static class Node {
		int i;
		int j;

		Node(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}
}