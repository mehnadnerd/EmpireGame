package com.mehnadnerd.EmpireGame.dialog;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenericEmpireDialog extends JDialog implements ActionListener {
	protected JPanel p;
	protected JLabel[] questions;
	protected JTextField[] answers;
	protected String title;
	private JButton enterButton;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public <T extends ActionListener> GenericEmpireDialog(String title, JLabel[] questions,
			JTextField[] answers, T callback, String enterButtonName,
			String actionCommand) {
		super();
		
		enterButton=new JButton(enterButtonName);
		enterButton.setActionCommand(actionCommand);
		enterButton.addActionListener(callback);
		enterButton.setVerticalTextPosition(JButton.CENTER);
        enterButton.setHorizontalTextPosition(JButton.LEADING);
		this.questions=questions;
		this.answers=answers;
		//this.setPreferredSize(new Dimension(300,50+30*this.questions.length));
		//this.setSize(this.getPreferredSize());

		/*p=new JPanel();
		p.setLayout(new GridLayout(0,2,10,15));
		for(int i=0; i<this.questions.length; i++) {
			p.add(questions[i]);
			p.add(answers[i]);
		}
		p.add(enterButton);
		//this.add
		this.add(p);*/
		this.setLayout(new GridLayout(0,2,10,15));
		for(int i=0; i<this.questions.length; i++) {
			this.add(questions[i]);
			this.add(answers[i]);
		}
		this.add(enterButton);
		this.pack();
	}
	public String getAnswer(int which) {
		return answers[which].getText();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dispose();
		
	}
	

}