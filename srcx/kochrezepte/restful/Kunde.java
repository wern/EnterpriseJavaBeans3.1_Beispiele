package kochrezepte.restful;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Kunde {
	@Id
	private int kdnr;
	private String name;

	@SuppressWarnings("unused")
	private Kunde() {
	}

	protected Kunde(int kdnr, String name) {
		this.kdnr = kdnr;
		this.name = name;
	}

	public void setKundennummer(int kdnr) {
		this.kdnr = kdnr;
	}

	public int getKundennummer() {
		return kdnr;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
