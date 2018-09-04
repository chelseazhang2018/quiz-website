package login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class accountManager {

    private HashMap<String, String> accounts;

    public accountManager(){
        accounts = new HashMap<String, String>();
        

    }
    
    public boolean isPasswordCorrect(String account, String password){
    	if (containsAccount(account) && accounts.get(account).equals(password))
    		return true;
    	else 
    		return false;
    }
    
    public boolean containsAccount(String name){
        return accounts.containsKey(name);
    }

    public void createNewAccount(String account, String password){
    	// Hash password
    	String hashPassword = convertToHash(password);
    	
    	// Store hash password to database
    	
    	
    	
    	accounts.put(account,password);
    }
    
    private String convertToHash(String word) {
		try {
			MessageDigest message = MessageDigest.getInstance("SHA");
			message.update(word.getBytes());
			return hexToString(message.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff; // remove higher bits, sign
			if (val < 16)
				buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}

}
