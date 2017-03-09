package controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import components.TimelinePosts;
import components.Users;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TimelineController {

	StartController start;
	Users users;
	TimelinePosts posts = new TimelinePosts();
	private ServerSocket accepter;
	int port = 8880;

	@FXML
	ListView<String> messageView;
	List<String> currentUser;
	ArrayList<String> ips;

	@FXML
	public void initialize() throws FileNotFoundException{
		fillPostInfo();
		Thread serverThread = new Thread(() -> {
			try {
				accepter = new ServerSocket(port);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Server: IP address: " + accepter.getInetAddress() + " (" + port + ")");
			try{
				for (;;) {
					Socket s = accepter.accept();
					new Thread(() -> {
						try {
							receive(s);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}).start();
				}
			} catch(IOException e){
				 e.printStackTrace();
			}
			});
		serverThread.start();
	}

	void receive(Socket target) throws IOException {
		BufferedReader sockin = new BufferedReader(new InputStreamReader(target.getInputStream()));
		while (!sockin.ready()) {}
		while (sockin.ready()) {
			try {
				String msg = sockin.readLine();
				Platform.runLater(() -> {
						messageView.getItems().add(msg);
				});
			} catch (Exception e) {
				Platform.runLater(() -> badNews(e.getMessage()));
				e.printStackTrace();
			}
		}

	}


	void badNews(String what) {
		Alert badNum = new Alert(AlertType.ERROR);
		badNum.setContentText(what);
		badNum.show();
	}

	void send(Socket target, String message) throws IOException {
		PrintWriter sockout = new PrintWriter(target.getOutputStream());
		sockout.println(message);
		sockout.flush();
	}


	@FXML
	public void viewProfile(){
		openProfile();
	}

	public void openProfile(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("Profile.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			ProfileController profile = (ProfileController) loader.getController();
			profile.importVariables(start, this, currentUser);
			profile.setProfile();

			Stage secondStage = new Stage();
			Scene scene = new Scene(root);
			Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
			secondStage.getIcons().add(anotherIcon);
		    secondStage.setTitle(currentUser.get(0));
			secondStage.setScene(scene);
			secondStage.show();


		} catch (Exception exc) {
			Alert r = new Alert(AlertType.NONE, "Cannot view Profile." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
			exc.printStackTrace();
		}

	}

	@FXML
	public void signOut(){
		Alert r = new Alert(AlertType.NONE,
				"You are about to sign out.\nThis will completely close the program. \nDo you wish to continue?" ,
				ButtonType.YES, ButtonType.CANCEL);
		r.setTitle("Sign Out?");

		Optional<ButtonType> result = r.showAndWait();
		if (result.get() == ButtonType.YES){
			System.exit(0);
		} else {
		   r.close();
		}
	}

	@FXML
	public void openEditProfile(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("EditInfo.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			EditProfileController editProfile = (EditProfileController) loader.getController();
			editProfile.importVariables(start, this, currentUser);
			Stage secondStage = new Stage();
			Scene scene = new Scene(root);
			Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
			secondStage.getIcons().add(anotherIcon);
			secondStage.setScene(scene);
			secondStage.show();
		} catch (Exception exc) {
			Alert r = new Alert(AlertType.NONE, "Cannot view Profile." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
			exc.printStackTrace();
		}
	}

	public void openNewPost(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GuiMain.class.getResource("Post.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			NewPostController post = (NewPostController) loader.getController();
			post.importVariables(start, currentUser, this, ips);

			Stage secondStage = new Stage();
			Scene scene = new Scene(root);
			secondStage.setScene(scene);
			secondStage.show();
		} catch (Exception exc) {
			Alert r = new Alert(AlertType.NONE, "Cannot create a new post." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
			exc.printStackTrace();
		}
	}

	public void importVariables(StartController start, List<String> currentUser, ArrayList<String> ips) {
		this.start = start;
		this.users = start.getUsers();
		this.currentUser = currentUser;
		this.ips = ips;

		// remove possible empty strings
		this.ips.removeAll(Collections.singleton(""));
	}



	public List<String> getCurrentUserInfo() {
		return currentUser;
	}

	public void setCurrentUserInfo() {
		currentUser = users.getCurrentUser(currentUser.get(0));
	}

	public TimelinePosts getPosts() {
		return posts;
	}

	public void fillPostInfo() throws FileNotFoundException{
		posts.readFromPostFile(this);
		for(int i = 0; i < posts.size(); i++){
			messageView.getItems().add(posts.getItem(i));
		}
	}

	@FXML
	public ListView<String> getMessageView(){
		return messageView;
	}

}
