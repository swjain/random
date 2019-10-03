package problems;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JamSolution {

	public static void main(String args[]) {

		List<Integer> lines = new ArrayList<>();
		try(Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)))) {
			while(in.hasNext()) {
				lines.add(in.nextInt());
			}
		}
		int T = lines.get(0);
		int i;
		for (i = 1; i <= T; i++) {
			Pair res = evaluateN(lines.get(i));
			System.out.print("Case #" + i + ": " + res.A + " " + res.B);
			if (i != T) {
				System.out.println();
			}
		}

	}

	private static boolean containsFour(int X){
		while (X > 0) {
			if (X % 10 == 4)
				return true;
			X = X / 10;
		}
		return false;
	}

	private static Pair evaluateN(int N) {
		int A = 0, B = 0, i = 0;
		boolean found = false;
		while (i++ < N && !found) {
			A = N / i;
			B = N - A;
			if (!containsFour(A) && !containsFour(B)) {
				found = true;
			}
		}
		return new Pair(A, B);
	}

	private static class Pair {

		public Pair(int a, int b) {
			A = a;
			B = b;
		}
		
		private int A;
		private int B;

	}

}	
