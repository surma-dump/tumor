import javax.swing.*;
import java.awt.event.*;

public interface tumorCallbackInterface
{
    public JFrame getMainFrame();
    public int getDownloadsActive();
    public void quit(int exitcode);
    public String getKeywords();
    public ActionListener getActionListener();
    public String getVersion();
    public void decreaseDlCounter();
}