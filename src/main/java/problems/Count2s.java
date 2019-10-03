package problems;

public class Count2s {

	public static void main(String[] args) {
		System.out.println(count2s(200));
	}

	public static long count2s(long num) {

		int degree = getDegree(num);

		if (degree == 0) {
			return num > 1 ? 1 : 0;
		}

		long baseNum = (long) Math.pow(10, degree);
		long remainder = num - baseNum;
		long count = (baseNum/10 * degree) + count2s(remainder);
		switch ((int) getStartDigit(num, degree)) {
		case 2: count += (num % (2 * baseNum)) + 1; break;
		case 3: count += num - (num % baseNum) - remainder - 1; break;
		}
		return count;

	}

	private static int getDegree(long num) {
		int degree = 0;
		do {
			num = num / 10;
			degree++;
		} while(num != 0);
		return degree-1;
	}

	private static long getStartDigit(long num, int degree) {
		while(degree-- > 0) {
			num = num / 10;
		}
		return num;
	}

}
