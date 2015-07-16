import java.text.DecimalFormat;

import javax.swing.JOptionPane;

public class Sparfunktion {
	
	static double zwischenwert = 0.00;
	static double zinsertrag = 0.00;
	static double ergebnis = 0.00;
	static double sparwert = 0.00;
	static double summezinsertrag=0.00;
	static double summesparrate=0.00;

	static void berechne_SparfktOhneZinsen(double startwert, double sparrate,
			int zeitraum) {
		/**
		 * Methode für die Sparfunktion / Werte aus den Textfeldern auslesen und
		 * berechnen
		 */
		DecimalFormat df = new DecimalFormat("#0.00");
		
		sparwert = sparrate * zeitraum;
		ergebnis = startwert + sparwert;

		JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
				+ zeitraum + " Monat(en) beträgt : " + ergebnis + "\n"
				+ "Ihre gesamten Sparzugaben betragen dabei : " + sparwert,
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
	}

	static void berechne_SparfktMitZinsenEinmalig(double startwert,
			double sparrate, int zeitraum, double zinssatz) {
		double zinsertrag=(startwert+sparrate)*zinssatz/100;
		double ergebnis=startwert+sparrate+zinsertrag;
		JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
				+ zeitraum + " Monat(en) beträgt : " + ergebnis + "\n"
				+ "Ihre gesamten Sparzugaben betragen dabei : " + sparrate +"\n"
				+ "Ihre gesamte Zinszuschrift beträgt dabei : " + zinsertrag,
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
		

	}

	static void berechne_SparfktMitZinsenMonatlich(double startwert,
			double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag=0.00;
		summesparrate=0.00;
		for(int i=1;i<=zeitraum;i++){
			  zinsertrag=(startwert+sparrate)*zinssatz/100;
			  summezinsertrag=summezinsertrag+zinsertrag;
			  summesparrate=summesparrate+sparrate;
			  zwischenwert=startwert+sparrate+zinsertrag;
			  startwert=zwischenwert;//12.1
			}
		JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
				+ zeitraum + " Monat(en) beträgt : " + (Math.round((startwert)*100)/100.0)+ "\n"
				+ "Ihre gesamten Sparzugaben betragen dabei : " + summesparrate +"\n"
				+ "Ihre gesamte Zinszuschrift beträgt dabei : " + (Math.round((summezinsertrag)*100)/100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
		
}

	public static void berechne_SparfktMitZinsenJaehrlichMonatsbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag=0.00;
		summesparrate=0.00;
		  for(int i=1;i<=zeitraum*12;i++){
				  zwischenwert=startwert+sparrate;
				  zinsertrag=zwischenwert*zinssatz/100;
				  summezinsertrag=summezinsertrag+zinsertrag;
				  summesparrate=summesparrate+sparrate;
				  zwischenwert=zwischenwert+zinsertrag;
				  startwert=zwischenwert;
		  }
		  JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
					+ zeitraum + " Jahr(en) beträgt : " + (Math.round((startwert)*100)/100.0) + "\n"
					+ "Ihre gesamten Sparzugaben betragen dabei : " + summesparrate +"\n"
					+ "Ihre gesamte Zinszuschrift beträgt dabei : " + (Math.round((summezinsertrag)*100)/100.0),
					"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
		
	}
//passt noch nicht
	public static void berechne_SparfktMitZinsenJaehrlichJahresbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag=0.00;
		summesparrate=0.00;
		for(int i=1;i<=zeitraum;i++){
			  zwischenwert=startwert+(sparrate*12);
			  zinsertrag=zwischenwert*zinssatz/100;
			  summezinsertrag=summezinsertrag+zinsertrag;
			  summesparrate=summesparrate+sparrate;
			  zwischenwert=zwischenwert+zinsertrag;
			  startwert=zwischenwert;
		}
		JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
				+ zeitraum + " Jahr(en) beträgt : " + (Math.round((startwert)*100)/100.0) + "\n"
				+ "Ihre gesamten Sparzugaben betragen dabei : " + summesparrate +"\n"
				+ "Ihre gesamte Zinszuschrift beträgt dabei : " + (Math.round((summezinsertrag)*100)/100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
	
	}

	public static void berechne_SparfktMitZinsenJaehrlichHalbjahresbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag=0.00;
		summesparrate=0.00;
		for(int i=1;i<=zeitraum*12;i++){
			  zwischenwert=startwert+sparrate;
			  startwert=zwischenwert;
			  if(i%6==0){
			  zinsertrag=zwischenwert*zinssatz/100;
			  zwischenwert=zwischenwert+zinsertrag;
			  startwert=zwischenwert;
			  }
			  summezinsertrag=summezinsertrag+zinsertrag;
			  summesparrate=summesparrate+sparrate;
		}
		JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
				+ zeitraum + " Jahr(en) beträgt : " + (Math.round((startwert)*100)/100.0) + "\n"
				+ "Ihre gesamten Sparzugaben betragen dabei : " + summesparrate +"\n"
				+ "Ihre gesamte Zinszuschrift beträgt dabei : " + (Math.round((summezinsertrag)*100)/100.0),
				"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void berechne_SparfktMitZinsenJaehrlichQuartalsbasis(
			double startwert, double sparrate, int zeitraum, double zinssatz) {
		summezinsertrag=0.00;
		summesparrate=0.00;
		 for(int i=1;i<=zeitraum*12;i++){
				  zwischenwert=startwert+sparrate;
				  startwert=zwischenwert;
				  if(i%3==0){
				  zinsertrag=zwischenwert*zinssatz/100;
				  zwischenwert=zwischenwert+zinsertrag;
				  startwert=zwischenwert;
				  }
				  summezinsertrag=summezinsertrag+zinsertrag;
				  summesparrate=summesparrate+sparrate;
		 }
			JOptionPane.showMessageDialog(null, "" + "Ihr Guthaben nach "
					+ zeitraum + " Jahr(en) beträgt : " + (Math.round((startwert)*100)/100.0) + "\n"
					+ "Ihre gesamten Sparzugaben betragen dabei : " + summesparrate +"\n"
					+ "Ihre gesamte Zinszuschrift beträgt dabei : " + (Math.round((summezinsertrag)*100)/100.0),
					"Ergebnis", JOptionPane.INFORMATION_MESSAGE);
		
	}
}
