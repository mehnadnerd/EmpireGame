package com.mehnadnerd.EmpireGame;

public class TheEmpireGame {
	private static boolean isClient = false;
	private static boolean doBoth = true;

	public static void main(String[] args) {
		if(args.length==1) {
			if(args[0].equalsIgnoreCase("client")) {
				isClient=true;
			} else if (args[0].equalsIgnoreCase("server")) {
				isClient=false;
			}
		}
		if(isClient||doBoth) {
			javax.swing.SwingUtilities.invokeLater(new ClientMain());
		}
		if (doBoth||!isClient) {
			javax.swing.SwingUtilities.invokeLater(new ServerMain());
		}
		
	}
	
	//Planny things:
	//EmpireFleets are held in an array in the player class
	//Add ships to fleet-shouldn't be messing with ships individually
	//All logic done server-side, client is dumb terminal
	
	//client should just manage 1 empirePlayer, server does all of them
	//Need to give 2 lists of planets to player-owned and discovered
	//Server has central list of all planets, single list?

}