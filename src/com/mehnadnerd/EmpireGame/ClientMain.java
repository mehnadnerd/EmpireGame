package com.mehnadnerd.EmpireGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.mehnadnerd.EmpireGame.client.ClientComms;
import com.mehnadnerd.EmpireGame.client.FleetsTab;
import com.mehnadnerd.EmpireGame.client.MoveTab;
import com.mehnadnerd.EmpireGame.client.NotificationsTab;
import com.mehnadnerd.EmpireGame.client.PlanetsTab;
import com.mehnadnerd.EmpireGame.client.StatusTab;
import com.mehnadnerd.EmpireGame.dialog.GenericEmpireDialog;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;

public class ClientMain extends MainThingy implements ActionListener {

	private JFrame mainWindow;
	private JMenuBar mainMenuBar;
	private JMenu settingsMenu;
	private JTabbedPane mainTabbedPane;
	private FleetsTab fleets;
	private PlanetsTab planets;
	private EmpirePlayer player = EmpirePlayer.getDefault();
	private MoveTab moves;
	private StatusTab status;
	private NotificationsTab notify;
	private ClientComms comms;
	private int turnNumber;
	private JMenuItem menuSetIP;
	private JMenuItem menuSetPlayer;
	private GenericEmpireDialog getIPDialog;
	private GenericEmpireDialog getPlayerDialog;

	ClientMain() {
		//create the Comms
		comms=new ClientComms(this,"http://localhost:8192");
		
		//create window
		mainWindow = new JFrame("The Empire Game");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create menu bar and items
		menuSetIP=new JMenuItem("Set Server IP");
		menuSetIP.addActionListener(this);
		menuSetIP.setActionCommand("SetIP");
		menuSetPlayer=new JMenuItem("Set Player");
		menuSetPlayer.addActionListener(this);
		menuSetPlayer.setActionCommand("SetPlayer");
		settingsMenu = new JMenu("Settings");
		settingsMenu.add(menuSetIP);
		settingsMenu.add(menuSetPlayer);
		
		//create menu bar
		mainMenuBar = new JMenuBar();
        mainMenuBar.setOpaque(true);
        mainMenuBar.setBackground(new Color(0x5f,0xff,0x8f));
        mainMenuBar.setPreferredSize(new Dimension(800, 20));
        mainMenuBar.add(settingsMenu);
        
        mainWindow.setJMenuBar(mainMenuBar);
      
        
        //create tabbed pane
        mainTabbedPane = new JTabbedPane();
        //create tabs
        status = new StatusTab(this);
        fleets = new FleetsTab(this);
        planets = new PlanetsTab(this);
        moves = new MoveTab(this);
        notify = new NotificationsTab(this);
        
        //add the tabs
        mainTabbedPane.addTab("Status", status);
        mainTabbedPane.addTab("Fleets",fleets);
        mainTabbedPane.addTab("Planets", planets);
        mainTabbedPane.addTab("Movements", moves);
        mainTabbedPane.addTab("Notifications",notify);
        //mainTabbedPane.addTab("Map", null);
        
        //add the tabbed pane to the window
        mainWindow.add(mainTabbedPane);        
	}

	@Override
	public void run() {
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
	
	public ClientComms getComms() {
		return this.comms;
	}
	
	public int getTurnNumber() {
		return turnNumber;
	}
	
	public void setTurnNumber(int turnNumber) {
		this.turnNumber=turnNumber;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("SetIP")) {
			getIPDialog=new GenericEmpireDialog("Change IP", new JLabel[] {new JLabel("New IP Address")}, new JTextField[] {new JTextField("localhost:8192")}, this, "Set IP", "SetIPDone");
			getIPDialog.setVisible(true);
		} else if(e.getActionCommand().equalsIgnoreCase("SetIPDone")) {
			comms.setIP("http://"+getIPDialog.getAnswer(0));
			getIPDialog.dispose();
		}
		if (e.getActionCommand().equalsIgnoreCase("SetPlayer")) {
			getPlayerDialog=new GenericEmpireDialog("Set Player", new JLabel[] {new JLabel("Player name")}, new JTextField[] {new JTextField("Twaggia")}, this, "Set Player", "SetPlayerDone");
			getPlayerDialog.setVisible(true);
		} else if(e.getActionCommand().equalsIgnoreCase("SetPlayerDone")) {
			this.player=comms.getPlayer(getPlayerDialog.getAnswer(0));
			System.out.println("Player is now: "+this.player);
			getPlayerDialog.dispose();
			this.regenAll();
		}
		
	}
	
	public EmpirePlayer getPlayer() {
		return player;
	}
	
	/**
	 * Forces a redownload of the player from the server
	 */
	public void reDownloadPlayer() {
		this.updatePlayer(comms.getPlayer(player.getName()));
	}
	
	/**
	 * Changes the player if the player isn't default, use after server requests
	 * @param p The new player (or default player)
	 */
	public void updatePlayer(EmpirePlayer p) {
		System.out.println("New player: "+p);
		if(!p.equals(EmpirePlayer.getDefault())) {
			this.player=p;
			this.regenAll();
		}
	}
	
	public void regenAll() {
		//System.out.println("RegenAll triggered");
		fleets.completeRegen();
		planets.completeRegen();
		status.completeRegen();
		notify.completeRegen();
		
	}

}