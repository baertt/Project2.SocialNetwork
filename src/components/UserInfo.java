package components;

import java.time.LocalDate;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.Serializable;

public class UserInfo implements Serializable {
	private String password, name, phone, email, biography, bday, host, port;
	private Object avatar;

	public UserInfo(String password, String name, String phone, String email, String bday, String host, String port /*String biography*/){//, Object object) {
		this.password = password;
		this.name = name;
		this.bday = bday;
		this.phone = phone;
		this.email = email;
		this.host = host;
		this.port = port;
		//this.biography = biography; comment out because haven't implement in GUI
		//this.avatar = object;
	}


	public String getPassword(){return this.password;}
	public String getName(){return this.name;}
	public String getPhone(){return this.phone;}
	public String getEmail(){return this.email;}
	public String getBday(){return this.bday;}
	public String getBiography(){return this.biography;}
	public Object getAvatar(){return this.avatar;}
	public String getHost(){return this.host;}
	public String getPort(){return this.port;}

	public void setPassword(String newPassword) {
		password = newPassword;
	}

	public void setName(String newName) {
		name = newName;
	}

	public void setPhone(String newPhoneNumber) {
		phone = newPhoneNumber;
	}

	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public void setBday(String newBday) {
		bday = newBday;
	}

	public void setBiography(String newBiography) {
		biography = newBiography;
	}

	public void setHost(String newHost){
		host = newHost;
	}

	public void setPort(String newPort){
		port = newPort;
	}

	public void setAvatar(Object newAvatar) {
		avatar = newAvatar;
	}

	@Override
	public String toString(){
		return(String.format("%s,%s,%s,%s,%s,%s,%s", password, name, phone, email, (bday == null ? "" : bday.toString()), host, port));
	}

	@Override
	public int hashCode(){
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof UserInfo){
			UserInfo that = (UserInfo)obj;
			return (this.password == that.password);
		}
		else {
			return false;
		}
	}
}
