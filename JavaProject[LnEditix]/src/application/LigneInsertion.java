package application;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;



public class LigneInsertion extends HBox
{
	Label nom = new Label();
	TextField champs =  new TextField();

	public LigneInsertion(String entree)
	{
		super();

		nom.setText(entree);

		nom.setFont(Font.font("Arial Rounded MT Bold", 15));
		champs.setFont(Font.font("Arial Rounded MT Bold", 20));
		nom.setPrefWidth(100);

		this.setSpacing(40);
		this.getChildren().addAll(nom,champs);

	}

	public String getNom()
	{
		return nom.getText();
	}

	public String getChamps()
	{
		return champs.getText();
	}
}
