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
import java.util.Date;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*; 
import java.awt.*; 
import javax.swing.*; 
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;


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

	private JPanel panel;
	private JLabel lblKontostandsanzeige;
	private JLabel lblKontostand;
	private JPanel panel_1;
	private JButton btnInfo, btnTaschenrechner, btnSchließen;
	private GroupLayout gl_panel_1;
	private JTabbedPane tabbedPane;
	private JPanel panel_Konto;
	private JPanel Panel_Ausgaben;
	private JPanel Panel_Einnahmen;
	private JPanel Panel_Statistiken;
	private JPanel panel_Sparfunktion;
	private JPanel panel_Dauerauftraege;
	private JPanel panel_Statistiken_Woche;
	private JPanel panel_Statistiken_Monat;
	private JPanel panel_Statistiken_Jahr;
	
	  private JTextArea textArea; //P2 
	
	  private JTextField textField_Einnahmen,textFieldNotiz_Ausgabe;
	  private JTextField textField_Ausgaben;
	  private JTextField txtTtmmjjjj_Ausgaben, txtTtmmjjjj_Einnahmen;			//p3
	  private JComboBox comboBox_Ausgaben;
	  private JComboBox comboBox_Einnahmen;
	  private JRadioButton rdbtnEinmalig;
	  private JLabel lblAusgabenBuchungsbetrag, lblAusgabenKategorie, lblAusgabenWiederholung, lblAusgabenFaelligkeitsdatum, lblNotiz_Ausgaben;//Label Ausgaben
	  private JRadioButton rdbtnMonatlich;
	  private JButton btnAusgabenBuchen, btnReset_Ausgaben, btnReset_Einnahmen;
	  
	  private JLabel lblEinnahmenBuchungsbetrag,lblEinnahmenKategorie,lblEinnahmenWiederholung,lblEinnahmenFaelligkeitsdatum,lblDatum,lblNotiz_Einnahmen ;
	  private JRadioButton rdbtnEinnahmenEinmalig;
	  private JRadioButton rdbtnEinnahmenMonatlich;				//p4
	  private JButton btnEinnahmenBuchen;
	  private JTabbedPane tabbedPane_Statistiken;		//P5
	  private JTextField textField;
	  private JTextField textField_Einnahmen_1;
	  private JLabel lblEuro;
	  private JLabel lblTtmmyyyy;
	  private JLabel lblEuroZb;
	  private JLabel label_2;
	  private JButton btnKontoLöschen;
	  
	  
	/**
	 * Konstruktor fuer die GUI.
	 * 
	 * Hier wird das Hauptfenster initialisiert und aktiviert.
	 * 
	 * @param budget
	 *            Modell der Daten
	 */
	public BudgetPlanGUI(BudgetPlanModel budget) {
		super("MyBudgetPlaner");
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
			JOptionPane.showMessageDialog(null, "Willkommen bei MyBudgetPlaner! \n"
					+ "Um das Programm anwenden zu können, legen Sie einen Startwert Ihres Kontos fest. \n"
					+ "Befindet sich dieser im positiven Breich, tätigen Sie ihre Eröffnungsbuchung unter dem Menüunterpunkt: Einnahmen. \n"
					+ "Anderenfalls gehen Sie auf Ausgaben.  \n"
					+ "\n"
					+ "Wir wünschen Ihnen viel Freunde an unserem tollen Produkt\n"
					+ "" ,"Willkommen bei MyBudgetPlan", JOptionPane.INFORMATION_MESSAGE);
			
			tabbedPane.setEnabledAt(0, false);
			tabbedPane.setEnabledAt(3, false);
			tabbedPane.setEnabledAt(4, false);
			tabbedPane.setEnabledAt(5, false);
			tabbedPane.setSelectedIndex(2);
			

			
			comboBox_Ausgaben.insertItemAt("Startwert", 1);
			comboBox_Ausgaben.setSelectedIndex(1);
			comboBox_Ausgaben.setEnabled(false);
			
			comboBox_Einnahmen.insertItemAt("Startwert", 1);
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
  			JOptionPane.showMessageDialog(null, "Ausgabe erfolgreich gebucht.", "Ausgabenbuchung", JOptionPane.INFORMATION_MESSAGE);
  			Init_Kontostand();
  			
  		} catch (FileNotFoundException ex) {
  			System.err
  					.println("Die Datei data/budget.csv wurde nicht gefunden!");
  			System.exit(1);
  		} catch (IOException ex) {
  			System.err
  					.println("Probleme beim Oeffnen der Datei data/budget.csv!");
  			System.exit(1);
  		}
			
		} 

		catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Falsche Datumseingabe. \n"
					+ "Formathinweis: TT.MM.JJJJ", "Fehler", JOptionPane.ERROR_MESSAGE); 
			txtTtmmjjjj_Ausgaben.setText(null);
			}
					
		
		catch ( NumberFormatException e)
		{JOptionPane.showMessageDialog(null, "Falsche Betragseingabe. \n"
				+ "Formathinweis: \n"
				+ "- Bitte verwenden Sie nur Zahlen \n"
				+ "- Benutzen Sie bitte Punkt statt Komma \n"
				+ "- Achten Sie bitte auf die evtl. fehlenden zwei Nachkommastellen \n"
				+ "- Benutzen Sie bitte kein Negativzeichen", "Fehler", JOptionPane.ERROR_MESSAGE); 
		textField_Ausgaben.setText(null);
	}
		
		catch ( IndexOutOfBoundsException e)
		{JOptionPane.showMessageDialog(null, "Bitte wählen sie eine Kategorie", "Fehler", JOptionPane.ERROR_MESSAGE); 
	
		}

	}
	  void Clear_Ausgaben() {
			textField_Ausgaben.setText(null);
			txtTtmmjjjj_Ausgaben.setText(new SimpleDateFormat("dd.MM.yyyy")
					.format(new Date()));
			comboBox_Ausgaben.setSelectedIndex(0);
			textFieldNotiz_Ausgabe.setText(null);
		}
	  
	  void Init_Kontostand(){
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
					budget.Geldvermögen.add(new Posten(datum,notiz, bezeichnung, betrag));
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
			
			if (i < 0)
				JOptionPane.showMessageDialog(null, "Ihr Kontostand befindet sich im negativen Bereich.\n"
						+ "Bitte achten Sie auf Ihre Ausgaben.", "Kontostandswarnung", JOptionPane.WARNING_MESSAGE);
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
	  			entries[1] = textField_Einnahmen_1.getText();
	  			entries[2] = (String) comboBox_Einnahmen.getSelectedItem();
	  			entries[3] = textField_Einnahmen.getText();
	  			entries[4] = "0"; // 0 gilt als Einnahme. Nur intern sichtbar 
	  			writer.writeNext(entries);
	  			writer.close();
	  			Clear_Einnahmen();
	  			JOptionPane.showMessageDialog(null, "Einnahme erfolgreich gebucht.", "Einnahmenbuchung", JOptionPane.INFORMATION_MESSAGE);
	  			Init_Kontostand();
	  			
	  		} catch (FileNotFoundException ex) {
	  			System.err
	  					.println("Die Datei data/budget.csv wurde nicht gefunden!");
	  			System.exit(1);
	  		} catch (IOException ex) {
	  			System.err
	  					.println("Probleme beim Oeffnen der Datei data/budget.csv!");
	  			System.exit(1);
	  		}
				
			}
			catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "Falsche Datumseingabe. \n"
						+ "Formathinweis: TT.MM.JJJJ", "Fehler", JOptionPane.ERROR_MESSAGE); 
				txtTtmmjjjj_Einnahmen.setText(null);
			}
			
			catch ( NumberFormatException e)
			{JOptionPane.showMessageDialog(null, "Falsche Betragseingabe.\n"
					+ "Formathinweis: \n"
					+ "- Bitte verwenden Sie nur Zahlen \n"
					+ "- Benutzen Sie bitte Punkt statt Komma \n"
					+ "- Achten Sie bitte auf die evtl. fehlenden zwei Nachkommastellen", "Fehler", JOptionPane.ERROR_MESSAGE); 
			textField_Einnahmen.setText(null);
			}
			
			catch ( IndexOutOfBoundsException e)
			{JOptionPane.showMessageDialog(null, "Bitte wählen sie eine Kategorie aus.", "Fehler", JOptionPane.ERROR_MESSAGE); 
		
			}
		}
	  
		  
	  
	  void Clear_Einnahmen(){
		  textField_Einnahmen.setText(null);
			txtTtmmjjjj_Einnahmen.setText(new SimpleDateFormat("dd.MM.yyyy")
					.format(new Date()));
			comboBox_Einnahmen.setSelectedIndex(0); 
			textField_Einnahmen_1.setText(null);
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
		panel_Konto.setLayout(null);
		
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
		
//		///////////////////////////
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
		
		
//			textArea = new JTextArea();
//	        textArea.setBounds(10, 34, 532, 282);
//	        panel_Konto.add(textArea);
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
		textField_Ausgaben.setBounds(197, 11, 150, 25);
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
		comboBox_Ausgaben.setModel(new DefaultComboBoxModel(new String[] {"(Bitte Wählen)", "Haushalt", "Elektronik", "Auto", "Sport", "Freizeit", "Sonstiges"}));
		comboBox_Ausgaben.setBounds(197, 69, 155, 25);
		Panel_Ausgaben.add(comboBox_Ausgaben);
		
		 
		 lblAusgabenWiederholung = new JLabel("Wiederholung:");
		 lblAusgabenWiederholung.setFont(new Font("Tahoma", Font.BOLD, 11));
		 lblAusgabenWiederholung.setBounds(21, 130, 142, 29);
		 Panel_Ausgaben.add(lblAusgabenWiederholung);
		 
		 rdbtnEinmalig = new JRadioButton("einmalig");
		 rdbtnEinmalig.setBounds(197, 133, 109, 23);
		 Panel_Ausgaben.add(rdbtnEinmalig);
		 
		 rdbtnMonatlich = new JRadioButton("monatlich\r\n");
		 rdbtnMonatlich.setBounds(305, 133, 109, 23);
		 Panel_Ausgaben.add(rdbtnMonatlich);
		 
		 lblAusgabenFaelligkeitsdatum = new JLabel("F\u00E4lligkeitsdatum:");
		 lblAusgabenFaelligkeitsdatum.setFont(new Font("Tahoma", Font.BOLD, 11));
		 lblAusgabenFaelligkeitsdatum.setBounds(21, 184, 142, 32);
		 Panel_Ausgaben.add(lblAusgabenFaelligkeitsdatum);
		 
		 txtTtmmjjjj_Ausgaben = new JTextField();
		 txtTtmmjjjj_Ausgaben.setBounds(197, 187, 150, 26);       
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
		  lblNotiz_Ausgaben.setBounds(21, 257, 99, 20);
		  Panel_Ausgaben.add(lblNotiz_Ausgaben);
		  
		  lblEuroZb = new JLabel(" Euro ( z.B. 5.55)");
		  lblEuroZb.setFont(new Font("Tahoma", Font.BOLD, 11));
		  lblEuroZb.setBounds(357, 16, 99, 14);
		  Panel_Ausgaben.add(lblEuroZb);
		  
		  label_2 = new JLabel("TT.MM.JJJJ");
		  label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		  label_2.setBounds(360, 193, 67, 14);
		  Panel_Ausgaben.add(label_2);
		  
		  btnReset_Ausgaben = new JButton("Eingaben reseten");
		  btnReset_Ausgaben.setBounds(21, 311, 135, 29);
		  Panel_Ausgaben.add(btnReset_Ausgaben);
	       
	        
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
		        comboBox_Einnahmen.setModel(new DefaultComboBoxModel(new String[] {"(Bitte Wählen)","Haushalt", "Elektronik","Lebensmittel", "Auto", "Sport", "Freizeit", "Sonstiges"}));
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
		        lblEinnahmenFaelligkeitsdatum.setBounds(21, 187, 142, 32);
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
		        lblNotiz_Einnahmen.setBounds(21, 254, 99, 20);
		        Panel_Einnahmen.add(lblNotiz_Einnahmen);
		        
		        textField_Einnahmen_1 = new JTextField();
		        textField_Einnahmen_1.setColumns(10);
		        textField_Einnahmen_1.setBounds(197, 252, 150, 25);
		        Panel_Einnahmen.add(textField_Einnahmen_1);
		        
		        lblEuro = new JLabel(" Euro ( z.B. 5.55)");
		        lblEuro.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblEuro.setBounds(357, 18, 99, 14);
		        Panel_Einnahmen.add(lblEuro);
		        
		        lblTtmmyyyy = new JLabel("TT.MM.JJJJ");
		        lblTtmmyyyy.setFont(new Font("Tahoma", Font.BOLD, 11));
		        lblTtmmyyyy.setBounds(357, 196, 67, 14);
		        Panel_Einnahmen.add(lblTtmmyyyy);
		        
		        btnReset_Einnahmen = new JButton("Eingaben reseten");
				  btnReset_Einnahmen.setBounds(21, 311, 135, 29);
				  Panel_Einnahmen.add(btnReset_Einnahmen);
	        //ENDE Einnahmen PANEL4
	        
	        
	        
	        
	        //Anfang Panel 5 Daueraufträge
	        /**
			 * 
			 * 
			 * Hier sollte noch ein Panel für Daueraufträge entstehen, der vor die Statistiken gesetzt wird
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
	        //ENDE PANEL5
	        
	        //Anfang Panel 5 Statistiken
	    	/**
			 * 
			 * 
			 * Panel in dem alle Ausgaben und Einnahmen aufgeführt werden
			 * Dieser Panel müsste dann in Panel 6 umbenannt werden
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
	        JTabbedPane tabbedPane_Statistiken = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPane_Statistiken.setBounds(10, 39, 532, 277);
	        Panel_Statistiken.add(tabbedPane_Statistiken);
	        
	        panel_Statistiken_Woche = new JPanel();
	        tabbedPane_Statistiken.addTab("Woche", null, panel_Statistiken_Woche, null);
	        
	        panel_Statistiken_Monat = new JPanel();
	        tabbedPane_Statistiken.addTab("Monat", null, panel_Statistiken_Monat, null);
	        
	        panel_Statistiken_Jahr = new JPanel();
	        tabbedPane_Statistiken.addTab("Jahr", null, panel_Statistiken_Jahr, null);
	        
	        //ENDE PANEL 5 Statistiken
	      //Anfang Panel 7 Sparfunktion
	    	/**
			 * 
			 * 
			 * Panel in dem die Sparfunktion seinen Platz findet
			 * Dieser Panel sollte zu Panel 7 umbenannt werden, sobald Panel Daueraufträge undStatistiken geordnet wurden
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
		
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
			
			
			btnReset_Ausgaben.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        			Clear_Ausgaben();
	 	        		}
	        	
	        });
			
		
			btnAusgabenBuchen.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent arg0) {
	        		
	        		Checkdata_Ausgaben();
	        		if ((comboBox_Ausgaben.isEnabled() == false) && (budget.Geldvermögen.size() == 0)){
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
		        		if ((comboBox_Ausgaben.isEnabled() == false) && (budget.Geldvermögen.size() != 0)){
		        			 Aktivierung();
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
					//, JOptionPane.YES_NO_OPTION);
					
					int n = JOptionPane.showConfirmDialog(null, "Möchten Sie wirklich ihr Konto löschen? \n"
							+ "Sämtliche Daten gehen dabei verloren!", "Konto löschen",
						    JOptionPane.YES_NO_OPTION);
					if (n == 0){			
					
					CSVWriter writer = null;  		
			  		try {
			  			writer = new CSVWriter(new FileWriter("data/budget.csv"));
			  			writer.writeNext(null);;
			  			writer.close();
			  			
			  		} catch (FileNotFoundException ex) {
			  			System.err
			  					.println("Die Datei data/budget.csv wurde nicht gefunden!");
			  			System.exit(1);
			  		} catch (IOException ex) {
			  			System.err
			  					.println("Probleme beim Oeffnen der Datei data/budget.csv!");
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
						JOptionPane.showMessageDialog(null, "Taschenrechner konnte nicht aufgerufen worden.", "ERROR", JOptionPane.ERROR_MESSAGE);
					} 
				}
			});
			
				
			
				
		}
}	
		
		
		
		




