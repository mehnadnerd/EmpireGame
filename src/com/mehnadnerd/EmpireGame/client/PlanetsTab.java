package com.mehnadnerd.EmpireGame.client;

import java.awt.Dimension;
import javax.swing.JTable;

import com.mehnadnerd.EmpireGame.ClientMain;
import com.mehnadnerd.EmpireGame.GenericTab;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;
import com.mehnadnerd.EmpireGame.things.EmpireTable;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class PlanetsTab extends GenericTab {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable ownedTable;
	private JTable discoveredTable;
	private ClientMain client;
	private EmpireTable<EmpirePlanet> ownedSubtable;
	private EmpireTable<EmpirePlanet> discoveredSubtable;
	private JPanel ownedPanel;
	private JPanel discoveredPanel;

	/**
	 * Create the panel.
	 * 
	 * @param clientMain
	 */
	public PlanetsTab(ClientMain clientMain) {
		//initialisation stuffs
		super();
		this.client=clientMain;
		this.setPreferredSize(new Dimension(800, 780));
		JTabbedPane thisPane = new JTabbedPane();
		thisPane.setPreferredSize(new Dimension(800, 780));
		//create owned panel
		ownedPanel = new JPanel();
		ownedPanel.setPreferredSize(new Dimension(800, 780));
		JScrollPane ownedTableScrollPane = new JScrollPane();
		JScrollPane ownedButtonScrollPane = new JScrollPane();
		ownedTableScrollPane.setPreferredSize(new Dimension(600, 780));
		ownedPanel.setLayout(new BoxLayout(ownedPanel, BoxLayout.LINE_AXIS));
		JPanel ownedButtonPanel = new JPanel();
		ownedButtonPanel.setLayout(new BoxLayout(ownedButtonPanel, BoxLayout.PAGE_AXIS));
		ownedButtonScrollPane.setPreferredSize(new Dimension(200, 780));
		
		ownedSubtable = new EmpireTable<EmpirePlanet>(client.getPlayer().getPlayerOwnedPlanets(),
				EmpirePlanet.getDefault());
		ownedTable = new JTable(ownedSubtable);
		ownedTable.setFillsViewportHeight(true);
		ownedTable.setPreferredScrollableViewportSize(new Dimension(450, 780));
		ownedTableScrollPane.setViewportView(ownedTable);

		ownedPanel.add(ownedTableScrollPane);
		ownedPanel.add(ownedButtonScrollPane);

		thisPane.addTab("Owned Planets", ownedPanel);
		//create discovered panel
		discoveredPanel = new JPanel();
		discoveredPanel.setPreferredSize(new Dimension(800, 780));
		JScrollPane discoveredTableScrollPane = new JScrollPane();
		JScrollPane discoveredButtonScrollPane = new JScrollPane();
		discoveredTableScrollPane.setPreferredSize(new Dimension(600, 780));
		discoveredPanel.setLayout(new BoxLayout(discoveredPanel, BoxLayout.LINE_AXIS));
		JPanel discoveredButtonPanel = new JPanel();
		discoveredButtonPanel.setLayout(new BoxLayout(discoveredButtonPanel, BoxLayout.PAGE_AXIS));
		discoveredButtonScrollPane.setPreferredSize(new Dimension(200, 780));
		
		discoveredSubtable = new EmpireTable<EmpirePlanet>(client.getPlayer().getPlayerOwnedPlanets(),
				EmpirePlanet.getDefault());
		discoveredTable = new JTable(discoveredSubtable);
		discoveredTable.setFillsViewportHeight(true);
		discoveredTable.setPreferredScrollableViewportSize(new Dimension(450, 780));
		discoveredTableScrollPane.setViewportView(discoveredTable);

		discoveredPanel.add(discoveredTableScrollPane);
		discoveredPanel.add(discoveredButtonScrollPane);

		thisPane.addTab("Discovered Planets", discoveredPanel);
		
		// this.setOpaque(true);
		this.add(thisPane);
	}

	@Override
	public void completeRegen() {
		// System.out.println("Regen planets tab");
		// System.out.println(this.client.getPlayer().getPlayerOwnedPlanets());
		ownedSubtable.setStorage(this.client.getPlayer().getPlayerOwnedPlanets());
		discoveredSubtable.setStorage(this.client.getPlayer().getPlayerDiscoveredPlanets());
		//System.out.println("Planets: " + this.client.getPlayer().getPlayerOwnedPlanets());

	}

}