package com.mehnadnerd.EmpireGame.things;
/**
 * Shouldn't exist alone, only as a part of a fleet
 * @author JSweeney
 *
 */
public class EmpireShip extends EmpireThing {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient double atk;//current atk (modified)
	private double hp;//current hp (damaged)
	private int popCost;//max atk/cost
	private int metalCost;//max hp/cost
	private int movesLeft;
	
	public EmpireShip() {
		
	}
	
	public double getAtk() {
		return this.atk;
	}
	
	public void setHP(double hp) {
		this.hp=hp;
	}
	
	public double getHP() {
		return this.hp;
	}
	
	public void setPopCost(int popCost) {
		this.popCost = popCost;
	}
	
	public int getPopCost() {
		return this.popCost;
	}
	
	public void setMetalCost(int metalCost) {
		this.metalCost=metalCost;
	}
	
	public int getMetalCost() {
		return this.metalCost;
	}
	
	public void setMovesLeft(int movesLeft) {
		this.movesLeft=movesLeft;
	}
	
	public int getMovesLeft() {
		return movesLeft;
	}
	
	private EmpireShip(int pop, int metal, EmpireLocationRef loc) {
		super.name=genName();
		this.atk = pop;
		this.hp = ((metal-1)*(metal-1)+(metal-1))/20;
		this.movesLeft = 0;
		this.loc = loc;
		this.popCost=pop;
		this.metalCost=metal;
	}
	private EmpireShip(String name, int pop, int metal, EmpireLocationRef loc) {
		
		super.name=name;
		this.atk = pop;
		this.hp = metalToHP(metal);
		this.movesLeft = 0;
		this.loc = loc;
		this.popCost=pop;
		this.metalCost=metal;
	}
	
	public void refreshStats() {
		this.atk = this.popCost-(metalToHP(this.metalCost)-hp);
	}
	
	public static double metalToHP(int metal) {
		return metal-(((double)(metal-1)*(metal-1)+(metal-1)))/20;
	}
	
	@Override
	public int getColumnsNeeded() {
		return 5;
	}

	@Override
	public void updateTick() {
		movesLeft=2;
		this.refreshAll();
	}
	
	public static EmpireShip buildShip(int pop, int metal, EmpireLocationRef loc) {
		return new EmpireShip(pop,metal,loc);
	}
	public static EmpireShip buildShip(String name, int pop, int metal, EmpireLocationRef loc) {
		return new EmpireShip(name,pop,metal,loc);
	}
	@Override
	public String genName() {
		
		return "ShipM"+this.metalCost+"P"+this.popCost+"L"+this.getLoc().toString();
	}

	@Override
	public Object[] tableForm() {
		Object[] ret = new Object[this.getColumnsNeeded()];
		ret[0]=name;
		ret[1]=atk+"/"+popCost;
		ret[2]=hp+"/"+metalToHP(metalCost);
		ret[3]=movesLeft;
		ret[4]=loc;
		return ret;
	}
	
	@Override
	public String[] tableHeaders() {
		String[] ret = new String[this.getColumnsNeeded()];
		ret[0]="Name";
		ret[1]="Atk/Max";
		ret[2]="HP/Max";
		ret[3]="Moves Left";
		ret[4]="Loc";
		return ret;
	}

	public static EmpireShip getDefault() {
		return new EmpireShip(0,0,new EmpireLocationRef(0,0));
	}

	@Override
	public void refreshAll() {
		refreshStats();
		
	}

}