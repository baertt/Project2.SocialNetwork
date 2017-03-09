package components;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import controllers.StartController;

public class Users implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, UserInfo> users = new Hashtable<String, UserInfo>();

	/**
	 * Adds a new user to the users hashTable and then writes the information to a file
	 * @param username
	 * @param password
	 * @param name - optional (could be null)
	 * @param bday - optional (could be null)
	 * @param phone - optional (could be null)
	 * @param email - optional (could be null)
	 * @throws IOException
	 */
	public void add(String username, String password, String name, String phone, String email,
					String bday, String host, String port /*String biography*/) throws IOException{
		for(String user: users.keySet()){System.out.println(user + users.get(user).toString());}
		users.put(username, new UserInfo(password, name, phone, email, bday /*biography*/, host, port));
		for(String user: users.keySet()){System.out.println(user + users.get(user).toString());}
		//(serialize change) writeToUserFile();
	}

	/**
	 * Writes new user information to a file to be accessed when app reopened
	 */
	public void writeToUserFile() throws IOException{
		PrintWriter out = new PrintWriter("users.txt");
		for(String user: users.keySet()){
			out.println(user + "," + users.get(user).toString());
		}



		// TODO: Ask about this. Do we need to hide the file? If so how? Does this work?


		String cmd1[] = {"attrib","+h", "SocialNetwork\"users.txt"};
		Runtime.getRuntime().exec(cmd1);

		out.close();
	}



	public Hashtable<String, UserInfo> getUsers(){
		return users;
	}

	public void readFromUserFile(StartController start) throws FileNotFoundException{
		Scanner in = new Scanner(new File("users.txt"));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			List<String> lineParts = Arrays.asList(line.split(","));
			start.getUsers().users.put(lineParts.get(0), new UserInfo(lineParts.get(1), lineParts.get(2), lineParts.get(3),
					lineParts.get(4), lineParts.get(5), lineParts.get(6), lineParts.get(7)));
			//(parts[5].equals("null"))?null:LocalDate.parse(parts[5]))

			//password, String name, String phone, String email, LocalDate bday
		}
		in.close();
		//printKeys();
	}
	/**
	 * Checks whether username exists in hashtable
	 * @param  		String username
	 * @return      boolean
	 */
	public boolean checkUserName(String username) {
		return users.containsKey(username);
	}

	/**
	 * Checks whether username and password matches.
	 * @param  		String username, password
	 * @return      boolean (Returns true if matches)
	 */
	public boolean checkUser(String username, String password) {
		if (checkUserName(username)) {
			UserInfo info = users.get(username);
			if (info.getPassword().equals(password)) {
				return true;
			} else { return false; }
		} return false;
	}

	public List<String> getCurrentUser(String username) {
		UserInfo info = users.get(username);
		List<String> userInfoList = Arrays.asList((username + "," + info.toString()).split(",")); //(username + "," + info.toString()).split(",")
		//System.out.println(userInfoList);
		return userInfoList;
	}

	public UserInfo getCurrentUserInfo(String username) {
		return users.get(username);
	}

	public void printKeys() {
		for (String key: users.keySet()) {
			//System.out.println(key);
			printValues(key);
		}
	}

	public void printValues(String key) {
		UserInfo info = users.get(key);
		//System.out.println(info.toString());
	}
}
