package problems;

public class EightHouses {
	
	public static void main(String[] args) {
		
		int[] in = new int[] {1,1,1,0,1,1,1,1};
		int[] actual = cellComplete(in, 2);
		int[] expected = new int[] {0,0,0,0,0,1,1,0};
		for (int i = 0; i < actual.length; i++) {
			System.out.print(actual[i] + ", ");
			if(actual[i] != expected[i]) {
				System.out.println("failed");
				System.exit(1);
			}
		}
		System.out.println("\npassed");
	}
	
	private static int[] cellComplete(int[] states, int days) {
		while(days-- > 0) {
			int[] newStates = states.clone();
			for (int i = 0; i < states.length; i++) {
				Integer prev = null, next = null;
				if (i == 0) {
					prev = 0;
				} else if (i == states.length - 1) {
					next = 0;
				}
				
				if (prev == null) {
					prev = states[i-1];
				}
				
				if (next == null) {
					next = states[i+1];
				}
				
				if (prev == next) {
					newStates[i] = 0;
				} else {
					newStates[i] = 1;
				}
			}
			states = newStates;
		}
		return states;
	}

}

/*
 * 
 * 
 https://stackoverflow.com/questions/39171403/cell-complete-problems/56228550
 
 Example
 
 input1
 [1, 0, 0, 0, 0, 1, 0, 0], 1
 output: 0, 1, 0, 0, 1, 0, 1, 0
 
 input2
 [1, 1, 1, 0, 1, 1, 1, 1], 2
 output: 0, 0, 0, 0, 0, 1, 1, 0
 
 
 Python solution:

 def cellCompete(states, days):
    new = states[:] #get a copy of the array
    n = len(states)

    if n == 1: print [0] #when only 1 node, return [0]

    for _ in range(days):
        new[0] = states[1] #determine the edge nodes first
        new[n - 1] = states[n - 2]

        for i in range(1, n-1):
            new[i] = 1 - (states[i-1] == states[i+1]) #logic for the rest nodes
        states = new[:] #update the list for the next day

    return new
 * 
 */
