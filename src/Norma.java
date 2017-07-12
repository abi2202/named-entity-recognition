/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package norma;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Abiss
 */
public class Norma {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        


       
            BufferedReader br = new BufferedReader(new FileReader( "C:\\\\Users\\\\ssent\\\\Documents\\\\NetBeansProjects\\\\practice\\\\tagger\\\\output3.txt" ));

            BufferedWriter bw = new BufferedWriter(new FileWriter( "C:\\\\Users\\\\ssent\\\\Documents\\\\NetBeansProjects\\\\practice\\\\tagger\\\\outputnofrm.txt" ));

            String line;
            while ((line = br.readLine()) != null) {
                line=line.replace(".","");
                line=line.replace("+","");
                line=line.replace(";","");
                line=line.replace("/","");
                line=line.replace(",","");
                line=line.replace("_","");
                line=line.replace("-","");
                line=line.replace("%","");
                line=line.replace("&","");
                line=line.replace("<","");
                line=line.replace(">","");
                line=line.toLowerCase();
                bw.write( line + "\n  \t \n" );
            }
            br.close();
            bw.close();
        
       /** String testList = "";

String characters = (SecondTextField.getText()); //String to read the user input
            int length = characters.length();  //change the string characters to length

         for(int i = 0; i < length; i++)  //to check the characters of string..
         {             
            char character = characters.charAt(i);          

            if(Character.isUpperCase(character)) 
            {
                SecondTextField.setText("" + characters.toLowerCase());

            }
{
    FileReader file = new FileReader("C:\\Users\\ssent\\Documents\\NetBeansProjects\\practice\\tagger\\input.txt");
    BufferedReader reader = new BufferedReader(file);

    // don't declare it here
    // String key = "";
    String line = reader.readLine();

    while (line != null) {
        testList += line;
        line = reader.readLine();
    }
    System.out.println(testList); // so key works
}
    **/            
    }
}
