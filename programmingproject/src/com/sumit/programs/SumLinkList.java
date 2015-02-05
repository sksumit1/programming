package com.sumit.programs;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Given linklists 4->5->1,  3->2>1->4, sum it
 * Eg. Answer 3->6->6-5
 */
public class SumLinkList {
	
	Node result = null;
	
	class Node {
		int value;
		Node next = null;
		Node(int value,Node next) {
			this.value = value;
			this.next = next;
		}
	}
	
	
	Node buildNode(int number) {
		Node n = null;
		while(number != 0) {
			int remainder = number%10;
			number = number/10;
			Node temp = new Node(remainder,n);
			n = temp;
		}
		return n;
	}
	
	void displayNode(Node n) {
		System.out.println();
		while(n != null) {
			System.out.print(n.value+" -> ");
			n = n.next;
		}
		System.out.println();
	}
	
	Node addToNode(int value,Node result) {
		Node temp = new Node(value, result);
		return temp;
	}
	
	int addNodes(Node m,Node n) {
		int total = 0;
		if(m.next != null && n.next != null) {
			total = m.value+n.value+addNodes(m.next,n.next);
		} else {
			total = m.value+n.value;
		}
		int remainder = total%10;
		this.result = addToNode(remainder,this.result);
		return total/10;
	}
	
	int length(Node n) {
		int i =0;
		while(n != null) {
			i++;
			n = n.next;
		}
		return i;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		SumLinkList obj = new SumLinkList();
		System.out.println("Please enter your numbers");
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		Node input[] = new Node[2];
		for (int i = 0; i < 2; i++) {
			input[i] = obj.buildNode(Integer.parseInt(buf.readLine().trim()));
		}
		for (int i = 0; i < 2; i++) {
			obj.displayNode(input[i]);
		}
		int m = obj.length(input[0]);
		int n = obj.length(input[1]);
		int diff = 0;
		Node smallNode = null;
		Node bigNode = null;
		if(m > n) {
			smallNode = input[1];
			bigNode = input[0];
			diff = m - n;
		} else if(m < n) {
			smallNode = input[0];
			bigNode = input[1];
			diff = n - m;
		} else {
			smallNode = input[0];
			bigNode = input[1];
		}
		while(diff != 0) {
			smallNode = obj.addToNode(0, smallNode);
			diff--;
		}
		System.out.println("=================");
		obj.displayNode(smallNode);
		obj.displayNode(bigNode);
		int carry = obj.addNodes(smallNode,bigNode);
		if(carry != 0) {
			obj.result = obj.addToNode(carry,obj.result);
		}
		System.out.println("Result");
		obj.displayNode(obj.result);
	}

}
