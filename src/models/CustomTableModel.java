package models;

import domain.Words;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CustomTableModel extends AbstractTableModel {

    private List<String> columnNames;
    private List<Words> wordsList;

    public CustomTableModel(List<String> columnNames, List<Words> wordsList)
    {
        this.columnNames = columnNames;
        this.wordsList = wordsList;
    }

    @Override
    public int getRowCount() {
        return wordsList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    public String getColumnName(int col) {
        return columnNames.get(col);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Words words = wordsList.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return words.getCategory();
            case 1: return words.getEnglish();
            case 2: return words.getGerman();
            case 3: return words.getGermanCounter();
            case 4: return words.getFrench();
            case 5: return words.getFrenchCounter();
            default: return null;
        }
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Words words = wordsList.get(rowIndex);
        switch(columnIndex)
        {
            case 0: words.setCategory(value.toString());
            case 1: words.setEnglish(value.toString());
            case 2: words.setGerman(value.toString());
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void update(List<Words> wordsList)
    {
        this.wordsList = wordsList;
        fireTableDataChanged();
    }

    public void addRow(Words rowData)
    {
        wordsList.add(rowData);
        fireTableDataChanged();
    }

    public void deleteRow(int rowNumber)
    {
        wordsList.remove(rowNumber);
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
       return false;
    }
}