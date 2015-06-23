import java.util.Date;

/**
 * Posten in der Budgetplanung
 */
public class Posten {
	/**
	 * Datum, wann der Posten verbucht wurde
	 */
	private Date datum;
	/**
	 * Kurze Beschreibung
	 */
	private String notiz;
	/**
	 * Kurze Beschreibung
	 */
	private String bezeichnung;
	/**
	 * Hoehe des Postens
	 */
	private double betrag;
	/**
	 * interner Wert für Ein-/Ausgabe
	 */
	private int intern_Einnahme_Ausgabe;

	/**
	 * Konstruktor
	 * 
	 * @param datum
	 *            Datum, wann der Posten verbucht wurde
	 * @param bezeichnung
	 *            Kurze Beschreibung
	 * @param betrag
	 *            Hoehe des Postens
	 */
	public Posten(Date datum,String notiz, String bezeichnung, double betrag, int intern_Einnahme_Ausgabe) {
		this.bezeichnung = bezeichnung;
		this.notiz = notiz;
		this.datum = datum;
		this.betrag = betrag;
		this.intern_Einnahme_Ausgabe = intern_Einnahme_Ausgabe;
	}

	public Date getDatum() {
		return datum;
	}
	public String getnotiz() {
		return notiz;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public double getBetrag() {
		return betrag;
	}
	
	public int getintern_Einnahme_Ausgabe() {
		return intern_Einnahme_Ausgabe;
	}
}

