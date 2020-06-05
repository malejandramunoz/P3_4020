package main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import strategies.BTNode.ComparableKey;
import strategies.HashTableSC;

/**
 * This is the main interface, done so that I could make sure that all the classes that were required for the project where there.
 * @author Maria Alejandra Muñoz Valenzuela
 *
 * @param <K> - Key used to find the value
 * @param <V> - Value associated with the key
 */

public interface InterfaceHuffmanCoding<K, V> {
	
	/**
	 * Simple method that adds every single line inside the file given. Done with the scanner class and made
	 * sure to be close the scanner to avoid data leaks anywhere.
	 * 
	 * @param filename - String with the name of the file that is to be used
	 * @return Single string with all the lines inside the file given
	 * @throws FileNotFoundException
	 */
	public String load_data(String path, String filename) throws FileNotFoundException;
	
	/**
	 * Simple method that adds every single line inside the file given. Done with the scanner class and made
	 * sure to be close the scanner to avoid data leaks anywhere.
	 * 
	 * @param filename - String with the name of the file that is to be used
	 * @return Single string with all the lines inside the file given
	 * @throws FileNotFoundException
	 */
	public HashTableSC<String, Integer> compute_fd(String input);
	
	/**
	 * This methods takes the Frequency Distribution and turns into the Huffman Root Node that we need to save the newly encoded
	 * characters. First with the helper method (sorting) passes the HashTable into a SortedArrayList. Once that is done, it begans
	 * to construct the Root. It checks the frequencies and if they are the same, then it needs to see which is the smaller one which
	 * is done with the compareTo with the values of the node. If the first node is smaller than the second, then it goes into the helper
	 * method that adds the leftChild first. Once it has added the leftChild, it adds the rightChild, and that's why I use 0 instead of
	 * anything else because I need to add the ones at the top. When it's the first node is bigger than the second node, then the
	 * rightChild is added first and then the leftChild. This is the way it's done because I need the "smaller" value on the left side
	 * and the bigger one on the right side. After only one node is left (which will have all the children), it simple returns this 
	 * ComparableKey which is the root.
	 * 
	 * Has two helper methods: 
	 * 1) huffman_tree_left: which is just repeated code, this how I avoid repeated when it adds the 
	 * leftChild first and then the rightChildren. 
	 * 2) sorting: this is where the HashTable is turned into a SortedArrayList so that the lowest frequencies are on the top.  
	 * 
	 * @param fd - HashTableSC<String, Integer> that has how many times a letter has been repeated inside the
	 * file given.
	 * @return Root of the newly created Huffman Tree
	 */
	public ComparableKey<Integer, String> huffman_tree(HashTableSC<String, Integer> fd);
	
	/**
	 * This method has two helper method which complete the idea of what needs to happen inside it. This are:
	 * 1) huffman_code_ aux: which is a recursive  method that traverses through the left and right children
	 * to get the code. Adding 1 when it's a right child, adding 0 when it's a left child.
	 * 2) depth: which is used to create the HashTable with the size of the initial root.
	 * 
	 * @param root - ComparableKey<Integer, String> that holds the root of the created Huffman Tree
	 * @return HashTableSC with the respective codes that encode each letter inside of the original 
	 * input
	 */
	public HashTableSC<String, String> huffman_code(ComparableKey<Integer, String> root);
	
	/**
	 * Takes the input string and turns into the newly encoded String. It is basically just taking each character from the
	 * input and using it as a key to find the value that was found inside of the huffmancode parameter. It's fairly simple
	 * and once found it is simply added to the result string that is returned.
	 * 
	 * @param huffmancode - HashTableSC<String, String> that holds the letters from the original string with their new
	 * code to be written.
	 * @param input - String that holds the initial string inside the file 
	 * @return String that holds the newly encoded string
	 */
	public String encode(HashTableSC<String, String> huffmancode, String input);

	/**
	 * This method prints put the results. It takes the frequency distribution and sorts it so that it can be printed 
	 * from the highest frequency to the lowest frequency. In that same line, it looks for the code for that specific 
	 * character. Once that is done, it is simple counting the bites from each string and printing them out to the
	 * console. I saved this values so that it's easier to read for the percentage. And once I have the percentage
	 * which is just a simple math equation of error, we have all the values that were asked for and in the way that
	 * it's wanted.
	 * 
	 * @param fd - HashTableSC<String, Integer> that has how many times a letter has been repeated inside the
	 * file given.
	 * @param huffmancode - HashTableSC<String, String> that holds the letters from the original string with their new
	 * code to be written.
	 * @param input - String that holds the initial string from the file given.
	 * @param output - String that holds the newly encoded string.
	 */
	public void process_results(HashTableSC<String, Integer> fd, HashTableSC<String, String> huffmancode, String input, String output) throws UnsupportedEncodingException;
	
}
