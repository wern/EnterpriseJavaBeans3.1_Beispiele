package stateless.kaffeepause;

import java.io.Serializable;

public class Kaffee implements Serializable {
	private int anzahlTassen;

	public Kaffee(int anzahlTassen) {
		this.anzahlTassen = anzahlTassen;
	}

	public int getAnzahlTassen() {
		return anzahlTassen;
	}

}
