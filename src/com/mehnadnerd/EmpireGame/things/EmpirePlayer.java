package com.mehnadnerd.EmpireGame.things;

import java.util.ArrayList;

import com.mehnadnerd.EmpireGame.things.EmpirePlanet.Biome;

@SuppressWarnings("unused")
public class EmpirePlayer extends EmpireThing {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmpireStorage<EmpireFleet> playerFleets = new EmpireStorage<EmpireFleet>();
	private EmpireStorage<EmpirePlanet> playerOwnedPlanets = new EmpireStorage<EmpirePlanet>();
	private EmpireStorage<EmpirePlanet> playerDiscoveredPlanets = new EmpireStorage<EmpirePlanet>();
	private int metal;
	private int fuel;
	private int food;
	private int pop;
	private double money;
	private boolean turnFinished;

	public EmpirePlayer() {

	}

	/**
	 * Constructor used for designed creation of player
	 * 
	 * @param name
	 * @param loc
	 */
	public EmpirePlayer(String name, EmpireLocationRef loc) {
		super.name = name;
		// super.owner = this;
		turnFinished = true;
		this.loc = loc;
		this.metal = 10;
		this.fuel = 10;
		this.food = 10;
		this.pop = 10;
		this.money = 0;
		this.addPlanet(new EmpirePlanet(name + " Planet", 2, 2, 2, Biome.CAPITAL, loc, this));
	}

	/*
	 * public EmpirePlayer(String name, EmpireLocationRef loc, int metal, int
	 * fuel, int food, int pop) { super.name = name; //super.owner = this;
	 * turnFinished = true; this.loc = loc; this.metal = metal; this.fuel =
	 * fuel; this.food = food; this.pop = pop; }
	 */

	@Override
	public EmpirePlayer getOwner() {
		return this;
	}

	public EmpireStorage<EmpireFleet> getPlayerFleets() {
		return playerFleets;
	}

	public void setPlayerFleets(EmpireStorage<EmpireFleet> playerFleets) {
		this.playerFleets = playerFleets;
	}

	public EmpireStorage<EmpirePlanet> getPlayerOwnedPlanets() {
		return playerOwnedPlanets;
	}

	public void setPlayerOwnedPlanets(EmpireStorage<EmpirePlanet> playerOwnedPlanets) {
		this.playerOwnedPlanets = playerOwnedPlanets;
	}

	public EmpireStorage<EmpirePlanet> getPlayerDiscoveredPlanets() {
		return playerDiscoveredPlanets;
	}

	public void setPlayerDiscoveredPlanets(EmpireStorage<EmpirePlanet> playerDiscoveredPlanets) {
		this.playerDiscoveredPlanets = playerDiscoveredPlanets;
	}

	public void setMetal(int metal) {
		this.metal = metal;
	}

	public int getMetal() {
		return metal;
	}

	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getFuel() {
		return fuel;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getFood() {
		return food;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getMoney() {
		return money;
	}

	public int getPop() {
		return pop;
	}

	public void setPop(int pop) {
		this.pop = pop;
	}

	public void setTurnFinished(boolean turnFinished) {
		this.turnFinished = turnFinished;
	}

	public boolean getTurnFinished() {
		return turnFinished;
	}

	@Override
	public int getColumnsNeeded() {
		return 8;
	}

	@Override
	public void updateTick() {
		turnFinished = false;
		this.food -= this.pop / 10;
		for (EmpirePlanet p : playerOwnedPlanets) {
			p.updateTick();
			this.metal += p.getMetalProd();
			this.fuel += p.getFuelProd();
			this.food += p.getFoodProd();
		}
		for (EmpireFleet f : playerFleets) {
			f.updateTick();
		}
		this.refreshAll();
	}

	@Override
	public String genName() {
		return "!GeneratedName";
	}

	@Override
	public Object[] tableForm() {
		Object[] ret = new Object[this.getColumnsNeeded()];
		ret[0] = name;
		ret[1] = metal;
		ret[2] = fuel;
		ret[3] = food;
		ret[4] = pop;
		ret[5] = money;
		ret[6] = turnFinished;
		ret[7] = this.loc;
		return ret;
	}

	@Override
	public String[] tableHeaders() {
		String[] ret = new String[this.getColumnsNeeded()];
		ret[0] = "Name";
		ret[1] = "Metal";
		ret[2] = "Fuel";
		ret[3] = "Food";
		ret[4] = "Pop";
		ret[5] = "Money";
		ret[6] = "Turn Finished";
		ret[7] = "Home";
		return ret;
	}

	@Override
	public String toString() {
		return "EmpirePlayer|" + name + "|Loc" + this.getLoc() + "|Rsc|" + metal + "|" + fuel + "|" + food + "|"
				+ pop + "|" + money + "|Done:"+turnFinished;
	}

	public static EmpirePlayer getDefault() {
		return new EmpirePlayer("Default Player", EmpireLocationRef.getDefault());
	}

	@Override
	public void refreshAll() {
		for (EmpirePlanet p : playerOwnedPlanets) {
			p.refreshAll();
			if (p.getOwner() != this) {
				p.setOwner(this);
			}

		}
		for (EmpirePlanet p : playerDiscoveredPlanets) {
			if (p.getOwner() == this) {
				playerOwnedPlanets.add(p);
				playerDiscoveredPlanets.delete(p);
			}
		}
		for (EmpireFleet f : playerFleets) {
			f.refreshAll();
		}

	}

	public void addPlanet(EmpirePlanet p) {
		playerOwnedPlanets.add(p);
	}

	public void addFleet(EmpireFleet f) {
		if (f.getName().equalsIgnoreCase(f.genName())) {
			f.setName("Fleet" + (playerFleets.size() + 1));
		}
		playerFleets.add(f);
	}

	public void discoverPlanet(EmpirePlanet p) {
		playerDiscoveredPlanets.add(p);
	}

	/**
	 * A better form of discoverPlanet. Checks if it is already discovered or
	 * owned, and will only add if new.
	 * 
	 * @param p
	 *            Planet that COULD be a new discovery
	 */
	public void betterDiscover(EmpirePlanet p) {
		if (this.isPlanetDiscovered(p) || p.getOwner().equals(this)) {
			return;
		}
		discoverPlanet(p);
	}

	public boolean isPlanetDiscovered(EmpirePlanet p) {
		return playerDiscoveredPlanets.contains(p)||playerOwnedPlanets.contains(p);
	}

	public boolean isPlanetOwned(EmpirePlanet p) {
		return playerOwnedPlanets.contains(p);
	}

	public boolean moveFleet(String fleetName, EmpireLocationRef newLoc) {
		return playerFleets.search(fleetName).move(newLoc);
	}

	public boolean spendPop(int toSpend) {
		if (this.pop >= toSpend) {
			this.pop -= toSpend;
			return true;
		} else {
			return false;
		}
	}

	public boolean spendMetal(int toSpend) {
		if (this.metal >= toSpend) {
			this.metal -= toSpend;
			return true;
		} else {
			return false;
		}

	}

	public boolean spendFuel(int toSpend) {
		if (this.fuel >= toSpend) {
			this.fuel -= toSpend;
			return true;
		} else {
			return false;
		}

	}

	public boolean spendFood(int toSpend) {
		if (this.food >= toSpend) {
			this.food -= toSpend;
			return true;
		} else {
			return false;
		}

	}

	public boolean spendMoney(double toSpend) {
		if (this.money >= toSpend) {
			this.money -= toSpend;
			return true;
		} else {
			return false;
		}

	}

}