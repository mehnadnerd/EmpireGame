package com.mehnadnerd.EmpireGame.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.mehnadnerd.EmpireGame.ClientMain;
import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;

public class ClientComms {
	private String ip;
	private ClientMain client;
	public ClientComms(ClientMain client, String ip) {
		this.ip=ip;
		this.client=client;
	}
	public String getIP() {
		return ip;
	}
	public void setIP(String ip){
		this.ip=ip;
	}
	
	public EmpirePlanet getPlanet(String planet) {
		EmpirePlanet toReturn = EmpirePlanet.getDefault();
		try {
	        URL url = new URL(ip+"/planetdl");
	        HttpURLConnection s = (HttpURLConnection) url.openConnection();
	        s.setDoOutput(true);
	        s.setDoInput(true);
	        s.setRequestMethod("GET");
	        s.setUseCaches(false);
	        s.addRequestProperty("EmpireThingName", planet);
	        ObjectInputStream ios = new ObjectInputStream(s.getInputStream());
	        toReturn = (EmpirePlanet) ios.readObject();
	        ios.close();
	        s.disconnect();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public EmpirePlayer getPlayer(String playerName) {
		EmpirePlayer toReturn = EmpirePlayer.getDefault();
		try {
	        URL url = new URL(ip+"/playerdl");
	        HttpURLConnection s = (HttpURLConnection) url.openConnection();
	        s.setDoOutput(true);
	        s.setDoInput(true);
	        s.setRequestMethod("GET");
	        s.setUseCaches(false);
	        s.addRequestProperty("EmpireThingName", playerName);
	        
	        ObjectInputStream ios = new ObjectInputStream(s.getInputStream());
	        toReturn = (EmpirePlayer) ios.readObject();
	        ios.close();
	        s.disconnect();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Got: "+toReturn);
		return toReturn;
	}
	
	/**
	 * Working?
	 * @param name
	 * @param metal
	 * @param pop
	 * @param loc
	 * @return
	 */
	public EmpirePlayer build(String name, int metal, int pop, EmpireLocationRef loc) {
		EmpirePlayer toReturn = EmpirePlayer.getDefault();
		try {
	        URL url = new URL(ip+"/build");
	        HttpURLConnection s = (HttpURLConnection) url.openConnection();
	        s.setDoOutput(true);
	        s.setDoInput(true);
	        s.setRequestMethod("GET");
	        s.setUseCaches(false);
	        s.addRequestProperty("EmpireThingName", name);
	        s.addRequestProperty("EmpireThingLoc", loc.toString());
	        s.addRequestProperty("EmpireThingData", metal+":"+pop);
	        s.addRequestProperty("RequestingPlayer", client.getPlayer().getName());
	        
	        ObjectInputStream ios = new ObjectInputStream(s.getInputStream());
	        toReturn = (EmpirePlayer) ios.readObject();
	        ios.close();
	        s.disconnect();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	/**
	 * Ready up. Not yet implemented.
	 * @return
	 */
	public EmpirePlayer ready() {
		return EmpirePlayer.getDefault();
	}
	
	
}