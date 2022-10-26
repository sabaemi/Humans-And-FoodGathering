package agents;

import behaviors.CollectorBehavior;
import jade.core.Agent;

public class CollectorAgent extends Agent {
    @Override
    protected void setup() {
//        System.out.println("Agent " + getAID().getLocalName() + " is ready to serve");
        int lx=0;
        int ly=19;
        String str = getAID().getLocalName();
        String[] arrOfStr = str.split("collectorAgent", 2);
        int number = Integer.parseInt(arrOfStr[1]);
        switch(number) {
            case 1:
                lx=14;
                break;
            case 2:
                lx=7;
                break;
            case 3:
                lx=0;
                break;
        }
        CollectorBehavior behavior = new CollectorBehavior(this, 100 * 1,lx,ly);// 1000 * 1
        addBehaviour(behavior);
    }
}
