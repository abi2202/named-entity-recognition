
package ner;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Test {

    public static List<String> ngrams(int n, String str) throws IOException {
        ArrayList<String> ngrams = new ArrayList<String>();
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - n + 1; i++)
            ngrams.add(concat(words, i, i+n));
       
        return ngrams;
        
    }

    public static String concat(String[] words, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++)
            sb.append((i > start ? " " : "") + words[i]);
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
    	//FileReader file = new FileReader("C:\\Users\\ssent\\Documents\\NetBeansProjects\\practice\\tagger\\input.txt");
         FileReader file = new FileReader("C:\\Users\\ssent\\Desktop\\NER\\input\\outputnofrm.txt");
  
        BufferedReader reader = new BufferedReader(file);
        FileWriter file1 = new FileWriter("C:\\Users\\ssent\\Documents\\NetBeansProjects\\practice\\tagger\\outputshing1.txt");
        PrintWriter writer = new PrintWriter(file1);
        String line = reader.readLine();
      
        StringBuilder sb = new StringBuilder();
        for (int n = 1; n <= 3; n++) {
                for (String ngram : ngrams(n, line )){
                	sb.append(ngram+" ");
               System.out.println(ngram);
                }
                sb.append("\n");
               
            System.out.println();
        }
        writer.write(sb.toString());
        writer.close();
    }
}


