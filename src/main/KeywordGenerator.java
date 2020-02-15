package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class KeywordGenerator {
	public static ArrayList<String> getkeywords(String input) {
		// TODO Auto-generated method stub
		ArrayList<String> stopWords = new ArrayList<>();
		ArrayList<String> filteredKeywords = new ArrayList<String>();
		String line;
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("stopwords.txt"));
			while ((line = reader.readLine()) != null) {
				stopWords.add(line);
			}
			// exception handling.
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// getting words from user input.
		String[] allwords = input.split(" ");
		StringBuilder builder = new StringBuilder();
		for (String currentword : allwords) {
			currentword = currentword.trim();
			if (!stopWords.contains(currentword)) {
				builder.append(currentword + "\n");
			}
		}
		filteredKeywords.addAll(Arrays.asList(builder.toString().split("\n").clone()));
		return filteredKeywords;
	}
}
