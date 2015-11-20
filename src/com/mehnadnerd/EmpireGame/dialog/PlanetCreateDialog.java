package com.mehnadnerd.EmpireGame.dialog;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;
import com.mehnadnerd.EmpireGame.things.EmpirePlayer;

public class PlanetCreateDialog extends GenericEmpireDialog {

	public <T extends ActionListener>PlanetCreateDialog(T callback) {
		super("Create Player", new JLabel[] { new JLabel("Name:"), new JLabel("X:"), new JLabel("Y:"),
				new JLabel("Metal Production"), new JLabel("Fuel Production"), new JLabel("Food Production"),
				new JLabel("Biome")},
				new JTextField[] { new JTextField("Twaggia"), new JTextField("10"), new JTextField("10"),
						new JTextField("2"),new JTextField("2"),new JTextField("2"),
						new JTextField("CAPITAL")}, callback,
				"Create Planet", "CreatePlanetDone");
	}

	public EmpirePlanet getPlanet() {
		return new EmpirePlanet(this.getAnswer(0), Integer.parseInt(this.getAnswer(3)),
				Integer.parseInt(this.getAnswer(4)), Integer.parseInt(this.getAnswer(5)),
				EmpirePlanet.nameBiome(this.getAnswer(6)), new EmpireLocationRef(Integer.parseInt(this.getAnswer(1)),
						Integer.parseInt(this.getAnswer(2))), EmpirePlayer.getDefault());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}