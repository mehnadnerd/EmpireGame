package com.mehnadnerd.EmpireGame.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.mehnadnerd.EmpireGame.ClientMain;
import com.mehnadnerd.EmpireGame.GenericTab;

public class StatusTab extends GenericTab implements ActionListener {
	private ClientMain client;
	private JLabel[] resources;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StatusTab(ClientMain clientMain) {
		super();
		this.client = clientMain;
		resources = new JLabel[] { new JLabel("Metal: "), new JLabel("Fuel: "), new JLabel("Food: "),
				new JLabel("Pop: "), new JLabel("Money: ")};
		for (JLabel l : resources) {
			this.add(l);
		}
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(this);
		endTurnButton.setActionCommand("TurnFinish");
		this.add(endTurnButton);
		
		JButton refreshPlayerButton = new JButton("Refresh Player");
		refreshPlayerButton.addActionListener(this);
		refreshPlayerButton.setActionCommand("Refresh");
		this.add(refreshPlayerButton);

		this.completeRegen();
	}

	@Override
	public void completeRegen() {
		// TODO Auto-generated method stub
		for (int i = 0; i < resources.length; i++) {
			resources[i].setText(resources[i].getText().replaceAll("[\\d\\.]+", "") + getResourceAmount(i));
		}

	}

	private Object getResourceAmount(int i) {
		switch (i) {
		case 0:
			return client.getPlayer().getMetal();
		case 1:
			return client.getPlayer().getFuel();
		case 2:
			return client.getPlayer().getFood();
		case 3:
			return client.getPlayer().getPop();
		case 4:
			return client.getPlayer().getMoney();
		default:
			return 0;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("TurnFinish")) {
			//Tell client to end turn
			client.getComms().ready();
		} else if (e.getActionCommand().equalsIgnoreCase("Refresh")) {
			client.reDownloadPlayer();
			
		}
		
	}

}