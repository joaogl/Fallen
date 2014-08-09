package com.webs.faragames.fallen;

import java.io.File;

public class Profile {

	public static void loadProfiles() {
		File f = new File(FileSystem.Dir);
		if (f.exists()) FileSystem.Logger(FileSystem.Dir + " exists.");
	}

}