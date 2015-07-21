import javax.swing.JOptionPane;

/**
 * Methoden für die Sparfunktion.
 * Werte aus Checkdata_sparfkt empfangen und verwerten.
 */

public class Sparfunktion {

	static double zinsertrag = 0.00;
	static double ergebnis = 0.00;
	static double sparwert = 0.00;
	static double summezinsertrag = 0.00;
	static double summesparrate = 0.00;

	// Berechnet gesamten Sparwert und Endergebnis wenn man keine Verzinsung
	// wünscht
	static void berechne_SparfktOhneZinsen(double startwert, double sparrate,
			int zeitraum) {
		sparwert = sparrate * zeitraum;
		ergebnis = startwert + sparwert;

		JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
				+ zeitraum + " Monat(en) beträgt : " + ergebnis + "\n"
				+ "Ihre gesamten Sparzugaben betragen dabei : " + sparwert,
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
	}

	// Berechnet gesamten Sparwert,gesamten Zinsertrag und Endergebnis mit einer
	// (automatisch) einmaligen Verzinsung einer einmaligen Sparrate
	static void berechne_SparfktMitZinsenEinmalig(double startwert,
			double sparrate, int zeitraum, double zinssatz) {
		double zinsertrag = (startwert + sparrate) * zinssatz / 100;
		double ergebnis = startwert + sparrate + zinsertrag;
		JOptionPane.showMessageDialog(
				null,
				"" + "Ihr Guthaben nach " + zeitraum + " Monat(en) beträgt : "
						+ (Math.round((ergebnis) * 100) / 100.0) + "\n"
						+ "Ihre gesamten Sparzugaben betragen dabei : "
						+ sparrate + "\n"
						+ "Ihre gesamte Zinszuschrift beträgt dabei : "
						+ (Math.round((zinsertrag) * 100) / 100.0), "Ergebnis",
				JOptionPane.INFORMATION_MESSAGE);

	}
	// Berechnet gesamten Sparwert,gesamten Zinsertrag und Endergebnis mit einer
	// periodischen Verzinsung jeweils am Monatsende bei monatlicher Sparrate
	static void berechne_SparfktMitZinsenMonatlich(double startwert,
			double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag = 0.00;
		summesparrate = 0.00;
		for (int i = 1; i <= zeitraum; i++) {
			zinsertrag = (startwert + sparrate) * zinssatz / 100;
			summezinsertrag = summezinsertrag + zinsertrag;
			summesparrate = summesparrate + sparrate;
			startwert = startwert + sparrate + zinsertrag;

		}
		JOptionPane.showMessageDialog(
				null,
				"" + "Ihr Guthaben nach " + zeitraum + " Monat(en) beträgt : "
						+ (Math.round((startwert) * 100) / 100.0) + "\n"
						+ "Ihre gesamten Sparzugaben betragen dabei : "
						+ summesparrate + "\n"
						+ "Ihre gesamte Zinszuschrift beträgt dabei : "
						+ (Math.round((summezinsertrag) * 100) / 100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);

	}

	// Berechnet gesamten Sparwert,gesamten Zinsertrag und Endergebnis mit einer
	// periodischen Verzinsung jeweils am Jahresende
	public static void berechne_SparfktMitZinsenJaehrlichJahresbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag = 0.00;
		summesparrate = 0.00;
		for (int i = 1; i <= zeitraum; i++) {
			startwert = startwert + sparrate * 12;
			zinsertrag = startwert * zinssatz / 100;
			startwert = startwert + zinsertrag;
			summezinsertrag = summezinsertrag + zinsertrag;
		}
		summesparrate = sparrate * zeitraum * 12;

		JOptionPane.showMessageDialog(
				null,
				"" + "Ihr Guthaben nach " + zeitraum + " Jahr(en) beträgt : "
						+ (Math.round((startwert) * 100) / 100.0) + "\n"
						+ "Ihre gesamten Sparzugaben betragen dabei : "
						+ summesparrate + "\n"
						+ "Ihre gesamte Zinszuschrift beträgt dabei : "
						+ (Math.round((summezinsertrag) * 100) / 100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);

	}

	// Berechnet gesamten Sparwert,gesamten Zinsertrag und Endergebnis mit einer
	// periodischen Verzinsung jeweils am Ende eines halben Jahres
	public static void berechne_SparfktMitZinsenJaehrlichHalbjahresbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag = 0.00;
		summesparrate = 0.00;
		for (int i = 1; i <= zeitraum * 12; i++) {
			startwert = startwert + sparrate;
			if (i % 6 == 0) {
				zinsertrag = startwert * zinssatz / 100;
				startwert = startwert + zinsertrag;
				summezinsertrag = summezinsertrag + zinsertrag;
			}
			summesparrate = summesparrate + sparrate;
		}
		JOptionPane.showMessageDialog(
				null,
				"" + "Ihr Guthaben nach " + zeitraum + " Jahr(en) beträgt : "
						+ (Math.round((startwert) * 100) / 100.0) + "\n"
						+ "Ihre gesamten Sparzugaben betragen dabei : "
						+ summesparrate + "\n"
						+ "Ihre gesamte Zinszuschrift beträgt dabei : "
						+ (Math.round((summezinsertrag) * 100) / 100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
	}

	// Berechnet gesamten Sparwert,gesamten Zinsertrag und Endergebnis mit einer
	// periodischen Verzinsung jeweils am Ende eines viertel Jahres
	public static void berechne_SparfktMitZinsenJaehrlichQuartalsbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag = 0.00;
		summesparrate = 0.00;
		for (int i = 1; i <= zeitraum * 12; i++) {
			startwert = startwert + sparrate;
			if (i % 3 == 0) {
				zinsertrag = startwert * zinssatz / 100;
				startwert = startwert + zinsertrag;
				summezinsertrag = summezinsertrag + zinsertrag;
			}
			summesparrate = summesparrate + sparrate;
		}
		JOptionPane.showMessageDialog(
				null,
				"" + "Ihr Guthaben nach " + zeitraum + " Jahr(en) beträgt : "
						+ (Math.round((startwert) * 100) / 100.0) + "\n"
						+ "Ihre gesamten Sparzugaben betragen dabei : "
						+ summesparrate + "\n"
						+ "Ihre gesamte Zinszuschrift beträgt dabei : "
						+ (Math.round((summezinsertrag) * 100) / 100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);

	}
	// Berechnet gesamten Sparwert,gesamten Zinsertrag und Endergebnis mit einer
	// periodischen Verzinsung jeweils am Ende eines Monats über eine gewünschte Anzahl von Jahren hinweg
	public static void berechne_SparfktMitZinsenJaehrlichMonatsbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag = 0.00;
		summesparrate = 0.00;
		for (int i = 1; i <= zeitraum * 12; i++) {
			startwert = startwert + sparrate;
			zinsertrag = startwert * zinssatz / 100;
			startwert = startwert + zinsertrag;

			summezinsertrag = summezinsertrag + zinsertrag;
			summesparrate = summesparrate + sparrate;
		}
		JOptionPane.showMessageDialog(
				null,
				"" + "Ihr Guthaben nach " + zeitraum + " Jahr(en) beträgt : "
						+ (Math.round((startwert) * 100) / 100.0) + "\n"
						+ "Ihre gesamten Sparzugaben betragen dabei : "
						+ summesparrate + "\n"
						+ "Ihre gesamte Zinszuschrift beträgt dabei : "
						+ (Math.round((summezinsertrag) * 100) / 100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);

	}
}
