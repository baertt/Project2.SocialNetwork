package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;


import components.Users;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateAccountController {
	/////////////////////////////////////////////////////////////////////////////////////

	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	PasswordField confirmPass;
	@FXML
	TextField name;
	@FXML
	DatePicker birthday;
	@FXML
	TextField phone;
	@FXML
	TextField email;
	@FXML
	Button createAccount;
	@FXML
	Button cancel;
	@FXML
	Label prompt;
	@FXML
	TextField host;
	@FXML
	TextField port;

	StartController start;
	Users users;

	/////////////////////////////////////////////////////////////////////////////////////


	@FXML
	public void initialize(){
	}


	public void getStart(StartController start){
		this.start = start;
		this.users = start.getUsers();
	}

	@FXML
	public void accountCreation() throws IOException{
		// Required fields
		try {
		String currentUsername = username.getText();
		String currentPassword = password.getText();
		String currentConfirmPass = confirmPass.getText();
		// Not Required fields
		String currentName = (name.getText().equals(""))?"null":name.getText();
		String currentPhone = (phone.getText().equals(""))?"null":phone.getText();
		String currentEmail = (email.getText().equals(""))?"null":email.getText();
		String currentBirthday = (birthday.getValue() == null)?"null":"" + birthday.getValue();
		String currentHost = "255.255.255.255";
		String currentPort = "8880";
		if (requiredNotFilled(currentUsername, currentPassword, currentConfirmPass)) {
			prompt.setText("Please fill in the required fields!");
		} else if (users.checkUserName(currentUsername)) {
			prompt.setText("This username has already been chosen. Please choose another one.");
		} else if (!checkPassWordConfirmation(currentPassword, currentConfirmPass)) {
			prompt.setText("Password and Confirm Password fields are different.");
		} else {
			users.add(currentUsername, currentPassword, currentName, currentPhone,
					  currentEmail, currentBirthday, currentHost, currentPort);
			serialize(users);
			openSignIn();
		}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	// Serialize UserOb object
	// Takes a user to serialize their information
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

	/**
	 * Checks if required values are filled
	 * @param  		String username, password, confirmPassword
	 * @return      boolean (if at least one default value is not filled, returns True)
	 */
	public boolean requiredNotFilled(String username, String password, String confirmPassword) {
		return (username.equals("")) || (password.equals("")) || (confirmPassword.equals(""));
	}

	/**
	 * Checks if password and confirm password are the same.
	 * @param  		String password, confirmPassword
	 * @return      boolean (returns True if they are the same)
	 */
	public boolean checkPassWordConfirmation(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}


	// Opens the signIn FXML
	public void openSignIn(){

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("SignIn.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			SignInController signIn = (SignInController) loader.getController();
			signIn.importVariables(start);
			signIn.deserialize();

			Stage secondStage = new Stage();
			Scene scene = new Scene(root);

			secondStage.setOnCloseRequest(e -> {
			        Platform.exit();
			        System.exit(0);
			    });

			Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
			secondStage.getIcons().add(anotherIcon);
		    secondStage.setTitle("Welcome to Bubble!");
			secondStage.setScene(scene);
			secondStage.show();
		} catch (Exception exc) {
			exc.printStackTrace();
		}

		Stage stage = (Stage) createAccount.getScene().getWindow();
	    stage.close();
	}

	// Literally just closes the entire program. Completely shuts down so there are no
	// server errors when restarting.
	@FXML
	void close() {
		System.exit(0);
	}
}