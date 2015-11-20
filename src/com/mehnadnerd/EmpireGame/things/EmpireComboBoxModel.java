package com.mehnadnerd.EmpireGame.things;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

@SuppressWarnings("rawtypes")
public class EmpireComboBoxModel<T extends EmpireThing> extends AbstractListModel implements ComboBoxModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpireStorage<T> storage;
	private int selectedIndex;
	public EmpireComboBoxModel(EmpireStorage<T> empireStorage) {
		this.storage=empireStorage;
	}
	
	@Override
	public int getSize() {
		return storage.size();
	}

	@Override
	public Object getElementAt(int index) {
		return storage.get(index);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(Object anItem) {
		this.selectedIndex=storage.indexOf((T)anItem);
		
	}

	@Override
	public Object getSelectedItem() {
		return storage.get(selectedIndex);
	}
}