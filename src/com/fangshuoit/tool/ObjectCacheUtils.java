package com.fangshuoit.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public class ObjectCacheUtils {

	static String sdCardPath = Environment.getExternalStorageDirectory()
			.getPath();
	static String cachePath = sdCardPath + "/newsilkroad/cache";
	static String filePath = cachePath + "/searchcell.dat";

	static ArrayList<String> a;
	static ArrayList<String> b = new ArrayList<String>();

	static File file;

	public static void saveObjCache(List<String> prizeList) {
		try {
			File directory = new File(cachePath);
			directory.mkdirs();
			file = new File(filePath);
			file.createNewFile();

			FileOutputStream fs = new FileOutputStream(filePath);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			for (int i = 0; i < prizeList.size(); i++) {
				os.writeObject(prizeList.get(i));
			}
			os.close();
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveObjCache(String searchCell) {
		try {
			File directory = new File(cachePath);
			directory.mkdirs();
			file = new File(filePath);
			file.createNewFile();
			boolean ifonly = true;
			a = readObjCache();
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals(searchCell)) {
					ifonly = false;
					continue;
				}
			}
			if (ifonly) {
				a.add(searchCell);
			}

			FileOutputStream fs = new FileOutputStream(filePath);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			for (int i = 0; i < a.size(); i++) {
				os.writeObject(a.get(i));
			}
			os.close();
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> readObjCache() {
		ArrayList<String> prizeList = new ArrayList<String>();
		try {
			FileInputStream fs = new FileInputStream(filePath);
			ObjectInputStream os = new ObjectInputStream(fs);
			String prize = null;
			while ((prize = (String) os.readObject()) != null) {
				prizeList.add(prize);
			}
			os.close();
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prizeList;
	}

	public static void deteleFile() {
		file = new File(filePath);
		file.delete();
	}
}
