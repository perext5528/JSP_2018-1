import javafx.scene.control.*;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Test extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();
		root.setPrefSize(350, 150);
		root.setAlignment(Pos.CENTER);
		Button btn = new Button("»Æ¿Œ");
		root.getChildren().add(btn);
		Scene scene = new Scene(root);
		primaryStage.setTitle("asda");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
