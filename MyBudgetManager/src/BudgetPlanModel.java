import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.opencsv.CSVReader;

/**
 * Datenmodell des Budgetplaners.
 * 
 * Die Daten werden in der Datei data/budget.csv abgespeichert als CSV-Datei.
 * 
 * @exception FileNotFoundException @throws Ausgabe, dass die csv Datei nicht da ist
 * 
 * @exception IOException @throws Ausgabe, dass es Probleme beim �ffnen gab
 * 
 * @exception ParseException @throws Ausgabe, dass des einen Formatfehler gibt
 */

public class BudgetPlanModel {
	List<Posten> Geldverm�gen;
		
	public BudgetPlanModel() {
		this.Geldverm�gen = new ArrayList<Posten>();
		try {
			// Zeilenweises Einlesen der Daten
			CSVReader reader = new CSVReader(new FileReader("data/budget.csv"));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
				Date datum = df.parse(nextLine[0]);
				String notiz = nextLine[1];
				String bezeichnung = nextLine[2];
				double betrag = Double.parseDouble(nextLine[3]);
				int intern_Einnahme_Ausgabe = Integer.parseInt(nextLine[4]);
				Geldverm�gen.add(new Posten(datum,notiz, bezeichnung, betrag, intern_Einnahme_Ausgabe));
			}
			reader.close();
		
		
			
			
		// F�r das Auslesen der Daten aus der CSV Datei wurden drei Fehlermeldungen festgelegt. 
			
		// Fehlermeldung, wenn die Csv Datei nicht gefunden wird. 
		} catch (FileNotFoundException e) {
			System.err
					.println("Die Datei data/budget.csv wurde nicht gefunden!");
			System.exit(1);
			
		// Fehlermeldung, wenn die Csv Datein probleme beim �ffnen hat.
		} catch (IOException e) {
			System.err
					.println("Probleme beim Oeffnen der Datei data/budget.csv!");
			System.exit(1);
		
		// Fehlermeldung, wenn es ein Problem beim lesen der Daten gibt.
		} catch (ParseException e) {
			System.err
					.println("Formatfehler: Die Datei konnte nicht eingelesen werden!");
			System.exit(1);
		}
		
		
		
		Collections.sort(Geldverm�gen, new Comparator<Posten>() {
			@Override
			public int compare(Posten o1, Posten o2) {
				return o1.getDatum().compareTo(o2.getDatum());
			}
		});
	}
}
	

	


	
