package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Printing extends Application {
	// Create the JobStatus Label
	private final Label jobStatus = new Label();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) {
		// Create the Text Label

		// Create the TextArea
		final TextArea textArea = new TextArea();

		// Create the Buttons
		Button printSetupButton = new Button("Print Setup and Print");

		// Create the Event-Handlers for the Button
		printSetupButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				printSetup(textArea, stage);
			}
		});

		// Create the Status Box

	}

	private void printSetup(Node node, Stage owner) {
		// Create the PrinterJob
		PrinterJob job = PrinterJob.createPrinterJob();

		if (job == null) {
			return;
		}

		// Show the print setup dialog
		boolean proceed = job.showPrintDialog(owner);

		if (proceed) {
			print(job, node);
		}
	}

	private void print(PrinterJob job, Node node) {
		// Set the Job Status Message
		jobStatus.textProperty().bind(job.jobStatusProperty().asString());

		// Print the node
		boolean printed = job.printPage(node);

		if (printed) {
			job.endJob();
		}
	}
}
