package agents;
import behaviors.PlanterBehavior;
import jade.core.Agent;
import java.util.Random;
public class PlanterAgent extends Agent {
//	public static int[][] array = new int[20][20];

	@Override
	protected void setup() {
//		System.out.println("Agent " + getAID().getLocalName() + " is ready to serve");
		PlanterBehavior behavior = new PlanterBehavior(this, 1000 * 5);
		addBehaviour(behavior);
	}
}
