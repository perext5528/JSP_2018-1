package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

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

public class SnakeGame_1_1V extends Application {
	// ---------------------------------------
	// 이동 관련
	boolean isMove = false; // 이동 여부
	int LAST_DIRECTION = 0; // 마지막으로 회전한 방향

	static final int UP = 1; // 방향 값 상수로 설정
	static final int DOWN = 2;
	static final int RIGHT = 3;
	static final int LEFT = 4;

	static final int DISTANCE = 25; // 이동 값
	// ---------------------------------------
	int num = 0;

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
				snake[body[i + 1]].setLayoutX(all_x[i]); // i는 앞에방의 위치
				snake[body[i + 1]].setLayoutY(all_y[i]); // 머리는 몸체를 먹고
			}
		}
	}

	// gameover시 재시작 버튼과 취소버튼 이벤트처리
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
		// --------------------------------------------
		// 화면 설정
		BorderPane outside = new BorderPane();
		BorderPane backgroundside = new BorderPane();

		AnchorPane gamePane = new AnchorPane();
		gamePane.setPrefSize(400, 400);

		outside.setCenter(backgroundside);
		backgroundside.setCenter(gamePane);
		// --------------------------------------------
		// 배경 화면 설정
		img = new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProject/src/application/background.png");
		ImageView background = new ImageView(img);
		background.setX(0);
		background.setY(0);
		background.setFitWidth(400);
		background.setFitHeight(400);
		background.setPreserveRatio(true);
		gamePane.getChildren().add(background);
		// 종료창
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
		// --------------------------------------------

		// 머리 설정
		img = new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/downH.png");
		snake[0] = new ImageView(img);
		snake[0].setImage(img);
		snake[0].setPreserveRatio(false);
		snake[0].setFitHeight(25);
		snake[0].setFitWidth(25);
		gamePane.getChildren().add(snake[0]);
		// --------------------------------------------
		// 랜덤으로 아이템 생성
		for (int i = 0; i < x.length; i++) {
			x[i] = Math.floor((Math.random() * 300));
			y[i] = Math.floor((Math.random() * 300));
			if (x[i] % 25 != 0) {
				x[i] = x[i] + 25 - x[i] % 25;
			}
			if (y[i] % 25 != 0) {
				y[i] = y[i] + 25 - y[i] % 25;
			}
		}
		// --------------------------------------------
		// 몸통 설정

		Image snake_body[] = {
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/1.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/2.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/3.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/4.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/5.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/6.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/7.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/8.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/tail.png"),
				new Image("file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/tail.png") };
		//
		// 벡터
		Vector<Image> vector = new Vector<Image>();
		for (int i = 0; i < snake_body.length; i++) {
			vector.add(snake_body[i]);
		}

		for (int i = 1; i < snake.length; i++) { // 꼬리
			snake[i] = new ImageView(snake_body[i - 1]);
			snake[i].setImage(snake_body[i - 1]);
			snake[i].setPreserveRatio(false);
			snake[i].setFitHeight(25);
			snake[i].setFitWidth(25);
			snake[i].setLayoutX(x[i - 1]); // x의 좌표값을 설정
			snake[i].setLayoutY(y[i - 1]); // y의 좌표값을 설정
			// snake[i].setLayoutX(i*25);
			gamePane.getChildren().add(snake[i]);
		}

		Scene scene = new Scene(outside);
		Move move = new Move();

		scene.setOnKeyPressed((event) -> {

			isMove = false;
			// 끝에 붙어있는 몸체의 위치
			double attach_x = snake[body[get_body]].getLayoutX();
			double attach_y = snake[body[get_body]].getLayoutY();

			all_x[0] = snake[0].getLayoutX();
			all_y[0] = snake[0].getLayoutY();

			// 키 이벤트 + 마지막으로 전환한 방향 반대로는 이동할 수 없게
			if (LAST_DIRECTION != LEFT && event.getCode() == KeyCode.RIGHT) {
				if (snake[0].getLayoutX() < 400 - snake[0].getFitWidth()) {
					img = new Image(
							"file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/rigntH.png");
					snake[0].setImage(img);

					snake[0].setLayoutX(snake[0].getLayoutX() + DISTANCE);
					LAST_DIRECTION = RIGHT;
					isMove = true;
				} else {
					GameOverAlert(primaryStage, b1, b2, v);
				}
			} else if (LAST_DIRECTION != RIGHT && event.getCode() == KeyCode.LEFT) {
				if (snake[0].getLayoutX() > 0) {
					snake[0].setImage(new Image(
							"file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/leftH.png"));
					snake[0].setLayoutX(snake[0].getLayoutX() - DISTANCE);
					LAST_DIRECTION = LEFT;
					isMove = true;
				} else {
					GameOverAlert(primaryStage, b1, b2, v);
				}
			} else if (LAST_DIRECTION != DOWN && event.getCode() == KeyCode.UP) {
				if (snake[0].getLayoutY() > 0) {
					img = new Image(
							"file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/upH.png");
					snake[0].setImage(img);

					snake[0].setLayoutY(snake[0].getLayoutY() - DISTANCE);
					LAST_DIRECTION = UP;
					isMove = true;
				}
				// 만약 뱀이 판 위를 벗어나면
				else {
					GameOverAlert(primaryStage, b1, b2, v);
				}
			} else if (LAST_DIRECTION != UP && event.getCode() == KeyCode.DOWN) {
				if (snake[0].getLayoutY() < 400 - snake[0].getFitHeight()) {
					img = new Image(
							"file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/downH.png");
					snake[0].setImage(img);

					snake[0].setLayoutY(snake[0].getLayoutY() + DISTANCE);
					LAST_DIRECTION = DOWN;
					isMove = true;
				} else {
					GameOverAlert(primaryStage, b1, b2, v);
				}
			}
			// 움직임이 참 값인 경우만 이동되게 만들기
			if (isMove == true) {
				for (int i = 1; i <= get_body; i++) {
					all_x[i] = snake[body[i]].getLayoutX();
					all_y[i] = snake[body[i]].getLayoutY();
				}
			}
			// 머리가 꼬리에 부딪힌 경우
			for (int i = 1; i <= get_body; i++) {
				if (all_x[i] == snake[0].getLayoutX() && all_y[i] == snake[0].getLayoutY())
					GameOverAlert(primaryStage, b1, b2, v);
			}

			for (int i = 1; i < snake.length; i++) // 몇번방의 몸통을 만났는지 알아보기 위해 포문 돌리기
			{
				boolean done = true;
				if (snake[0].getLayoutX() == snake[i].getLayoutX() && // 머리가 몸통을 만나게 되는경우(겹쳐지면서 머리에 몸통이 붙는다),,
				snake[0].getLayoutY() == snake[i].getLayoutY())
				// num == snake[i].
				{
					while (done) {
						if (snake[i].getImage() == vector.get(num)) {
							get_body++; // 처음에 0으로 초기치를 주고(머리는 0, 몸통은 1부터 따라서 붙을때마다 숫자가 1씩증가)
							body[get_body] = i; // [몸통 get_body번째 방]에는 몇번방의 몸통인지 대입한다. ( (get)body는 1부터 순차적으로 증가 ) 몸통1에
												// 3(스네이크할때의 3)이 넣어짐
							snake[i].setLayoutX(attach_x); // 스네이크 3에는
							snake[i].setLayoutY(attach_y);
							num++;
							snake[i].setImage(new Image(
									"file:///C:/Users/dd/Desktop/Code/Java/JavaClinicProjectLab1/src/application/body.png"));
						} else {
							done = false;
						}
					}
				}
			}
			move.Combind(); // 연결
		});

		h.getChildren().addAll(b1, b2);
		v.getChildren().addAll(la, h);

		// -----------------------------------------------------------------------------------
		// Menu UI start
		MenuBar mb = new MenuBar();
		Menu game = new Menu("_Game");
		Menu score = new Menu("_Score");

		// 툴바 설정, 추가 보류중
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
				// 파일 객체 생성
				File file = new File(
						"C:\\Users\\dd\\Desktop\\Code\\Java\\JavaClinicProjectLab2\\src\\application\\Score.txt");
				// 입력 스트림 생성
				FileReader filereader = new FileReader(file);
				// 입력 버퍼 생성
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
		// 파일 선택 후 읽어들이고, 읽은 텍스트 파일을 알림창에 표시되도록 만들기

		// 임의의 경로에 해당 게임의 점수를 저장하도록 만들기
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
		primaryStage.setResizable(false); // 창 사이즈 변동 x
	}

	public static void main(String[] args) {
		launch(args);
	}

}