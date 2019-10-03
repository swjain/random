package problems;

public class HeapSort {

	public static void main(String[] args) {

		int[] a = {962, 29, 643, 291, 8, 298, 133, 481, 175, 916, 948};

		int n = a.length;
		for (int i = n/2-1; i >= 0; i--) {
			heapify(a, n, i);
		}

		n = a.length;
		do {
			n--;
			swap(a, 0, n);
			heapify(a, n, 0);
		} while (n>0);

		for(int x: a)
			System.out.println(x);

	}

	/*
	 * @param heap
	 * @param n heap size
	 */
	private static void heapify(int[] a, int n, int i) {
		int largest = i; 
		int l = 2*i + 1; 
		int r = 2*i + 2; 

		if (l < n && a[l] > a[largest]) 
			largest = l; 

		if (r < n && a[r] > a[largest]) 
			largest = r; 

		if (largest != i) { 
			swap(a, i, largest);
			heapify(a, n, largest); 
		} 
	}

	private static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

}