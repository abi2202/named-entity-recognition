
package ner;

import java.io.*;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
 
public class tag {
 
 public static void main(String[] args) throws IOException,
 ClassNotFoundException {
 
 String tagged;
 MaxentTagger tagger = new MaxentTagger("tagger/english-left3words-distsim.tagger");
 String sample;
 
 FileInputStream fstream = new FileInputStream("tagger/outputshing1.txt");
 DataInputStream in = new DataInputStream(fstream);
 BufferedReader br = new BufferedReader(new InputStreamReader(in));
 
 while((sample = br.readLine())!=null)
 {
 tagged = tagger.tagString(sample);
 FileWriter q = new FileWriter("tagger/output.txt",true);
 BufferedWriter out =new BufferedWriter(q);
 out.write(tagged);
 out.newLine();
 out.close();
 }
 
}
 
}
