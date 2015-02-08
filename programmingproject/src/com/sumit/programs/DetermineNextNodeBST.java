package com.sumit.programs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 
 * Given a Node inside a BST, determine the next node (in-order search)
 * The program doesn't make use of the parent pointer
 * 
 */
public class DetermineNextNodeBST {
	
	Tree tree = null;
	Node firstNode,secondNode;
	BufferedReader reader = null;
	void init() throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
		tree = new Tree(reader);
		tree.takeInput();		
	}
	
	void close() {
		if(reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void takeInput() {
		while(true) {
			try {
				System.out.println("Press any key to continue, press x to exit");
				String input = reader.readLine();
				if(input.trim().equalsIgnoreCase("x")) {
					System.out.println("Exiting...");
					break;
				} else {
					System.out.println("Please enter your node");
					int value = Integer.parseInt(reader.readLine());
					this.findNextNode(value);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}		
	}
	
	void findNextNode(int n) {
		Node node = tree.searchBFS(n);
		if(node == null) {
			System.out.println("Node doesn't exist");
		} else {
			if(node.right == null) {
				//Find 1st ancestor that is larger than the number
				List<Node> nodeAncestory = new ArrayList<Node>();
				this.getDepth(tree.root, node, nodeAncestory);
				for (Iterator<Node> iterator = nodeAncestory.iterator(); iterator.hasNext();) {
					Node temp = iterator.next();
					if(temp.value > node.value) {
						System.out.println("The next node is "+temp.value);
						break;
					}
				}
			} else {
				//Find extreme left of the right child (including right child).
				Node temp = node.right;
				while(temp.left != null) {
					temp = temp.left;
				}
				System.out.println("The next node is "+temp.value);
			}
		}
	}
	
	int getDepth(Node ptr, Node node,List<Node> nodeAncestory) {
		if(ptr == null) {
			return  -1;
		}
		nodeAncestory.add(0, ptr);
		int n = getDepth(ptr.left, node,nodeAncestory);
		if(n != -1) {
			return n+1;
		}
		n = getDepth(ptr.right, node,nodeAncestory);
		if(n != -1) {
			return n+1;
		}
		if(ptr.value == node.value) {
			return 0;
		}
		if(nodeAncestory.size() > 0) {
			nodeAncestory.remove(0); //Removing self node
		}
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		DetermineNextNodeBST dnn = new DetermineNextNodeBST();
		try {
			dnn.init();
			dnn.takeInput();
		} finally {
			dnn.close();
		}
	}
	

}
