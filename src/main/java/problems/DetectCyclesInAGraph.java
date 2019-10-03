package problems;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DetectCyclesInAGraph {

	public static void main(String args[]) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new ByteArrayInputStream(
				(
						"4 " +
								"29 4 " + 
								"6 21 17 12 2 11 9 11 " +
								"2 2 " + 
								"0 1 0 0 " + 
								"4 3 " + 
								"0 1 1 2 2 3 " + 
								"4 3 " + 
								"0 1 2 3 3 2 "
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
			dfs(i, list, vis, new LinkedList<>());            
		}
		return cycleDetected;
	}

	static void dfs(int src, ArrayList<ArrayList<Integer>> list, boolean vis[], LinkedList<Integer> currentTraversed) {
		if (!vis[src]) {
			currentTraversed.push(src);
			vis[src] = true;
			List<Integer> nextVertices = list.get(src);
			for(int next: nextVertices) {
				dfs(next, list, vis, currentTraversed);
				System.out.println(next);
				System.out.println(currentTraversed);
				if(currentTraversed.contains(next)) {
					cycleDetected = true;
				}
			}
			currentTraversed.pop();
		}
	}

}


