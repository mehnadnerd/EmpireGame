package com.mehnadnerd.EmpireGame.dialog;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;

public class PlanetGenerateDialog extends GenericEmpireDialog {

	public <T extends ActionListener> PlanetGenerateDialog(T callback) {
		super("Generate Planet", new JLabel[] { new JLabel("Name:"), new JLabel("X:"), new JLabel("Y:")},
				new JTextField[] { new JTextField("Twaggia"), new JTextField("10"), new JTextField("10") }, callback,
				"Generate Planet", "GeneratePlanetDone");
	}

	public EmpirePlanet getPlanet() {
		return EmpirePlanet.randomPlanet(this.getAnswer(0),
				new EmpireLocationRef(Integer.parseInt(this.getAnswer(1)), Integer.parseInt(this.getAnswer(1))));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}