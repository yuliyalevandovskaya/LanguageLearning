package main;

import dao.Database;
import domain.Words;
import panels.MyPanel;

import javax.swing.*;

public class Main extends JFrame {

    public static void createAndShowGui()
    {
        JFrame frame = new JFrame("BUTTONS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyPanel panel = new MyPanel();
        panel.setVisible(true);
        frame.setContentPane(panel);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public static void main(String[] args) {

        Database database = Database.getInstance();

        /*database.insertWords(new Words(0,"family","mother", "mutter",0, "mere",0));
        database.insertWords(new Words(0,"family","father", "vater",0, "pere",0));
        database.insertWords(new Words(0,"cosmos","sky", "himmel",0, "ciel",0));*/

        javax.swing.SwingUtilities.invokeLater(
                new Runnable()
                {
                    public void run()
                    {
                        createAndShowGui();
                    }
                }
        );
    }
}