package main;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import libraries.In;

public class tokenizer {

	public ArrayList<String> tokens = new ArrayList<String>();
	public void tokenize(File input) {

		In in = new In(input);
		String testtext = in.readLine().concat(" ").replaceAll("\\W", " ");
		StringTokenizer tk = new StringTokenizer(testtext, " ");
		while (tk.hasMoreTokens()) {
			String temp = tk.nextToken();
			if (!tokens.contains(temp))
				tokens.add(temp);
		}
		tokens.size();
	}
}
