package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			root.setPrefSize(400, 400);
			root.setPadding(new Insets(10, 30, 10, 30));
			
			Button b1 = new Button("Center");
			b1.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			BorderPane.setMargin(b1, new Insets(0, 10, 0, 10));
			root.setCenter(b1);

			Button b2 = new Button("Top");
			b2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			BorderPane.setMargin(b2, new Insets(0, 0, 10, 0));
			root.setTop(b2);
			
			Button b3 = new Button("Right");
			b3.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			root.setRight(b3);
			
			Button b4 = new Button("Bottom");
			BorderPane.setMargin(b4, new Insets(10, 0, 0, 0));
			BorderPane.setAlignment(b4, Pos.CENTER);
			root.setBottom(b4);
			
			Button b5 = new Button("Left");
			b5.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			root.setLeft(b5);
			
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
