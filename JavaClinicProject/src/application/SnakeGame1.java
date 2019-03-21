package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SnakeGame1 extends Application {
	// ---------------------------------------
	// �̵� ����
	boolean isMove = false; // �̵� ����
	int LAST_DIRECTION = 0; // ���������� ȸ���� ����

	static final int UP = 1; // ���� �� ����� ����
	static final int DOWN = 2;
	static final int RIGHT = 3;
	static final int LEFT = 4;

	static final int DISTANCE = 25; // �̵� ��
	// ---------------------------------------
	Image img;
	ImageView[] snake = new ImageView[10];

	double[] x = new double[9];
	double[] y = new double[9];

	double[] all_x = new double[10];
	double[] all_y = new double[10];

	int[] body = new int[10];
	int get_body = 0;

	class Move {
		public Move() {
		}

		public void Combind() {
			for (int i = 0; i < get_body; i++) {
				snake[body[i + 1]].setLayoutX(all_x[i]); // i�� �տ����� ��ġ
				snake[body[i + 1]].setLayoutY(all_y[i]); // �Ӹ��� ��ü�� �԰�
			}
		}
	}

	// gameover�� ����� ��ư�� ��ҹ�ư �̺�Ʈó��
	void GameOverAlert(Stage primaryStage, Button b1, Button b2, VBox v) {
		Scene scene1 = new Scene(v);
		primaryStage.setScene(scene1);
		b1.setOnAction(event -> {
			try {
				new SnakeGame_1_1V().start(new Stage());
				primaryStage.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		b2.setOnAction(event -> System.exit(0));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane outside = new BorderPane();
		BorderPane backgroundside = new BorderPane();
		
		AnchorPane gamePane = new AnchorPane();
		gamePane.setPrefSize(400, 400);
		
		outside.setCenter(backgroundside);
		backgroundside.setCenter(gamePane);
		
		// background image
		img = new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProject/src/application/background.png");
		ImageView background = new ImageView(img);
		background.setX(0);
		background.setY(0);
		background.setFitWidth(400);
		background.setFitHeight(400);
		background.setPreserveRatio(true);
		gamePane.getChildren().add(background);

		// alert���� �ʰ� ����â ����
		VBox v = new VBox();
		v.setPrefSize(300, 150);
		v.setPadding(new Insets(10));
		v.setAlignment(Pos.CENTER);
		v.setSpacing(30);

		Label la = new Label();
		la.setText("GAME OVER");
		la.setFont(new Font("Arial", 30));
		la.setPrefSize(300, 20);
		la.setAlignment(Pos.CENTER);

		HBox h = new HBox();
		h.setSpacing(20);
		h.setAlignment(Pos.BOTTOM_CENTER);
		Button b1 = new Button("Restart");
		b1.setPrefSize(100, 20);
		Button b2 = new Button("Exit");
		b2.setPrefSize(100, 20);

		// snake head
		img = new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProject/src/application/head.png");
		snake[0] = new ImageView(img);
		snake[0].setImage(img);
		snake[0].setPreserveRatio(false); //
		snake[0].setFitHeight(25);
		snake[0].setFitWidth(25);
		gamePane.getChildren().add(snake[0]); // pane�� �� head�� ����

		for (int i = 0; i < x.length; i++) // �׸�ĭ �������� �ѷ��� �� �׸�ĭ 25��ŭ���� ���߾ �Ѹ���
		{
			x[i] = Math.floor((Math.random() * 300)); //
			y[i] = Math.floor((Math.random() * 300)); // �ݿø�. ���ڸ� �� �������� ����
			if (x[i] % 25 != 0) // 25�� �ƴ� ���, 25�� ����� �����ֱ�
			{
				x[i] = x[i] + 25 - x[i] % 25;
			}
			if (y[i] % 25 != 0) {
				y[i] = y[i] + 25 - y[i] % 25;
			}
		}

		img = new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProject/src/application/tail.png"); // ����
		for (int i = 1; i < snake.length; i++) {
			snake[i] = new ImageView(img);
			snake[i].setImage(img);
			snake[i].setPreserveRatio(false);
			snake[i].setFitHeight(25);
			snake[i].setFitWidth(25);
			snake[i].setLayoutX(x[i - 1]); // x�� ��ǥ���� ����
			snake[i].setLayoutY(y[i - 1]); // y�� ��ǥ���� ����
			// snake[i].setLayoutX(i*25);
			gamePane.getChildren().add(snake[i]);
		}

		Scene scene = new Scene(outside);
		Move move = new Move();
		
		scene.setOnKeyPressed((event) -> {
			
			isMove = false;
			// ���� �پ��ִ� ��ü�� ��ġ
			double attach_x = snake[body[get_body]].getLayoutX();
			double attach_y = snake[body[get_body]].getLayoutY();

			all_x[0] = snake[0].getLayoutX();
			all_y[0] = snake[0].getLayoutY();

			// Ű �̺�Ʈ + ���������� ��ȯ�� ���� �ݴ�δ� �̵��� �� ����
			if (LAST_DIRECTION != LEFT && event.getCode() == KeyCode.RIGHT) {
				if (snake[0].getLayoutX() < 400 - snake[0].getFitWidth()) {
					snake[0].setLayoutX(snake[0].getLayoutX() + DISTANCE);
					LAST_DIRECTION = RIGHT;
					isMove = true;
				} else {
					GameOverAlert(primaryStage, b1, b2, v);
				}
			} else if (LAST_DIRECTION != RIGHT && event.getCode() == KeyCode.LEFT) {
				if (snake[0].getLayoutX() > 0) {
					snake[0].setLayoutX(snake[0].getLayoutX() - DISTANCE);
					LAST_DIRECTION = LEFT;
					isMove = true;
				} else {
					GameOverAlert(primaryStage, b1, b2, v);
				}
			} else if (LAST_DIRECTION != DOWN && event.getCode() == KeyCode.UP) {
				if (snake[0].getLayoutY() > 0) {
					snake[0].setLayoutY(snake[0].getLayoutY() - DISTANCE);
					LAST_DIRECTION = UP;
					isMove = true;
				}
				// ���� ���� �� ���� �����
				else {
					GameOverAlert(primaryStage, b1, b2, v);
				}
			} else if (LAST_DIRECTION != UP && event.getCode() == KeyCode.DOWN) {
				if (snake[0].getLayoutY() < 400 - snake[0].getFitHeight()) {
					snake[0].setLayoutY(snake[0].getLayoutY() + DISTANCE);
					LAST_DIRECTION = DOWN;
					isMove = true;
				} else {
					GameOverAlert(primaryStage, b1, b2, v);

				}
			}
			// �������� �� ���� ��츸 �̵��ǰ� �����
			if (isMove == true) {
				for (int i = 1; i <= get_body; i++) {
					all_x[i] = snake[body[i]].getLayoutX();
					all_y[i] = snake[body[i]].getLayoutY();
				}
			}

			for (int i = 1; i < snake.length; i++) {
				if (snake[0].getLayoutX() == snake[i].getLayoutX() && snake[0].getLayoutY() == snake[i].getLayoutY()) {
					get_body++;
					body[get_body] = i;
					snake[i].setLayoutX(attach_x); // ��ü�� ��ġ ��ǥ�� �迭 �ȿ� �־���
					snake[i].setLayoutY(attach_y);
				}
			}
			// �Ӹ��� ������ �ε��� ���
			for (int i = 1; i <= get_body; i++) {
				if (all_x[i] == snake[0].getLayoutX() && all_y[i] == snake[0].getLayoutY())
					GameOverAlert(primaryStage, b1, b2, v);
			}
			move.Combind(); // ����
		});

		h.getChildren().addAll(b1, b2);
		v.getChildren().addAll(la, h);

		// -----------------------------------------------------------------------------------
		// Menu UI start
		MenuBar mb = new MenuBar();
		Menu game = new Menu("_Game");
		Menu score = new Menu("_Score");

		// ���� ����, �߰� ������
		ToolBar tb = new ToolBar();
		tb.getItems().addAll(new Button("Pause"), new Button("Play"));
		tb.setLayoutX(mb.getLayoutX() + 170);
		tb.setPrefHeight(10);

		// Record // txtFile Chooser

		FileChooser txtChooser = new FileChooser();
		txtChooser.setTitle("Open Image File");
		txtChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt File", "*.txt"),
				new FileChooser.ExtensionFilter("All Files", "*.*"));

		MenuItem help = new MenuItem("_Help");
		MenuItem restart = new MenuItem("_Restart");
		restart.setAccelerator(KeyCombination.keyCombination("SHORTCUT+R"));
		MenuItem recordOpen = new MenuItem("_Record_Open");
		recordOpen.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));
		MenuItem scoreSave = new MenuItem("_Score_Save");
		scoreSave.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		MenuItem exitItem = new MenuItem("_Exit");
		exitItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+E"));

		recordOpen.setOnAction(event -> {
			try {
				// ���� ��ü ����
				File file = new File("C:\\Users\\dd\\Desktop\\Code\\Java\\JavaClinicProjectLab2\\src\\application\\Score.txt");
				// �Է� ��Ʈ�� ����
				FileReader filereader = new FileReader(file);
				// �Է� ���� ����
				BufferedReader bufReader = new BufferedReader(filereader);
				String line = "";
				while ((line = bufReader.readLine()) != null) {
					System.out.println(line);
				}
				bufReader.close();
			} catch (FileNotFoundException e) {
				// TODO: handle exception
			} catch (IOException e) {
				System.out.println(e);
			}
		});
		// ���� ���� �� �о���̰�, ���� �ؽ�Ʈ ������ �˸�â�� ǥ�õǵ��� �����

		// ������ ��ο� �ش� ������ ������ �����ϵ��� �����
		scoreSave.setOnAction(event -> {
			
			System.out.println("Saved!");
		});
		exitItem.setOnAction(event -> System.exit(0));
		restart.setOnAction(event -> {
			try {
				new SnakeGame_1_1V().start(new Stage());
				primaryStage.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		game.getItems().addAll(help, new SeparatorMenuItem(), restart, new SeparatorMenuItem(), exitItem);
		score.getItems().addAll(recordOpen, scoreSave);
		mb.getMenus().addAll(game, score);
		// Menu UI end
		// -----------------------------------------------------------------------------------

		outside.setTop(mb);
		gamePane.requestFocus();
		primaryStage.setScene(scene);
		primaryStage.setTitle("SnakeGame");

		primaryStage.show();
		primaryStage.setResizable(false); // â ������ ���� x
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}