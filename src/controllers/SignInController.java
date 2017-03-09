package controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.List;

import components.UserInfo;
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

public class SignInController {
	@FXML
	Button signIn;
	@FXML
	Button cancel;
	@FXML
	TextField username;
	@FXML
	PasswordField password;
	@FXML
	Label prompt;

	StartController start;
	Users users;
	List<String> currentUser;

	@FXML
	public void initialize(){
	}

	public void deserialize() {
		System.out.println("Deserializing in SignIn");
		 try {
	         FileInputStream fileIn = new FileInputStream("users.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         start.setUsers((Users) in.readObject());
	         users = start.getUsers();
	         in.close();
	         fileIn.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c) {
	         System.out.println("UserObs class not found");
	         c.printStackTrace();
	         return;
	      }
	}
	@FXML
	public void signIn(){
		String currentUsername = username.getText();
		String currentPassword = password.getText();
		boolean userExist = users.checkUser(currentUsername, currentPassword);
		if (!userExist) {
			prompt.setText("Username or Password doesn't exist");
		} else {
			currentUser = users.getCurrentUser(currentUsername);
			openGetIP();
			//openTimeline();
		}
	}

	@FXML
	public void openGetIP(){
		try{
	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("EnterIP.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			

			EnterIPController enterIP = (EnterIPController) loader.getController();
			enterIP.importVariables(start, currentUser);

			Stage secondStage = new Stage();
			Scene scene = new Scene(root);

			secondStage.setOnCloseRequest(e -> {
			        Platform.exit();
			        System.exit(0);
			   });
			Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
            secondStage.getIcons().add(anotherIcon);
			//secondStage.getIcons().add(new Image(imgLink));
		    secondStage.setTitle(currentUser.get(0));
			secondStage.setScene(scene);
			secondStage.show();
		}catch (Exception exc) {
			Alert r = new Alert(AlertType.NONE, "Cannot open the add users interface." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
			exc.printStackTrace();
		}
	}
/*	public void openTimeline(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("TimelineVer2.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			TimelineController timeline = (TimelineController) loader.getController();
			timeline.importVariables(start, currentUser);


			Stage secondStage = new Stage();
			Scene scene = new Scene(root);

			secondStage.setOnCloseRequest(e -> {
			        Platform.exit();
			        System.exit(0);
			   });

			Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
            secondStage.getIcons().add(anotherIcon);
			//secondStage.getIcons().add(new Image(imgLink));
		    secondStage.setTitle(currentUser.get(0));
			secondStage.setScene(scene);
			secondStage.show();
		} catch (Exception exc) {
			Alert r = new Alert(AlertType.NONE, "Cannot open the timeline." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
			exc.printStackTrace();
		}

		Stage stage = (Stage) signIn.getScene().getWindow();
	    stage.close();
	}*/

	@FXML
	void close() {
		cancel.getScene().getWindow().hide();
	}

	void importVariables(StartController start) {
		this.start = start;
		this.users = start.getUsers();
	}
}
