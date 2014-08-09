package com.webs.faragames.fallen;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileManager {

	public static String Name = "null";
	public static String Password = "null";

	public static String ResDir;
	public static String ResVer;
	public static String ResLink;
	public static String GameDir;
	public static String GameVer;
	public static String GameLink;
	public static String LibDir;
	public static String LibVer;
	public static String LibLink;
	public static String NatDir;
	public static String NatVer;
	public static String NatLink;

	public static boolean isGameInstalled() {
		return new File(MainFile).exists();
	}

	public static void createFiles() {
		Logger("Creating Directories");
		createDirs();
		Logger("Directories Created");

		Logger("Downloading res");
		download(ResLink, Dir + sep + "res.zip");
		Logger("res Downloaded");

		Logger("Downloading lib");
		download(LibLink, Dir + sep + "lib.zip");
		Logger("lib Downloaded");

		Logger("Downloading natives");
		download(NatLink, Dir + sep + "nat.zip");
		Logger("natives Downloaded");

		Logger("Uncompressing res");
		unCompress(Dir + sep + "res.zip", ResDir);
		Logger("res Uncompressed");

		Logger("Uncompressing lib");
		unCompress(Dir + sep + "lib.zip", LibDir);
		Logger("lib Uncompressed");

		Logger("Uncompressing natives");
		unCompress(Dir + sep + "nat.zip", NatDir);
		Logger("natives Uncompressed");

		Logger("Deleting Res Zip");
		removeFile(Dir + sep + "res.zip");
		Logger("Res zip Deleted");

		Logger("Deleting Lib Zip");
		removeFile(Dir + sep + "lib.zip");
		Logger("Lib zip Deleted");

		Logger("Deleting Native Zip");
		removeFile(Dir + sep + "nat.zip");
		Logger("Native zip Deleted");

		Logger("Closing Program.");

		/**
		 * TODO:Launch Game
		 */
	}

	public static void createDirs() {
		File f = new File(Dir);
		f.mkdir();
		ResDir = FileManager.Dir + FileManager.sep + "textures";
		File f2 = new File(ResDir);
		f2.mkdir();
		LibDir = FileManager.Dir + FileManager.sep + "bin";
		File f3 = new File(LibDir);
		f3.mkdir();
		NatDir = FileManager.Dir + FileManager.sep + "bin" + FileManager.sep + "natives";
		GameDir = FileManager.Dir + FileManager.sep + "bin" + FileManager.sep + "LD.jar";
	}

	private static void download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
			}
			if (in != null) in.close();
			if (out != null) out.close();
		} catch (Exception exception) {
			FileManager.Logger("Error, on download: " + exception);
		} finally {
			try {
				if (in != null) in.close();
				if (out != null) out.close();
			} catch (IOException ioe) {
			}
		}
	}

	public static void unCompress(String source, String outFilename) {
		try {
			int BUFFER = 2048;
			File file = new File(source);
			ZipFile zip = new ZipFile(file);
			String newPath = outFilename;
			new File(newPath).mkdir();
			@SuppressWarnings("rawtypes")
			Enumeration zipFileEntries = zip.entries();
			while (zipFileEntries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
				String currentEntry = entry.getName();
				File destFile = new File(newPath, currentEntry);
				File destinationParent = destFile.getParentFile();
				destinationParent.mkdirs();
				if (!entry.isDirectory()) {
					BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
					int currentByte;
					byte data[] = new byte[BUFFER];
					FileOutputStream fos = new FileOutputStream(destFile);
					BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
					while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, currentByte);
					}
					dest.flush();
					dest.close();
					is.close();
				}
			}
			zip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void removeFile(String fileName) {
		try {
			Files.delete(Paths.get(fileName));
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", fileName);
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", fileName);
		} catch (IOException x) {
			System.err.println(x);
		}
	}

}