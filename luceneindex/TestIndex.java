package luceneindex;

import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class TestIndex 
{
	Lindex index;
	SIndex si;
	String data = "M:/W1617/IR_Lucene/Dataset";
	String output = "M:/W1617/IR_Lucene/output";
	
	public static void main(String[] args)
	{
		TestIndex ti;
		try
		{
			ti = new TestIndex();
			ti.Index();
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Enter the string to search");
			String mysearch = keyboard.next();
			ti.search(mysearch);
			keyboard.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParseException pe)
		{
			pe.printStackTrace();
		}
	}
	private void Index() throws IOException
	{
		index = new Lindex(output);
		int IndexNum;
		long start = System.currentTimeMillis();
		IndexNum = index.doIndex(data, new Filefilter());
		long end = System.currentTimeMillis();
		index.close();
		System.out.println(IndexNum+" Indexing files,time taken: "+(end-start)+" ms");	
	}
	private void search(String searchQuery) throws IOException, ParseException
	{
	      si = new SIndex(output);
	      long startTime = System.currentTimeMillis();
	      TopDocs hits = si.search(searchQuery);
	      long endTime = System.currentTimeMillis();

	      System.out.println(hits.totalHits + " documents found. Time :" + (endTime - startTime) +" ms");
	      for(ScoreDoc scoreDoc : hits.scoreDocs) 
	      {
	         Document doc = si.getDoc(scoreDoc);
	         System.out.println("File: "+ doc.get(Lconstants.FILE_PATH));
	      }
	      //si.close();
	   }	
}
