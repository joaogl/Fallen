package com.webs.faragames.fallen;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Main Class for the game.
 * 
 * @author FARA Games
 *
 */
public class Main {

	/**
	 * Main method that runs the game.
	 * 
	 * @param args
	 *            : String[] with anykind of arguments.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setUndecorated(true);
		try {
			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(Main.class.getResource("/back.png")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.pack();
		frame.setTitle("Fallen Launcher");
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		JButton close = new JButton();
		try {
			Image img = ImageIO.read(Main.class.getResource("/close.jpg"));
			close.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		close.setBounds(500 - 30, 5, 25, 25);
		frame.add(close);

		frame.repaint();
	}
}