package com.mehnadnerd.EmpireGame.things;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class EmpireStorage<T extends EmpireThing> implements Iterable<T>, Serializable {
	protected ArrayList<T> storage = new ArrayList<T>();
	private static final long serialVersionUID = 1L;
	
	public EmpireStorage() {
	}
	/*
	public EmpireStorage<T>(Class<T>) {
		
	}*/
	
	public T search(String name) {
		for(int i = 0; i<storage.size(); i++) {
			if(storage.get(i).getName().equalsIgnoreCase(name)) {
				return storage.get(i);
			}
		}
		return null;
	}
	
	/**
	 * For beans compliance, please don't use
	 * @param newStorage
	 */
	public void setStorage(ArrayList<T> newStorage) {
		this.storage=newStorage;
	}
	
	/**
	 * Don't get this directly
	 * @return
	 */
	public ArrayList<T> getStorage() {
		return this.storage;
	}
	
	public void delete(T t) {
		storage.remove(t);
	}
	
	/**
	 * Delete function by name
	 * @param name
	 */
	public void delete(String name) {
		System.out.println("Before Delete: "+storage);
		this.delete(this.search(name));
		System.out.println("After Delete: "+storage);
		
	}
	
	public T search(EmpireLocationRef loc) {
		for(int i = 0; i<storage.size(); i++) {
			if(storage.get(i).getLoc().getDistance(loc)==0) {
				return storage.get(i);
			}
		}
		return null;
	}
	public EmpireStorage<T> getAllWithOwner(EmpirePlayer owner) {
		EmpireStorage<T> toReturn = new EmpireStorage<T>();
		for(int i = 0; i<storage.size(); i++) {
			if(storage.get(i).getOwner().equals(owner)) {
				toReturn.add(storage.get(i));
			}
		}
		return toReturn;
	}
	
	public T get(int index) {
		
		return storage.get(index);
		
	}
	
	
	public void add(T thing) {
		storage.add(thing);
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public int size() {
		return storage.size();
	}
	
	public boolean contains(T t) {
		return storage.contains(t);
	}
	
	@Override
	public String toString() {
		return "EmpireStorage: "+storage;
	}
	
	@Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < storage.size() && storage.get(currentIndex) != null;
            }

            @Override
            public T next() {
                return storage.get(currentIndex++);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }
	
	public int indexOf(T t) {
		return storage.indexOf(t);
	}
}