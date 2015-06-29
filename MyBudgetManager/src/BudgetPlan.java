/**
 * 
 * 
 * Im vorliegendem Programm handelt es sich um eine Applikation, die primär für die Erfassung von Ausgaben und Einnahmen zuständig ist. 
 * Diese Daten können dann vom Benutzer auf verschiedene Weisen eingesehen werden. 
 * Unteranderem werden die Daten in Form von Statistiken oder einer Kontoübersicht präsentiert.
 * Als zustäzliches Gimmick besitzt die App eine Sparfunktion, durch die der User die Möglichkeit hat sich prognostizieren zu lassen, 
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
