/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Abiss
 */
import java.util.*;
import java.io.*;
import java.text.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

public class Abbreviation {

    HashMap mTestDefinitions = new HashMap();
    HashMap mStats = new HashMap();
    int truePositives = 0, falsePositives = 0, falseNegatives = 0, trueNegatives = 0;
    char delimiter = '\t';
    boolean testMode = false;

    static Logger s_logger = Logger.getLogger(Abbreviation.class.getName());
    
    private boolean isValidShortForm(String str) {
    	return (hasLetter(str) && (Character.isLetterOrDigit(str.charAt(0)) || (str.charAt(0) == '(')));
    }

    private boolean hasLetter(String str) {
		for (int i=0; i < str.length() ; i++)
		    if (Character.isLetter(str.charAt(i)))
			return true;
		return false;
    }

    private boolean hasCapital(String str) {
		for (int i=0; i < str.length() ; i++)
		    if (Character.isUpperCase(str.charAt(i)))
			return true;
		return false;
    }

    private void loadTrueDefinitions(String inFile) {
		String abbrString, defnString, str = "";
		Vector entry;
		HashMap definitions = mTestDefinitions;
	
		try {
		    BufferedReader fin = new BufferedReader(new FileReader (inFile));
		    while ((str = fin.readLine()) != null) {
			int j = str.indexOf(delimiter);
			abbrString = str.substring(0,j).trim();
			defnString = str.substring(j,str.length()).trim();		
			entry = (Vector)definitions.get(abbrString);
			if (entry == null)
			    entry = new Vector();
			entry.add(defnString);
			    definitions.put(abbrString, entry);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    System.out.println(str);
		}
    }
    
    private boolean isTrueDefinition(String shortForm, String longForm) {
		Vector entry;
		Iterator itr;
	
		entry = (Vector)mTestDefinitions.get(shortForm);
		if (entry == null)
		    return false;
		itr = entry.iterator();
		while(itr.hasNext()){
		    if (itr.next().toString().equalsIgnoreCase(longForm))
			return true;
		}
		return false;
    }

    public Map extractAbbrPairs(String data) {
	
    	data = StringEscapeUtils.unescapeHtml(data);
    	
		String str, tmpStr, longForm = "", shortForm = "";
		String currSentence = "";
		int openParenIndex, closeParenIndex = -1, sentenceEnd, newCloseParenIndex, tmpIndex = -1;
		boolean newParagraph = true;
		StringTokenizer shortTokenizer;
		HashMap candidates = new HashMap();
	
		try {
		    BufferedReader fin = new BufferedReader(new StringReader(data));
		    while ((str = fin.readLine()) != null) {
			if (str.length() == 0 || newParagraph && 
			    ! Character.isUpperCase(str.charAt(0))){
			    currSentence = "";
			    newParagraph = true;
			    continue;
			}
			newParagraph = false;
			str += " ";
			currSentence += str;
			openParenIndex =  currSentence.indexOf(" (");
			do {
			    if (openParenIndex > -1)
				openParenIndex++;
			    sentenceEnd = Math.max(currSentence.lastIndexOf(". "), currSentence.lastIndexOf(", "));
			    if ((openParenIndex == -1) && (sentenceEnd == -1)) {
				//Do nothing
			    }
			    else if (openParenIndex == -1) {
				currSentence = currSentence.substring(sentenceEnd + 2);
			    } else if ((closeParenIndex = currSentence.indexOf(')',openParenIndex)) > -1){
				sentenceEnd = Math.max(currSentence.lastIndexOf(". ", openParenIndex), 
						       currSentence.lastIndexOf(", ", openParenIndex));
				if (sentenceEnd == -1)
				    sentenceEnd = -2;
				longForm = currSentence.substring(sentenceEnd + 2, openParenIndex);
				shortForm = currSentence.substring(openParenIndex + 1, closeParenIndex);
			    }
			    if (shortForm.length() > 0 || longForm.length() > 0) {
				if (shortForm.length() > 1 && longForm.length() > 1) {
				    if ((shortForm.indexOf('(') > -1) && 
					((newCloseParenIndex = currSentence.indexOf(')', closeParenIndex + 1)) > -1)){
					shortForm = currSentence.substring(openParenIndex + 1, newCloseParenIndex);
					closeParenIndex = newCloseParenIndex;
				    }
				    if ((tmpIndex = shortForm.indexOf(", ")) > -1)
					shortForm = shortForm.substring(0, tmpIndex);			    
				    if ((tmpIndex = shortForm.indexOf("; ")) > -1)
					shortForm = shortForm.substring(0, tmpIndex);
				    shortTokenizer = new StringTokenizer(shortForm);
				    if (shortTokenizer.countTokens() > 2 || shortForm.length() > longForm.length()) {
					// Long form in ( )
					tmpIndex = currSentence.lastIndexOf(" ", openParenIndex - 2);
					tmpStr = currSentence.substring(tmpIndex + 1, openParenIndex - 1);
					longForm = shortForm;
					shortForm = tmpStr;
					if (! hasCapital(shortForm))
					    shortForm = "";
				    }
				    if (isValidShortForm(shortForm)){
				    	String best = extractAbbrPair(shortForm.trim(), longForm.trim());
				    	if (best != null && best.length() > 1) {
				    		candidates.put(shortForm.trim().toUpperCase(), best);
				    		s_logger.debug("Short: " + shortForm + " Long: " + best);
				    	}
				    }
				}
				currSentence = currSentence.substring(closeParenIndex + 1);
			    } else if (openParenIndex > -1) {
				if ((currSentence.length() - openParenIndex) > 200)
				    // Matching close paren was not found
				    currSentence = currSentence.substring(openParenIndex + 1);
				break; // Read next line
			    }
			    shortForm = "";
			    longForm = "";
			} while ((openParenIndex =  currSentence.indexOf(" (")) > -1);
		    }
		    fin.close();
		} catch (Exception ioe) {
		    ioe.printStackTrace();
		    System.out.println(currSentence);
		    System.out.println(tmpIndex);
		}
		    return candidates;
    }
    
    public Map extractAbbrPairsByLongFormKey(String data) {
    	
    	data = StringEscapeUtils.unescapeHtml(data);
    	
		String str, tmpStr, longForm = "", shortForm = "";
		String currSentence = "";
		int openParenIndex, closeParenIndex = -1, sentenceEnd, newCloseParenIndex, tmpIndex = -1;
		boolean newParagraph = true;
		StringTokenizer shortTokenizer;
		HashMap candidates = new HashMap();
	
		try {
		    BufferedReader fin = new BufferedReader(new StringReader(data));
		    while ((str = fin.readLine()) != null) {
			if (str.length() == 0 || newParagraph && 
			    ! Character.isUpperCase(str.charAt(0))){
			    currSentence = "";
			    newParagraph = true;
			    continue;
			}
			newParagraph = false;
			str += " ";
			currSentence += str;
			openParenIndex =  currSentence.indexOf(" (");
			do {
			    if (openParenIndex > -1)
				openParenIndex++;
			    sentenceEnd = Math.max(currSentence.lastIndexOf(". "), currSentence.lastIndexOf(", "));
			    if ((openParenIndex == -1) && (sentenceEnd == -1)) {
				//Do nothing
			    }
			    else if (openParenIndex == -1) {
				currSentence = currSentence.substring(sentenceEnd + 2);
			    } else if ((closeParenIndex = currSentence.indexOf(')',openParenIndex)) > -1){
				sentenceEnd = Math.max(currSentence.lastIndexOf(". ", openParenIndex), 
						       currSentence.lastIndexOf(", ", openParenIndex));
				if (sentenceEnd == -1)
				    sentenceEnd = -2;
				longForm = currSentence.substring(sentenceEnd + 2, openParenIndex);
				shortForm = currSentence.substring(openParenIndex + 1, closeParenIndex);
			    }
			    if (shortForm.length() > 0 || longForm.length() > 0) {
				if (shortForm.length() > 1 && longForm.length() > 1) {
				    if ((shortForm.indexOf('(') > -1) && 
					((newCloseParenIndex = currSentence.indexOf(')', closeParenIndex + 1)) > -1)){
					shortForm = currSentence.substring(openParenIndex + 1, newCloseParenIndex);
					closeParenIndex = newCloseParenIndex;
				    }
				    if ((tmpIndex = shortForm.indexOf(", ")) > -1)
					shortForm = shortForm.substring(0, tmpIndex);			    
				    if ((tmpIndex = shortForm.indexOf("; ")) > -1)
					shortForm = shortForm.substring(0, tmpIndex);
				    shortTokenizer = new StringTokenizer(shortForm);
				    if (shortTokenizer.countTokens() > 2 || shortForm.length() > longForm.length()) {
					// Long form in ( )
					tmpIndex = currSentence.lastIndexOf(" ", openParenIndex - 2);
					tmpStr = currSentence.substring(tmpIndex + 1, openParenIndex - 1);
					longForm = shortForm;
					shortForm = tmpStr;
					if (! hasCapital(shortForm))
					    shortForm = "";
				    }
				    if (isValidShortForm(shortForm)){
				    	String best = extractAbbrPair(shortForm.trim(), longForm.trim());
				    	if (best != null && best.length() > 1) {
				    		best = RegExpLib.getCleanToken(best);
				    		candidates.put(RegExpLib.getCleanToken(best.toLowerCase()), shortForm.trim());
				    		s_logger.debug("Long" + best  + "Short: " + shortForm.trim() + " :" + RegExpLib.getCleanToken(best.toLowerCase()) + ":");
				    	}
				    }
				}
				currSentence = currSentence.substring(closeParenIndex + 1);
			    } else if (openParenIndex > -1) {
				if ((currSentence.length() - openParenIndex) > 200)
				    // Matching close paren was not found
				    currSentence = currSentence.substring(openParenIndex + 1);
				break; // Read next line
			    }
			    shortForm = "";
			    longForm = "";
			} while ((openParenIndex =  currSentence.indexOf(" (")) > -1);
		    }
		    fin.close();
		} catch (Exception ioe) {
		    ioe.printStackTrace();
		    System.out.println(currSentence);
		    System.out.println(tmpIndex);
		}
		    
		return candidates;
    }

    private String findBestLongForm(String shortForm, String longForm) {
		int sIndex;
		int lIndex;
		char currChar;
	
		sIndex = shortForm.length() - 1;
		lIndex = longForm.length() - 1;
		for ( ; sIndex >= 0; sIndex--) {
		    currChar = Character.toLowerCase(shortForm.charAt(sIndex));
		    if (!Character.isLetterOrDigit(currChar))
			continue;
		    while (((lIndex >= 0) && (Character.toLowerCase(longForm.charAt(lIndex)) != currChar)) ||
			   ((sIndex == 0) && (lIndex > 0) && (Character.isLetterOrDigit(longForm.charAt(lIndex - 1)))))
			lIndex--;
		    if (lIndex < 0)
			return null;
		    lIndex--;
		}
		lIndex = longForm.lastIndexOf(" ", lIndex) + 1;
		return longForm.substring(lIndex);
    }

    private String extractAbbrPair(String shortForm, String longForm) {
		String bestLongForm;
		StringTokenizer tokenizer;
		int longFormSize, shortFormSize;
	
		if (shortForm.length() == 1)
		    return "";
		bestLongForm = findBestLongForm(shortForm, longForm);
		if (bestLongForm == null)
		    return "";
		tokenizer = new StringTokenizer(bestLongForm, " \t\n\r\f-");
		longFormSize = tokenizer.countTokens();
		shortFormSize = shortForm.length();
		for (int i=shortFormSize - 1; i >= 0; i--)
		    if (!Character.isLetterOrDigit(shortForm.charAt(i)))
			shortFormSize--;
		if (bestLongForm.length() < shortForm.length() || 
		    bestLongForm.indexOf(shortForm + " ") > -1 ||
		    bestLongForm.endsWith(shortForm) ||
		    longFormSize > shortFormSize * 2 ||
		    longFormSize > shortFormSize + 5 ||
		    shortFormSize > 10)
		    return "";
	
		if (testMode) {
		    if (isTrueDefinition(shortForm, bestLongForm)) {
			System.out.println(shortForm + delimiter + bestLongForm + delimiter + "TP");
			truePositives++;
		    }
		    else {
			falsePositives++;
			System.out.println(shortForm + delimiter + bestLongForm + delimiter + "FP");
		    }	
		}
	
		return bestLongForm;
    }
    

    private static void usage() {
        System.err.println("Usage: ExtractAbbrev [-options] <filename>");
        System.err.println("       <filename> contains text from which abbreviations are extracted" );
        System.err.println("       -testlist <file> = list of true abbreviation definition pairs");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
		String shortForm, longForm, defnString, str;
		Abbreviation extractAbbrev = new Abbreviation();
		Vector candidates;	
		String[] candidate;
		String filename =  null;
                String testList = "";
                {
                    FileReader file = new FileReader("in.txt");
                    BufferedReader reader = new BufferedReader(file);
                    String line = reader.readLine();
                    while (line != null) {
                        testList += line;
                        line = reader.readLine();
                    }
                  //  System.out.println(testList); 
                }
		System.out.println("OK");
		Map vec = (HashMap)extractAbbrev.extractAbbrPairs(testList);
                String[] testData = testList.split(" ");
                //System.out.println(testData[0]);
		System.out.println("size " + vec.size());
                String replacedText ="";
               for(int a=0;a<testData.length;a++){
                   if(vec.containsKey(testData[a])){
                    for (Iterator it=vec.entrySet().iterator(); it.hasNext(); ) {
                        Map.Entry entry = (Map.Entry)it.next();
                         String key = entry.getKey().toString();
                         if(key.equals(testData[a])){
                             replacedText += testData[a].replaceAll(key, entry.getValue().toString())+" ";
                         }
                     }
                   }
                   else
                   {
                       replacedText += testData[a]+" ";
                   }
               }
               System.out.println(replacedText);
               StringBuilder sb = new StringBuilder();
               sb.append(replacedText);
               PrintWriter pw = new PrintWriter("C:\\Users\\NetBeansProjects\\inputabbr.txt");
               pw.write(sb.toString());
               pw.close();
               
    /*	for (Iterator it=vec.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry)it.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            
            System.out.println("Short: " + key + " Long: " + value);
    	}*/
	}
}




