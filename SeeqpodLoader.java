import javax.swing.* ;
import java.io.* ;
import java.net.* ;
import javax.swing.filechooser.* ;
public class SeeqpodLoader extends JDialog implements Runnable 
{
    private tumorSearchResult _tsr ;
    private JLabel _jl ;
    private File _savefile ;
    private final char[] active = {'|', '/', '-', '\\'} ;
    private tumorCallbackInterface _ci ;
    public SeeqpodLoader (tumorSearchResult tsr, tumorCallbackInterface ci)
    {
	super(ci.getMainFrame(), "Downloading...", false) ;
	_tsr = tsr ;
	_ci = ci ;

	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio File (*.mp3)", "mp3");
	chooser.setFileFilter(filter);
	chooser.setSelectedFile (new File (_tsr.getArtist() + " - " + _tsr.getTitle())) ;
	int returnVal = chooser.showSaveDialog(this);
	if(returnVal == JFileChooser.APPROVE_OPTION) 
	    {
		_savefile =  chooser.getSelectedFile();
		String path = _savefile.getAbsolutePath() ;
		System.err.println(path);
		if (!path.endsWith(".mp3"))
		    {
			path += ".mp3" ;
		    }
		System.err.println(path);
		_savefile = new File(path) ;
		_jl = new JLabel ("Collecting Data for \""+_tsr.getTitle()+"\" by \""+_tsr.getArtist()+"\"...") ;
		add (_jl) ;
		setSize (400,80) ;
		setVisible(true) ;
		Thread t = new Thread (this) ;
		t.start() ;
	    }
    }

    public void run()
    {
	try 
	    {
		URL decode = new URL ("http://www.seeqpod.com/api/youtube.com/results?mp3_url_id="+_tsr.getTag()) ;
		String newfile = new BufferedReader(new InputStreamReader(decode.openStream())).readLine() ;
		BufferedInputStream in = new BufferedInputStream(new URL(newfile).openStream()) ;
		FileOutputStream fos = new FileOutputStream(_savefile);

		int i=0;
		long j = 0, t1= System.currentTimeMillis(), t2=System.currentTimeMillis();
		_jl.setText ("<html><body>Downloading \""+_tsr.getTitle()+"\" by \""+_tsr.getArtist()+"\"...</body></html>") ;			   
		while ((i = in.read()) != -1) 
		       {
			   fos.write(i);
			   if ((++j) % 1024 == 0)
			       {
				   t2=t1;
				   t1=System.currentTimeMillis();
				   _jl.setText ("<html><body>Downloading \""+_tsr.getTitle()+"\" by \""+_tsr.getArtist()+"\"... "+(j/1024)+"kB</body></html>") ;  
			       }
		       }
		fos.close();
		in.close() ;
		_ci.decreaseDlCounter();
		setVisible(false) ;
	    }
	catch (Exception e)
	    {
		System.err.println ("pwnd!") ;
		setVisible(false) ;
	    }
    }
}