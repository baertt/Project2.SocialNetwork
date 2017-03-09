package controllers;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import components.Message;
import components.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewPostController {


		StartController start;
		Users users;
		TimelineController timeline;
		Users user;


		@FXML
		Button cancel;

		@FXML
		Button post;

		@FXML
		TextArea textArea;

		Socket target;


		@FXML
		public void initialize(){}

		public void importVariables(StartController start, TimelineController timeline) {
			this.start = start;
			this.users = start.getUsers();
			this.timeline = timeline;
		}

		@FXML
		public void quitWindow() {
			cancel.getScene().getWindow().hide();
		}

		@FXML
		public void post() {
			String msg = textArea.getText();
			timeline.messageView.getItems().add(msg);
			new Thread(() ->  {
				try {
					target = new Socket("10.253.202.151", 8880);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					timeline.send(target, msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}).start();

			quitWindow();
		}

}

