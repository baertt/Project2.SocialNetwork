package controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GuiMain extends Application{
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(GuiMain.class.getResource("Start.fxml"));
		Pane root = (Pane) loader.load();

		Image anotherIcon = new Image("https://lh3.ggpht.com/am4rWpEvZqhjEMJoD4Imp-tdKxtQpsa6uel50xRHegrxtIybnDdT8spmvLOH9wPZiIs=w300");
        primaryStage.getIcons().add(anotherIcon);
	    primaryStage.setTitle("Welcome to Bubble!");

	    // This completely shuts down the server when the user clicks close
	    primaryStage.setOnCloseRequest(e -> {
	        Platform.exit();
	        System.exit(0);
	    });


		Scene scene = new Scene(root, 355, 190);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args){

		launch(args);
	}
}


