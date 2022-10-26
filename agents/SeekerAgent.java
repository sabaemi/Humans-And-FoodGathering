package agents;
import behaviors.SeekerBehavior;
import jade.core.Agent;

public class SeekerAgent extends Agent {
    @Override
    protected void setup() {
//        System.out.println("Agent " + getAID().getLocalName() + " is ready to serve");
        int lx=0;
        int ly=0;
        SeekerBehavior behavior = new SeekerBehavior(this, 100 * 1,lx,ly);// 1000 * 1
        addBehaviour(behavior);
    }
}
