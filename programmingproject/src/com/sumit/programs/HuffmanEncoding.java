package com.sumit.programs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.file.spi.FileTypeDetector;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import sun.nio.fs.DefaultFileTypeDetector;

public class HuffmanEncoding {
	
	private int MCU = 1;

	/**
	 * If the type is text, use MCU = 1 word, else MCU = 1 byte
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private boolean isText(String file) throws IOException {
		File f = new File(file);
		FileTypeDetector ftd = DefaultFileTypeDetector.create();
		String type = ftd.probeContentType(f.toPath());
		if(type != null && type.toLowerCase().matches("text")) {
			return true;
		} else {
			return false;
		}
	}
	
	class Node  implements Serializable , Comparable{
		private byte[] content;
		private String codeValue;
		private int occuranceCount = 0;
		private Node [] childs = null;
		public Node(byte[] content,int occuranceCount) {
			this.content = content;
			this.occuranceCount = occuranceCount;
		}
		public byte[] getContent() {
			return content;
		}
		public void setContent(byte[] content) {
			this.content = content;
		}
		public String getCodeValue() {
			return codeValue;
		}
		public void setCodeValue(String codeValue) {
			this.codeValue = codeValue;
		}
		public int getOccuranceCount() {
			return occuranceCount;
		}
		public void setOccuranceCount(int occuranceCount) {
			this.occuranceCount = occuranceCount;
		}
		public void incrementCounter() {
			this.occuranceCount++;
		}
		
		@Override
		public int hashCode() {
			return Arrays.hashCode(content);
		}
		@Override
		public boolean equals(Object o) {
			if(o instanceof Node) {
					if(content == null || ((Node)o).getContent() == null) {
						return false;
					} else if(content.length < ((Node)o).getContent().length) {
						return false;
					} else if(content.length < ((Node)o).getContent().length) {
						return false;
					} else {
						for (int i = 0; i < content.length; i++) {
							if(content[i] != ((Node)o).getContent()[i]) {
								return false;
							}
						}
						return true;
					}				
			} else {
				return false;
			}
		}
		@Override
		public int compareTo(Object o) {
			int comp = 1;
			if(o instanceof Node) {
				comp = Integer.compare(occuranceCount, ((Node)o).getOccuranceCount());
				if(comp == 0) {
					if(content == null) {
						return 1;
					} else if (((Node)o).getContent() == null) {
						return -1;
					}else if(content.length < ((Node)o).getContent().length) {
						return 1;
					} else if(content.length < ((Node)o).getContent().length) {
						return -1;
					} else {
						comp = 0;
						for (int i = 0; i < content.length; i++) {
							comp = Byte.compare(content[i], ((Node)o).getContent()[i]);
							if(comp != 0) {
								break;
							}
						}
					}
				}
			}
			return comp;
		}
		
	}
	
	Map<byte[],Integer> items = new HashMap<byte[],Integer>();
	private PriorityQueue<Node> huffQueue = new PriorityQueue<Node>();
	private Node huffTree = null;
	
	private void generateHuffTable(String file) throws IOException, UnsupportedEncodingException {
		if(isText(file)) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					if(words[i].trim().equals("")) {
						continue;
					} else {
						Integer count = items.get(words[i].getBytes("UTF-8"));
						if(count == null) {
							items.put(words[i].getBytes("UTF-8"), 1);
						} else {
							items.put(words[i].getBytes("UTF-8"), ++count);
						}
					}
				}
			}
		} else {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file), MCU);
			byte[] element = new byte[MCU];
			while(bis.read(element) != -1) {
				Integer count = items.get(element);
				if(count == null) {
					items.put(element, 1);
				} else {
					items.put(element, ++count);
				}
			}
		}
		Iterator<byte[]> it = items.keySet().iterator();
		while(it.hasNext()) {
			byte[] content = it.next();
			int count = items.get(content);
			Node n = new Node(content, count);
			huffQueue.add(n);
		}
		//Build the priority heap (Huffman logic here
		while(true) {
			Node n1 = huffQueue.poll();
			if(n1 == null) {
				break;
			}
			Node n2 = huffQueue.poll();
			if(n2 == null) {
				huffTree = n1;
				break;
			}
			Node parent = new Node(null, n1.getOccuranceCount()+n2.getOccuranceCount());
			Node[] childs = {n1,n2};
			parent.childs = childs;
			huffQueue.add(parent);
		}
	}
	
}


