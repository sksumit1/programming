package com.sumit.programs;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Searching word inside array of words. Words are delimited by space. The search is case insensitive
 *
 */
public class Tries {
	
	class Node {
		char character;
		Set<Node> childs = null;
		Node(char character) {
			this.character = character;
		}
	}
	
	private Node root = new Node(' ');
	
	public void initializeTrie(String document) {
		String[] docWords = document.toLowerCase().split(" ");
		for (int i = 0; i < docWords.length; i++) {
			char [] charArray = docWords[i].toCharArray();
			Node parentNode = root;
			for (int j = 0; j < charArray.length; j++) {
				Node node = this.childNodeWithCharacter(parentNode, charArray[j]);
				if(parentNode.childs == null) {
					parentNode.childs = new HashSet<Node>();
				}
				if(node == null) {
					node = new Node(charArray[j]);
					parentNode.childs.add(node);
				}
				parentNode = node;				
			}
			if(parentNode.childs == null) {
				parentNode.childs = new HashSet<Node>();
			}
			parentNode.childs.add(new Node('$'));
		}
	}
	
	Node childNodeWithCharacter(Node parent,char ch) {
		Set<Node> childs = parent.childs;
		if(childs == null) {
			return null;
		} else {
			for (Iterator<Node> iterator = childs.iterator(); iterator.hasNext();) {
				Node node =  iterator.next();
				if(node.character == ch) {
					return node;
				}
			}
			return null;
		}
	}
	
	void searchWordPattern(String word) {
		char wordArray[] = word.toLowerCase().toCharArray();
		Node parent = this.root;
		for (int i = 0; i < wordArray.length; i++) {
			Node child = this.childNodeWithCharacter(parent, wordArray[i]);
			if(child != null) {
				parent = child;
				continue;
			} else {
				System.out.println("The word doesnot exist in the document");
				return;
			}
		}
		if(parent != null) {
			Node child = this.childNodeWithCharacter(parent, '$');
			if(child != null) {
				System.out.println("The exact word exists");
			} else {
				System.out.println("The word exists but its a prefix of a different word");
			}
		}
	}
	
	public static void main(String[] args) {
		String document = "bear bell bid bull buy sell stock stop";
		Tries trie = new Tries();
		trie.initializeTrie(document);
		trie.searchWordPattern("buy");
		trie.searchWordPattern("buyer");
		trie.searchWordPattern("abc");
		trie.searchWordPattern("bul");
	}

}
