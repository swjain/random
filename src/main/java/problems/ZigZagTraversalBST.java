package problems;

import java.util.LinkedList;

public class ZigZagTraversalBST {

	public static void main(String[] args) {
		Node root = setupBst();
		LinkedList<Node> stack = new LinkedList<>();
		stack.add(root);
		traverse(stack, true);
	}

	private static void traverse(LinkedList<Node> stack, boolean rightFirst) {
		if (stack.isEmpty()) {
			return;
		}
		
		LinkedList<Node> nextStack = new LinkedList<>();

		while(!stack.isEmpty()) {
			Node node = stack.poll();
			System.out.println(node.value);
			addSafe(nextStack, rightFirst ? node.left : node.right);
			addSafe(nextStack, rightFirst ? node.right : node.left);
		}

		traverse(nextStack, !rightFirst);
	}

	private static void addSafe(LinkedList<Node> stack, Node node) {
		if (node != null)
			stack.push(node);
	}

	private static Node setupBst() {
		return Node.builder().value(1)
					.left(Node.builder().value(2)
							.left(Node.builder().value(4).build())
							.right(Node.builder().value(5).build())
							.build())
					.right(Node.builder().value(3)
							.left(Node.builder().value(6)
									.left(Node.builder().value(8).build())
									.build())
							.right(Node.builder().value(7)
									.right(Node.builder().value(9).build())
									.build())
							.build())
					.build();
	}

}

class Node {
	Node left;
	Node right;
	Integer value;

	public static NodeBuilder builder() {
		return new NodeBuilder();
	}

	public static class NodeBuilder {
		private Node node = new Node();

		public NodeBuilder value(Integer value) {
			node.value = value;
			return this;
		}

		public NodeBuilder left(Node left) {
			node.left = left;
			return this;
		}

		public NodeBuilder right(Node right) {
			node.right = right;
			return this;
		}

		public Node build() {
			return node;
		}
	}
}