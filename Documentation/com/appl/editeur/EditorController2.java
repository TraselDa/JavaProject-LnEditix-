package com.appl.editeur;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;



public class EditorController2 {

    @FXML
   private HTMLEditor htmledit;
    @FXML
    private TabPane tabPane;
    @FXML
	private Button replace ;
    private ArrayList<String> dictionnaire;
    private String htmlstring;
    
	private org.jsoup.nodes.Document docu;
	org.jsoup.nodes.Document docu1;
    private ArrayList<TextNode> noeudsT;
    private ArrayList<String> dic;
   
	@FXML
    private ListView<String> suggestions;
    

    private ListView<String> lview_proposals;
    ArrayList<String> text = new ArrayList<String>();
    int INode = 0;// nombre de noeuds
    int iFrom;
    int iTo;

    ArrayList<Integer> list = new ArrayList<Integer>(); // liste de tous les// debuts derreur
    ArrayList<Integer> list1 = new ArrayList<Integer>();// liste de toutes les// fin derreurs
    ArrayList<Integer> nbErrorNode = new ArrayList<Integer>();// nombre derreurs// par noeud
    ArrayList<String> AllErrors = new ArrayList<String>(); // nombre derreurs
    // total
    int to;
    int tempI = 0;
    int tos = 0;
    int PosNode = 0;
    ImageView myImageView ;
    

    @FXML
    private void onNouveau()throws IOException{
    	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ui2.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle("Nouveau document - SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    } catch(Exception e){
    	System.out.println("Impossible d'ouvrir nouveau document");
    }}

       



    @FXML
    private void onOuvrir() {
    	  FileChooser fileChooser = new FileChooser();
          
          //Set extension filter
    		 FileChooser.ExtensionFilter extFiltertTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    		 FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
             fileChooser.getExtensionFilters().addAll(extFiltertTXT, extFilterHTML);
           
          //Show open file dialog
          File file = fileChooser.showOpenDialog(null);
          if(file != null){
              String textRead = readFile(file);
              htmledit.setHtmlText(textRead);
              
          }
      }
   
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
    

    
   
    
    @FXML
    private void onEnregistrerSous() {
    	String stringHtml = htmledit.getHtmlText();
    	htmledit.setHtmlText(stringHtml);
         
        FileChooser fileChooser = new FileChooser();
         
        //Set extension filter
   	 FileChooser.ExtensionFilter extFiltertTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	 FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
     fileChooser.getExtensionFilters().addAll(extFiltertTXT, extFilterHTML);
         
        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        if(file != null){
            Enregistrer(stringHtml, file);
        }
        }
    
    	
    
    private void Enregistrer(String content ,File file) {
		
    	
            try {
                FileWriter fileWriter = null;
                 
                fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
            }
     
              
        }
	

	@FXML
    private void onEnregistrer()  {
		String stringHtml = htmledit.getHtmlText();
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			 FileChooser.ExtensionFilter extFiltertTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			 FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
	         fileChooser.getExtensionFilters().addAll(extFiltertTXT, extFilterHTML);
            try {
                // if file doesn't exists, then create it
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(stringHtml);
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // open a file dialog box
            file = fileChooser.showSaveDialog(null);
       	 FileChooser.ExtensionFilter extFiltertTXT = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		 FileChooser.ExtensionFilter extFilterHTML = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
         fileChooser.getExtensionFilters().addAll(extFiltertTXT, extFilterHTML);

            if (file != null) {
                Stage stage = (Stage) htmledit.getScene().getWindow();
                stage.setTitle(file.getName() + " - Swiftpad");
                try {
                    // if file doesn't exists, then create it
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(stringHtml);
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
	

    	
    	
    	
        
	

	@FXML
    private void onQuitter() {
        System.exit(0);
    }

    @FXML
    private void onAide() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Aide");
        alert.setContentText("");
        alert.show();
    }

@FXML
private void onImage() {
FileChooser openfileChooser = new FileChooser();
	
	myImageView = new ImageView(); 
	
    //Set extension filter
    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
    openfileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
      
    //Show open file dialog
    File file = openfileChooser.showOpenDialog(null);
    if ( file != null){
             try {
        	BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        
            myImageView.setImage(image);
            ImageIO.write(bufferedImage, "jpg", file);
            

         
            htmledit.setHtmlText("<img src=' " + file.toURI() +  "'/>");
           
        } catch (IOException ex) {
            Logger.getLogger(EditorController.class.getName()).log(Level.SEVERE, null, ex);
        }
			
		      }
      }
@FXML


public void nouveau() throws IOException{
	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pad.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle(" SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    	} catch(Exception e){
    		System.out.println("Impossible d'ouvrir nouveau document");
    	}
    }


@FXML
public void SqlEditor()throws IOException{
	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SqlEditor.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle("Base de données - SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    	} catch(Exception e){
    		System.out.println("");
    	}
    }
	

@FXML
public void fermerfenetre(){
	System.exit(0);
}
@FXML
public void valid2(){
	try{
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fulldatabase.fxml"));
    	Parent root1 = (Parent) fxmlLoader.load();
    	Stage stage = new Stage();
    	stage.setTitle(" SwiftPad");
    	stage.setScene(new Scene(root1));
    	stage.show();
    	} catch(Exception e){
    		System.out.println("");
    	}
    }

@FXML
public void retoursurPAD(){

    }
	

@FXML
public void valid1(){
}

public void check() {

    dictionnaire= loadDictionary(htmlstring);

   
    docu1 =  Jsoup.parse("");
    correcteur(htmledit.getHtmlText(), dictionnaire, false);
    System.out.println(dictionnaire);
    
    launch();
    if (nbErrorNode.get(PosNode) == 0) {
        PosNode = PosNode + 1;
    }

    htmledit.setHtmlText(getHtml());

 if (!(launch())) {

    } else {

        if (nbErrorNode.get(PosNode) == 0) {
            PosNode = PosNode + 1;
        }

        htmledit.setHtmlText(getHtml());

    }

}
private String getHtml() {
    // TODO Auto-generated method stub
    return htmlstring;
}
private void correcteur(String html1, ArrayList<String> dict1, boolean b) {

    
    htmlstring = html1;
    tempI = 0;
    tos = 0;
    list.clear();
    list1.clear();
    text.clear();
    INode = 0;
    AllErrors.clear();
    nbErrorNode.clear();
    suggestions.getItems().clear();
    // textNodes.clear();

    docu =  Jsoup.parse(htmlstring);
    noeudsT = new ArrayList<TextNode>();

    extract(((org.jsoup.nodes.Document) docu).body());
}
private void extract( org.jsoup.nodes.Node child2 )
{
	 int childrenCount = child2.childNodeSize();
     for( int i=0;i<childrenCount;++i ) { /* parcourir tout les enfants */

         org.jsoup.nodes.Node child = child2.childNode(i);

         if( child instanceof TextNode ) { /* si on tombe sur un noeud texte */

             String content = ((TextNode)child).text(); /* on recupère son conten */
             content = content.trim();

            if( !content.isEmpty() ) { /* et s'il n'est pas que blanc */
//				System.out.println("#Text : " + content );
                noeudsT.add((TextNode) child); /* l'ajouter */
            }

        }else {
            this.extract(child);
        }
    }
}

// Charger le dictionnaire
private ArrayList<String> loadDictionary(String htmlstring) {
    // TODO Auto-generated method stub
    String line = "";
    ArrayList<String> dic = new ArrayList<String>();

    try {

        BufferedReader reader = new BufferedReader(new FileReader("diction.txt"));

         
    



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
public boolean launch() {
    // TODO Auto-generated method stub

    JLanguageTool langTool = null;
    
        langTool = new JLanguageTool(new BritishEnglish()); /* utiliser le dico anglais */
    
   /* else
        langTool = new JLanguageTool(new French()); /* utiliser le dico français */
    for (TextNode node : noeudsT) {

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

                    if (suggested.isEmpty() )
                        suggested = searchSuggested(word, dic);
                    
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
    htmlstring = ((Element) docu).html();
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
    list.add(from);
    list1.add(to);
    tempI++;

    if (!suggested.isEmpty()) {
        for (String proposal : suggested) {

            lview_proposals.getItems().add(proposal);
            suggestions.getItems().add(proposal);
        }
    } else
        suggestions.getItems().add("pas de propositions veuillez resaisir SVP!!");
    }


private List<String> searchSuggested(String str, ArrayList<String> dict) {
    ArrayList<String> result = new ArrayList<String>();
    String textSoundex = pourcorrecteur.soundex(str); /* code phonétique du mot fourni */
    for (String word : dict) {
        String wordSoundex = pourcorrecteur.soundex(word); /*
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

public void replace(){
	
	String word = lview_proposals.getSelectionModel().getSelectedItem();
	 /* remplacer */

		if (nbErrorNode.get(PosNode) > 0 && PosNode < INode) {
			System.out.println("AVEC ERROR 1" + text);
			int i1 = 0;
			String espace = "";

			String temp = text.get(0).toString().substring(this.list.get(tos), this.list1.get(tos));
			if (word.length() > temp.length())
				i1 = word.length() - temp.length();
			for (int j = 0; j < i1; j++) {
				espace = espace + " ";
			}

			text.set(0,
					noeudsT.get(PosNode).toString().replaceAll(
							noeudsT.get(PosNode).toString().substring(this.list.get(0), this.list1.get(0)),
							word + espace));

			int i = 0;
			while (i < INode) {
				/* for (int i = 0; i < (INode); i++) */ {
					if (i == PosNode) {
						System.out.println(11);
						docu1 = Jsoup.parse(docu1 + "<p> " + text.get(0) + " </p>");
					} else {
						System.out.println(22);
						docu1 = Jsoup.parse(docu1 + "<p> " + noeudsT.get(i) + " </p>");
					}
					i = i + 1;
				}
			}

			htmledit.setHtmlText(docu1.body().toString());
			AllErrors.clear();
			text.clear();

			check();
		} else if (nbErrorNode.get(PosNode) == 0 && PosNode < INode) {
			int i = 0;
			while (i < INode) {
				{
					{
						System.out.println(22);
						docu1 = Jsoup.parse(docu1 + "<p> " + noeudsT.get(i) + " </p>");
					}
					i = i + 1;
				}
			}
			// PosNode = PosNode + 1;
			check();
		}

		else if (PosNode >= INode) {
			replace.setDisable(true);
		}
	}
	
}
	
	

