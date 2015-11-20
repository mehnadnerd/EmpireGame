package com.mehnadnerd.EmpireGame;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.*;
import com.mehnadnerd.EmpireGame.client.FleetsTab;
import com.mehnadnerd.EmpireGame.client.PlanetsTab;
import com.mehnadnerd.EmpireGame.server.PlanetManager;
import com.mehnadnerd.EmpireGame.server.PlayerManager;
import com.mehnadnerd.EmpireGame.server.PlayersTab;
import com.mehnadnerd.EmpireGame.server.ServerComms;
import com.mehnadnerd.EmpireGame.server.ServerPlanetsTab;
import com.mehnadnerd.EmpireGame.server.TimeTab;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;

@SuppressWarnings("unused")
public class ServerMain extends MainThingy {

	private JFrame mainWindow;
	private JMenuBar mainMenuBar;
	private JTabbedPane mainTabbedPane;
	//private FleetsTab fleets;
	private ServerPlanetsTab planets;
	private ServerComms comms;
	private PlayersTab playersTab;
	private PlanetManager planetManager = new PlanetManager(this);
	private PlayerManager playerManager = new PlayerManager(this);
	private TimeTab timeTab;

	ServerMain() {
		//create the Comms
		comms=new ServerComms(8192,this);
		
		//create window
		mainWindow = new JFrame("The Empire Game Server");
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//create menu bar
		mainMenuBar = new JMenuBar();
        mainMenuBar.setOpaque(true);
        mainMenuBar.setBackground(new Color(0x5f,0xff,0x8f));
        mainMenuBar.setPreferredSize(new Dimension(800, 20));
        mainWindow.setJMenuBar(mainMenuBar);
      
        
        //create tabbed pane
        mainTabbedPane = new JTabbedPane();
        //create tabs 
        //fleets = new FleetsTab();
        planets = new ServerPlanetsTab(planetManager);
        playersTab = new PlayersTab(playerManager);
        timeTab = new TimeTab(this);
        
        //add the tabs
		mainTabbedPane.addTab("Players", playersTab);
        mainTabbedPane.addTab("Planets", planets);
       
        mainTabbedPane.addTab("Time", timeTab);
        //mainTabbedPane.addTab("Map", null);
        
        //add the tabbed pane to the window
        mainWindow.add(mainTabbedPane);        
	}

	@Override
	public void run() {
		mainWindow.pack();
		mainWindow.setVisible(true);
	}
	
	public ServerComms getComms() {
		return this.comms;
	}
	
	public PlayerManager getPlayerManager() {
		return this.playerManager;
	}
	
	public PlanetManager getPlanetManager() {
		return this.planetManager;
	}
	
	public TimeTab getTimeTab() {
		return this.timeTab;
	}
	
	public void updateTick() {
		planetManager.updateTick();
		playerManager.updateTick();
	}

}