package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class RootController implements Initializable {
	@FXML
	private Button btn;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		btn.setOnAction(event->HandleBtnAction(event));

	}
	public void HandleBtnAction(ActionEvent event) {
		Platform.exit();
	}
}
