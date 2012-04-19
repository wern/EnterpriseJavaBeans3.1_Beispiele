package einstein.client.gui.modell;

import javax.ejb.EJBException;

import einstein.server.oeffentlich.MogelException;
import einstein.server.oeffentlich.Position;
import einstein.server.oeffentlich.SpielException;
import einstein.server.oeffentlich.SpielStatusException;
import einstein.server.oeffentlich.Zug;

/**
 * Das Modell fuer ein Feld beschreibt die Position des Feldes 
 * und merkt sich die Nr. und die Id. des Steins, der evtl. auf 
 * das Feld gesetzt wurde. Ferner bietet es eine Methode zum Ziehen 
 * des Steins.
 * 
 * @author <a href="mailto:info@jessicarubart.de">Jessica Rubart</a>
 */
public class FeldModell {
	
	protected Position position = null;
	protected int nr = 0;
	protected int id = 0;
	protected FeldViewInterface viewInterface = null;
	
	public FeldModell(Position position, FeldViewInterface view) {
		this.position = position;
		this.viewInterface = view;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	public void setNr(int nr) {
		this.nr = nr;
	}
	
	public int getNr() {
		return this.nr;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void ziehen (Position pos) {
		// Hier koennte auch auf der Client-Seite geprueft werden, ob der Zug erlaubt ist.
		try {
			EinsteinSitzungsSynchronisierer.ziehen(new Zug(getId(), pos));
		} catch (SpielException e1) {
			viewInterface.falschesSpiel(e1);
		} catch (SpielStatusException e1) {
			viewInterface.falscherStatus(e1);
		} catch (EJBException ex) {
			if (ex.getCause() instanceof MogelException) {
				// Da das Server-seitige Sitzungs-Bean nach einer MogelException nicht mehr existiert,
				// wird der Aktualisierungsprozess gestoppt.
				LoginModell.getLoginModell().stoppeAktualisierungsProzess();
				viewInterface.gemogelt();
				System.exit(0);
			}
			ex.printStackTrace();
		}
	}

}
