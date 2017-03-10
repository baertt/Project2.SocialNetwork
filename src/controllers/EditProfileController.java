package controllers;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
//import Objects.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import components.UserInfo;
import components.Users;

public class EditProfileController  {
	StartController start;
	Users users;
	TimelineController timeline;
	List<String> currentUser;

	@FXML
	Button saveChanges;
	@FXML
	Button cancel;
	@FXML
	TextField username;
	@FXML
	TextField name;
	@FXML
	TextField password;
	@FXML
	TextField confirmPass;
	@FXML
	DatePicker birthday;
	@FXML
	TextField phone;
	@FXML
	TextField email;
	@FXML
	TextArea bio;

	@FXML
	public void initialize(){
	}

	@FXML
	public void saveChanges(){
		/*// get UserInfo
		UserInfo currentUserInfo = users.getCurrentUserInfo(username.getText());
		currentUserInfo.setName((name.getText().equals(""))?"null":name.getText());
		if (birthday.getValue() != null) {
			currentUserInfo.setBday("" + birthday.getValue());
		}
		currentUserInfo.setPhone((phone.getText().equals(""))?"null":phone.getText());
		currentUserInfo.setEmail((email.getText().equals(""))?"null":email.getText());
		// if password field = confirm password field and when the user did not leave it blank.
		// when user leaves field blank, should not change password to empty string.
		if (password.getText().equals(confirmPass) && !password.getText().equals("")) {
			currentUserInfo.setPassword(password.getText());
		}
		timeline.setCurrentUserInfo();*/
		String user = currentUser.get(0);
		UserInfo info = timeline.users.getCurrentUserInfo(user);

		info.setName(name.getText());
		info.setPhone(phone.getText());
		info.setEmail(email.getText());
		info.setBday(birthday.toString());
		info.setBiography(bio.getText());

		serialize(users);
		closeEdit();

	}

	public void serialize(Users user) {
		try {
			FileOutputStream fileOut = new FileOutputStream("users.ser", false);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(user);
	        out.close();
	        fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void closeEdit() {
	    Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}

	public void importVariables(StartController start, TimelineController timeline, List<String> currentUser) {
		this.start = start;
		this.users = start.getUsers();
		this.timeline = timeline;
		this.currentUser = currentUser;
	}

	// PrePopulates the EditProfile GUI with the users information
	// Inputs: 5 strings
	// Literally just uses the strings to populate the profile textboxes
	public void prePopulate() {
		this.username.setText(currentUser.get(0).equals("null")?"":currentUser.get(0));
		this.name.setText(currentUser.get(2).equals("null")?"":currentUser.get(2));
		this.phone.setText(currentUser.get(3).equals("null")?"":currentUser.get(3));
		this.email.setText(currentUser.get(4).equals("null")?"":currentUser.get(4));
		this.bio.setText("");
	}
}


