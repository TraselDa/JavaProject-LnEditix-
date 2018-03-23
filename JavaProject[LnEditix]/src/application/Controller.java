package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.languagetool.JLanguageTool;
import org.languagetool.language.English;
import org.languagetool.language.French;
import org.languagetool.rules.RuleMatch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

	@FXML
	private MenuItem PrintId;
	@FXML
	private MenuItem Converter;
	@FXML
	private MenuItem save;
	@FXML
	private MenuItem saveAs;
	@FXML
	private MenuItem open;
	@FXML
	private MenuItem essaiecut;
	@FXML
	private MenuItem Search;
	@FXML
	private MenuItem essaiecopy;
	@FXML
	private MenuItem Nouveau;
	@FXML
	public TextArea textAreaId;
	@FXML
	private MenuItem about;
	@FXML
	public Label label;
	@FXML
	public Label nbError;
	@FXML
	public Label Error;
	@FXML
	private HTMLEditor editor;
	@FXML
	private MenuItem BdID;
	@FXML
	private ListView<String> suggestionListe;
	@FXML
	private VBox sugest;
	@FXML
	private ToggleButton lang;
	@FXML
	private Button btn_replace;
	@FXML
	private Button btn_check;
	@FXML
	private Button btn;

	@FXML
	private TabPane tabPane;
	@SuppressWarnings("unused")
	private LinkedList<HTMLEditor> editorsList;
	private ArrayList<String> dictionary;
	private String html;
	private Document document, document1;
	private ArrayList<TextNode> textNodes;
	private ArrayList<String> dict;
	private boolean bUseEnglish;
	private ListView<String> lview_proposals;
	ArrayList<String> text = new ArrayList<String>();
	int INode = 0;// nombre de noeuds
	int iFrom;
	int iTo;

	ArrayList<Integer> liste = new ArrayList<Integer>(); // liste de tous les
															// debuts derreur
	ArrayList<Integer> liste1 = new ArrayList<Integer>();// liste de toutes les
															// fin derreurs
	ArrayList<Integer> nbErrorNode = new ArrayList<Integer>();// nombre derreurs
																// par noeu
	ArrayList<String> AllErrors = new ArrayList<String>(); // nombre derreurs
															// total
	int to;
	int tempI = 0;
	int tos = 0;
	int PosNode = 0;
	String PathSave = new String();

	// javafx.scene.Node webNode = editor.lookup(".web-view");
	WebView webView = new WebView();
	WebEngine engine = webView.getEngine();

	@SuppressWarnings("unused")
	public void initialize() {
		document1 = Jsoup.parse("");
		lang.setOnAction((event) -> {

			if (lang.isSelected())
				lang.setText("Anglais");
			else
				lang.setText("Francais");
		});

		save.setOnAction((event) -> {

			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("RTF files (*.rtf)", "*.rtf");
			fileChooser.getExtensionFilters().add(extFilter);

			Stage stage = new Stage();
			// Show save file dialog
			File file = fileChooser.showSaveDialog(stage);
			String content = new String();

			if (file != null) {
				String test = editor.getHtmlText();
				Document doc = Jsoup.parse(test);
				content = doc.body().text();
				//
				PathSave = file.getAbsolutePath();
				try {
					FileWriter fileWriter = null;

					fileWriter = new FileWriter(file);
					fileWriter.write(content);
					fileWriter.close();
				} catch (IOException ex) {
					Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

				}

			}

		});

		saveAs.setOnAction((event) -> {
			if (PathSave.equals("")) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("RTF files (*.rtf)", "*.rtf");
				fileChooser.getExtensionFilters().add(extFilter);

				Stage stage = new Stage();
				// Show save file dialog
				File file = fileChooser.showSaveDialog(stage);
				String content = new String();

				if (file != null) {
					String test = editor.getHtmlText();
					Document doc = Jsoup.parse(test);
					content = doc.body().text();
					//

					try {
						FileWriter fileWriter = null;

						fileWriter = new FileWriter(file);
						fileWriter.write(content);
						fileWriter.close();
						PathSave = file.getAbsolutePath();
					} catch (IOException ex) {
						Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

					}

				}
			} else {

				String content = new String();
				File file = new File("");

				if (file != null) {
					String test = editor.getHtmlText();
					Document doc = Jsoup.parse(test);
					content = doc.body().text();
					//

					try {

						FileWriter fileWriter = new FileWriter(PathSave);
						fileWriter.write(content);
						fileWriter.close();
						PathSave = file.getAbsolutePath();
					} catch (IOException ex) {
						Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

					}
				}
			}
		});

		Converter.setOnAction((event) -> {

			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);

			Stage stage = new Stage();
			// Show save file dialog
			File file = fileChooser.showSaveDialog(stage);
			String content = new String();

			if (file != null) {
				String test = editor.getHtmlText();
				Document doc = Jsoup.parse(test);
				content = doc.body().text();
				//

				try {
					FileWriter fileWriter = null;

					fileWriter = new FileWriter(file);
					fileWriter.write(content);
					fileWriter.close();
				} catch (IOException ex) {
					Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

				}
			}

		});
		open.setOnAction((event) -> {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(stage);
			String content = null;
			if (file != null) {

				try {

					BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
					StringWriter out = new StringWriter();
					int b;
					while ((b = in.read()) != -1)
						out.write(b);
					out.flush();
					out.close();
					in.close();
					content = out.toString();
					PathSave = null;
				} catch (IOException ex) {
					Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

				}
			}
			editor.setHtmlText(content);

		});

		BdID.setOnAction((event) -> {
			Stage primaryStage = new Stage();
			try {
				Parent root = null;
				if (new File("flat.temp").isFile()) {
					root = FXMLLoader.load(getClass().getResource("MySgbdInterfaceFlat.fxml"));
					Scene scene = new Scene(root, 800, 600);
					scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.setTitle("MySGBD");
				} else {
					root = FXMLLoader.load(getClass().getResource("MySgbdInterface.fxml"));
					Scene scene = new Scene(root, 800, 600);
					primaryStage.setScene(scene);
					primaryStage.setTitle("MySGBD");
				}
				primaryStage.setResizable(false);
				primaryStage.show();
				primaryStage.getIcons().add(new Image("file:sql.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

		Search.setOnAction((event) -> {
			Stage primaryStage = new Stage();
			try {
				Label titre = new Label("RECHERCHER & REMPLACER");
				Label tRech = new Label("Rechercher : ");
				Label tRemp = new Label("Remplacer : ");
				Button btn_search;
				Button btn_RpAll;
				Parent root = null;
				TextField txta = new TextField();
				TextField txtb = new TextField();

				btn_RpAll = new Button("Remplacer");
				btn_search = new Button("Rechercher");

				txta.setFont(Font.font("System", 16));
				txtb.setFont(Font.font("System", 16));

				txtb.setPrefWidth(200);
				txtb.setPrefHeight(20);
				txta.setPrefWidth(200);
				txta.setPrefHeight(20);

				titre.setFont(Font.font("System", 20));
				titre.setStyle("-fx-font-weight:bold");
				tRech.setFont(Font.font("System", 15));
				tRech.setStyle("-fx-font-weight:bold");
				tRemp.setFont(Font.font("System", 15));
				tRemp.setStyle("-fx-font-weight:bold");
				btn_search.setFont(Font.font("System", 16));
				btn_search.setStyle("-fx-font-weight:bold");
				btn_RpAll.setFont(Font.font("System", 16));
				btn_RpAll.setStyle("-fx-font-weight:bold");
				titre.setLayoutX(130);
				titre.setLayoutY(30);
				tRech.setLayoutX(50);
				tRech.setLayoutY(100);
				txta.setLayoutX(150);
				txta.setLayoutY(90);
				btn_search.setLayoutX(355);
				btn_search.setLayoutY(90);
				tRemp.setLayoutX(50);
				tRemp.setLayoutY(180);
				txtb.setLayoutX(150);
				txtb.setLayoutY(170);
				btn_RpAll.setLayoutX(355);
				btn_RpAll.setLayoutY(170);
				Boolean test = false;
				btn_RpAll.setOnAction(e -> btnClick_onReplace(txtb.getText(), txta.getText(), test));
				btn_search.setOnAction(e -> btnClick_onSearch(txta.getText(), test));
				Pane VBox1 = new Pane();
				VBox1.getChildren().addAll(titre, tRech, txta, btn_search, tRemp, txtb, btn_RpAll);
				Scene scene1 = new Scene(VBox1, 500, 250);
				primaryStage.setScene(scene1);
				primaryStage.setResizable(false);
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
		about.setOnAction((event) -> {
			Stage fenetre = (Stage) btn_check.getScene().getWindow();
			Apropos.afficher(fenetre, "#dddddd", "#2fcafa", "#444444");
		});

		Nouveau.setOnAction((event) -> {
			if (!editor.getHtmlText().equals(null)) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Enregistrement");
				alert.setHeaderText("Des modification ont été apportées");
				alert.setContentText("Voulez vous les enregistrer ?");

				ButtonType b1 = new ButtonType("Oui", ButtonData.YES);
				ButtonType b2 = new ButtonType("Non", ButtonData.NO);
				ButtonType b3 = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(b3, b2, b1);

				Optional<ButtonType> result = alert.showAndWait();

				if (result.get() == b1) {
					// ... user chose OK
					FileChooser fileChooser = new FileChooser();

					// Set extension filter
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("RTF files (*.rtf)",
							"*.rtf");
					fileChooser.getExtensionFilters().add(extFilter);

					Stage stage = new Stage();
					// Show save file dialog
					File file = fileChooser.showSaveDialog(stage);
					String content = new String();

					if (file != null) {
						String test = editor.getHtmlText();
						Document doc = Jsoup.parse(test);
						content = doc.body().text();
						//

						try {
							FileWriter fileWriter = null;

							fileWriter = new FileWriter(file);
							fileWriter.write(content);
							fileWriter.close();
						} catch (IOException ex) {
							Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

						}

					}
				} else if (result.get() == b2) {
					// ... user chose CANCEL or closed the dialog
					PathSave = null;
					editor.setHtmlText("");

				} else {

				}
			}
		});

		btn_check.setOnAction((event) -> {
			{
				tos = 0;
				liste.clear();
				liste1.clear();
				tempI = 0;
				suggestionListe.getItems().clear();
				INode = 0;
				PosNode = 0;
				check();
				btn_replace.setDisable(false);

			}

		});

		btn_replace.setOnAction((event) -> {

			String word = suggestionListe.getSelectionModel().getSelectedItem();
			if (word == null) {

			} else { /* remplacer */

				if (nbErrorNode.get(PosNode) > 0 && PosNode < INode) {
					System.out.println("AVEC ERROR 1" + text);
					int i1 = 0;
					String espace = "";

					String temp = text.get(0).toString().substring(this.liste.get(tos), this.liste1.get(tos));
					if (word.length() > temp.length())
						i1 = word.length() - temp.length();
					for (int j = 0; j < i1; j++) {
						espace = espace + " ";
					}

					text.set(0,
							textNodes.get(PosNode).toString().replaceAll(
									textNodes.get(PosNode).toString().substring(this.liste.get(0), this.liste1.get(0)),
									word + espace));

					int i = 0;
					while (i < INode) {
						/* for (int i = 0; i < (INode); i++) */ {
							if (i == PosNode) {
								System.out.println(11);
								document1 = Jsoup.parse(document1 + "<p> " + text.get(0) + " </p>");
							} else {
								System.out.println(22);
								document1 = Jsoup.parse(document1 + "<p> " + textNodes.get(i) + " </p>");
							}
							i = i + 1;
						}
					}

					editor.setHtmlText(document1.body().toString());
					AllErrors.clear();
					text.clear();

					check();
				} else if (nbErrorNode.get(PosNode) == 0 && PosNode < INode) {
					int i = 0;
					while (i < INode) {
						{
							{
								System.out.println(22);
								document1 = Jsoup.parse(document1 + "<p> " + textNodes.get(i) + " </p>");
							}
							i = i + 1;
						}
					}
					// PosNode = PosNode + 1;
					check();
				}

				else if (PosNode >= INode) {
					btn_replace.setDisable(true);
				}
			}
		});

		PrintId.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();

				String test = editor.getHtmlText();
				Document doc = Jsoup.parse(test);
				TextArea content = new TextArea();
				content.setText(doc.body().text());
				pageSetup(content, stage);
			}

		});

		btn.setOnAction((ActionEvent event) -> {
			document1 = Jsoup.parse(editor.getHtmlText());
			FileChooser fc = new FileChooser();
			File myFile = new File("");
			myFile = fc.showOpenDialog(null);
			if (myFile != null) {

				Path pt = FileSystems.getDefault().getPath("F:\\EdiTix\\src\\" + myFile.getName());
				Path pt1 = FileSystems.getDefault().getPath(myFile.getPath());

				try {

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {

				String jsCodeInsertHtml = "function insertHtmlAtCursor(html) {\n" + "    var range, node;\n"
						+ "    if (window.getSelection && window.getSelection().getRangeAt) {\n"
						+ "        range = window.getSelection().getRangeAt(0);\n"
						+ "        node = range.createContextualFragment(html);\n" + "        range.insertNode(node);\n"
						+ "    } else if (document.selection && document.selection.createRange) {\n"
						+ "        document.selection.createRange().pasteHTML(html);\n" + "    }\n"
						+ "}insertHtmlAtCursor('####html####')";
				String filePath = myFile.getPath().replace('\\', '/');
				String img = "<img src=\"file:///" + filePath + "\" width=\"150\" height=\"150\" >";
				document1 = Jsoup.parse(document1 + "" + img);
				editor.setHtmlText(document1.body().toString());

			} catch (Exception e) {

			}

		});
	}

	private Object btnClick_onSearch(String txta, Boolean checking) {
		// TODO Auto-generated method stub
		if (editor.getHtmlText().contains(txta)) {
			checking = true;
			System.out.println(checking);
		} else
			checking = false;
		return null;
	}

	private void btnClick_onReplace(String txtb, String txta, Boolean checking) {
		// TODO Auto-generated method stub
		if (!checking) {

			editor.setHtmlText(editor.getHtmlText().replaceAll(txta, txtb));

		} else
			;
		// return null;
	}

	private void check() {

		dictionary = loadDictionary();
		nbError.setText("Nombre D'erreurs ");
		Error.setText("Erreur ");
		document1 = Jsoup.parse("");
		correcteur(editor.getHtmlText(), dictionary, lang.isSelected());
		if (!(launch())) {

		} else {

			if (nbErrorNode.get(PosNode) == 0) {
				PosNode = PosNode + 1;
			}

			editor.setHtmlText(getHtml());

		}

	}

	private String getHtml() {
		// TODO Auto-generated method stub
		return html;
	}

	private void correcteur(String html1, ArrayList<String> dict1, boolean useEnglish) {

		bUseEnglish = useEnglish;
		dict = dict1;
		html = html1;
		tempI = 0;
		tos = 0;
		liste.clear();
		liste1.clear();
		text.clear();
		INode = 0;
		AllErrors.clear();
		nbErrorNode.clear();
		suggestionListe.getItems().clear();
		// textNodes.clear();

		document = Jsoup.parse(html);
		textNodes = new ArrayList<TextNode>();

		extract(document.body());
	}

	private void extract(Node root) {

		int childrenCount = root.childNodeSize();
		for (int i = 0; i < childrenCount; ++i) {

			Node child = root.childNode(i);

			if (child instanceof TextNode) {
				String content = ((TextNode) child)
						.text(); /* on recupère son conten */
				content = content.trim();

				if (!content.isEmpty()) {
					textNodes.add((TextNode) child);

				}

			} else {
				this.extract(child);
			}
		}

	}

	// Charger le dictionnaire
	private ArrayList<String> loadDictionary() {
		// TODO Auto-generated method stub
		String line = "";
		ArrayList<String> dic = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader("dic.txt"));

			while ((line = reader.readLine()) != null) {
				dic.add(line);

			}

			reader.close();

			return dic;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
	}

	protected boolean launch() {
		// TODO Auto-generated method stub

		JLanguageTool langTool = null;
		if (bUseEnglish)
			langTool = new JLanguageTool(
					new English()); /* utiliser le dico anglais */
		else
			langTool = new JLanguageTool(
					new French()); /* utiliser le dico français */

		for (TextNode node : textNodes) {

			try {
				boolean replaced = true;
				while (replaced) {

					replaced = false;

					List<RuleMatch> errors = langTool
							.check(node.text()); /* Verifier les erreurs */
					nbErrorNode.add(errors.size());
					for (RuleMatch err : errors) {

						List<String> suggested = err.getSuggestedReplacements();
						String word = node.text().substring(err.getFromPos(), err.getToPos());

						if (suggested.isEmpty() && !bUseEnglish)
							suggested = searchSuggested(word, dict);

						Suuggest(node.text(), err.getMessage(), suggested, err.getFromPos(), err.getToPos());

					}
				}
				INode = INode + 1;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

		}

		/* prendre le document ou l'on à fait les corrections */
		html = document.html();
		// html = document.toString();
		return true;
	}

	private void Suuggest(String text1, String details, List<String> suggested, int from, int to) {
		// TODO Auto-generated method stub
		text.add(text1);
		iFrom = from;
		iTo = to;
		lview_proposals = new ListView<String>();
		AllErrors.add(text1.substring(this.iFrom, this.iTo));
		System.out.println("ERREUR " + text1.substring(this.iFrom, this.iTo));
		liste.add(from);
		liste1.add(to);
		tempI++;
		nbError.setText("Nombre D'erreurs " + tempI);

		if (!suggested.isEmpty()) {
			for (String proposal : suggested) {

				lview_proposals.getItems().add(proposal);
				suggestionListe.getItems().add(proposal);
			}
		} else
			suggestionListe.getItems().add("pas de propositions veuillez resaisir SVP!!");

		if (!AllErrors.isEmpty()) {

			Error.setText("L'erreur est " + AllErrors.get(0));
			// System.out.println("rempli avec " + tempI);
		} else {
			Error.setText("Erreur");
			nbError.setText("Nombre D'erreur = 0");
			// System.out.println("vide avec " + tempI);
		}
	}

	private List<String> searchSuggested(String str, ArrayList<String> dictionary) {
		ArrayList<String> result = new ArrayList<String>();
		String textSoundex = correcteur
				.soundex(str); /* code phonétique du mot fourni */
		for (String word : dictionary) {
			String wordSoundex = correcteur.soundex(
					word); /*
							 * code phonétique du mot dans le dictionnaire
							 */

			if (wordSoundex.compareToIgnoreCase(
					textSoundex) == 0) { /*
											 * comparer les deux codes
											 * phonétiques
											 */
				result.add(word); /* proposer le mot */
			}
		}

		return result;

	}

	private void pageSetup(TextArea content, Stage owner) {
		// Create the PrinterJob
		PrinterJob job = PrinterJob.createPrinterJob();

		if (job == null) {
			return;
		}

		// Show the page setup dialog
		boolean proceed = job.showPageSetupDialog(owner);

		if (proceed) {
			print(job, content);
		}
	}

	private void print(PrinterJob job, TextArea content) {
		// Set the Job Status Message
		// jobStatus.textProperty().bind(job.jobStatusProperty().asString());

		// Print the node
		boolean printed = job.printPage(content);

		if (printed) {
			job.endJob();
		}
	}

}
