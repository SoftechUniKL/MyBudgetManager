import java.util.Date;

/**
 * Posten in der Budgetplanung
 */
public class Posten {
	
	/**
	 * Datum, wann der Posten verbucht wurde.
	 */
	private Date datum;
	
	/**
	 * Notiz, die bei der Eingabe vom Benutzer individuell eingegeben werden kann
	 * und den Posten beschreiben soll.
	 */
	private String notiz;
	
	/**
	 * Bezeichnung, die die Kategorie der Einnahme/Ausgabe angibt.
	 */
	private String bezeichnung;
	
	/**
	 * Betrag, der einmaligen Einnahme/Ausgabe
	 */
	private double betrag;
	
	/**
	 * interner Wert für Ein-/Ausgabe
	 */
	private int intern_Einnahme_Ausgabe;

	/**
	 * Konstruktor
	 
	 *    
	 */
	public Posten(Date datum,String notiz, String bezeichnung, double betrag, int intern_Einnahme_Ausgabe) {
		this.bezeichnung = bezeichnung;
		this.notiz = notiz;
		this.datum = datum;
		this.betrag = betrag;
		this.intern_Einnahme_Ausgabe = intern_Einnahme_Ausgabe;
	}

	public Date getDatum() {
		/**
		 *@return <Gibt das Datum der Buchung zurück>
		 */
		return datum; 
	}
	public String getnotiz() {
		/**
		 *@return <Gibt die eingegebene Notzi zurück>
		 */
		return notiz;
	}

	public String getBezeichnung() {
		/**
		 *@return <Gibt die ausgewählte Kategorie zurück>
		 */
		return bezeichnung;
	}

	public double getBetrag() {
		/**
		 *@return <Gibt den eingegebenen Geldbetrag zurück>
		 */
		return betrag;
	}
	
	public int getintern_Einnahme_Ausgabe() {
		/**
		 *@return <Gibt intern eine 0 oder eine 1 zurück>
		 */
		return intern_Einnahme_Ausgabe;
	}
}

