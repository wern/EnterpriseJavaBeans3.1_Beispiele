package einstein.client.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import einstein.client.gui.modell.SpielbrettModell;
import einstein.client.gui.modell.SpielbrettViewInterface;
import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatusException;

/**
 * Beschreibt die Statuszeile, das Spielbrett und die Steuerungsschaltflaechen 
 * und unterstuetzt deren Aktualisierung. 
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class Spielbrett extends JPanel implements ActionListener, SpielbrettViewInterface {
	private static final long serialVersionUID = 1L;
	protected SpielbrettModell modell = null;
	protected Hashtable<String, Feld> felder = null;
	protected Hashtable<Integer, ImageIcon> iconsRot = null;
	protected Hashtable<Integer, ImageIcon> iconsGelb = null;
	protected JPanel spielbrettPanel = null;
	protected JLabel labelStatus = null;
	protected JLabel labelTisch = null;
	protected Wuerfel wuerfelA, wuerfelB = null;
	protected JPanel aktivierungsLeiste = null;
	protected JButton bereitB, verlassenB = null;
	protected final static String tischUeberschrift = "Tisch";
	public final static String leereTischNummer = "X";
	protected String tischNr = leereTischNummer;
	public final static String leererTischUeberschrift = "Leerer Tisch";
	protected SpielbrettAktualisierer spielbrettAkt = null;
	protected static int ANZAHL_SPIELSPALTEN = 5;
	protected static int ANZAHL_SPIELZEILEN = 5;
	
	public interface AktionsSchluessel {
        public final static String VERLASSEN = "Tisch verlassen",
                                   BEREIT = "Bereit";
    }

	/**
	 * Initialisiert das Spielbrett.
	 */
	public Spielbrett() {
		super(new BorderLayout());
		this.modell = new SpielbrettModell(this);
		
		JPanel innen = new JPanel(new BorderLayout());		
		innen.add(initialisiereInfoBereich(), BorderLayout.NORTH);
		innen.add(initialisiereFelderUndWuerfel(), BorderLayout.CENTER);
		innen.add(initialisiereSteuerungsBereich(), BorderLayout.SOUTH);
		innen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
		this.add(innen, BorderLayout.CENTER);
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(560, 490);
	}
	
	public void updateTischNr(String tischNr) {
		this.tischNr = tischNr;
		if (labelTisch != null) {
			labelTisch.setText(tischUeberschrift + " " + tischNr);
		}
	}
	
	protected void erzeugeTischBeschriftung() {
		if (labelTisch == null) {
			labelTisch = new JLabel(tischUeberschrift + " " + tischNr);
			labelTisch.setFont(new java.awt.Font("Arial", 1, 14));
		}
	}
	
	/**
	 * Initialisiert den Ueberschrift- und Statusbereich.
	 * 
	 * @return zugehoeriges Panel
	 */
	protected JPanel initialisiereInfoBereich() {
		JPanel info = new JPanel() {
			private static final long serialVersionUID = 1L;
			public Insets getInsets() {
	        	return new Insets(10, 10, 20, 10);
	        }
	      };
	    info.setBackground(Color.LIGHT_GRAY);
		info.setLayout(new FlowLayout(FlowLayout.LEFT));
		erzeugeTischBeschriftung();
		labelStatus = new JLabel(leererTischUeberschrift);
		JPanel statusPanel = new JPanel(new BorderLayout()){
			private static final long serialVersionUID = 1L;
			public Insets getInsets() {
        		return new Insets(0, 20, 0, 0);
        	}
		}; 
		statusPanel.add(labelStatus, BorderLayout.CENTER); 
		statusPanel.setBackground(Color.LIGHT_GRAY);
		labelStatus.setFont(new java.awt.Font("Arial", 1, 14));
		info.add(labelTisch); 
		info.add(statusPanel);
		return info;
	}
	
	/**
	 * Initialisiert den Steuerungsbereich (Buttons)
	 * 
	 * @return zugehoeriges Panel
	 */
	protected JPanel initialisiereSteuerungsBereich() {
		final FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
		aktivierungsLeiste = new JPanel(){
			private static final long serialVersionUID = 1L;
			public Insets getInsets() {
				return new Insets(0, wuerfelA.getWidth() - fl.getHgap(), 0, 0);
			};
		};
		aktivierungsLeiste.setLayout(fl);	
		aktivierungsLeiste.setBackground(Color.LIGHT_GRAY);
		bereitB = new JButton(AktionsSchluessel.BEREIT);
		initialisiereButton(aktivierungsLeiste, bereitB);
		verlassenB = new JButton(AktionsSchluessel.VERLASSEN);
		initialisiereButton(aktivierungsLeiste, verlassenB);
		return aktivierungsLeiste;
	}
	
	protected void initialisiereButton(JPanel panel, JButton button) {
		button.setEnabled(false);
		button.addActionListener(this);
		panel.add(button);
	}
	
	/**
	 * Initialisiert die Felder des Spielbretts und die Wuerfel.
	 * 
	 * @return zugehoeriges Panel
	 */
	protected JPanel initialisiereFelderUndWuerfel() {
		spielbrettPanel = new JPanel();
		spielbrettPanel.setBackground(Color.LIGHT_GRAY);
		spielbrettPanel.setLayout(new GridLayout(0, 7));
		felder = new Hashtable<String, Feld>();
		iconsRot = new Hashtable<Integer, ImageIcon>();
		iconsGelb = new Hashtable<Integer, ImageIcon>();
		for (int i = 1; i < 7; i++) {
			iconsRot.put(new Integer(i), new ImageIcon(this.getClass().getClassLoader().getResource("einstein/client/gui/icons/Piece-Red-" + i + ".gif")));
			iconsGelb.put(new Integer(i), new ImageIcon(this.getClass().getClassLoader().getResource("einstein/client/gui/icons/Piece-Yellow-" + i + ".gif")));
		}
		
		wuerfelA = new Wuerfel(this, true);
		spielbrettPanel.add(wuerfelA);
		for (int i = 1; i < ANZAHL_SPIELSPALTEN + 1; i++) {
			if (i%2!=0) {
				erzeugeZeile(Color.GRAY, Color.WHITE, i);
			} else {
				erzeugeZeile(Color.WHITE, Color.GRAY, i);
			}
		}
		wuerfelB = new Wuerfel(this, false);
		spielbrettPanel.add(wuerfelB);
		return spielbrettPanel;
	}
	
	/**
	 * Erzeugt eine Zeile mit Feldern des Spielbretts und
	 * benachbarten Spalten des Layouts.
	 * 
	 * @param color1
	 * @param color2
	 * @param line
	 */
	protected void erzeugeZeile (Color color1, Color color2, int line) {	
		if (line > 1) {
			spielbrettPanel.add(new Feld(Color.LIGHT_GRAY, uebersetzePosition(new Position(0, line))));
		}		
		int column = 0;
		for (int i = 1; i < 3; i++) {
			column = erzeugeInneresFeld(color1, column, line);
			column = erzeugeInneresFeld(color2, column, line);
		}
		Position pos = uebersetzePosition(new Position(ANZAHL_SPIELSPALTEN, line));
		Feld currentField = new Feld(color1, pos);
		spielbrettPanel.add(currentField);
		felder.put(pos.toString(), currentField);
		if (line < ANZAHL_SPIELZEILEN) {
			spielbrettPanel.add(new Feld(Color.LIGHT_GRAY, uebersetzePosition(new Position(ANZAHL_SPIELSPALTEN + 1, line))));
		}
	}
	
	protected int erzeugeInneresFeld(Color color, int column, int line){
		column++;
		Position pos = uebersetzePosition(new Position(column, line));
		Feld feld = new Feld(color, pos);
		spielbrettPanel.add(feld); 
		felder.put(pos.toString(), feld);
		return column;
	}
	
	protected Position uebersetzePosition(Position pos) {
		pos.y = ANZAHL_SPIELZEILEN - pos.y + 1;
		return pos;
	}
	
	public SpielbrettModell getModell() {
		return this.modell;
	}
	
	public void setBereit(boolean bereit) {
		this.bereitB.setEnabled(bereit);
	}
	
	public boolean getBereit() {
		return this.bereitB.isEnabled();
	}
	
	public void setVerlassen(boolean verlassen) {
		this.verlassenB.setEnabled(verlassen);
	}
	
	public boolean getVerlassen() {
		return this.verlassenB.isEnabled();
	}
	
	public Wuerfel getWuerfelA() {
		return this.wuerfelA;
	}
	
	public Wuerfel getWuerfelB() {
		return this.wuerfelB;
	}
	
	public JPanel getSpielbrettPanel() {
		return this.spielbrettPanel;
	}
	
	public ImageIcon getSteinIcon(boolean spielerA, Integer steinNr) {
		if (spielerA) {
			return iconsRot.get(steinNr);
		} else {
			return iconsGelb.get(steinNr);
		}
	}
	
	public Hashtable<String, Feld> getFelder() {
		return felder;
	}
	
	public void setStatusText(String text) {
		labelStatus.setText(text);
	}
	
	/**
	 * Aktualisiert das Spielbrett abhaengig vom Status des Servers.
	 * 
	 * @throws SpielStatusException
	 */
	public void update() throws SpielStatusException {
		if (spielbrettAkt == null) {
			spielbrettAkt = new SpielbrettAktualisierer(this);
		}
		spielbrettAkt.update();
	}
	
	public void falschesSpiel(SpielException e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Falsches Spiel", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void falscherStatus(SpielStatusException e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Falscher Status", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(AktionsSchluessel.BEREIT)) {
			modell.bereit();
		}  else if (event.getActionCommand().equals(AktionsSchluessel.VERLASSEN)) {
			modell.verlassen();
		} 
	}
}