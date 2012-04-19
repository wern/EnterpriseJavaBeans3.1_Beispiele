/**********************************************************
* Begleitmaterial zum Buch "Enterprise JavaBeans 3.1"
* Das EJB-Praxisbuch fuer Ein- und Umsteiger
* Von Werner Eberling und Jan Lessner
* Hanser Fachbuchverlag Muenchen, 2011
* http://www.hanser.de/buch.asp?isbn=3-446-42259-5
* Feedback an ejb3buch@werner-eberling.de
**********************************************************/ 
package stateful.warenkorb;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful(name="Warenkorb")
public class WarenkorbImpl implements Warenkorb {

	class Ware {
		int anzahl;

		int einzelpreis;

		String artikelnummer;

		public Ware(int anzahl, int einzelpreis, String artikelnummer) {
			super();
			this.anzahl = anzahl;
			this.einzelpreis = einzelpreis;
			this.artikelnummer = artikelnummer != null ? artikelnummer : "";
		}

		public int getGesamtpreis() {
			return anzahl * einzelpreis;
		}
	}

	private Map<String, Ware> waren = new HashMap<String, Ware>();

	public void entferneAusWarenkorb(String artikelnummer) {
		waren.remove(artikelnummer);
	}

	public void legeInWarenkorb(int anzahl, String artikelnummer, int einzelpreis) {
		waren.put(artikelnummer, new Ware(anzahl, einzelpreis, artikelnummer));
	}

	@Remove
	public int geheZurKasse() {
		int gesamtpreis = 0;
		for (Ware ware : waren.values()) {
			gesamtpreis += ware.getGesamtpreis();
		}
		return gesamtpreis;
	}

	@PostConstruct
	public void erzeugt(){
		System.out.println("Ich wurde erzeugt.");
	}
	
	@PreDestroy
	public void beanEntfernt(){
		System.out.println("Ich werde entfernt.");
	}

	@PrePassivate
	public void schlafenGehen(){
		System.out.println("Ich wurde passiviert.");
	}
	
	@PostActivate
	public void wiederErwachen(){
		System.out.println("Ich werde aktiviert.");
	}
}
