package application;

public class Suuggest {

	/*
	 * private Stage stage;
	 * 
	 * private TextArea txta_errorText; private TextArea txta_errorDetails;
	 * private ListView<String> lview_proposals; private Button btn_ignore; /*
	 * Bouton ignorer private Button btn_cancel; private String text; private
	 * boolean replace; private boolean stop; private int iFrom; private int
	 * iTo; // Controller test = new Controller();
	 * 
	 * public Suuggest(String text, String details, List<String> suggested, int
	 * from, int to) { stage = new Stage();
	 * stage.setTitle("Correcteur orthographique");
	 * stage.initModality(Modality.APPLICATION_MODAL); ContextMenu contextMenu =
	 * new ContextMenu(); ArrayList<MenuItem> item = new ArrayList(); this.text
	 * = text; this.iFrom = from; this.iTo = to;
	 * 
	 * 
	 * btn_replace = new Button("Remplacer le mot"); btn_replace.setOnAction(e
	 * -> btnClick_onReplace());
	 * 
	 * btn_ignore = new Button("Ignorer"); btn_ignore.setOnAction(e ->
	 * btnClick_onIgnore());
	 * 
	 * btn_cancel = new Button("Annuler la correction");
	 * btn_cancel.setOnAction(e -> btnClick_onStop());
	 * 
	 * txta_errorText = new TextArea(); txta_errorText.setText(text);
	 * txta_errorText.selectRange(this.iFrom, this.iTo);
	 * 
	 * txta_errorDetails = new TextArea(details);
	 * 
	 * lview_proposals = new ListView<String>();
	 * 
	 * for (String proposal : suggested) {
	 * 
	 * lview_proposals.getItems().add(proposal); MenuItem temp = new
	 * MenuItem(proposal); // test.suggestion(proposal); item.add(temp);
	 * 
	 * } for (MenuItem i : item) { System.out.println(i.getText()); }
	 * 
	 * 
	 * VBox buttonsVBox = new VBox(20); buttonsVBox.getChildren().addAll(new
	 * Label("Options:"), btn_replace, btn_ignore);
	 * 
	 * VBox textVBox = new VBox(7); textVBox.getChildren().addAll(new
	 * Label("Texte avec erreurs : "), txta_errorText, new Label("Details : "),
	 * txta_errorDetails, new Label("Propositions:"), lview_proposals,
	 * btn_cancel);
	 * 
	 * HBox mainHBox = new HBox(10); mainHBox.getChildren().addAll(textVBox,
	 * buttonsVBox);
	 * 
	 * /* Cration de la scene Scene scene = new Scene(mainHBox, 650, 450);
	 * stage.setScene(scene); stage.setResizable(false);
	 * 
	 * }
	 * 
	 * public String retour() { return lview_proposals.toString(); }
	 * 
	 * public void btnClick_onReplace() { String word =
	 * lview_proposals.getSelectionModel().getSelectedItem(); if (word == null)
	 * { Alert alert = new Alert(AlertType.WARNING, "Choisissez un mot",
	 * ButtonType.OK); alert.showAndWait();
	 * 
	 * } else { /* remplacer
	 * 
	 * text = text.replace(text.substring(this.iFrom, this.iTo), word);
	 * stage.close(); /* fermer la fenetre this.replace = true; } }
	 * 
	 * public void btnClick_onIgnore() { stage.close(); }
	 * 
	 * public void btnClick_onStop() { this.stop = true; stage.close(); }
	 * 
	 * public void show() { stage.showAndWait(); }
	 * 
	 * public boolean doReplace() { return this.replace; }
	 * 
	 * public boolean doStop() { return this.stop; }
	 * 
	 * public String getText() { return this.text; }
	 */
}
