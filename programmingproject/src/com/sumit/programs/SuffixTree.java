package com.sumit.programs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Searching pattern inside array of words. Words are delimited by space. The search is case insensitive
 * 
 * A suffix tree T for string S is a rooted directed tree whose edge are labeled with non-empty substrings of S.
 * Each leaf corresponds to a suffix of S in the sense that the concatenation of edge-labels on the unique path from the root to the leaf, spells out a suffix.
 * Each internal node, other than the root, has at least 2 children.
 * No two out-edge of a node can have edge-labels with the same first character.
 * E.g. : Used in undefined boundaries (like genes matching)
 * Its another type of compressed trie.
 * No. of suffixes = no. of nodes
 * Size of tree = no. of leaves + no. of internal nodes
 *              ~ n + (n-1)
 *              = O(n^2)
 * 
 * If the pattern appears in the document, there is some suffix whose prefix is that pattern.
 * Build complexity = O(n*n) = O(n^2)
 * $ : Terminal character. It doesn't appear in tree T and is lexically smallest.
 * 
 * Its uses :
 * 1. Find if a string 'S' is a substring of T.
 *    Every substring is a prefix of some suffix of T. Follow the 'S' in trie/suffix tree and don't fall off.
 * 2. Check if string 'S' is a suffix of T.
 *    Follow the 's' in the trie and end up with $.
 * 3. Find the count of number of times a string 'S' occurs as a substring of T.
 *    Follow the 's' in trie. IF we fall off the trie, ans = 0.
 *    If we don't, we may end up at node 'n'. In this case, ans = no. of leaf nodes subrooted at 'n'
 * 4. Find the longest repeated substring of T.
 * 	  Find the deepest node with more than 1 child.
 *
 */
public class SuffixTree {
	
	class Node {
		String character;
		Set<Node> childs = new HashSet<Node>();
		Node(String character) {
			this.character = character;
		}
		@Override
		public String toString() {
			return character;
		}
		
		@Override
		public int hashCode() {
			return character.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj != null && obj instanceof Node) {
				return character.equals(((Node)obj).character);
			} else {
				return false;
			}
		}
		
	}
	
	private Node root = new Node("");
	
	public void initializeTrie(String document) {
		for (int i = document.length() -1; i >= 0; i--) {
			String docWord = document.toLowerCase().substring(i);
			//System.out.println("Processing for "+docWords[i]+" "+i);
			Node parentNode = root;
			int startIndex = 0;
			while(startIndex < docWord.length()) {
				AtomicInteger intRef = new AtomicInteger(0);
				Node node = this.childNodeWithCharacter(parentNode, docWord, startIndex, intRef);
				startIndex += intRef.get();
				if(node == null) {
					//No child contains the prefix of the pattern. Create a new child
					Node child = new Node(docWord.substring(startIndex));
					child.childs.add(new Node("$")); //Add terminator character to child
					parentNode.childs.add(child);
					break;
				}
				if(node.character.length() == intRef.get()) {
					//The child node is exausted but is a prefix of the pattern. Test childs of this child node
					parentNode = node;
				} else {
					//Need to break the child into 2 parts
					Node childPart1 = new Node(node.character.substring(0, intRef.get()));
					parentNode.childs.remove(node);
					//Part2
					node.character = node.character.substring(intRef.get());
					childPart1.childs.add(node);
					//New child
					Node newChild = new Node(docWord.substring(startIndex));
					newChild.childs.add(new Node("$")); //Add terminator character to child
					childPart1.childs.add(newChild);
					parentNode.childs.add(childPart1);
					break;
				}
			}
		}
	}
	
	Node childNodeWithCharacter(Node parent,String ch, int startIndex, AtomicInteger mutableInteger) {
		Set<Node> childs = parent.childs;
		if(childs == null) {
			return null;
		} else {
			for (Iterator<Node> iterator = childs.iterator(); iterator.hasNext();) {
				Node node =  iterator.next();
				int indexNode  = 0, indexChar = startIndex;
				while(indexNode < node.character.length() && indexChar < ch.length()) {
					if(node.character.charAt(indexNode) == ch.charAt(indexChar) && node.character.charAt(indexNode) != '$') {
						indexNode++;indexChar++;
					} else {
						break;
					}
				}
				if(indexNode != 0) {
					//The prefixes matched
					mutableInteger.set(indexNode);
					return node;
				}
			}
			return null;
		}
	}
	
	void printTrie() {
		TriePrinter.printNode(root);
	}
	
	void searchWordPattern(String pattern) {
		Node parent = this.root;
		AtomicInteger indexRef = new AtomicInteger(0);
		int startIndex = 0;
		while (startIndex < pattern.length()) {
			Node child = this.childNodeWithCharacter(parent, pattern,startIndex, indexRef);
			startIndex += indexRef.get();
			if(child != null) {
				parent = child;
				continue;
			} else {
				System.out.println("The pattern doesnot exist in the document");
				return;
			}
		}
		if(parent != null) {
				System.out.println("The pattern exists in the document");
		}
	}
	
	public static void main(String[] args) {
		String document = "bearbellbidbullbuysellstockstop";
		SuffixTree trie = new SuffixTree();
		trie.initializeTrie(document);
		//trie.printTrie();
		trie.searchWordPattern("buy");
		trie.searchWordPattern("buyer");
		trie.searchWordPattern("abc");
		trie.searchWordPattern("bul");
	}
	
	//Bug in TriePrinter. Doesn't print correctly
	static class TriePrinter {

	    public static void printNode(Node root) {
	        int maxLevel = TriePrinter.maxLevel(root);

	        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	    }

	    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
	        if (nodes.isEmpty() || TriePrinter.isAllElementsNull(nodes))
	            return;

	        int floor = maxLevel - level;
	        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
	        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
	        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

	        TriePrinter.printWhitespaces(firstSpaces);

	        List<Node> newNodes = new ArrayList<Node>();
	        for (Node node : nodes) {
	            if (node != null) {
	                System.out.print(node.character);
	                newNodes.addAll(node.childs);
	            } else {
	                newNodes.add(null);
	                newNodes.add(null);
	                System.out.print(" ");
	            }

	            TriePrinter.printWhitespaces(betweenSpaces);
	        }
	        System.out.println("");

	        for (int i = 1; i <= endgeLines; i++) {
	            for (int j = 0; j < nodes.size(); j++) {
	                TriePrinter.printWhitespaces(firstSpaces - i);
	                if (nodes.get(j) == null) {
	                    TriePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
	                    continue;
	                }

	                if (nodes.get(j).childs != null)
	                    System.out.print("|");
	                else
	                    TriePrinter.printWhitespaces(1);

	                TriePrinter.printWhitespaces(i + i - 1);

	                if (nodes.get(j).childs != null)
	                    System.out.print("\\");
	                else
	                    TriePrinter.printWhitespaces(1);

	                TriePrinter.printWhitespaces(endgeLines + endgeLines - i);
	            }

	            System.out.println("");
	        }

	        printNodeInternal(newNodes, level + 1, maxLevel);
	    }

	    private static void printWhitespaces(int count) {
	        for (int i = 0; i < count; i++)
	            System.out.print(" ");
	    }

	    private static int maxLevel(Node node) {
	        if (node == null)
	            return 0;
	        int maxLevel = -1;
	        for (Iterator<Node> iterator = node.childs.iterator(); iterator.hasNext();) {
				Node child = iterator.next();
				int level = TriePrinter.maxLevel(child);
				if(level > maxLevel) {
					maxLevel = level;
				}
				
			}
	        return maxLevel + 1;
	    }

	    private static <T> boolean isAllElementsNull(List<T> list) {
	        for (Object object : list) {
	            if (object != null)
	                return false;
	        }

	        return true;
	    }

	}

}
