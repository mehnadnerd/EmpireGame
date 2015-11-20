package com.mehnadnerd.EmpireGame.things;

//import java.io.Externalizable;
//import java.io.ObjectOutput;
import java.io.Serializable;

public abstract class EmpireThing implements /*Externalizable,*/ Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected EmpirePlayer owner/* = EmpirePlayer.getDefault()*/;
	protected String name;
	protected EmpireLocationRef loc;
	public abstract /*static*/ int getColumnsNeeded()/* {
		return 0;
	}*/;
	
	public EmpireThing() {
		
	}
	
	public EmpireLocationRef getLoc() {
		if(this.loc==null) {
			return EmpireLocationRef.getDefault();
		}
		return this.loc;
	}
	public void setLoc(EmpireLocationRef newLoc) {
		this.loc=newLoc;
	}
	public String getName() {
		return name;
	}
	public void setName(String newName) {
		this.name=newName;
	}
	public EmpirePlayer getOwner() {
		if(this.owner==null) {
			return EmpirePlayer.getDefault();
		}
		return owner;
	}
	public void setOwner(EmpirePlayer newOwner) {
		this.owner=newOwner;
	}
	
	public static EmpireThing getDefault() {
		return null;
	}
	
	/*
	@Override
	public abstract void writeExternal(ObjectOutput out);*/
	/**
	 * Called to update everything with a turn ticking over.
	 */
	public abstract void updateTick();
	public abstract String genName();
	public abstract Object[] tableForm();
	public abstract String[] tableHeaders();
	
	/**
	 * Refreshes all stats, etc. MUST be idempotent, with no significant side effects
	 */
	public abstract void refreshAll();
	
	//public abstract <T extends EmpireThing> T fromSendString(String s);
	//public abstract String toSendString();
}