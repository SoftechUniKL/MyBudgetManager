/**
 * 
 * 
 * Im vorliegendem Programm handelt es sich um eine Applikation, die prim�r f�r die Erfassung von Ausgaben und Einnahmen zust�ndig ist. 
 * Diese Daten k�nnen dann vom Benutzer auf verschiedene Weisen eingesehen werden. 
 * Unteranderem werden die Daten in Form von Statistiken oder einer Konto�bersicht pr�sentiert.
 * Als zust�zliches Gimmick besitzt die App eine Sparfunktion, durch die der User die M�glichkeit hat sich prognostizieren zu lassen, 
 * wie lange er auf einen bestimmten Betrag sparen muss.
 * Das vorliegende Programm wurde in Java geschrieben.
 * 
 * @author Markus Dittmann, Philipp Heintz, Lukas Breit
 * 
 * @author der Einnahmen/Ausgaben erfassung Lukas Breit
 * @author der Statistik Markus Dittmann
 * @author der Sparfunktion Philipp Heintz
 * 
 * 
 */

public class BudgetPlan {
	public static void main(String[] args) {
		BudgetPlanModel budget = new BudgetPlanModel(); // Modell
		new BudgetPlanGUI(budget); //View und Controller
	}

}
