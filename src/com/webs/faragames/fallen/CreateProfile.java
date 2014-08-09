package com.webs.faragames.fallen;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateProfile {

	static JFrame frame;
	static JLabel u = new JLabel("Username");
	static JTextField ut = new JTextField();
	static JLabel p = new JLabel("Password");
	static JPasswordField pt = new JPasswordField();
	static JPanel panel = new JPanel();
	static JButton c = new JButton("Create");
	static JButton e = new JButton("Cancel");

	public static void createProfile() {
		frame = new JFrame();
		frame.pack();
		frame.setTitle("Fallen Create Profile");
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);

		frame.setAlwaysOnTop(true);

		addUPFields();

		frame.repaint();
		frame.setVisible(true);
	}

	public static void addUPFields() {
		int yOffset = 10;

		u.setBounds((frame.getWidth() / 2) - (85 / 2), yOffset, 85, 25);
		panel.add(u);

		ut.setBounds((frame.getWidth() - 130) / 2, yOffset + 25, 130, 20);
		panel.add(ut);

		p.setBounds((frame.getWidth() - 85) / 2, yOffset + 55, 85, 25);
		panel.add(p);

		pt.setBounds((frame.getWidth() - 130) / 2, yOffset + 80, 130, 20);
		panel.add(pt);

		c.setBounds((frame.getWidth() / 4) - (100 / 2), frame.getHeight() - 65, 100, 25);
		e.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (ut.getText().length() >= 4 && pt.getText().length() >= 4) {
					/**
					 * TODO: Create Profile.
					 */
					Main.updateProfiles();
					frame.dispose();
				}
			}
		});
		panel.add(c);

		e.setBounds(((frame.getWidth() / 4) * 3) - (100 / 2), frame.getHeight() - 65, 100, 25);
		e.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(e);
	}
}