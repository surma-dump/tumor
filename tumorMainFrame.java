import java.awt.*;
import javax.swing.*;

public class tumorMainFrame extends JFrame
{
    tumorCallbackInterface _ci ;
    tumorSearchBar _tsb ;
    tumorResultBar _trb ;
    JComboBox _jcb ;
    public tumorMainFrame(tumorCallbackInterface ci)
    {
	super("T U M O R - Don't ask why, just use it");
	setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
	addWindowListener (new tumorWindowListener(ci));
	setSize (600,120);
	
	setLayout (new BorderLayout());
	_tsb = new tumorSearchBar(ci) ;
	add (_tsb, BorderLayout.NORTH) ;
	_trb = new tumorResultBar(ci) ;
	add (_trb, BorderLayout.CENTER) ;
	//add (new JLabel ("Downloadmanager"), BorderLayout.EAST) ;

	setVisible(true);
	_ci = ci ;
    }

    public String getKeywords()
    {
	return _tsb.getKeywords() ;
    }

    public void setResults(tumorSearchResult[] tsr)
    {
	_trb.setResults(tsr) ;
    }

    public tumorSearchResult getSelectedItem()
    {
	return _trb.getSelectedItem() ;
    }

}

class tumorSearchBar extends JPanel
{
    private JTextField _jtf ;

    public tumorSearchBar(tumorCallbackInterface ci)
    {
	super (new BorderLayout());
	add (new JLabel ("<html><body><p align='center'>Tumor v"+ci.getVersion()+" - &copy; 2008 Alexander \"crock\"  Surma &lt;alexander.surma@gmx.de&gt;</p></body></html>"), BorderLayout.NORTH);
	add (new JLabel ("Keywords:"), BorderLayout.WEST) ;
	_jtf = new JTextField() ;
	add (_jtf, BorderLayout.CENTER) ;
	JButton jb = new JButton ("Search") ;
	jb.addActionListener (ci.getActionListener());
	add (jb, BorderLayout.EAST) ;
	setVisible(true) ;
    }
    
    public String getKeywords()
    {
	return _jtf.getText() ;
    }
}

class tumorResultBar extends JPanel
{
    private JComboBox _jcb ;
    public tumorResultBar (tumorCallbackInterface ci)
    {
	super (new BorderLayout()) ;
	_jcb = new JComboBox() ;
	add (_jcb, BorderLayout.CENTER) ;
	JButton jb = new JButton ("Download") ;
	jb.addActionListener(ci.getActionListener()) ;
	add (jb, BorderLayout.SOUTH) ;
    }

    public void setResults (tumorSearchResult[] tsr)
    {
	_jcb.removeAllItems() ;
	for (int i = 0; i < tsr.length; i++)
	    {
		_jcb.addItem(tsr[i]) ;
	    }
    }

    public tumorSearchResult getSelectedItem()
    {
	return (tumorSearchResult)_jcb.getSelectedItem() ;
    }
}