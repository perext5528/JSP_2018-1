package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Optional;
import java.util.Vector;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

class stopWatch extends Thread
{
   Label label;
   int sec;
   
   public stopWatch(Label label, int sec)
   {
      this.label = label;
      this.sec = sec;
   }
   
   public void run() {
       sec = 0;
      while(true) 
      {
         Platform.runLater(() -> {
            label.setText(Integer.toString(sec));
            sec++;
         });
         
         try {
            sleep(1000);
         }
         catch(Exception e) { return; }
      }
   }
   
}

public class SnakeGame extends Application {
   // ---------------------------------------
   // 이동 관련
   boolean isMove = false; // 이동 여부
   int LAST_DIRECTION = 0; // 마지막으로 회전한 방향
   
   String ipAddress;
   static final int port = 4000;
   Socket client = null;
   BufferedReader read;
   PrintWriter pw;
   BufferedReader br;
   String sendData;
   String receiveData;
   
   TextArea ta;
   Button btn, to_server;
   TextField tf;

   String user_id;
   ReceiveDataThread rt;
   boolean endflag = false;

   static final int UP = 1; // 방향 값 상수로 설정
   static final int DOWN = 2;
   static final int RIGHT = 3;
   static final int LEFT = 4;

   static final int DISTANCE = 25; // 이동 값
   // ---------------------------------------
   stopWatch th; // 시간초 스레드
   Label second; // 시간초 스레드 레이블
   int sec=0;
   
   int num = 0;
   Image img;
   ImageView[] snake = new ImageView[10];

   double[] x = new double[9];
   double[] y = new double[9];

   double[] all_x = new double[10];
   double[] all_y = new double[10];

   int[] body = new int[10];
   int get_body = 0;
   
   class ReceiveDataThread implements Runnable{
       Socket client;
       BufferedReader ois;
       String receiveData;

       public ReceiveDataThread(Socket s, BufferedReader ois){
           client = s;
           this.ois = ois;
       }

     public void run(){
       try{
           while( ( receiveData = ois.readLine() ) != null )
           {
              ta.setText(ta.getText() + receiveData + "\n");
           }
       }catch(Exception e){ 
           e.printStackTrace(); 
       }
       finally{
           try{
               ois.close();
               client.close(); 
           }catch(IOException e2){
               e2.printStackTrace();
           }
       }
     }
   }

   class Move {
      public Move() {
      }

      public void Combind() {
         for (int i = 0; i < get_body; i++) {
            snake[body[i + 1]].setLayoutX(all_x[i]); // i는 앞에방의 위치
            snake[body[i + 1]].setLayoutY(all_y[i]); // 머리는 몸체를 먹고
            
            if(get_body ==9)
            {
              if( snake[9].getLayoutX() < snake[8].getLayoutX() ){
                 //left
                 snake[9].setImage( new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_left.png") );
              }
              else if( snake[9].getLayoutX() > snake[8].getLayoutX() ){
                 snake[9].setImage( new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_right.png") );
              }
              else if( snake[9].getLayoutY() < snake[8].getLayoutY() ) {
                 snake[9].setImage( new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_up.png") );
              }
              else if( snake[9].getLayoutY() > snake[8].getLayoutY() ) {
                 snake[9].setImage( new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_down.png") );
              }
            }
            
         }
         
         
      }
   }
   
   class rank_class
   {
      public void rankkk(Stage primaryStage)
      {
         Alert alert = new Alert(AlertType.CONFIRMATION);
         Optional<ButtonType> result = alert.showAndWait();  //alert버튼의 객체를 받음
         if(result.get() == ButtonType.OK)
         {
            try {
            new Report().start(new Stage());  //화면 재시작
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         }
         /*VBox vb = new VBox();
           HBox hb = new HBox();
           Label la = new Label(th.sec+"초 걸리셨습니다.\n기록을 저장하시겠습니까?");
           Button btn1 = new Button("예");
           Button btn2 = new Button("아니요");
           
           hb.setAlignment(Pos.CENTER);
           hb.setSpacing(15);
           hb.getChildren().addAll(btn1, btn2);
           
           vb.setAlignment(Pos.CENTER);
           vb.setSpacing(10);
           vb.getChildren().addAll(la, hb);
           
           Scene rank_storage = new Scene(vb);
           primaryStage.setScene(rank_storage);
           primaryStage.setWidth(250);
           primaryStage.setHeight(150);
           
           btn1.setOnAction(event -> {
            primaryStage.setScene(rankScene);
            primaryStage.setTitle("RANKING");
            primaryStage.setWidth(320);
            primaryStage.setHeight(250);
           });
         
         btn2.setOnAction(event -> {
            System.exit(0);
         });*/
           
      }
   } rank_class r = new rank_class();


   // gameover시 재시작 버튼과 취소버튼 이벤트처리
   void GameOverAlert(Stage primaryStage, Button b1, Button b2, VBox v) {
      Scene scene1 = new Scene(v);
      primaryStage.setScene(scene1);
      b1.setOnAction(event -> {
         try {
            th.interrupted();
            th = new stopWatch(second,sec);
            new Main().start(new Stage());
            primaryStage.close();
            
         } catch (Exception e1) {
            e1.printStackTrace();
         }
      });
      b2.setOnAction(event -> System.exit(0));
   }

   
   public void start(Stage primaryStage) throws Exception {
      // --------------------------------------------
      // 화면 설정
     VBox root = new VBox();
     root.setPrefSize(370, 600);
       
      //AnchorPane root = new AnchorPane();
      //root.setPrefSize(550, 480);
      //p2.getChildren().add(new TextArea("asd"));
      
      btn = new Button("Exit");
      //to_server = new Button("전송");

      AnchorPane gamePane = new AnchorPane();
      gamePane.setPrefSize(370, 200);
      
      ta = new TextArea();
      ta.setEditable(false);
      tf = new TextField();
      ta.setPrefSize(430, 150);
      ta.setLayoutX(0);
      ta.setLayoutY(410);
      tf.setPrefSize(430, 30);
      tf.setLayoutX(0);
      tf.setLayoutY(560);
      
      //outside.setCenter(backgroundside);
      //backgroundside.setCenter(gamePane);
      
      user_id="gil";
       ipAddress="127.0.0.1";
       
       gamePane.setOnMouseClicked(new EventHandler<MouseEvent>() {

         @Override
         public void handle(MouseEvent event) {
            
            if(event.getY() < 300)
            {
               gamePane.requestFocus();
            }
         }
          
       });
       
       try{
           
           System.out.println("**** 클라이언트*****");
           ta.setText(ta.getText() + "**** 클라이언트*****\n");
           client = new Socket(ipAddress, port);

           //read= new BufferedReader(new InputStreamReader(System.in));
            br = new BufferedReader( new InputStreamReader( client.getInputStream() ) );
            pw = new PrintWriter( client.getOutputStream() );
 
            pw.println( user_id );
            pw.flush();           
 
            rt= new ReceiveDataThread(client, br);
            Thread t = new Thread(rt);
            t.start();
            } 
       catch(Exception e)
       { 
           if(!endflag) e.printStackTrace();
       }
      
      btn.setOnAction(new EventHandler<ActionEvent>() {
         
         @Override
         public void handle(ActionEvent action) {
            
            primaryStage.close();
            ta.setText(ta.getText() + "클라이트의 접속을 종료합니다.\n");
            System.out.print("클라이트의 접속을 종료합니다.");
                  try{
                      br.close();
                      pw.close();
                      client.close();
                  }catch(IOException e2){
                      e2.printStackTrace();
                  }
            }
         });
      
      tf.setOnKeyPressed((event) -> {
         if(event.getCode() == KeyCode.ENTER)
         {
            String message = tf.getText();
            tf.setText("");
            pw.println(message);
            pw.flush();
         }
      });
      
      
      // --------------------------------------------
      // 배경 화면 설정
      img = new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/snakebackground.png");
      ImageView background = new ImageView(img);
      background.setX(10);
      background.setY(10);
      background.setFitWidth(450);
      background.setFitHeight(400);
      background.setPreserveRatio(true);
      gamePane.getChildren().addAll(background, tf, ta);
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
      img = new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/downH.png");
      snake[0] = new ImageView(img);
      snake[0].setImage(img);
      snake[0].setPreserveRatio(false);
      snake[0].setFitHeight(25);
      snake[0].setFitWidth(25);
      snake[0].setLayoutX(25);
      snake[0].setLayoutY(25);
      gamePane.getChildren().add(snake[0]);
      // --------------------------------------------
      // 랜덤으로 아이템 생성
     
      //랜덤값 비교할 대상
      double randomnumx;
      double randomnumy;
      
      //중복확인을 위한 boolean 값
      boolean check=true;
      for(int i=0;i<x.length;i++)
      {
        randomnumx = Math.floor((Math.random() * 350)+10);
         randomnumy = Math.floor((Math.random() * 350)+10);
        
       if (randomnumx % 25 != 0) {
           x[i] = randomnumx + 25 - randomnumx % 25;
       }else {
          x[i] = randomnumx;
       }
       if (randomnumy % 25 != 0) {
           y[i] = randomnumy + 25 - randomnumy % 25;
            
       }else {
          y[i] = randomnumy;
       }
       int n = 1;
       //중복 해결할 때까지 루프 돌림
       while(check) {
          if(x[i]==x[n]&&y[i]==y[n]) {
                randomnumx = Math.floor((Math.random() * 350)+10);
               randomnumy = Math.floor((Math.random() * 350)+10);
              
              if (randomnumx % 25 != 0) {
                  x[i] = randomnumx + 25 - randomnumx % 25;
              }else {
                 x[i] = randomnumx;
              }
              if (randomnumy % 25 != 0) {
                  y[i] = randomnumy + 25 - randomnumy % 25;
                   
              }else {
                 y[i] = randomnumy;
              }
              n++;
          }
          else {
             n=0;
             check = false; //중복해결시 루프탈출 
          }
       }
    }
      // --------------------------------------------
      // 몸통 설정

      Image snake_body[] = { new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/1.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/2.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/3.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/4.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/5.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/6.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/7.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/8.png"),
            //수정 필요
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_up.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_down.png") };
            //
      //벡터
      Vector<Image> vector = new Vector<Image>();
      for (int i = 0; i < snake_body.length; i++) {
         vector.add(snake_body[i]);
      }
      
      
      for (int i = 1; i < snake.length; i++) {            //꼬리  겸  뿌리기
         snake[i] = new ImageView(snake_body[i-1]);
         snake[i].setImage(snake_body[i-1]);
         snake[i].setPreserveRatio(false);
         snake[i].setFitHeight(25);
         snake[i].setFitWidth(25);
         snake[i].setLayoutX(x[i - 1]); // x의 좌표값을 설정
         snake[i].setLayoutY(y[i - 1]); // y의 좌표값을 설정
         gamePane.getChildren().add(snake[i]);
      }

      Move move = new Move();

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
       //수정필요 만약 스탑버튼을 누르면 머리는 못 움직이게, 시간 멈추기. 
      //MenuItem stop = new MenuItem("_Stop");
      //stop.setAccelerator(KeyCombination.keyCombination("SHORTCUT+Z"));
      MenuItem restart = new MenuItem("_Restart");
      restart.setAccelerator(KeyCombination.keyCombination("SHORTCUT+R"));
      MenuItem recordOpen = new MenuItem("_Record_Open");
      recordOpen.setAccelerator(KeyCombination.keyCombination("SHORTCUT+O"));
      MenuItem scoreSave = new MenuItem("_Score_Save");
      scoreSave.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
      MenuItem exitItem = new MenuItem("_Exit");
      exitItem.setAccelerator(KeyCombination.keyCombination("SHORTCUT+E"));
      MenuItem help = new MenuItem("_Help");
      
      /*stop.setOnAction(new EventHandler<ActionEvent>() {
      
      public void handle(ActionEvent event) {
         snake[0].setLayoutX(snake[0].getLayoutX() - DISTANCE);
      }
   });*/
      
      //help내용
      help.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            Alert help_alt = new Alert(AlertType.INFORMATION);
             help_alt.setTitle("Help");
             help_alt.setHeaderText("How to play the Game");
             help_alt.setContentText("\r\n" + 
                   "You can play a snake game.\n "
                   + "When playing a game, you must eat in the order of the given number, \n"
                   + "and you can not go all at once in the opposite direction. \n"
                    +"If you cross the wall or bump into the body, the game over.");
             help_alt.showAndWait();
        }
      });

      recordOpen.setOnAction(event -> {
         try {
            // 파일 객체 생성
            File file = new File(
                  "C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\Score.txt");
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
            th.interrupt();
            th = new stopWatch(second, sec);
            
            new SnakeGame().start(new Stage());
            primaryStage.close();
         } catch (Exception e1) {
            e1.printStackTrace();
         }
      });

      game.getItems().addAll(restart, exitItem,new SeparatorMenuItem(),help);
      score.getItems().addAll(recordOpen, scoreSave);
      mb.getMenus().addAll(game, score);
      // Menu UI end
      // -----------------------------------------------------------------------------------

      root.getChildren().add(mb);
      root.getChildren().add(gamePane);
      Scene scene = new Scene(root);
      gamePane.setOnKeyPressed((event) -> {

          isMove = false;
          // 끝에 붙어있는 몸체의 위치
          double attach_x = snake[body[get_body]].getLayoutX();
          double attach_y = snake[body[get_body]].getLayoutY();


          
          //머리 좌표를 가져옴
          all_x[0] = snake[0].getLayoutX();
          all_y[0] = snake[0].getLayoutY();
          
          // 키 이벤트 + 마지막으로 전환한 방향 반대로는 이동할 수 없게
          if (LAST_DIRECTION != LEFT && event.getCode() == KeyCode.RIGHT) {
             all_x[0] = snake[0].getLayoutX();
              all_y[0] = snake[0].getLayoutY();
              if (snake[0].getLayoutX() < 400 - snake[0].getFitWidth()) {
                  img = new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/rigntH.png");
                  snake[0].setImage(img);
                  
                  snake[0].setLayoutX(snake[0].getLayoutX() + DISTANCE);
                   LAST_DIRECTION = RIGHT;   
                  isMove = true;

                 
               } else {
                  GameOverAlert(primaryStage, b1, b2, v);
             }
          } else if (LAST_DIRECTION != RIGHT && event.getCode() == KeyCode.LEFT) {
             if (snake[0].getLayoutX() > 10) {
                snake[0].setImage( new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/leftH.png"));
                snake[0].setLayoutX(snake[0].getLayoutX() - DISTANCE);
                LAST_DIRECTION = LEFT;
                isMove = true;
             } else {
                GameOverAlert(primaryStage, b1, b2, v);
             }
          } else if (LAST_DIRECTION != DOWN && event.getCode() == KeyCode.UP) {
             if (snake[0].getLayoutY() > 10) {
                img = new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/upH.png");
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
                img = new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/downH.png");
                snake[0].setImage(img);
                
                snake[0].setLayoutY(snake[0].getLayoutY() + DISTANCE);
                LAST_DIRECTION = DOWN;
                isMove = true;
             } else {
                GameOverAlert(primaryStage, b1, b2, v);

             }
          }          // 움직임이 참 값인 경우만 이동되게 만들기
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
          
          for(int i=1;i<snake.length;i++)      //몇번방의 몸통을 만났는지 알아보기 위해 For문 돌리기
             {
                 
                boolean done =true;
                if(snake[0].getLayoutX() == snake[i].getLayoutX() &&       //머리가 몸통을 만나게 되는경우(겹쳐지면서 머리에 몸통이 붙는다)
                   snake[0].getLayoutY() == snake[i].getLayoutY() )
                   //num == snake[i].
                {
                   while(done)
                   {
                      if(snake[i].getImage() == vector.get(num))
                      {
                           get_body++;            // 처음에 0으로 초기치를 주고(머리는 0, 몸통은 1부터 따라서 붙을때마다 숫자가 1씩증가) 
                            body[get_body] = i;      // [몸통 get_body번째 방]에는 몇번방의 몸통인지 대입한다.  ( (get)body는 1부터 순차적으로 증가 ) 몸통1에 3(스네이크할때의 3)이 넣어짐
                            snake[i].setLayoutX(attach_x);      //스네이크 3에는 
                            snake[i].setLayoutY(attach_y);
                            num ++;
                            
                            if( i<snake.length-1) {
                               snake[i].setImage(new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/body.png"));
                            }
                      }
                      else { done = false; }
                   }
                }
                
             }
          move.Combind(); // 연결
          
          if(get_body==9)
          {
             th.interrupt();
             r.rankkk(primaryStage);
          }
       });

      gamePane.requestFocus();
      primaryStage.setScene(scene);
      primaryStage.setTitle("SnakeGame");

      primaryStage.show();
      primaryStage.setResizable(false); // 창 사이즈 변동 x
     
      second = new Label();
      second.setFont(new Font("Gothic", 60));
      second.setTextFill(Color.BROWN);
      second.setLayoutX(320);
      second.setLayoutY(320);
      
      
      th = new stopWatch(second,sec);
      th.start();
      
      gamePane.getChildren().add(second);
      
   }

   public static void main(String[] args) {
      launch(args);
   }

}