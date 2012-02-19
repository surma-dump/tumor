import javax.swing.* ;
import java.awt.event.* ;
import java.net.* ;

public class tumor implements tumorCallbackInterface, ActionListener
{
    private tumorMainFrame _tmf; 
    private int dlcount = 0 ;

    public tumor()
    {
	_tmf = new tumorMainFrame (this) ;
    }

    public static void main(String[] args)
    {
	new tumor() ;
    }

    public JFrame getMainFrame()
    {
	return _tmf ;
    }

    public int getDownloadsActive()
    {
	return dlcount;
    }

    public void quit(int exitcode)
    {
	_tmf.setVisible(false) ;
	System.exit(exitcode);
    }
    
    public String getKeywords()
    {
	return _tmf.getKeywords() ;
    }
    
    public ActionListener getActionListener()
    {
	return this ;
    }

    public void actionPerformed(ActionEvent ae)
    {
	String src = ((JButton)ae.getSource()).getText() ;
	if (src.equals("Search"))
	    {
		HttpXMLParser hxp = new HttpXMLParser("http://www.seeqpod.com/api/seeq/search?q="+getKeywords().replace(" ", "%20").replace("&","%26")+"&rm=1&rp=0&s=0&rt=0&rv=0&n=160") ;
		hxp.parseFile() ;
		while (hxp.getStatus() != HttpXMLParser.DONE)
		    { 
			try
			    {
				wait(500);
			    }
			catch (Exception e)
			    {
			    }
		    }
		_tmf.setResults (hxp.getResults());
	    }
	else if (src.equals("Download"))
	    {
		dlcount += 1 ;
		new SeeqpodLoader(_tmf.getSelectedItem(), this) ;
	    }
    }
    
    public String getVersion()
    {
	return "1.1";
    }

    public void decreaseDlCounter()
    {
	dlcount -= 1 ;
    }
}