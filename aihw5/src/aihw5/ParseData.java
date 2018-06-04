package aihw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

// Real all the words in many different file and sort them in decresing order by number of them being produce... 

public class ParseData {

	public static void main(String[] args) {
		List<String> ls = new ArrayList();
		File directory = new File("/Users/cheickcisse/Downloads/WARC201709");
		for (File file : directory.listFiles()) {
			try {
				Scanner s = new Scanner(file);
				while (s.hasNext()){
					ls.add(s.next());
				}
				s.close();	
			} catch (FileNotFoundException e) {
				System.out.print("Exception Catched");
				e.printStackTrace();
			}	
		}
		System.out.println("size of list " + ls.size());
		List<String> deduped = ls.stream().distinct().collect(Collectors.toList());
		System.out.println("size of list with single words " + deduped.size());
		Set<String> s = new LinkedHashSet<>(ls);
		//System.out.println("size of list with single words verification " + s.size());
		
		
		Parse p = new Parse();
		p.parse(ls);
        List<String> l = p.getWordsInOrderOfFrequency ();
        Collections.reverse(l);
        
        System.out.println("start");
   
        try {
			FileWriter fileWritier = new FileWriter("outputAIHW5.txt");
			PrintWriter printWriter = new PrintWriter(fileWritier);
			int i = 1;
			for (String q: l) {
	        		printWriter.printf("%d\n", p.getCount(q));
					i++;
			}
			printWriter.close();
		} catch (IOException e) {
	
			e.printStackTrace();
		}
	}
	
}
