package com.mehnadnerd.EmpireGame.dialog;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;

public class PlayerCreateDialog extends GenericEmpireDialog {

	public <T extends ActionListener>PlayerCreateDialog(T callback) {
		super("Create Player",
				new JLabel[]{new JLabel("Name:"),new JLabel("Home X:"),new JLabel("Home Y:")},
				new JTextField[]{new JTextField("Twaggia"),new JTextField("10"),new JTextField("10")},
				callback,"Create Player","CreatePlayerDone");
	}
	
	public EmpirePlayer getPlayer() {
		return new EmpirePlayer(this.getAnswer(0),
				new EmpireLocationRef(Integer.parseInt(this.getAnswer(1)),
						Integer.parseInt(this.getAnswer(2))));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}