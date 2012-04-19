package einstein.client.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import einstein.client.gui.modell.LoginModell;
import einstein.client.gui.modell.BenutzerTischeModell;
import einstein.client.gui.modell.BenutzerTischeViewInterface;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.Spieler;

/**
 * Beschreibt die Liste der Benutzer und Tische mittels einer JTable, 
 * unterstuetzt deren Aktualisierung, das Anlegen von neuen Tischen und 
 * das Auswaehlen von Tischen als Zuschauer oder Spieler.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class BenutzerTischListe extends JPanel implements BenutzerTischeViewInterface, ListSelectionListener, ActionListener {
	private static final long serialVersionUID = 1L;
	protected BenutzerTischeModell modell = null;
	protected DefaultTableModel tabellenModell = null;
	protected String newTableCmd = "Neuer Tisch";
	protected String spielenCmd = "Spielen";
	protected String zuschauenCmd = "Zuschauen";
	protected JTable benutzerTischListe = null;
	protected String selektierterBenutzer = null;
	protected Integer selektierterTisch = null;
	protected JButton neuerTischB, spielenB, zuschauenB = null;
	protected boolean binSpieler = false;
	protected boolean aktualisierung = false;

	/**
	 * Initialisiert die Benutzer/Tische-Liste.
	 */
	public BenutzerTischListe() {
		super(new BorderLayout());
		modell = new BenutzerTischeModell(this);
		
		initialisiereTabelle();
		JScrollPane listScrollPane = new JScrollPane(benutzerTischListe){
			private static final long serialVersionUID = 1L;
			public Dimension getPreferredSize() {
	        	return new Dimension(150, 490);
	        }
	      };
	    listScrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
		this.add(listScrollPane, BorderLayout.CENTER);
		this.setBackground(Color.LIGHT_GRAY);
		
		this.add(initialisiereSteuerungsBereich(), BorderLayout.PAGE_END);
	}
	
	/**
	 * Initialisiert die JTable.
	 */
	protected void initialisiereTabelle() {
		initialisiereTabellenModell();	
		
		benutzerTischListe = new JTable(tabellenModell){
			private static final long serialVersionUID = 1L;
			public void tableChanged(TableModelEvent e) {
				// Damit die Tabelle sich waehrend des Updates nicht staendig aktualisiert 
				// und damit flackert
				if (aktualisierung) {
					return;
				}
				super.tableChanged(e);
			}
		}; 	
		benutzerTischListe.setBackground(Color.WHITE);
		benutzerTischListe.getTableHeader().setBackground(Color.LIGHT_GRAY);
		benutzerTischListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		benutzerTischListe.getSelectionModel().addListSelectionListener(this);
	}
	
	/**
	 * Initialisiert das Swing-Modell der JTable.
	 */
	protected void initialisiereTabellenModell() {
		tabellenModell = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
	        	return false;
	        }
	      };;
		tabellenModell.addColumn("Spieler");
		tabellenModell.addColumn("Tisch");
		tabellenModell.addColumn("Spiele");
		tabellenModell.addColumn("Siege");
	}
	
	/**
	 * Initialisiert den Steuerungs-Bereich (Buttons).
	 * 
	 * @return JPanel mit Buttons
	 */
	protected JPanel initialisiereSteuerungsBereich() {
		JPanel steuerungsBereich = new JPanel(new GridBagLayout());
		steuerungsBereich.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = 1;
	    constraints.insets = new Insets(5, 0, 0, 0);
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.weightx = 1.0;
		neuerTischB = new JButton (newTableCmd);
		neuerTischB.addActionListener(this);
		steuerungsBereich.add(neuerTischB, constraints);
		GridBagConstraints constraints2 = new GridBagConstraints();
		constraints2.gridheight = 1;
		constraints2.insets = new Insets(5, 0, 0, 5);
		
		spielenB = new JButton (spielenCmd);
		spielenB.addActionListener(this);
		zuschauenB = new JButton (zuschauenCmd);
		zuschauenB.addActionListener(this);
		steuerungsBereich.add(spielenB, constraints2);
		steuerungsBereich.add(zuschauenB, constraints);
	      
		return steuerungsBereich;
	}
	
	public Insets getInsets() {
		return new Insets(10, 10, 10, 10);
	}

	/**
	 * Haelt den selektierten Benutzer und ggf. implizit selektierten
	 * Tisch fest. 
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (aktualisierung) {
			return;
		}
		
		int zeile = benutzerTischListe.getSelectedRow();
		if (zeile != -1) {
			String tischNr = (String)benutzerTischListe.getModel().getValueAt(zeile, 1); 
			selektierterBenutzer = (String)benutzerTischListe.getModel().getValueAt(zeile, 0); 
			if (tischNr != "") {
				if (tischNr.startsWith("(")) {
					tischNr = tischNr.substring(1, 2);
				}			
				selektierterTisch = new Integer(tischNr);
			} else {
				selektierterTisch = null;
			}
		}
	}
	
	/**
	 * Aktualisiert ggf. den Zustand der Buttons.
	 */
	protected void ueberpruefeSteuerung() {
		if (binSpieler) {
			neuerTischB.setEnabled(false);
			spielenB.setEnabled(false);
			zuschauenB.setEnabled(false);
		} else {
			neuerTischB.setEnabled(true);
			spielenB.setEnabled(true);
			zuschauenB.setEnabled(true);
		}
	}

	
	/**
	 * Aktualisiert die Benutzer/Tische-Liste, indem alle Eintraege erneuert werden.
	 * (Hier koennte optimiert werden.)
	 */
	public void update() {
		aktualisierung = true;
		loescheTabellenEintraege();
		binSpieler = false;
		fuegeAnwesendeSpielerHinzu();
		aktualisierung = false;
		benutzerTischListe.revalidate();
		benutzerTischListe.repaint();
		ueberpruefeSteuerung();
	}
	
	/**
	 * Fuegt alle anwesenden Spieler-Eintraege der Benutzer/Tische-Liste hinzu,
	 * merkt sich, ob der aktuelle Benutzer Spieler ist und setzt ggf.
	 * die Selektion.
	 */
	protected void fuegeAnwesendeSpielerHinzu() {		 
		List<Spieler> spielerListe = modell.getAnwesendeSpieler();
		Vector<String> rowData = null;
		int index = 0;
		for (Spieler spieler : spielerListe) {
			rowData = new Vector<String>();
			rowData.addElement(spieler.name);
			if (spieler.spielerAn != null) {
				rowData.addElement(spieler.spielerAn.toString());
				// Ist Benutzer Spieler?
				if (spieler.name.equals(LoginModell.getBenutzerName())) {
					binSpieler = true;
				}
			} else if (spieler.zuschauerAn != null){
				rowData.addElement("(" + spieler.zuschauerAn.toString() + ")");
			} else {
				rowData.addElement("");
			}
			rowData.addElement((new Integer(spieler.partienGespielt)).toString());
			rowData.addElement((new Integer(spieler.partienGewonnen)).toString());
			tabellenModell.addRow(rowData);
			// Setzt die eventuelle Selektion
			if (selektierterBenutzer != null) {
				if (spieler.name.equals(selektierterBenutzer)) {
					benutzerTischListe.setRowSelectionInterval(index, index);
				}
			}
			index++;
		}	
	}
	
	/**
	 * Alle Eintraege der Benutzer/Tische-Tabelle werden geloescht.
	 */
	protected void loescheTabellenEintraege() {
		int anzahlZeilen = tabellenModell.getRowCount();
		for (int i = 0; i < anzahlZeilen; i++) {
			tabellenModell.removeRow(0);
		}
	}

	/**
	 * Beschreibt die Aktionen zum Anlegen eines neuen Tisches sowie
	 * zum Setzen an einen Tisch als Zuschauer oder Spieler.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(newTableCmd)) {
			modell.eroeffneNeuenTisch();
		} else if (e.getActionCommand().equals(zuschauenCmd)) {
			if (this.selektierterTisch != null) {
				modell.waehleTischAlsZuschauer(selektierterTisch.intValue());
			} else {
				JOptionPane.showMessageDialog(getParent(), "Es ist kein Tisch selektiert.", "Kein Tisch", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getActionCommand().equals(spielenCmd)) {
			if (this.selektierterTisch != null) {
				modell.waehleTischAlsSpieler(selektierterTisch.intValue());
			} else {
				JOptionPane.showMessageDialog(getParent(), "Es ist kein Tisch selektiert.", "Kein Tisch", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/**
	 * Fragt, ob ein Mitspieler gesucht wird oder der Computer der Gegner sein soll.
	 * 
	 * @return true, wenn ein Mitspieler gesucht wird; andernfalls false.
	 */
	public boolean sucheMitspieler() {
		Object[] options = {"Ja", "Nein"};
        int result = JOptionPane.showOptionDialog(getParent(), "Möchtest Du einen Mitspieler suchen? Falls nein, ist der Computer Dein Gegner.", 
        									 "Gegnerauswahl", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
        									 options[0]);
        if (result == JOptionPane.YES_OPTION) {
            return true;
        } 
        
        return false;
	}
	
	public void falschesSpiel(SpielException e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Falsches Spiel", JOptionPane.INFORMATION_MESSAGE);
	}

}
