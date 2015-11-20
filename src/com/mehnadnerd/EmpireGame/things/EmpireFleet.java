package com.mehnadnerd.EmpireGame.things;

import java.util.Random;

public class EmpireFleet extends EmpireThing {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpireStorage<EmpireShip> ships = new EmpireStorage<EmpireShip>();
	private double atkTotal;
	private double hpTotal;
	private int popCost;
	private int metalCost;
	private int movesLeft;
	
	public EmpireFleet() {
		
	}
	
	public EmpireFleet(EmpireShip ship) {
		ships.add(ship);
		this.setLoc(ship.getLoc());
		this.movesLeft=ship.getMovesLeft();
		this.setName("Fleet of "+ship.getName());
		this.refreshAll();
	}
	
	public double getAtkTotal() {
		return this.atkTotal;
	}
	
	/**
	 * Ignores number put in and regens
	 * @param newAtkTotal
	 */
	public void setAtkTotal(double newAtkTotal) {
		this.refreshStats();
		return;
	}
	
	public double getHPTotal() {
		return this.atkTotal;
	}
	
	/**
	 * Ignores number put in and regens
	 * @param newAtkTotal
	 */
	public void setHPTotal(double newHPTotal) {
		this.refreshStats();
		return;
	}
	
	public int getPopCostTotal() {
		return this.popCost;
	}
	
	/**
	 * Ignores number put in and regens
	 * @param newAtkTotal
	 */
	public void setPopCostTotal(int newPopTotal) {
		this.refreshStats();
		return;
	}
	
	public int getMetalCostTotal() {
		return this.metalCost;
	}
	
	/**
	 * Ignores number put in and regens
	 * @param newAtkTotal
	 */
	public void setMetalCostTotal(int newMetalTotal) {
		this.refreshStats();
		return;
	}

	public void setMovesLeft(int movesLeft) {
		this.movesLeft=movesLeft;
	}
	
	public int getMovesLeft() {
		return movesLeft;
	}
	
	/**
	 * Beans compliant, shouldn't use directly
	 * @param ships
	 */
	public void setShips(EmpireStorage<EmpireShip> ships) {
		this.ships=ships;
	}
	
	/**
	 * Beans compliant, shouldn't use directly
	 * @return
	 */
	public EmpireStorage<EmpireShip> getShips() {
		return ships;
	}
	
	public EmpireFleet(EmpireLocationRef loc) {
		movesLeft = 0;
		this.loc = loc;
		super.name=this.genName();
	}
	public EmpireFleet(String name, EmpireLocationRef loc) {
		movesLeft = 0;
		this.loc = loc;
		super.name = name;
	}

	@Override
	public int getColumnsNeeded() {
		return 6;
	}

	@Override
	public void updateTick() {
		movesLeft = 2;
		refreshAll();
	}

	public void refreshStats() {
		atkTotal = 0;
		hpTotal = 0;
		popCost = 0;
		metalCost = 0;
		for (EmpireShip ship : ships) {
			atkTotal += ship.getAtk();
			hpTotal += ship.getHP();
			popCost += ship.getPopCost();
			metalCost += ship.getMetalCost();
		}
	}

	public void refreshLocs() {
		for (EmpireShip ship : ships) {
			ship.setLoc(this.loc);
		}
	}

	@Override
	public String genName() {
		Random r = new Random();
		return "FleetA" + atkTotal + "D" + hpTotal + "L" + this.getLoc() + "#" + (r.nextInt(10000)+1);
	}

	@Override
	public Object[] tableForm() {
		Object[] ret = new Object[this.getColumnsNeeded()];
		ret[0] = this.getName();
		ret[1] = ships.size();
		ret[2] = atkTotal+"/"+popCost;
		ret[3] = hpTotal+"/"+EmpireShip.metalToHP(metalCost);
		ret[4] = movesLeft;
		ret[5] = this.getLoc();
		return ret;
	}
	
	@Override
	public String[] tableHeaders() {
		String[] ret = new String[this.getColumnsNeeded()];
		ret[0]="Name";
		ret[1]="# Ships";
		ret[2]="Atk/Max";
		ret[3]="HP/Max";
		ret[4]="Moves Left";
		ret[5]="Loc";
		return ret;
	}

	public void addShip(EmpireShip ship) {
		ships.add(ship);
		refreshStats();
	}

	public int getNumShips() {
		return ships.size();
	}

	/**
	 * Attempts to move the fleet
	 * 
	 * @param newLoc
	 *            An EmpireLocationRef for the destination
	 * @return true, if succeeds, false if fails
	 */
	public boolean move(EmpireLocationRef newLoc) {
		if (movesLeft > 0) {
			switch (loc.getDistance(newLoc)) {
			case 1:
				loc = newLoc;
				movesLeft--;
				return true;
			case 2:
				loc = newLoc;
				movesLeft--;
				return true;
			default:
				return false;
			}
		} else {
			return false;
		}

	}

	public static EmpireFleet getDefault() {
		return new EmpireFleet(EmpireShip.getDefault());
	}

	@Override
	public void refreshAll() {
		this.refreshLocs();
		this.refreshStats();
		
	}

}