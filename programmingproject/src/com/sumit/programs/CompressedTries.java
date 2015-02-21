package com.sumit.programs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Searching word inside array of words. Words are delimited by space. The search is case insensitive
 *
 */
public class CompressedTries {
	
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
		String[] docWords = document.toLowerCase().split(" ");
		for (int i = 0; i < docWords.length; i++) {
			//System.out.println("Processing for "+docWords[i]+" "+i);
			Node parentNode = root;
			int startIndex = 0;
			while(startIndex < docWords[i].length()) {
				AtomicInteger intRef = new AtomicInteger(0);
				Node node = this.childNodeWithCharacter(parentNode, docWords[i], startIndex, intRef);
				startIndex += intRef.get();
				if(node == null) {
					//No child contains the prefix of the word. Create a new child
					Node child = new Node(docWords[i].substring(startIndex));
					child.childs.add(new Node("$")); //Add terminator character to child
					parentNode.childs.add(child);
					break;
				}
				if(node.character.length() == intRef.get()) {
					//The child node is exausted but is a prefix of the word. Test childs of this child node
					parentNode = node;
				} else {
					//Need to break the child into 2 parts
					Node childPart1 = new Node(node.character.substring(0, intRef.get()));
					parentNode.childs.remove(node);
					//Part2
					node.character = node.character.substring(intRef.get());
					childPart1.childs.add(node);
					//New child
					Node newChild = new Node(docWords[i].substring(startIndex));
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
	
	void searchWordPattern(String word) {
		Node parent = this.root;
		AtomicInteger indexRef = new AtomicInteger(0);
		int startIndex = 0;
		while (startIndex < word.length()) {
			Node child = this.childNodeWithCharacter(parent, word,startIndex, indexRef);
			startIndex += indexRef.get();
			if(child != null) {
				parent = child;
				continue;
			} else {
				System.out.println("The word doesnot exist in the document");
				return;
			}
		}
		if(parent != null) {
			if(parent.character.equals(word.substring(startIndex-indexRef.get()))) {
				System.out.println("The exact word exists");
			} else {
				System.out.println("The word exists but its a prefix of a different word");
			}
		}
	}
	
	public static void main(String[] args) {
		String document = "bear bell bid bull buy sell stock stop";
		CompressedTries trie = new CompressedTries();
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
