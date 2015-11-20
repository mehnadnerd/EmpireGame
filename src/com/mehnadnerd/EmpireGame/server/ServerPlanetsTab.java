package com.mehnadnerd.EmpireGame.server;

//This should basically be a graphical frontend for planetmanager
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.mehnadnerd.EmpireGame.GenericTab;
import com.mehnadnerd.EmpireGame.dialog.PlanetCreateDialog;
import com.mehnadnerd.EmpireGame.dialog.PlanetGenerateDialog;
import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;

import javax.swing.JScrollPane;

public class ServerPlanetsTab extends GenericTab implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private PlanetManager pm;
	private BoxLayout layout;
	private JButton createPlanetButton;
	private JButton generatePlanetButton;
	private JButton refreshTableButton;
	private JScrollPane buttonScrollPane;
	private JScrollPane tableScrollPane;
	private JPanel buttonPanel;
	private PlanetCreateDialog planetCreateDialog;
	private PlanetGenerateDialog planetGenerateDialog;

	/**
	 * Create the panel.
	 */
	public ServerPlanetsTab(PlanetManager pm) {
		super();
		this.pm = pm;

		tableScrollPane = new JScrollPane();
		buttonScrollPane = new JScrollPane();
		this.setPreferredSize(new Dimension(800, 780));
		tableScrollPane.setPreferredSize(new Dimension(600, 780));
		layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(layout);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonScrollPane.setPreferredSize(new Dimension(200, 780));

		// Create all of the buttons
		createPlanetButton = new JButton("Create Planet");
		createPlanetButton.setActionCommand("CreatePlanet");
		createPlanetButton.addActionListener(this);
		createPlanetButton.setVerticalTextPosition(JButton.CENTER);
		createPlanetButton.setHorizontalTextPosition(JButton.LEADING);

		generatePlanetButton = new JButton("Generate Planet");
		generatePlanetButton.setActionCommand("GeneratePlanet");
		generatePlanetButton.addActionListener(this);
		generatePlanetButton.setVerticalTextPosition(JButton.CENTER);
		generatePlanetButton.setHorizontalTextPosition(JButton.LEADING);
		
		refreshTableButton = new JButton("Refresh Table");
		refreshTableButton.setActionCommand("RefreshTable");
		refreshTableButton.addActionListener(this);
		refreshTableButton.setVerticalTextPosition(JButton.CENTER);
		refreshTableButton.setHorizontalTextPosition(JButton.LEADING);

		table = new JTable();

		//pm.addPlayer(new EmpirePlayer("DefaultTwaggia", new EmpireLocationRef(10, 10)));

		table.setModel(pm.getPlanetsTable());

		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(450, 780));
		tableScrollPane.setViewportView(table);

		buttonPanel.add(createPlanetButton);
		buttonPanel.add(generatePlanetButton);
		buttonPanel.add(refreshTableButton);
		buttonScrollPane.setViewportView(buttonPanel);

		this.add(tableScrollPane);
		this.add(buttonScrollPane);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("CreatePlanet")) {
			System.out.println("Turtles are the best!");
			planetCreateDialog=new PlanetCreateDialog(this);
			planetCreateDialog.setVisible(true);
		} else if (e.getActionCommand().equalsIgnoreCase("GeneratePlanet")) {
			System.out.println("Turtwigs are the best!");
			planetGenerateDialog=new PlanetGenerateDialog(this);
			planetGenerateDialog.setVisible(true);
		}  else if (e.getActionCommand().equalsIgnoreCase("RefreshTable")) {
			pm.regenTable();
		} else if (e.getActionCommand().equalsIgnoreCase("CreatePlanetDone")) {
			pm.addPlanet(planetCreateDialog.getPlanet());
			planetCreateDialog.dispose();
		} else if (e.getActionCommand().equalsIgnoreCase("GeneratePlanetDone")) {
			pm.addPlanet(planetGenerateDialog.getPlanet());
			planetGenerateDialog.dispose();
		}
	}
	
	public EmpirePlanet getPlanetAt(EmpireLocationRef loc) {
		return pm.getPlanet(loc);
	}

	@Override
	public void completeRegen() {
		// TODO Auto-generated method stub

	}

}