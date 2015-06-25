import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

import com.opencsv.CSVReader;

import java.awt.Font;

public class Statistik {

	JFrame Statistic = new JFrame("MyBudgetManager  -- Statistik --    ["
			+ new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss").format(new Date())
			+ "]");

	private JScrollPane scrollPane1;
	private static final long serialVersionUID = 1L;
	private BudgetPlanModel budget;
	private JScrollPane scrollPane_Einnahmen;
	private JScrollPane scrollPane_Ausgaben;
	private int size_Einnahmen;
	private int size_Ausgaben;
	private JPanel panel;
	private JPanel panel_3;
	private JPanel panel_4;
	private JLabel lblKontoübersicht_Einnahmen;
	private JLabel lblKontoübersicht_Ausgaben;
	private JPanel panel_1;
	private JLabel lblKurzübersicht;
	private JPanel panel_2;
	private JLabel lblBuchungen;
	private JLabel lblEinnahmen;
	private JLabel lblAusgaben;
	private JLabel lblSaldo;
	private JLabel lblBuchungswert;
	private JLabel lblEinnhamenwert;
	private JLabel lblAusgabenwert;
	private JLabel lblSaldowert;

	public Statistik(BudgetPlanModel budget) {
		Statistic.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Statistic.setBounds(100, 100, 450, 300); // Groesse des Frames
		Statistic.setVisible(true);
		Statistic.setMinimumSize(new Dimension(800, 550));
		Statistic.getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		Statistic.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(0, 180));
		panel.setLayout(new BorderLayout(0, 0));

		panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(1, 2));

		scrollPane_Einnahmen = new JScrollPane();
		panel_3.add(scrollPane_Einnahmen);

		scrollPane_Ausgaben = new JScrollPane();
		panel_3.add(scrollPane_Ausgaben);

		panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new GridLayout(1, 2));

		lblKontoübersicht_Einnahmen = new JLabel(
				"Konto\u00FCbersicht:  Einnahmen:");
		panel_4.add(lblKontoübersicht_Einnahmen);

		lblKontoübersicht_Ausgaben = new JLabel("Ausgaben:");
		panel_4.add(lblKontoübersicht_Ausgaben);

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setPreferredSize(new Dimension(270, 180));
		panel_1.setLayout(null);

		lblKurzübersicht = new JLabel("Kurz\u00FCbersicht:");
		lblKurzübersicht.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblKurzübersicht.setBounds(73, 5, 96, 14);
		panel_1.add(lblKurzübersicht);

		lblBuchungen = new JLabel("Buchungen:");
		lblBuchungen.setBounds(10, 40, 74, 14);
		panel_1.add(lblBuchungen);

		lblEinnahmen = new JLabel("Einnahmen:");
		lblEinnahmen.setBounds(10, 70, 74, 14);
		panel_1.add(lblEinnahmen);

		lblAusgaben = new JLabel("Ausgaben:");
		lblAusgaben.setBounds(10, 100, 74, 14);
		panel_1.add(lblAusgaben);

		lblSaldo = new JLabel("Saldo:");
		lblSaldo.setBounds(10, 135, 46, 14);
		panel_1.add(lblSaldo);

		lblBuchungswert = new JLabel("");
		lblBuchungswert.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBuchungswert.setBounds(94, 40, 150, 17);
		panel_1.add(lblBuchungswert);

		lblEinnhamenwert = new JLabel("");
		lblEinnhamenwert.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEinnhamenwert.setBounds(94, 70, 140, 14);
		panel_1.add(lblEinnhamenwert);

		lblAusgabenwert = new JLabel("");
		lblAusgabenwert.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAusgabenwert.setBounds(94, 100, 140, 14);
		panel_1.add(lblAusgabenwert);

		lblSaldowert = new JLabel("");
		lblSaldowert.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaldowert.setBounds(94, 135, 140, 14);
		panel_1.add(lblSaldowert);

		panel_2 = new JPanel();
		Statistic.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(1, 2));

		this.budget = budget;

	}

	public void Buchungsübersicht() {

		for (Posten pos : budget.Geldvermögen) {
			if (pos.getintern_Einnahme_Ausgabe() == 0)
				size_Einnahmen += 1;
		}

		for (Posten po : budget.Geldvermögen) {
			if (po.getintern_Einnahme_Ausgabe() == 1)
				size_Ausgaben += 1;
		}

		Object[][] data_Ausgaben = new Object[size_Ausgaben][4];
		Object[][] data_Einnahmen = new Object[size_Einnahmen][4];
		int i = 0, j = 0;

		for (Posten p : budget.Geldvermögen) {
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

		JTable table_Einnahmen = new JTable(data_Einnahmen, new Object[] {
				"Datum", "Kategorie", "Beschreibung", "Betrag" });
		table_Einnahmen.enable(false);
		table_Einnahmen.setAutoCreateRowSorter(true);
		table_Einnahmen.setBackground(new Color(180, 250, 200));
		scrollPane_Einnahmen.setViewportView(table_Einnahmen);

		JTable table_Ausgaben = new JTable(data_Ausgaben, new Object[] {
				"Datum", "Kategorie", "Beschreibung", "Betrag" });
		table_Ausgaben.enable(false);
		table_Ausgaben.setAutoCreateRowSorter(true);
		table_Ausgaben.setBackground(new Color(250, 210, 210));
		scrollPane_Ausgaben.setViewportView(table_Ausgaben);

		Init_Kurzübersicht();
	}

	public void Init_Kurzübersicht() {
		NumberFormat nf = NumberFormat.getInstance(new Locale("de", "DE"));
		double i = 0, a = 0, e = 0;
		lblBuchungswert.setText((size_Ausgaben + size_Einnahmen) + "   [Ein ("
				+ size_Einnahmen + ") " + ", Aus (" + size_Ausgaben + ")]");
		for (Posten p : budget.Geldvermögen) {
			i += p.getBetrag();
			if (p.getintern_Einnahme_Ausgabe() == 0)
				e += p.getBetrag();
			else
				a += p.getBetrag();
		}
		lblSaldowert.setText(nf.format(i) + " \u20ac");
		lblEinnhamenwert.setText(nf.format(e) + " \u20ac");
		lblEinnhamenwert.setForeground(new Color(20, 170, 20));
		lblAusgabenwert.setText(nf.format(a) + " \u20ac");
		lblAusgabenwert.setForeground(Color.RED);
		if (i < 0)
			lblSaldowert.setForeground(Color.RED);
		else
			lblSaldowert.setForeground(new Color(20, 170, 20));
	}

	public void Init_Posten_Zeitraum(String StartingDate, String EndingDate) {

		budget.Geldvermögen.clear();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Date startDate = null;
		Date endDate = null;
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

		try {
			// Zeilenweises Einlesen der Daten
			CSVReader reader = new CSVReader(new FileReader("data/budget.csv"));
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
					Locale.GERMAN);

			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				start.setTime(startDate);
				for (Date date = start.getTime(); !start.after(end); start.add(
						Calendar.DATE, 1), date = start.getTime()) {
					if (formatter.format(date).toString().equals(nextLine[0])) {
						Date datum = df.parse(nextLine[0]);
						String notiz = nextLine[1];
						String bezeichnung = nextLine[2];
						double betrag = Double.parseDouble(nextLine[3]);
						int intern_Einnahme_Ausgabe = Integer
								.parseInt(nextLine[4]);
						budget.Geldvermögen.add(new Posten(datum, notiz,
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

		Buchungsübersicht();
	}

	public void Kategorie_Kreisdiagramm_Grafik() {
		DefaultPieDataset pd = new DefaultPieDataset();
		for (Posten p : budget.Geldvermögen)
			if (p.getintern_Einnahme_Ausgabe() == 0)
				pd.setValue(p.getBezeichnung(), p.getBetrag());

		JFreeChart pie = ChartFactory.createPieChart3D("Einnahmen", pd);
		ChartPanel chartpanel = new ChartPanel(pie);
		panel_2.add(chartpanel);

		DefaultPieDataset pd2 = new DefaultPieDataset();
		for (Posten p : budget.Geldvermögen)
			if (p.getintern_Einnahme_Ausgabe() == 1)
				pd2.setValue(p.getBezeichnung(), Math.abs(p.getBetrag()));

		JFreeChart pie2 = ChartFactory.createPieChart3D("Ausgaben", pd2);
		ChartPanel chartpanel2 = new ChartPanel(pie2);
		panel_2.add(chartpanel2);

	}

	public void Statistik_Manager(String selection, String Start, String End) {
		if ((Start != "0") && (End != "0")) {
			Init_Posten_Zeitraum(Start, End);
			switch (selection) {
			case "Kategorie_Kreisdiagramm":
				Kategorie_Kreisdiagramm_Grafik();
				break;
			case "Kategorie_Balkendiagramm":
				Kategorie_Balkendiagramm_Grafik();
				break;
			}
		} else {
			Buchungsübersicht();
			switch (selection) {
			case "Kategorie_Kreisdiagramm":
				Kategorie_Kreisdiagramm_Grafik();
				break;
			case "Kategorie_Balkendiagramm":
				Kategorie_Balkendiagramm_Grafik();
				break;
			}
		}
	}

	public void Kategorie_Balkendiagramm_Grafik() {
		DefaultCategoryDataset cd = new DefaultCategoryDataset();
		for (Posten p : budget.Geldvermögen)
			if (p.getintern_Einnahme_Ausgabe() == 0)
				cd.addValue(p.getBetrag(), "Einnahmen", p.getBezeichnung());

		JFreeChart bar = ChartFactory.createBarChart(
				"Einnahmen nach Kategorien", null, null, (CategoryDataset) cd);
		ChartPanel chartpanel = new ChartPanel(bar);
		panel_2.add(chartpanel);

		DefaultCategoryDataset cd2 = new DefaultCategoryDataset();
		for (Posten p : budget.Geldvermögen)
			if (p.getintern_Einnahme_Ausgabe() == 1)
				cd2.addValue(Math.abs(p.getBetrag()), "Ausgaben",
						p.getBezeichnung());

		JFreeChart bar2 = ChartFactory.createBarChart(
				"Ausgaben nach Kategorien", null, null, (CategoryDataset) cd2);
		ChartPanel chartpanel2 = new ChartPanel(bar2);
		panel_2.add(chartpanel2);

	}

}

