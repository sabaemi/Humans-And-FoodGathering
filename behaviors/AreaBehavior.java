package behaviors;

import agents.AreaAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.*;

import javax.swing.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import behaviors.Checkerboard;

public class AreaBehavior extends TickerBehaviour {
    private long period;
    private Agent myAgent;

    public AreaBehavior(Agent a, long period) {
        super(a, period);
        this.period = period;
        this.myAgent = a;
    }

    @Override
    protected void onTick() {
        AreaAgent.time++;
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||");
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(AreaAgent.array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println("Number of Stored Food:" + AreaAgent.StoredFood);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Checkerboard cb = new Checkerboard();
                AreaAgent.f.add(cb.getGui());
                AreaAgent.f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);

        if(AreaAgent.time%10==0)System.out.println("time:" + AreaAgent.time/10);
//        if(AreaAgent.time>=60){
//            System.out.println("time"+AreaAgent.time);
//            doDelete();
//        }
    }
//    protected void takeDown() {
//        System.out.println("Agent "+ getAID().getLocalName() +" terminating.");
//    }
}
