package com.mehnadnerd.EmpireGame.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import com.mehnadnerd.EmpireGame.ClientMain;
import com.mehnadnerd.EmpireGame.GenericTab;
import com.mehnadnerd.EmpireGame.dialog.BuildDialog;
import com.mehnadnerd.EmpireGame.things.EmpireFleet;
import com.mehnadnerd.EmpireGame.things.EmpireTable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FleetsTab extends GenericTab implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private EmpireTable<EmpireFleet> subtable;
	private ClientMain client;
	private BuildDialog buildDialog;

	/**
	 * Create the panel.
	 */
	public FleetsTab(ClientMain client) {
		super();
		this.client=client;
		
		JScrollPane tableScrollPane = new JScrollPane();
		JScrollPane buttonScrollPane = new JScrollPane();
		this.setPreferredSize(new Dimension(800, 780));
		tableScrollPane.setPreferredSize(new Dimension(600, 780));
		BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(layout);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonScrollPane.setPreferredSize(new Dimension(200, 780));

		// Create all of the buttons
		JButton buildShipButton = new JButton("Build Ship");
		buildShipButton.setActionCommand("StartBuildShip");
		buildShipButton.addActionListener(this);
		buildShipButton.setVerticalTextPosition(JButton.CENTER);
		buildShipButton.setHorizontalTextPosition(JButton.LEADING);
		buttonPanel.add(buildShipButton);
		
		subtable = new EmpireTable<EmpireFleet>(this.client.getPlayer().getPlayerFleets(),EmpireFleet.getDefault());
		table = new JTable();
		table.setModel(subtable);
	
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(600,780));
		tableScrollPane.setViewportView(table);
		buttonScrollPane.setViewportView(buttonPanel);
		this.add(tableScrollPane);
		this.add(buttonScrollPane);
		//this.setOpaque(true);
		
	}

	@Override
	public void completeRegen() {
		subtable.setStorage(this.client.getPlayer().getPlayerFleets());
		
	}
	
	public ClientMain getClient() {
		return client;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("StartBuildShip")) {
			//
			buildDialog=new BuildDialog(this);
			buildDialog.setVisible(true);
		} else if (e.getActionCommand().equalsIgnoreCase("BuildDialogFinished")) {
			client.updatePlayer(client.getComms().build(buildDialog.getShipName(),
			buildDialog.getShipMetal(), buildDialog.getShipPop(), buildDialog.getBuildLoc()));
			buildDialog.dispose();
		}
		
	}

}