package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = null;
			if (new File("flat.temp").isFile()) {
				root = FXMLLoader.load(getClass().getResource("Testflat.fxml"));
				Scene scene = new Scene(root, 800, 600);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("EdiTix");
			} else {
				root = FXMLLoader.load(getClass().getResource("Test.fxml"));
				Scene scene = new Scene(root, 800, 600);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("EdiTix");
			}
			primaryStage.getIcons().add(new Image("file:icone.png"));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		primaryStage.setOnCloseRequest((event) -> {
			List<String> choices = new ArrayList<>();
			choices.add("Flat Design UI");
			choices.add("Classic Design UI");

			ChoiceDialog<String> dialog;
			if (new File("flat.temp").isFile()) {
				dialog = new ChoiceDialog<>("Flat Design UI", choices);
			} else {
				dialog = new ChoiceDialog<>("Classic Design UI", choices);
			}

			dialog.setTitle("EdiTix");
			dialog.setHeaderText("Choix de l'interface lors du prochain démarrage");
			dialog.setContentText("Choisissez une Interface:");

			Optional<String> result = dialog.showAndWait();

			if (result.isPresent()) {
				if (result.get().equals("Flat Design UI")) {
					try {
						File file = new File("flat.temp");
						file.createNewFile();
					} catch (IOException e) {
					}
				} else {
					if (new File("flat.temp").isFile()) {
						new File("flat.temp").delete();
					}
				}
			}

		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}