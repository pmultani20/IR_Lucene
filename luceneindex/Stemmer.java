package luceneindex;

import org.tartarus.snowball.ext.PorterStemmer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Stemmer
{
	public static String applyPorterStemmer(File input) throws IOException 
	{
		PorterStemmer ps = new PorterStemmer();
		String output = "";
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line = "";
		while((line = br.readLine()) != null)
		{
			//System.out.println(line);
			String[] words = line.split(" ");
			for(int i = 0; i< words.length; i++)
			{
				//System.out.println(words[i]);
				ps.setCurrent(words[i]);
		        ps.stem();
		        output = output + " "+ ps.getCurrent();
				//System.out.println(output);
			}
		}
		return output;
		
	}
}