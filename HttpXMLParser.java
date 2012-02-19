import java.net.* ;
import java.io.* ;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.*;

public class HttpXMLParser implements Runnable 
{
    public static final int NOTHING = 0;
    public static final int REQUESTING = 1;
    public static final int RECEIVING = 2;
    public static final int PARSING = 3;
    public static final int DONE = 4;
    public static final int ERR_READERR = 5 ;
    private int _status ;
    private URL _url ;
    private tumorSearchResult[] _tsr ;

    public HttpXMLParser(String url)
    {
	try
	    {
		_url = new URL (url) ;
	    }
	catch (MalformedURLException mue)
	    {
		System.err.println ("Dick verkackt!");
		System.exit(1) ;
	       
	    }
    }

    public void run()
    {
	_status = REQUESTING ;
	LinkedList<tumorSearchResult> l = new LinkedList<tumorSearchResult>() ;
	try
	    {
		_status = RECEIVING ;
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(_url.openStream());
		doc.getDocumentElement().normalize() ;
		NodeList nl = doc.getElementsByTagName("result_list_item") ;
		for (int i = 0; i < nl.getLength(); i++)
		    {
			NodeList nl2 = nl.item(i).getChildNodes() ;
			Node metanode = null, titlenode = null;
			String title = null, artist = null, mp3id = null;
			for (int j = 0; j < nl2.getLength(); j++)
			    {
				String nodename = nl2.item(j).getNodeName();
				if (nodename.equals("title"))
				    {
					titlenode = nl2.item(j) ;
				    }
				else if(nodename.equals("meta"))
				    {
					metanode = nl2.item(j) ;
				    }		
			    }
			title = titlenode.getTextContent();
			nl2 = metanode.getChildNodes();
			for (int j = 0; j < nl2.getLength(); j++)
			    {
				String nodename = nl2.item(j).getNodeName() ;
				if (nodename.equals("mp3_url_id"))
				    {
					mp3id = nl2.item(j).getTextContent();
				    }
				else if (nodename.equals("creator"))
				    {
					artist = nl2.item(j).getTextContent();
				    }
			    }			
			l.add(new tumorSearchResult (artist, title, mp3id)) ;
			
		    }
	    }
	catch (IOException ioe)
	    {
		_status = ERR_READERR;
	    }
	catch (Exception e)
	    {
		System.err.println ("Other Exception!") ;
	    }
	_tsr = l.toArray(new tumorSearchResult[0]);
	_status = DONE ;
    }

    public void parseFile()
    {
	_status = NOTHING ;
	Thread t = new Thread (this) ;
	t.start() ;
    }

    public int getStatus()
    {
	return _status ;
    }
    
    public tumorSearchResult[] getResults()
    {
	return _tsr;
    }
}