package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import components.Message;
import components.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class NewPostController {

		StartController start;
		Users users;
		TimelineController timeline;
		Users user;

		int port = 8880;

		@FXML
		Button cancel;

		@FXML
		Button post;

		@FXML
		TextArea textArea;

		Socket target;

		List<String> currentUser;

		ArrayList<String> ips;


		@FXML
		public void initialize(){}

		public void importVariables(StartController start, List<String> currentUser,
				TimelineController timeline, ArrayList<String> ips) {
			this.start = start;
			this.users = start.getUsers();
			this.timeline = timeline;
			this.currentUser = currentUser;
			this.ips = ips;
		}

		@FXML
		public void quitWindow() {
			cancel.getScene().getWindow().hide();
		}

		@FXML
		public void post() {
			String msg = textArea.getText();
			Message newMessage = new Message(currentUser.get(0), msg);
			timeline.messageView.getItems().add(newMessage.toString());
			try {
				timeline.posts.add(newMessage.toString());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new Thread(() ->  {
				try {
					target = new Socket("10.253.193.153", port);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					timeline.send(target, newMessage.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();

			quitWindow();

		}

}

