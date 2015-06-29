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
 * Datenmodell des Budgetplaners
 * 
 * Die Daten werden in der Datei data/budget.csv abgespeichert als CSV-Datei.
 * Hierbei handelt es sich um die 4 @param <datum,notiz,bezeichnung,betrag,intern_Einnahme_Ausgabe> (nähere Beschreibung unter Posten), 
 * die durch den Benutzer in der Gui @class <BudgetPlanGUI> eingelesen werden.
 * 
 * @param <Geldvermögen><>
 */
public class BudgetPlanModel {
	List<Posten> Geldvermögen;
		
	public BudgetPlanModel() {
		this.Geldvermögen = new ArrayList<Posten>();
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
				Geldvermögen.add(new Posten(datum,notiz, bezeichnung, betrag, intern_Einnahme_Ausgabe));
			}
			reader.close();
		
		/**
		 * Für das Auslesen der Daten aus der CSV Datei wurden drei Fehlermeldungen festgelegt. 
		 * 
		 * 
		 */
			
			
		/**
		 *@exception <FileNotFoundException e>  @throws <Ausgabe, dass die csv Datei nicht da ist>
		 */
		} catch (FileNotFoundException e) {
			System.err
					.println("Die Datei data/budget.csv wurde nicht gefunden!");
			System.exit(1);
			
			/**
			 *@exception <IOException e>  @throws <Ausgabe, dass es Probleme beim öffnen gab>
			 */
		} catch (IOException e) {
			System.err
					.println("Probleme beim Oeffnen der Datei data/budget.csv!");
			System.exit(1);
			
			/**
			 *@exception <ParseException e>  @throws <Ausgabe, dass des einen Formatfehler gibt>
			 */
		} catch (ParseException e) {
			System.err
					.println("Formatfehler: Die Datei konnte nicht eingelesen werden!");
			System.exit(1);
		}
		
		
		
		Collections.sort(Geldvermögen, new Comparator<Posten>() {
			@Override
			public int compare(Posten o1, Posten o2) {
				return o1.getDatum().compareTo(o2.getDatum());
			}
		});
	}
}
	

	


	
