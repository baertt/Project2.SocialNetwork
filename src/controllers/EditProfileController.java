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
		// get UserInfo
		UserInfo currentUserInfo = users.getCurrentUserInfo(username.getText());
		currentUserInfo.setName((name.getText().equals(""))?"null":name.getText());
		if (birthday.getValue() != null) {
			currentUserInfo.setBday("" + birthday.getValue());
		}
		//currentUserInfo.setBday((birthday.getValue() == null)?:"" + birthday.getValue());
		currentUserInfo.setPhone((phone.getText().equals(""))?"null":phone.getText());
		currentUserInfo.setEmail((email.getText().equals(""))?"null":email.getText());
		// if password field = confirm password field and when the user did not leave it blank.
		// when user leaves field blank, should not change password to empty string.
		if (password.getText().equals(confirmPass) && !password.getText().equals("")) {
			currentUserInfo.setPassword(password.getText());
		}
		timeline.setCurrentUserInfo();

		serialize(users);
		closeEdit();

	}

	public void serialize(Users user) {
		System.out.println("Serializing in EditProfile");
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
	}

	public void prePopulate(String username, String name, String phone,
			String email, String bio) {
		this.username.setText(username);
		this.name.setText(name);
		this.phone.setText(phone);
		this.email.setText(email);
		this.bio.setText(bio);
	}







}


