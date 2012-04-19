package einstein.client.gui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import einstein.client.gui.modell.LoginModell;
import einstein.client.gui.modell.EinSteinViewInterface;
import einstein.server.oeffentlich.SpielStatusException;

/**
 * Beschreibt das Anwendungsfenster und unterstuetzt das Anmelden ans
 * System und das Aktualisieren der GUI.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class EinStein implements EinSteinViewInterface {
	protected JFrame einsteinFrame = null;
	protected BenutzerTischListe tischListe = null;
	protected Spielbrett spielbrett = null;
	protected static int MIN_WIDTH = 710;
	protected static int MIN_HEIGHT = 490;
	
	/**
	 * Initialisiert die LoginModell-Instanz und das Anwendungsfenster.
	 */
	public EinStein() {
		LoginModell.getLoginModell().setViewInterface(this);
		einsteinFrame = this.createFrame();
	}
	
	public static LoginModell getModell() {
		return LoginModell.getLoginModell();
	}
	
	/**
	 * Initialisiert das Anwendungsfenster.
	 * 
	 * @return Anwendungsfenster
	 */
	protected JFrame createFrame() {
		final JFrame frame = new JFrame(getModell().getAnwendungsTitel()) {
			private static final long serialVersionUID = 1L;

			public Dimension getMinimumSize() {
	        	return new Dimension(MIN_WIDTH, MIN_HEIGHT);
	        }
	    };
		ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("einstein/client/gui/icons/Dice-1.gif"));
        frame.setIconImage(icon.getImage());
        frame.setSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) {
	        	try {
					getModell().abmelden();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	        }
	      }
	    );
        frame.addComponentListener(new ComponentAdapter() {
        	public void componentResized(ComponentEvent e) {
        		Component c = e.getComponent();
        		Dimension d = c.getSize();
        		if (d.width < frame.getMinimumSize().width) {
        			d.width = frame.getMinimumSize().width;
        		}
        		if (d.height < frame.getMinimumSize().height) {
        			d.height = frame.getMinimumSize().height;
        		}
        		c.setSize(d);
            }
	      }
	    );
        
        return frame;
	}
	
	/**
	 * Zeigt das Fenster zum Einloggen und loggt den Benutzer ggf. ein.
	 * 
	 * @throws Exception
	 */
	protected void login() throws Exception {			
		String benutzerName = (String)JOptionPane.showInputDialog(einsteinFrame, "Anmelden als ...", "Anmeldung", JOptionPane.QUESTION_MESSAGE,
                									   		      null, null, "");
		if ((benutzerName != null) && (benutzerName.length() > 0)) {
			getModell().login(benutzerName);
			einsteinFrame.setTitle(benutzerName + "@" + getModell().getAnwendungsTitel());
		} else {
			System.exit(0);
		}
	}
	
	/**
	 * Aktualisiert die GUI.
	 */
	public void update() {
		if (tischListe != null) {
			tischListe.update();
		}
		if (spielbrett != null) {
				try {
					spielbrett.update();
				} catch (SpielStatusException ex) {
					ex.printStackTrace();
				}
		}
	}
	
	/**
	 * Erstellt ggf. die Spielbrett-Instanz.
	 * 
	 * @return Spielbrett
	 */
	protected JPanel getBoard() {
		if (spielbrett == null) {
			spielbrett = new Spielbrett();
		}
		return spielbrett;
	}

	/**
	 * Erstellt ggf. die Benutzer/Tische-Liste-Instanz.
	 * 
	 * @return
	 */
	protected JPanel getTablePanel() {
		if (tischListe == null) {
			tischListe = new BenutzerTischListe();
		}
		return tischListe;
	}
	
	public JFrame getJFrame()
	{
		return this.einsteinFrame;
	}
	
	/**
	 * Laesst das Anwendungsfenster erstellen, setzt die Inhalte, loggt
	 * ggf. den Benutzer ein und startet den Aktualisierungsprozess.
	 * 
	 * @throws Exception
	 */
	protected static void createAndShowGUI() throws Exception {
		final EinStein einstein = new EinStein();
		JFrame f1 = einstein.getJFrame();
		
		JPanel board = einstein.getBoard();
		JPanel tablepanel = einstein.getTablePanel();
		f1.getContentPane().setLayout(new BorderLayout());
		f1.getContentPane().add(board, BorderLayout.CENTER);
		f1.getContentPane().add(tablepanel, BorderLayout.WEST);	
        
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        f1.setLocation((screenDim.width - f1.getWidth()) / 2, (screenDim.height - f1.getHeight()) / 2);
		
        f1.setVisible(true);
        
        einstein.login();
        getModell().starteAktualisierungsProzess();
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		createAndShowGUI();
            	} catch (Exception ex) {
            		ex.printStackTrace();
            	}   
            }
		});
	}

}
