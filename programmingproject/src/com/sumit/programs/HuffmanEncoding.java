package com.sumit.programs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoding {
	
	private int MCU = 1;

	/**
	 * If the type is text, use MCU = 1 word, else MCU = 1 byte
	 * @param file
	 * @return
	 * @throws IOException
	 */
	/*private boolean isText(String file) throws IOException {
		File f = new File(file);
		System.out.println(f.exists());
		FileTypeDetector ftd = DefaultFileTypeDetector.create();
		String type = ftd.probeContentType(f.toPath());
		if(type != null && type.toLowerCase().matches("text")) {
			return true;
		} else {
			return false;
		}
	}*/
	//TODO: Refine to know the inputtype on the fly.For e.g. assume all files ending with .txt is text file, else its a binary file
	private boolean isText(String file) {
		return true;   //For images etc make this return false
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
					} else if(content.length > ((Node)o).getContent().length) {
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
	
	private Map<byte[],Integer> items = new HashMap<byte[],Integer>();
	private PriorityQueue<Node> huffQueue = new PriorityQueue<Node>();
	private Node huffTree = null;
	//TODO: Change it to Map<ByteArrayWrapper,Long> for more unique works
	private Map<ByteArrayWrapper,Byte> encodingMap = new HashMap<ByteArrayWrapper,Byte>();
	//TODO: Change it to Map<ByteArrayWrapper,Long> for more unique works
	private Map<Byte,ByteArrayWrapper> decodingMap = new HashMap<Byte,ByteArrayWrapper>();
	
	private void generateHuffEncodingMap(String file) throws IOException, UnsupportedEncodingException {
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
		//Build the priority heap Huffman logic here
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
		//Build encode map
		traverseNode(huffTree,"0");
		//Build decode map
		Iterator<ByteArrayWrapper> it1 = encodingMap.keySet().iterator();
		while(it1.hasNext()) {
			ByteArrayWrapper item = it1.next();
			decodingMap.put(encodingMap.get(item), item);
		}
		
	}
	
	private void printEncodingMap() throws UnsupportedEncodingException {
		Iterator<ByteArrayWrapper> it1 = encodingMap.keySet().iterator();
		while(it1.hasNext()) {
			ByteArrayWrapper item = it1.next();
			Byte code = encodingMap.get(item);
			System.out.println(new String(item.getData(), "UTF-8")+" -encodes to-> "+code);
		}
	}
	
	private void printDecodingMap() throws UnsupportedEncodingException {
		Iterator<Byte> it1 = decodingMap.keySet().iterator();
		while(it1.hasNext()) {
			Byte code = it1.next();
			ByteArrayWrapper item = decodingMap.get(code);
			System.out.println(code+" -decodes to-> "+new String(item.getData(), "UTF-8"));
		}
	}
	
	private void traverseNode(Node n, String code) {
		if(n == null) {
			return;
		}
		if(n.content != null) {
			//This is a leaf
			encodingMap.put(new ByteArrayWrapper(n.content), Byte.parseByte(code, 2));
			return;
		} else {
			traverseNode(n.childs[0],code+"0");
			traverseNode(n.childs[1],code+"1");
		}
	}
	
	private void encodeFile(String file) throws Exception {
		BufferedReader br = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		try {
		if(isText(file)) {
			br = new BufferedReader(new FileReader(file));
			File f = new File(file+".coded");
	    	f.delete();
			fos = new FileOutputStream(file+".coded");
			String line = null;
			while((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				for (int i = 0; i < words.length; i++) {
					if(words[i].trim().equals("")) {
						continue;
					} else {
						byte[] item = words[i].getBytes("UTF-8");
						Byte code = encodingMap.get(new ByteArrayWrapper(item));
						byte [] ar = {code};
						fos.write(ar);						
					}
				}
				fos.write("\n".getBytes());
			}
		} else {
			bis = new BufferedInputStream(new FileInputStream(file), MCU);
			File f = new File(file+".coded");
	    	f.delete();
			fos = new FileOutputStream(file+".coded");
			byte[] element = new byte[MCU];
			while(bis.read(element) != -1) {
				Byte code = encodingMap.get(element);
				byte [] ar = {code};
				fos.write(ar);
			}
		}
		} finally {
			if(fos != null) {
				fos.close();
			}
			if(bis != null) {
				bis.close();
			}
			if(br != null) {
				br.close();
			}
		}
	}
	
	private void decodeFile(String file) throws Exception {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		try {
		boolean isTextFile = false;
		if(new File(file+".text").exists()) {
			isTextFile = true;
		}
		String encodedFile = file+".coded";
		bis = new BufferedInputStream(new FileInputStream(encodedFile),1);
		fos = new FileOutputStream(file+".decoded");
		Byte b = null;
		while((b = Byte.valueOf((byte) bis.read())) != -1) {
			ByteArrayWrapper data = decodingMap.get(b);
			if(data == null) {
				fos.write("\n".getBytes());
				continue;
			}
			fos.write(data.getData());
			if(isTextFile) {
				fos.write(" ".getBytes("UTF-8"));
			}
		}
		} finally {
			if(fos != null) {
				fos.close();
			}
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	private void touch(String file)
	{
		try {
			if (this.isText(file)) {
				file = file + ".text";
			} else {
				file = file + ".binary";
			}
			new FileOutputStream(file).close();
		} catch (IOException e) {
	    }
	}
	
	private void saveDecodingMap(String file) {
		ObjectOutputStream oos = null;
		try {
			File f = new File(file+".codermap");
	    	f.delete();
			oos = new ObjectOutputStream(new FileOutputStream(file+".codermap"));
			oos.writeObject(decodingMap);
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
	
	private void loadDecodingMap(String file) {
		FileInputStream fis = null;
    	ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(file+".codermap");
			ois = new ObjectInputStream(fis);
			this.decodingMap = (Map<Byte, ByteArrayWrapper>) ois.readObject();
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
	
	//TODO: Make this program menu driven.
	//Run it once to save data
	//Run it again to decode data 
	public static void main(String[] args) throws Exception {
		String file = "huffman.txt";
		HuffmanEncoding obj = new HuffmanEncoding();
		obj.generateHuffEncodingMap(file);
		obj.printEncodingMap();
		obj.printDecodingMap();
		obj.encodeFile(file);		
		//obj.saveDecodingMap(file); Make this part serializable
		//obj.loadDecodingMap(file);
		obj.touch(file); //This tells if the encoded file is text or binary
		obj.decodeFile(file);
		
	}
	
	final class ByteArrayWrapper implements Serializable
	{
	    private final Byte[] data;

	    public ByteArrayWrapper(byte[] data)
	    {
	        if (data == null)
	        {
	            throw new NullPointerException();
	        }
	        this.data = new Byte[data.length];
	        for (int i = 0; i < data.length; i++) {
				this.data[i] = new Byte(data[i]);
			}
	    }
	    
	    public byte[] getData() {
	    	byte[] temp = new byte[data.length];
	    	for (int i = 0; i < data.length; i++) {
				temp[i] = data[i].byteValue();
			}
	    	return temp;
	    }

	    @Override
	    public boolean equals(Object other)
	    {
	        if (!(other instanceof ByteArrayWrapper))
	        {
	            return false;
	        }
	        return Arrays.equals(data, ((ByteArrayWrapper)other).data);
	    }

	    @Override
	    public int hashCode()
	    {
	        return Arrays.hashCode(data);
	    }
	}
	
}


