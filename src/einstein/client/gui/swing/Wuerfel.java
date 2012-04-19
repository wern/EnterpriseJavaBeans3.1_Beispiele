package einstein.client.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import einstein.client.gui.modell.WuerfelModell;
import einstein.client.gui.modell.WuerfelViewInterface;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatusException;

/**
 * Beschreibt einen Wuerfel, dargestellt mittels ImageIcons in einem
 * JPanel. Per Mausklick kann gewuerfelt werden.
 * Der Name des zugehoerigen Benutzers wird unter (falls spielerA) bzw.
 * ueber dem Wuerfel-Icon angezeigt.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class Wuerfel extends JPanel implements WuerfelViewInterface {

	private static final long serialVersionUID = 1L;
	protected Hashtable<Integer, ImageIcon> icons = null;
	protected Spielbrett spielbrett = null;
	protected JPanel wuerfelIconPanel = null;
	protected JLabel spielerName = null;
	protected WuerfelModell modell = null;
	
	/**
	 * Initialisiert einen Wuerfel.
	 * 
	 * @param spielbrett
	 * @param spielerA
	 */
	public Wuerfel(Spielbrett spielbrett, boolean spielerA) {
		super(new BorderLayout());
		this.spielbrett = spielbrett;
		modell = new WuerfelModell(spielerA, this, spielbrett.getModell());
		this.setBackground(Color.LIGHT_GRAY);
		
		wuerfelIconPanel = new JPanel(new BorderLayout());
		wuerfelIconPanel.setBackground(Color.LIGHT_GRAY);
		this.add(wuerfelIconPanel, BorderLayout.CENTER);
		setzeSpielerNamen(spielerA);
		
		initialisiereIconsUndAugenzahl();
		
		this.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	modell.wuerfle();
	        }
	      }
	    );
	}
	
	/**
	 * Setzt den Spielernamen in einem Label unter (falls spielerA)
	 * bzw. ueber das Wuerfel-Icon.
	 * 
	 * @param spielerA
	 */
	protected void setzeSpielerNamen(boolean spielerA) {
		spielerName = new JLabel(modell.getSpielerName(), SwingConstants.CENTER);
		spielerName.setFont(new java.awt.Font("Arial", 0, 11));
		if (spielerA) {
			this.add(spielerName, BorderLayout.SOUTH);
		} else {
			this.add(spielerName, BorderLayout.NORTH);
		}
	}
	
	/**
	 * Initialisiert die Wuerfel-Icons und die Augenzahl.
	 */
	protected void initialisiereIconsUndAugenzahl() {
		icons = new Hashtable<Integer, ImageIcon>();
		for (int i = 0; i < 7; i++) {
			icons.put(new Integer(i), new ImageIcon(this.getClass().getClassLoader().getResource("einstein/client/gui/icons/Dice-" + i + ".gif")));
		}
		setCount(0);
	}
	
	public void falscherWuerfel() {
		JOptionPane.showMessageDialog(spielbrett, "Das ist nicht Dein Würfel!", "Falscher Würfel", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void falschesSpiel(SpielException e) {
		JOptionPane.showMessageDialog(spielbrett, e.getMessage(), "Falsches Spiel", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void falscherStatus(SpielStatusException e) {
		JOptionPane.showMessageDialog(spielbrett, e.getMessage(), "Falscher Status", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Setzt die Augenzahl und aktualisiert die GUI.
	 * 
	 * @param count
	 */
	public void setCount(int count) {
		modell.setCount(count);
		wuerfelIconPanel.removeAll();
		wuerfelIconPanel.add(new JLabel(this.getIcon(), SwingConstants.CENTER), BorderLayout.CENTER);
		this.validate();
	}
	
	public void setSpielerName(String name) {
		modell.setSpielerName(name);
		spielerName.setText(modell.getSpielerName());
	}
	
	public int getCount() {
		return modell.getCount();
	}
	
	public ImageIcon getIcon() {
		return icons.get(new Integer(modell.getCount()));
	}

}
