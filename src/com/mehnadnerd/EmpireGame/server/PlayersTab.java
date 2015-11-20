package com.mehnadnerd.EmpireGame.server;

//This should basically be a graphical frontend for playermanager
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.mehnadnerd.EmpireGame.GenericTab;
import com.mehnadnerd.EmpireGame.dialog.PlayerCreateDialog;

import javax.swing.JScrollPane;

public class PlayersTab extends GenericTab implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	// private StandardTable subtable;
	private PlayerManager pm;
	private BoxLayout layout;
	private JButton createPlayerButton;
	private JButton deletePlayerButton;
	private JButton refreshTableButton;
	private JScrollPane buttonScrollPane;
	private JScrollPane tableScrollPane;
	private JPanel buttonPanel;
	private PlayerCreateDialog playerCreateDialog;

	/**
	 * Create the panel.
	 */
	public PlayersTab(PlayerManager pm) {
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
		createPlayerButton = new JButton("Create Player");
		createPlayerButton.setActionCommand("CreatePlayer");
		createPlayerButton.addActionListener(this);
		createPlayerButton.setVerticalTextPosition(JButton.CENTER);
		createPlayerButton.setHorizontalTextPosition(JButton.LEADING);

		deletePlayerButton = new JButton("Delete Player");
		deletePlayerButton.setActionCommand("DeletePlayer");
		deletePlayerButton.addActionListener(this);
		deletePlayerButton.setVerticalTextPosition(JButton.CENTER);
		deletePlayerButton.setHorizontalTextPosition(JButton.LEADING);
		
		refreshTableButton = new JButton("Refresh Table");
		refreshTableButton.setActionCommand("RefreshTable");
		refreshTableButton.addActionListener(this);
		refreshTableButton.setVerticalTextPosition(JButton.CENTER);
		refreshTableButton.setHorizontalTextPosition(JButton.LEADING);

		table = new JTable();

		table.setModel(this.pm.getPlayersTable());

		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(450, 780));
		tableScrollPane.setViewportView(table);

		buttonPanel.add(createPlayerButton);
		buttonPanel.add(deletePlayerButton);
		buttonPanel.add(refreshTableButton);
		buttonScrollPane.setViewportView(buttonPanel);

		this.add(tableScrollPane);
		this.add(buttonScrollPane);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("CreatePlayer")) {
			//System.out.println("Turtles are the best!");
			playerCreateDialog=new PlayerCreateDialog(this);
			playerCreateDialog.setVisible(true);
		} else if (e.getActionCommand().equalsIgnoreCase("DeletePlayer")) {
			//System.out.println("Turtwigs are the best!");
			pm.deletePlayer(pm.getPlayersTable().getValueAt(table.getSelectedRow(),0).toString());
		}  else if (e.getActionCommand().equalsIgnoreCase("RefreshTable")) {
			pm.regenTable();
		} else if (e.getActionCommand().equalsIgnoreCase("CreatePlayerDone")) {
			pm.addPlayer(playerCreateDialog.getPlayer());
			playerCreateDialog.dispose();
		}

	}

	@Override
	public void completeRegen() {
		// TODO Auto-generated method stub

	}

}