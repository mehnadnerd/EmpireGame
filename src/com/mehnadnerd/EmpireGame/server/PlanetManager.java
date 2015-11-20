package com.mehnadnerd.EmpireGame.server;

import javax.swing.table.AbstractTableModel;

import com.mehnadnerd.EmpireGame.GenericManager;
import com.mehnadnerd.EmpireGame.MainThingy;
import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;
import com.mehnadnerd.EmpireGame.things.EmpireStorage;
import com.mehnadnerd.EmpireGame.things.EmpireTable;

public class PlanetManager extends GenericManager {
	private EmpireStorage<EmpirePlanet> allPlanets = new EmpireStorage<EmpirePlanet>();
	private EmpireTable<EmpirePlanet> planetsTable2 = new EmpireTable<EmpirePlanet>(allPlanets,EmpirePlanet.getDefault());
	//private StandardTable planetsTable;

	public PlanetManager(MainThingy main) {
		super(main);
	}

	public void deletePlanet(String name) {
		System.out.println("Deleting: " + name);
		allPlanets.delete(name);
		//this.regenTable();
	}

	/*
	public StandardTable getPlanetsTable() {
		return planetsTable;
	}*/
	public AbstractTableModel getPlanetsTable() {
		return planetsTable2;
	}

	public void addPlanet(EmpirePlanet planet) {
		allPlanets.add(planet);
		//planetsTable.addRow(planet.tableForm());
	}

	public void regenTable() {
		/*
		planetsTable.clearData();
		for (int i = 0; i < allPlanets.size(); i++) {
			planetsTable.addRow(allPlanets.get(i).tableForm());
		}
		planetsTable.fireTableStructureChanged();*/
	}
	
	public EmpireStorage<EmpirePlanet> getPlayerPlanets(EmpirePlayer p) {
		return allPlanets.getAllWithOwner(p);
	}

	public EmpirePlanet getPlanet(String name) {
		return allPlanets.search(name);
	}
	
	public EmpirePlanet getPlanet(EmpireLocationRef loc) {
		return allPlanets.search(loc);
	}
	//EmpirePlanets with EmpirePlayer.getDefault() as their EmpirePlayer are unowned

	@Override
	public void updateTick() {
		for (int i = 0; i < allPlanets.size(); i++) {
			allPlanets.get(i).updateTick();
		}
		this.regenTable();
	}
	
	public boolean planetExists(EmpirePlanet p) {
		return allPlanets.contains(p);
	}
}