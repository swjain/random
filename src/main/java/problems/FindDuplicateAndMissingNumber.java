package problems;

public class FindDuplicateAndMissingNumber {

	public static void main(String[] args) {

		Integer[] a= new Integer[]{5, 5, 4, 2, 3, 1};

		for (int i=0; i<a.length; i++) {
			for (int j=0; j<i; j++) {
				if (a[i] < a[j]) {
					int temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}

		Integer duplicateIndex = null;
		for (int i =0; i<a.length; i++) {
			if (a[i] == a[i+1]) {
				duplicateIndex = i;
				break;
			}
		}

		Integer missing;
		if (a[duplicateIndex - 1] < a[duplicateIndex] - 1) {
			missing = a[duplicateIndex] - 1;
		} else {
			missing = a[duplicateIndex] + 1;
		}

		System.out.println("Duplicate: " + a[duplicateIndex]);
		System.out.println("Missing: " + missing);
		
	}
}
