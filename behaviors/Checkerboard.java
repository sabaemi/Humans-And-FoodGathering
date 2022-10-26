package behaviors;
import agents.AreaAgent;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class Checkerboard {

    public final JPanel gui = new JPanel(new BorderLayout(0, 0));
    public static JButton[][] BoardSquares = new JButton[20][20];
    public static JPanel Board;

    public Checkerboard() {
        initializeGui();
    }

    public final void initializeGui() {

        Board = new JPanel(new GridLayout(0, 20));//21
        Board.setBorder(new LineBorder(Color.BLACK));
        gui.add(Board);

        // create the board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < BoardSquares.length; ii++) {
            for (int jj = 0; jj < BoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                b.setBackground(Color.WHITE);
//                if(ii==10 && jj==9) b.setBackground(Color.GRAY);//./
                if(AreaAgent.array[ii][jj]==1) b.setBackground(Color.black);
                if(AreaAgent.array[ii][jj]==2) b.setBackground(Color.blue);
                if(AreaAgent.array[ii][jj]==4) b.setBackground(Color.green);
                if(AreaAgent.array[ii][jj]==24||AreaAgent.array[ii][jj]==431||AreaAgent.array[ii][jj]==432||AreaAgent.array[ii][jj]==433) b.setBackground(Color.gray);
                if(AreaAgent.array[ii][jj]==31) b.setBackground(Color.pink);
                if(AreaAgent.array[ii][jj]==32) b.setBackground(Color.magenta);
                if(AreaAgent.array[ii][jj]==33) b.setBackground(Color.red);
                BoardSquares[ii][jj] = b;
            }
        }

        for (int ii = 0; ii < 20; ii++) {
            for (int jj = 0; jj < 20; jj++) {
                Board.add(BoardSquares[ii][jj]);
            }
        }
        JButton cf = new JButton();
        cf.setMargin(buttonMargin);
        cf.setBackground(Color.green);
        Board.add(cf);
        Board.add(new JLabel(" : "));
        Board.add(new JLabel(String.valueOf(AreaAgent.StoredFood)));
        for (int ii = 0; ii < 16; ii++) {
            Board.add(new JLabel(""));
        }
        Board.add(new JLabel(String.valueOf(AreaAgent.time/10)));
    }

    public final JComponent getGui() {
        return gui;
    }
}
