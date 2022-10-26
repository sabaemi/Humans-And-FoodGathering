package agents;
import behaviors.AreaBehavior;
import behaviors.PlanterBehavior;
import jade.core.Agent;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import behaviors.Checkerboard;
public class AreaAgent extends Agent {
    public static int[][] array = new int[20][20];
    public static int StoredFood=0;
    public static int time=0;
    public static JFrame f= new JFrame("Board");
    public static Checkerboard cb = new Checkerboard();
    @Override
    protected void setup() {
//        System.out.println("Agent " + getAID().getLocalName() + " is ready to serve");

        //warehouse
        array[10][9]=1;//./array[9][9]=1
        //seeker
        array[0][0]=2;
        //collector
        array[14][19]=31;
        array[7][19]=32;
        array[0][19]=33;
        Random rand = new Random();
        //first foods
        for(int i=0;i<3;i++){
            int rand11 = rand.nextInt(20);
            int rand12 = rand.nextInt(20);
            if(array[rand11][rand12]==0){
                array[rand11][rand12]=4;
            }
            else i--;
        }

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        //swing
		Runnable r = new Runnable() {
			@Override
			public void run() {
				f.add(cb.getGui());
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setPreferredSize(new Dimension(600, 600));
				f.pack();
                f.setLocationRelativeTo(null);
				f.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
        //behavior
        AreaBehavior behavior = new AreaBehavior(this,100 * 1);// 1000 * 1
        addBehaviour(behavior);
    }
//    protected void takeDown() {
//        System.out.println("Agent "+ getAID().getLocalName() +" terminating.");
//    }
}
