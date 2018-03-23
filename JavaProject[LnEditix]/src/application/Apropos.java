package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class Apropos {
	public static void afficher(Stage ownerStage, String col, String col1, String col2) {
		Stage toastStage = new Stage();
		toastStage.initOwner(ownerStage);
		toastStage.setResizable(false);
		toastStage.initStyle(StageStyle.TRANSPARENT);

		Text titre = new Text("EdiTix");
		titre.setFont(Font.font("System", 40));
		titre.setStyle("-fx-font-weight:bold");
		titre.setFill(Color.web(col1));

		titre.setLayoutX(50);
		titre.setLayoutY(70);

		Text sstitre = new Text("Version 1.0 \nEdtion de Textes \nGestion de Base de Données");
		sstitre.setFont(Font.font("System", 20));
		sstitre.setStyle("-fx-font-weight:bold");
		sstitre.setFill(Color.web(col2));

		sstitre.setLayoutX(50);
		sstitre.setLayoutY(100);

		Text text = new Text("Developpé par : ");
		text.setFont(Font.font("System", 16));
		text.setFill(Color.web(col2));

		text.setLayoutX(50);
		text.setLayoutY(180);

		Text noms = new Text(
				"ALAO Paul-Hermann • KOUAKOU Akoua Roxane \nBAMBA Arnaud • EHOUNOU Semira\nTANOE Sarah • KOUADIO Stephane\nTRAORE SELIM • YATTE William");
		noms.setFont(Font.font("System", 16));
		noms.setStyle("-fx-font-weight:bold");
		noms.setFill(Color.web(col1));

		noms.setLayoutX(50);
		noms.setLayoutY(220);

		Pane root = new Pane();
		root.setStyle("-fx-background-radius: 10; -fx-background-color:" + col + "; -fx-padding: 20px;");
		root.setOpacity(0);

		root.setPrefWidth(500);
		root.setPrefHeight(300);
		root.getChildren().addAll(titre, sstitre, text, noms);

		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		toastStage.setScene(scene);
		toastStage.show();

		Timeline fadeInTimeline = new Timeline();
		KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(500),
				new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
		fadeInTimeline.getKeyFrames().add(fadeInKey1);
		fadeInTimeline.setOnFinished((ae) -> {
			new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Timeline fadeOutTimeline = new Timeline();
				KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(500),
						new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
				fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
				fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
				fadeOutTimeline.play();
			}).start();
		});
		fadeInTimeline.play();
	}
}