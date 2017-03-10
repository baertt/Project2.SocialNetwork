package controllers;

import java.util.List;

import components.UserInfo;
import components.Users;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Profile2Controller {
	@FXML
	Button edit;
	@FXML
	TextArea bio;
	@FXML
	Label name;
	@FXML
	Label bday;
	@FXML
	Label email;
	@FXML
	Label phone;
	@FXML
	Tab username;

	String user;
	UserInfo info;

	StartController start;
	Users users;
	TimelineController timeline;
	List<String> currentUser;
	//private Label whichLabel;

	@FXML
	public void initialize(){
		//fillUserProfiles();
		// application user should not be able to edit bio unless they are editing their profile
		bio.setEditable(false);
	}

	/*private void fillUserProfiles() {
		for(String user:timeline.users.getUsers().keySet()){

		}
	}*/

	// Sets the users profile to display their personal information.
	public void setProfile() {
		username.setText(user);
		name.setText(info.getName());
		bday.setText(info.getBday());
		email.setText(info.getEmail());
		phone.setText(info.getPhone());
		bio.setText(info.getBiography());
		/*setLabel(whichLabel = name, 2, "");
		setLabel(whichLabel = bday, 5, "Birthday: ");
		setLabel(whichLabel = email, 4, "Email: ");
		setLabel(whichLabel = phone, 3, "Phone Number: ");
		this.bio.setText(currentUser.get(6));*/
	}

	// Helper method for setProfile
	// Inputs: Label, index, string
	// Sets the label entered to the info taken from the list of information using the index
	// to get the info from the list. The string adds a description to the info for their profile.
/*	public void setLabel(Label whichLabel, int index, String doWhat){
		this.whichLabel.setText(currentUser.get(index).equals("null")?"":doWhat + currentUser.get(index));
	}*/

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

	public void importVariables(StartController start, TimelineController timeline, String username, UserInfo info) {
		this.start = start;
		this.users = start.getUsers();
		this.timeline = timeline;
		this.user = username;
		this.info = info;
	}

	public void closeProfile() {
	    Stage stage = (Stage) edit.getScene().getWindow();
	    stage.close();
	}
}
