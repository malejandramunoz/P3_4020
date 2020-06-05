package main;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

import strategies.BTNode.ComparableKey;
import strategies.HashFunction;
import strategies.HashTableSC;
import strategies.List;
import strategies.SortedArrayList;

/**
 * Main classes where the Interface methods are implemented to their specifications and their respective JavaDoc.
 * 
 * This code creates a Huffman code based on the Frequency Distribution of their respective character. It has a preMain class that calls 
 * the File Path and File name. Each method is explained more in details in ea
 * 
 * @author Maria Alejandra Munoz Valenzuela
 *
 * @param <K> - Key used to find the value
 * @param <V> - Value associated with the key
 */
public class HuffmanCoding<K, V> implements InterfaceHuffmanCoding<K, V> {

	@SuppressWarnings("hiding")
	private class SimpleHashFunction<K> implements HashFunction<K> {
		@Override
		public int hashCode(K key) {
			String temp = key.toString();
			int result = 0;
			for (int i = 0; i < temp.length(); i++)
				result += temp.charAt(i);
			return result;
		}
	}

	
	@Override
	public String load_data(String filepath, String filename) throws FileNotFoundException {
		String result = "";
		File test = new File(filepath, filename);
		Scanner reader = new Scanner(test);
		while(reader.hasNext()) {
			String add = reader.nextLine();
			result += add;
		}
		reader.close();
		return result;
	}
	
	@Override
	public HashTableSC<String, Integer> compute_fd(String input) {
		HashTableSC<String, Integer> mapTable = new HashTableSC<String, Integer>(input.length(), new SimpleHashFunction<String>());
		for (int i = 0; i < input.length(); i++) {
			String sub = input.substring(i, i + 1);
			if(!mapTable.containsKey(sub)) {
				mapTable.put(sub, 1);
			} else {
				mapTable.put(sub, mapTable.get(sub) + 1);
			}
		}
		return mapTable;
	}

	
	@Override
	public ComparableKey<Integer, String> huffman_tree(HashTableSC<String, Integer> fd) {
		SortedArrayList<ComparableKey<Integer, String>> nodes = new SortedArrayList<ComparableKey<Integer, String>>(fd.size());
		sorting(fd, nodes);
		while(nodes.size() != 1){
			ComparableKey<Integer, String> N = new ComparableKey<Integer, String>();
			if(nodes.get(0).compareTo(nodes.get(1)) == 0) {
				if(nodes.get(0).getValue().compareTo(nodes.get(1).getValue()) < 0) {
					huffman_tree_left(N, nodes);
				} else if(nodes.get(0).getValue().compareTo(nodes.get(1).getValue()) > 0) {
					N.setRightChild(nodes.removeIndex(0));
					N.setLeftChild(nodes.removeIndex(0));
				}
			}
			else {
				huffman_tree_left(N, nodes);
			}
			N.setKey(N.getLeftChild().getKey() + N.getRightChild().getKey());
			N.setValue(N.getLeftChild().getValue() + N.getRightChild().getValue());
			nodes.add(N);
		}
		return nodes.removeIndex(0);
	}
	private void huffman_tree_left(ComparableKey<Integer, String> N, SortedArrayList<ComparableKey<Integer, String>> nodes) {
		N.setLeftChild(nodes.removeIndex(0));
		N.setRightChild(nodes.removeIndex(0));
	}
	
	@Override
	public HashTableSC<String, String> huffman_code(ComparableKey<Integer, String> root) {
		HashTableSC<String, String> result = new HashTableSC<String, String>(depth(root), new SimpleHashFunction<String>());
		huffman_code_aux(root, "", result);
		return result;
	}
	
	/**
	 * Used in huffman_code.
	 * @param root - ComparableKey<Integer, String> that holds the root of the huffman tree.
	 * @param s - String with the new meaning to the character.
	 * @param result - HashTableSC<String, String> that holds the newly created code.
	 */
	private void huffman_code_aux(ComparableKey<Integer, String> root, String s, HashTableSC<String, String> result) {
		if(root.getLeft() == null && root.getRight() == null)  {
			result.put(root.getValue(), s);
			return;
		}
		huffman_code_aux((ComparableKey<Integer, String>) root.getLeftChild(), s + "0", result);
		huffman_code_aux((ComparableKey<Integer, String>) root.getRightChild(), s + "1", result);
	}
	/**
	 * Used in huffman_code
	 * @param node - ComparableKey<Integer, String> that holds the huffman tree
	 * @return Integer with how long is the tree.
	 */
	private int depth(ComparableKey<Integer, String> node) {
		int count = 0;
		if(node == null) {
			return 0;
		}
		if(node.getLeftChild() == null && node.getRight() == null) {
			return 0;
		} else {
			count++;
		}
		count += depth((ComparableKey<Integer, String>) node.getLeftChild()) + depth((ComparableKey<Integer, String>) node.getRightChild());

		return count;
	}
	
	@Override
	public String encode(HashTableSC<String, String> huffmancode, String input) {
		String result = "";
		for (int i = 0; i < input.length(); i++) {
			String sub = input.substring(i, i + 1);
			result += huffmancode.get(sub);		
		}
		return result;
	}
	
	/**
	 * Helper method used to add the fd into the SortedArrayList so that the smallest is on the top. Used in: huffman_tree and process_results.
	 * @param fd - HashTableSC<String, Integer> that has how many times a letter has been repeated inside the
	 * file given.
	 * @param nodes - ComparableKey<Integer, String> that holds the root of the huffman tree.
	 */
	public void sorting(HashTableSC<String, Integer> fd, SortedArrayList<ComparableKey<Integer, String>> nodes) {
		List<String> keys = fd.getKeys();
		for (String s : keys) {
			ComparableKey<Integer, String> newEntry = new ComparableKey<Integer, String>(fd.get(s), s);
			nodes.add(newEntry);
		} 
	}
	 
	@Override
	public void process_results(HashTableSC<String, Integer> fd, HashTableSC<String, String> huffmancode, String input, String output) throws UnsupportedEncodingException {
		SortedArrayList<ComparableKey<Integer, String>> ordered = new SortedArrayList<ComparableKey<Integer,String>>(fd.size());
		sorting(fd, ordered);
		System.out.println("System\tFrequency\tCode");
		System.out.println("------\t---------\t----");
		for (int i = ordered.size() - 1; i >= 0; i--) {
			System.out.print(ordered.get(i).getValue() + "\t" + ordered.get(i).getKey() + "           \t" + huffmancode.get(ordered.get(i).getValue()));
			System.out.println();
		}
		System.out.println("Original string:\n" + input);
		System.out.println("Encoded string:\n" + output);


		int inputb = input.getBytes("UTF-8").length;
		int outputb = output.getBytes().length / 8;
		System.out.println("The original string requires " + input.getBytes("UTF-8").length + " bytes.");
		if(output.getBytes().length % 8 > 0) {
			outputb++;
			System.out.println("The encoded string requires " + outputb + " bytes.");
		} else {
			System.out.println("The encoded string requires " + outputb + " bytes.");
		}
		
		double percentage = Math.abs((double) outputb - (double) inputb) / inputb * 100;
		NumberFormat nf = new DecimalFormat("##.##");
		System.out.println("Difference is space required is " + nf.format(percentage) + "%.");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void preMain(String path, String filename) throws FileNotFoundException, UnsupportedEncodingException{
		HuffmanCoding huffman = new HuffmanCoding();
		String input = huffman.load_data(path, filename);
		HashTableSC<String, Integer> fd = huffman.compute_fd(input);
		ComparableKey<Integer, String> node = huffman.huffman_tree(fd);
		HashTableSC huffmancode = huffman.huffman_code(node);
		String output = huffman.encode(huffmancode, input);
//		BinaryTreePrinter.print(node);
		huffman.process_results(fd, huffmancode, input, output);
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		preMain("inputData", "stringData.txt");
	}
}
