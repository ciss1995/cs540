////////////////////////////////////////////////////////////////////////////////
// This File:        C
// Other Files:      (name of all other files if any)
// Semester:         Fall 2017
//
// Author:           Cheick
// Email:            ccisse@wisc.edu
// CS Login:         cheick
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
package aihm7;
import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "/Users/cheickcisse/Documents/workspace/aihm7/src/aihm7/WARC201709_wid.txt";
    		//"./WARC201709_wid.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }
    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
        
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int count = 0;
            //TODO count occurence of w
            // Get number of occurence
            for(int i = 0; i < corpus.size(); i++){
            	if (corpus.get(i) == w) count++;
            }
            //Print
            System.out.println(count);
            System.out.println(String.format("%.7f",count/(double)corpus.size()));
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            //TODO generate
            //Copy Corpus
            ArrayList<Integer> SortedCorpus = new ArrayList<Integer>(corpus);
            // Create a multinomial of array for triple (i, li , ri )
            ArrayList<double[]> multinomial = new ArrayList<double[]>();
            //Sort copied corpus
            Collections.sort(SortedCorpus);
            //Get r
            double r = (double)n1/(double)n2;
            int count = 0;
            double p = 0.0; 
            int t = 0;
            //Search 
            for (int i = 0; i < SortedCorpus.size(); i++){
            	//Get current index
            	int currIndex = SortedCorpus.get(i);
            	//Loop while the index is still in sorted corpus
            	while (i < SortedCorpus.size() && SortedCorpus.get(i).equals(currIndex)){
            		//Increment the number of occurrence
            		count++;
            		i++;
            	}
            	//decrease i due to extra incrementation above
            	i -= 1;
            	//get p
            	p = count/(double)corpus.size();
            	//create array for triple
            	double arr [] = new double[3];
            	//Index 0 get the index corresponding to the interval
            	arr[0] = SortedCorpus.get(i);	
            	//Index 2 get p
            	arr[2] = p;
            	// if first triple, li is 0/ otherwise take previous p
            	if (t > 0) arr[1] = multinomial.get(t-1)[2];
            	t++;
            	//add only non null
            	if (p != 0){
            		multinomial.add(arr);
            	}
            	//search if r is with in the interval and print
            	for (int j = 0; j < multinomial.size(); j++){
            		if (r <= multinomial.get(j)[2] && r >= multinomial.get(j)[1]) {
            			System.out.println((int)multinomial.get(j)[0]);
            			System.out.println(String.format("%.7f",multinomial.get(j)[1]));
            			System.out.println(String.format("%.7f",multinomial.get(j)[2]));
            			i = SortedCorpus.size();
            		}
            	}
            }

        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            //TODO
            //Search through the corpus
            for (int i = 0; i < corpus.size() - 1; i++){
            	//Check for history
            	if (corpus.get(i).equals(h)){
            		words_after_h.add(corpus.get(i+1));
            		//Check if next is w
            		if (corpus.get(i+1).equals(w)) count++;
            	}	
            }
            //output 
            System.out.println(count);
            System.out.println(words_after_h.size());
            System.out.println(String.format("%.7f",count/(double)words_after_h.size()));
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            //TODO
            //Get r
            double r = (double)n1/(double)n2;
            
            ArrayList<double[]> multinomial = new ArrayList<double[]>();
            //Copy Corpus
            ArrayList<Integer> SortedCorpus = new ArrayList<Integer>(corpus);
            //Sort copied corpus
            Collections.sort(SortedCorpus);
            double test = 0.0;
            int keepTrack = 0;
            for (int i = 0; i < SortedCorpus.size(); i++){
            	int w = SortedCorpus.get(i);
            	//Loop while the index is still in sorted corpus
            	while (i < SortedCorpus.size() && SortedCorpus.get(i).equals(w)) {
            		i++;
            	}
            	i -= 1;
            	//create a new list of words after h for new word
            	ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            	int count = 0;
                //Search through the corpus
                for (int j = 0; j < corpus.size() - 1; j++){
                	//Check for history
                	if (corpus.get(j).equals(h)){
                		words_after_h.add(corpus.get(j+1));
                		//Check if next is w
                		if (corpus.get(j+1).equals(w)) count++;
                	}	
                }
                //create array for triplet
                double arr[] = new double[3];
                //First value take the word
                arr[0] = w;
                //calculate 
                double p = count/(double)words_after_h.size();
                test += p;
                //Skip if empty 
                if (multinomial.size() == 0){
                	arr[1] = 0.0;
                	arr[2] = p;
                	if (test != 0) multinomial.add(arr);
                }else {
                	arr[1] = multinomial.get(keepTrack)[2];
                	arr[2] = test;
                	if (test != 0) multinomial.add(arr);
                	keepTrack++;
                }    
            	//search if r is with in the interval and print      
            	for (int j = 0; j < multinomial.size(); j++){
            		if (r <= multinomial.get(j)[2] && r >= multinomial.get(j)[1]) {
            			System.out.println((int)multinomial.get(j)[0]);
            			System.out.println(String.format("%.7f",multinomial.get(j)[1]));
            			System.out.println(String.format("%.7f",multinomial.get(j)[2]));
            			i = SortedCorpus.size();
            		}
            	}                		
            }  
        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            //TODO
            //Search through the corpus
            for (int i = 0; i < corpus.size() - 2; i++){
            	//Check for history
            	if (corpus.get(i).equals(h1)){
            		if (corpus.get(i+1).equals(h2)) {
            			words_after_h1h2.add(corpus.get(i+2));
            			//Check if next is w
            			if (corpus.get(i+2).equals(w)) count++;		
            		}
            	}	
            }
            //output 
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            if(words_after_h1h2.size() == 0)
                System.out.println("undefined");
            else
                System.out.println(String.format("%.7f",count/(double)words_after_h1h2.size()));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            //TODO
            //Get r
            double r = (double)n1/(double)n2;
            
            ArrayList<double[]> multinomial = new ArrayList<double[]>();
            //Copy Corpus
            ArrayList<Integer> SortedCorpus = new ArrayList<Integer>(corpus);
            //Sort copied corpus
            Collections.sort(SortedCorpus);
            double test = 0.0;
            int keepTrack = 0;
            //Search 
            for (int i = 0; i < SortedCorpus.size(); i++){
            	int w = SortedCorpus.get(i);
            	while (i < SortedCorpus.size() && SortedCorpus.get(i).equals(w)) {
            		i++;
            	}
            	i -= 1;
            	ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            	int count = 0;
                //Search through the corpus
                for (int j = 0; j < corpus.size() - 2; j++){
                	//Check for history
                	if (corpus.get(j).equals(h1)){
                		if (corpus.get(j+1).equals(h2)){
                		words_after_h1h2.add(corpus.get(j+2));
                		//Check if next is w
                		if (corpus.get(j+2).equals(w)) count++;
                		}
                	}	
                }
                //Create array triplet
                double arr[] = new double[3];
                arr[0] = w;
                double p = count/(double)words_after_h1h2.size();
                test += p;
                //Skip if 0
                if (multinomial.size() == 0){
                	arr[1] = 0.0;
                	arr[2] = p;
                	if (test != 0) multinomial.add(arr);
                }else {
                	arr[1] = multinomial.get(keepTrack)[2];
                	arr[2] = test;
                	if (test != 0) multinomial.add(arr);
                	keepTrack++;
                }    
            	//search if r is with in the interval and print      
            	for (int j = 0; j < multinomial.size(); j++){
            		if (r <= multinomial.get(j)[2] && r >= multinomial.get(j)[1]) {
            			System.out.println((int)multinomial.get(j)[0]);
            			System.out.println(String.format("%.7f",multinomial.get(j)[1]));
            			System.out.println(String.format("%.7f",multinomial.get(j)[2]));
            			i = SortedCorpus.size();
            		} else if (words_after_h1h2.size() == 0){
            			System.out.println("undefined");
            			i = SortedCorpus.size();
            		}
            	}                	
            }           
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);
            
            //Copy Corpus
            ArrayList<Integer> SortedCorpus = new ArrayList<Integer>(corpus);
            //Sort copied corpus
            Collections.sort(SortedCorpus);
            //t == 0 *********************************************************
            if(t == 0){
                // TODO Generate first word using r get h1 ***************
                // Create a multinomial of array for triple (i, li , ri )
                ArrayList<double[]> multinomial = new ArrayList<double[]>();
                int count = 0;
                double p = 0.0; 
                int l = 0;
            	double r = rng.nextDouble();
                //Search 
                for (int i = 0; i < corpus.size(); i++){
                	//Get current index
                	int currIndex = SortedCorpus.get(i);
                	//Loop while the index is still in sorted corpus
                	while (i < SortedCorpus.size() && SortedCorpus.get(i).equals(currIndex)){
                		//Increment the number of occurrence
                		count++;
                		i++;
                	}
                	//decrease i due to extra incrementation above
                	i -= 1;
                	//get p
                	p = count/(double)corpus.size();
                	//create array for triple
                	double arr [] = new double[3];
                	//Index 0 get the index corresponding to the interval
                	arr[0] = SortedCorpus.get(i);	
                	//Index 2 get p
                	arr[2] = p;
                	// if first triple, li is 0/ otherwise take previous p
                	if (l > 0) arr[1] = multinomial.get(l-1)[2];
                	l++;
                	//add only non null
                	if (p != 0){
                		multinomial.add(arr);
                	}
                	//search if r is with in the interval and print
                	for (int j = 0; j < multinomial.size(); j++){
                		if (r <= multinomial.get(j)[2] && r >= multinomial.get(j)[1]) {
                			h1 = ((int)multinomial.get(j)[0]);
                			i = SortedCorpus.size();
                		}
                	}
                }
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }
                
                // TODO Generate second word using r get h2 ****************
                multinomial = new ArrayList<double[]>();
                r = rng.nextDouble();
                double test = 0.0;
                int keepTrack = 0;
                Collections.sort(SortedCorpus);
                for (int i = 0; i < SortedCorpus.size(); i++){
                	int wr = SortedCorpus.get(i);
                	//Loop while the index is still in sorted corpus
                	while (i < SortedCorpus.size() && SortedCorpus.get(i).equals(wr)) {
                		i++;
                	}
                	i -= 1;
                	//create a new list of words after h for new word
                	ArrayList<Integer> words_after_h = new ArrayList<Integer>();
                	count = 0;
                    //Search through the corpus
                    for (int j = 0; j < corpus.size() - 1; j++){
                    	//Check for history
                    	if (corpus.get(j).equals(h1)){
                    		words_after_h.add(corpus.get(j+1));
                    		//Check if next is w
                    		if (corpus.get(j+1).equals(wr)) count++;
                    	}	
                    }
                    //create array for triplet
                    double arr[] = new double[3];
                    //First value take the word
                    arr[0] = wr;
                    //calculate 
                    p = count/(double)words_after_h.size();
                    test += p;
                    //Skip if empty 
                    if (multinomial.size() == 0){
                    	arr[1] = 0.0;
                    	arr[2] = p;
                    	if (test != 0) multinomial.add(arr);
                    }else {
                    	arr[1] = multinomial.get(keepTrack)[2];
                    	arr[2] = test;
                    	if (test != 0) multinomial.add(arr);
                    	keepTrack++;
                    }    
                	//search if r is with in the interval and print      
                	for (int j = 0; j < multinomial.size(); j++){
                		if (r <= multinomial.get(j)[2] && r >= multinomial.get(j)[1]) {
                			h2 = (int)multinomial.get(j)[0];
                			i = SortedCorpus.size();
                		}
                	}                		
                }                   
                System.out.println(h2);
            }
            // t == 1***************************************************************
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                // TODO Generate second word using r                  
                double r = rng.nextDouble();
                ArrayList<double[]> multinomial = new ArrayList<double[]>();
                double test = 0.0;
                int keepTrack = 0;
                Collections.sort(SortedCorpus);
                for (int i = 0; i < SortedCorpus.size(); i++){
                	int wr = SortedCorpus.get(i);
                	//Loop while the index is still in sorted corpus
                	while (i < SortedCorpus.size() && SortedCorpus.get(i).equals(wr)) {
                		i++;
                	}
                	i -= 1;
                	//create a new list of words after h for new word
                	ArrayList<Integer> words_after_h = new ArrayList<Integer>();
                	int count = 0;
                    //Search through the corpus
                    for (int j = 0; j < corpus.size() - 1; j++){
                    	//Check for history
                    	if (corpus.get(j).equals(h1)){
                    		words_after_h.add(corpus.get(j+1));
                    		//Check if next is w
                    		if (corpus.get(j+1).equals(wr)) count++;
                    	}	
                    }
                    //create array for triplet
                    double arr[] = new double[3];
                    //First value take the word
                    arr[0] = wr;
                    //calculate 
                    double p = count/(double)words_after_h.size();
                    test += p;
                    //Skip if empty 
                    if (multinomial.size() == 0){
                    	arr[1] = 0.0;
                    	arr[2] = p;
                    	if (test != 0) multinomial.add(arr);
                    }else {
                    	arr[1] = multinomial.get(keepTrack)[2];
                    	arr[2] = test;
                    	if (test != 0) multinomial.add(arr);
                    	keepTrack++;
                    }    
                	//search if r is with in the interval and print      
                	for (int j = 0; j < multinomial.size(); j++){
                		if (r <= multinomial.get(j)[2] && r >= multinomial.get(j)[1]) {
                			h2 = (int)multinomial.get(j)[0];
                			i = SortedCorpus.size();
                		}
                	}                		
                }                
                System.out.println(h2);
            }
            // t == 2****************************************************************
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }
            //***********************************************************************
            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
                // TODO Generate new word using h1,h2
                ArrayList<double[]> multinomial = new ArrayList<double[]>();    
                //Sort copied corpus
                double test = 0.0;
                int keepTrack = 0;
                Collections.sort(SortedCorpus);
                //Search 
                for (int i = 0; i < SortedCorpus.size(); i++){
                	int wr = SortedCorpus.get(i);
                	while (i < SortedCorpus.size() && SortedCorpus.get(i).equals(wr)) {
                		i++;
                	}
                	i -= 1;
                	ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
                	int count = 0;
                    //Search through the corpus
                    for (int j = 0; j < corpus.size() - 2; j++){
                    	//Check for history
                    	if (corpus.get(j).equals(h1)){
                    		if (corpus.get(j+1).equals(h2)){
                    		words_after_h1h2.add(corpus.get(j+2));
                    		//Check if next is w
                    		if (corpus.get(j+2).equals(wr)) count++;
                    		}
                    	}	
                    }
                    //Create array triplet
                    double arr[] = new double[3];
                    arr[0] = wr;
                    double p = count/(double)words_after_h1h2.size();
                    test += p;
                    //Skip if 0
                    if (multinomial.size() == 0){
                    	arr[1] = 0.0;
                    	arr[2] = p;
                    	if (test != 0) multinomial.add(arr);
                    }else {
                    	arr[1] = multinomial.get(keepTrack)[2];
                    	arr[2] = test;
                    	if (test != 0) multinomial.add(arr);
                    	keepTrack++;
                    }    
                	//search if r is with in the interval and print      
                	for (int j = 0; j < multinomial.size(); j++){
                		if (r <= multinomial.get(j)[2] && r >= multinomial.get(j)[1]) {
                			w = (int)multinomial.get(j)[0];
                			i = SortedCorpus.size();
                		} 
                	}                	
                }                          
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }
        return;
    }
}
