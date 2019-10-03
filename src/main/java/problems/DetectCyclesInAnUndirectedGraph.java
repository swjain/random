package problems;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DetectCyclesInAnUndirectedGraph {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new ByteArrayInputStream(
				(
						"2 " +
								"29 4 " + 
								"6 21 17 12 2 11 9 11 " +
								"68 24 " + 
								"6 48 15 47 21 55 4 10 32 3 10 4 20 40 41 65 63 39 30 41 21 45 7 49 60 0 46 40 24 13 27 39 2 42 26 31 37 30 49 9 34 59 21 62 39 62 0 43 " 
						).getBytes()));
		int t =sc.nextInt();
		while(t-- > 0)
		{
			ArrayList<ArrayList<Integer>> list = new ArrayList<>();
			int nov = sc.nextInt();
			int edg = sc.nextInt();
			for(int i = 0; i < nov+1; i++)
				list.add(i, new ArrayList<Integer>());
			for(int i = 1; i <= edg; i++)
			{
				int u = sc.nextInt();
				int v = sc.nextInt();
				list.get(u).add(v);
				list.get(v).add(u);
			}
			if(isCyclic(list, nov) == true)
				System.out.println("1");
			else System.out.println("0");
		}
	}

	private static boolean cycleDetected = false;

	static boolean isCyclic(ArrayList<ArrayList<Integer>> list, int v) {
		cycleDetected = false;
		boolean[] vis = new boolean[list.size()];
		for(int i=0; i<list.size(); i++) {
			dfs(i, list, vis, new LinkedList<>(), -1);            
		}
		return cycleDetected;
	}

	static void dfs(int src, ArrayList<ArrayList<Integer>> list, boolean vis[], LinkedList<Integer> currentTraversed, int parent) {
		if (!vis[src]) {
			currentTraversed.push(src);
			vis[src] = true;
			List<Integer> nextVertices = list.get(src);
			for(int next: nextVertices) {
				if (next != -1 && next != parent) {
					if(currentTraversed.contains(next)) {
						cycleDetected = true;
					}
					dfs(next, list, vis, currentTraversed, src);
				}
			}
			currentTraversed.pop();
		}
	}

}


