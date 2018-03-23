package application;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class LigneCreationTable extends HBox
{
	TextField t1 = new TextField();
	TextField t2 = new TextField();
	TextField t3 = new TextField();
	ComboBox<String> cb = new ComboBox<String>();

	public LigneCreationTable()
	{
		super();

		t1.setPrefWidth(150);
		t1.setPrefHeight(30);
		t1.setFont(Font.font("Arial Rounded MT Bold", 15));

		t2.setPrefWidth(130);
		t2.setPrefHeight(30);
		t2.setFont(Font.font("Arial Rounded MT Bold", 15));

		t3.setPrefWidth(207);
		t3.setPrefHeight(30);
		t3.setFont(Font.font("Arial Rounded MT Bold", 15));

		cb.setPrefWidth(130);
		cb.setPrefHeight(30);
		cb.getItems().add("INT");
		cb.getItems().add("DOUBLE");
		cb.getItems().add("FLOAT");
		cb.getItems().add("VARCHAR");
		cb.getItems().add("TEXT");

		this.getChildren().addAll(t1,cb,t2,t3);
		this.setSpacing(40);
	}

	public String getNom()
	{
		return t1.getText();
	}

	public String getType()
	{
		return cb.getValue();
	}

	public String getTaille()
	{
		if(t2.getText().length()==0)
		{
			if((cb.getValue()=="INT") || (cb.getValue()=="DOUBLE") || (cb.getValue()=="FLOAT"))
				return "10";

			else
				return "255";
		}

		else
			return t2.getText();
	}

	public String getValeurDefaut()
	{
		if(t3.getText().length()==0)
			return "NULL";
		else
			return "'"+t3.getText()+"'";
	}

	public void setNom(String nom)
	{
		t1.setText(nom);
	}

	public void setType(String type)
	{
		cb.getSelectionModel().select(type);
	}

	public void setTaille(int taille)
	{
		t2.setText(Integer.toString(taille));
	}
}
