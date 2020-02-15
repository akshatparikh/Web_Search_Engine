package main;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import libraries.*;
import main.Crawler;
import main.HTMLtoTEXT;
import main.tokenizer;
import main.KeywordGenerator;
import main.Hint;

public class MainClass {
	public static ArrayList<String> tokens = new ArrayList<String>();
	//public static Set<String> wordlist = new HashSet<>();
	public static ArrayList<TST<Integer>> allTSTs = new ArrayList<>();

	public static void start() {
		// TODO Auto-generated method stub
		// Firstly HTML files are being crawled
		// Next, HTML files are downloaded.

		// Converting HTML files to Text files.
		File inputH = new File("HTMLFiles/");
		System.out.println("Converting to Text...");
		File[] st = inputH.listFiles();
		for (int i = 0; i < st.length; i++) {
			if (st[i].isFile()) {
				HTMLtoTEXT.parse(st[i]);
			}
		}
		System.out.println("Conversion Complete.");

		// Tokenising the files into tokens.
		tokenizer tk = new tokenizer();
		File inputT = new File("textFiles/");
		File[] stFiles = inputT.listFiles();

		for (int i = 0; i < stFiles.length; i++) {
			if (stFiles[i].isFile()) {
				tk.tokenize(stFiles[i]);
			}
		}
		tokens = tk.tokens;

		for (File f : stFiles) {

			// inserting tokens into TST and hash set.
			TST<Integer> tst = new TST<>();
			for (int i = 0; i < tokens.size(); i++) {

				String temp = tokens.get(i).replaceAll("[^a-zA-Z]", " ");
				// further separating tokens so no words are left combined
				StringTokenizer stk = new StringTokenizer(temp, " ");
				while (stk.hasMoreTokens()) {
					String tmp = stk.nextToken();
					//wordlist.add(tmp);

					if (tmp.length() > 0) {
						if (tst.contains(tmp)) {
							tst.put(tmp, tst.get(tmp) + 1);
						} else {
							tst.put(tmp, 1);
						}
					}
				}
			}
			allTSTs.add(tst);
		}
	}

	public static void main(String[] args) throws IOException {
		String HTML = "HTMLFiles\\";
		String text = "textFiles\\";
		String userInput = "";
		String[] allHTML;
		boolean bool = true;
		ArrayList<String> keywords = new ArrayList<String>();
		ArrayList<String> hintss = new ArrayList<String>();
		Scanner scan = new Scanner(System.in);
		File dir = new File(HTML);
		allHTML = dir.list();

		start();

		while (bool) {
			System.out.println("Type 1 to Search");
			System.out.println("Type 2 to Exit");
			System.out.print("> ");
			if (scan.nextInt() == 1) {
				System.out.print("Search: ");
				userInput = scan.next().toLowerCase();

				keywords = KeywordGenerator.getkeywords(userInput);
				if (keywords.isEmpty()) {
					System.out.println("This is not a valid word. try Again!");
				} else {
					hintss = Hint.hints(keywords);
					// System.out.println("hint size:" + hintss.size());
					System.out.println("tst size:" + allTSTs.size());
					if (!(hintss.size() == 0)) {
						System.out.print("Some Word Hints: ");
						for (String string : hintss) {
							System.out.print(string + " ");
						}
						System.out.println();
					}

					// getting the web pages that match the keywords.
					int pagefrequency[][] = Searcher.search(keywords);

					// sorting the pages according to frequencies.
					Arrays.sort(pagefrequency, new Comparator<int[]>() {
						@Override
						public int compare(final int[] e1, final int[] e2) {
							if (e1[0] < e2[0])
								return 1;
							else
								return -1;
						}
					});

					System.out.println("Results: ");
					for (int i = 0; i < pagefrequency.length; i++) {

						if (i % 10 == 0 && i != 0) {
							System.out.print("\nEnter 1 for next page: ");
							if (scan.nextInt() == 1) {
								continue;
							}else {
								break;
							}
						}
						int index = pagefrequency[i][1];
						if (pagefrequency[i][0] == 0) {
							break;
						}

						System.out.println(allHTML[index]);
					}
				}
			} else {
				break;
			}
		}
		scan.close();
	}// main
}// class
