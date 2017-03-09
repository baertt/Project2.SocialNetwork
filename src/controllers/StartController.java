package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;

//import project2.AddActivityController;
import components.Users;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartController {
	@FXML
	Button signIn;
	@FXML
	Button createAccount;

	private Users users;

	@FXML
	public void initialize(){
		users = new Users();
		deserialize();
		//addExistingUsers();
	}

	@FXML
	public void addExistingUsers() throws FileNotFoundException{
		users.readFromUserFile(this);
	}
	
	public void deserialize() {
		System.out.println("Deserializing in Start");
		 try {
	         FileInputStream fileIn = new FileInputStream("users.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         users = (Users) in.readObject();
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
	public void openSignIn(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("SignIn.fxml"));
			Pane root = (Pane) loader.load();

			SignInController signIn = (SignInController)loader.getController();
			signIn.importVariables(this);
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
			Alert r = new Alert(AlertType.NONE, "Cannot open Sign In page." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
		}
	}

	@FXML
	public void openCreateAccount(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("CreateAccount.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			CreateAccountController createAccount = (CreateAccountController)loader.getController();
			createAccount.getStart(this);

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

		Stage stage = (Stage) signIn.getScene().getWindow();
	    stage.close();
	}

	public Users getUsers() {
		return users;
	}
	
	public void setUsers(Users newUsers) {
		users = newUsers;
	}
}
