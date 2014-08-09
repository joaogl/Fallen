package com.webs.faragames.fallen;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.joaolourenco.fallen.FallenSystemService;

/**
 * Main Class for the game.
 * 
 * @author FARA Games
 *
 */
public class Main {

	/*
	 * public static final String Success = "000";
	 * public static final String Login_Failed = "001";
	 * public static final String User_Not_Found = "002";
	 * public static final String Missing_Permission = "003";
	 * public static final String User_Already_Logged = "004";
	 * public static final String Crash_Occured = "005";
	 * public static final String User_Target_Not_Found = "006";
	 * public static final String Operation_Not_Found = "007";
	 * public static final String Permission_Already_Exists = "008";
	 * public static final String Permission_Doesnt_Exists = "009";
	 * public static final String Ban_Already_Exists = "010";
	 * public static final String Ban_Doesnt_Exists = "011";
	 * public static final String Airliner_Already_Exists = "012";
	 * public static final String Airliner_Doesnt_Exists = "013";
	 * public static final String Too_Many_Updates = "998";
	 * public static final String Banned = "999";
	 */

	static JFrame frame;
	static JTextField et = new JTextField();
	static JLabel e = new JLabel("Email");
	static JLabel u = new JLabel("Username");
	static JTextField ut = new JTextField();
	static JLabel p = new JLabel("Password");
	static JPasswordField pt = new JPasswordField();
	static FallenSystemService fallen = new FallenSystemService();
	static String token = "";
	static JButton login = new JButton();
	static JButton logout = new JButton();
	static JButton register = new JButton();
	static String launcherVersion = "Fallen Launcher V0.1";
	static String[] launcherServerVersion = new String[3];
	static JButton profiles = new JButton("Create Profile");
	static JButton edit = new JButton("Edit Profile");
	static JComboBox<String> profileList = new JComboBox<String>();

	/**
	 * Main method that runs the game.
	 * 
	 * @param args
	 *            : String[] with anykind of arguments.
	 */
	public static void main(String[] args) {
		//CreateProfile.createProfile();
		Profile.loadProfiles();

		frame = new JFrame();
		frame.setUndecorated(true);
		try {
			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(Main.class.getResource("/back.png")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.pack();
		frame.setTitle("Fallen Launcher");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		String input = fallen.getFallenSystemPort().checkLauncherVersion();
		launcherServerVersion[0] = input.split("-")[0];
		launcherServerVersion[1] = input.split("-")[1];
		launcherServerVersion[2] = input.split("-")[2] + "-" + input.split("-")[3] + "-" + input.split("-")[4];

		addGuiStuff();
		addLRStuff();

		frame.repaint();
		frame.setVisible(true);

	}

	public static void addGuiStuff() {
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
		close.setBounds(frame.getWidth() - 30, 5, 25, 25);
		frame.add(close);

		JLabel lv = new JLabel("Launcher is up to date.");
		lv.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		if (!launcherVersion.equalsIgnoreCase(launcherServerVersion[0])) {
			lv.setText("A new launcher version was launched on " + launcherServerVersion[2] + ".");
			Object[] options = { "Yes, please", "No, thanks" };
			int n = JOptionPane.showOptionDialog(frame, "A new launcher version is available.\n Version: " + launcherServerVersion[0] + "\n Launched: " + launcherServerVersion[2] + "\n Do you want to update?", "Update Available", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
			if (n == 0) {
				/**
				 * TODO: Update Launcher!
				 */
				System.out.println("YES");
			}
		}
		lv.setBounds(20, frame.getHeight() - 30, 320, 25);
		frame.add(lv);

		profiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateProfile.createProfile();
			}
		});
		profiles.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		profiles.setBounds(frame.getWidth() - 105, frame.getHeight() - 33, 100, 25);
		frame.add(profiles);

		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * TODO: Edit the profile.
				 */
			}
		});
		edit.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		edit.setBounds(frame.getWidth() - 200, frame.getHeight() - 33, 89, 25);
		edit.setEnabled(false);
		frame.add(edit);

		/**
		 * TODO: Load profiles from files.
		 */
		profileList.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		profileList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * TODO: Update the fields with the profile data.
				 */
			}
		});
		profileList.setBounds(frame.getWidth() - 300, frame.getHeight() - 33, 95, 25);
		frame.add(profileList);
	}

	public static void addLRStuff() {
		int yOffset = 150;

		u.setBounds(frame.getWidth() - 160, yOffset, 85, 25);
		u.setFont(new Font("Times New Roman", Font.BOLD, 19));
		frame.add(u);

		ut.setBounds(frame.getWidth() - 160, yOffset + 25, 130, 20);
		frame.add(ut);

		p.setBounds(frame.getWidth() - 160, yOffset + 55, 85, 25);
		p.setFont(new Font("Times New Roman", Font.BOLD, 19));
		frame.add(p);

		pt.setBounds(frame.getWidth() - 160, yOffset + 80, 130, 20);
		frame.add(pt);

		e.setBounds(frame.getWidth() - 160, yOffset + 110, 85, 25);
		e.setFont(new Font("Times New Roman", Font.BOLD, 19));
		e.setVisible(false);
		frame.add(e);

		et.setBounds(frame.getWidth() - 160, yOffset + 135, 130, 20);
		et.setVisible(false);
		frame.add(et);

		try {
			Image img = ImageIO.read(Main.class.getResource("/b1.png"));
			login.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		login.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ee) {
				if (et.isVisible()) {
					et.setVisible(false);
					e.setVisible(false);
				} else {
					if (ut.getText().length() >= 3 && pt.getText().length() >= 4) {
						String out = fallen.getFallenSystemPort().login(ut.getText(), hash(pt.getText().toString()));
						if (out.contains("001")) JOptionPane.showMessageDialog(null, "User or Password wrong.", "Login Failed", JOptionPane.ERROR_MESSAGE);
						else if (out.length() >= 10 && !out.contains("999")) {
							token = out;
							et.setVisible(false);
							e.setVisible(false);
							ut.setVisible(false);
							u.setVisible(false);
							pt.setVisible(false);
							p.setVisible(false);
							login.setVisible(false);
							register.setVisible(false);
							logout.setVisible(true);
						} else if (out.contains("004")) JOptionPane.showMessageDialog(null, "You are already logged in.", "Login Failed", JOptionPane.ERROR_MESSAGE);
						else JOptionPane.showMessageDialog(null, "Something went wrong. Please give this message to the administrator for more information: " + out, "Login Failed", JOptionPane.ERROR_MESSAGE);
					} else JOptionPane.showMessageDialog(null, "Please make sure you filled all the fields.", "Login Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		login.setBounds(frame.getWidth() - 200, yOffset + 170, 80, 40);
		frame.add(login);

		try {
			Image img = ImageIO.read(Main.class.getResource("/b2.png"));
			register.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		register.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ee) {
				if (et.isVisible()) {
					if (ut.getText().length() >= 3 && pt.getText().length() >= 4 && et.getText().length() >= 4) {
						String res = fallen.getFallenSystemPort().addUser(ut.getText(), hash(pt.getText().toString()), et.getText());
						if (res.contains("005")) JOptionPane.showMessageDialog(null, "User already in use.", "Register Error", JOptionPane.ERROR_MESSAGE);
						else if (res.contains("000")) {
							et.setVisible(false);
							e.setVisible(false);
						} else JOptionPane.showMessageDialog(null, "Something went wrong. Please give this message to the administrator for more information: " + res, "Login Failed", JOptionPane.ERROR_MESSAGE);
					} else JOptionPane.showMessageDialog(null, "All the fields must be at least 4 characters long.", "Register Error", JOptionPane.ERROR_MESSAGE);
				} else {
					et.setVisible(true);
					e.setVisible(true);
				}
			}
		});
		register.setBounds(frame.getWidth() - 100, yOffset + 170, 80, 40);
		frame.add(register);

		try {
			Image img = ImageIO.read(Main.class.getResource("/b3.png"));
			logout.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ee) {
				String res = fallen.getFallenSystemPort().logout(token);
				if (res.contains("002") || res.contains("000")) {
					ut.setVisible(true);
					u.setVisible(true);
					pt.setVisible(true);
					p.setVisible(true);
					login.setVisible(true);
					register.setVisible(true);
					logout.setVisible(false);
				} else System.out.println(res);
			}
		});
		logout.setBounds(frame.getWidth() - 100, yOffset + 170, 80, 40);
		logout.setVisible(false);
		frame.add(logout);
	}

	public static void updateProfiles() {
		/**
		 * TODO: Update the profiles list.
		 */
	}

	public static String hash(String input) {
		String md5 = null;
		if (null == input) return null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
	}

}