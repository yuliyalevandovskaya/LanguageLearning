package panels;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private JButton back;
    private JLabel scoreLabel1;
    private JLabel resultLabel1;
    private JLabel resultLabel2;
    private JLabel scoreLabel2;
    private int result;

    public ScorePanel()
    {
        super(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();

        scoreLabel1 = new JLabel("YOUR SCORE:  ");
        scoreLabel1.setFont(new Font("Monospace", Font.ITALIC, 20));

        JPanel resultPanel = new JPanel(new FlowLayout());
        resultLabel1 = new JLabel("0");
        resultLabel1.setFont(new Font("Monospace", Font.ITALIC, 16));

        JLabel res = new JLabel("from");
        res.setFont(new Font("Monospace", Font.ITALIC, 16));

        resultLabel2 = new JLabel("1");
        resultLabel2.setFont(new Font("Monospace", Font.ITALIC, 16));

        resultPanel.add(scoreLabel1);
        resultPanel.add(resultLabel1);
        resultPanel.add(res);
        resultPanel.add(resultLabel2);

        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,50,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        add(resultPanel, gb);

        scoreLabel2 = new JLabel();
        scoreLabel2.setFont(new Font("Monospace", Font.TRUETYPE_FONT, 16));

        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,50,20,0);
        gb.weightx = 1;
        gb.weighty = 1;
        add(scoreLabel2, gb);

        back = new JButton("OK");
        back.setPreferredSize(new Dimension(60,20));
        gb.gridx = 0;
        gb.gridy = 2;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(5,50,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        add(back, gb);
        back.addActionListener(e -> {
            this.setVisible(false);
            GermanPanel.reset();
            FrenchPanel.reset();
        });
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public JLabel getResultLabel1() {
        return resultLabel1;
    }

    public JLabel getResultLabel2() {
        return resultLabel2;
    }

    public JLabel getScoreLabel2() {
        return scoreLabel2;
    }
}