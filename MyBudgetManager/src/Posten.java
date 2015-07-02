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

	
	/**
	 * 
	 * @return datum Gibt das Datum zurück 
	 */
	public Date getDatum() {
		
		return datum; 
	}
	
	
	/**
	 * 
	 * @return notiz 
	 */
	public String getnotiz() {
		
		return notiz;
	}
	
	
	/**
	 * 
	 * @return bezeichnung 
	 */
	public String getBezeichnung() {
		
		return bezeichnung;
	}

	
	/**
	 * 
	 * @return betrag (Bekommt den zu buchenden Betrag
	 */
	public double getBetrag() {
		
		return betrag;
	}
	
	
	/**
	 * 
	 * @return intern_Einnahme_Ausgabe 
	 * Gibt eine 0 oder 1 zurück
	 * 0 = Einnahme
	 * 1 = Ausgabe
	 */
	public int getintern_Einnahme_Ausgabe() {
		
		return intern_Einnahme_Ausgabe;
	}
}

