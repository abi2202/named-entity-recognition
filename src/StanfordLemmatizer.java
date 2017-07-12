/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ner;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.Properties;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class StanfordLemmatizer {

    protected StanfordCoreNLP pipeline;

    public StanfordLemmatizer() {
       
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

     
        this.pipeline = new StanfordCoreNLP(props);
    }

    public List<String> lemmatize(String documentText)
    {
        List<String> lemmas = new LinkedList<String>();
        
        Annotation document = new Annotation(documentText);
       
        this.pipeline.annotate(document);
       
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
           
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
               
                lemmas.add(token.get(LemmaAnnotation.class));
            }
        }
        System.out.print(lemmas);
        //println(lemmas);
        return lemmas;
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("Starting Stanford Lemmatizer");
        
        FileInputStream fstream = new FileInputStream("C:\\Users\\ssent\\Documents\\NetBeansProjects\\practice\\tagger\\inputabbr.txt");
 DataInputStream in = new DataInputStream(fstream);
 BufferedReader br = new BufferedReader(new InputStreamReader(in));
  FileWriter q = new FileWriter("C:\\Users\\ssent\\Documents\\NetBeansProjects\\practice\\tagger\\outlem.txt",true);
 BufferedWriter out =new BufferedWriter(q);
 String text ="";
 
 while((text = br.readLine())!=null)
 {
     StanfordLemmatizer slem = new StanfordLemmatizer();
        List<String> sb = slem.lemmatize(text);
        
       // System.out.println(slem.lemmatize(text));
        out.write(sb.toString());
        out.newLine();
       
 }
 
    }

}
