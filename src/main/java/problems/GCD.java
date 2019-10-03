package problems;
public class GCD {
	
	public static void main(String[] args) {
		System.out.println(generalizeGcd(2, new int[] {4, 4, 8}));
	}

	private static int generalizeGcd(int num, int[] arr) {
		int factors = arr[0];
		for (int i=1; i < arr.length; i++) {
			factors = gcd(factors, arr[i]);
		}
		return factors;
	}

	private static int  gcd(int a, int b) {
		if(b == 0) {
			return a;
		}
		return gcd(b, a%b);
	}
    
}