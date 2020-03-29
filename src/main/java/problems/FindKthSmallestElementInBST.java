package problems;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.Scanner;

//unsolved

public class FindKthSmallestElementInBST
{
	public static void main(String args[])
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new ByteArrayInputStream(
				(
						"1 " +
								"11 " + 
								"962 29 643 291 8 298 133 481 175 916 948 " +
								"6 " 
						).getBytes()));
		int t = sc.nextInt();
		while(t-- > 0)
		{
			int n = sc.nextInt();
			Node fhead = null;
			for(int i = 0; i < n; i++)
			{
				if(fhead == null)
					fhead = Node.builder().value(sc.nextInt()).build();
				else{
					insert(fhead, sc.nextInt());
				}
			}
			int k = sc.nextInt();
			int ans = kthSmallest(fhead, k);
			System.out.println(ans);
		}
	}

	private static Node insert(Node head, int a){
		if(head == null)
			return Node.builder().value(a).build();
		if(head.value >= a)
			head.left=insert(head.left, a);
		if(head.value < a)
			head.right=insert(head.right, a);
		return head;
	}

	private static int kthSmallest(Node root, int k) {
		
		System.out.println(root);
		int direction = 0;

		LinkedList<Node> parentStack = new LinkedList<>();
		Node next = getLeftMostLeaf(root, parentStack);

		while(--k > 0) {
			switch(direction) {
			case 0:
				next = parentStack.pop();
				break;
			case 1:
				next = next.right;
				while(next == null) {
					next = parentStack.pop().right;
				}
				next = getLeftMostLeaf(next, parentStack);
				break;
			}
			direction = ++direction % 2;
		}
		return next.value;
	}

	private static Node getLeftMostLeaf(Node root, LinkedList<Node> parentStack) {
		Node next = root;
		while(next.left != null) {
			parentStack.push(next);
			next = next.left;
		}
		return next;
	}

}

