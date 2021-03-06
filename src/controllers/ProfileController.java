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
	private Label whichLabel;


	@FXML
	public void initialize(){
		// application user should not be able to edit bio unless they are editing their profile
		biography.setEditable(false);
	}

	// Sets the users profile to display their personal information.
	public void setProfile() {
		setLabel(whichLabel = name, 2, "");
		setLabel(whichLabel = birthday, 5, "Birthday: ");
		setLabel(whichLabel = email, 4, "Email: ");
		setLabel(whichLabel = phoneNumber, 3, "Phone Number: ");
		this.biography.setText(currentUser.get(6));
	}

	// Helper method for setProfile
	// Inputs: Label, index, string
	// Sets the label entered to the info taken from the list of information using the index
	// to get the info from the list. The string adds a description to the info for their profile.
	public void setLabel(Label whichLabel, int index, String doWhat){
		this.whichLabel.setText(currentUser.get(index).equals("null")?"":doWhat + currentUser.get(index));
	}

	public void openEdit(){
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("EditInfo.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			EditProfileController editProfile = (EditProfileController) loader.getController();
			editProfile.importVariables(start, timeline, currentUser);

			editProfile.prePopulate();
			System.out.println(currentUser);

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


