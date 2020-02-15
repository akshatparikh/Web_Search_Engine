package main;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	private static final int MAX_DEPTH = 3;
	private static final int LIMIT = 500;
	private HashSet<String> links;
	private List<List<String>> articles;
	
	public Crawler() {
		links = new HashSet<>();
		// pages = new ArrayList<>();
	}
	
	public int getNumOfPages() {
		// TODO Auto-generated method stub
		return links.size();
	}

	public void getPageLinks(String URL, int depth) {
		if ((!links.contains(URL) && URL.contains("https://www.w3schools.com/") && (depth < MAX_DEPTH))) {
			System.out.println(">> Depth: " + depth + " [" + URL + "]");
			try {
				links.add(URL);
				Document document = Jsoup.connect(URL).get();
				Elements linksOnPage = document.select("a[href]");
				depth++;
				for (Element page : linksOnPage) {
					if (links.size() > LIMIT) {
						break;
					}
					if (page.hasAttr("abs:href"))
						getPageLinks(page.attr("abs:href"), depth);
				}
			} catch (IOException e) {
				System.err.println("For '" + URL + "': " + e.getMessage());
			}
		}
	}

	public void downloadLinks() {
		links.forEach(x -> {
			Document document;
			try {
				document = Jsoup.connect(x).get();
			    
				File file = new File("HTMLFiles\\"+document.title()+".html");
		        file.getParentFile().mkdir();
		        PrintWriter out = new PrintWriter(file);
		        
				try {
					String temp = document.html();
					out.write(temp);
				} catch (Exception e) {
					// TODO: handle exception
				}
				out.close();
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}

	public static void main(String[] args) throws IOException {
		Crawler crawl = new Crawler();
		crawl.getPageLinks("https://www.w3schools.com/", 0);
		System.out.println("Downloading..");
		crawl.downloadLinks();
		System.out.println("Done..");
	}

}
