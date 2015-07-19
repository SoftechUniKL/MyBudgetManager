// Erforderliche Importe
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import com.opencsv.CSVReader;

/**
 * Die Statistik-Klasse enth�lt alle erforderlichen Methoden, um verschiedene
 * Grafik-Modelle, 2 Tabellen f�r je Einnahmen und Ausgaben, sowie eine
 * Kurz�bersicht in einem separatem Fenster darzustellen. Der Benutzer kann
 * somit eine �bersicht �ber die Statistik seiner Ausgaben und Einnahmen
 * erhalten. Dabei kann er im Hauptprogramm den individuellen Zeitraum und ein
 * gew�nschtes Grafik-Modell w�hlen.
 * <p>
 * Folgende Statistik-Modelle stehen zur Verf�gung:
 * "Einnahmen vs Ausgaben (Kategorie) [Kreisdiagramm]",
 * "Einnahmen vs Ausgaben (Kategorie) [Balkendiagramm]",
 * "Einnahmen & Ausgaben (Kategorie) [Balkendiagramm]",
 * "Einnahmen (Zeit: Buchungsdatum) [Balken- und Kreisdiagramm]",
 * "Ausgaben (Zeit: Buchungsdatum) [Balken- und Kreisdiagramm]",
 * "Einnahmen vs Ausgaben (Gesamt) [Balkendiagramm]",
 * "Einnahmen vs Ausgaben (Zeit: monatlich) [Liniendiagramm]",
 * "Einnahmen vs Ausgaben: Differenz (Zeit: Buchungsdatum) [Liniendiagramm]",
 * "Einnahmen vs Ausgaben (Kategorie) [Wasserfalldiagramm]".
 * <p>
 * Die Kurz�bersicht enth�lt die Anzahl von Buchungen, die Summen von Einnahmen
 * und Ausgaben und das Saldo.
 * 
 * @author Markus Dittmann
 *
 */
public class Statistik {

	// Alle Objektvariablen
	/**
	 * Objekt f�r Statistik-Fenster
	 */
	JFrame Statistic = new JFrame("MyBudgetManager  -- Statistik --    ["
			+ new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss").format(new Date())
			+ "]"); // Neues Fenster mit Name und Datum/Zeit in Titelleiste

	/**
	 * Modell der Daten
	 */
	private BudgetPlanModel budget;

	// Objektvariablen f�r die Statistik-GUI
	private JScrollPane scrollPane_Einnahmen;
	private JScrollPane scrollPane_Ausgaben;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel lblKonto�bersicht_Einnahmen;
	private JLabel lblKonto�bersicht_Ausgaben;
	private JLabel lblKurz�bersicht;
	private JLabel lblBuchungen;
	private JLabel lblEinnahmen;
	private JLabel lblAusgaben;
	private JLabel lblSaldo;
	private JLabel lblBuchungswert_gesamt;
	private JLabel lblBuchungswert_detail;
	private JLabel lblEinnhamenwert;
	private JLabel lblAusgabenwert;
	private JLabel lblSaldowert;
	private JLabel lblL�ckenf�ller;
	private JLabel lblZeitraum;
	private JTable table_Einnahmen;
	private JTable table_Ausgaben;

	/*
	 * Objektvariablen f�r die Methoden
	 * (Berechnungen/Datenverwaltung/Grafikerstellung)
	 */
	private SimpleDateFormat formatter;
	private int size_Einnahmen;
	private int size_Ausgaben;
	private Object[][] data_Ausgaben;
	private Object[][] data_Einnahmen;
	private ArrayList<Posten> tmp;
	private CSVReader reader;
	private NumberFormat nf;
	private ArrayList<Posten> Sortierte_Kategorie_Liste;
	private DefaultPieDataset piedataset;
	private ChartPanel chartpanel;
	private ChartPanel chartpanel2;
	private DefaultCategoryDataset categorydataset;
	private DefaultCategoryDataset cd;
	private ArrayList<Posten> Only_Einnahmen_Or_Ausgaben;
	private DefaultCategoryDataset defaultcategorydataset;
	private NumberAxis numberaxis;
	private CategoryPlot categoryplot;
	private AbstractCategoryItemRenderer lineandshaperenderer;
	private NumberAxis numberaxis1;
	private AbstractCategoryItemRenderer barrenderer;
	private TimeSeries series1;
	private TimeSeries series2;
	private JFreeChart jfreechart;
	private CategoryPlot categoryplot1;
	private CategoryAxis categoryaxis;
	private CombinedDomainCategoryPlot combineddomaincategoryplot;
	private TimeSeriesCollection dataset;
	private JFreeChart chart;
	private JFreeChart chart2;
	private static ValueAxis domainAxis;

	/**
	 * Objekt f�r Fenster-Icon
	 */
	private Image icon = Toolkit.getDefaultToolkit().getImage(
			"src/img/Money.png");

	/**
	 * Konstruktor f�r die Statistik.
	 * 
	 * Hier wird das Statistikfenster initialisiert.
	 *
	 * @param budget
	 *            Modell der Daten
	 * @throws whoJackedMyIcon
	 *             Konnte Programm-Icon icht laden.
	 */
	public Statistik(BudgetPlanModel budget) {
		// schlie�t aktuelles Fenster und Ressourcen-Freigabe
		Statistic.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Statistic.setBounds(100, 100, 450, 300); // Gr��e des Frames
		Statistic.setVisible(true);
		Statistic.setMinimumSize(new Dimension(800, 550));
		Statistic.getContentPane().setLayout(new BorderLayout(0, 0));
		// setzt Icon in Titelleiste
		try {
			Statistic.setIconImage(icon);
		} catch (Exception whoJackedMyIcon) {
			System.out.println("Could not load program icon.");
		}
		// Erstellung der GUI f�rs neue Statistikfenster
		panel = new JPanel();
		Statistic.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(0, 185));
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(270, 185));
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);
		panel.add(panel_1, BorderLayout.EAST);

		panel_2 = new JPanel();
		Statistic.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(1, 2));

		panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 2));

		panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setBackground(Color.WHITE);
		panel_4.setLayout(new GridLayout(2, 2));

		scrollPane_Einnahmen = new JScrollPane();
		panel_3.add(scrollPane_Einnahmen);

		scrollPane_Ausgaben = new JScrollPane();
		panel_3.add(scrollPane_Ausgaben);

		lblZeitraum = new JLabel("Zeitraum");
		lblZeitraum.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblZeitraum.setForeground(new Color(5, 50, 180));
		lblZeitraum.setHorizontalAlignment(JTextField.RIGHT);
		panel_4.add(lblZeitraum);

		lblL�ckenf�ller = new JLabel("");
		panel_4.add(lblL�ckenf�ller);

		lblKonto�bersicht_Einnahmen = new JLabel(
				"Konto\u00FCbersicht:  Einnahmen:");
		panel_4.add(lblKonto�bersicht_Einnahmen);

		lblKonto�bersicht_Ausgaben = new JLabel("Ausgaben:");
		panel_4.add(lblKonto�bersicht_Ausgaben);

		lblKurz�bersicht = new JLabel(
				"<html><body><u>Kurz\u00FCbersicht:</u></body></html>");
		lblKurz�bersicht.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblKurz�bersicht.setBounds(73, 0, 96, 14);
		panel_1.add(lblKurz�bersicht);

		lblBuchungen = new JLabel("Buchungen:");
		lblBuchungen.setBounds(10, 30, 74, 14);
		panel_1.add(lblBuchungen);

		lblEinnahmen = new JLabel("Einnahmen:");
		lblEinnahmen.setBounds(10, 65, 74, 14);
		panel_1.add(lblEinnahmen);

		lblAusgaben = new JLabel("Ausgaben:");
		lblAusgaben.setBounds(10, 95, 74, 14);
		panel_1.add(lblAusgaben);

		lblSaldo = new JLabel("Saldo:");
		lblSaldo.setBounds(10, 130, 46, 14);
		panel_1.add(lblSaldo);

		lblBuchungswert_gesamt = new JLabel("");
		lblBuchungswert_gesamt.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuchungswert_gesamt.setBounds(94, 20, 160, 17);
		panel_1.add(lblBuchungswert_gesamt);

		lblBuchungswert_detail = new JLabel("");
		lblBuchungswert_detail.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuchungswert_detail.setBounds(94, 38, 160, 17);
		panel_1.add(lblBuchungswert_detail);

		lblEinnhamenwert = new JLabel("");
		lblEinnhamenwert.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEinnhamenwert.setBounds(94, 64, 160, 14);
		panel_1.add(lblEinnhamenwert);

		lblAusgabenwert = new JLabel("");
		lblAusgabenwert.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAusgabenwert.setBounds(94, 94, 160, 14);
		panel_1.add(lblAusgabenwert);

		lblSaldowert = new JLabel("");
		lblSaldowert.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaldowert.setBounds(94, 129, 160, 14);
		panel_1.add(lblSaldowert);

		this.budget = budget;
	}

	//
	// Methoden f�r die Strukturierung der Datens�tze und
	// �bersichtenerstellung f�r die GUI
	//

	/**
	 * Neuerstellung der ArrayList "Geldverm�gen". Diese enth�lt alle Daten aus
	 * der urspr�nglichen ArrayList "Geldverm�gen" nur ohne den Eintrag
	 * "Kontoer�ffnung", da er nicht zu den direkten Einnahmen und Ausgaben
	 * z�hlt.
	 */
	private void Buchungen_ohne_Kontoer�ffnung() {
		// Hilfs-ArrayList
		tmp = new ArrayList<Posten>();
		for (Posten p : budget.Geldverm�gen)
			if (!p.getBezeichnung().equals("Kontoer�ffnung"))
				tmp.add(p);
		budget.Geldverm�gen.clear();
		// neu reinkoppieren
		for (Posten t : tmp)
			budget.Geldverm�gen.add(t);
	}

	/**
	 * Es erstellt eine Kurz�bersicht zu dem Zeitraum, den der Benutzer vorher
	 * ausgew�hlt hat. Dazu geh�ren die Anzahl der Buchungen insgesamt und
	 * detailiert jeweils in Einnahmen und Ausgaben, sowie die Summen von
	 * Einnahmen und Ausgaben und dessen Saldo.
	 */
	private void Init_Kurz�bersicht() {
		nf = NumberFormat.getInstance(new Locale("de", "DE"));
		double i = 0, a = 0, e = 0;
		// Setzten der Anzahl von Buchungen
		lblBuchungswert_gesamt.setText("Gesamt: "
				+ (size_Ausgaben + size_Einnahmen));
		lblBuchungswert_detail.setText("Ein (" + size_Einnahmen + ") "
				+ ", Aus (" + size_Ausgaben + ")");
		// Summieren von Einnahmen und Ausgaben
		for (Posten p : budget.Geldverm�gen) {
			i += p.getBetrag();
			if (p.getintern_Einnahme_Ausgabe() == 0)
				e += p.getBetrag();
			else
				a += p.getBetrag();
		}
		// Setzten der Summen
		lblSaldowert.setText(nf.format(i) + " \u20ac");
		lblEinnhamenwert.setText(nf.format(e) + " \u20ac");
		lblEinnhamenwert.setForeground(new Color(20, 170, 20));
		lblAusgabenwert.setText(nf.format(a) + " \u20ac");
		lblAusgabenwert.setForeground(Color.RED);
		// Farbwahl bei Saldobetrag
		if (i < 0)
			lblSaldowert.setForeground(Color.RED);
		else
			lblSaldowert.setForeground(new Color(20, 170, 20));
	}

	/**
	 * Erstellung der beiden Tabellen f�r Einnahmen und Ausgaben. Zun�chst wird
	 * die Anzahl von Einnahmen und Ausgaben ermittelt (notwendig f�r die Gr��e
	 * der Tabelleneintr�ge). Dann werden die Daten aus der ArrayList
	 * "Geldverm�gen" ausgelesen und in die Tabellen eingef�gt.
	 */
	@SuppressWarnings("deprecation")
	private void Buchungs�bersicht() {
		// Ermittlung der Anzahl von Einnahmen und Ausgaben
		for (Posten pos : budget.Geldverm�gen)
			if (pos.getintern_Einnahme_Ausgabe() == 0)
				size_Einnahmen += 1;
			else
				size_Ausgaben += 1;
		// Erstellung von Objektvariablen
		data_Ausgaben = new Object[size_Ausgaben][4];
		data_Einnahmen = new Object[size_Einnahmen][4];
		int i = 0, j = 0;
		/*
		 * Auslesen der Daten aus ArrayList "Geldverm�gen" jeweils f�r Einnahmen
		 * und Ausgaben, und abspeichern in Objektvariablen
		 */
		for (Posten p : budget.Geldverm�gen) {
			if (p.getintern_Einnahme_Ausgabe() == 0) {
				data_Einnahmen[i][0] = new SimpleDateFormat("dd.MM.yyyy")
						.format(p.getDatum());
				data_Einnahmen[i][1] = p.getBezeichnung();
				data_Einnahmen[i][2] = p.getnotiz();
				data_Einnahmen[i][3] = p.getBetrag();
				i++;
			} else {
				data_Ausgaben[j][0] = new SimpleDateFormat("dd.MM.yyyy")
						.format(p.getDatum());
				data_Ausgaben[j][1] = p.getBezeichnung();
				data_Ausgaben[j][2] = p.getnotiz();
				data_Ausgaben[j][3] = p.getBetrag();
				j++;
			}
		}
		// Erstellung zweier Tabellen mit den Daten von Einnahmen
		table_Einnahmen = new JTable(data_Einnahmen, new Object[] { "Datum",
				"Kategorie", "Beschreibung", "Betrag" });
		table_Einnahmen.enable(false);
		table_Einnahmen.setAutoCreateRowSorter(true);
		table_Einnahmen.setBackground(new Color(180, 250, 200));
		scrollPane_Einnahmen.setViewportView(table_Einnahmen);
		// und Ausgaben
		table_Ausgaben = new JTable(data_Ausgaben, new Object[] { "Datum",
				"Kategorie", "Beschreibung", "Betrag" });
		table_Ausgaben.enable(false);
		table_Ausgaben.setAutoCreateRowSorter(true);
		table_Ausgaben.setBackground(new Color(250, 210, 210));
		scrollPane_Ausgaben.setViewportView(table_Ausgaben);

		Init_Kurz�bersicht();
	}

	/**
	 * Filterung der Datens�tze nur f�r den individuellen Zeitraum. Die
	 * ArrayList "Geldverm�gen" enth�lt danach nur die Datens�tze f�r den
	 * individuellen Zeitraum, den der Benutzer ausgew�hlt hat.
	 * 
	 * @param StartingDate
	 *            Anfangsdatum der Benutzereingabe bei individuellem Zeitraum
	 * @param EndingDate
	 *            Enddatum der Benutzereingabe bei individuellem Zeitraum
	 * 
	 */
	private void Init_Posten_Zeitraum(String StartingDate, String EndingDate) {
		// L�schung von ArrayList "Geldverm�gen"
		budget.Geldverm�gen.clear();
		formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date startDate = null;
		Date endDate = null;
		// Formatieung von String zu Date
		try {
			startDate = formatter.parse(StartingDate);
			endDate = formatter.parse(EndingDate);
		} catch (ParseException e) {
			System.err
					.println("Formatfehler: Datum konnte nicht formatiert werden!");
			System.exit(1);
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		// Zeilenweises Einlesen der Daten
		try {
			reader = new CSVReader(new FileReader("data/budget.csv"));
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
					Locale.GERMAN);
			String[] nextLine;
			/*
			 * von Anfangsdatum bis zum Enddatum Tag f�r Tag abgleichen ob Datum
			 * in CSV-Datei vorhanden; falls ja, Datensatz in ArrayList
			 * "Geldverm�gen" einf�gen
			 */
			while ((nextLine = reader.readNext()) != null) {
				start.setTime(startDate);
				for (Date date = start.getTime(); !start.after(end); start.add(
						Calendar.DATE, 1), date = start.getTime()) {
					if (formatter.format(date).compareTo(nextLine[0]) == 0) {
						Date datum = df.parse(nextLine[0]);
						String notiz = nextLine[1];
						String bezeichnung = nextLine[2];
						double betrag = Double.parseDouble(nextLine[3]);
						int intern_Einnahme_Ausgabe = Integer
								.parseInt(nextLine[4]);
						budget.Geldverm�gen.add(new Posten(datum, notiz,
								bezeichnung, betrag, intern_Einnahme_Ausgabe));
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err
					.println("Die Datei data/budget.csv wurde nicht gefunden!");
			System.exit(1);
		} catch (IOException e) {
			System.err
					.println("Probleme beim Oeffnen der Datei data/budget.csv!");
			System.exit(1);
		} catch (ParseException e) {
			System.err
					.println("Formatfehler: Die Datei konnte nicht eingelesen werden!");
			System.exit(1);
		}

		Buchungen_ohne_Kontoer�ffnung();
		// Sortierung der Datens�tze nach Datum (aufsteigend)
		Collections.sort(budget.Geldverm�gen, new Comparator<Posten>() {
			@Override
			public int compare(Posten o1, Posten o2) {
				return o1.getDatum().compareTo(o2.getDatum());
			}
		});

		Buchungs�bersicht();
	}

	//
	// Methoden f�r die Grafikerstellung
	//

	/**
	 * Erstellung der Grafik f�r Kreisdiagramm (Ausgaben) f�r die Methode
	 * "Kategorie_Kreisdiagramm_Grafik()".
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChartPieAusgaben(DefaultPieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D("Ausgaben", dataset,
				true, true, false);
		return chart;
	}

	/**
	 * Erstellung der Grafik f�r Kreisdiagramm (Einnahmen) f�r die Methode
	 * "Kategorie_Kreisdiagramm_Grafik()".
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChartPieEinnahmen(DefaultPieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D("Einnahmen", dataset,
				true, true, false);
		return chart;
	}

	/**
	 * Erstellung der Datens�tze f�r Kreisdiagramm f�r die Methode
	 * "Kategorie_Kreisdiagramm_Grafik()". Durch ein Algorithmus werden gleiche
	 * Kategorien und somit die jeweiligen Betr�ge zusammengefasst und nicht mit
	 * dem letzten Wert �berschrieben, was eine fehlerhafte Grafik zur Folge
	 * h�tte.
	 * 
	 * @param selection_data_ein_aus
	 *            erh�lt den Wert 0 f�r Einnahmen oder 1 f�r Ausgaben
	 * @return fertig zusammengesetzte Datens�tze
	 */
	private DefaultPieDataset createDataset_Auswahl2(int selection_data_ein_aus) {
		int einnahme_ausgabe = -1;
		// Abfrage des Parameterwerts
		if (selection_data_ein_aus == 0)
			einnahme_ausgabe = 0;
		else
			einnahme_ausgabe = 1;
		// Erstellung einer Hilfs-ArrayList
		Sortierte_Kategorie_Liste = new ArrayList<Posten>();
		for (Posten p : budget.Geldverm�gen)
			Sortierte_Kategorie_Liste.add(p);
		// Sortierung der Datens�tze nach Kategorie
		Collections.sort(Sortierte_Kategorie_Liste, new Comparator<Posten>() {
			@Override
			public int compare(Posten o1, Posten o2) {
				return o1.getBezeichnung().compareTo(o2.getBezeichnung());
			}
		});
		/*
		 * Algorithmus zur Zusammenfassung der gleichen Kategorien inkl. der
		 * jeweiligen Betr�ge 
		 * [hier detailierter, analog auch f�r die weiteren Algorithmen unten]
		 */
		double betrag = 0;
		int twin = 0, i = 0;
		piedataset = new DefaultPieDataset();
		for (Posten p : Sortierte_Kategorie_Liste) {
			i++;
			// Filter f�r reine Einnahmen oder Ausgaben
			if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
				// Schutz vor Zugriff auf ung�ltigen Index
				if (i < Sortierte_Kategorie_Liste.size()) {
					// Pr�fung ob Nachfolger gleiche Kategorie					
					if (Sortierte_Kategorie_Liste
							.get(i - 1)
							.getBezeichnung()
							.equals(Sortierte_Kategorie_Liste.get(i)
									.getBezeichnung())) {
						// "Zwilling"-> Existenz mind. 2 gleicher Kateg. 
						twin = 1;  
						betrag += Math.abs(p.getBetrag());
					} // Ausf�hrung bei "Zwilling"
					else if (twin == 1) {
						betrag += Math.abs(p.getBetrag());
						piedataset.setValue(p.getBezeichnung(), betrag);
						twin = 0;
						betrag = 0;
					} else
						piedataset.setValue(p.getBezeichnung(),
								Math.abs(p.getBetrag()));
				} // abschlie�ende Ausf�hrung da Zugriff auf unerlaubten Bereich
				  // (�ber letzten Index hinaus)
				else {
					if (twin == 1) {
						betrag += Math.abs(p.getBetrag());
						piedataset.setValue(p.getBezeichnung(), betrag);
						twin = 0;
						betrag = 0;
					} else
						piedataset.setValue(p.getBezeichnung(),
								Math.abs(p.getBetrag()));
				}
			}
		}
		return piedataset;
	}

	/**
	 * Hauptmethode f�r Kreisdiagramme nach Kategorien jeweils f�r Einnahmen und
	 * Ausgaben mit Einf�gen der Grafik in Panel (GUI).
	 */
	private void Kategorie_Kreisdiagramm_Grafik() {
		// Einf�gen des Kreisdiagramms nach Kategorien von Einnahmen in Panel
		JFreeChart pie = createChartPieEinnahmen((DefaultPieDataset) createDataset_Auswahl2(0));
		chartpanel = new ChartPanel(pie);
		chartpanel.setMouseWheelEnabled(true);
		panel_2.add(chartpanel);
		// Einf�gen des Kreisdiagramms nach Kategorien von Ausgaben in Panel
		JFreeChart pie2 = createChartPieAusgaben((DefaultPieDataset) createDataset_Auswahl2(1));
		chartpanel2 = new ChartPanel(pie2);
		chartpanel2.setMouseWheelEnabled(true);
		panel_2.add(chartpanel2);
	}

	/**
	 * Erstellung der Datens�tze f�r Balkendiagramm f�r die Methoden
	 * "Kategorie_Balkendiagramm_Grafik()" &
	 * "GesamtKategorie_Balkendiagramm_Grafik()". Durch ein Algorithmus werden
	 * gleiche Kategorien und somit die jeweiligen Betr�ge zusammengefasst und
	 * nicht mit dem letzten Wert �berschrieben, was eine fehlerhafte Grafik zur
	 * Folge h�tte.
	 * 
	 * @param selection_data_ein_aus
	 *            erh�lt den Wert 0 f�r Einnahmen oder 1 f�r Ausgaben f�r Grafik
	 *            der Methode "Kategorie_Balkendiagramm_Grafik()" oder den Wert
	 *            2 f�r die Grafik der Methode
	 *            "GesamtKategorie_Balkendiagramm_Grafik()"
	 * @return fertig zusammengesetzte Datens�tze
	 */
	private CategoryDataset createDataset_Auswahl3(int selection_data_ein_aus) {
		categorydataset = new DefaultCategoryDataset();
		int einnahme_ausgabe = -1;
		String einnahme_ausgabe_text = null;
		// Abfrage des Parameterwerts mit Variableninitialisierung
		if (selection_data_ein_aus == 0) {
			einnahme_ausgabe = 0;
			einnahme_ausgabe_text = "Einnahmen";
		} else {
			einnahme_ausgabe = 1;
			einnahme_ausgabe_text = "Ausgaben";
		}
		// Erstellung einer Hilfs-ArrayList
		Sortierte_Kategorie_Liste = new ArrayList<Posten>();
		for (Posten p : budget.Geldverm�gen)
			Sortierte_Kategorie_Liste.add(p);
		// Sortierung der Datens�tze nach Kategorie
		Collections.sort(Sortierte_Kategorie_Liste, new Comparator<Posten>() {
			@Override
			public int compare(Posten o1, Posten o2) {
				return o1.getBezeichnung().compareTo(o2.getBezeichnung());
			}
		});
		/*
		 * Algorithmus zur Zusammenfassung der gleichen Kategorien inkl. der
		 * jeweiligen Betr�ge
		 * 
		 * bei "selection_data_ein_aus == 2" Algorithmus f�r Methode
		 * "GesamtKategorie_Balkendiagramm_Grafik()", sonst bei 0 oder 1 f�r
		 * Methode "Kategorie_Balkendiagramm_Grafik()"
		 */
		if (selection_data_ein_aus == 2)
			for (int data_einnahme_ausgabe = 0; data_einnahme_ausgabe < 2; data_einnahme_ausgabe++) {
				double betrag = 0;
				int twin = 0, i = 0;
				for (Posten p : Sortierte_Kategorie_Liste) {
					i++;
					if (p.getintern_Einnahme_Ausgabe() == data_einnahme_ausgabe) {
						if (i < Sortierte_Kategorie_Liste.size()) {
							if (Sortierte_Kategorie_Liste
									.get(i - 1)
									.getBezeichnung()
									.equals(Sortierte_Kategorie_Liste.get(i)
											.getBezeichnung())) {
								twin = 1;
								betrag += p.getBetrag();
							} else if (twin == 1) {
								betrag += p.getBetrag();
								categorydataset
										.addValue(
												betrag,
												"Einnahmen (pos. Werte) und Ausgaben (neg. Werte)",
												p.getBezeichnung());
								twin = 0;
								betrag = 0;
							} else
								categorydataset
										.addValue(
												p.getBetrag(),
												"Einnahmen (pos. Werte) und Ausgaben (neg. Werte)",
												p.getBezeichnung());
						} else {
							if (twin == 1) {
								betrag += p.getBetrag();
								categorydataset
										.addValue(
												betrag,
												"Einnahmen (pos. Werte) und Ausgaben (neg. Werte)",
												p.getBezeichnung());
								twin = 0;
								betrag = 0;
							} else
								categorydataset
										.addValue(
												p.getBetrag(),
												"Einnahmen (pos. Werte) und Ausgaben (neg. Werte)",
												p.getBezeichnung());
						}
					}
				}
			}
		else {
			double betrag = 0;
			int twin = 0, i = 0;
			for (Posten p : Sortierte_Kategorie_Liste) {
				i++;
				if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
					if (i < Sortierte_Kategorie_Liste.size()) {
						if (Sortierte_Kategorie_Liste
								.get(i - 1)
								.getBezeichnung()
								.equals(Sortierte_Kategorie_Liste.get(i)
										.getBezeichnung())) {
							twin = 1;
							betrag += Math.abs(p.getBetrag());
						} else if (twin == 1) {
							betrag += Math.abs(p.getBetrag());
							categorydataset.addValue(betrag,
									einnahme_ausgabe_text, p.getBezeichnung());
							twin = 0;
							betrag = 0;
						} else
							categorydataset.addValue(Math.abs(p.getBetrag()),
									einnahme_ausgabe_text, p.getBezeichnung());
					} else {
						if (twin == 1) {
							betrag += Math.abs(p.getBetrag());
							categorydataset.addValue(betrag,
									einnahme_ausgabe_text, p.getBezeichnung());
							twin = 0;
							betrag = 0;
						} else
							categorydataset.addValue(Math.abs(p.getBetrag()),
									einnahme_ausgabe_text, p.getBezeichnung());
					}
				}
			}

		}
		return categorydataset;
	}

	/**
	 * Erstellung der Grafik f�r Balkendiagramm (Ausgaben) f�r die Methode
	 * "Kategorie_Balkendiagramm_Grafik()".
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChartBarAusgaben(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"Ausgaben nach Kategorien", "Kategorie", "Euro",
				(CategoryDataset) dataset);
		return chart;
	}

	/**
	 * Erstellung der Grafik f�r Balkendiagramm (Einnahmen) f�r die Methode
	 * "Kategorie_Balkendiagramm_Grafik()".
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChartBarEinnahmen(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"Einnahmen nach Kategorien", "Kategorie", "Euro",
				(CategoryDataset) dataset);
		return chart;
	}

	/**
	 * Hauptmethode f�r Balkendiagramme nach Kategorien jeweils f�r Einnahmen
	 * und Ausgaben mit Einf�gen der Grafik in Panel (GUI).
	 */
	private void Kategorie_Balkendiagramm_Grafik() {
		// Einf�gen des Balkendiagramms nach Kategorien von Einnahmen in Panel
		JFreeChart bar = createChartBarEinnahmen((CategoryDataset) createDataset_Auswahl3(0));
		chartpanel = new ChartPanel(bar);
		panel_2.add(chartpanel);
		// Einf�gen des Balkendiagramms nach Kategorien von Ausgaben in Panel
		JFreeChart bar2 = createChartBarAusgaben((CategoryDataset) createDataset_Auswahl3(1));
		chartpanel2 = new ChartPanel(bar2);
		panel_2.add(chartpanel2);
	}

	/**
	 * Erstellung der Grafik f�r Balkendiagramm (Einnahmen & Ausgaben) f�r die
	 * Methode "GesamtKategorie_Balkendiagramm_Grafik()".
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChartBarGesamt(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"Einnahmen und Ausgaben nach Kategorien", "Kategorie", "Euro",
				(CategoryDataset) dataset);
		return chart;
	}

	/**
	 * Hauptmethode f�r Balkendiagramme nach Kategorien f�r Einnahmen und
	 * Ausgaben zusammen mit Einf�gen der Grafik in Panel (GUI).
	 */
	private void GesamtKategorie_Balkendiagramm_Grafik() {
		JFreeChart bar = createChartBarGesamt((CategoryDataset) createDataset_Auswahl3(2));
		chartpanel = new ChartPanel(bar);
		panel_2.add(chartpanel);
	}

	/**
	 * Hauptmethode f�r Balkendiagramme nach Gesamt-Einnahmen und
	 * Gesamt-Ausgaben mit Einf�gen der Grafik in Panel (GUI). Zus�tzlich auch
	 * Erstellung der Grafik und Datens�tze.
	 */
	private void Vergleich_Balkendiagramm_Grafik() {
		// Erstellung der Datens�tze
		double einnahmen = 0, ausgaben = 0;
		cd = new DefaultCategoryDataset();
		for (Posten p : budget.Geldverm�gen)
			if (p.getintern_Einnahme_Ausgabe() == 0)
				einnahmen += p.getBetrag();
			else
				ausgaben += Math.abs(p.getBetrag());
		cd.addValue(einnahmen, "Einnahmen", "");
		cd.addValue(ausgaben, "Ausgaben", "");
		// Erstellung der Grafik
		JFreeChart bar = ChartFactory.createBarChart(
				"Gesamt-Einnahmen  vs  Gesamt-Ausgaben", null, "Euro",
				(CategoryDataset) cd);
		chartpanel = new ChartPanel(bar);
		// Einf�gung von Grafik in Panel
		panel_2.add(chartpanel);
	}

	/**
	 * Hauptmethode f�r Balken- und Liniendiagramm nach Zeit f�r Ausgaben mit
	 * Einf�gen der Grafik in Panel (GUI).
	 */
	private void Zeit_Kombidiagramm_Ausgaben_Grafik() {
		JFreeChart KombiChart_Ausgaben = createChart_Auswahl(1);
		chartpanel = new ChartPanel(KombiChart_Ausgaben);
		panel_2.add(chartpanel);
	}

	/**
	 * Hauptmethode f�r Balken- und Liniendiagramm nach Zeit f�r Einnahmen mit
	 * Einf�gen der Grafik in Panel (GUI).
	 */
	private void Zeit_Kombidiagramm_Einnahmen_Grafik() {
		JFreeChart KombiChart_Einnahmen = createChart_Auswahl(0);
		chartpanel = new ChartPanel(KombiChart_Einnahmen);
		panel_2.add(chartpanel);
	}

	/**
	 * Erstellung der Datens�tze f�r Balken- und Liniendiagramm f�r die Methoden
	 * "Zeit_Kombidiagramm_Einnahmen_Grafik()" &
	 * "Zeit_Kombidiagramm_Ausgaben_Grafik()". Durch ein Algorithmus werden
	 * gleiche Zeiten und somit die jeweiligen Betr�ge zusammengefasst und nicht
	 * mit dem letzten Wert �berschrieben, was eine fehlerhafte Grafik zur Folge
	 * h�tte.
	 * 
	 * @param selection_data_ein_aus
	 *            erh�lt den Wert 0 f�r Einnahmen oder 1 f�r Ausgaben
	 * @return fertig zusammengesetzte Datens�tze
	 */
	private CategoryDataset createDataset_Auswahl(int selection_data_ein_aus) {
		int einnahme_ausgabe = -1;
		String einnahme_ausgabe_text = null;
		// Abfrage des Parameterwerts mit Variableninitialisierung
		if (selection_data_ein_aus == 0) {
			einnahme_ausgabe = 0;
			einnahme_ausgabe_text = "Einnahmen";
		} else {
			einnahme_ausgabe = 1;
			einnahme_ausgabe_text = "Ausgaben";
		}
		// Hilfs-ArrayList mit nur reinen Einnahmen oder Ausgaben
		Only_Einnahmen_Or_Ausgaben = new ArrayList<Posten>();
		for (Posten p : budget.Geldverm�gen) {
			if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
				Only_Einnahmen_Or_Ausgaben.add(p);
			}
		}
		/*
		 * Algorithmus zur Zusammenfassung der gleichen Zeiten inkl. der
		 * jeweiligen Betr�ge
		 */
		double betrag = 0;
		int twin = 0, i = 0;
		defaultcategorydataset = new DefaultCategoryDataset();
		for (Posten p : Only_Einnahmen_Or_Ausgaben) {
			i++;
			if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
				if (i < Only_Einnahmen_Or_Ausgaben.size()) {
					if (Only_Einnahmen_Or_Ausgaben
							.get(i - 1)
							.getDatum()
							.equals(Only_Einnahmen_Or_Ausgaben.get(i)
									.getDatum())) {
						twin = 1;
						betrag += Math.abs(p.getBetrag());
					} else if (twin == 1) {
						betrag += Math.abs(p.getBetrag());
						defaultcategorydataset.addValue(betrag,
								einnahme_ausgabe_text, new SimpleDateFormat(
										"dd.MM.yyyy").format(p.getDatum()));
						twin = 0;
						betrag = 0;
					} else
						defaultcategorydataset.addValue(
								Math.abs(p.getBetrag()), einnahme_ausgabe_text,
								new SimpleDateFormat("dd.MM.yyyy").format(p
										.getDatum()));
				} else {
					if (twin == 1) {
						betrag += Math.abs(p.getBetrag());
						defaultcategorydataset.addValue(betrag,
								einnahme_ausgabe_text, new SimpleDateFormat(
										"dd.MM.yyyy").format(p.getDatum()));
						twin = 0;
						betrag = 0;
					} else
						defaultcategorydataset.addValue(
								Math.abs(p.getBetrag()), einnahme_ausgabe_text,
								new SimpleDateFormat("dd.MM.yyyy").format(p
										.getDatum()));
				}
			}
		}
		return defaultcategorydataset;
	}

	/**
	 * Erstellung der Grafik f�r Balken- und Liniendiagramm (Einnahmen &
	 * Ausgaben) f�r die Methoden "Zeit_Kombidiagramm_Einnahmen_Grafik()" &
	 * "Zeit_Kombidiagramm_Ausgaben_Grafik()".
	 * 
	 * @param selection_ein_aus
	 *            erh�lt den Wert 0 f�r Einnahmen oder 1 f�r Ausgaben
	 * @return fertige Grafik
	 */
	private JFreeChart createChart_Auswahl(int selection_ein_aus) {
		String einnahme_ausgabe_text = null;
		if (selection_ein_aus == 0)
			einnahme_ausgabe_text = "Einnahmen";
		else
			einnahme_ausgabe_text = "Ausgaben";
		// individuelle Grafikerstellung mit Hilfe von Renderer
		CategoryDataset categorydataset = createDataset_Auswahl(selection_ein_aus);
		numberaxis = new NumberAxis("Euro");
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		lineandshaperenderer = new LineAndShapeRenderer();
		lineandshaperenderer
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		categoryplot = new CategoryPlot(categorydataset, null, numberaxis,
				lineandshaperenderer);
		categoryplot.setDomainGridlinesVisible(true);
		numberaxis1 = new NumberAxis("Euro");
		numberaxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		barrenderer = new BarRenderer();
		barrenderer
				.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
		categoryplot1 = new CategoryPlot(categorydataset, null, numberaxis1,
				barrenderer);
		categoryplot1.setDomainGridlinesVisible(true);
		categoryaxis = new CategoryAxis("Zeit");
		combineddomaincategoryplot = new CombinedDomainCategoryPlot(
				categoryaxis);
		combineddomaincategoryplot.add(categoryplot, 2);
		combineddomaincategoryplot.add(categoryplot1, 1);
		jfreechart = new JFreeChart(einnahme_ausgabe_text
				+ " nach Zeit - Kombidiagramm", new Font("SansSerif", 1, 12),
				combineddomaincategoryplot, true);
		return jfreechart;
	}

	/**
	 * Erstellung der Datens�tze f�r Liniendiagramm f�r die Methoden
	 * "Zeit_Liniendiagramm_Differenz_Gesamt_Grafik()" &
	 * "Zeit_Liniendiagramm_Gesamt_Grafik()". Durch ein Algorithmus werden
	 * gleiche Zeiten und somit die jeweiligen Betr�ge zusammengefasst und nicht
	 * mit dem letzten Wert �berschrieben, was eine fehlerhafte Grafik zur Folge
	 * h�tte.
	 * 
	 * @param selection_model
	 *            0 f�r "Zeit_Liniendiagramm_Differenz_Gesamt_Grafik()" & 1 f�r
	 *            "Zeit_Liniendiagramm_Gesamt_Grafik()"
	 * @return fertig zusammengesetzte Datens�tze
	 */
	private XYDataset createDataset(int selection_model) {
		series1 = new TimeSeries("Einnahmen");
		series2 = new TimeSeries("Ausgaben");
		// Durchf�hrung f�r "Zeit_Liniendiagramm_Differenz_Gesamt_Grafik()"
		if (selection_model == 0) {
			for (int einnahme_ausgabe = 0; einnahme_ausgabe < 2; einnahme_ausgabe++) {
				// Hilfs-ArrayList mit nur reinen Einnahmen oder Ausgaben
				Only_Einnahmen_Or_Ausgaben = new ArrayList<Posten>();
				for (Posten p : budget.Geldverm�gen) {
					if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
						Only_Einnahmen_Or_Ausgaben.add(p);
					}
				}
				/*
				 * Algorithmus zur Zusammenfassung der gleichen Zeiten inkl. der
				 * jeweiligen Betr�ge
				 */
				double betrag = 0;
				int twin = 0, i = 0;
				for (Posten p : Only_Einnahmen_Or_Ausgaben) {
					i++;
					if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
						if (i < Only_Einnahmen_Or_Ausgaben.size()) {
							if (Only_Einnahmen_Or_Ausgaben
									.get(i - 1)
									.getDatum()
									.equals(Only_Einnahmen_Or_Ausgaben.get(i)
											.getDatum())) {
								twin = 1;
								betrag += Math.abs(p.getBetrag());

							} else if (twin == 1) {
								betrag += Math.abs(p.getBetrag());
								if (einnahme_ausgabe == 0)
									series1.add(new Day(p.getDatum()), betrag);
								else
									series2.add(new Day(p.getDatum()), betrag);
								twin = 0;
								betrag = 0;
							} else if (einnahme_ausgabe == 0)
								series1.add(new Day(p.getDatum()),
										Math.abs(p.getBetrag()));
							else
								series2.add(new Day(p.getDatum()),
										Math.abs(p.getBetrag()));
						} else {
							if (twin == 1) {
								betrag += Math.abs(p.getBetrag());
								if (einnahme_ausgabe == 0)
									series1.add(new Day(p.getDatum()), betrag);
								else
									series2.add(new Day(p.getDatum()), betrag);
								twin = 0;
								betrag = 0;
							} else if (einnahme_ausgabe == 0)
								series1.add(new Day(p.getDatum()),
										Math.abs(p.getBetrag()));
							else
								series2.add(new Day(p.getDatum()),
										Math.abs(p.getBetrag()));
						}
					}
				}
			}
		} // Durchf�hrung f�r "Zeit_Liniendiagramm_Gesamt_Grafik()"
		else {
			for (int einnahme_ausgabe = 0; einnahme_ausgabe < 2; einnahme_ausgabe++) {
				Only_Einnahmen_Or_Ausgaben = new ArrayList<Posten>();
				for (Posten p : budget.Geldverm�gen) {
					if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
						Only_Einnahmen_Or_Ausgaben.add(p);
					}
				}
				double betrag = 0;
				int twin = 0, i = 0;
				for (Posten p : Only_Einnahmen_Or_Ausgaben) {
					i++;
					if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
						if (i < Only_Einnahmen_Or_Ausgaben.size()) {
							if (new SimpleDateFormat("MM.yyyy").format(
									Only_Einnahmen_Or_Ausgaben.get(i - 1)
											.getDatum()).equals(
									new SimpleDateFormat("MM.yyyy")
											.format(Only_Einnahmen_Or_Ausgaben
													.get(i).getDatum()))) {
								twin = 1;
								betrag += Math.abs(p.getBetrag());

							} else if (twin == 1) {
								betrag += Math.abs(p.getBetrag());
								if (einnahme_ausgabe == 0)
									series1.add(
											new Month(
													Integer.valueOf((new SimpleDateFormat(
															"MM").format(p
															.getDatum()))
															.toString()),
													Integer.valueOf((new SimpleDateFormat(
															"yyyy").format(p
															.getDatum()))
															.toString())),
											betrag);
								else
									series2.add(
											new Month(
													Integer.valueOf((new SimpleDateFormat(
															"MM").format(p
															.getDatum()))
															.toString()),
													Integer.valueOf((new SimpleDateFormat(
															"yyyy").format(p
															.getDatum()))
															.toString())),
											betrag);
								twin = 0;
								betrag = 0;
							} else if (einnahme_ausgabe == 0)
								series1.add(
										new Month(Integer
												.valueOf((new SimpleDateFormat(
														"MM").format(p
														.getDatum()))
														.toString()), Integer
												.valueOf((new SimpleDateFormat(
														"yyyy").format(p
														.getDatum()))
														.toString())), Math
												.abs(p.getBetrag()));
							else
								series2.add(
										new Month(Integer
												.valueOf((new SimpleDateFormat(
														"MM").format(p
														.getDatum()))
														.toString()), Integer
												.valueOf((new SimpleDateFormat(
														"yyyy").format(p
														.getDatum()))
														.toString())), Math
												.abs(p.getBetrag()));
						} else {
							if (twin == 1) {
								betrag += Math.abs(p.getBetrag());
								if (einnahme_ausgabe == 0)
									series1.add(
											new Month(
													Integer.valueOf((new SimpleDateFormat(
															"MM").format(p
															.getDatum()))
															.toString()),
													Integer.valueOf((new SimpleDateFormat(
															"yyyy").format(p
															.getDatum()))
															.toString())),
											betrag);
								else
									series2.add(
											new Month(
													Integer.valueOf((new SimpleDateFormat(
															"MM").format(p
															.getDatum()))
															.toString()),
													Integer.valueOf((new SimpleDateFormat(
															"yyyy").format(p
															.getDatum()))
															.toString())),
											betrag);
								twin = 0;
								betrag = 0;
							} else if (einnahme_ausgabe == 0)
								series1.add(
										new Month(Integer
												.valueOf((new SimpleDateFormat(
														"MM").format(p
														.getDatum()))
														.toString()), Integer
												.valueOf((new SimpleDateFormat(
														"yyyy").format(p
														.getDatum()))
														.toString())), Math
												.abs(p.getBetrag()));
							else
								series2.add(
										new Month(Integer
												.valueOf((new SimpleDateFormat(
														"MM").format(p
														.getDatum()))
														.toString()), Integer
												.valueOf((new SimpleDateFormat(
														"yyyy").format(p
														.getDatum()))
														.toString())), Math
												.abs(p.getBetrag()));
						}
					}
				}
			}
		}
		dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);
		return dataset;
	}

	/**
	 * Erstellung der Grafik f�r Liniendiagramm (Differenz) (Einnahmen &
	 * Ausgaben) f�r die Methode "Zeit_Liniendiagramm_Differenz_Gesamt_Grafik()"
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Differenz zw. Einnahmen & Ausgaben", "Zeit", "Euro", dataset,
				true, true, false);
		// Grafikerstellung mit Hilfe von Renderer
		XYPlot plot = chart.getXYPlot();
		plot.setRenderer(new XYDifferenceRenderer(Color.green, Color.red, false));
		domainAxis = new DateAxis("Zeit");
		domainAxis.setLowerMargin(0.0);
		domainAxis.setUpperMargin(0.0);
		plot.setDomainAxis(domainAxis);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}

	/**
	 * Hauptmethode f�r Liniendiagramme (Differenz) nach Zeit (monatlich) f�r
	 * Einnahmen und Ausgaben gemeinsam mit Einf�gen der Grafik in Panel (GUI).
	 */
	private void Zeit_Liniendiagramm_Differenz_Gesamt_Grafik() {
		chart = createChart(createDataset(0));
		chartpanel = new ChartPanel(chart);
		panel_2.add(chartpanel);
	}

	/**
	 * Erstellung der Grafik f�r Liniendiagramm (Einnahmen & Ausgaben) f�r die
	 * Methode "Zeit_Liniendiagramm_Gesamt_Grafik()"
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChart2(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Einnahmen  vs  Ausgaben (monatlich)", "Datum", "Euro",
				dataset, true, true, false);
		// Grafikerstellung mit Hilfe von Renderer
		XYPlot plot = (XYPlot) chart.getPlot();
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		return chart;
	}

	/**
	 * Hauptmethode f�r Liniendiagramme nach Zeit (monatlich) f�r Einnahmen und
	 * Ausgaben gemeinsam mit Einf�gen der Grafik in Panel (GUI).
	 */
	private void Zeit_Liniendiagramm_Gesamt_Grafik() {
		chart = createChart2(createDataset(1));
		chartpanel = new ChartPanel(chart);
		panel_2.add(chartpanel);
	}

	/**
	 * Erstellung der Datens�tze f�r Wasserfalldiagramm f�r die Methode
	 * "Kategorie_Wasserfalldiagramm_Gesamt_Grafik()". Durch ein Algorithmus
	 * werden gleiche Kategorien und somit die jeweiligen Betr�ge
	 * zusammengefasst und nicht mit dem letzten Wert �berschrieben, was eine
	 * fehlerhafte Grafik zur Folge h�tte.
	 * 
	 * @param selection_data_ein_aus
	 *            erh�lt den Wert 0 f�r Einnahmen oder 1 f�r Ausgaben
	 * @return fertig zusammengesetzte Datens�tze
	 */
	private CategoryDataset createDataset_Wasserfall(int selection_data_ein_aus) {
		double m = 0;
		int einnahme_ausgabe = -1;
		String einnahme_ausgabe_text = null;
		// Abfrage des Parameterwerts mit Variableninitialisierung
		if (selection_data_ein_aus == 0) {
			einnahme_ausgabe = 0;
			einnahme_ausgabe_text = "Einnahmen";
		} else {
			einnahme_ausgabe = 1;
			einnahme_ausgabe_text = "Ausgaben";
		}
		// Erstellung einer Hilfs-ArrayList
		Sortierte_Kategorie_Liste = new ArrayList<Posten>();
		for (Posten p : budget.Geldverm�gen)
			Sortierte_Kategorie_Liste.add(p);
		// Sortierung der Datens�tze nach Kategorie
		Collections.sort(Sortierte_Kategorie_Liste, new Comparator<Posten>() {
			@Override
			public int compare(Posten o1, Posten o2) {
				return o1.getBezeichnung().compareTo(o2.getBezeichnung());
			}
		});
		/*
		 * Algorithmus zur Zusammenfassung der gleichen Kategorien inkl. der
		 * jeweiligen Betr�ge
		 */
		double betrag = 0;
		int twin = 0, i = 0;
		defaultcategorydataset = new DefaultCategoryDataset();
		for (Posten p : Sortierte_Kategorie_Liste) {
			i++;
			if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe) {
				if (i < Sortierte_Kategorie_Liste.size()) {
					if (Sortierte_Kategorie_Liste
							.get(i - 1)
							.getBezeichnung()
							.equals(Sortierte_Kategorie_Liste.get(i)
									.getBezeichnung())) {
						twin = 1;
						betrag += p.getBetrag();

					} else if (twin == 1) {
						betrag += p.getBetrag();
						defaultcategorydataset.addValue(betrag,
								einnahme_ausgabe_text, p.getBezeichnung());
						twin = 0;
						betrag = 0;
					} else
						defaultcategorydataset.addValue(p.getBetrag(),
								einnahme_ausgabe_text, p.getBezeichnung());
				} else {
					if (twin == 1) {
						betrag += p.getBetrag();
						defaultcategorydataset.addValue(betrag,
								einnahme_ausgabe_text, p.getBezeichnung());
						twin = 0;
						betrag = 0;
					} else
						defaultcategorydataset.addValue(p.getBetrag(),
								einnahme_ausgabe_text, p.getBezeichnung());
				}
			}
		}
		// letzte "Kategorie" (Total) sind Gesamtbetr�ge
		for (Posten p : Sortierte_Kategorie_Liste)
			if (p.getintern_Einnahme_Ausgabe() == einnahme_ausgabe)
				m += p.getBetrag();
		defaultcategorydataset.addValue(m, einnahme_ausgabe_text, "Total");
		return defaultcategorydataset;
	}

	/**
	 * Erstellung der Grafik f�r Wasserfalldiagramm (Einnahmen) f�r die Methode
	 * "Kategorie_Wasserfalldiagramm_Gesamt_Grafik()".
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChartWasserfall_Einnahmen(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createWaterfallChart(
				"Einnahmen nach Kategorien", "Kategorie", "Euro", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setForegroundAlpha(0.8f);
		return chart;
	}

	/**
	 * Erstellung der Grafik f�r Wasserfalldiagramm (Ausgaben) f�r die Methode
	 * "Kategorie_Wasserfalldiagramm_Gesamt_Grafik()".
	 * 
	 * @param dataset
	 *            Die Datens�tze
	 * @return fertige Grafik
	 */
	private JFreeChart createChartWasserfall_Ausgaben(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createWaterfallChart(
				"Ausgaben nach Kategorien", "Kategorie", "Euro", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setForegroundAlpha(0.8f);
		return chart;
	}

	/**
	 * Hauptmethode f�r Wasserfalldiagramme nach Kategorie jeweils f�r Einnahmen
	 * und Ausgabe mit Einf�gen der Grafik in Panel (GUI).
	 */
	private void Kategorie_Wasserfalldiagramm_Gesamt_Grafik() {
		// Einf�gen des Wasserfalldiagramms nach Kategorien von Einnahmen in Panel
		chart = createChartWasserfall_Einnahmen((CategoryDataset) createDataset_Wasserfall(0));
		chartpanel = new ChartPanel(chart);
		panel_2.add(chartpanel);
		// Einf�gen des Wasserfalldiagramms nach Kategorien von Ausgaben in Panel
		chart2 = createChartWasserfall_Ausgaben((CategoryDataset) createDataset_Wasserfall(1));
		chartpanel2 = new ChartPanel(chart2);
		panel_2.add(chartpanel2);
	}

	//
	// Hauptmethode zur Koordination der ganzen Methoden
	//

	/**
	 * Hauptmethode zur Koordination der f�r die Statistik notwendigen
	 * Komponenten. Je nach Auswahl ob "Gesamtzeitraum" oder
	 * "individueller Zeitraum", werden spezielle Methoden aufgerufen. Dabei
	 * werden zun�chst die Datens�tze wenn n�tig (individueller Zeitraum)
	 * aktualisiert. Dann werden die Tabellen und die K�rz�bersicht erstellt.
	 * Zum Schluss werden die speziellen Methoden der vom Benutzer ausgew�hlten
	 * Statistik-Modelle aufgerufen und die Grafiken erstellt.
	 * 
	 * @param selection
	 *            gew�hlte Grafik-Modell der Statistik
	 * @param Start
	 *            Anfangsdatum der Benutzereingabe bei individuellem Zeitraum
	 * @param End
	 *            Enddatum der Benutzereingabe bei individuellem Zeitraum
	 */
	public void Statistik_Manager(String selection, String Start, String End) {
		// Vorgehen bei individuell gew�hltem Zeitraum
		if ((Start != "0") && (End != "0")) {
			lblZeitraum.setText("  Zeitraum:  " + Start + " - " + End);
			Init_Posten_Zeitraum(Start, End);
			// Grafiken
			switch (selection) {
			case "Kategorie_Kreisdiagramm":
				Kategorie_Kreisdiagramm_Grafik();
				break;
			case "Kategorie_Balkendiagramm":
				Kategorie_Balkendiagramm_Grafik();
				break;
			case "GesamtKategorie_Balkendiagramm":
				GesamtKategorie_Balkendiagramm_Grafik();
				break;
			case "Zeit_Kombidiagramm_Einnahmen":
				Zeit_Kombidiagramm_Einnahmen_Grafik();
				break;
			case "Zeit_Kombidiagramm_Ausgaben":
				Zeit_Kombidiagramm_Ausgaben_Grafik();
				break;
			case "Vergleich_Balkendiagramm":
				Vergleich_Balkendiagramm_Grafik();
				break;
			case "Zeit_Liniendiagramm_Differenz_Gesamt":
				Zeit_Liniendiagramm_Differenz_Gesamt_Grafik();
				break;
			case "Zeit_Liniendiagramm_Gesamt":
				Zeit_Liniendiagramm_Gesamt_Grafik();
				break;
			case "Kategorie_Wasserfalldiagramm":
				Kategorie_Wasserfalldiagramm_Gesamt_Grafik();
				break;
			}
		} // Vorgehen bei gew�hltem Gesamtzeitraum
		else {
			lblZeitraum.setText("  Zeitraum:  Gesamtzeitraum");
			Buchungen_ohne_Kontoer�ffnung();
			Buchungs�bersicht();
			// Grafiken
			switch (selection) {
			case "Kategorie_Kreisdiagramm":
				Kategorie_Kreisdiagramm_Grafik();
				break;
			case "Kategorie_Balkendiagramm":
				Kategorie_Balkendiagramm_Grafik();
				break;
			case "GesamtKategorie_Balkendiagramm":
				GesamtKategorie_Balkendiagramm_Grafik();
				break;
			case "Zeit_Kombidiagramm_Einnahmen":
				Zeit_Kombidiagramm_Einnahmen_Grafik();
				break;
			case "Zeit_Kombidiagramm_Ausgaben":
				Zeit_Kombidiagramm_Ausgaben_Grafik();
				break;
			case "Vergleich_Balkendiagramm":
				Vergleich_Balkendiagramm_Grafik();
				break;
			case "Zeit_Liniendiagramm_Differenz_Gesamt":
				Zeit_Liniendiagramm_Differenz_Gesamt_Grafik();
				break;
			case "Zeit_Liniendiagramm_Gesamt":
				Zeit_Liniendiagramm_Gesamt_Grafik();
				break;
			case "Kategorie_Wasserfalldiagramm":
				Kategorie_Wasserfalldiagramm_Gesamt_Grafik();
				break;
			}
		}
	}
}
