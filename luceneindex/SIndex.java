package luceneindex;

import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;

import java.io.File;
import java.io.IOException;

public class SIndex
{
		public QueryParser qp;
		public IndexSearcher is;
		public IndexReader dir;
		public Query q;

			public SIndex(String indexDir) throws IOException
			{
			/*directory creation and initialization to store index*/
			IndexReader dir = DirectoryReader.open(FSDirectory.open(new File(indexDir)));
			//IndexReader r = new IndexReader(dir);
			is = new IndexSearcher(dir);
			Analyzer analyze = new StandardAnalyzer(Version.LUCENE_47);
			/*query parser execution*/
			qp = new QueryParser(Version.LUCENE_47,Lconstants.CONTENTS,analyze);
			}

			

/*return the best documents*/
public TopDocs search(String searchQuery) throws IOException,ParseException
	{
	   q = qp.parse(searchQuery);
	   return is.search(q,Lconstants.MAX_SEARCH);
	}

public Document getDoc(ScoreDoc score) throws CorruptIndexException,IOException
	{
		   return is.doc(score.doc);	
	}

/*close searcher*/
public void close() throws IOException
	{
	   dir.close();
	}
}