package panels;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel  {

    public MyPanel()
    {
        super(new CardLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();

        choice = new JLabel("CHOOSE LANGUAGE");
        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(20,100,30,0);
        gb.weightx = 1;
        gb.weighty = 1;
        choice.setFont(new Font("Monospace", Font.PLAIN, 20));
        mainPanel.add(choice, gb);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));

        german = new JButton("GERMAN");
        german.setPreferredSize(new Dimension(150,50));
        german.setFont(new Font("Monospace", Font.TRUETYPE_FONT, 16));
        german.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) (this.getLayout());
            cardLayout.show(this, "germanMode");
        });

        french = new JButton("FRENCH");
        french.setPreferredSize(new Dimension(150,50));
        french.setFont(new Font("Monospace", Font.TRUETYPE_FONT, 16));
        french.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) (this.getLayout());
            cardLayout.show(this,"frenchMode");
        });

        buttons.add(german);
        buttons.add(french);

        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,0,30,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(buttons, gb);

        JPanel dictionaryPanel = new JPanel(new FlowLayout());

        dictionaryButton = new JButton("MY DICTIONARY");
        dictionaryButton.setFont(new Font("Monospace", Font.TRUETYPE_FONT, 16));
        dictionaryButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) (this.getLayout());
            cardLayout.show(this,"dictionaryPanel");
        });

        dictionaryPanel.add(dictionaryButton);

        gb.gridx = 0;
        gb.gridy = 2;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,30,30,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(dictionaryPanel, gb);

        add(mainPanel, "main");

        dictionaryPanel = new DictionaryPanel();
        add(dictionaryPanel, "dictionaryPanel");

        germanPanel = new GermanPanel((DictionaryPanel) dictionaryPanel);
        germanNormalPanel = new GermanNormalPanel();

        frenchPanel = new FrenchPanel((DictionaryPanel) dictionaryPanel);
        frenchNormalPanel = new FrenchNormalPanel();

        frenchLearningModePanel = new FrenchLearningModePanel(frenchPanel, frenchNormalPanel);
        add(frenchLearningModePanel, "frenchMode");

        germanLearningModePanel = new GermanLearningModePanel(germanPanel, germanNormalPanel);
        add(germanLearningModePanel, "germanMode");
    }

    private JButton dictionaryButton;
    private JLabel choice;
    private JButton german;
    private JButton french;
    private JButton normal;
    private JButton inteligence;
    private GermanPanel germanPanel;
    private GermanNormalPanel germanNormalPanel;
    private GermanLearningModePanel germanLearningModePanel;
    private FrenchPanel frenchPanel;
    private FrenchNormalPanel frenchNormalPanel;
    private FrenchLearningModePanel frenchLearningModePanel;
    private DictionaryPanel dictionaryPanel;
}