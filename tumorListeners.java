import java.awt.event.*;
import javax.swing.*;

class tumorWindowListener extends WindowAdapter
{
    private tumorCallbackInterface _ci ;

    public tumorWindowListener (tumorCallbackInterface ci)
    {
	_ci = ci;
    }

    public void windowClosing(WindowEvent e)
    {
	if (_ci.getDownloadsActive() > 0)
	    {
		if (JOptionPane.showConfirmDialog (_ci.getMainFrame(), "Wollen Sie wirklich beenden?", "O RLY?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		    {
			_ci.quit(0);
		    }
	    }
	else
	    {
		_ci.quit(0);
	    }
    }
}