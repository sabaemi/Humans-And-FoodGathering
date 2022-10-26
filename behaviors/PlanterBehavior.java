package behaviors;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import agents.PlanterAgent;
import agents.AreaAgent;
import jade.lang.acl.*;

import java.util.Random;

public class PlanterBehavior extends TickerBehaviour {
    private long period;
    private Agent myAgent;
    public PlanterBehavior(Agent a, long period) {
        super(a, period);
        this.period = period;
        this.myAgent = a;
    }

    @Override
    protected void onTick() {
        //planting
        Random rand = new Random();
        int i=0;
        while(i!=1){
            int rand11 = rand.nextInt(20);
            int rand12 = rand.nextInt(20);
            if(AreaAgent.array[rand11][rand12]==0){
                AreaAgent.array[rand11][rand12]=4;
                i=1;
            }
        }
    }
}
