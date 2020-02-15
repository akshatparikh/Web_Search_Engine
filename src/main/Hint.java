package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import libraries.Sequences;

public class Hint {
	public static ArrayList<String> hints (ArrayList<String> input) {
		ArrayList<String> hints = new ArrayList<String>();
		BufferedReader reader;
		String line;
		ArrayList<String> engWords = new ArrayList<>();

		try {
			reader = new BufferedReader(new FileReader("words_alpha.txt"));
			while ((line = reader.readLine()) != null) {
				engWords.add(line);
			}
			//System.out.println("size: "+engWords.size());
			// exception handling.
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int MaxDist = 1000;
		int dist;
		String word = null;
		for (String s : input) {
			for (String t : engWords) {
				dist = Sequences.editDistance(s.toLowerCase(), t.toLowerCase());
				if (dist < MaxDist) {
					MaxDist = dist;
					word = t;
				}
			}
			hints.add(word.toLowerCase());
			MaxDist = 1000;
		}
		for (String string : input) {	
			if (hints.contains(string.toLowerCase())) {
				hints.remove(string);
			}
		}
		return hints;
//		if(hints.isEmpty()) {
//			return null;
//		}else {
//		}
	}
}