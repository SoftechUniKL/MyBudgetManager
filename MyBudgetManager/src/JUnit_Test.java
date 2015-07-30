import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class JUnit_Test {
	private BudgetPlanModel budget;

	BudgetPlanGUI test = new BudgetPlanGUI(budget);

	@Ignore
	@Test
	public void test() {
		fail("Im Testbetrieb");
	}
}
