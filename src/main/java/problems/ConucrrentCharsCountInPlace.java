package problems;

public class ConucrrentCharsCountInPlace {
	
	public static void main(String[] args) {
		
		char a[] = " abc d d".toCharArray();
		int startPos = preCompress(a);
		System.out.println(a);
		compress(a, startPos);
		System.out.println(new String(a).trim());
		
	}
	
	private static int preCompress(char[] a) {
		int lastCharPos = 0;
		
		for(int i=0; i<a.length; i++) {
			if (a[i] == ' ') {
				for(int j=i; j>lastCharPos;) {
					a[j] = a[--j];
				}
				a[lastCharPos++] = ' ';
			}
		}
		
		return lastCharPos;
	}
	
	private static int compress(char[] a, int p) {
		if (p == a.length) {
			return p-1;
		}
		
		char currChar = a[p];
		int count = 1;
		
		while(p < a.length - 1 && a[p] == a[p + 1]) {
			p++;
			count++;
		}
		
		p = compress(a, ++p);
		
		a[p--] = (char) (count + 48);
		a[p--] = currChar;
		return p;
	}

}
