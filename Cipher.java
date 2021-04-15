
/*
 *  Encode and decode by the Caesar Cipher
 *  02/25/2020
 */
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Cipher {

	public static final int NUM_LETTERS = 26;
	public static final int ENCODE = 1;
	public static final int DECODE = 2;

	public static void main(String[] args) throws IOException {

		// letters
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		// Check args length, if error, print usage message and exit
		if (args.length != 3) {
			System.out.print("Wrong number of arguments");
			System.exit(0);
		}

		// Extract input args to variables
		String inputFilename = args[0];
		String key = args[1];
		int action = Integer.parseInt(args[2]);
		String outputFilename = getOutputFilename(inputFilename, action);
		Scanner input = openInput(inputFilename);
		PrintWriter output = openOutput(outputFilename);

		if (action != ENCODE && action != DECODE) {
			System.out.println("Option" + action + "is not valid");
			return;
		}

		// Read in data and output to file
		String in = new String();
		String out = new String();
		while (input.hasNext()) {
			in += input.next() + " ";
		}

		// Convert all letters to lower case for input
		in = in.toLowerCase();

		if (action == ENCODE) {
			out = encode(in, key);
		} else {
			out = decode(in, key);
		}

		// Convert all letters to lower case for output
		out = out.toLowerCase();

		output.print(out);

		// Close streams
		input.close();
		output.close();
	}

	public static String encode(String input, String key) {
		String coded = new String();
		key = key.toLowerCase();
		for (int i = 0; i < input.length(); i++) {
			int distance = key.charAt(i % key.length()) - 'a'; // FIXME
			coded += shiftUpByK(input.charAt(i), distance);
		}
		return coded;
	}

	public static String decode(String input, String key) {
		String decoded = new String();
		key = key.toLowerCase();
		for (int i = 0; i < input.length(); i++) {
			int distance = key.charAt(i % key.length()) - 'a'; // FIXME
			decoded += shiftDownByK(input.charAt(i), distance);
		}
		return decoded;
	}

	/**
	 * Open input for reading
	 * 
	 * @param filename
	 * @return Scanner
	 * @throws FileNotFoundException
	 */
	public static Scanner openInput(String fileName) throws FileNotFoundException {
		File newfile = new File(fileName);
		return new Scanner(newfile);

	}

	/**
	 * Open output for writing
	 * 
	 * @param filename
	 * @return PrintWriter
	 * @throws FileNotFoundException
	 */
	public static PrintWriter openOutput(String fileName) throws FileNotFoundException {
		File newfile = new File(fileName);
		PrintWriter pw = new PrintWriter(newfile);
		return pw;
	}

	/**
	 * Encode letter by some offset d
	 * 
	 * @param c      input character
	 * @param offset amount to shift character value
	 * @return char encoded character
	 */
	public static char shiftUpByK(char c, int distance) {
		if ('a' <= c && c <= 'z')
			return (char) ('a' + (c - 'a' + distance) % NUM_LETTERS);
		if ('A' <= c && c <= 'Z')
			return (char) ('A' + (c - 'A' + distance) % NUM_LETTERS);
		return c; // don't encrypt if not an ic character
	}

	/**
	 * Decode letter by some offset d
	 * 
	 * @param c      input character
	 * @param offset amount to shift character value
	 * @return char decoded character
	 */
	public static char shiftDownByK(char c, int distance) {
		if ('a' <= c && c <= 'z')
			return (char) ('z' - ('z' - c + distance) % NUM_LETTERS);
		if ('A' <= c && c <= 'Z')
			return (char) ('Z' - ('Z' - c + distance) % NUM_LETTERS);
		return c;
	}

	/**
	 * Changes file extension to ".coded" or ".decoded"
	 * 
	 * @param filename
	 * @return String new filename or null if action is illegal
	 */
	public static String getOutputFilename(String fileName, int action) {
		String name = new String(fileName);
		if (action == ENCODE) {
			name += ".coded";
		} else {
			name += ".decoded";
		}
		return name;
	}


}
