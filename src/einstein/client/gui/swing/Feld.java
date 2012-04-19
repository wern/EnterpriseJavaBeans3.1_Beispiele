package einstein.client.gui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import einstein.client.gui.modell.FeldModell;
import einstein.client.gui.modell.FeldViewInterface;
import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatusException;

/**
 * View und Controller fuer ein Feld.
 * Als View wird ein JPanel mit einer Hintergrundfarbe verwendet. 
 * Ein evtl. Stein wird mittels eines ImageIcons in einem JLabel dargestellt,
 * zentriert ueber das BorderLayout.
 * Das Ziehen eines Steins ist via drag&drop ueber die Mouse-Events
 * umgesetzt.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class Feld extends JPanel implements FeldViewInterface {
	private static final long serialVersionUID = 1L;
	protected FeldModell modell = null;
	protected Color color = null;
	protected ImageIcon icon = null;
	protected boolean dragging = false;
	protected Point currentDraggingPoint = null;
	protected int getroffen_X = 0;
	protected int getroffen_Y = 0;

	/**
	 * Initialisiert eine Feld-Instanz.
	 * Setzt Farbe und Position.
	 * 
	 * @param color
	 * @param position
	 */
	public Feld(Color color, Position position) {
		super(new BorderLayout());
		this.color = color;
		modell = new FeldModell(position, this);
		currentDraggingPoint = new Point();
		this.setBackground(color);	
		addMouseListener();
		addMouseMotionListener();
	}
	
	/**
	 * Definiert "mousePressed" und "mouseReleased" fuer das Feld.
	 * Ein Zug wird ausgefuehrt, falls ge-dragged wurde und ein
	 * "mouseReleased"-Ereignis eintrifft.
	 */
	protected void addMouseListener() {
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (icon != null) {
					getroffen_X = e.getX() - ((Feld.this.getSize().width - icon.getIconWidth()) / 2);
					getroffen_Y = e.getY() - ((Feld.this.getSize().height - icon.getIconHeight()) / 2);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (dragging) {
					dragging = false;
					Point point = transformiereKoordinaten(e, false);
					Component comp = getParent().getComponentAt(point.x, point.y);
					if (comp instanceof Feld) {
						Position pos = ((Feld) comp).getPosition();
						modell.ziehen(pos);
					}
				}
			}
        });
	}
	
	/**
	 * Definiert "mouseDragged" fuer ein Feld.
	 * Malt ggf. das Icon an den aktuellen Koordinaten.
	 */
	protected void addMouseMotionListener() {
		this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged (MouseEvent e) {
            	if (icon != null) {
            		dragging = true;
                	getParent().update(getParent().getGraphics());
                	Point point = transformiereKoordinaten(e, true);
                	icon.paintIcon(Feld.this, getParent().getGraphics(), point.x, point.y);
                }
            }
        });
	}
	
	/**
	 * Berechnet den oberen linken Punkt des zu malenden Icons unter
	 * Beruecksichtigung des aktuellen MouseEvents.
	 * 
	 * @param e
	 * @return umgerechneten Punkt
	 */
	protected Point transformiereKoordinaten(MouseEvent e, boolean offset) {
		currentDraggingPoint.x = getLocationOnScreen().x-getParent().getLocationOnScreen().x;
		currentDraggingPoint.y = getLocationOnScreen().y-getParent().getLocationOnScreen().y;
		currentDraggingPoint.x = currentDraggingPoint.x + e.getX();
		currentDraggingPoint.y = currentDraggingPoint.y + e.getY();
		if (offset) {
			currentDraggingPoint.x = currentDraggingPoint.x - this.getroffen_X;
			currentDraggingPoint.y = currentDraggingPoint.y - this.getroffen_Y;
		}
        return currentDraggingPoint;
	}
	
	/**
	 * Fuegt dem Feld einen Stein als ImageIcon in einem Label hinzu.
	 * Setzt die Nr. und die Id. des Steins.
	 * 
	 * @param icon
	 * @param nr
	 * @param id
	 */
	public void addStein(ImageIcon icon, int nr, int id) {
		this.removeAll();
		this.icon = icon;
		modell.setNr(nr);
		modell.setId(id);
		
		add(new JLabel(icon), BorderLayout.CENTER);
		this.validate();
		if (this.getParent() != null) {
			this.getParent().validate();
		}
	}
	
	/**
	 * Loescht ggf. den Stein auf dem Feld.
	 */
	public void loescheStein() {
		this.removeAll();
		this.icon = null;
		modell.setNr(0);
		modell.setId(0);
	}
	
	public void update() {
		if (dragging) {
			icon.paintIcon(this, getParent().getGraphics(), currentDraggingPoint.x, currentDraggingPoint.y);
		}
	}
	
	public Position getPosition() {
		return modell.getPosition();
	}
	
	public int getSteinId() {
		return modell.getId();
	}
	
	public int getNr() {
		return modell.getNr();
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}

	public void gemogelt() {
		JOptionPane.showMessageDialog(getParent(), "Du hast gemogelt!", "Gemogelt", JOptionPane.INFORMATION_MESSAGE);
	}

	public void falschesSpiel(SpielException e) {
		JOptionPane.showMessageDialog(getParent(), e.getMessage(), "Falsches Spiel", JOptionPane.INFORMATION_MESSAGE);
	}

	public void falscherStatus(SpielStatusException e) {
		JOptionPane.showMessageDialog(getParent(), e.getMessage(), "Falscher Status", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
