package com.mehnadnerd.EmpireGame.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.mehnadnerd.EmpireGame.client.FleetsTab;
import com.mehnadnerd.EmpireGame.things.EmpireComboBoxModel;
import com.mehnadnerd.EmpireGame.things.EmpireLocationRef;
import com.mehnadnerd.EmpireGame.things.EmpirePlanet;

public class BuildDialog extends GenericEmpireDialog implements ActionListener {
	private EmpireLocationRef buildLoc;
	private static final long serialVersionUID = 1L;

	public BuildDialog(FleetsTab callback) {
		super("Build Ship", new JLabel[] { new JLabel("Ship Name"), new JLabel("Metal"), new JLabel("Pop") },
				new JTextField[] { new JTextField("Turtle Ship"), new JTextField("1"), new JTextField("1") }, callback,
				"Build", "BuildDialogFinished");
		@SuppressWarnings("unchecked")
		JComboBox<EmpirePlanet> comboBox = new JComboBox<EmpirePlanet>(
				new EmpireComboBoxModel<EmpirePlanet>(callback.getClient().getPlayer().getPlayerOwnedPlanets()));
		comboBox.addActionListener(this);
		comboBox.setActionCommand("BuildPlanet");
		this.add(comboBox, this.getComponentCount()-2);
		buildLoc = ((EmpirePlanet) comboBox.getSelectedItem()).getLoc();
		//this.getComponent(0).add(comboBox, this.getComponentCount() - 2);
		
		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("BuildPlanet")) {
			@SuppressWarnings("rawtypes")
			JComboBox cb = (JComboBox) e.getSource();

			buildLoc = ((EmpirePlanet) cb.getSelectedItem()).getLoc();
		}
	}

	public EmpireLocationRef getBuildLoc() {
		return buildLoc;
	}
	/*
	public EmpireShip getBuiltShip() {
		return EmpireShip.buildShip(this.getAnswer(0), Integer.parseInt(this.getAnswer(2)),
				Integer.parseInt(this.getAnswer(1)), buildLoc);
	}*/
	public String getShipName() {
		System.out.println("Ship name: "+this.getAnswer(0));
		return this.getAnswer(0);
	}
	public int getShipMetal() {
		return Integer.parseInt(this.getAnswer(2));
	}
	public int getShipPop() {
		return Integer.parseInt(this.getAnswer(1));
	}
	

}