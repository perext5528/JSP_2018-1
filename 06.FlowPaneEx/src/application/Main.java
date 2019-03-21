package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FlowPane root = new FlowPane();
			root.setPadding(new Insets(10));
			root.setPrefSize(250, 100);
			root.setHgap(30);
			root.setVgap(20);
			
			root.getChildren().add(new Button("Add"));
			root.getChildren().add(new Button("Subtract"));
			root.getChildren().add(new Button("Multifly"));
			root.getChildren().add(new Button("Divide"));
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
