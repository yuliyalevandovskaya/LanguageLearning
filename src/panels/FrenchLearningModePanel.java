package panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Julia on 3/23/2017.
 */
public class FrenchLearningModePanel extends JPanel {

    private JButton back;
    private JButton normal;
    private JButton intelligent;
    private FrenchNormalPanel frenchNormalPanel;
    private FrenchPanel frenchPanel;

    public FrenchLearningModePanel(FrenchPanel frenchPanel, FrenchNormalPanel frenchNormalPanel)
    {
        super(new CardLayout());
        this.frenchPanel = frenchPanel;
        this.frenchNormalPanel = frenchNormalPanel;

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));

        normal = new JButton("NORMAL");
        normal.setPreferredSize(new Dimension(150,50));
        normal.setFont(new Font("Monospace", Font.TRUETYPE_FONT, 16));
        normal.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) this.getLayout();
            cardLayout.show(this, "frenchNormal");
        });

        intelligent = new JButton("INTELLIGENT");
        intelligent.setPreferredSize(new Dimension(150,50));
        intelligent.setFont(new Font("Monospace", Font.TRUETYPE_FONT, 16));
        intelligent.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) this.getLayout();
            cardLayout.show(this, "frenchPanel");
        });

        buttonsPanel.add(normal);
        buttonsPanel.add(intelligent);

        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,10,5,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(buttonsPanel, gb);

        back = new JButton("BACK");
        back.setPreferredSize(new Dimension(90,20));
        back.setFont(new Font("Monospace", Font.ITALIC, 10));
        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(10,40,5,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(back, gb);
        back.addActionListener(e -> this.setVisible(false));

        add(mainPanel);
        add(frenchNormalPanel, "frenchNormal");
        add(frenchPanel, "frenchPanel");
    }
}