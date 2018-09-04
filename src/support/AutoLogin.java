package support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class AutoLogin {
	private static final String saveDir = "AppData";
	private static final String filename = "/defaultLogin";
	
	private static ArrayList<String> readFile() {
		String pathname = saveDir + filename;
		ArrayList<String> info = new ArrayList<String>();
		File file = new File(pathname);
		if (!file.exists()) {
			return info;
		} 
		try {
			System.out.println(file.getAbsolutePath());
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				info.add(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	
	public static boolean hasDefaultInfo() {
		return readFile().size() == 2;
	}
	
	public static String getDefaultUsername() {
		return readFile().get(0);
	}
	
	public static String getDefaultPassword() {
		return readFile().get(1);
	}
	
	public static void saveDefaultUser(String username, String password) {
		try {
			File dir = new File(saveDir);
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File(saveDir+filename);
			file.createNewFile();
			Writer writer = new BufferedWriter(new FileWriter(file));
			writer.write(username + "\n");
			writer.write(password + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clearDefaultInfo() {
		String pathname = saveDir + filename;
		File dir = new File(pathname);
		if (dir.exists()) {
			////System.out.println("delete");
			dir.delete();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		////System.out.println(AutoLogin.hasDefaultInfo());
		AutoLogin.saveDefaultUser("wwddd23", "1234dd5");
		//System.out.println(AutoLogin.hasDefaultInfo());
		//System.out.println(AutoLogin.getDefaultUsername());
		//System.out.println(AutoLogin.getDefaultPassword());
		AutoLogin.clearDefaultInfo();
	}
	
}
