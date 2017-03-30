package panels;

import dao.Database;
import domain.Words;
import models.CustomComboBoxModel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GermanPanel extends JPanel {

    public GermanPanel(DictionaryPanel dictionaryPanel)
    {
        super(new CardLayout());
        this.dictionaryPanel = dictionaryPanel;

        database = Database.getInstance();

        wordsList = new LinkedList<>(database.selectWords());
        wordsSet = new HashSet<>();
        wordsSet = randomWords();

        scorePanel = new ScorePanel();

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();

        back = new JButton("BACK");
        back.setPreferredSize(new Dimension(80,20));
        back.setFont(new Font("Old Style", Font.ITALIC, 10));
        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(5,10,5,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(back, gb);
        back.addActionListener(e -> this.setVisible(false));

        JPanel categoryPanel = new JPanel(new FlowLayout());
        categoryLabel = new JLabel("Please choose category: ");
        categoryLabel.setFont(new Font("Old Style", Font.ITALIC, 12));

        allWordcheckBox = new JCheckBox("all words", false);
        allWordcheckBox.setFont(new Font("Old Style", Font.ITALIC, 14));
        allWordcheckBox.addActionListener(e -> {
            JCheckBox checkbox = (JCheckBox)e.getSource();
            if(checkbox.isSelected()){
                filteredWords = wordsSet;
                maxWords.setText("max " + (filteredWords.size()) + " words");
            }
        });

        categorySet = new HashSet<>();
        wordsList.forEach(words -> categorySet.add(words.getCategory()));
        categoriesList = new LinkedList<>(categorySet);
        filteredWords = new HashSet<>();
        customComboBoxModel = new CustomComboBoxModel(categoriesList);
        category = new JComboBox(customComboBoxModel);
        category.setFont(new Font("Old Style", Font.ITALIC, 12));
        category.addActionListener(e -> {
            String cat = (String) category.getSelectedItem();
            allWordcheckBox.setSelected(false);
            filteredWords = wordsSet.stream().filter(words -> words.getCategory().equals(cat)).collect(Collectors.toSet());
            maxWords.setText("max " + (filteredWords.size()) + " words");
        });

        categoryPanel.add(categoryLabel);
        categoryPanel.add(category);
        categoryPanel.add(allWordcheckBox);
        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,4,5,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(categoryPanel, gb);

        howManyLabel = new JLabel("Number of words");
        howManyLabel.setFont(new Font("Old Style", Font.ITALIC, 12));

        howMany = new JTextField(3);

        maxWords = new JLabel();
        maxWords.setFont(new Font("Old Style", Font.ITALIC, 12));

        start = new JButton("START");
        start.setPreferredSize(new Dimension(90,30));
        start.setFont(new Font("Old Style", Font.PLAIN, 14));
        start.addActionListener(e -> {
            check.setEnabled(true);
            if(howMany.getText().isEmpty() || Integer.parseInt(howMany.getText().trim()) > Integer.parseInt(maxWords.getText().trim().split(" ")[1])){
                howMany.setBackground(Color.RED);
                JOptionPane.showMessageDialog(null, "Please choose how many words would you like to train");
                howMany.setBackground(Color.white);
            } else {
                word.setText(filteredWords.stream().map(w -> w.getEnglish()).findFirst().get());
            }
        });

        JPanel numberOfWords = new JPanel(new FlowLayout());
        numberOfWords.add(howManyLabel);
        numberOfWords.add(howMany);
        numberOfWords.add(maxWords);
        numberOfWords.add(start);
        gb.gridx = 0;
        gb.gridy = 2;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,4,30,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(numberOfWords, gb);

        JPanel translationPanel = new JPanel(new FlowLayout());
        word = new JTextField(10);
        word.setEditable(false);
        labelWord = new JLabel("IN GERMAN MEANS?");
        labelWord.setFont(new Font("Old Style", Font.ITALIC, 16));
        translationPanel.add(word);
        translationPanel.add(labelWord);

        gb.gridx = 0;
        gb.gridy = 3;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,30,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(translationPanel, gb);

        translationLabel = new JLabel("Please write your translation above:");
        translationLabel.setFont(new Font("Old Style", Font.ITALIC, 12));
        gb.gridx = 0;
        gb.gridy = 4;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(10,35,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(translationLabel, gb);

        translation = new JTextField(10);
        gb.gridx = 0;
        gb.gridy = 5;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,35,20,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(translation, gb);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));

        counter = 0;
        check = new JButton("CHECK");
        check.setFont(new Font("Old Style", Font.ITALIC, 14));
        check.setEnabled(false);
        check.setPreferredSize(new Dimension(90,30));
        check.addActionListener(e -> {
            check.setEnabled(false);
            next.setEnabled(true);
                    if(Words.checkTranslationGerman(wordsSet, word.getText().trim()).equals(translation.getText().trim())){
                        translation.setBackground(Color.green);
                        counter++;
                        for(Words w : wordsSet)
                        {
                            if(w.getEnglish().matches(word.getText().trim())){
                                if(w.getGermanCounter() < 100){
                                    w.setGermanCounter(w.getGermanCounter() + 25);
                                }
                                database.updateGermanCounter(w);
                                this.dictionaryPanel.update();
                            }
                        }
                    } else {
                        translation.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(null, "Correct answer is:  " + Words.checkTranslationGerman(wordsSet, word.getText().trim()));
                        for(Words w : wordsSet)
                        {
                            if(w.getEnglish().matches(word.getText().trim())){
                                if(w.getGermanCounter() >= 25){
                                    w.setGermanCounter(w.getGermanCounter() - 25);
                                }
                                database.updateGermanCounter(w);
                                this.dictionaryPanel.update();
                            }
                        }
                    }
        });

        index = 0;
        next = new JButton("NEXT");
        next.setFont(new Font("Old Style", Font.ITALIC, 14));
        next.setPreferredSize(new Dimension(90,30));
        next.setEnabled(false);
        next.addActionListener(e -> {
            check.setEnabled(true);
            index++;
            List<Words> choosedWords = new LinkedList<Words>(filteredWords);
            if(index < Integer.parseInt(howMany.getText().trim())){
                word.setText(choosedWords.get(index).getEnglish());
            } else {
                word.setText("");
                scorePanel.getResultLabel1().setText(counter + "");
                scorePanel.getResultLabel2().setText(howMany.getText().trim());
                CardLayout cardLayout = (CardLayout) (this.getLayout());
                cardLayout.show(this,"score");
            }
            translation.setBackground(Color.white);
            translation.setText("");
            scorePanel.setResult(Words.getCounter(counter,Integer.parseInt(howMany.getText().trim())));
            if(scorePanel.getResult() >= 0 && scorePanel.getResult() < 25){
                scorePanel.getScoreLabel2().setText(scorePanel.getResult() + "%" + "   YOU NEED TO LEARN MORE");
            }
            else if(scorePanel.getResult() > 25 && scorePanel.getResult() <= 50){
                scorePanel.getScoreLabel2().setText(scorePanel.getResult() + "%" + "   NOT BAD, KEEP LEARNING");
            }
            else if(scorePanel.getResult() > 50 && scorePanel.getResult() <= 75){
                scorePanel.getScoreLabel2().setText(scorePanel.getResult() + "%" + "   GOOD");
            }
            else if(scorePanel.getResult() > 75 && scorePanel.getResult() < 100){
                scorePanel.getScoreLabel2().setText(scorePanel.getResult() + "%" + "   VERY GOOD");
            }
            else if(scorePanel.getResult() == 100){
                scorePanel.getScoreLabel2().setText(scorePanel.getResult() + "%" + "   EXCELLENT");
            }
        });

        buttonsPanel.add(check);
        buttonsPanel.add(next);

        gb.gridx = 0;
        gb.gridy = 6;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(10,4,10,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(buttonsPanel, gb);

        add(mainPanel);
        add(scorePanel, "score");
    }

    public static void reset()
    {
        counter = 0;
        index = 0;
    }

    public static void updateList(List<Words> words){
        wordsList = words;
        wordsSet = randomWords();

    }

    public static void updateComboboxModel(List<String> wordsList){
        customComboBoxModel.updateCustomComboboxModel(wordsList);
    }

    public static Set<Words> randomWords()
    {
        Random rnd = new Random();
        int i = 0;
        while(i<= wordsList.size()){
            i++;
            wordsSet.add(wordsList.get(rnd.nextInt(wordsList.size())));
        }
        return wordsSet;
    }

    private JTextField word;
    private JTextField translation;
    private JLabel labelWord;
    private JLabel translationLabel;
    private JButton check;
    private JButton next;
    private JLabel howManyLabel;
    private JTextField howMany;
    private JLabel maxWords;
    private JButton start;
    private JButton back;
    private JLabel categoryLabel;
    private JComboBox category;
    private static List<Words> wordsList;
    private static Set<Words> wordsSet;
    private Set<Words> filteredWords;
    private Set<String> categorySet;
    private ScorePanel scorePanel;
    private DictionaryPanel dictionaryPanel;
    private static int index;
    private static int counter;
    private Database database;
    private static CustomComboBoxModel customComboBoxModel;
    private List<String> categoriesList;
    private JCheckBox allWordcheckBox;
}