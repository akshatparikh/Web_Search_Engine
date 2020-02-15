package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLtoTEXT {
	
	
	public static void main(String args[])
	{
    	File input = new File("HTMLFiles/");
	    File[] st = input.listFiles();
	    
	    for (int i = 0; i < st.length; i++) 
	{
	    	if(st[i].isFile())
	    	{
                parse(st[i]);
            }
	    	//System.out.println(i+" "+st[i]);
	}
}
	
	 public static void parse(File input) {

		 	
		 Document d;
		 try{
			 	String text="";
			 	d = (Document) Jsoup.parse(input, "UTF-8", "");
		        text = d.text();
		        File file = new File("textFiles\\"+input.getName().split("\\.")[0]+".txt");
		        file.getParentFile().mkdir();
	     		PrintWriter out = new PrintWriter(file);
	     		out.println(text);
	     		out.close();
		    }
		        catch(IOException e){
					System.err.println(e.getMessage());
		        }
		 }
}
