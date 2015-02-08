package com.sumit.programs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {
	
	Node root = null;
	BufferedReader reader = null;
	String fileName ="tree.ser";
	Tree(BufferedReader reader) {
		this.reader = reader;
	}
	
	void init() throws IOException {
		reader = new BufferedReader(new InputStreamReader(System.in));
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
	
	void save() {
		ObjectOutputStream oos = null;
		try {
			File f = new File(fileName);
	    	f.delete();
			oos = new ObjectOutputStream(new FileOutputStream(fileName));
			oos.writeObject(root);
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if(oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	void load() {
		FileInputStream fis = null;
    	ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			this.root = (Node) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	void takeInput() throws IOException {
		BufferedReader reader = null;			
		reader = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			System.out.println("1.Add Node 2.DisplayTree 3.Save 4.Load 5.Exit");
			String command = reader.readLine();
			if("1".equals(command.trim())) {
				System.out.println("Enter node parent isonleft : Eg. 2 1 true");
				command = reader.readLine();
				String args[] = command.split(" ");
				if(args.length == 1) {
					this.addNode(Integer.parseInt(args[0]), 0, true);
					System.out.println("Added root node");
				} else {
					this.addNode(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Boolean.parseBoolean(args[2]));
				}
			} else if("2".equals(command.trim())) {
				BTreePrinter.printNode(root);
			} else if("3".equals(command.trim())) {
				this.save();
			} else if("4".equals(command.trim())) {
				this.load();
			} else if("5".equals(command.trim())) {
				System.out.println("Exiting...");
				return;
			} else {
				System.out.println("Not a valid input");
			}
		}
	}
	
	void addNode(int value,int parent, boolean left) {
		if(root == null) {
			root = new Node(value);
			return;
		}
		Node p = this.searchNode(parent,true);
		if(p != null) {
			Node node = new Node(value);
			if(left) {
				System.out.println("Adding to left branch");
				node.left = p.left;
				p.left = node;
				node.parent = p;
			} else {
				System.out.println("Adding to right branch");
				node.right = p.right;
				p.right = node;
				node.parent = p;
			}
		} else {
			System.out.println("Parent not found");
		}
	}
	
	Node searchNode(int node, boolean searchBFS) {
		if(searchBFS) {
			return this.searchBFS(node);
		} else {
			return this.searchDFS(this.root, node);
		}
	}
	
	Node searchBFS(int node) {
		Queue<Node>queue = new LinkedList<Node>();
		queue.add(this.root);
		Node n;
		while((n = queue.poll()) != null) {
			if(n.value == node) {
				return n;
			}
			if(n.left != null) {
				queue.add(n.left);
			}
			if(n.right != null) {
				queue.add(n.right);
			}
		}
		return null;
	}
	
	Node searchDFS(Node ptr, int node) {
		if(ptr == null) {
			return  null;
		}
		Node n = searchDFS(ptr.left, node);
		if(n != null) {
			return n;
		}
		n = searchDFS(ptr.right, node);
		if(n != null) {
			return n;
		}
		if(ptr.value == node) {
			return ptr;
		}
		return null;
	}
	    
    public static void main(String[] args) throws IOException {
		Tree tree = new Tree(null);
		tree.init();
		tree.takeInput();
		tree.close();
	}

}

class Node implements Serializable {

	private static final long serialVersionUID = 6216185550477151641L;
	int value;
	Node left = null,right = null, parent = null;;
	Node(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Node getLeft() {
		return left;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public Node getRight() {
		return right;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	
}

class BTreePrinter {

    public static void printNode(Node root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.value);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
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

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}
