package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = new AnchorPane();
			root.setPrefSize(300, 200);
			
			Label label = new Label("Hello");
			label.setLayoutX(50);
			label.setLayoutY(30);
			root.getChildren().add(label);
			for(int i=0;i<6;i++) {
				Button btn = new Button(Integer.toString(i));
				btn.setLayoutX(100);
				btn.setLayoutY(i*25);
				root.getChildren().add(btn);
			}
			
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("AnchorPaneEx");
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
