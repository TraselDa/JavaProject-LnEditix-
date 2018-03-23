package application;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
/***********************IMPORTATION DES BIBLIOTHEQUES UTILISE***************************************/
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/*************************
 * FIN IMPORTATION
 ***********************************************/

public class BDController {
	/****************************
	 * DECLARATION DES CONTROLES ET OBJETS UTILISES
	 ***********************/
	@FXML
	private Button butCreationBD; // button de creation de base de données

	@FXML
	private Button butCreationTable; // button de creation de table

	@FXML
	private Button butCreationTable1; // button de creation de table depuis
										// l'onglet "parcourir"

	@FXML
	private Button butCreaTable; // button de validation de creation de table

	@FXML
	private Button butSQL1; // button d'ajout d'exemple de requete de creation
							// de table a l'editeur sql

	@FXML
	private Button butSQL2; // button d'ajout d'exemple de requete de selection
							// de champs a l'editeur sql

	@FXML
	private Button butSQL3; // button d'ajout d'exemple de requete d'insertion
							// dans une table a l'editeur sql

	@FXML
	private Button butSQL4; // button d'ajout d'exemple de requete de
							// modification de champs a l'editeur sql

	@FXML
	private Button butSQL5; // button d'ajout d'exemple de requete de
							// suppression de champs a l'editeur sql

	@FXML
	private Button butExcSQL; // button d'execution de l'editeur sql

	@FXML
	private Button butTableInsertion; // button d'insertion dans la table

	@FXML
	private Button butCreaTableVal; // button de validation des ligne de
									// creation de table

	@FXML
	private TextField FiltreLigne; // champs de filtrage des ligne selon une
									// recherche

	@FXML
	private TextField textBoxNomBD; // champs d'edition du nom de la base de
									// donné a cree

	@FXML
	private TextField textBoxNomTable; // champs d'edition du nom de la table a
										// cree

	@FXML
	private TextField textBoxNomTable1; // champs d'edition du nom de la table a
										// cree depuis l'onglet 'parcourir'

	@FXML
	private Spinner<String> nbColTable; // choix du nombre de colonne pour la
										// creation de la table

	@FXML
	private Spinner<String> nbColTable1; // choix du nombre de colonne pour la
											// creation de la table depuis
											// l'onglet 'parcourir'

	@FXML
	private Spinner<String> nbInsertion; // choix du nombre de ligne pour
											// insertion

	@FXML
	private Pane panelCreationTable; // panel contenenant les controls pour la
										// creation d'une table

	@FXML
	private Pane panelCreationBD; // panel contenant les controls pour la
									// creation d'une base de donnees

	@FXML
	private Pane panelBasInsert; // panel situe en bas de l'onglet
									// insertion(ajout de ligne et validation)

	@FXML
	private FlowPane lignesCreationTable; // zone d'ajout des lignes ligne a
											// remplir pour la creation de la
											// table

	@FXML
	private TabPane tabs; // tabpane principal

	@FXML
	private TabPane parcourirTabs; // tabpane de l'onglet "parcourir"

	@FXML
	private Tab butCreation; // onglet de creation de base de données

	@FXML
	private Tab butCreation1; // onglet de remplissage des champs pour creation
								// table

	@FXML
	private Tab butParcourir; // onglet de parcour de table et bd

	@FXML
	private Tab butInsertion; // onglet insertion d'element dans une table

	@FXML
	private Tab butSQL; // onglet de l'editeur sql

	@FXML
	private Tab butExportation; // onglet exportation de la base de données

	@FXML
	private TextArea SQLText; // champs de saisie du code sql pour l'editeur sql

	@FXML
	private TextArea errorSQL; // champs d'affichage des erreurs lors de
								// l'execution d'une requete sql

	@FXML
	private ComboBox<String> listParcourTable; // liste deroulante des tables
												// pour l'onglet parcour

	@FXML
	private ComboBox<String> listParcourTable1; // liste deroulante des tables
												// pour l'onglet insertion

	@FXML
	private ComboBox<String> listCle; // liste deroulante pour le choix de cle
										// pour la table

	@FXML
	private AnchorPane panelParcTable; // zone d'ajout de
										// tableview(visualisation de table)

	@FXML
	private FlowPane paneInsertion; // zone d'insertion des lignes pour
									// insertion des enregistement dans une
									// table

	@FXML
	private ImageView insertionAttend; // zone de gif d'attende de selection de
										// table a remplir

	@FXML
	private AnchorPane tabpage; // zone de l'onglet insertion

	@FXML
	private ListView<String> listBD; // liste des base de données
	private ObservableList<String> listViewData = FXCollections.observableArrayList(); // liste
																						// des
																						// donnees
																						// de
																						// listBD

	@FXML
	private ListView<String> listTable; // liste des tables
	private ObservableList<String> myTables = FXCollections.observableArrayList(); // liste
																					// des
																					// données
																					// pour
																					// listTable

	@SuppressWarnings("rawtypes")
	private ObservableList<ObservableList> data; // liste des donnes pour
													// tableview
	@SuppressWarnings("rawtypes")
	private TableView tableview; // table de visualiasation des donnés d'une
									// table

	private String NomBD; // constante correspondant au nom de la base donne
							// connecté
	private static Stage fenetre; // constante correspondant a la fenetre parent
	private static String tableActive; // constant correspondate a la table
										// active pour creation

	private ObservableList<LigneCreationTable> TabLigneCreationTable = FXCollections.observableArrayList();
	private ObservableList<ObservableList<LigneInsertion>> TabLignesInsertion = FXCollections.observableArrayList();

	/********************************************
	 * FIN DECLARATION
	 ************************************************/

	/******************************
	 * DECLARATION DE METHODE DE REMPISSAGE DE TABLEVIEW
	 *******************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buildData(String table) {
		Connection c; // variable ce connection
		data = FXCollections.observableArrayList(); // variable de collection de
													// données de la table

		try {
			/********* CONNECTION A LA BASE DE DONNEE ************/
			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + textBoxNomBD.getText() + ".db";
			c = DriverManager.getConnection(lien);
			/******************* FIN CONNECTION ***********************/

			/********** EXECUTION DE REQUETE DE SELECTION ***********/
			String SQL = "SELECT * FROM " + table;
			ResultSet rs = c.createStatement().executeQuery(SQL);
			/*******************************************************/

			/***************
			 * GENERATION DYNAMIQUE DES COLONNES DE LA TABLE
			 ***************/
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));

				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});
				col.setPrefWidth(755 / rs.getMetaData().getColumnCount());
				col.setResizable(false);
				col.setStyle("-fx-font-size:15px");
				tableview.getColumns().addAll(col);
			}
			/***************************************************************************/

			/***********************
			 * AJOUT DE DONNEES AUX COLONNE
			 **********************/
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}
				data.add(row);

			}
			/******************************************************************************/

			/****************
			 * AJOUT DES COLONNES A LA TABLEVIEW
			 ****************************/
			tableview.setItems(data);
			/**************************************************************************/
			c.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/******************************
	 * FIN DECLARATION DE METHODE DE REMPISSAGE DE TABLEVIEW
	 *******************************/

	/******************
	 * DECLARATION DE CELLULE CUSTOMISE POUR LA LISTE DE BASE DE DONNEES
	 ***********************/
	class XCell extends ListCell<String> {
		HBox hbox = new HBox();
		Label label = new Label("");
		Pane pane = new Pane();
		Button button0 = new Button("Mod");
		Button button = new Button("Supp");

		public XCell() {
			label.setFont(Font.font("Arial Rounded MT Bold", 20));
			button.setStyle("-fx-background-color:#ff5a5f;");
			button.setTextFill(Color.WHITE);
			button0.setStyle("-fx-background-color:#2fcafa; -fx-margin-right:10px;");
			button.setCursor(Cursor.HAND);
			button0.setCursor(Cursor.HAND);
			button0.setTextFill(Color.WHITE);

			hbox.setSpacing(10);
			hbox.getChildren().addAll(label, pane, button0, button);
			HBox.setHgrow(pane, Priority.ALWAYS);

			button.setOnAction(event -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Suppression de BD");
				alert.setHeaderText("La base de données " + label.getText() + " sera supprimée");
				String nom = label.getText();
				alert.setContentText("Voulez vous continuer ?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						getListView().getItems().remove(getItem());
						new File("DataBases\\" + nom + ".db").delete();
						String toastMsg = "La base de données " + nom + " a été supprimée";
						Toast.makeText(fenetre, toastMsg, "#70c1b3");
					} catch (Exception ex) {
						String toastMsg = "Une erreur est survenue lors de la suppression de la BD";
						Toast.makeText(fenetre, toastMsg, "#ff5a5f");
					}

				}
			});

			button0.setOnAction((event) -> {
				TextInputDialog dialog = new TextInputDialog(label.getText());
				dialog.setTitle("MySGBD");
				dialog.setHeaderText("MODIFICATION DU NOM DE LA BD");
				dialog.setContentText("Entrer le nouveau Nom :");

				Optional<String> result = dialog.showAndWait();

				if (result.isPresent()) {
					String val = label.getText() + ".db";
					File[] files = new File("DataBases").listFiles();
					for (File file : files)

					{

						if (file.getName().equals(val)) {
							file.renameTo(new File("DataBases\\" + result.get() + ".db"));

						}
					}

					label.setText(result.get());
				}
			});
		}

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);

			if (item != null && !empty) {
				label.setText(item);
				setGraphic(hbox);
			}
		}
	}

	/******************
	 * FIN DECLARATION DE CELLULE CUSTOMISE POUR LA LISTE DE BASE DE DONNEES
	 ***********************/

	/******************
	 * DECLARATION DE CELLULE CUSTOMISE POUR LA LISTE DE TABLE
	 ***********************/
	class XCell1 extends ListCell<String> {
		HBox hbox = new HBox();
		Label label = new Label("");
		Pane pane = new Pane();
		Button button0 = new Button("Mod");
		Button button = new Button("Supp");

		public XCell1() {
			super();

			label.setFont(Font.font("Arial Rounded MT Bold", 20));
			button.setStyle("-fx-background-color:#ff5a5f;");
			button.setTextFill(Color.WHITE);
			button.setCursor(Cursor.HAND);
			button0.setStyle("-fx-background-color:#2fcafa; -fx-margin-right:10px;");
			button0.setCursor(Cursor.HAND);
			button0.setTextFill(Color.WHITE);

			hbox.setSpacing(10);
			hbox.getChildren().addAll(label, pane, button0, button);
			HBox.setHgrow(pane, Priority.ALWAYS);

			button.setOnAction(event -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Suppression de Table");
				alert.setHeaderText("La table " + label.getText() + " sera supprimée");
				alert.setContentText("Voulez vous continuer ?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					try {
						Class.forName("org.sqlite.JDBC");
						String lien = "jdbc:sqlite:DataBases//" + NomBD + ".db";
						Connection c = DriverManager.getConnection(lien);

						Statement stmt = c.createStatement();
						String sql = "DROP TABLE " + label.getText();
						stmt.executeUpdate(sql);
						stmt.close();
						c.close();
						String toastMsg = "La table " + label.getText() + " a été supprimée";
						Toast.makeText(fenetre, toastMsg, "#70c1b3");
						getListView().getItems().remove(getItem());

					} catch (Exception ex) {
						String toastMsg = "Une erreur est survenue lors de la suppression de la table";
						Toast.makeText(fenetre, toastMsg, "#ff5a5f");
					}

				} else {
				}
			});

			button0.setOnAction((event) -> {
				// modificationTable(label.getText());
				/**
				 * on grise les ligne existante on propose d'ajouté des lignes
				 * on renome la table et on cree une table a partir du nom
				 * precedant et un recupere les donnes de l'ancienne tabe qu'on
				 * insert dans la nvelle table en a....
				 **/

				TextInputDialog dialog = new TextInputDialog(label.getText());
				dialog.setTitle("MySGBD");
				dialog.setHeaderText("MODIFICATION DU NOM DE LA TABLE");
				dialog.setContentText("Entrer le nouveau Nom :");

				// Traditional way to get the response value.// Traditional way
				// to get the response value.
				Optional<String> result = dialog.showAndWait();

				if (result.isPresent()) {
					String requete = "ALTER TABLE " + label.getText() + " RENAME TO " + result.get();
					operationSQL(NomBD, requete, true);
					label.setText(result.get());
				}

			});
		}

		@Override
		protected void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			setText(null);
			setGraphic(null);

			if (item != null && !empty) {
				label.setText(item);
				setGraphic(hbox);
			}
		}
	}

	/******************
	 * FIN DECLARATION DE CELLULE CUSTOMISE POUR LA LISTE DE TABLE
	 ***********************/

	@SuppressWarnings("rawtypes")
	@FXML
	private void initialize() /*****************
								 * METHODE D'INITIALISATION DES CONTROLS
								 ******************/
	{

		/***************************** DESIGN ********************************/
		tabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (tabs.getSelectionModel().getSelectedIndex() == 3) {
				try {
					listParcourTable1.getSelectionModel().select(0);

					TabLignesInsertion.clear();
					paneInsertion.getChildren().clear();
					List<String> liste = listeChamp(listParcourTable1.getValue());

					VBox cadre = new VBox();
					cadre.setPadding(new Insets(20, 20, 20, 40));
					cadre.setStyle("-fx-background-color:#dddddd ; -fx-background-radius:10;");
					cadre.setSpacing(20);
					cadre.setPrefWidth(700);

					ObservableList<LigneInsertion> TabLigneInsertion = FXCollections.observableArrayList();
					for (String s : liste) {
						LigneInsertion ligne = new LigneInsertion(s);
						TabLigneInsertion.add(ligne);
						cadre.getChildren().add(ligne);
					}
					panelBasInsert.setDisable(false);
					TabLignesInsertion.add(TabLigneInsertion);
					paneInsertion.getChildren().add(cadre);

				} catch (Exception ex) {
				}
			}

			if ((tabs.getSelectionModel().getSelectedIndex() != 1) && (oldValue == butCreation1)) {
				lignesCreationTable.getChildren().clear();
			}

		});

		/*******************************************************************/

		ObservableList<String> valeur = FXCollections.observableArrayList(); // donne
																				// de
																				// remplissage
																				// de
																				// la
																				// zone
																				// de
																				// choix
																				// de
																				// colonne
																				// de
																				// table
		ObservableList<String> valeur1 = FXCollections.observableArrayList(); // donne
																				// de
																				// remplissage
																				// de
																				// la
																				// zone
																				// de
																				// choix
																				// ligne
																				// a
																				// inserer

		/************************************
		 * REMPLISSAGE DES ZONES DE TAILLE
		 *************************************/
		for (int i = 1; i <= 20; i++)
			valeur.add(Integer.toString(i));

		for (int i = 0; i <= 20; i++)
			valeur1.add(Integer.toString(i));

		SpinnerValueFactory<String> value = new SpinnerValueFactory.ListSpinnerValueFactory<String>(valeur);
		SpinnerValueFactory<String> value1 = new SpinnerValueFactory.ListSpinnerValueFactory<String>(valeur1);

		nbColTable.setValueFactory(value);
		nbColTable1.setValueFactory(value);
		nbInsertion.setValueFactory(value1);
		value.setValue("1");
		value1.setValue("0");
		/********************************************************************************************************/

		/************************
		 * REMPLISSAGE DE LA LISTE DE BASE DE DONNEES
		 ********************************/
		File[] files = new File("DataBases").listFiles();
		for (File file : files) {
			String ext = "";
			try {
				ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			} catch (Exception e) {
			}
			if ((file.isFile()) && ext.equals("db")) {
				listViewData.add(file.getName().substring(0, file.getName().lastIndexOf(".")));

			}
		}
		listBD.setItems(listViewData);
		listBD.setCellFactory(param -> new XCell());
		/*************************************************************************************************/

		/**********************
		 * ACTION DE CREATION DE LA BASE DE DONNE
		 *******************************/
		butCreationBD.setOnAction((event) -> {
			connectionBD(textBoxNomBD.getText());
			Stage primarystage = (Stage) butCreationBD.getScene().getWindow();
			fenetre = primarystage;
			primarystage.setTitle("MySGBD - Connect to : " + textBoxNomBD.getText());
			NomBD = textBoxNomBD.getText();
		});
		/*****************************************************************************************/

		/**************************
		 * ACTION DE CREATION DE TABLE
		 ***********************************/
		butCreationTable.setOnAction((event) -> {
			butCreation.setDisable(true);
			butCreation1.setDisable(false);
			tabs.getSelectionModel().selectNext();

			TabLigneCreationTable.clear();
			for (int i = 0; i < Integer.parseInt(nbColTable.getValue()); i++) {
				LigneCreationTable ligne = new LigneCreationTable();
				TabLigneCreationTable.add(ligne);
				lignesCreationTable.getChildren().add(ligne);
			}
			tableActive = textBoxNomTable.getText();
		});
		/*****************************************************************************************/

		/**************
		 * ACTION DE CREATION DE TABLE A PARTIR DE L'ONGLET PARCOURIR
		 ****************/
		butCreationTable1.setOnAction((event) -> {
			butCreation1.setDisable(false);
			tabs.getSelectionModel().selectPrevious();
			butCreationTable1.setDisable(true);

			TabLigneCreationTable.clear();
			for (int i = 0; i < Integer.parseInt(nbColTable1.getValue()); i++) {
				LigneCreationTable ligne = new LigneCreationTable();
				TabLigneCreationTable.add(ligne);
				lignesCreationTable.getChildren().add(ligne);
			}
			tableActive = textBoxNomTable1.getText();
			textBoxNomTable1.setText("");
		});
		/******************************************************************************************/

		/***********************
		 * ACTION DE VALIDATION DES LIGNES POUR CREATION DE TABLE
		 *****************/
		butCreaTableVal.setOnAction((event) -> {
			lignesCreationTable.setDisable(true);
			butCreaTableVal.setDisable(true);
			for (LigneCreationTable x : TabLigneCreationTable) {
				// insertion des nom des champs dans le combobox des cle
				listCle.getItems().add(x.getNom());
			}
			String toastMsg = "Choississez une clé pour continuer";
			Toast.makeText(fenetre, toastMsg, "#2fcafa");

		});
		/****************************************************************************************/

		/******************************
		 * ACTION DE CREATION DE TABLE
		 **********************************/
		butCreaTable.setOnAction((event) -> {
			creationTable(tableActive);
		});
		/********************************************************************************************/

		/**********************
		 * ACTION D'AJOUT DES TEXTES EXAMPLE A L'EDUTEUR SQL
		 ****************/
		butSQL1.setOnAction((event) -> {
			String nexText = "CREATE TABLE Nomtable (" + "  champs1 type , " + " champs2 type " + " )";
			SQLText.setText(nexText);
		});

		butSQL2.setOnAction((event) -> {
			String nexText = "SELECT champs1 , champs2 FROM Nomtable WHERE 1";
			SQLText.setText(nexText);
		});

		butSQL3.setOnAction((event) -> {
			String nexText = "INSERT INTO Nomtable ( champs1 ,  champs2 ) VALUES ('value-1','value-2')";
			SQLText.setText(nexText);
		});

		butSQL4.setOnAction((event) -> {
			String nexText = "UPDATE Nomtable SET champs1= 'value-1',champs2 = 'value-2' WHERE 1";
			SQLText.setText(nexText);
		});

		butSQL5.setOnAction((event) -> {
			String nexText = "DELETE FROM Nomtable WHERE 0";
			SQLText.setText(nexText);
		});
		/***********************************************************************************************************/

		/****************************
		 * ACTION D'EXECUTION DE REQUETE SQL DE L'EDITEUR SQL
		 *******************/
		butExcSQL.setOnAction((event) -> {
			operationSQL(textBoxNomBD.getText(), SQLText.getText(), true);
		});
		/************************************************************************************************/

		/*************************
		 * ACTION D'OUVERTURE DE BASE DE DONNEE AU CLICK
		 ************************/
		listBD.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			textBoxNomBD.setText(newValue);
			NomBD = textBoxNomBD.getText();
			connectionBD1(newValue.toString());
			Stage primarystage = (Stage) butCreationBD.getScene().getWindow();
			primarystage.setTitle("MySGBD - Connect to : " + newValue);

		});
		/*********************************************************************************************/

		/****************************
		 * ACTION D'OUVERTURE DE TABLE AU CLICK
		 *********************************/
		listTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				panelParcTable.getChildren().remove(tableview);
			} catch (Exception e) {
			} // suppression de l'ancien tableview
			parcourirTabs.getSelectionModel().selectNext();
			listParcourTable.getSelectionModel().select(newValue);
			tableview = new TableView();
			tableview.setPrefHeight(338);
			tableview.setPrefWidth(772);
			tableview.setLayoutX(14);
			tableview.setLayoutY(69);
			buildData(newValue);
			panelParcTable.getChildren().add(tableview);
		});
		/*************************************************************************************************/

		/*****************************
		 * ACTION DE CHOIX DE TABLE A PARCOURIR
		 **********************************/
		listParcourTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			try {
				panelParcTable.getChildren().remove(tableview);
			} catch (Exception e) {
			} // suppression de l'ancien tableview
			tableview = new TableView();
			buildData(newValue);
			tableview.setPrefHeight(338);
			tableview.setPrefWidth(772);
			tableview.setLayoutX(14);
			tableview.setLayoutY(69);

			panelParcTable.getChildren().add(tableview);
		});
		/**************************************************************************************************/

		/******************************
		 * ACTION DE CHOIX DE TABLE A REMPLIR
		 **************************************/
		listParcourTable1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			listTable.getSelectionModel().select(newValue);
			TabLignesInsertion.clear();
			paneInsertion.getChildren().clear();
			tabpage.getChildren().remove(insertionAttend);
			List<String> liste = listeChamp(newValue);

			VBox cadre = new VBox();
			cadre.setPadding(new Insets(20, 20, 20, 40));
			cadre.setStyle("-fx-background-color:#dddddd ; -fx-background-radius:10;");
			cadre.setSpacing(20);
			cadre.setPrefWidth(700);

			ObservableList<LigneInsertion> TabLigneInsertion = FXCollections.observableArrayList();
			for (String s : liste) {
				LigneInsertion ligne = new LigneInsertion(s);
				TabLigneInsertion.add(ligne);
				cadre.getChildren().add(ligne);
			}
			TabLignesInsertion.add(TabLigneInsertion);
			paneInsertion.getChildren().add(cadre);

		});
		/*****************************************************************************************/

		/************************
		 * ACTION DE DEFINITION DE NOMBRE DE LIGNE A INSERER
		 ***************/
		nbInsertion.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (!listParcourTable1.getValue().isEmpty()) {
				if (Integer.parseInt(oldValue) <= Integer.parseInt(newValue)) {
					List<String> liste = listeChamp(listParcourTable1.getValue());
					for (int i = 0; i < Integer.parseInt(newValue); i++) {
						VBox cadre = new VBox();
						cadre.setPadding(new Insets(20, 20, 20, 40));
						cadre.setStyle("-fx-background-color:#dddddd ; -fx-background-radius:10;");
						cadre.setSpacing(20);
						cadre.setPrefWidth(700);
						ObservableList<LigneInsertion> TabLigneInsertion = FXCollections.observableArrayList();

						for (String s : liste) {
							LigneInsertion ligne = new LigneInsertion(s);
							TabLigneInsertion.add(ligne);
							cadre.getChildren().add(ligne);
						}
						TabLignesInsertion.add(TabLigneInsertion);
						paneInsertion.getChildren().add(cadre);
					}
				} else {
					if (paneInsertion.getChildren().size() > 1)
						paneInsertion.getChildren().remove(paneInsertion.getChildren().size() - 1);
				}
			}

		});
		/********************************************************************************************************/

		/*******************************************
		 * CHOIX DE CLE POUR LA TABLE
		 ************************************/
		listCle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			butCreaTable.setDisable(false);
		});
		/*********************************************
		 * FIN CHOIX DE CLE POUR LA TABLE
		 ***************************************************************/

		/***************************
		 * ACTION DE SWITCH ENTRES LES TABS DE PARCOUR
		 *****************************/
		parcourirTabs.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (parcourirTabs.getSelectionModel().getSelectedIndex() == 1) {
				FiltreLigne.setDisable(true);
			} else

			{
				FiltreLigne.setDisable(false);
			}
		});
		/*************************************************************************************************/

		/****************************
		 * ACTION D'ACTIVATION DU BOUTON DE CREATION BD
		 *****************************/
		textBoxNomBD.textProperty().addListener((observable, oldValue, newValue) -> {
			if (textBoxNomBD.getText().length() >= 2) {
				butCreationBD.setDisable(false);
			} else {
				butCreationBD.setDisable(true);
			}
		});
		/***************************************************************************************************/

		/***************************
		 * ACTION D'ACTIVATION DU BOUTON DE CREATION DE TABLE
		 *************************/
		textBoxNomTable.textProperty().addListener((observable, oldValue, newValue) -> {
			if (textBoxNomTable.getText().length() >= 2) {
				butCreationTable.setDisable(false);
			} else {
				butCreationTable.setDisable(true);
			}
		});
		/*****************************************************************************************************/

		/**********
		 * ACTION D'ACTIVATION DU BOUTON DE CREATION DE TABLE A PARTIR DE
		 * L'ONGLET PARCOURIR
		 ************/
		textBoxNomTable1.setOnKeyPressed((event) -> {
			if (textBoxNomTable1.getText().length() >= 2) {
				butCreationTable1.setDisable(false);
			} else {
				butCreationTable1.setDisable(true);
			}
		});
		/*******************************************************************************************************/

		/**************************
		 * ACTION DE VIDAGE DE LA ZONE D'ERREUR
		 *****************************************/
		SQLText.textProperty().addListener((observable, oldValue, newValue) -> {
			errorSQL.setText(null);
		});
		/*******************************************************************************************************/

		/*************************
		 * ACTION DE FILTRAGE DE LA TABLE (RECHERCHE)
		 ************************************/
		FiltreLigne.textProperty().addListener((observable, oldValue, newValue) -> {
			ObservableList<String> newList = FXCollections.observableArrayList();
			try {
				if (parcourirTabs.getSelectionModel().getSelectedIndex() == 0) {
					listTable.getItems().clear();

					if (newValue.length() == 0) {
						newList.addAll(myTables);
					} else {
						for (String element : myTables) {
							if (element.toLowerCase().contains(newValue.toLowerCase())) {
								newList.add(element);
							}
						}
					}
					listTable.setItems(newList);
				}
			} catch (Exception ex) {
			}
		});
		/*******************************************************************************************/

		/************************
		 * ACTION D'INSERTION DES LIGNE DANS UNE TABLE
		 *****************/
		butTableInsertion.setOnAction((event) -> {
			Connection c = null;
			Statement stmt = null;
			try {
				String FullRequete = "";
				String table = listParcourTable1.getValue();
				for (ObservableList<LigneInsertion> t : TabLignesInsertion) {
					StringBuilder requete = new StringBuilder("INSERT INTO " + table + " ( ");

					for (LigneInsertion l : t) {
						requete.append(l.getNom() + " , ");
					}

					requete.deleteCharAt(requete.length() - 2);
					requete.append(") VALUES (");

					for (LigneInsertion l : t) {
						requete.append("'" + l.getChamps() + "' , ");
					}
					requete.deleteCharAt(requete.length() - 2);
					requete.append(")");

					FullRequete += requete.toString() + " ; ";
				}

				Class.forName("org.sqlite.JDBC");
				String lien = "jdbc:sqlite:DataBases//" + NomBD + ".db";
				c = DriverManager.getConnection(lien);

				stmt = c.createStatement();
				String sql = FullRequete;
				stmt.executeUpdate(sql);
				stmt.close();
				c.close();

				try {
					panelParcTable.getChildren().remove(tableview);
				} catch (Exception e) {
				} // suppression de l'ancien tableview
				tableview = new TableView();
				tableview.setLayoutX(14);
				tableview.setLayoutY(69);
				buildData(listParcourTable1.getValue());
				tableview.setPrefHeight(338);
				tableview.setPrefWidth(772);
				panelParcTable.getChildren().add(tableview);

				String toastMsg = "Les lignes ont été insérées dans la table " + table;
				Toast.makeText(fenetre, toastMsg, "#70c1b3");

				tabs.getSelectionModel().selectPrevious();
				paneInsertion.getChildren().clear();
			} catch (Exception e) {
				String toastMsg;

				if (e.getMessage().startsWith("[SQLITE_CONSTRAINT_PRIMARYKEY]"))
					toastMsg = "L'une des valeur de la clé primaire est deja attribuée";
				else
					toastMsg = "Une erreur est survenue lors de l'insertion dans la table "
							+ listParcourTable1.getValue();

				Toast.makeText(fenetre, toastMsg, "#ff5a5f");

				System.out.println(e.getMessage());
			}

		});
		/***********************************************************************************************************/

	}

	/*******************************
	 * METHODE DE CONNECTION LORS DE LA CREATION
	 *****************************************/
	public void connectionBD(String nom) {
		Stage primarystage = (Stage) butCreationBD.getScene().getWindow();
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + nom + ".db";

			c = DriverManager.getConnection(lien);

			String toastMsg = "La base de données a été crée !";
			Toast.makeText(primarystage, toastMsg, "#70c1b3");
			panelCreationTable.setDisable(false);
			butCreationBD.setDisable(true);
			textBoxNomBD.setDisable(true);
			butSQL.setDisable(false);
			afficherTables(c);
			c.close();

		} catch (Exception e) {
			String toastMsg = "Impossible de créer la base de données!";
			Toast.makeText(primarystage, toastMsg, "#ff5a5f");
		}
	}

	/***********************************************************************************/

	/*****************
	 * METHODE DE CONNECTION A LA BASE DE DONNE
	 *******************/
	public void connectionBD1(String nom) {
		Stage primarystage = (Stage) butCreationBD.getScene().getWindow();
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + nom + ".db";

			c = DriverManager.getConnection(lien);

			String toastMsg = "La base de données a été ouverte!";
			Toast.makeText(primarystage, toastMsg, "#70c1b3");
			butCreation.setDisable(true);
			butCreation1.setDisable(true);
			butParcourir.setDisable(false);
			butInsertion.setDisable(false);
			butExportation.setDisable(false);
			butSQL.setDisable(false);
			tabs.getSelectionModel().select(butParcourir);
			afficherTables(c);
			c.close();
		} catch (Exception e) {
			String toastMsg = "Impossible d'ouvrir la base de données!";
			Toast.makeText(primarystage, toastMsg, "#ff5a5f");
		}
	}

	/****************************************************************************************/

	/************************
	 * METHODE D'EXECUTION DE REQUETE SQL
	 *****************************/
	public void operationSQL(String nom, String requete, boolean bool) {
		Connection c = null;
		Statement stmt = null;
		Stage primarystage = (Stage) butCreationBD.getScene().getWindow();
		try {
			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + nom + ".db";
			c = DriverManager.getConnection(lien);

			stmt = c.createStatement();
			String sql = requete;
			stmt.executeUpdate(sql);
			stmt.close();
			afficherTables(c);
			c.close();

			if (bool) {
				String toastMsg = "La requete a été exécutée avec succès!";
				Toast.makeText(primarystage, toastMsg, "#70c1b3");
			}

			butCreation.setDisable(true);
			butCreation1.setDisable(true);
			butParcourir.setDisable(false);
			butInsertion.setDisable(false);
			butExportation.setDisable(false);
		} catch (Exception e) {
			String toastMsg = "Une erreur est survenue lors de l'exécution";
			Toast.makeText(primarystage, toastMsg, "#ff5a5f");
			errorSQL.setText(
					e.getMessage().substring(e.getMessage().lastIndexOf("(") + 1, e.getMessage().length() - 1));
		}
	}

	/**************************************************************************************************************/

	/***********************************
	 * METHODE DE RECUPERATON DE LA LISTE DE TABLE
	 *******************************/
	public static ObservableList<String> getTablesList(Connection conn) throws SQLException {

		ObservableList<String> listofTable = FXCollections.observableArrayList();
		;

		DatabaseMetaData md = conn.getMetaData();

		ResultSet rs = md.getTables(null, null, "%", null);

		while (rs.next()) {
			if (rs.getString(4).equalsIgnoreCase("TABLE")) {
				listofTable.add(rs.getString(3));
			}
		}
		return listofTable;
	}

	/************************************************************************************************/

	public void afficherTables(Connection c) {
		try {
			ObservableList<String> Tables = getTablesList(c);
			myTables.addAll(Tables);
			listTable.setItems(Tables);
			listTable.setCellFactory(param -> new XCell1());

			// remplissage de la combobox de parcour des tables
			listParcourTable.setItems(Tables);
			listParcourTable1.setItems(Tables);
		} catch (Exception ex) {
		}
	}

	public int NbreColonneTable(String table) {
		int nb = 0;

		Connection c;
		data = FXCollections.observableArrayList();
		try {

			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + textBoxNomBD.getText() + ".db";

			c = DriverManager.getConnection(lien);
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "SELECT * FROM " + table;
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);
			nb = rs.getMetaData().getColumnCount();
			c.close();
		} catch (Exception ex) {
		}
		return nb;
	}

	// fonction qui retourne une liste contenant le nom de touts les champs
	// d'une table.

	public List<String> listeChamp(String tableNom) {
		List<String> liste = FXCollections.observableArrayList();
		Connection c;
		try {

			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + textBoxNomBD.getText() + ".db";

			c = DriverManager.getConnection(lien);
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "SELECT * FROM " + tableNom;
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				liste.add(rs.getMetaData().getColumnName(i + 1));
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return liste;
	}

	public List<String> listeTypeChamp(String tableNom) {
		List<String> liste = FXCollections.observableArrayList();
		Connection c;
		try {

			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + textBoxNomBD.getText() + ".db";

			c = DriverManager.getConnection(lien);
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "SELECT * FROM " + tableNom;
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				liste.add(rs.getMetaData().getColumnTypeName(i + 1));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return liste;
	}

	public List<Integer> listeTailleChamp(String tableNom) {
		List<Integer> liste = FXCollections.observableArrayList();
		Connection c;
		try {

			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + textBoxNomBD.getText() + ".db";

			c = DriverManager.getConnection(lien);
			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = "SELECT * FROM " + tableNom;
			// ResultSet
			ResultSet rs = c.createStatement().executeQuery(SQL);

			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				liste.add(rs.getMetaData().getPrecision(i + 1));
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return liste;
	}

	public void creationTable(String nom) {
		Stage primarystage = (Stage) butCreationBD.getScene().getWindow();

		Connection c = null;
		Statement stmt = null;
		try {
			StringBuilder requete = new StringBuilder("CREATE TABLE " + nom + " ( ");

			for (LigneCreationTable x : TabLigneCreationTable) {
				requete.append(x.getNom() + " ");
				requete.append(x.getType() + "(");
				requete.append(x.getTaille() + ") DEFAULT ");
				requete.append(x.getValeurDefaut() + " , ");

				// insertion des nom des champs dans le combobox des cle
				listCle.getItems().add(x.getNom());
			}
			requete.append("PRIMARY KEY ( ");
			requete.append(listCle.getValue());
			requete.append(" ) ");
			requete.append(")");

			Class.forName("org.sqlite.JDBC");
			String lien = "jdbc:sqlite:DataBases//" + textBoxNomBD.getText() + ".db";
			c = DriverManager.getConnection(lien);

			stmt = c.createStatement();
			String sql = requete.toString();
			stmt.executeUpdate(sql);
			stmt.close();
			afficherTables(c);
			c.close();

			butCreation1.setDisable(true);
			butParcourir.setDisable(false);
			butInsertion.setDisable(false);
			butExportation.setDisable(false);

			String toastMsg = "La table a été crée !";
			Toast.makeText(primarystage, toastMsg, "#70c1b3");

			tabs.getSelectionModel().selectNext();
			lignesCreationTable.getChildren().clear();
			listCle.getItems().clear();
			lignesCreationTable.setDisable(false);
			butCreaTable.setDisable(true);
			butCreaTableVal.setDisable(false);
		} catch (Exception e) {
			String toastMsg = "Impossible de créer la table!";
			Toast.makeText(primarystage, toastMsg, "#ff5a5f");
		}

	}

	public void modificationTable(String table) {
		tabs.getSelectionModel().selectPrevious();
		butCreation1.setDisable(false);
		lignesCreationTable.getChildren().clear();

		List<String> lesChamps = listeChamp(table);
		List<String> lesTypes = listeTypeChamp(table);
		List<Integer> lesTailles = listeTailleChamp(table);

		TabLigneCreationTable.clear();
		for (int i = 0; i < lesChamps.size(); i++) {

			LigneCreationTable ligne = new LigneCreationTable();

			ligne.setNom(lesChamps.get(i));
			ligne.setType(lesTypes.get(i));
			ligne.setTaille(lesTailles.get(i));

			TabLigneCreationTable.add(ligne);
			lignesCreationTable.getChildren().add(ligne);
		}
		tableActive = table;
	}

	public String getBDName() {
		return NomBD;
	}
}
