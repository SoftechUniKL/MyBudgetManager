public class Meldungen {

	// Fehlermeldungen CSV-Datein:
	/**
	 * Nachricht bei fehlender csv Datei:
	 */
	static String csv_nichtgefunden = "Die Datei data/budget.csv wurde nicht gefunden!";
	/**
	 * Nachrichtentext, wenn die csv Datein Probleme hat:
	 */
	static String csv_problem�ffnen = "Probleme beim Oeffnen der Datei data/budget.csv!";

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

	static String falsche_datumsgroesse = "Falsche Datumsgr��en. \n"
			+ "Enddatum muss gr��er/gleich dem Anfangsdatum sein!";
	/**
	 * Nachricht beim Nichtausw�hlen der Kategorie:
	 */
	static String kategorie = "Bitte w�hlen sie eine Kategorie aus.";
	
	/**
	 * Nachricht beim Falscher Angabe des Sparintervalls oder Zinsperiode:
	 */
	static String periode_sparfkt="Fehlerhafte Auswahl. \n"
			+ "Bitte w�hlen sie ein Sparintervall aus! \n"
			+ "Bei j�hrlichem Sparintervall m�ssen sie au�erdem eine Zinsperiode ausw�hlen!";
	/**
	 * Nachricht beim fehleherhabfter Zeitraumsangabe:
	 */
	static String zeitraum_sparfkt="Falsche Zeitraumseingabe. \n"
			+ "Formathinweis: \n"
			+ "- Bitte verwenden Sie nur ganze Zahlen, die gr��er 0 sind \n"
			+ "- Benutzen Sie kein Negativzeichen!";
			
			
	// Sonstige Meldungen:
	
	/**
	 * Willkommensnachricht, die sich �ffnet, wenn ein neues Konto ge�ffnet wird
	 */
	static String willkommen = "Willkommen bei MyBudgetManager! \n"
			+ "Um das Programm anwenden zu k�nnen, legen Sie einen Startwert Ihres Kontos fest. \n"
			+ "Befindet sich dieser im positiven Breich, t�tigen Sie ihre Er�ffnungsbuchung unter dem Men�unterpunkt: Einnahmen. \n"
			+ "Anderenfalls gehen Sie auf Ausgaben.  \n" + "\n"
			+ "Wir w�nschen Ihnen viel Freunde an unserem tollen Produkt\n"
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
	 * Nachricht beim Kontol�schen
	 */
	static String konto_l = "M�chten Sie wirklich ihr Konto l�schen? \n"
			+ "S�mtliche Daten gehen dabei verloren!";
	/**
	 * Nachricht, ob das Programm wirklich geschlossen werden soll
	 */
	static String prog_close = "M�chten Sie das Programm wirklich schlie�en?";
	/**
	 * Nachricht beim Klicken des Infobuttons
	 */
	static String info = "MyBudgetManager (Version 1.1.0 beta) 2015 \n \n"
			+ "Programmierprojekt SS2015 \n Leitung: Dr. Annette Bieniusa \n "
			+ "Fachbereich Informatik \n Lehrstuhl AG Softwaretechnik \n \n "
			+ "Programmierer: \n Philipp Heintz \n Lukas Breit \n Markus Dittmann \n \n"
			+ "Technische Universit�t Kaiserslautern \n \u00a9 copyright 2015 \n "
			+ "Die Programmierer �bernehmen keine Haftung f�r enthaltene Fehler oder f�r Sch�den, \n "
			+ "die im Zusammenhang mit der Verwendung der Software entstehen.";

	/**
	 * Nachrichtentext f�r den Taschenrechner
	 */
	static String taschenrechner = "Taschenrechner konnte nicht aufgerufen worden.";

	// Nachrichten des Hilfebutton f�r jeweiliges Panel:
	
	/**
	 * Nachrichtentext f�r den Hilfebutton im Panel Ausgaben
	 */
	static String hilfe_a = "Hilfe \n \n"
			+ "- Bei den Ausgaben ist es Ganz wichtig den Betrag in folgender Form anzugeben: \n  \n"
			+ "               X.XX Euro \n \n"
			+ " Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag gesetzt werden muss.\n"
			+ " Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- W�hlen Sie immer eine Kategorie.  \n\n"
			+ "- Durch die Wahl eines Dauerauftrags werden die Auftr�ge nicht nur einmal sondern monatlich gebucht.\n "
			+ " Dadurch m�ssen sie regelm��ig anfallende Ausgaben nicht jeden Monat manuel eingeben. \n\n"
			+ "- In der Beschreibung k�nnen Angaben zur eigenen Buchung gemacht werden. \n  "
			+ " Dabei steht Ihnen frei, wie die Eingabe aussieht oder ob sie �berhaupt gemacht wird. \n"
			+ "	 \n" + "ShortKeys: \n" + "- Buchen <alt + Enter\n"
			+ "- Reset <alt + R>\n" + "- Taschenrechner <alt + T>\n"
			+ "- Hilfebutton <alt + H> \n";
	/**
	 * Nachrichtentext f�r den Hilfebutton im Panel Einnahmen
	 */
	static String hilfe_e = "Hilfe \n \n"
			+ "- Bei den Einnahmen ist es Ganz wichtig den Betrag in folgender Form anzugeben: \n  \n"
			+ "               X.XX Euro \n \n"
			+ "  Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag gesetzt werden muss.\n"
			+ "  Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- W�hlen Sie immer eine Kategorie.  \n\n"
			+ "- Durch die Wahl eines Dauerauftrags werden die Auftr�ge nicht nur einmal sondern monatlich gebucht.\n "
			+ " Dadurch m�ssen sie regelm��ig anfallende Einnahmen nicht jeden Monat manuel eingeben. \n\n"
			+ "- In der Beschreibung k�nnen Angaben zur eigenen Buchung gemacht werden. \n  "
			+ " Dabei steht Ihnen frei, wie die Eingabe aussieht oder ob sie �berhaupt gemacht wird. \n"
			+ "  \n"
			+ "ShortKeys: \n"
			+ "- Buchen <alt + Enter>\n"
			+ "- Reset <alt + R>\n"
			+ "- Taschenrechner <alt + T>\n"
			+ "- Hilfebutton <alt + H>\n";
	/**
	 * Nachrichtentext f�r den Hilfebutton im Panel Statistik
	 */
	static String hilfe_stat = "\nHier k�nnen Sie sich die entsprechende Statistik zu Ihren Ausgaben und Einnahmen \n"
			+ "f�r einen bestimmten Zeitraum anzeigen lassen. \n"
			+ "Dabei k�nnen Sie zwischen den verschiedenen Statistik-Modellen w�hlen. \n \n"
			+ "Anleitung: \n \nSchritt 1: \n"
			+ "  W�hlen Sie Ihren gew�nschten Zeitraum aus, indem Sie 'Gesamtzeitraum' \n"
			+ "  (alle Einnahmen und Ausgaben seit Kontoer�ffnung) oder \n  'individueller Zeitraum' ausw�hlen. \n"
			+ "  Bei 'individueller Zeitraum' geben Sie bitte noch zus�tzlich sowohl das \n  Anfangs- als auch das Enddatum an (Format: TT.MM.JJJJ). \n \n"
			+ "Schritt 2: \n  W�hlen Sie das gew�nschte Statistik-Modell aus der Auswahl-Liste aus. \n \n"
			+ "Schritt 3: \n  Klicken Sie nun auf den Button 'Statistik anzeigen' und \n  es �ffnet sich ein neues Fenster mit Ihrer Statistik.\n\n"
			+ "Wichtige Anmerkung: \n  In der Statistik werden ausschlie�lich sowohl Einnahmen als auch Ausgaben verwendet. \n"
			+ "  D.h. der Betrag Ihrer Kontoer�ffnung wird nicht ber�cksichtigt. \n\n"
			+ "Tipps: \n - Sie k�nnen sich auch mehrere Statistiken anzeigen lassen und vergleichen. \n"
			+ " - Bei den Kreisdiagramm-Grafiken lassen sich die Scheiben mittels Scrollrad der Maus individuell drehen. \n"
			+ " - Bei den restlichen Diagrammen k�nnen Sie individuell in die Grafik hineinzoomen, \n"
			+ "   indem Sie den gew�nschten Bereich mit dem Mauszeiger ausw�hlen. \n"
			+ "   (zur �rsprungs�bersicht gelangen: auf der Grafik linken Mauszeiger gedr�ckt nach oben ziehen) \n"
			+ " - Mit einem Klick der rechten Maustaste auf eine Grafik, k�nnen sie \n   Eigenschaften der Grafik ver�ndern, drucken, abspeichern, uvm. \n"
			+ " - Auf der Titelleiste des 'Statistik-Fensters' wird das Datum samt Uhrzeit beim �ffnen angezeigt. \n"
			+ "   Dies hilft beim Vergleich mehrer ge�ffneter 'Statistik-Fenster' (z.B. Ermittlung der Reihenfolge) \n "
			+ " \n"
			+ "ShortKeys: \n"
			+ "- Statistik anzeigen <alt + Enter>\n"
			+ "- Taschenrechner <alt + T>\n"
			+ "- Hilfebutton <alt + H>\n";
	/**
	 * Nachrichtentext f�r den Hilfebutton im Panel Sparfunktion
	 */
	static String hilfe_spar = "Hilfe \n \n"
			+ "- Bei der Eingabe des Startwertes und der Sparrate muss der Betrag in folgender Form angegeben werden: \n  \n"
			+ "               X.XX Euro \n \n"
			+ "  Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag gesetzt werden muss.\n"
			+ "  Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- W�hlen Sie immer eine Sparintervall.  \n"			
			+ "- Der Zeitraum muss als ganze Zahl angegeben werden und darf nicht 0 sein. \n"
			+ "- Aktivieren sie den Zinssatz f�r ihre Berechnung m�ssen Sie diesen selbstverst�ndlich auch in der richtigen Form angeben(siehe oben)\n"
			+ "- Entscheiden sie sich f�r ein j�hrliches Sparintervall haben sie verschiedene M�glichkeiten eine Zinsperiode zu w�hlen\n"
			+ "  Bei aktivierten Zinssatz darf diese Auswahl nicht leer ausfallen!";		

}
