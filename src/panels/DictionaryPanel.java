package panels;

import dao.Database;
import domain.Words;
import models.CustomComboBoxModel;
import models.CustomTableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryPanel extends JPanel {

    public DictionaryPanel()
    {
        super(new CardLayout());
        database = Database.getInstance();

        addWordsPanel = new AddWordsPanel(this);
        wordsList = database.selectWords();
        categories = new HashSet<>();
        wordsList.forEach(words -> categories.add(words.getCategory()));

        filteredList = new LinkedList<>();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30,0));

        allWordcheckBox = new JCheckBox("all words", false);
        allWordcheckBox.setFont(new Font("Old Style", Font.ITALIC, 14));
        allWordcheckBox.addActionListener(e -> {
            JCheckBox checkbox = (JCheckBox)e.getSource();
            if(checkbox.isSelected()){
                filteredList = wordsList;
                model.update(filteredList);
            }
        });

        categoriesList = new LinkedList<>(categories);
        customComboBoxModel = new CustomComboBoxModel(categoriesList);
        filtrCombobox = new JComboBox(customComboBoxModel);
        filtrCombobox.addActionListener(e -> {
            String cat = (String) filtrCombobox.getSelectedItem();
            allWordcheckBox.setSelected(false);
            filteredList = wordsList.stream().filter(words -> words.getCategory().equals(cat)).collect(Collectors.toList());
            model.update(filteredList);
        });

        back = new JButton("BACK");
        back.setPreferredSize(new Dimension(60,20));
        back.setFont(new Font("Old Style", Font.ITALIC, 10));
        back.addActionListener(e -> this.setVisible(false));

        topPanel.add(back);
        topPanel.add(allWordcheckBox);
        topPanel.add(filtrCombobox);

        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(5,5,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(topPanel, gb);

        columnNames = Arrays.asList("CATEGORY", "ENGLISH", "GERMAN", "%", "FRENCH", "%");
        model = new CustomTableModel(columnNames, wordsList);
        wordsTable = new JTable(model);
        wordsTable.setPreferredScrollableViewportSize(new Dimension(380, 150));
        wordsTable.setFont(new Font("Old Style", Font.ITALIC, 12));
        wordsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
            {
                Component c = super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
                    if((Integer)table.getValueAt(row, 3) == 100 || (Integer)table.getValueAt(row, 5) == 100){
                    c.setForeground(Color.RED);
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                        return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(wordsTable);

        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(5,15,0,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(scrollPane, gb);

        JPanel buttons = new JPanel(new FlowLayout());
        insert = new JButton("add new word");
        insert.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) this.getLayout();
            cardLayout.show(this, "addWordsPanel");
        });

        delete = new JButton("delete");
        delete.addActionListener(e -> {
            String w = (String) model.getValueAt(wordsTable.getSelectedRow(), 1);
            System.out.println(w);
            database.deleteWords(w);
            int i = wordsTable.getSelectedRow();
            model.deleteRow(i);
            Set<String> categoriesSet = new HashSet<>();
            database.selectWords().forEach(words -> categoriesSet.add(words.getCategory()));
            List<String> categoriesList  = new LinkedList<>(categoriesSet);
            customComboBoxModel.updateCustomComboboxModel(categoriesList);
        });

        file = new JButton("add file");
        file.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    FileReader fileReader = new FileReader(selectedFile);
                    Scanner sc = new Scanner(fileReader);
                    while(sc.hasNextLine()){
                        String line = sc.nextLine();
                        String[] wordTab = line.split(",");
                        List<Words> newWordsList = new LinkedList<>();
                        Words temp = new Words(0, wordTab[0], wordTab[1], wordTab[2], 0, wordTab[3], 0);
                        newWordsList.add(temp);
                        newWordsList.forEach(words -> database.insertWords(words));
                        model.addRow(temp);
                        model.update(database.selectWords());
                        Set<String> categoriesSet = new HashSet<>();
                        database.selectWords().forEach(words -> categoriesSet.add(words.getCategory()));
                        List<String> categoriesList  = new LinkedList<>(categoriesSet);
                        customComboBoxModel.updateCustomComboboxModel(categoriesList);
                    }

            } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        buttons.add(insert);
        buttons.add(delete);
        buttons.add(file);
        gb.gridx = 0;
        gb.gridy = 2;
        gb.anchor = GridBagConstraints.LINE_START;
        gb.insets = new Insets(5,10,5,0);
        gb.weightx = 1;
        gb.weighty = 1;
        mainPanel.add(buttons, gb);

        add(mainPanel);
        add(addWordsPanel, "addWordsPanel");
    }

    public void addWords(Words word)
    {
        model.addRow(word);
        model.fireTableDataChanged();
    }

    public void update()
    {
        model.update(database.selectWords());
        wordsTable.updateUI();
        wordsTable.repaint();
    }

    public void updateComboboxModel(List<String> wordsList){
        customComboBoxModel.updateCustomComboboxModel(wordsList);
    }

    private JButton back;
    private JButton insert;
    private JButton delete;
    private JButton file;
    private JTable wordsTable;
    private JComboBox filtrCombobox;
    private JCheckBox allWordcheckBox;
    private JLabel allWordsLabel;
    private List<Words> wordsList;
    private List<Words> filteredList;
    private Set<String> categories;
    private List<String> categoriesList;
    private List<String> columnNames;
    private AddWordsPanel addWordsPanel;
    private CustomTableModel model;
    private Database database;
    private static CustomComboBoxModel customComboBoxModel;
}