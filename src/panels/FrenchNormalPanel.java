package panels;

import dao.Database;
import domain.Words;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class FrenchNormalPanel extends JPanel {

    public FrenchNormalPanel()
    {
        super(new CardLayout());
        database  = Database.getInstance();

        wordsList = new LinkedList<>();
        wordsList = database.selectWords();
        wordsSet = new HashSet<>();
        wordsSet = randomWords();

        scorePanel = new ScorePanel();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();

        back = new JButton("BACK");
        back.setPreferredSize(new Dimension(80,20));
        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,15,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(back, gb);
        back.addActionListener(e -> {
            this.setVisible(false);
        });

        start = new JButton("START");
        start.setPreferredSize(new Dimension(100,30));
        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,150,30,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(start, gb);
        start.addActionListener(e -> {
            word.setText(wordsSet.stream().map(w -> w.getEnglish()).findFirst().get());
            check.setEnabled(true);
        });

        JPanel translationPanel = new JPanel(new FlowLayout());
        word = new JTextField(10);
        word.setEditable(false);
        labelWord = new JLabel("IN FRENCH MEANS?");
        labelWord.setFont(new Font("Old Style", Font.ITALIC, 16));
        translationPanel.add(word);
        translationPanel.add(labelWord);

        gb.gridx = 0;
        gb.gridy = 2;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(0,30,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(translationPanel, gb);

        translationLabel = new JLabel("Please write your translation above:");
        translationLabel.setFont(new Font("Old Style", Font.ITALIC, 12));
        gb.gridx = 0;
        gb.gridy = 3;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(10,35,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(translationLabel, gb);

        translation = new JTextField(10);
        gb.gridx = 0;
        gb.gridy = 4;
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
            next.setEnabled(true);
            check.setEnabled(false);
            if(Words.checkTranslationFrench(wordsSet, word.getText().trim()).equals(translation.getText().trim())){
                translation.setBackground(Color.green);
                counter++;
            }else{
                translation.setBackground(Color.red);
                JOptionPane.showMessageDialog(null, "Correct answer is: " + Words.checkTranslationFrench(wordsSet, word.getText().trim()));
            }
        });

        index = 0;
        next = new JButton("NEXT");
        next.setFont(new Font("Old Style", Font.ITALIC, 14));
        next.setPreferredSize(new Dimension(90,30));
        next.setEnabled(false);
        next.addActionListener(e -> {
            index++;
            check.setEnabled(true);
            next.setEnabled(false);
            translation.setBackground(Color.white);
            translation.setText(" ");
            List<Words> wordsToTranslate = new LinkedList<Words>(wordsSet);
            if(index <= wordsToTranslate.size() - 1){
                word.setText(wordsToTranslate.get(index).getEnglish());
            }else{
                word.setText("");
                scorePanel.getResultLabel1().setText(counter + "");
                scorePanel.getResultLabel2().setText(wordsToTranslate.size() + "");
                CardLayout cardLayout = (CardLayout) (this.getLayout());
                cardLayout.show(this,"scorePanel");
            }
            scorePanel.setResult(Words.getCounter(counter, wordsToTranslate.size()));
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
        gb.gridy = 5;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(10,4,10,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(buttonsPanel, gb);

        add(mainPanel);
        add(scorePanel, "scorePanel");
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

    public static void reset()
    {
        counter = 0;
        index = 0;
    }

    public static void updateList(List<Words> words){
        wordsList = words;
    }

    private JTextField word;
    private JTextField translation;
    private JLabel labelWord;
    private JLabel translationLabel;
    private JButton check;
    private JButton next;
    private JButton back;
    private JButton start;
    private ScorePanel scorePanel;
    private static List<Words> wordsList;
    private static Set<Words> wordsSet;
    private static int counter;
    private static int index;
    private Database database;
}