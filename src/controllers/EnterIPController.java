package controllers;

import java.util.ArrayList;
import java.util.List;

import components.Users;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class EnterIPController {
	@FXML
	TextField friend1;
	@FXML
	TextField friend2;
	@FXML
	TextField friend3;
	@FXML
	TextField friend4;
	@FXML
	Button connect;
	@FXML
	Button cancel;
	@FXML
	Label prompt;

	StartController start;
	Users users;
	List<String> currentUser;

	ArrayList<String> ips = new ArrayList<String>(4);

	public void importVariables(StartController start, List<String> currentUser) {
		this.start = start;
		this.users = start.getUsers();
		this.currentUser = currentUser;
	}

	@FXML
	public void addUsers(){
		// check if at least one field is entered
		if (!friend1.getText().equals("") || !friend2.getText().equals("") || !friend3.getText().equals("") || !friend4.getText().equals("")) {
			// check if the entered field is in the correct format
			if (checkIPInput(friend1.getText()) && checkIPInput(friend2.getText()) && checkIPInput(friend3.getText()) && checkIPInput(friend4.getText())) {
				// possible empty string will be added into ArrayList. Remember to filter out when taking ip out of ArrayList.

				ips.add(friend1.getText());
				ips.add(friend2.getText());
				ips.add(friend3.getText());
				ips.add(friend4.getText());
				openTimeline();
			}
		} else {
			prompt.setText("Please key in at least one IP Address!");
		}

	}

	// check IP input to see if it is the correct format
	public boolean checkIPInput(String ipInput) {
		String[] ipSplit = ipInput.split("\\.");
		if(ipInput.equals("")){
			return true;
		}

		if(rightFormat(ipSplit) == false ||
				notNumeric(ipSplit) == false || tooLong(ipSplit) == false){
			return false;
		} else {
			return true;
		}
	}

	// Helper for checkIpInput
	public boolean tooLong(String[] ipSplit){
		if (ipSplit[0].length() > 3 || ipSplit[1].length() > 3 || ipSplit[2].length() > 3 || ipSplit[3].length() > 3) {
			prompt.setText("Max length of each segment is 3!");
			return false;
		} else {
			return true;
		}
	}

	// Helper for checkIpInput
	public boolean notNumeric(String[] ipSplit){
		if (!isNumeric(ipSplit[0]) || !isNumeric(ipSplit[1]) || !isNumeric(ipSplit[2]) || !isNumeric(ipSplit[3])) {
			prompt.setText("Please key in an IP Address with all numbers (IPv4)");
			return false;
		} else {
			return true;
		}
	}

	// Helper for checkIpInput
	public boolean rightFormat(String[] ipSplit){
		if (ipSplit.length != 4) {
			prompt.setText("Please key in a correct IP Address including periods");
			return false;
		} else {
			return true;
		}
	}

	// check if string is numeric
	public boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");
	}

	public void openTimeline(){
		//System.out.println("HERE");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("TimelineVer2.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			TimelineController timeline = (TimelineController) loader.getController();
			timeline.importVariables(start, currentUser, ips);


			Stage secondStage = new Stage();
			Scene scene = new Scene(root);

			secondStage.setOnCloseRequest(e -> {
			        Platform.exit();
			        System.exit(0);
			   });

			Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
            secondStage.getIcons().add(anotherIcon);
		    secondStage.setTitle(currentUser.get(0));
			secondStage.setScene(scene);
			secondStage.show();
		} catch (Exception exc) {
			Alert r = new Alert(AlertType.NONE, "Cannot open the timeline." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
			exc.printStackTrace();
		}

		Stage stage = (Stage) connect.getScene().getWindow();
	    stage.close();
	}

	@FXML
	void close() {
		cancel.getScene().getWindow().hide();
	}
}