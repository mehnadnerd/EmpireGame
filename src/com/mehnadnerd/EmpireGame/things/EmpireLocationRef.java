package com.mehnadnerd.EmpireGame.things;

public class EmpireLocationRef extends EmpireThing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int xLoc;
	private int yLoc;
	private LocationOpinion opinion;
	//private String name=null;
	public EmpireLocationRef() {
		
	}
	public EmpireLocationRef(int xLoc, int yLoc) {
		this.xLoc=xLoc;
		this.yLoc=yLoc;
		//this.loc=this;
	}
	public EmpireLocationRef(String name, int xLoc, int yLoc) {
		this.name=name;
		this.xLoc=xLoc;
		this.yLoc=yLoc;
	}
	@Override
	public EmpireLocationRef getLoc() {
		return this;
	}
	public int getXLoc() {
		return xLoc;
	}
	public int getYLoc() {
		return yLoc;
	}
	public void setXLoc(int xLoc) {
		this.xLoc=xLoc;
	}
	public void setYLoc(int yLoc) {
		this.yLoc=yLoc;
	}
	public LocationOpinion getOpinion() {
		return opinion;
	}
	public void setOpinion(LocationOpinion opinion) {
		this.opinion=opinion;
	}
	
	public int getDistance(EmpireLocationRef r) {
		return Math.abs(r.getXLoc()-xLoc)+Math.abs(r.getYLoc()-yLoc);
	}
	
	@Override
	public int getColumnsNeeded() {
		return 3;
	}
	@Override
	public void updateTick() {
		return;
	}
	@Override
	public String genName() {
		return "Loc"+xLoc+","+yLoc;
	}
	
	@Override
	public String toString() {
		/*if(name==null||name==this.genName()) {*/
			return xLoc+","+yLoc;
		/*}
		return this.name+"@"+xLoc+","+yLoc;*/
	}
	@Override
	public String getName() {
		return this.toString();
	}
	
	@Override
	public Object[] tableForm() {
		Object[] ret = new Object[this.getColumnsNeeded()];
		ret[0]=name;
		ret[1]=xLoc;
		ret[2]=yLoc;
		return ret;
	}
	
	@Override
	public String[] tableHeaders() {
		String[] ret = new String[this.getColumnsNeeded()];
		ret[0]="Name";
		ret[1]="X";
		ret[2]="Y";
		return ret;
	}
	
	public LocationOpinion getLocationOpinion() {
		return this.opinion;
	}
	
	public enum LocationOpinion {
		FRIENDLY, OWNED, NEUTRAL, EMPTY, HOSTILE
	}
	
	public static EmpireLocationRef getDefault() {
		return new EmpireLocationRef(0,0);
	}
	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub
		
	}
	
	public static EmpireLocationRef stringToRef(String string) {
		String[] s = string.split(",");
		return new EmpireLocationRef(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
	}
}