package com.mehnadnerd.EmpireGame.server;

import javax.swing.table.AbstractTableModel;

import com.mehnadnerd.EmpireGame.GenericManager;
import com.mehnadnerd.EmpireGame.MainThingy;
import com.mehnadnerd.EmpireGame.ServerMain;
import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;
import com.mehnadnerd.EmpireGame.things.EmpireStorage;
import com.mehnadnerd.EmpireGame.things.EmpireTable;

public class PlayerManager extends GenericManager {
	private EmpireStorage<EmpirePlayer> allPlayers = new EmpireStorage<EmpirePlayer>();
	private EmpireTable<EmpirePlayer> playersTable = new EmpireTable<EmpirePlayer>(allPlayers,EmpirePlayer.getDefault());

	public PlayerManager(MainThingy server) {
		super(server);
	}
	
	public void refreshAllPlayerPlanets() {
		for(EmpirePlayer p: allPlayers) {
			p.setPlayerOwnedPlanets(((ServerMain) main).getPlanetManager().getPlayerPlanets(p));
		}
	}

	public void deletePlayer(String name) {
		System.out.println("Deleting: " + name);
		allPlayers.delete(name);
		//this.regenTable();
	}

	public AbstractTableModel getPlayersTable() {
		return playersTable;
	}

	public void addPlayer(EmpirePlayer player) {
		allPlayers.add(player);
		for(EmpirePlanet p: player.getPlayerOwnedPlanets()) {
			if(!((ServerMain) main).getPlanetManager().planetExists(p)) {
				((ServerMain) main).getPlanetManager().addPlanet(p);
			}
		}
	}

	public void regenTable() {
		playersTable.fireTableStructureChanged();
	}

	public EmpirePlayer getPlayer(String name) {
		return allPlayers.search(name);
	}
	
	public EmpirePlayer getPlayer(EmpireLocationRef loc) {
		return allPlayers.search(loc);
	}

	@Override
	public void updateTick() {
		for (int i = 0; i < allPlayers.size(); i++) {
			allPlayers.get(i).updateTick();
		}
		this.regenTable();
	}
}