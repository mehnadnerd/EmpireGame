package com.mehnadnerd.EmpireGame;

public abstract class GenericManager {
	protected MainThingy main;
	public GenericManager(MainThingy main) {
		this.main=main;
	}
	
	/**
	 * Do the things nessecary to update the turn, or "tick" it over.
	 */
	public abstract void updateTick();
}