import java.io.*;
import java.util.*;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
public class Preprocessing {

  public static void main(String[] args) throws IOException {
    PrintWriter out;
   out = new PrintWriter("C:\\Users\\ssent\\Documents\\NetBeansProjects\\practice\\tagger\\outputra.txt");
     
    Properties props=new Properties();
    props.setProperty("annotators","tokenize, ssplit, pos,lemma");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    Annotation annotation;  
    	String readString = null;
	    PrintWriter pw = null;
	    BufferedReader br = null;
	    br = new BufferedReader ( new FileReader ( "C:\\Users\\ Documents\\NetBeansProjects\\practice\\tagger\\inputabbr.txt" )  ) ;
	   // pw = new PrintWriter ( new BufferedWriter ( new FileWriter ( "C:\\Users \\Documents\\NetBeansProjects\\practice\\tagger\\outputre.txt", false )  )  ) ;      
            String x = null;
	    while  (( readString = br.readLine ())  != null)   {
	         
    	annotation = new Annotation(x);
 
    pipeline.annotate(annotation);    //System.out.println("LamoohAKA");
    pipeline.prettyPrint(annotation, out);
	    }
	    br.close (  ) ;
    out.close (  ) ;
    System.out.println("Done...");
   
       
  }
  
}
