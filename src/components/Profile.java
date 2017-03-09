package components;
import javafx.scene.image.Image;

public class Profile {

	// Elements of a profile ///////////////////////////////////////////////////////////////////////////////

	private String name;
	private String userName;
	private String birthday;
	private String email;
	private String phoneNumber;
	private Image profilePicture;

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Getters for all elements of a Profile ////////////////////////////////////////////////////////////////

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}

	public String getBirthday() {
		return birthday;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Image getProfilePicture() {
		return profilePicture;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Setters for all elements of a Profile ////////////////////////////////////////////////////////////////

	// These setters get their information from "Edit". When the user edits their profile,
	// the information is passed through and set as the new value
	public void setName(String name) {
		this.name = name;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setProfilePicture(Image profilePicture) {
		this.profilePicture = profilePicture;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Profile() {
		// TODO Auto-generated constructor stub
	}


}
