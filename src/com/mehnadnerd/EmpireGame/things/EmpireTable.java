package com.mehnadnerd.EmpireGame.things;

import javax.swing.table.AbstractTableModel;

/**
 * A StandardTable that can be used as an AbstractTable Model, that is backed by an
 * EmpireStorage and will update based on it.
 * @author JSweeney
 * @param <T>
 *
 */
public class EmpireTable<T extends EmpireThing> extends AbstractTableModel {
	private EmpireStorage<T> storage;
	private final T defaultT;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmpireTable(EmpireStorage<T> storage, T defaultT) {
		this.storage=storage;
		this.defaultT=defaultT;
	}
	
	
	public EmpireStorage<T> getStorage() {
		return storage;
	}
	
	public void setStorage(EmpireStorage<T> storage) {
		this.storage=storage;
		this.fireTableStructureChanged();
	}

	@Override
	public int getRowCount() {
		return storage.size();
	}

	@Override
	public int getColumnCount() {
		//T.getColumnsNeeded();
		//System.out.println("Column Count: "+defaultT.getColumnsNeeded());
		return defaultT.getColumnsNeeded();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return storage.get(rowIndex).tableForm()[columnIndex];
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		try {
			return defaultT
				.tableForm()[c]
						.getClass();
		} catch (NullPointerException e) {
			System.out.println("DefaultT:"+defaultT);
			System.out.println("DefaultT Table:"+defaultT.tableForm());
			for(Object o: defaultT.tableForm()) {
				System.out.println("Object:"+o.toString());
			}
		}
		return null;
		
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	@Override
	public String getColumnName(int col) {
		return defaultT.tableHeaders()[col];
	}
	
	

}