package agents;

import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class Starter extends Agent {
    /**
     *
     */
    private static final long serialVersionUID = 1552505848413859743L;
    private static final int NUMBER_OF_AGENTS = 6;//6

    @Override
    protected void setup() {

        System.out.println("Setup of starter agent");

        String[] collector = new String[3];//3
        for (int i = 0; i < collector.length; i++) {
            collector[i] = "collectorAgent" + (i + 1);
        }

        AgentContainer c = getContainerController();//main container for all agents
        AgentController[] a = new AgentController[NUMBER_OF_AGENTS];//agent controller for each agent

        //areaAgent
        try {
            a[0] = c.createNewAgent("areaAgent", "agents.AreaAgent", null);
            a[0].start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //seekerAgent
        try {
            a[1] = c.createNewAgent("seekerAgent", "agents.SeekerAgent", null);
            a[1].start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //planterAgent
        try {
            a[2] = c.createNewAgent("planterAgent", "agents.PlanterAgent", null);
            a[2].start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //collectorAgent
        for (int j = 3; j < a.length; j++) {
            try {
                a[j] = c.createNewAgent(collector[j - 3], "agents.CollectorAgent", null);
                a[j].start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //finish
        long start = System.currentTimeMillis();
        long end = start + 67000; // (60 seconds for simulation + 7 seconds for running java ) * 1000 ms/sec
        while (System.currentTimeMillis() <= end) {
            // run
        }
        doDelete();
        System.exit(0);
    }
    protected void takeDown() {
        System.out.println("Agent " + getAID().getLocalName() + " terminating.");
    }
}