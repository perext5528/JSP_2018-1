package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class ChatClient extends Application {

	Socket socket;
	TextArea txtDisplay;

	public void startClient(String ipAddress, int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress(ipAddress, port));
					receive();
				} catch (Exception e) {
					if (!socket.isClosed())
						stopClient();
					System.out.println("[Server connection failure!!]");
					Platform.exit();
				}
			}
		};
		thread.start();
	}

	public void stopClient() {
		try {
			if (socket != null && !socket.isClosed())
				socket.close();

		} catch (Exception e) {

		}
	}

	public void receive() {
		while (true) {
			try {
				InputStream in = socket.getInputStream();
				byte[] buf = new byte[100];
				int len = in.read(buf);
				if (len == -1)
					throw new IOException();

				String msg = new String(buf, 0, len, "UTF-8");

				Platform.runLater(() -> {
					txtDisplay.appendText(msg);
				});
			} catch (Exception e) {
				stopClient();
				break;
			}
		}
	}

	public void send(String msg) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buf = msg.getBytes("UTF-8");
					out.write(buf);
					out.flush();
				} catch (Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(5));

		HBox hbox = new HBox();
		hbox.setSpacing(5);

		TextField txtName = new TextField();
		txtName.setPrefWidth(150);
		txtName.setPromptText("Input your nickname");

		TextField txtIP = new TextField("localhost");
		HBox.setHgrow(txtIP, Priority.ALWAYS);

		TextField txtPort = new TextField("4000");
		txtPort.setPrefWidth(80);

		hbox.getChildren().addAll(txtName, txtIP, txtPort);
		root.setTop(hbox);

		txtDisplay = new TextArea();
		txtDisplay.setEditable(false);
		root.setCenter(txtDisplay);

		BorderPane pane = new BorderPane();

		TextField txtInput = new TextField();
		txtInput.setPrefWidth(Double.MAX_VALUE);
		txtInput.setDisable(true);

		txtInput.setOnAction(event -> {
			send(txtName.getText() + " > " + txtInput.getText() + "\n");
			txtInput.setText("");
			txtInput.requestFocus();
		});

		Button btnSend = new Button("Send");
		btnSend.setDisable(true);

		btnSend.setOnAction(event -> {
			send(txtName.getText() + " > " + txtInput.getText() + "\n");
			txtInput.setText("");
			txtInput.requestFocus();
		});

		Button btnConn = new Button("Start");
		btnConn.setOnAction(event -> {
			if (btnConn.getText().equals("Start")) {
				int port = 4000;
				try {
					port = Integer.parseInt(txtPort.getText());
				} catch (Exception e) {
				}
				startClient(txtIP.getText(), port);
				Platform.runLater(() -> {
					txtDisplay.appendText("Chatting Started...\n");
				});
				btnConn.setText("Stop");
				txtInput.setDisable(false);
				btnSend.setDisable(false);
				txtInput.requestFocus();
			} else {
				stopClient();
				Platform.runLater(() -> {
					txtDisplay.appendText("Chatting Stopped...\n");
				});
				btnConn.setText("Start");
				txtInput.setDisable(true);
				btnSend.setDisable(true);
			}
		});
		pane.setLeft(btnConn);
		pane.setCenter(txtInput);
		pane.setRight(btnSend);

		root.setBottom(pane);

		Scene scene = new Scene(root, 500, 500);
		primaryStage.setTitle("Chat Client");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> stopClient());
		primaryStage.show();
		btnConn.requestFocus();

	}

	public static void main(String[] args) {
		launch(args);
	}

}