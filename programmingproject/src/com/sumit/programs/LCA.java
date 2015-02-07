package com.sumit.programs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Given a binary tree and 2 nodes, determine the least common ancestor
 *
 *
 */
public class LCA {
	
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
	
	boolean inputForSearch() throws IOException {
		System.out.println("Enter your nodes");
		boolean valid = true;
		String[] nodes = reader.readLine().split(" ");
		firstNode = tree.searchNode(Integer.parseInt(nodes[0]), true);
		secondNode = tree.searchNode(Integer.parseInt(nodes[1]), true);
		if(firstNode == null || secondNode == null) {
			System.out.println("Node not found in tree");
			valid = false;
		}
		return valid;
	}
	
	
	void search() throws IOException {
		int input = 0;
		while(input != 1) {
			boolean isValid = this.inputForSearch();
			if(isValid) {
				this.searchLCA();
			}
			System.out.println("Press any key to search more, 1 for exit");
			try {
				input = Integer.parseInt(reader.readLine());
			} catch(NumberFormatException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Exiting LCA...");
	}
	
	//This is the actual function for performing LCA
	void searchLCA() {
		List<Node> firstNodeAncestory = new ArrayList<Node>();
		int firstDepth = this.getDepth(this.tree.root, this.firstNode,firstNodeAncestory);
		List<Node> secondNodeAncestory = new ArrayList<Node>();
		int secondDepth = this.getDepth(this.tree.root, this.secondNode,secondNodeAncestory);
		int delta = 0;
		List<Node> nodeToBubbleUpForLevelAncestory = null;
		List<Node> nodeToHoldLevelAncestory = null;
		if(firstDepth > secondDepth) {
			delta = firstDepth - secondDepth;
			nodeToBubbleUpForLevelAncestory = firstNodeAncestory;
			nodeToHoldLevelAncestory = secondNodeAncestory;
		} else {
			delta = secondDepth - firstDepth;
			nodeToBubbleUpForLevelAncestory = secondNodeAncestory;
			nodeToHoldLevelAncestory = firstNodeAncestory;
		}
		Iterator<Node> nodeToBubbleUpForLevelAncestoryItr = nodeToBubbleUpForLevelAncestory.iterator();
		for (int i = 0; i < delta; i++) {
			nodeToBubbleUpForLevelAncestoryItr.next();
		}
		Iterator<Node> nodeToHoldLevelAncestoryItr = nodeToHoldLevelAncestory.iterator();
		while(nodeToBubbleUpForLevelAncestoryItr.hasNext() && nodeToHoldLevelAncestoryItr.hasNext()) {
			Node node1 = nodeToBubbleUpForLevelAncestoryItr.next();
			Node node2 = nodeToHoldLevelAncestoryItr.next();
			if(node1.value == node2.value) {
				System.out.println("LCA : "+node1.value);
				break;
			}
		}
		System.out.println("Done");
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
			nodeAncestory.remove(0);
		}
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		LCA lca = new LCA();
		try {
			lca.init();
			lca.search();
		} finally {
			lca.close();
		}
	}

}

