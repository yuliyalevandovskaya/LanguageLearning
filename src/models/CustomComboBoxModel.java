package models;

import javax.swing.*;
import java.util.List;

public class CustomComboBoxModel<T> extends AbstractListModel<T> implements ComboBoxModel<T> {

    private List<T> itemsList;
    private T selectedItem;

    public CustomComboBoxModel(List<T> itemsList)
    {
        this.itemsList = itemsList;
    }


    @Override
    public void setSelectedItem(Object anItem) {
        this.selectedItem = (T) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selectedItem;
    }

    @Override
    public int getSize() {
        return itemsList.size();
    }

    @Override
    public T getElementAt(int index) {
        return itemsList.get(index);
    }

    public void updateCustomComboboxModel(List<T> itemsList){
        this.itemsList = itemsList;
        fireContentsChanged(this, 0, itemsList.size() -1);
    }
}