package com.mehnadnerd.EmpireGame.client;

import com.mehnadnerd.EmpireGame.ClientMain;
import com.mehnadnerd.EmpireGame.GenericTab;

public class NotificationsTab extends GenericTab {
	@SuppressWarnings("unused")
	private ClientMain client;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 * @param clientMain 
	 */
	public NotificationsTab(ClientMain clientMain) {
		this.client=clientMain;
	}

	@Override
	public void completeRegen() {
		
	}

}