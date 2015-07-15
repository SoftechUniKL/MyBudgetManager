import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * Graphische Benutzeroberflaeche des BudgetPlaners
 */
public class BudgetPlanGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Modell der Daten
	 */
	private BudgetPlanModel budget;
	// panel Headline:

	private JPanel panel;
	private JLabel lblKontostandsanzeige, lblKontostand, lblDatum;
	private JLabel lblKontostandWarnung;

	// panel_1:
	private JPanel panel_1;
	private GroupLayout gl_panel_1;
	private JButton btnKontoLöschen, btnInfo, btnTaschenrechner, btnSchließen;

	// TABBED PANE:
	private JTabbedPane tabbedPane;

	// panel_Konto:
	private JPanel panel_Konto;
	private JPanel panel_Kontouebersicht;
	private JLabel lblKontouebersicht;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton btnKontoDrucken;

	// panel_Ausgaben:
	private JPanel Panel_Ausgaben;
	private JTextField textField_Ausgaben, txtTtmmjjjj_Ausgaben,
			textFieldNotiz_Ausgabe;
	private JLabel lblAusgabenBuchungsbetrag, lblAusgabenKategorie,
			lblAusgabenWiederholung, lblAusgabenFaelligkeitsdatum,
			lblNotiz_Ausgaben, lblEuroZb, label_Datum;
	private JButton btnAusgabenBuchen, btnReset_Ausgaben;
	private JRadioButton rdbtnEinmaligAusgaben, rdbtnMonatlichAusgaben;
	private JComboBox<String> comboBox_Ausgaben;
	private JButton btnHelpButton_Ausgaben;
	private JTextArea textAreaAusgabenHilfe;
	private JScrollPane scrollPaneAusgabenHilfe;
	
	// panel_Einnahmen:
	private JPanel Panel_Einnahmen;
	private JLabel lblEinnahmenBuchungsbetrag, lblEinnahmenKategorie,
			lblEinnahmenWiederholung, lblEinnahmenFaelligkeitsdatum,
			lblNotiz_Einnahmen, lblEuro, lblTtmmyyyy;
	private JButton btnEinnahmenBuchen, btnReset_Einnahmen;
	private JRadioButton rdbtnEinnahmenEinmalig, rdbtnEinnahmenMonatlich;
	private JTextField txtTtmmjjjj_Einnahmen, textField_Einnahmen,
			textFieldNotiz_Einnahmen;
	private JComboBox<String> comboBox_Einnahmen;
	private JButton btnHelpButton_Einnahmen;
	private JTextArea textAreaEinnahmenHilfe;
	private JScrollPane scrollPaneEinnahmenHilfe;
	
	// panel_Daueraufträge:
	private JPanel panel_Dauerauftraege;

	// panel_Statistiken
	private JPanel Panel_Statistiken;
	private JRadioButton rdbtnGesamtzeitraum;
	private JRadioButton rdbtnIndividualZeitraum;
	private JTextField textField_Statistik_Startwert;
	private JTextField textField_Statistik_Endwert;
	private JLabel lblAnfangsdatum;
	private JLabel lblStatistikWarnung;
	private JLabel lblEnddatum;
	private JComboBox<String> comboBoxStatistikModelle;
	private JButton btnStatistikanzeigen;
	private JButton btnStatistikHilfe;
	private Date Start;
	private Date End;
	private SimpleDateFormat sdf;
	private DateFormat dateFormat;
	private Pattern p;
	private Matcher m;
	private Matcher m2;
	private SimpleDateFormat monatformat;
	private JLabel lblStatistikmodell;
	private JTextArea textAreaStatHilfe;
	private JScrollPane scrollPaneStatHilfe;
	private Statistik Statistikauswahl;
	

	// panel_Sparfunktion
	private JPanel panel_Sparfunktion;
	private JLabel lblsparrate, lblnachSparmaßnahme, lbl_prozent, lblStartwert;
	private JTextField textFieldsparrate, textFieldZeitraum,
			textFieldStartwert, textFieldZinsen;
	private JRadioButton rdbtnMonatlichSparfkt, rdbtnJaehrlichSparfkt,
			rdbtnEinmaligSparfkt, rdbtnZinsenAnAus;
	private JButton btnBerechne;
	private JComboBox<String> comboBoxZinsperiode;

	// Fehlermeldungen als vorgeschriebene Strings
	/**
	 * Nachricht bei einer falschen Betragseingabe
	 */
	String falsche_betragseingabe = "Falsche Betragseingabe. \n"
			+ "Formathinweis: \n"
			+ "- Bitte verwenden Sie nur Zahlen \n"
			+ "- Benutzen Sie bitte Punkt statt Komma \n"
			+ "- Achten Sie bitte auf die evtl. fehlenden zwei Nachkommastellen \n"
			+ "- Benutzen Sie bitte kein Negativzeichen";
	/**
	 * Nachricht bei fehlender csv Datei
	 */
	String csv_nichtgefunden = "Die Datei data/budget.csv wurde nicht gefunden!";
	/**
	 * Nachrichtentext, wenn die csv Datein probleme hat
	 */
	String csv_problemöffnen = "Probleme beim Oeffnen der Datei data/budget.csv!";
	/**
	 * Nachrichtentext bei falscher Datumsanzeige
	 */
	String falsches_datum = "Falsche Datumseingabe. \n"
			+ "Formathinweis: TT.MM.JJJJ";

	String falsche_datumsgroesse = "Falsche Datumsgrößen. \n"
			+ "Enddatum muss größer/gleich dem Anfangsdatum sein!";
	/**
	 * Nachricht beim Nichtauswählen der Kategorie
	 */
	String kategorie = "Bitte wählen sie eine Kategorie aus.";
	/**
	 * Nachrichtentext für den Taschenrechner
	 */
	String taschenrechner = "Taschenrechner konnte nicht aufgerufen worden.";

	// Hilfe Meldungen als vorgeschriebene Strings
	/**
	 * Nachrichtentext für den Hilfebutton im Panel Ausgaben
	 */
	String hilfe_a = "Hilfe \n \n"
			+ "- Bei den Ausgaben ist es Ganz wichtig den Betrag in folgender Form anzugeben: \n  \n"
			+ "               X.XX Euro \n \n"
			+ " Beachten Sie hierbei, dass ein Punkt zwischen dem Euro- und Centbetrag gesetzt werden muss.\n"
			+ " Ansonsten kann es zu Fehlern kommen. \n \n"
			+ "- Wählen Sie immer eine Kategorie.  \n\n"
			+ "- Durch die Wahl eines Dauerauftrags werden die Aufträge nicht nur einmal sondern monatlich gebucht.\n "
			+ " Dadurch müssen sie regelmäßig anfallende Ausgaben nicht jeden Monat manuel eingeben. \n\n"
			+ "- In der Beschreibung können Angaben zur eigenen Buchung gemacht werden. \n  "
			+ " Dabei steht Ihnen frei, wie die Eingabe aussieht oder ob sie überhaupt gemacht wird. \n"
			+ "	 \n"
			+ "ShortKeys: \n"
			+ "- Buchen <alt + Enter\n"
			+ "- Reset <alt + R>\n"
			+ "- Taschenrechner <alt + T>\n"
			+ "- Hilfebutton <alt + H> \n";
	
	/**
	 * Nachrichtentext für den Hilfebutton im Panel Einnahmen
	 */
	String hilfe_e = "Hilfe \n \n"
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
	String hilfe_stat = "\nHier können Sie sich die entsprechende Statistik zu Ihren Ausgaben und Einnahmen \n"
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
	 * Willkommensnachricht, die sich öffnet, wenn ein neues Konto geöffnet wird
	 */
	String willkommen = "Willkommen bei MyBudgetManager! \n"
			+ "Um das Programm anwenden zu können, legen Sie einen Startwert Ihres Kontos fest. \n"
			+ "Befindet sich dieser im positiven Breich, tätigen Sie ihre Eröffnungsbuchung unter dem Menüunterpunkt: Einnahmen. \n"
			+ "Anderenfalls gehen Sie auf Ausgaben.  \n" + "\n"
			+ "Wir wünschen Ihnen viel Freunde an unserem tollen Produkt\n"
			+ "";

	// Dialoge als vorgeschriebene Strings
	/**
	 * Nachricht bei Ausgabenbuchung
	 */
	String buchung_a = "Ausgabe erfolgreich gebucht.";
	/**
	 * Nachricht bei der Einnahmenbuchung
	 */
	String buchung_e = "Einnahme erfolgreich gebucht.";
	/**
	 * Nachricht beim Kontolöschen
	 */
	String konto_l = "Möchten Sie wirklich ihr Konto löschen? \n"
			+ "Sämtliche Daten gehen dabei verloren!";
	/**
	 * Nachricht, ob das Programm wirklich geschlossen werden soll
	 */
	String prog_close = "Möchten Sie das Programm wirklich schließen?";
	/**
	 * Nachricht beim Klicken des Infobuttons
	 */
	String info = "MyBudgetManager (Version 1.1.0 beta) 2015 \n \n"
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
	 */
	public BudgetPlanGUI(BudgetPlanModel budget) {
		// Name im Fenster
		super("MyBudgetManager");
		setBackground(new Color(135, 206, 235));
		// Setzt das Icon des Programms
		try {
			setIconImage(getToolkit().getImage("src/img/Money.png"));
		} catch (Exception whoJackedMyIcon) {
			System.out.println("Could not load program icon.");
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 450, 300); // Groesse des Frames
		setVisible(true); // Frame wird sichtbar
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setBackground(Color.WHITE);// Gesamter Hintergrund auf
													// Weiß
		setMinimumSize(new Dimension(800, 550));
		setLocationRelativeTo(null);

		this.budget = budget;
		initWindow(); // Initialisierung des Frameinhalts
		addBehavior(); // Verhalten der GUI Elemente dieses Frames

	}
	
	//
	// Methode für die Haupt-GUI
	//
	
	/**
	 * Das Ausgangs Fenster wird hier initalisiert
	 */
	protected void initWindow() {

		panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(0, 80));
		panel.setLayout(null);

		// ///////////////////////////
		// Panel 1 Anfang

		//
		// Panel in dem der Kontostand angezeigt und aktualisiert wird
		// Ebenfalls wird hier das Datum und die Fehlermeldung bei gefährlichen
		// Kontoständen gezeigt
		//

		lblKontostandsanzeige = new JLabel("Kontostand in [\u20ac]:");
		lblKontostandsanzeige.setForeground(Color.BLUE);
		lblKontostandsanzeige.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKontostandsanzeige.setBounds(340, 0, 156, 34);
		panel.add(lblKontostandsanzeige);
		// Kontostand
		lblKontostand = new JLabel("0");
		lblKontostand.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblKontostand.setBounds(170, 35, 470, 35);
		lblKontostand.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblKontostand);
		// Datum wird hier angezeigt
		lblDatum = new JLabel("Datum: "
				+ new SimpleDateFormat("E, dd.MM.yyyy").format(new Date()));
		lblDatum.setBounds(10, 49, 142, 14);
		// lblDatum.setOpaque(false);
		panel.repaint();
		panel.add(lblDatum);

		// Kontostandswarnung
		lblKontostandWarnung = new JLabel("");
		lblKontostandWarnung.setBounds(588, 11, 156, 59);
		panel.add(lblKontostandWarnung);

		//
		// Panel für externe Button
		//
		//
		// Konto Löschen / Taschenrechner / Schließen / Info
		//

		// Unterster Panel, indem einige Zusatzfunktionen und Informationen zu
		// finden sind
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setPreferredSize(new Dimension(0, 50));

		// Info Button über dem man Informationen zum Programm bekommt
		btnInfo = new JButton("Info", new ImageIcon("src/img/info.png"));

		// Taschenrechner mit dem man schnell selbst kleinigkeiten ausrechnen
		// kann
		btnTaschenrechner = new JButton("Taschenrechner", new ImageIcon(
				"src/img/calculator.png"));
		btnTaschenrechner.setMnemonic(KeyEvent.VK_T);
		
		// Button über den das Programm geschlossen werden kann
		btnSchließen = new JButton("Schlie\u00DFen", new ImageIcon(
				"src/img/close.png"));
		// Button über den das aktuelle Konto des Programm gelöscht werden kann
		btnKontoLöschen = new JButton(
				"<html><body>Konto<br>löschen</body></html>", new ImageIcon(
						"src/img/trash.png"));

		gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnKontoLöschen,
								GroupLayout.PREFERRED_SIZE, 109,
								GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addComponent(btnTaschenrechner)
						.addPreferredGap(ComponentPlacement.RELATED, 351,
								Short.MAX_VALUE).addComponent(btnSchließen)
						.addGap(27).addComponent(btnInfo).addGap(20)));
		gl_panel_1
				.setVerticalGroup(gl_panel_1
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												gl_panel_1
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(btnInfo)
														.addComponent(
																btnSchließen)
														.addComponent(
																btnTaschenrechner,
																GroupLayout.PREFERRED_SIZE,
																35,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap())
						.addGroup(
								Alignment.LEADING,
								gl_panel_1
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(btnKontoLöschen,
												GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		//
		// Panel über den das Hauptmenü gezeigt wird
		// Dieses Panel besteht aus einem JTabbedPane. So kann zwischen
		// einzelnen Panels gewählt werden.
		//

		// Hier wird ein TabbedPane hinzugefügt. Über verschiede Blätter können
		// die einzelnen Panel erreicht werden
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBackground(new Color(135, 206, 235));
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// Panel Konto
		panel_Konto = new JPanel();
		tabbedPane
				.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Konto</body></html>",
						null, panel_Konto, null);
		panel_Konto.setLayout(new BorderLayout(0, 0));
		// Panel Einnahmen
		Panel_Einnahmen = new JPanel();
		tabbedPane
				.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Einnahmen</body></html>",
						null, Panel_Einnahmen, null);
		Panel_Einnahmen.setLayout(null);

		// Panel Ausgaben
		Panel_Ausgaben = new JPanel();
		tabbedPane
				.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Ausgaben</body></html>",
						null, Panel_Ausgaben, null);
		Panel_Ausgaben.setLayout(null);

		// Panel Daueraufträge
		panel_Dauerauftraege = new JPanel();
		tabbedPane
				.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Daueraufträge</body></html>",
						null, panel_Dauerauftraege, null);
		panel_Dauerauftraege.setLayout(null);

		// Panel Statistik
		Panel_Statistiken = new JPanel();
		tabbedPane
				.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Statistik</body></html>",
						null, Panel_Statistiken, null);
		Panel_Statistiken.setLayout(null);

		// Panel Sparfunktion
		panel_Sparfunktion = new JPanel();
		tabbedPane
				.addTab("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=20>Sparfunktion</body></html>",
						null, panel_Sparfunktion, null);
		panel_Sparfunktion.setLayout(null);

		// Panel 1 Ende
		//
		// Panel 2 Kontoübersicht Anfang
		//

		// Tabelle mit Uebersicht der Ausgaben und Einnahmen
		panel_Kontouebersicht = new JPanel();
		panel_Kontouebersicht.setBackground(new Color(135, 206, 235));
		panel_Konto.add(panel_Kontouebersicht, BorderLayout.NORTH);
		panel_Kontouebersicht.setPreferredSize(new Dimension(0, 40));

		// Label
		lblKontouebersicht = new JLabel(
				"Konto\u00FCbersicht:                                                                    ");
		panel_Kontouebersicht.add(lblKontouebersicht);
		lblKontouebersicht.setFont(new Font("Tahoma", Font.BOLD, 18));

		// Button über den das Konto gedruckt werden kann
		btnKontoDrucken = new JButton("Drucken", new ImageIcon(
				"src/img/printer.png"));
		panel_Kontouebersicht.add(btnKontoDrucken);

		// ScrollPane über den in der Tabelle gescrolled werden kann
		scrollPane = new JScrollPane();
		panel_Konto.add(scrollPane, BorderLayout.CENTER);

		// Panel 2 Kontoübersicht Ende

		// Panel 3 Anfang
		//
		// Panel 3 in dem die Ausgaben erfasst werden
		//

		// Textfeld in das die Ausgaben vom Benutzer eingegeben werden können
		textField_Ausgaben = new JTextField();
		textField_Ausgaben.setBounds(197, 13, 150, 25);
		Panel_Ausgaben.add(textField_Ausgaben);
		textField_Ausgaben.setHorizontalAlignment(JTextField.RIGHT);
		// Label
		lblAusgabenBuchungsbetrag = new JLabel("Buchungsbetrag:");
		lblAusgabenBuchungsbetrag.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAusgabenBuchungsbetrag.setBounds(21, 11, 142, 29);
		Panel_Ausgaben.add(lblAusgabenBuchungsbetrag);

		// Label
		lblAusgabenKategorie = new JLabel("Kategorie:");
		lblAusgabenKategorie.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAusgabenKategorie.setBounds(21, 67, 142, 29);
		Panel_Ausgaben.add(lblAusgabenKategorie);

		// comboBox in der die Kategorien gewählt werden können
		comboBox_Ausgaben = new JComboBox<String>();
		comboBox_Ausgaben.setModel(new DefaultComboBoxModel<String>(
				new String[] { "(Bitte Wählen)", "Krankenversicherung",
						"Hausversicherung", "Kfz-Versicherung",
						"TÜV/Versicherung", "Benzin/Diesel", "Musik",
						"Filmleihe", "Kino", "Bücher/Zeitschriften",
						"Kinder/Spielzeug", "Persönliche Gegenstände",
						"Bekleidung", "Restaurant/Mensa", "Lebensmittel",
						"Müll", "Wasser", "Einkäufe", "Ersparnisse",
						"Haustiere", "Party", "Haushalt", "Hotel/Unterkunft",
						"Kreditkarte", "Gesundheitsausgaben",
						"Krankenversicherung", "Internet/Telefon", "Handy",
						"Gas/Heizung", "Strom", "Vermögenssteuer",
						"Mitgliedsgebür", "Miete", "Kredit", "Sonstiges" }));
		comboBox_Ausgaben.setBounds(197, 69, 155, 25);
		Panel_Ausgaben.add(comboBox_Ausgaben);
		// Label
		lblAusgabenWiederholung = new JLabel("Wiederholung:");
		lblAusgabenWiederholung.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAusgabenWiederholung.setBounds(21, 130, 142, 29);
		Panel_Ausgaben.add(lblAusgabenWiederholung);

		// RadioButton zu Wiederholungswahl
		rdbtnEinmaligAusgaben = new JRadioButton("einmalig");
		rdbtnEinmaligAusgaben.setBounds(197, 133, 109, 23);
		rdbtnEinmaligAusgaben.setSelected(true);
		Panel_Ausgaben.add(rdbtnEinmaligAusgaben);

		// RadioButton zu Wiederholungswahl
		rdbtnMonatlichAusgaben = new JRadioButton("monatlich\r\n");
		rdbtnMonatlichAusgaben.setBounds(308, 133, 109, 23);
		rdbtnMonatlichAusgaben.setEnabled(false);
		Panel_Ausgaben.add(rdbtnMonatlichAusgaben);

		// Label
		lblAusgabenFaelligkeitsdatum = new JLabel("F\u00E4lligkeitsdatum:");
		lblAusgabenFaelligkeitsdatum.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAusgabenFaelligkeitsdatum.setBounds(21, 189, 142, 29);
		Panel_Ausgaben.add(lblAusgabenFaelligkeitsdatum);

		// TextField in dem das Datum angegeben werden kann
		txtTtmmjjjj_Ausgaben = new JTextField();
		txtTtmmjjjj_Ausgaben.setBounds(197, 190, 150, 26);
		txtTtmmjjjj_Ausgaben.setText(new SimpleDateFormat("dd.MM.yyyy")
				.format(new Date()));
		txtTtmmjjjj_Ausgaben.setFont(new Font("Tahoma", Font.BOLD, 11));
		Panel_Ausgaben.add(txtTtmmjjjj_Ausgaben);

		// Button über den die Buchung ausgeführt werden kann
		btnAusgabenBuchen = new JButton("Buchen", new ImageIcon(
				"src/img/account.png"));
		btnAusgabenBuchen.setMnemonic(KeyEvent.VK_ENTER);
		btnAusgabenBuchen.setBounds(421, 311, 150, 29);
		Panel_Ausgaben.add(btnAusgabenBuchen);

		// TextField über das eine Notiz zur Ausgabe hinzugeführt werden kann
		textFieldNotiz_Ausgabe = new JTextField();
		textFieldNotiz_Ausgabe.setBounds(197, 252, 150, 25);
		Panel_Ausgaben.add(textFieldNotiz_Ausgabe);

		// Label
		lblNotiz_Ausgaben = new JLabel("Beschreibung:");
		lblNotiz_Ausgaben.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNotiz_Ausgaben.setBounds(21, 250, 142, 29);
		Panel_Ausgaben.add(lblNotiz_Ausgaben);

		// Label
		lblEuroZb = new JLabel(" Euro ( z.B. 5.55)");
		lblEuroZb.setForeground(new Color(128, 128, 128));
		lblEuroZb.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEuroZb.setBounds(357, 18, 99, 14);
		Panel_Ausgaben.add(lblEuroZb);

		// Label
		label_Datum = new JLabel("TT.MM.JJJJ");
		label_Datum.setForeground(new Color(128, 128, 128));
		label_Datum.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_Datum.setBounds(357, 196, 67, 14);
		Panel_Ausgaben.add(label_Datum);

		// Reset Button über den die Eingabe alle zurückgesetzt werden können
		btnReset_Ausgaben = new JButton("Reset", new ImageIcon(
				"src/img/reset.png"));
		btnReset_Ausgaben.setMnemonic(KeyEvent.VK_R);
		btnReset_Ausgaben.setBounds(21, 311, 150, 29);
		Panel_Ausgaben.add(btnReset_Ausgaben);

		// HilfeButton über den der Nutzer Hilfe für die richtige Eingabe
		// bekommt
		btnHelpButton_Ausgaben = new JButton("", new ImageIcon(
				"src/img/help.png"));
		btnHelpButton_Ausgaben.setMnemonic(KeyEvent.VK_H);
		btnHelpButton_Ausgaben.setBounds(570, 10, 60, 50);
		Panel_Ausgaben.add(btnHelpButton_Ausgaben);
		// Panel 3 Ende

		// Panel 4 Einnahmen Anfang
		//
		// Panel in dem die Einnahmen erfasst werden
		//

		// Textfeld in das die Einnahmen vom Benutzer eingegeben werden können
		textField_Einnahmen = new JTextField();
		textField_Einnahmen.setBounds(197, 13, 150, 25);
		Panel_Einnahmen.add(textField_Einnahmen);
		textField_Einnahmen.setHorizontalAlignment(JTextField.RIGHT);

		// Label
		lblEinnahmenBuchungsbetrag = new JLabel("Buchungsbetrag:");
		lblEinnahmenBuchungsbetrag.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEinnahmenBuchungsbetrag.setBounds(21, 11, 142, 29);
		Panel_Einnahmen.add(lblEinnahmenBuchungsbetrag);

		// Label
		lblEinnahmenKategorie = new JLabel("Kategorie:");
		lblEinnahmenKategorie.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEinnahmenKategorie.setBounds(21, 67, 142, 29);
		Panel_Einnahmen.add(lblEinnahmenKategorie);

		// comboBox in der die Kategorien gewählt werden können
		comboBox_Einnahmen = new JComboBox<String>();
		comboBox_Einnahmen.setModel(new DefaultComboBoxModel<String>(
				new String[] { "(Bitte Wählen)", "Lohn/Gehalt", "Taschengeld",
						"Erbe", "Mieteinnahmen", "Kredite", "Verkauf",
						"Zinsen", "Mitgliedsbeitrag", "Spende", "Sonstiges" }));
		comboBox_Einnahmen.setBounds(197, 69, 155, 25);
		Panel_Einnahmen.add(comboBox_Einnahmen);

		// Label
		lblEinnahmenWiederholung = new JLabel("Wiederholung:");
		lblEinnahmenWiederholung.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEinnahmenWiederholung.setBounds(21, 130, 142, 29);
		Panel_Einnahmen.add(lblEinnahmenWiederholung);

		// RadioButton zu Wiederholungswahl
		rdbtnEinnahmenEinmalig = new JRadioButton("einmalig");
		rdbtnEinnahmenEinmalig.setBounds(197, 133, 109, 23);
		rdbtnEinnahmenEinmalig.setSelected(true);
		Panel_Einnahmen.add(rdbtnEinnahmenEinmalig);

		// RadioButton zu Wiederholungswahl
		rdbtnEinnahmenMonatlich = new JRadioButton("monatlich\r\n");
		rdbtnEinnahmenMonatlich.setBounds(308, 133, 109, 23);
		rdbtnEinnahmenMonatlich.setEnabled(false);
		Panel_Einnahmen.add(rdbtnEinnahmenMonatlich);

		// Label
		lblEinnahmenFaelligkeitsdatum = new JLabel("F\u00E4lligkeitsdatum:");
		lblEinnahmenFaelligkeitsdatum
				.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEinnahmenFaelligkeitsdatum.setBounds(21, 189, 142, 29);
		Panel_Einnahmen.add(lblEinnahmenFaelligkeitsdatum);

		// TextField in dem das Datum angegeben werden kann
		txtTtmmjjjj_Einnahmen = new JTextField();
		txtTtmmjjjj_Einnahmen.setBounds(197, 190, 150, 26);
		txtTtmmjjjj_Einnahmen.setText(new SimpleDateFormat("dd.MM.yyyy")
				.format(new Date()));
		txtTtmmjjjj_Einnahmen.setFont(new Font("Tahoma", Font.BOLD, 11));
		Panel_Einnahmen.add(txtTtmmjjjj_Einnahmen);

		// Button über den die Buchung ausgeführt werden kann
		btnEinnahmenBuchen = new JButton("Buchen", new ImageIcon(
				"src/img/account.png"));
		btnEinnahmenBuchen.setMnemonic(KeyEvent.VK_ENTER);
		btnEinnahmenBuchen.setBounds(421, 311, 150, 29);
		Panel_Einnahmen.add(btnEinnahmenBuchen);

		// Label
		lblNotiz_Einnahmen = new JLabel("Beschreibung:");
		lblNotiz_Einnahmen.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNotiz_Einnahmen.setBounds(21, 250, 142, 29);
		Panel_Einnahmen.add(lblNotiz_Einnahmen);

		// TextField über das eine Notiz zur Ausgabe hinzugeführt werden kann
		textFieldNotiz_Einnahmen = new JTextField();
		textFieldNotiz_Einnahmen.setColumns(10);
		textFieldNotiz_Einnahmen.setBounds(197, 252, 150, 25);
		Panel_Einnahmen.add(textFieldNotiz_Einnahmen);

		// Label
		lblEuro = new JLabel(" Euro ( z.B. 5.55)");
		lblEuro.setForeground(new Color(128, 128, 128));
		lblEuro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEuro.setBounds(357, 18, 99, 14);
		Panel_Einnahmen.add(lblEuro);

		// Label
		lblTtmmyyyy = new JLabel("TT.MM.JJJJ");
		lblTtmmyyyy.setForeground(new Color(128, 128, 128));
		lblTtmmyyyy.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTtmmyyyy.setBounds(357, 196, 67, 14);
		Panel_Einnahmen.add(lblTtmmyyyy);

		// Reset Button über den die Eingabe alle zurückgesetzt werden können
		btnReset_Einnahmen = new JButton("Reset", new ImageIcon(
				"src/img/reset.png"));
		btnReset_Einnahmen.setMnemonic(KeyEvent.VK_R);
		btnReset_Einnahmen.setBounds(21, 311, 150, 29);
		Panel_Einnahmen.add(btnReset_Einnahmen);

		// HilfeButton über den der Nutzer Hilfe für die richtige Eingabe
		// bekommt
		btnHelpButton_Einnahmen = new JButton("", new ImageIcon(
				"src/img/help.png"));
		btnHelpButton_Einnahmen.setMnemonic(KeyEvent.VK_H);
		btnHelpButton_Einnahmen.setBounds(570, 10, 60, 50);
		Panel_Einnahmen.add(btnHelpButton_Einnahmen);

		// ANFANG PANEL 5 Daueraufträge

		// Label und Nachricht für die Daueraufträge
		JLabel labelDA = new JLabel(new ImageIcon("src/img/work.png"));
		labelDA.setBounds(232, 123, 207, 138);
		panel_Dauerauftraege.add(labelDA);

		JLabel labelDA2 = new JLabel(
				"<html><body>We're sorry!<br>This feature is temporarily unavailable.<br>Please wait for next Update.</body></html>");
		labelDA2.setFont(new Font("Tahoma", Font.BOLD, 18));
		labelDA2.setBounds(150, 30, 550, 100);
		panel_Dauerauftraege.add(labelDA2);

		// ENDE PANEL 5 Daueraufträge

		// ANFANG PANEL 6 Statistiken

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

		btnStatistikanzeigen = new JButton("Statistik anzeigen", new ImageIcon(
				"src/img/statistik.png"));
		btnStatistikanzeigen.setMnemonic(KeyEvent.VK_ENTER);
		btnStatistikanzeigen.setBounds(222, 247, 186, 45);
		Panel_Statistiken.add(btnStatistikanzeigen);

		comboBoxStatistikModelle = new JComboBox<String>();
		comboBoxStatistikModelle.setBounds(100, 180, 459, 23);
		comboBoxStatistikModelle
				.setModel(new DefaultComboBoxModel<String>(
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
		lblStatistikWarnung.setBounds(468, 247, 155, 87);
		Panel_Statistiken.add(lblStatistikWarnung);

		lblStatistikmodell = new JLabel("Statistik-Modell:");
		lblStatistikmodell.setBounds(100, 155, 113, 20);
		Panel_Statistiken.add(lblStatistikmodell);

		btnStatistikHilfe = new JButton("", new ImageIcon("src/img/help.png"));
		btnStatistikHilfe.setMnemonic(KeyEvent.VK_H);
		btnStatistikHilfe.setBounds(10, 10, 60, 50);
		Panel_Statistiken.add(btnStatistikHilfe);

		// ENDE PANEL 6 Statistiken

		// Anfang Panel 7 Sparfunktion
		//
		//

		lblStartwert = new JLabel("Startwert:");
		lblStartwert.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblStartwert.setBackground(Color.WHITE);
		lblStartwert.setBounds(22, 11, 171, 29);
		panel_Sparfunktion.add(lblStartwert);

		lblsparrate = new JLabel("Sparrate:");
		lblsparrate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblsparrate.setBounds(21, 70, 142, 29);
		panel_Sparfunktion.add(lblsparrate);

		textFieldsparrate = new JTextField();
		textFieldsparrate.setBounds(147, 74, 97, 20);
		panel_Sparfunktion.add(textFieldsparrate);
		textFieldsparrate.setColumns(10);

		JLabel lblIn = new JLabel(" Euro ( z.B. 5.55)");
		lblIn.setBounds(254, 77, 109, 14);
		panel_Sparfunktion.add(lblIn);

		JLabel lblWiederholung = new JLabel("Sparintervall:");
		lblWiederholung.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWiederholung.setBounds(21, 133, 142, 29);
		panel_Sparfunktion.add(lblWiederholung);

		rdbtnEinmaligSparfkt = new JRadioButton("einmalig");
		rdbtnEinmaligSparfkt.setBounds(147, 136, 97, 23);
		panel_Sparfunktion.add(rdbtnEinmaligSparfkt);

		rdbtnMonatlichSparfkt = new JRadioButton("monatlich");
		rdbtnMonatlichSparfkt.setBounds(242, 136, 109, 23);
		panel_Sparfunktion.add(rdbtnMonatlichSparfkt);

		rdbtnJaehrlichSparfkt = new JRadioButton("j\u00E4hrlich");
		rdbtnJaehrlichSparfkt.setBounds(353, 136, 109, 23);
		panel_Sparfunktion.add(rdbtnJaehrlichSparfkt);

		JLabel lblZeitraum = new JLabel("Zeitraum:");
		lblZeitraum.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZeitraum.setBounds(21, 186, 142, 29);
		panel_Sparfunktion.add(lblZeitraum);

		textFieldZeitraum = new JTextField();
		textFieldZeitraum.setBounds(147, 190, 97, 20);
		panel_Sparfunktion.add(textFieldZeitraum);
		textFieldZeitraum.setColumns(10);

		JLabel lblMonatejahre = new JLabel("Monate/Jahre");
		lblMonatejahre.setBounds(254, 193, 96, 14);
		panel_Sparfunktion.add(lblMonatejahre);

		JLabel lblKontostandNachSparmanahme = new JLabel(
				"Kontostand nach Sparma\u00DFnahme:");
		lblKontostandNachSparmanahme.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblKontostandNachSparmanahme.setBounds(22, 332, 200, 34);
		panel_Sparfunktion.add(lblKontostandNachSparmanahme);

		lblnachSparmaßnahme = new JLabel("-");
		lblnachSparmaßnahme.setBounds(242, 338, 184, 22);
		panel_Sparfunktion.add(lblnachSparmaßnahme);

		btnBerechne = new JButton("Berechne");
		btnBerechne.setMnemonic(KeyEvent.VK_ENTER);
		btnBerechne.setBounds(22, 297, 89, 23);
		panel_Sparfunktion.add(btnBerechne);

		textFieldStartwert = new JTextField();
		textFieldStartwert.setBounds(147, 15, 97, 20);
		panel_Sparfunktion.add(textFieldStartwert);
		textFieldStartwert.setColumns(10);

		JLabel lblZinsen = new JLabel("Zinssatz:");
		lblZinsen.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZinsen.setBounds(22, 247, 55, 20);
		panel_Sparfunktion.add(lblZinsen);

		JLabel lblZinsperiode = new JLabel("Zinsperiode");
		lblZinsperiode.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblZinsperiode.setBounds(362, 250, 80, 14);
		panel_Sparfunktion.add(lblZinsperiode);

		textFieldZinsen = new JTextField();
		textFieldZinsen.setBounds(147, 247, 97, 20);
		panel_Sparfunktion.add(textFieldZinsen);
		textFieldZinsen.setColumns(10);

		lbl_prozent = new JLabel("% (z.B. 3.02)");
		lbl_prozent.setText("% z.B. 3.02");
		lbl_prozent.setBounds(254, 250, 71, 14);
		panel_Sparfunktion.add(lbl_prozent);

		comboBoxZinsperiode = new JComboBox<String>();
		comboBoxZinsperiode.setBounds(470, 247, 109, 20);
		comboBoxZinsperiode.setModel(new DefaultComboBoxModel<String>(
				new String[] { "(Bitte Wählen)", "jährlich", "halbjährig",
						"quartalsweise", "monatlich" }));
		panel_Sparfunktion.add(comboBoxZinsperiode);

		rdbtnZinsenAnAus = new JRadioButton("an\r\n");
		rdbtnZinsenAnAus.setBounds(75, 246, 55, 23);
		panel_Sparfunktion.add(rdbtnZinsenAnAus);
		rdbtnZinsenAnAus.setSelected(true);
		// ENDE PANEL 7 Sparfunktion

		// ENDE PANEL 7 Sparfunktion

	}
	
	//
	// allgemeine Methoden
	//

	/**
	 * Anfangsabfrage, die überprüft, ob die Csv Datei leer ist. Wenn das der
	 * Fall ist wird man dazu aufgefordert ein neues Konto anzulegen
	 * 
	 */	
	public void Anfangsabfrage() {

		// Infomessage, die gezeigt wird, wenn die Csv Datei leer ist und von
		// einem neuen Kontostart zu rechnen ist.
		if (budget.Geldvermögen.size() == 0) {
			JOptionPane.showMessageDialog(null, willkommen,
					"Willkommen bei MyBudgetManager",
					JOptionPane.INFORMATION_MESSAGE);
			// Deaktivierung von Zugriffsmöglichkeiten
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(3, false);
			tabbedPane.setEnabledAt(4, false);
			tabbedPane.setEnabledAt(5, false);
			tabbedPane.setSelectedIndex(1);
			// ComboBox nur mit dem Startwert
			comboBox_Ausgaben.insertItemAt("Kontoeröffnung", 1);
			comboBox_Ausgaben.setSelectedIndex(1);
			comboBox_Ausgaben.setEnabled(false);
			// ComboBox nur mit dem Startwert
			comboBox_Einnahmen.insertItemAt("Kontoeröffnung", 1);
			comboBox_Einnahmen.setSelectedIndex(1);
			comboBox_Einnahmen.setEnabled(false);

			btnReset_Einnahmen.setEnabled(false);
			btnReset_Ausgaben.setEnabled(false);
		}

	}

	/**
	 * Auflösen der Deativierung, wenn das Programm zum ersten Mal gestartet
	 * wird.
	 */
	public void Aktivierung() {
		// Freigabe der tabbedPane Blätter
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setEnabledAt(3, true);
		tabbedPane.setEnabledAt(4, true);
		tabbedPane.setEnabledAt(5, true);

		// Freigabe der ComboBox_Ausgaben und Umschlag auf das erste Item
		comboBox_Ausgaben.removeItemAt(1);
		comboBox_Ausgaben.setEnabled(true);

		// Freigabe der ComboBox_Einnahmen und Umschlag auf das erste Item
		comboBox_Einnahmen.removeItemAt(1);
		comboBox_Einnahmen.setEnabled(true);

		// Freigabe der Resetbutton in Einnahmen und Ausgaben
		btnReset_Einnahmen.setEnabled(true);
		btnReset_Ausgaben.setEnabled(true);

	}

	/**
	 * Hier wird die Kontoübersicht initiiert, die die Tabelle in der
	 * Kontoübersicht anzeigt
	 */
	@SuppressWarnings("deprecation")
	public void Init_Kontoübersicht() {
		// Hier wird ein mehrdimensionales Array erzeugt, dass 5 Spalten hat und
		// die Zeilenanzahl von budget.Geldvermögen bekommt.
		// Diese Spaltenanzahl bezieht sich auf die Zeilenanzahl der Csv Datei
		Object[][] data = new Object[budget.Geldvermögen.size()][5];
		int i = 0;
		String m;
		for (Posten p : budget.Geldvermögen) {
			data[i][0] = new SimpleDateFormat("dd.MM.yyyy")
					.format(p.getDatum());
			data[i][1] = p.getBezeichnung();
			data[i][2] = p.getnotiz();
			data[i][3] = p.getBetrag();
			if (p.getintern_Einnahme_Ausgabe() == 0)
				m = "Einnahme";
			else
				m = "Ausgabe";

			data[i][4] = m;
			i++;
		}

		table = new JTable(data, new Object[] { "Datum", "Kategorie",
				"Beschreibung", "Betrag", "Buchungsart" });
		table.enable(false);
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
	}

	/**
	 * Hier wird der Aktuelle Kontostand aktualisiert.
	 * 
	 * @param k "1" schaltet Warnmeldung ein, "0" aus
	 * 
	 * @exception FileNotFoundException @throws Ausgabe, dass die csv Datei nicht da ist
	 * 
	 * @exception IOException @throws Ausgabe, dass es mit der Csv Datein ein Problem gibt
	 * 
	 * @exception ParseException @throws Ausgabe, dass die Datein nicht eingelesen werden kann
	 * 
	 */
	public void Init_Kontostand(int k) {
		NumberFormat nf = NumberFormat.getInstance(new Locale("de", "DE"));
		double i = 0;
		budget.Geldvermögen.clear();

		try {
			// Zeilenweises Einlesen der Daten
			CSVReader reader = new CSVReader(new FileReader("data/budget.csv"));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
						Locale.GERMAN);
				// Einlesen von Datum/Notizen/Kategorie/Buchungsbetrag/Angabe,
				// ob es sich um eine Einnahme oder Ausgabe handelt
				Date datum = df.parse(nextLine[0]);
				String notiz = nextLine[1];
				String bezeichnung = nextLine[2];
				double betrag = Double.parseDouble(nextLine[3]);
				int intern_Einnahme_Ausgabe = Integer.parseInt(nextLine[4]);
				budget.Geldvermögen.add(new Posten(datum, notiz, bezeichnung,
						betrag, intern_Einnahme_Ausgabe));

			}
			reader.close();

			// Exceptions
		} catch (FileNotFoundException e) {
			System.err.println(csv_nichtgefunden);
			System.exit(1);

		} catch (IOException e) {
			System.err.println(csv_problemöffnen);
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

		lblKontostand.setText(nf.format(i) + "");
		if (i >= 0) {
			lblKontostand.setForeground(new Color(20, 170, 20));
		} else {
			lblKontostand.setForeground(Color.RED);
		}

		// Warnungen, wenn der Kontostand sehr niedrig ist.
		// Eine Warnung im oberen recht Feld als permanente Warnung
		// Eine Warunung als Popupbenachrichtigung
		// Der Startwert ist davon ausgeschlossen.

		// Wenn der übergebene Parameter 1 ist werden die Fehlermeldungen
		// ausgegeben, wenn die Initalisierung stattfindet
		if (k == 1) {

			if (budget.Geldvermögen.size() >= 2) {
				if (i < 20 && i >= 0 && budget.Geldvermögen.size() != 0) {
					JOptionPane.showMessageDialog(null,
							"Ihr Kontostand ist sehr gering.\n"
									+ "Bitte achten Sie auf Ihre Ausgaben.",
							"Kontostandswarnung", JOptionPane.WARNING_MESSAGE);

					lblKontostandWarnung
							.setText("<html><body><center><u>  ACHTUNG!</u><u><br>Ihr Kontostand ist sehr niedrig!</br></u></center></body></html>");
					lblKontostandWarnung
							.setBackground(new Color(250, 250, 120));
					lblKontostandWarnung.setForeground(Color.black);
					lblKontostandWarnung.setOpaque(true);
				} else if (i < 0) {
					JOptionPane.showMessageDialog(null,
							"Ihr Kontostand befindet sich im negativen Bereich.\n"
									+ "Bitte achten Sie auf Ihre Ausgaben.",
							"Kontostandswarnung", JOptionPane.WARNING_MESSAGE);

					lblKontostandWarnung
							.setText("<html><body><center><u>ACHTUNG!</u><u><br>Ihr Kontostand ist im negativen Bereich!</br></u></center></body></html>");
					lblKontostandWarnung.setBackground(new Color(250, 70, 70));
					lblKontostandWarnung.setForeground(Color.white);
					lblKontostandWarnung.setOpaque(true);
				} else {
					lblKontostandWarnung.setText("");
					lblKontostandWarnung.setBackground(Color.WHITE);
				}
			}
		}

		// Startwert Sparfunktion, basierend auf dem aktuellen Kontostand
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("#0.00", dfs);
		String s = String.valueOf(df.format(i));// zur Sparfunktion -> Startwert
												// durch Punkt statt durch Komma
												// trennen
		// um exception zu verhindern

		textFieldStartwert.setText(s);
		textFieldZeitraum.setText("0");

		// Weitere Methodenausführung
		Init_Kontoübersicht();
		Statistik_Warnung();

	}
	
	//
	// Methoden für die Ausgaben
	//

	/**
	 * 
	 * Checkdata_Ausgaben prüft die Benutzereingaben für die Gui Maske der
	 * "Ausgaben". Wenn alle Eingaben korrekt sind wird die Csv-Datei mit diesen
	 * Daten beschrieben.
	 * 
	 * @exception FileNotFoundException @throws Ausgabe, dassdie csv Datei nicht da ist
	 * 
	 * @exception IOException @throws Ausgabe, dass die csv Datei probleme hat
	 * 
	 * @exception ParseException @throws Falsche Datumseingabe
	 * 
	 * @exception NumberFormatException @throws Ausgabe, dass es eine fasche Betragseingabe gab
	 * 
	 * @exception IndexOutofBoundsException @throws Ausgabe, dass eine Kategorie gewählt werden soll
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
			// strenge Datumsprüfung einschalten
			dateFormat.setLenient(false);
			dateFormat.parse(txtTtmmjjjj_Ausgaben.getText());

			double k = Double.parseDouble(textField_Ausgaben.getText());
			if (k < 0)
				throw new NumberFormatException();
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
			// Punkttrennung
			dfs.setDecimalSeparator('.');
			// Gibt das Format für die Betragseingabe an
			DecimalFormat df = new DecimalFormat("#0.00", dfs);
			String j;
			j = df.format(k);
			// Falsche Betragseingabe (Muss der Formatvorgabe entsprechen)
			if (!textField_Ausgaben.getText().equals(j))
				throw new NumberFormatException();

			// ComboBox auf den Feld "Bitte Wählen"
			if (comboBox_Ausgaben.getSelectedIndex() == 0)
				throw new IndexOutOfBoundsException();

			CSVWriter writer = null;

			// Wenn alle Vorgaben korrekt sind wird hier die Csv Datei
			// beschrieben
			try {
				writer = new CSVWriter(new FileWriter("data/budget.csv", true));
				String[] entries = new String[5];
				// Auslese der einzelnen Felder
				entries[0] = txtTtmmjjjj_Ausgaben.getText();
				entries[1] = textFieldNotiz_Ausgabe.getText();
				entries[2] = (String) comboBox_Ausgaben.getSelectedItem();
				entries[3] = "-" + textField_Ausgaben.getText();
				entries[4] = "1"; // 1 gilt als Ausgabe. Nur intern sichtbar
				writer.writeNext(entries);
				writer.close();
				Clear_Ausgaben();
				JOptionPane.showMessageDialog(null, buchung_a,
						"Ausgabenbuchung", JOptionPane.INFORMATION_MESSAGE);
				Init_Kontostand(1);

				// Exceptions
			} catch (FileNotFoundException ex) {
				System.err.println(csv_nichtgefunden);
				System.exit(1);

			} catch (IOException ex) {
				System.err.println(csv_problemöffnen);
				System.exit(1);
			}

		}

		catch (ParseException e) {
			JOptionPane.showMessageDialog(null, falsches_datum, "Fehler",
					JOptionPane.ERROR_MESSAGE);
			txtTtmmjjjj_Ausgaben.setText(null);
		}

		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, falsche_betragseingabe,
					"Fehler", JOptionPane.ERROR_MESSAGE);
			textField_Ausgaben.setText(null);
		}

		catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, kategorie, "Fehler",
					JOptionPane.ERROR_MESSAGE);

		}

	}

	/**
	 * Methode, die die Eingaben in die Felder der Ausgaben auf den
	 * ursprünglichen Zustand zurück setzt
	 */
	public void Clear_Ausgaben() {
		textField_Ausgaben.setText(null);
		txtTtmmjjjj_Ausgaben.setText(new SimpleDateFormat("dd.MM.yyyy")
				.format(new Date()));
		comboBox_Ausgaben.setSelectedIndex(0);
		textFieldNotiz_Ausgabe.setText(null);
	}

	//
	// Methoden für die Einnahmen
	//
	
	/**
	 * 
	 * Einlesen der Einnahmen in die Csv Datei aus der Gui. Fehlerabfragen, ob
	 * der Nutzer auch alle daten richtig eingegeben hat.
	 * 
	 * @exception FileNotFoundException @throws Ausgabe, dass die csv Datei nicht da ist
	 * 
	 * @exception IOException @throws Ausgabe, dass die csv Datei Probleme hat
	 * 
	 * @exception ParseException @throws Ausgabe, dass das Datum falsch eingegeben wurde
	 * 
	 * @exception NumberFormatException @throws Ausgabe, dass es einen Fehler bei der Betrageingabe gab
	 * 
	 * @exception IndexOutOfBoundsException @throws Ausgabe, dass ein Fehler bei der Kategorie gab
	 * 
	 */
	public void CheckData_Einnahmen() {
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
			// strenge Datumsprüfung einschalten
			dateFormat.setLenient(false);
			dateFormat.parse(txtTtmmjjjj_Einnahmen.getText());

			double k = Double.parseDouble(textField_Einnahmen.getText());
			if (k < 0)
				throw new NumberFormatException();
			DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
			// Punkttrennung
			dfs.setDecimalSeparator('.');
			// Gibt das Format für die Betragseingabe an
			DecimalFormat df = new DecimalFormat("#0.00", dfs);
			String j;
			j = df.format(k);
			// Falsche Betragseingabe (Muss der Formatvorgabe entsprechen)
			if (!textField_Einnahmen.getText().equals(j))
				throw new NumberFormatException();

			// ComboBox muss auf das Feld "Bitte Wählen"
			if (comboBox_Einnahmen.getSelectedIndex() == 0)
				throw new IndexOutOfBoundsException();

			// Wenn alle Vorgaben korrekt sind wird hier die Csv Datei
			// beschrieben
			CSVWriter writer = null;

			try {
				writer = new CSVWriter(new FileWriter("data/budget.csv", true));
				String[] entries = new String[5];
				// Auslese der einzelnen Felder
				entries[0] = txtTtmmjjjj_Einnahmen.getText();
				entries[1] = textFieldNotiz_Einnahmen.getText();
				entries[2] = (String) comboBox_Einnahmen.getSelectedItem();
				entries[3] = textField_Einnahmen.getText();
				entries[4] = "0"; // 0 gilt als Einnahme. Nur intern sichtbar
				writer.writeNext(entries);
				writer.close();
				Clear_Einnahmen();
				JOptionPane.showMessageDialog(null, buchung_e,
						"Einnahmenbuchung", JOptionPane.INFORMATION_MESSAGE);
				Init_Kontostand(1);

				// Exceptions
			} catch (FileNotFoundException ex) {
				System.err.println(csv_nichtgefunden);
				System.exit(1);

			} catch (IOException ex) {
				System.err.println(csv_problemöffnen);
				System.exit(1);
			}

		}

		catch (ParseException e) {
			JOptionPane.showMessageDialog(null, falsches_datum, "Fehler",
					JOptionPane.ERROR_MESSAGE);
			txtTtmmjjjj_Einnahmen.setText(null);
		}

		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, falsche_betragseingabe,
					"Fehler", JOptionPane.ERROR_MESSAGE);
			textField_Einnahmen.setText(null);
		}

		catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(null, kategorie, "Fehler",
					JOptionPane.ERROR_MESSAGE);

		}
	}

	/**
	 * Die Fehler der Einnahmen in der GUI werden auf den Urzustand gebracht.
	 */
	public void Clear_Einnahmen() {
		textField_Einnahmen.setText(null);
		txtTtmmjjjj_Einnahmen.setText(new SimpleDateFormat("dd.MM.yyyy")
				.format(new Date()));
		comboBox_Einnahmen.setSelectedIndex(0);
		textFieldNotiz_Einnahmen.setText(null);
	}

	//
	// Methoden für die Statistik
	//

	/**
	 * Das vom Benutzer gewählte Statistik-Modell wird an die Statistik-Klasse
	 * weitergeleitet. Dabei wird noch überprüft, ob ein individueller oder der
	 * Gesamtzeitraum ausgewählt wurde, und ebenfalls an die Statistik-Klasse
	 * weitergeleitet. Der Vorgang wird beim individuell gewähltem Zeitraum nur
	 * bei korrekter Datumsangabe durchgeführt.
	 * 
	 * @param selection
	 *            ausgewählte Statistik-Modell des Benutzers
	 */
	public void Grafikmodellauswahl(String selection) {
		// Initialisierung der Daten auf Ursprungswerte, falls Änderungen
		// vorgenommen wurden
		Init_Kontostand(0);
		// Referenz auf Statistik-Klasse
		Statistikauswahl = new Statistik(budget);
		// Radiobutton mit individuellem Zeitraum ausgewählt
		if (rdbtnIndividualZeitraum.isSelected() == true) {
			if ((Statistik_CheckAuswahlDatum() == true)
					&& (Statistik_CheckDateDiff() == true)) {
				Statistikauswahl.Statistik_Manager(selection,
						textField_Statistik_Startwert.getText(),
						textField_Statistik_Endwert.getText());
			}
		} // Radiobutton für Gesamtzeitraum ausgewählt
		else {
			Statistikauswahl.Statistik_Manager(selection, "0", "0");
		}
	}

	/**
	 * Im Statistik Panel wird das Datum beim individuellem Zeitraum auf
	 * Korrektheit überprüft. Es gibt "wahr" bei richtiger und "falsch" bei
	 * fehlerhafter Eingabe zurück.
	 * 
	 * @return true bei korrekter Datumseingabe, ansonsten <code>false</code>
	 * 
	 * @throws ParseException
	 *             bei fehlerhafter Datumseingabe Error-Meldung
	 */
	public boolean Statistik_CheckAuswahlDatum() {

		try {
			// überprüft die Gültigkeit von Datumsstartwert & Datumsendwert
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

	/**
	 * Im Statistik Panel wird das Datum beim individuellem Zeitraum auf die
	 * korrekte Reihenfolge überprüft. Das Enddatum muss größer/gleich dem
	 * Anfangsdatum sein.
	 * 
	 * @return true bei korrekter Datumsreihenfolge, ansonsten
	 *         <code>false</code>
	 * 
	 * @throws Exception
	 *             bei fehlerhafter Datumsreihenfolge Error-Meldung
	 */
	public boolean Statistik_CheckDateDiff() {
		try {
			sdf = new SimpleDateFormat("dd.MM.yyyy");
			Start = sdf.parse(textField_Statistik_Startwert.getText());
			End = sdf.parse(textField_Statistik_Endwert.getText());
			// Datumsvergleich
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

	/**
	 * Die Warnung im Statistik Panel besteht bei zu geringer Anzahl an Daten.
	 * Diese Warnung bleibt solange bestehen, bis mindestens 20 Datensätze von
	 * jeweiligen Einnahmen und Ausgaben vorhanden sind. Außerdem müssen
	 * mindesten 2 verschiedenartige Datumswerte vom Monat her, jeweils für
	 * Einnahmen und Ausgaben existieren, damit die Warnung erlischt.
	 */
	public void Statistik_Warnung() {
		int Zaehler_einnahmen = 0, Zaehler_ausgaben = 0, x = -1, y = -1;
		String monat_einnahmen = null, monat_ausgaben = null;
		boolean month_einnahmen = false, month_ausgaben = false;
		monatformat = new SimpleDateFormat("MM.yyyy");
		// temporäre ArrayList tmp wird erstellt ohne Eintrag "Kontoeröffnung"
		List<Posten> tmp = new ArrayList<Posten>();
		for (Posten p : budget.Geldvermögen)
			if (!p.getBezeichnung().equals("Kontoeröffnung"))
				tmp.add(p);
		// Suche des ersten Eintragindex für Einnahme
		for (Posten p : tmp) {
			x++;
			if (p.getintern_Einnahme_Ausgabe() == 0)
				break;
		}
		// Erfassung des Datums vom ersten Eintragindex für Einnahme
		if (!tmp.isEmpty())
			monat_einnahmen = monatformat.format(tmp.get(x).getDatum());
		// Suche des ersten Eintragindex für Ausgabe
		for (Posten p : tmp) {
			y++;
			if (p.getintern_Einnahme_Ausgabe() == 1)
				break;
		}
		// Erfassung des Datums vom ersten Eintragindex für Ausgabe
		if (!tmp.isEmpty())
			monat_ausgaben = monatformat.format(tmp.get(y).getDatum());
		// Ermittlung der Anzahl von Einträgen für Einnahmen und Ausgaben, sowie
		// Überprüfung der jeweiligen Datumswerte nach verschiedenen
		// Monat/Jahres-Werten
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
		// Bedingungsabfrage für Warnmeldung
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

	//
	// Methoden für die Sparfunktion
	//
	

	
	// HIER Sparfunktion-Methoden!
	
	
	
	
	//
	// Verhalten hinzufuegen (Buttons-Funktionen)
	//
	
	/**
	 * Im Verhalten werden die Methoden angewand
	 */
	public void addBehavior() {
		// Abfrage, ob die Csv Datei leer ist und ein neues Konto erstellt
		// werden muss
		Anfangsabfrage();
		// Kontostandsinitiierung
		Init_Kontostand(1);

		// Button der die Kontoübersicht druckt.
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

		// Button der die Ausgaben resettet und dabei die Methoden
		// Clear_Ausgaben verwendet.
		btnReset_Ausgaben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Clear_Ausgaben();
			}

		});

		// Button der die Ausgaben bucht und dabei die Methode
		// Checkdata_Ausgaben verwendet.
		btnAusgabenBuchen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				Checkdata_Ausgaben();
				if ((comboBox_Ausgaben.isEnabled() == false)
						&& (budget.Geldvermögen.size() != 0)) {
					Aktivierung();
				}
			}

		});

		// Button über den man Hilfe bekommt (Ausgaben)
		btnHelpButton_Ausgaben.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaAusgabenHilfe = new JTextArea(hilfe_a);
				scrollPaneAusgabenHilfe = new JScrollPane(textAreaAusgabenHilfe);
				scrollPaneAusgabenHilfe.setPreferredSize(new Dimension(620,300));
				textAreaAusgabenHilfe.setEditable(false);
				JOptionPane.showMessageDialog(null, scrollPaneAusgabenHilfe, "Hilfe",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});


		// Button der die Einnahmen resettet und dabei die Methoden
		// Clear_Ausgaben verwendet.
		btnReset_Einnahmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Clear_Einnahmen();
			}

		});

		// Button der die Einnahmen bucht und dabei die Methode
		// CheckData_Einnahmen verwendet
		btnEinnahmenBuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CheckData_Einnahmen();
				if ((comboBox_Ausgaben.isEnabled() == false)
						&& (budget.Geldvermögen.size() != 0)) {
					Aktivierung();
				}
			}

		});

		// Button über den man Hilfe bekommt (Einnahmen)
		btnHelpButton_Einnahmen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaEinnahmenHilfe = new JTextArea(hilfe_e);
				scrollPaneEinnahmenHilfe = new JScrollPane(textAreaEinnahmenHilfe);
				scrollPaneEinnahmenHilfe.setPreferredSize(new Dimension(620,300));
				textAreaEinnahmenHilfe.setEditable(false);
				JOptionPane.showMessageDialog(null, scrollPaneEinnahmenHilfe, "Hilfe",
						JOptionPane.INFORMATION_MESSAGE);


			}
		});

		// Radiobutton der den gesamten Zeitraum nimmt
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

		// Radiobutton der einen individuellen Zeitraum nimmt
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

		// Button, der die Statistik anzeigt
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

		// Button über den man Hilfe bekommt (Statistik)
		btnStatistikHilfe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaStatHilfe = new JTextArea(hilfe_stat);
				scrollPaneStatHilfe = new JScrollPane(textAreaStatHilfe);
				scrollPaneStatHilfe.setPreferredSize(new Dimension(620, 300));
				textAreaStatHilfe.setEditable(false);
				JOptionPane.showMessageDialog(null, scrollPaneStatHilfe,
						"Hilfe - Statistik", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// Button für die Ausführung der Sparfunktion

		// Button der das Programm schließt
		btnSchließen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int schliessen = JOptionPane.showConfirmDialog(null,
						prog_close, "Programm schließen",
						JOptionPane.YES_NO_OPTION);
				if (schliessen == 0) {
					setVisible(false);
					dispose();
					System.exit(0);
				}
			}
		});

		/**
		 * Button, der das Konto löscht
		 * 
		 * @exception FileNotFoundException @throws Ausgabe, dass die csv Datei nicht da ist
		 * 
		 * @exception IOException @throws Ausgabe, dass es ein Probleme beim Öffnen der csv Datei gab
		 */
		btnKontoLöschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int loeschen = JOptionPane.showConfirmDialog(null, konto_l,
						"Konto löschen", JOptionPane.YES_NO_OPTION);
				if (loeschen == 0) {
					comboBox_Ausgaben.removeItem("Kontoeröffnung");
					comboBox_Einnahmen.removeItem("Kontoeröffnung");
					lblKontostandWarnung.setText("");
					lblKontostandWarnung.setBackground(Color.WHITE);

					CSVWriter writer = null;
					try {
						writer = new CSVWriter(
								new FileWriter("data/budget.csv"));
						writer.writeNext(null);
						;
						writer.close();

					} catch (FileNotFoundException ex) {
						System.err.println("csv_nichtgefunden");
						System.exit(1);

					} catch (IOException ex) {
						System.err.println("csv_problemöffnen");
						System.exit(1);
					}

					budget.Geldvermögen.clear();
					Init_Kontostand(0);
					Anfangsabfrage();

				}
			}
		});

		// Button der den Taschenrechner öffnet
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

		// Button der eine Infoanzeige auslöst
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, info, "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

	}
}
