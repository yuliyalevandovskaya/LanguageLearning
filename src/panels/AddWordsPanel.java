package panels;

import dao.Database;
import domain.Words;
import models.CustomComboBoxModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AddWordsPanel extends JPanel {

    public AddWordsPanel(DictionaryPanel dictionaryPanel)
    {
        super(new GridBagLayout());
        this.dictionaryPanel = dictionaryPanel;
        database = Database.getInstance();

        wordsList = new LinkedList<>();
        wordsList = database.selectWords();
        categorySet = new HashSet<>();
        wordsList.forEach(words -> categorySet.add(words.getCategory()));


        GridBagConstraints gb = new GridBagConstraints();

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));

        categoryLabel = new JLabel("category");
        categoryLabel.setFont(new Font("Old Style", Font.ITALIC, 14));

        comboboxList = new LinkedList<>(categorySet);
        customComboBoxModel = new CustomComboBoxModel(comboboxList);
        categoryComboBox = new JComboBox(customComboBoxModel);
        categoryComboBox.addActionListener(e -> categoryTextField.setText((String) categoryComboBox.getSelectedItem()));

        categoryTextField = new JTextField(10);
        categoryTextField.setEditable(false);

        newCategoryButton = new JButton("add new");
        newCategoryButton.setFont(new Font("Old Style", Font.ITALIC, 10));
        newCategoryButton.addActionListener(e -> {
            categoryTextField.setVisible(true);
            categoryTextField.setEditable(true);
            categoryTextField.setText(" ");
        });


        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryComboBox);
        categoryPanel.add(newCategoryButton);
        categoryPanel.add(categoryTextField);
        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,10,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        add(categoryPanel, gb);

        JPanel englishPanel = new JPanel(new GridLayout(2,1,0, 5));

        englishLabel = new JLabel("english");
        englishLabel.setFont(new Font("Old Style", Font.ITALIC, 14));

        englishTextField = new JTextField(10);

        englishPanel.add(englishLabel);
        englishPanel.add(englishTextField);
        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,100,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        add(englishPanel, gb);

        JPanel translationsPanel = new JPanel(new GridLayout(2,2,50,10));

        germanLabel = new JLabel("german translation");
        germanLabel.setFont(new Font("Old Style", Font.ITALIC, 14));

        germanTextField = new JTextField(10);

        frenchLabel = new JLabel("french translation");
        frenchLabel.setFont(new Font("Old Style", Font.ITALIC, 14));


        frenchTextField = new JTextField(10);

        translationsPanel.add(germanLabel);
        translationsPanel.add(frenchLabel);
        translationsPanel.add(germanTextField);
        translationsPanel.add(frenchTextField);
        gb.gridx = 0;
        gb.gridy = 2;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,30,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        add(translationsPanel, gb);

        categoriesSet2 = new HashSet<>();

        insert = new JButton("add");
        insert.setFont(new Font("Old Style", Font.ITALIC, 14));
        insert.setPreferredSize(new Dimension(90,30));
        insert.addActionListener(e -> {
            Words toInsert = new Words(0, categoryTextField.getText().trim(), englishTextField.getText().trim(),
                    germanTextField.getText().trim(), 0, frenchTextField.getText().trim(),0);
            database.insertWords(toInsert);
            dictionaryPanel.addWords(toInsert);

            database.selectWords().forEach(words -> categoriesSet2.add(words.getCategory()));
            categoriesList  = new LinkedList<>(categoriesSet2);
            customComboBoxModel.updateCustomComboboxModel(categoriesList);
            dictionaryPanel.updateComboboxModel(categoriesList);
            GermanPanel.updateComboboxModel(categoriesList);
            GermanPanel.updateList(database.selectWords());
            GermanNormalPanel.updateList(database.selectWords());
            FrenchPanel.updateList(database.selectWords());
            FrenchNormalPanel.updateList(database.selectWords());

            JOptionPane.showMessageDialog(null, "new word has been added");
            this.setVisible(false);
        });
        gb.gridx = 0;
        gb.gridy = 3;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,5,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        add(insert, gb);
    }

    private JLabel categoryLabel;
    private JTextField categoryTextField;
    private JComboBox categoryComboBox;
    private JButton newCategoryButton;
    private JLabel englishLabel;
    private JTextField englishTextField;
    private JLabel germanLabel;
    private JTextField germanTextField;
    private JLabel frenchLabel;
    private JTextField frenchTextField;
    private JButton insert;
    private List<Words> wordsList;
    private Set<String> categorySet;
    private Set<String> categoriesSet2;
    private List<String> categoriesList;
    private List<String> comboboxList;
    private Database database;
    private CustomComboBoxModel customComboBoxModel;
    private DictionaryPanel dictionaryPanel;

}
