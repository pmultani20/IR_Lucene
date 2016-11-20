package luceneindex;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Lindex 
{
	/*method to get files from folder*/
	private Document getFile(File file) throws IOException
	{
		Document doc = new Document();
		/*setting index for filepath,filename and file contents*/
		Field filepath = new Field(Lconstants.FILE_PATH,file.getCanonicalPath(),TextField.TYPE_STORED);
		Field filename = new Field(Lconstants.FILE_NAME,file.getName(),TextField.TYPE_STORED);
		String contents = Stemmer.applyPorterStemmer(file);
		System.out.println(contents + Lconstants.CONTENTS);
	    Field filecontent = new Field(Lconstants.CONTENTS,contents,TextField.TYPE_STORED);
		/*add the above indexes to the Document object*/
		doc.add(filepath);
		doc.add(filename);
		doc.add(filecontent);
		return doc;
	}
	/*object for IndexWriter*/
	private IndexWriter iw;
	/*method for generating directory to store indexes*/
	public Lindex(String indexDir) throws IOException
	{
		Directory dir = FSDirectory.open(new File(indexDir));
		/*index for above directory*/
		Analyzer analyze = new StandardAnalyzer(Version.LUCENE_47);
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_47,analyze);
		iw = new IndexWriter(dir,conf);
		iw.deleteAll();
	}
	public void close() throws CorruptIndexException, IOException
	{
		iw.close();
	}
	/*private void updateDoc(File file) throws IOException
	{
		  Document doc = new Document();
		  iw.updateDocument(new Term(Lconstants.FILE_NAME,file.getName()),doc); 
	      //iw.close();
	} */
	/*method for indexing*/
	private void FileIndex(File file)  throws IOException
	{
		Document doc = getFile(file);
		//Document doc = new Document();
		System.out.println(file.getCanonicalPath());
		iw.addDocument(doc);
		
		//iw.updateDocument(new Term(Lconstants.FILE_NAME,file.getName()),doc);
		//updateDoc(file);
	}
	public int doIndex(String dirpath, FileFilter filter) throws IOException
	{
		
		File[] files = new File(dirpath).listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				doIndex(file.getAbsolutePath(),filter);
			}
			if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead())
			{
				System.out.println("calling fileindex:"+file.getName());
				FileIndex(file);
			}
		}
		System.out.println("index creation:"+iw.numDocs());
		return iw.numDocs();
		}
}
