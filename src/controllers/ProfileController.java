package controllers;


import java.time.LocalDate;
import java.util.List;

import javafx.event.Event;
//import Objects.UserInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import components.UserInfo;
import components.Users;

public class ProfileController  {
	@FXML
	Button edit;
	@FXML
	TextArea biography;
	@FXML
	ImageView profilePic;
	@FXML
	Label name;
	@FXML
	Label birthday;
	@FXML
	Label email;
	@FXML
	Label phoneNumber;

	StartController start;
	Users users;
	TimelineController timeline;
	List<String> currentUser;


	@FXML
	public void initialize(){
		biography.setEditable(false);
	}

	public void setProfile() {
		this.name.setText(currentUser.get(2).equals("null")?"":"Name: " + currentUser.get(2));
		this.birthday.setText(currentUser.get(5).equals("null")?"":"Birthday: " + currentUser.get(5));
		this.email.setText(currentUser.get(4).equals("null")?"":"Email: " + currentUser.get(4));
		this.phoneNumber.setText(currentUser.get(3).equals("null")?"":"Phone Number: " + currentUser.get(3));
		this.biography.setText("My username is " + currentUser.get(0) + "! Please view my profile!");
	}

	public void openEdit(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("EditInfo.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			EditProfileController editProfile = (EditProfileController) loader.getController();
			editProfile.importVariables(start, timeline, currentUser);
			editProfile.prePopulate((timeline.currentUser.get(0).equals("null"))?"":timeline.currentUser.get(0),
					(timeline.currentUser.get(2).equals("null"))?"":timeline.currentUser.get(2),
							(timeline.currentUser.get(3).equals("null"))?"":timeline.currentUser.get(3),
									(timeline.currentUser.get(4).equals("null"))?"":timeline.currentUser.get(4), "");

			Stage secondStage = new Stage();
			Scene scene = new Scene(root);
			Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
			secondStage.getIcons().add(anotherIcon);
			secondStage.setScene(scene);
			secondStage.show();

			closeProfile();
		} catch (Exception exc) {
			Alert r = new Alert(AlertType.NONE, "Cannot open the edit page." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
		}

	}

	@FXML
	public void editProfile(){
		openEdit();
	}

	public void importVariables(StartController start, TimelineController timeline, List<String> currentUser) {
		this.start = start;
		this.users = start.getUsers();
		this.timeline = timeline;
		this.currentUser = currentUser;
	}

	public void closeProfile() {
	    Stage stage = (Stage) edit.getScene().getWindow();
	    stage.close();
	}

}


