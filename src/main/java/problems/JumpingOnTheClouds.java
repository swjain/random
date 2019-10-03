package problems;

public class JumpingOnTheClouds {

	public static void main(String[] args) {

		int[] a = {0, 0, 1, 0, 0, 1, 0};
		System.out.println(jumpingOnClouds(a));

	}

	static int jumpingOnClouds(int[] a) {
		return countJumps(a, 0);
	}

	static int countJumps(int[] a, int i) {
		if(i == a.length-1) {
			return 0;
		}

		int path1 = i < a.length-1 && a[i+1] == 0 ? countJumps(a, i+1) : Integer.MAX_VALUE;
		int path2 = i < a.length-2 && a[i+2] == 0 ? countJumps(a, i+2) : Integer.MAX_VALUE;
		return Math.min(path1, path2) + 1;
	}

}
