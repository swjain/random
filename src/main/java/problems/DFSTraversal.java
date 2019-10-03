package problems;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DFSTraversal {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new ByteArrayInputStream(
				("1 "
				+ "8 28 "
				+ "0 4 2 0 1 3 7 0 5 7 6 3 3 4 7 4 7 4 2 6 1 3 7 6 3 4 5 6 3 1 7 5 4 3 0 5 7 1 0 3 5 7 1 7 7 1 6 7 0 2 6 7 7 6 1 0")
				.getBytes()));
		int t =sc.nextInt();
		while(t-- > 0) {
			ArrayList<ArrayList<Integer>> list = new ArrayList<>();
			int nov = sc.nextInt();
			int edg = sc.nextInt();
			for(int i = 0; i < nov; i++)
				list.add(i, new ArrayList<Integer>());

			for(int i = 1; i <= edg; i++) {
				int u = sc.nextInt();
				int v = sc.nextInt();
				list.get(u).add(v);
				list.get(v).add(u);
			}
			boolean vis[] = new boolean[nov];
			for(int i = 0; i < nov; i++)
				vis[i] = false;
			dfs(0, list, vis);
			System.out.println();
		}
	}

	static void dfs(int src, ArrayList<ArrayList<Integer>> list, boolean vis[]) {
		dfsRecursive(src, list, vis, false);
	}
	
	static void dfsRecursive(int src, ArrayList<ArrayList<Integer>> list, boolean vis[], boolean isFirstIteration) {
		if (!vis[src]) {
			System.out.print(isFirstIteration ? src : " " + src);
			vis[src] = true;
			List<Integer> nextVertices = list.get(src);
			for(int next: nextVertices) {
				dfsRecursive(next, list, vis, false);
			}
		}
	}

}
