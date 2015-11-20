package com.mehnadnerd.EmpireGame.server;

//This is both the tab and manager for Time and tick events
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.mehnadnerd.EmpireGame.GenericTab;
import com.mehnadnerd.EmpireGame.ServerMain;

import javax.swing.JScrollPane;

public class TimeTab extends GenericTab implements ActionListener {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private BoxLayout layout;
	private JButton nextTurnButton;
	private JScrollPane buttonScrollPane;
	private JScrollPane tableScrollPane;
	private JPanel buttonPanel;
	private ServerMain server;
	private int turnNumber;

	/**
	 * Create the panel.
	 */
	public TimeTab(ServerMain server) {
		super();
		this.server=server;

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
		nextTurnButton = new JButton("Next Turn");
		nextTurnButton.setActionCommand("NextTurn");
		nextTurnButton.addActionListener(this);
		nextTurnButton.setVerticalTextPosition(JButton.CENTER);
		nextTurnButton.setHorizontalTextPosition(JButton.LEADING);

		buttonPanel.add(nextTurnButton);
		buttonScrollPane.setViewportView(buttonPanel);

		this.add(tableScrollPane);
		this.add(buttonScrollPane);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("NextTurn")) {
			System.out.println("Ticking to turn: "+(turnNumber+1));
			this.doUpdateTick();
		}  else if (e.getActionCommand().equalsIgnoreCase("RefreshTable")) {
		}

	}
	
	public int getTurnNumber() {
		return this.turnNumber;
	}
	
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
	}

	@Override
	public void completeRegen() {
		// TODO Auto-generated method stub

	}
	/**
	 * The master updateTick() function for the server. Calling this will call all other updateTick()s, and will
	 * advance the turn count. This should be the only updateTick() called, it will call the others.
	 */
	public void doUpdateTick() {
		turnNumber++;
		server.updateTick();
	}

}