package problems;

public class QuickSort {

	public static void main(String[] args) {
		int[] a = {962, 29, 643, 291, 8, 298, 133, 481, 175, 916, 948};
		
		sort(a, 0, a.length-1);
		
		for(int x: a)
			System.out.println(x);
	}
	
	private static void sort(int[] a, int l, int h) {
		if(l<h) {
			 int j = partition(a, l, h);
	        sort(a, l, j-1);
	        sort(a, j+1, h);
		}
    }

    private static int partition(int[] a, int l, int h) {
        int i=l, j=h;
        
        while(i<j) {
            while(i<j && a[l] > a[i]) {
                i++;
            }

            while(i<j && a[l] < a[j]) {
                j--;
            }

            if (i<j) {
                swap(a, i, j);
            }
        }
        
        if(j>l && a[l] < a[j]) {
            swap(a, l, j);
        }
        
        return j;
    }
    
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

}
