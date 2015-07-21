public class Meldungen {

	// Fehlermeldungen CSV-Datein:
	/**
	 * Nachricht bei fehlender csv Datei:
	 */
	static String csv_nichtgefunden = "Die Datei data/budget.csv wurde nicht gefunden!";
	/**
	 * Nachrichtentext, wenn die csv Datein Probleme hat:
	 */
	static String csv_problemöffnen = "Probleme beim Oeffnen der Datei data/budget.csv!";

	// Fehlermeldungen bei Benutzereingabe:
	/**
	 * Nachricht bei einer falschen Betragseingabe:
	 */
	static String falsche_betragseingabe = "Falsche Betragseingabe. \n"
			+ "Formathinweis: \n"
			+ "- Bitte verwenden Sie nur Zahlen \n"
			+ "- Benutzen Sie bitte Punkt statt Komma \n"
			+ "- Achten Sie bitte auf die evtl. fehlenden zwei Nachkommastellen \n"
			+ "- Benutzen Sie bitte kein Negativzeichen";

	/**
	 * Nachrichtentext bei falscher Datumsanzeige:
	 */
	static String falsches_datum = "Falsche Datumseingabe. \n"
			+ "Formathinweis: TT.MM.JJJJ";

	static String falsche_datumsgroesse = "Falsche Datumsgrößen. \n"
			+ "Enddatum muss größer/gleich dem Anfangsdatum sein!";
	/**
	 * Nachricht beim Nichtauswählen der Kategorie:
	 */
	static String kategorie = "Bitte wählen sie eine Kategorie aus.";
	
	/**
	 * Nachricht beim Falscher Angabe des Sparintervalls oder Zinsperiode:
	 */
	static String periode_sparfkt="Fehlerhafte Auswahl. \n"
			+ "Bitte wählen sie ein Sparintervall aus! \n"
			+ "Bei jährlichem Sparintervall müssen sie außerdem eine Zinsperiode auswählen!";
	/**
	 * Nachricht beim fehleherhabfter Zeitraumsangabe:
	 */
	static String zeitraum_sparfkt="Falsche Zeitraumseingabe. \n"
			+ "Formathinweis: \n"
			+ "- Bitte verwenden Sie nur ganze Zahlen, die größer 0 sind \n"
			+ "- Benutzen Sie kein Negativzeichen!";
			
			
	// Sonstige Meldungen:
	
	/**
	 * Willkommensnachricht, die sich öffnet, wenn ein neues Konto geöffnet wird
	 */
	static String willkommen = "Willkommen bei MyBudgetManager! \n"
			+ "Um das Programm anwenden zu können, legen Sie einen Startwert Ihres Kontos fest. \n"
			+ "Befindet sich dieser im positiven Breich, tätigen Sie ihre Eröffnungsbuchung unter dem Menüunterpunkt: Einnahmen. \n"
			+ "Anderenfalls gehen Sie auf Ausgaben.  \n" + "\n"
			+ "Wir wünschen Ihnen viel Freunde an unserem tollen Produkt\n"
			+ "";
	
	/**
	 * Nachricht bei Ausgabenbuchung
	 */
	static String buchung_a = "Ausgabe erfolgreich gebucht.";
	/**
	 * Nachricht bei der Einnahmenbuchung
	 */
	static String buchung_e = "Einnahme erfolgreich gebucht.";
	/**
	 * Nachricht beim Kontolöschen
	 */
	static String konto_l = "Möchten Sie wirklich ihr Konto löschen? \n"
			+ "Sämtliche Daten gehen dabei verloren!";
	/**
	 * Nachricht, ob das Programm wirklich geschlossen werden soll
	 */
	static String prog_close = "Möchten Sie das Programm wirklich schließen?";
	/**
	 * Nachricht beim Klicken des Infobuttons
	 */
	static String info = "MyBudgetManager (Version 1.1.0 beta) 2015 \n \n"
			+ "Programmierprojekt SS2015 \n Leitung: Dr. Annette Bieniusa \n "
			+ "Fachbereich Informatik \n Lehrstuhl AG Softwaretechnik \n \n "
			+ "Programmierer: \n Philipp Heintz \n Lukas Breit \n Markus Dittmann \n \n"
			+ "Technische Universität Kaiserslautern \n \u00a9 copyright 2015 \n "
			+ "Die Programmierer übernehmen keine Haftung für enthaltene Fehler oder für Schäden, \n "
			+ "die im Zusammenhang mit der Verwendung der Software entstehen.";

	/**
	 * Nachrichtentext für den Taschenrechner
	 */
	static String taschenrechner = "Taschenrechner konnte nicht aufgerufen worden.";

	// Nachrichten des Hilfebutton für jeweiliges Panel:
	
	/**
	 * Nachrichtentext für den Hilfebutton im Panel Ausgaben
	 */
	static String hilfe_a = "Hilfe \n \n"
			+ "- Bei den Ausgaben ist es Ganz wichtig den Betrag in folgender Form anzugeben: \n  \n"
			+ "               X.XX Euro \n \n"
			+ " Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag gesetzt werden muss.\n"
			+ " Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- Wählen Sie immer eine Kategorie.  \n\n"
			+ "- Durch die Wahl eines Dauerauftrags werden die Aufträge nicht nur einmal sondern monatlich gebucht.\n "
			+ " Dadurch müssen sie regelmäßig anfallende Ausgaben nicht jeden Monat manuel eingeben. \n\n"
			+ "- In der Beschreibung können Angaben zur eigenen Buchung gemacht werden. \n  "
			+ " Dabei steht Ihnen frei, wie die Eingabe aussieht oder ob sie überhaupt gemacht wird. \n"
			+ "	 \n" + "ShortKeys: \n" + "- Buchen <alt + Enter\n"
			+ "- Reset <alt + R>\n" + "- Taschenrechner <alt + T>\n"
			+ "- Hilfebutton <alt + H> \n";
	/**
	 * Nachrichtentext für den Hilfebutton im Panel Einnahmen
	 */
	static String hilfe_e = "Hilfe \n \n"
			+ "- Bei den Einnahmen ist es Ganz wichtig den Betrag in folgender Form anzugeben: \n  \n"
			+ "               X.XX Euro \n \n"
			+ "  Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag gesetzt werden muss.\n"
			+ "  Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- Wählen Sie immer eine Kategorie.  \n\n"
			+ "- Durch die Wahl eines Dauerauftrags werden die Aufträge nicht nur einmal sondern monatlich gebucht.\n "
			+ " Dadurch müssen sie regelmäßig anfallende Einnahmen nicht jeden Monat manuel eingeben. \n\n"
			+ "- In der Beschreibung können Angaben zur eigenen Buchung gemacht werden. \n  "
			+ " Dabei steht Ihnen frei, wie die Eingabe aussieht oder ob sie überhaupt gemacht wird. \n"
			+ "  \n"
			+ "ShortKeys: \n"
			+ "- Buchen <alt + Enter>\n"
			+ "- Reset <alt + R>\n"
			+ "- Taschenrechner <alt + T>\n"
			+ "- Hilfebutton <alt + H>\n";
	/**
	 * Nachrichtentext für den Hilfebutton im Panel Statistik
	 */
	static String hilfe_stat = "\nHier können Sie sich die entsprechende Statistik zu Ihren Ausgaben und Einnahmen \n"
			+ "für einen bestimmten Zeitraum anzeigen lassen. \n"
			+ "Dabei können Sie zwischen den verschiedenen Statistik-Modellen wählen. \n \n"
			+ "Anleitung: \n \nSchritt 1: \n"
			+ "  Wählen Sie Ihren gewünschten Zeitraum aus, indem Sie 'Gesamtzeitraum' \n"
			+ "  (alle Einnahmen und Ausgaben seit Kontoeröffnung) oder \n  'individueller Zeitraum' auswählen. \n"
			+ "  Bei 'individueller Zeitraum' geben Sie bitte noch zusätzlich sowohl das \n  Anfangs- als auch das Enddatum an (Format: TT.MM.JJJJ). \n \n"
			+ "Schritt 2: \n  Wählen Sie das gewünschte Statistik-Modell aus der Auswahl-Liste aus. \n \n"
			+ "Schritt 3: \n  Klicken Sie nun auf den Button 'Statistik anzeigen' und \n  es öffnet sich ein neues Fenster mit Ihrer Statistik.\n\n"
			+ "Wichtige Anmerkung: \n  In der Statistik werden ausschließlich sowohl Einnahmen als auch Ausgaben verwendet. \n"
			+ "  D.h. der Betrag Ihrer Kontoeröffnung wird nicht berücksichtigt. \n\n"
			+ "Tipps: \n - Sie können sich auch mehrere Statistiken anzeigen lassen und vergleichen. \n"
			+ " - Bei den Kreisdiagramm-Grafiken lassen sich die Scheiben mittels Scrollrad der Maus individuell drehen. \n"
			+ " - Bei den restlichen Diagrammen können Sie individuell in die Grafik hineinzoomen, \n"
			+ "   indem Sie den gewünschten Bereich mit dem Mauszeiger auswählen. \n"
			+ "   (zur Ürsprungsübersicht gelangen: auf der Grafik linken Mauszeiger gedrückt nach oben ziehen) \n"
			+ " - Mit einem Klick der rechten Maustaste auf eine Grafik, können sie \n   Eigenschaften der Grafik verändern, drucken, abspeichern, uvm. \n"
			+ " - Auf der Titelleiste des 'Statistik-Fensters' wird das Datum samt Uhrzeit beim Öffnen angezeigt. \n"
			+ "   Dies hilft beim Vergleich mehrer geöffneter 'Statistik-Fenster' (z.B. Ermittlung der Reihenfolge) \n "
			+ " \n"
			+ "ShortKeys: \n"
			+ "- Statistik anzeigen <alt + Enter>\n"
			+ "- Taschenrechner <alt + T>\n"
			+ "- Hilfebutton <alt + H>\n";
	/**
	 * Nachrichtentext für den Hilfebutton im Panel Sparfunktion
	 */
	static String hilfe_spar = "Hilfe \n \n"
			+ "- Bei der Eingabe des Startwertes und der Sparrate muss der Betrag in folgender Form angegeben werden: \n  \n"
			+ "               X.XX Euro \n \n"
			+ "  Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag gesetzt werden muss.\n"
			+ "  Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- Wählen Sie immer eine Sparintervall.  \n"			
			+ "- Der Zeitraum muss als ganze Zahl angegeben werden und darf nicht 0 sein. \n"
			+ "- Aktivieren sie den Zinssatz für ihre Berechnung müssen Sie diesen selbstverständlich auch in der richtigen Form angeben(siehe oben)\n"
			+ "- Entscheiden sie sich für ein jährliches Sparintervall haben sie verschiedene Möglichkeiten eine Zinsperiode zu wählen\n"
			+ "  Bei aktivierten Zinssatz darf diese Auswahl nicht leer ausfallen!";		

}
