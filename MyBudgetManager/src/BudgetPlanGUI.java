import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*; 

import javax.swing.*; 


/**
 * Graphische Benutzeroberflaeche des BudgetPlaners
 * 
 */
public class BudgetPlanGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	
	//	/**
//	 * Tabelle mit Uebersicht der Ausgaben
//	 */
//	private JTable table;
//	/**
//	 * Scrollelemente, das die Tabelle umfasst
//	 */
//	private JScrollPane scrollpane;
//	/**
//	 * Schaltflaeche, die beim Klicken einen Dialog anzeigt
//	 */
//	private JButton button;
	
	
	
	
	/**
	/* Modell der Daten
	 */
	private BudgetPlanModel budget;
	//panel Headline:	
	private JPanel panel;
	private JLabel lblKontostandsanzeige,lblKontostand,lblDatum;
	
	//panel_1:
	private JPanel panel_1;
	private GroupLayout gl_panel_1;
	private JButton btnKontoLöschen,btnInfo, btnTaschenrechner, btnSchließen;
	
	//TABBED PANE:
	private JTabbedPane tabbedPane;
	
	//panel_Konto:
	private JPanel panel_Konto;
	private JPanel panel_Kontouebersicht;
	private JLabel lblKontouebersicht;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnKontoDrucken;
	
	//panel_Ausgaben:
	private JPanel Panel_Ausgaben;
	private JTextField textField_Ausgaben, txtTtmmjjjj_Ausgaben,textFieldNotiz_Ausgabe;
	private JLabel lblAusgabenBuchungsbetrag, lblAusgabenKategorie,lblAusgabenWiederholung,
				   lblAusgabenFaelligkeitsdatum, lblNotiz_Ausgaben,lblEuroZb,label_Datum;
	private JButton btnAusgabenBuchen, btnReset_Ausgaben;
	private JRadioButton rdbtnEinmaligAusgaben,rdbtnMonatlichAusgaben;
	private JComboBox comboBox_Ausgaben;
	private JButton btnHelpButton_Ausgaben;
	
	//panel_Einnahmen:	
	private JPanel Panel_Einnahmen;
	private JLabel lblEinnahmenBuchungsbetrag,lblEinnahmenKategorie,lblEinnahmenWiederholung,
	lblEinnahmenFaelligkeitsdatum,lblNotiz_Einnahmen,lblEuro,lblTtmmyyyy;
	private JButton btnEinnahmenBuchen,btnReset_Einnahmen;
	private JRadioButton rdbtnEinnahmenEinmalig,rdbtnEinnahmenMonatlich;	
	private JTextField  txtTtmmjjjj_Einnahmen,textField_Einnahmen,textFieldNotiz_Einnahmen;
	private JComboBox comboBox_Einnahmen;
	private JButton btnHelpButton_Einnahmen;
	
	//panel_Daueraufträge:
	private JPanel panel_Dauerauftraege;
	
	//panel_Statistiken
	private JPanel Panel_Statistiken;
	private JRadioButton rdbtnGesamtzeitraum;
	private JRadioButton rdbtnIndividualZeitraum;
	private JTextField textField_Statistik_Startwert;
	private JTextField textField_Statistik_Endwert;
	private JLabel lblAnfangsdatum;
	private JLabel lblEnddatum;
	private JLabel lblStatistikWarnung;
	private JComboBox comboBoxStatistikModelle;
	private JButton btnStatistikanzeigen;
	private Date Start;
	private Date End;
	private SimpleDateFormat sdf;
	private DateFormat dateFormat;
	private Pattern p;
	private Matcher m;
	private Matcher m2;
	private SimpleDateFormat monatformat;
	
	//panel_Sparfunktion
	private JPanel panel_Sparfunktion;


	
	//Fehlermeldungen als vorgeschriebene Strings

	String falsche_betragseingabe = "Falsche Betragseingabe. \n"
			+ "Formathinweis: \n"
			+ "- Bitte verwenden Sie nur Zahlen \n"
			+ "- Benutzen Sie bitte Punkt statt Komma \n"
			+ "- Achten Sie bitte auf die evtl. fehlenden zwei Nachkommastellen \n"
			+ "- Benutzen Sie bitte kein Negativzeichen";

	String csv_nichtgefunden = "Die Datei data/budget.csv wurde nicht gefunden!";

	String csv_problemöffnen = "Probleme beim Oeffnen der Datei data/budget.csv!";

	String falsches_datum = "Falsche Datumseingabe. \n"
			+ "Formathinweis: TT.MM.JJJJ";
	
	String falsche_datumsgroesse = "Falsche Datumsgrößen. \n"
			+ "Enddatum muss größer/gleich dem Anfangsdatum sein!";

	String kategorie = "Bitte wählen sie eine Kategorie aus.";
	
	String taschenrechner = "Taschenrechner konnte nicht aufgerufen worden.";
	
	//Hilfe Meldungen als vorgeschriebene Strings
	
	String hilfe_a = "Hilfe \n \n"
			+ "- Bei den Ausgaben ist es Ganz wichtig den Betrag in folgender Form anzugeben: \n  \n"
			+ "               X.XX Euro \n \n"
			+ "  Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag setzen. Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- Wählen Sie immer eine Kategorie.  \n\n"
			+ "- Durch die Wahl eines Dauerauftrags werden die Aufträge nicht nur einmal sondern monatlich gebucht.\n "
			+ " Dadurch müssen sie regelmäßig anfallende Ausgaben nicht jeden Monat manuel eingeben. \n\n"
			+ "- In der Beschreibung können Angaben zur eigenen Buchung gemacht werden. \n  Dabei steht Ihnen frei, wie die Eingabe aussieht oder ob sie überhaupt gemacht wird." ;

	String hilfe_e = "Hilfe \n \n"
			+ "- Bei den Einnahmen ist es Ganz wichtig den Betrag in folgender Form anzugeben: \n  \n"
			+ "               X.XX Euro \n \n"
			+ "  Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag setzen. Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- Wählen Sie immer eine Kategorie.  \n\n"
			+ "- Durch die Wahl eines Dauerauftrags werden die Aufträge nicht nur einmal sondern monatlich gebucht.\n "
			+ " Dadurch müssen sie regelmäßig anfallende Einnahmen nicht jeden Monat manuel eingeben. \n\n"
			+ "- In der Beschreibung können Angaben zur eigenen Buchung gemacht werden. \n  Dabei steht Ihnen frei, wie die Eingabe aussieht oder ob sie überhaupt gemacht wird." ;
	
	//Willkommensmeldung als vorgeschriebene Strings
	
	String willkommen = "Willkommen bei MyBudgetManager! \n"
			+ "Um das Programm anwenden zu können, legen Sie einen Startwert Ihres Kontos fest. \n"
			+ "Befindet sich dieser im positiven Breich, tätigen Sie ihre Eröffnungsbuchung unter dem Menüunterpunkt: Einnahmen. \n"
			+ "Anderenfalls gehen Sie auf Ausgaben.  \n"
			+ "\n"
			+ "Wir wünschen Ihnen viel Freunde an unserem tollen Produkt\n"
			+ "";
	
	//Dialoge als vorgeschriebene Strings
	
	String buchung_a = "Ausgabe erfolgreich gebucht.";
	
	String buchung_e = "Einnahme erfolgreich gebucht.";
	
	String konto_l = "Möchten Sie wirklich ihr Konto löschen? \n"
			+ "Sämtliche Daten gehen dabei verloren!";
	
	String info = "MyBudgetManager (Version 1.0.2 beta) 2015 \n \n"
			+ "Programmierprojekt SS2015 \n Leitung: Dr. Annette Bieniusa \n "
			+ "Fachbereich Informatik \n Lehrstuhl AG Softwaretechnik \n \n "
			+ "Programmierer: \n Philipp Heintz \n Lukas Breit \n Markus Dittmann \n \n"
			+ "Technische Universität Kaiserslautern \n \u00a9 copyright 2015 \n "
			+ "Die Programmierer übernehmen keine Haftung für enthaltene Fehler oder für Schäden, \n "
			+ "die im Zusammenhang mit der Verwendung der Software entstehen.";
	
	
	/**
	 * Konstruktor fuer die GUI.
	 * 
	 * Hier wird das Hauptfenster initialisiert und aktiviert.
	 * 
	 * @param budget
	 *            Modell der Daten
	 */
	
	
	public BudgetPlanGUI(BudgetPlanModel budget) {
		super("MyBudgetManager");
		
		 try {
		     setIconImage(getToolkit().getImage("src/img/Money.png"));
		  } catch (Exception whoJackedMyIcon) {
		     System.out.println("Could not load program icon.");
		  }
		 
		 

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 450, 300); // Groesse des Frames
		setVisible(true); // Frame wird sichtbar
		getContentPane().setLayout(new BorderLayout(0, 0));
		setMinimumSize(new Dimension(800, 550));
		setLocationRelativeTo(null);

		
		this.budget = budget;
		initWindow(); // Initialisierung des Frameinhalts
		addBehavior(); // Verhalten der GUI Elemente dieses Frames
			
	}
	
	
	
	
	
	





	public void Anfangsabfrage(){
		
		if (budget.Geldvermögen.size() == 0) {
			JOptionPane.showMessageDialog(null, willkommen ,"Willkommen bei MyBudgetManager", JOptionPane.INFORMATION_MESSAGE);
			
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(3, false);
			tabbedPane.setEnabledAt(4, false);
			tabbedPane.setEnabledAt(5, false);
			tabbedPane.setSelectedIndex(2);
			
			comboBox_Ausgaben.insertItemAt("Kontoeröffnung", 1);
			comboBox_Ausgaben.setSelectedIndex(1);
			comboBox_Ausgaben.setEnabled(false);
			
			comboBox_Einnahmen.insertItemAt("Kontoeröffnung", 1);
			comboBox_Einnahmen.setSelectedIndex(1);
			comboBox_Einnahmen.setEnabled(false);
			
			btnReset_Einnahmen.setEnabled(false);
			btnReset_Ausgaben.setEnabled(false);
			}
		
	}
	
	public void Aktivierung(){
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setEnabledAt(3, true);
		tabbedPane.setEnabledAt(4, true);
		tabbedPane.setEnabledAt(5, true);
		
		comboBox_Ausgaben.removeItemAt(1);
		comboBox_Ausgaben.setEnabled(true);
		
		comboBox_Einnahmen.removeItemAt(1);
		comboBox_Einnahmen.setEnabled(true);
		
		btnReset_Einnahmen.setEnabled(true);
		btnReset_Ausgaben.setEnabled(true);
		
	}
	 /**
	   * 
	   * 
	   * Start der Methoden für die Ausgaben
	   * 
	   * 
	   * 
	   * 
	   * 
	   * 
	   */
	
	public void Checkdata_Ausgaben() {
		try {
			// Check date validation
			if (txtTtmmjjjj_Ausgaben.getText().length() != 10)
				throw new ParseException("Fehler", 0);
	  			
			
			Pattern p = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
			Matcher m = p.matcher(txtTtmmjjjj_Ausgaben.getText());
			boolean b = m.matches();
			if (b == false)
				throw new ParseException("Fehler", 0);
	  			
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			dateFormat.setLenient(false); // strenge Datumsprüfung einschalten
			dateFormat.parse(txtTtmmjjjj_Ausgaben.getText());
			
			
			double k = Double.parseDouble(textField_Ausgaben.getText());
			if (k < 0)
				throw new NumberFormatException();
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
			dfs.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("#0.00" ,dfs); 
			String j;
			j = df.format(k);
			if  (! textField_Ausgaben.getText().equals(j))
				throw new NumberFormatException();
						
			
			if (comboBox_Ausgaben.getSelectedIndex() == 0)
					throw new IndexOutOfBoundsException();
			
			CSVWriter writer = null;
  		
  		try {
  			writer = new CSVWriter(new FileWriter("data/budget.csv", true));
  			String[] entries = new String[5];
  			
  			entries[0] = txtTtmmjjjj_Ausgaben.getText();
  			entries[1] = textFieldNotiz_Ausgabe.getText();
  			entries[2] = (String) comboBox_Ausgaben.getSelectedItem();
  			entries[3] = "-" + textField_Ausgaben.getText(); 
  			entries[4] = "1"; // 1 gilt als Ausgabe. Nur intern sichtbar 
  			writer.writeNext(entries);
  			writer.close();
  			Clear_Ausgaben();
  			JOptionPane.showMessageDialog(null, buchung_a, "Ausgabenbuchung", JOptionPane.INFORMATION_MESSAGE);
  			Init_Kontostand();
  			
  		} catch (FileNotFoundException ex) {
  			System.err
  					.println(csv_nichtgefunden); 
  			System.exit(1);
  		} catch (IOException ex) {
  			System.err
  					.println(csv_problemöffnen);
  			System.exit(1);
  		} 
			
		} 

		catch (ParseException e) {
			JOptionPane.showMessageDialog(null, falsches_datum, "Fehler", JOptionPane.ERROR_MESSAGE); 
			txtTtmmjjjj_Ausgaben.setText(null);
			}
					
		
		catch ( NumberFormatException e)
		{JOptionPane.showMessageDialog(null, falsche_betragseingabe, "Fehler", JOptionPane.ERROR_MESSAGE); 
		textField_Ausgaben.setText(null);
	}
		
		catch ( IndexOutOfBoundsException e)
		{JOptionPane.showMessageDialog(null, kategorie, "Fehler", JOptionPane.ERROR_MESSAGE); 
	
		}

	}
	  public void Clear_Ausgaben() {
			textField_Ausgaben.setText(null);
			txtTtmmjjjj_Ausgaben.setText(new SimpleDateFormat("dd.MM.yyyy")
					.format(new Date()));
			comboBox_Ausgaben.setSelectedIndex(0);
			textFieldNotiz_Ausgabe.setText(null);
		}
	  
	  public void Init_Kontoübersicht(){
		  Object[][] data = new Object[budget.Geldvermögen.size()][5];		
			int i = 0;	
			String m;
			for (Posten p : budget.Geldvermögen) {		
				data[i][0] = new SimpleDateFormat("dd.MM.yyyy")		
						.format(p.getDatum());	
				data[i][1] = p.getBezeichnung();	
				data[i][2] = p.getnotiz();		
				data[i][3] = p.getBetrag();
				if  (p.getintern_Einnahme_Ausgabe() == 0)
					m = "Einnahme";
				else
					m = "Ausgabe";
				
				data[i][4] = m;
				i++;		
			}

			table = new JTable(data, new Object[] { "Datum", "Kategorie", "Beschreibung", "Betrag", "Buchungsart" });		
			table.enable(false);

			table.setAutoCreateRowSorter(true);
			scrollPane.setViewportView(table);
	  }
	  
	  public void Init_Kontostand(){
		  NumberFormat nf = NumberFormat.getInstance(new Locale("de", "DE"));
			double i = 0;
			budget.Geldvermögen.clear();
			
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
					budget.Geldvermögen.add(new Posten(datum, notiz, bezeichnung, betrag, intern_Einnahme_Ausgabe));
				    
				}
				reader.close();

			} catch (FileNotFoundException e) {
				System.err
						.println(csv_nichtgefunden);
				System.exit(1);
			} catch (IOException e) {
				System.err
						.println(csv_problemöffnen);
				System.exit(1);
			} catch (ParseException e) {
				System.err
						.println("Formatfehler: Die Datei konnte nicht eingelesen werden!");
				System.exit(1);
			}
			Collections.sort(budget.Geldvermögen, new Comparator<Posten>() {
				@Override
				public int compare(Posten o1, Posten o2) {
					return o1.getDatum().compareTo(o2.getDatum());
				}
			});
			
			for (Posten p : budget.Geldvermögen) {
				i += p.getBetrag();
			}
			
			
			lblKontostand.setText(nf.format(i)+"");
			if (i >= 0) {
				lblKontostand.setForeground(new Color(20, 170, 20));
			}
			else {
				lblKontostand.setForeground(Color.RED);
			}
			if (i<20 && i>=0 && budget.Geldvermögen.size() != 0)
				JOptionPane.showMessageDialog(null, "Ihr Kontostand ist sehr gering.\n"
						+ "Bitte achten Sie auf Ihre Ausgaben.", "Kontostandswarnung", JOptionPane.WARNING_MESSAGE);
			if (i < 0)
				JOptionPane.showMessageDialog(null, "Ihr Kontostand befindet sich im negativen Bereich.\n"
						+ "Bitte achten Sie auf Ihre Ausgaben.", "Kontostandswarnung", JOptionPane.WARNING_MESSAGE);
			
			
			Init_Kontoübersicht();
			Statistik_Warnung();
			}
	  
	  
	  /**
	   * 
	   * 
	   * Start der Methoden für die Einnahmen
	   * 
	   * 
	   * 
	   * 
	   * 
	   * 
	   */
	  
	  public void CheckData_Einnahmen(){
		  try {
				// Check date validation
				if (txtTtmmjjjj_Einnahmen.getText().length() != 10)
					throw new ParseException("Fehler", 0);
		  			
				
				Pattern p = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
				Matcher m = p.matcher(txtTtmmjjjj_Einnahmen.getText());
				boolean b = m.matches();
				if (b == false)
					throw new ParseException("Fehler", 0);
		  			
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				dateFormat.setLenient(false); // strenge Datumsprüfung einschalten
				dateFormat.parse(txtTtmmjjjj_Einnahmen.getText());
				
				
				
				double k = Double.parseDouble(textField_Einnahmen.getText());
				if (k < 0)
					throw new NumberFormatException();
				DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
				dfs.setDecimalSeparator('.');
				DecimalFormat df = new DecimalFormat("#0.00" ,dfs); 
				String j;
				j = df.format(k);
				if  (! textField_Einnahmen.getText().equals(j))
					throw new NumberFormatException();
				
				
				if (comboBox_Einnahmen.getSelectedIndex() == 0)
						throw new IndexOutOfBoundsException();
				
				CSVWriter writer = null;
	  		
	  		try {
	  			writer = new CSVWriter(new FileWriter("data/budget.csv", true));
	  			String[] entries = new String[5];
	  			
	  			entries[0] = txtTtmmjjjj_Einnahmen.getText();
	  			entries[1] = textFieldNotiz_Einnahmen.getText();
	  			entries[2] = (String) comboBox_Einnahmen.getSelectedItem();
	  			entries[3] = textField_Einnahmen.getText();
	  			entries[4] = "0"; // 0 gilt als Einnahme. Nur intern sichtbar 
	  			writer.writeNext(entries);
	  			writer.close();
	  			Clear_Einnahmen();
	  			JOptionPane.showMessageDialog(null, buchung_e, "Einnahmenbuchung", JOptionPane.INFORMATION_MESSAGE);
	  			Init_Kontostand();
	  			
	  		} catch (FileNotFoundException ex) {
	  			System.err
	  					.println(csv_nichtgefunden);
	  			System.exit(1);
	  		} catch (IOException ex) {
	  			System.err
	  					.println(csv_problemöffnen);
	  			System.exit(1);
	  		} 
				
			}
			catch (ParseException e) {
				JOptionPane.showMessageDialog(null, falsches_datum, "Fehler", JOptionPane.ERROR_MESSAGE); 
				txtTtmmjjjj_Einnahmen.setText(null);
			}
			
			catch ( NumberFormatException e)
			{JOptionPane.showMessageDialog(null, falsche_betragseingabe, "Fehler", JOptionPane.ERROR_MESSAGE); 
			textField_Einnahmen.setText(null);
			}
			
			catch ( IndexOutOfBoundsException e)
			{JOptionPane.showMessageDialog(null, kategorie, "Fehler", JOptionPane.ERROR_MESSAGE); 
		
			}
		}
	  
		  
	  
	  public void Clear_Einnahmen(){
		  textField_Einnahmen.setText(null);
			txtTtmmjjjj_Einnahmen.setText(new SimpleDateFormat("dd.MM.yyyy")
					.format(new Date()));
			comboBox_Einnahmen.setSelectedIndex(0); 
			textFieldNotiz_Einnahmen.setText(null);
	  }

	  /**
		 * 
		 * Methoden für die Statistik 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */

	public void Grafikmodellauswahl(String selection) {
		Init_Kontostand();
		if (rdbtnIndividualZeitraum.isSelected() == true) {
			if ((Statistik_CheckAuswahlDatum() == true)
					&& (Statistik_CheckDateDiff() == true)) {
				Statistik testen = new Statistik(budget);
				testen.Statistik_Manager(selection,
						textField_Statistik_Startwert.getText(),
						textField_Statistik_Endwert.getText());
			}
		} else {
			Statistik testen = new Statistik(budget);
			testen.Statistik_Manager(selection, "0", "0");
		}
	}

	public boolean Statistik_CheckAuswahlDatum() {

		try {
			// Check date validation Datumsstartwert & Datumsendwert
			if ((textField_Statistik_Startwert.getText().length() != 10)
					|| (textField_Statistik_Endwert.getText().length() != 10))
				throw new ParseException("Fehler", 0);

			p = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
			m = p.matcher(textField_Statistik_Startwert.getText());
			m2 = p.matcher(textField_Statistik_Endwert.getText());
			boolean b = m.matches(), b2 = m2.matches();
			if ((b == false) || (b2 == false))
				throw new ParseException("Fehler", 0);

			dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			dateFormat.setLenient(false); // strenge Datumsprüfung einschalten
			dateFormat.parse(textField_Statistik_Startwert.getText());
			dateFormat.parse(textField_Statistik_Endwert.getText());

			return true;

		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, falsches_datum, "Fehler",
					JOptionPane.ERROR_MESSAGE);
			textField_Statistik_Endwert.setText(null);
			textField_Statistik_Startwert.setText(null);
			return false;
		}
	}

	public boolean Statistik_CheckDateDiff() {
		try {
			sdf = new SimpleDateFormat("dd.MM.yyyy");
			Start = sdf.parse(textField_Statistik_Startwert.getText());
			End = sdf.parse(textField_Statistik_Endwert.getText());
			if (End.compareTo(Start) < 0)
				throw new Exception();
			return true;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, falsche_datumsgroesse,
					"Fehler", JOptionPane.ERROR_MESSAGE);
			textField_Statistik_Endwert.setText(null);
			textField_Statistik_Startwert.setText(null);

			return false;
		}
	}

	public void Statistik_Warnung() {
		int Zaehler_einnahmen = 0, Zaehler_ausgaben = 0, x = -1, y = -1;
		String monat_einnahmen = null, monat_ausgaben = null;
		boolean month_einnahmen = false, month_ausgaben = false;
		monatformat = new SimpleDateFormat("MM.yyyy");

		List<Posten> tmp = new ArrayList<Posten>();
		for (Posten p : budget.Geldvermögen)
			if (!p.getBezeichnung().equals("Kontoeröffnung"))
				tmp.add(p);
		for (Posten p : tmp) {
			x++;
			if (p.getintern_Einnahme_Ausgabe() == 0)
				break;
		}
		if (!tmp.isEmpty())
			monat_einnahmen = monatformat.format(tmp.get(x).getDatum());

		for (Posten p : tmp) {
			y++;
			if (p.getintern_Einnahme_Ausgabe() == 1)
				break;
		}
		if (!tmp.isEmpty())
			monat_ausgaben = monatformat.format(tmp.get(y).getDatum());

		for (Posten p : tmp) {
			if (p.getintern_Einnahme_Ausgabe() == 0) {
				Zaehler_einnahmen++;
				if (monatformat.format(p.getDatum()).compareTo(monat_einnahmen) != 0)
					month_einnahmen = true;
				monat_einnahmen = monatformat.format(p.getDatum());
			} else {
				Zaehler_ausgaben++;

				if (monatformat.format(p.getDatum()).compareTo(monat_ausgaben) != 0)
					month_ausgaben = true;
				monat_ausgaben = monatformat.format(p.getDatum());
			}
		}
		if ((Zaehler_einnahmen > 20) && (Zaehler_ausgaben > 20)
				&& (month_einnahmen == true) && (month_ausgaben == true)) {
			lblStatistikWarnung.setText("");
			lblStatistikWarnung.setBackground(new Color(240, 240, 240));

		} else {
			lblStatistikWarnung
					.setText("<html><body><center><u>ACHTUNG!</u></center>Für eine sinnvolle Statistik<br>sind für einige Modelle<br>zu wenig Daten vorhanden!</body></html>");
			lblStatistikWarnung.setBackground(new Color(250, 250, 120));
			lblStatistikWarnung.setOpaque(true);
		}
	}
		
	/**
	 * 
	 * Initalisierung des Fensters 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	  
	  
	  
	protected void initWindow() {

		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(0,80));
		panel.setLayout(null);
		
//		///////////////////////////
		//Panel 1 Anfang
		
		/**
		 * 
		 * 
		 * Panel in dem der Kontostand angezeigt und aktualisiert wird
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		lblKontostandsanzeige = new JLabel("Kontostand in [\u20ac]:");
		lblKontostandsanzeige.setForeground(Color.BLUE);
		lblKontostandsanzeige.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKontostandsanzeige.setBounds(340, 0, 156, 34);
		panel.add(lblKontostandsanzeige);
		
		lblKontostand = new JLabel("0");
		lblKontostand.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblKontostand.setBounds(170, 35, 470, 35);
		lblKontostand.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblKontostand);
		
		
		lblDatum = new JLabel("Datum: " + new SimpleDateFormat("E, dd.MM.yyyy").format(new Date()));
		lblDatum.setBounds(10, 49, 142, 14);
		panel.add(lblDatum);

		/**
		 * 
		 * 
		 * Panel für externe Buttons
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setPreferredSize(new Dimension(0,50));
		
		
		
		btnInfo = new JButton("Info");
				
		
		btnTaschenrechner = new JButton("Taschenrechner");
		
		btnSchließen = new JButton("Schlie\u00DFen");
		
		btnKontoLöschen = new JButton("Konto l\u00F6schen");
		
		
		gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnKontoLöschen)
					.addGap(45)
					.addComponent(btnTaschenrechner)
					.addPreferredGap(ComponentPlacement.RELATED, 354, Short.MAX_VALUE)
					.addComponent(btnSchließen)
					.addGap(27)
					.addComponent(btnInfo)
					.addGap(20))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnInfo)
						.addComponent(btnTaschenrechner)
						.addComponent(btnSchließen)
						.addComponent(btnKontoLöschen))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
	
		/**
		 * 
		 * 
		 * Panel für Hauptmenue
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		panel_Konto = new JPanel();
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Konto</body></html>", null, panel_Konto, null);
		panel_Konto.setLayout(new BorderLayout(0, 0));
		
		Panel_Ausgaben = new JPanel();
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Ausgaben</body></html>", null, Panel_Ausgaben, null);
		Panel_Ausgaben.setLayout(null);
		
		Panel_Einnahmen = new JPanel();
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Einnahmen</body></html>", null, Panel_Einnahmen, null);
		Panel_Einnahmen.setLayout(null);
			
		panel_Dauerauftraege = new JPanel();
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Daueraufträge</body></html>", null, panel_Dauerauftraege, null);
		panel_Dauerauftraege.setLayout(null);
		
		Panel_Statistiken = new JPanel();
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Statistik</body></html>", null, Panel_Statistiken, null);
		Panel_Statistiken.setLayout(null);
		
		panel_Sparfunktion = new JPanel();
		tabbedPane.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Sparfunktion</body></html>", null, panel_Sparfunktion, null);
		panel_Sparfunktion.setLayout(null);
		
		
		//Panel 1 Ende
		
		//Panel 2 Kontoübersicht Anfang
		/**
		 * 
		 * 
		 * Panel in dem alle Ausgaben und Einnahmen aufgeführt werden
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		// Tabelle mit Uebersicht der Ausgaben und Einnahmen
		panel_Kontouebersicht = new JPanel();
		panel_Konto.add(panel_Kontouebersicht, BorderLayout.NORTH);
		panel_Kontouebersicht.setPreferredSize(new Dimension(0, 40));
		
		lblKontouebersicht = new JLabel("Konto\u00FCbersicht:                                                                  ");
		panel_Kontouebersicht.add(lblKontouebersicht);
		lblKontouebersicht.setFont(new Font("Tahoma", Font.BOLD, 18));
		
	    btnKontoDrucken = new JButton("Drucken");
		panel_Kontouebersicht.add(btnKontoDrucken);
	
		scrollPane = new JScrollPane();
		panel_Konto.add(scrollPane, BorderLayout.CENTER);
				
		//Panel 2 Kontoübersicht Ende
		
		//Panel3 Anfang
	        
	        
	    	/**
			 * 
			 * 
			 * Panel in dem alle Ausgaben erfasst werden
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
		
		
	    textField_Ausgaben = new JTextField();
		textField_Ausgaben.setBounds(197, 13, 150, 25);
		Panel_Ausgaben.add(textField_Ausgaben);
		textField_Ausgaben.setHorizontalAlignment(JTextField.RIGHT);
		
		lblAusgabenBuchungsbetrag = new JLabel("Buchungsbetrag:");
		lblAusgabenBuchungsbetrag.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAusgabenBuchungsbetrag.setBounds(21, 11, 142, 29);
		Panel_Ausgaben.add(lblAusgabenBuchungsbetrag);
		
		lblAusgabenKategorie = new JLabel("Kategorie:");
		lblAusgabenKategorie.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAusgabenKategorie.setBounds(21, 67, 142, 29);
		Panel_Ausgaben.add(lblAusgabenKategorie);
		
		comboBox_Ausgaben = new JComboBox();
		comboBox_Ausgaben.setModel(new DefaultComboBoxModel(new String[] {"(Bitte Wählen)", "Krankenversicherung", "Hausversicherung", "Kfz-Versicherung", "TÜV/Versicherung", "Benzin/Diesel", "Musik","Filmleihe", "Kino", "Bücher/Zeitschriften", "Kinder/Spielzeug", "Persönliche Gegenstände", "Bekleidung", "Restaurant/Mensa","Lebensmittel", "Müll", "Wasser", "Einkäufe", "Ersparnisse", "Haustiere","Party", "Haushalt", "Hotel/Unterkunft", "Kreditkarte", "Gesundheitsausgaben", "Krankenversicherung", "Internet/Telefon","Handy", "Gas/Heizung", "Strom", "Vermögenssteuer", "Mitgliedsgebür", "Miete","Kredit","Sonstiges"}));
		comboBox_Ausgaben.setBounds(197, 69, 155, 25);
		Panel_Ausgaben.add(comboBox_Ausgaben);
		
		 
		 lblAusgabenWiederholung = new JLabel("Wiederholung:");
		 lblAusgabenWiederholung.setFont(new Font("Tahoma", Font.BOLD, 11));
		 lblAusgabenWiederholung.setBounds(21, 130, 142, 29);
		 Panel_Ausgaben.add(lblAusgabenWiederholung);
		 
		 rdbtnEinmaligAusgaben = new JRadioButton("einmalig");
		 rdbtnEinmaligAusgaben.setBounds(197, 133, 109, 23);
		 Panel_Ausgaben.add(rdbtnEinmaligAusgaben);
		 
		 rdbtnMonatlichAusgaben = new JRadioButton("monatlich\r\n");
		 rdbtnMonatlichAusgaben.setBounds(308, 133, 109, 23);
		 Panel_Ausgaben.add(rdbtnMonatlichAusgaben);
		 
		 lblAusgabenFaelligkeitsdatum = new JLabel("F\u00E4lligkeitsdatum:");
		 lblAusgabenFaelligkeitsdatum.setFont(new Font("Tahoma", Font.BOLD, 11));
		 lblAusgabenFaelligkeitsdatum.setBounds(21, 189, 142, 29);
		 Panel_Ausgaben.add(lblAusgabenFaelligkeitsdatum);
		 
		 txtTtmmjjjj_Ausgaben = new JTextField();
		 txtTtmmjjjj_Ausgaben.setBounds(197, 190, 150, 26);       
		 txtTtmmjjjj_Ausgaben.setText( new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
		 txtTtmmjjjj_Ausgaben.setFont(new Font("Tahoma", Font.BOLD, 11));
		 Panel_Ausgaben.add(txtTtmmjjjj_Ausgaben);
		 
	      
		  
		  
		  btnAusgabenBuchen = new JButton("Ausgabe Buchen!");
		  btnAusgabenBuchen.setBounds(421, 311, 142, 29);
		  Panel_Ausgaben.add(btnAusgabenBuchen);		
		  
		  textFieldNotiz_Ausgabe = new JTextField();
		  textFieldNotiz_Ausgabe.setBounds(197, 252, 150, 25);
		  Panel_Ausgaben.add(textFieldNotiz_Ausgabe);

		  
		  lblNotiz_Ausgaben = new JLabel("Beschreibung:");
		  lblNotiz_Ausgaben.setFont(new Font("Tahoma", Font.BOLD, 11));
		  lblNotiz_Ausgaben.setBounds(21, 250, 142, 29);
		  Panel_Ausgaben.add(lblNotiz_Ausgaben);
		  
		  lblEuroZb = new JLabel(" Euro ( z.B. 5.55)");
		  lblEuroZb.setForeground(new Color(128, 128, 128));
		  lblEuroZb.setFont(new Font("Tahoma", Font.BOLD, 11));
		  lblEuroZb.setBounds(357, 18, 99, 14);
		  Panel_Ausgaben.add(lblEuroZb);
		  
		  label_Datum = new JLabel("TT.MM.JJJJ");
		  label_Datum.setForeground(new Color(128, 128, 128));
		  label_Datum.setFont(new Font("Tahoma", Font.BOLD, 11));
		  label_Datum.setBounds(357, 196, 67, 14);
		  Panel_Ausgaben.add(label_Datum);
		  
		  btnReset_Ausgaben = new JButton("Eingaben reseten");
		  btnReset_Ausgaben.setBounds(21, 311, 135, 29);
		  Panel_Ausgaben.add(btnReset_Ausgaben);
		  
		  btnHelpButton_Ausgaben = new JButton("Hilfe");
		  btnHelpButton_Ausgaben.setBounds(585, 0, 67, 20);
		  Panel_Ausgaben.add(btnHelpButton_Ausgaben);
		  btnHelpButton_Ausgaben.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent arg0) {

		  		
				JOptionPane.showMessageDialog(null, hilfe_a
						, "Hilfe", JOptionPane.INFORMATION_MESSAGE); 
			
	  	
		  	}
		  });
	        
	        //Panel 3 Ende
	        
	        //Panel 4 Einnahmen Anfang
	    	/**
			 * 
			 * 
			 * Panel in dem alle Einnahmen erfasst werden 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
		     	textField_Einnahmen = new JTextField();
		        textField_Einnahmen.setBounds(197, 13, 150, 25);
		        Panel_Einnahmen.add(textField_Einnahmen);
		        textField_Einnahmen.setHorizontalAlignment(JTextField.RIGHT);
		        
		        lblEinnahmenBuchungsbetrag=new JLabel("Buchungsbetrag:");
		        lblEinnahmenBuchungsbetrag.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblEinnahmenBuchungsbetrag.setBounds(21, 11, 142, 29);
		        Panel_Einnahmen.add(lblEinnahmenBuchungsbetrag);
		        
		        lblEinnahmenKategorie=new JLabel("Kategorie:");
		        lblEinnahmenKategorie.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblEinnahmenKategorie.setBounds(21, 67, 142, 29);
		        Panel_Einnahmen.add(lblEinnahmenKategorie);
		        
		        comboBox_Einnahmen = new JComboBox();
		        comboBox_Einnahmen.setModel(new DefaultComboBoxModel(new String[] {"(Bitte Wählen)","Lohn/Gehalt", "Taschengeld","Erbe", "Mieteinnahmen", "Kredite", "Verkauf","Zinsen","Mitgliedsbeitrag","Spende", "Sonstiges"}));
		        comboBox_Einnahmen.setBounds(197, 69, 155, 25);
		        Panel_Einnahmen.add(comboBox_Einnahmen);
		        
		        lblEinnahmenWiederholung=new JLabel("Wiederholung:");
		        lblEinnahmenWiederholung.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblEinnahmenWiederholung.setBounds(21, 130, 142, 29);
		        Panel_Einnahmen.add(lblEinnahmenWiederholung);
		        
		        rdbtnEinnahmenEinmalig=new JRadioButton("einmalig");
		        rdbtnEinnahmenEinmalig.setBounds(197, 133, 109, 23);
		        Panel_Einnahmen.add(rdbtnEinnahmenEinmalig);
		        
		        rdbtnEinnahmenMonatlich=new JRadioButton("monatlich\r\n");
		        rdbtnEinnahmenMonatlich.setBounds(308, 133, 109, 23);
		        Panel_Einnahmen.add(rdbtnEinnahmenMonatlich);
		        
		        lblEinnahmenFaelligkeitsdatum=new JLabel("F\u00E4lligkeitsdatum:");
		        lblEinnahmenFaelligkeitsdatum.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblEinnahmenFaelligkeitsdatum.setBounds(21, 189, 142, 29);
		        Panel_Einnahmen.add(lblEinnahmenFaelligkeitsdatum);
		        
		        
		        txtTtmmjjjj_Einnahmen = new JTextField();
		        txtTtmmjjjj_Einnahmen.setBounds(197, 190, 150, 26);       
		        txtTtmmjjjj_Einnahmen.setText( new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
		        txtTtmmjjjj_Einnahmen.setFont(new Font("Tahoma", Font.BOLD, 11));
		        Panel_Einnahmen.add(txtTtmmjjjj_Einnahmen);
		        
		        btnEinnahmenBuchen = new JButton("Einnahme Buchen!");
		        btnEinnahmenBuchen.setBounds(421, 311, 142, 29);
		        Panel_Einnahmen.add(btnEinnahmenBuchen);
		        
		        lblNotiz_Einnahmen = new JLabel("Beschreibung:");
		        lblNotiz_Einnahmen.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblNotiz_Einnahmen.setBounds(21, 250, 142, 29);
		        Panel_Einnahmen.add(lblNotiz_Einnahmen);
		        
		        textFieldNotiz_Einnahmen = new JTextField();
		        textFieldNotiz_Einnahmen.setColumns(10);
		        textFieldNotiz_Einnahmen.setBounds(197, 252, 150, 25);
		        Panel_Einnahmen.add(textFieldNotiz_Einnahmen);
		        
		        lblEuro = new JLabel(" Euro ( z.B. 5.55)");
		        lblEuro.setForeground(new Color(128, 128, 128));
		        lblEuro.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblEuro.setBounds(357, 18, 99, 14);
		        Panel_Einnahmen.add(lblEuro);
		        
		        lblTtmmyyyy = new JLabel("TT.MM.JJJJ");
		        lblTtmmyyyy.setForeground(new Color(128, 128, 128));
		        lblTtmmyyyy.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblTtmmyyyy.setBounds(357, 196, 67, 14);
		        Panel_Einnahmen.add(lblTtmmyyyy);
		        
		        btnReset_Einnahmen = new JButton("Eingaben reseten");
				  btnReset_Einnahmen.setBounds(21, 311, 135, 29);
				  Panel_Einnahmen.add(btnReset_Einnahmen);
				  btnHelpButton_Einnahmen = new JButton("Hilfe");
				  btnHelpButton_Einnahmen.addActionListener(new ActionListener() {
				  	public void actionPerformed(ActionEvent e) {
				  		
							JOptionPane.showMessageDialog(null, hilfe_e
									, "Hilfe", JOptionPane.INFORMATION_MESSAGE); 
						
				  	}
				  });
				  btnHelpButton_Einnahmen.setBounds(585, 0, 67, 20);
				  Panel_Einnahmen.add(btnHelpButton_Einnahmen);
				
			//ANFANG PANEL 5 Daueraufträge 
				  //				  
				  //				  
			//ENDE PANEL 5 Daueraufträge
				  
				  
			//ANFANG PANEL 6 Statistiken  
				  
		rdbtnGesamtzeitraum = new JRadioButton("Gesamtzeitraum");
		rdbtnGesamtzeitraum.setBounds(60, 81, 150, 23);
		Panel_Statistiken.add(rdbtnGesamtzeitraum);
		rdbtnGesamtzeitraum.setSelected(true);

		rdbtnIndividualZeitraum = new JRadioButton("individueller Zeitraum");
		rdbtnIndividualZeitraum.setBounds(249, 81, 148, 23);
		Panel_Statistiken.add(rdbtnIndividualZeitraum);

		textField_Statistik_Startwert = new JTextField();
		textField_Statistik_Startwert.setBounds(403, 58, 86, 20);
		Panel_Statistiken.add(textField_Statistik_Startwert);
		textField_Statistik_Startwert.setEnabled(false);

		textField_Statistik_Endwert = new JTextField();
		textField_Statistik_Endwert.setBounds(403, 101, 86, 20);
		Panel_Statistiken.add(textField_Statistik_Endwert);
		textField_Statistik_Endwert.setEnabled(false);

		lblAnfangsdatum = new JLabel("Anfangsdatum");
		lblAnfangsdatum.setBounds(498, 61, 113, 14);
		Panel_Statistiken.add(lblAnfangsdatum);

		lblEnddatum = new JLabel("Enddatum");
		lblEnddatum.setBounds(499, 104, 86, 14);
		Panel_Statistiken.add(lblEnddatum);

		btnStatistikanzeigen = new JButton("Statistik anzeigen");
		btnStatistikanzeigen.setBounds(249, 247, 159, 45);
		Panel_Statistiken.add(btnStatistikanzeigen);

		comboBoxStatistikModelle = new JComboBox();
		comboBoxStatistikModelle.setBounds(128, 179, 407, 23);
		comboBoxStatistikModelle
				.setModel(new DefaultComboBoxModel(
						new String[] {
								"Einnahmen vs Ausgaben (Kategorie) [Kreisdiagramm]",
								"Einnahmen vs Ausgaben (Kategorie) [Balkendiagramm]",
								"Einnahmen & Ausgaben (Kategorie) [Balkendiagramm]",
								"Einnahmen (Zeit: Buchungsdatum) [Balken- und Kreisdiagramm]",
								"Ausgaben (Zeit: Buchungsdatum) [Balken- und Kreisdiagramm]",
								"Einnahmen vs Ausgaben (Gesamt) [Balkendiagramm]",
								"Einnahmen vs Ausgaben (Zeit: monatlich) [Liniendiagramm]",
								"Einnahmen vs Ausgaben: Differenz (Zeit: Buchungsdatum) [Liniendiagramm]",
								"Einnahmen vs Ausgaben (Kategorie) [Wasserfalldiagramm]" }));
		Panel_Statistiken.add(comboBoxStatistikModelle);
		
		lblStatistikWarnung = new JLabel("");
		lblStatistikWarnung.setBounds(473, 261, 155, 87);
		Panel_Statistiken.add(lblStatistikWarnung);
  
	        //ENDE PANEL 6 Statistiken
					
	       //Anfang Panel 7 Sparfunktion
	       //
		   //		
	       //ENDE PANEL 7 Sparfunktion
		
	}	
		
		//Verhalten hinzufuegen
	/**
	 * 
	 * 
	 * 
	 * 
	 * Im Verhalten werden die obigen Methoden angewand
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	public void addBehavior() {
		Anfangsabfrage();
		Init_Kontostand();

		btnKontoDrucken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (!table.print()) {
						System.err.println("User cancelled printing");
					}
				} catch (java.awt.print.PrinterException e) {
					System.err.format("Cannot print %s%n", e.getMessage());
				}
			}
		});

		btnReset_Ausgaben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Clear_Ausgaben();
			}
		});

		btnAusgabenBuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Checkdata_Ausgaben();
				if ((comboBox_Ausgaben.isEnabled() == false)
						&& (budget.Geldvermögen.size() != 0)) {
					Aktivierung();
				}
			}
		});

		btnReset_Einnahmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Clear_Einnahmen();
			}
		});

		btnEinnahmenBuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CheckData_Einnahmen();
				if ((comboBox_Ausgaben.isEnabled() == false)
						&& (budget.Geldvermögen.size() != 0)) {
					Aktivierung();
				}
			}
		});

		rdbtnGesamtzeitraum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnIndividualZeitraum.isSelected() == true) {
					rdbtnGesamtzeitraum.setSelected(true);
					rdbtnIndividualZeitraum.setSelected(false);
					textField_Statistik_Startwert.setEnabled(false);
					textField_Statistik_Startwert.setText(null);
					textField_Statistik_Endwert.setEnabled(false);
					textField_Statistik_Endwert.setText(null);
				} else
					rdbtnGesamtzeitraum.setSelected(true);
			}
		});

		rdbtnIndividualZeitraum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGesamtzeitraum.isSelected() == true) {
					rdbtnIndividualZeitraum.setSelected(true);
					textField_Statistik_Startwert.setEnabled(true);
					textField_Statistik_Endwert.setEnabled(true);
					rdbtnGesamtzeitraum.setSelected(false);
				} else
					rdbtnIndividualZeitraum.setSelected(true);
			}
		});

		btnStatistikanzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (comboBoxStatistikModelle.getSelectedIndex()) {
				case 0:
					Grafikmodellauswahl("Kategorie_Kreisdiagramm");
					break;
				case 1:
					Grafikmodellauswahl("Kategorie_Balkendiagramm");
					break;
				case 2:
					Grafikmodellauswahl("GesamtKategorie_Balkendiagramm");
					break;
				case 3:
					Grafikmodellauswahl("Zeit_Kombidiagramm_Einnahmen");
					break;
				case 4:
					Grafikmodellauswahl("Zeit_Kombidiagramm_Ausgaben");
					break;
				case 5:
					Grafikmodellauswahl("Vergleich_Balkendiagramm");
					break;
				case 6:
					Grafikmodellauswahl("Zeit_Liniendiagramm_Gesamt");
					break;
				case 7:
					Grafikmodellauswahl("Zeit_Liniendiagramm_Differenz_Gesamt");
					break;
				case 8:
					Grafikmodellauswahl("Kategorie_Wasserfalldiagramm");
					break;
				}
			}
		});

		btnSchließen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				System.exit(0);
			}
		});

		btnKontoLöschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showConfirmDialog(null, konto_l,
						"Konto löschen", JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					comboBox_Ausgaben.removeItem("Kontoeröffnung");
					comboBox_Einnahmen.removeItem("Kontoeröffnung");

					CSVWriter writer = null;
					try {
						writer = new CSVWriter(
								new FileWriter("data/budget.csv"));
						writer.writeNext(null);
						writer.close();

					} catch (FileNotFoundException ex) {
						System.err.println("csv_nichtgefunden");
						System.exit(1);
					} catch (IOException ex) {
						System.err.println("csv_problemöffnen");
						System.exit(1);
					}

					budget.Geldvermögen.clear();
					Init_Kontostand();
					Anfangsabfrage();
				}
			}
		});

		btnTaschenrechner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Runtime.getRuntime().exec("calc.exe");
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, taschenrechner,
							"ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, info, "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}
}

