package problems;

import java.util.*;

public class DemolitionRobot {   

	public static void main(String[] args) {
		DemolitionRobot solution = new DemolitionRobot();
		List<List<Integer>> lot = new ArrayList<>();
		lot.add(Arrays.asList(1,0,0));
		lot.add(Arrays.asList(1,0,0));
		lot.add(Arrays.asList(1,9,1));
		int result = solution.removeObstacle(3, 3, lot);
		System.out.println(result);
	}


	public int removeObstacle(int numRows, int numColumns, List<List<Integer>> lot) {
		int[][] grid = new int[numRows][numColumns];
		boolean[][] visited = new boolean[numRows][numColumns];

		for(int i = 0  ;i < lot.size(); i++) {
			List<Integer> sub = lot.get(i);
			for(int j = 0 ; j < sub.size(); j++) {
				grid[i][j] = lot.get(i).get(j);
			}
		}

		int count = 0;

		int[][] directions = new int[][]{{1,0}, {-1,0}, {0, 1}, {0, -1}};

		Queue<int[]> queue = new LinkedList<>();
		queue.offer(new int[]{0,0});
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i = 0 ;i < size; i++) {
				int[] current = queue.poll();
				int row = current[0];
				int column = current[1];

				if(row < 0 || column < 0 || row >= numRows || column>= numColumns || grid[row][column] == 0 || visited[row][column]) {
					continue;
				}

				visited[row][column] = true;
				if(grid[row][column] == 9) {
					return count;
				}

				for(int[] dir : directions) {
					int nr = dir[0] + row;
					int nc = dir[1] + column;
					queue.offer(new int[]{nr, nc});
				}
			}
			count++;
		}
		return -1;
	}
}

