package application;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = new VBox();

			root.setPadding(new Insets(10));
			root.getChildren().add(new Label("TextArea1"));
			TextArea ta1 = new TextArea();
			root.getChildren().add(ta1);

			root.getChildren().add(new Label("TextArea2"));
			TextArea ta2 = new TextArea();
			root.getChildren().add(ta2);

			Bindings.bindBidirectional(ta1.textProperty(), ta2.textProperty());
			// ta1.textProperty().bindBidirectional(ta2.textProperty());

			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
