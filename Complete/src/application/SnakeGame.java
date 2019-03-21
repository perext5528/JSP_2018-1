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
   // �̵� ����
   boolean isMove = false; // �̵� ����
   int LAST_DIRECTION = 0; // ���������� ȸ���� ����
   
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

   static final int UP = 1; // ���� �� ����� ����
   static final int DOWN = 2;
   static final int RIGHT = 3;
   static final int LEFT = 4;

   static final int DISTANCE = 25; // �̵� ��
   // ---------------------------------------
   stopWatch th; // �ð��� ������
   Label second; // �ð��� ������ ���̺�
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
            snake[body[i + 1]].setLayoutX(all_x[i]); // i�� �տ����� ��ġ
            snake[body[i + 1]].setLayoutY(all_y[i]); // �Ӹ��� ��ü�� �԰�
            
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
         Optional<ButtonType> result = alert.showAndWait();  //alert��ư�� ��ü�� ����
         if(result.get() == ButtonType.OK)
         {
            try {
            new Report().start(new Stage());  //ȭ�� �����
         } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         }
         /*VBox vb = new VBox();
           HBox hb = new HBox();
           Label la = new Label(th.sec+"�� �ɸ��̽��ϴ�.\n����� �����Ͻðڽ��ϱ�?");
           Button btn1 = new Button("��");
           Button btn2 = new Button("�ƴϿ�");
           
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


   // gameover�� ����� ��ư�� ��ҹ�ư �̺�Ʈó��
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
      // ȭ�� ����
     VBox root = new VBox();
     root.setPrefSize(370, 600);
       
      //AnchorPane root = new AnchorPane();
      //root.setPrefSize(550, 480);
      //p2.getChildren().add(new TextArea("asd"));
      
      btn = new Button("Exit");
      //to_server = new Button("����");

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
           
           System.out.println("**** Ŭ���̾�Ʈ*****");
           ta.setText(ta.getText() + "**** Ŭ���̾�Ʈ*****\n");
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
            ta.setText(ta.getText() + "Ŭ����Ʈ�� ������ �����մϴ�.\n");
            System.out.print("Ŭ����Ʈ�� ������ �����մϴ�.");
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
      // ��� ȭ�� ����
      img = new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/snakebackground.png");
      ImageView background = new ImageView(img);
      background.setX(10);
      background.setY(10);
      background.setFitWidth(450);
      background.setFitHeight(400);
      background.setPreserveRatio(true);
      gamePane.getChildren().addAll(background, tf, ta);
      // ����â
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

      // �Ӹ� ����
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
      // �������� ������ ����
     
      //������ ���� ���
      double randomnumx;
      double randomnumy;
      
      //�ߺ�Ȯ���� ���� boolean ��
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
       //�ߺ� �ذ��� ������ ���� ����
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
             check = false; //�ߺ��ذ�� ����Ż�� 
          }
       }
    }
      // --------------------------------------------
      // ���� ����

      Image snake_body[] = { new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/1.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/2.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/3.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/4.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/5.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/6.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/7.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/8.png"),
            //���� �ʿ�
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_up.png"),
            new Image("file:///C:/Users/User/eclipse-workspace/ClinicResult/images/tail_down.png") };
            //
      //����
      Vector<Image> vector = new Vector<Image>();
      for (int i = 0; i < snake_body.length; i++) {
         vector.add(snake_body[i]);
      }
      
      
      for (int i = 1; i < snake.length; i++) {            //����  ��  �Ѹ���
         snake[i] = new ImageView(snake_body[i-1]);
         snake[i].setImage(snake_body[i-1]);
         snake[i].setPreserveRatio(false);
         snake[i].setFitHeight(25);
         snake[i].setFitWidth(25);
         snake[i].setLayoutX(x[i - 1]); // x�� ��ǥ���� ����
         snake[i].setLayoutY(y[i - 1]); // y�� ��ǥ���� ����
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
       //�����ʿ� ���� ��ž��ư�� ������ �Ӹ��� �� �����̰�, �ð� ���߱�. 
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
      
      //help����
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
            // ���� ��ü ����
            File file = new File(
                  "C:\\Users\\User\\eclipse-workspace\\ClinicResult\\word\\Score.txt");
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
          // ���� �پ��ִ� ��ü�� ��ġ
          double attach_x = snake[body[get_body]].getLayoutX();
          double attach_y = snake[body[get_body]].getLayoutY();


          
          //�Ӹ� ��ǥ�� ������
          all_x[0] = snake[0].getLayoutX();
          all_y[0] = snake[0].getLayoutY();
          
          // Ű �̺�Ʈ + ���������� ��ȯ�� ���� �ݴ�δ� �̵��� �� ����
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
             // ���� ���� �� ���� �����
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
          }          // �������� �� ���� ��츸 �̵��ǰ� �����
          if (isMove == true) {
             for (int i = 1; i <= get_body; i++) {
                all_x[i] = snake[body[i]].getLayoutX();
                all_y[i] = snake[body[i]].getLayoutY();
             }
          }
          // �Ӹ��� ������ �ε��� ���
          for (int i = 1; i <= get_body; i++) {
             if (all_x[i] == snake[0].getLayoutX() && all_y[i] == snake[0].getLayoutY())
                GameOverAlert(primaryStage, b1, b2, v);
          }
          
          for(int i=1;i<snake.length;i++)      //������� ������ �������� �˾ƺ��� ���� For�� ������
             {
                 
                boolean done =true;
                if(snake[0].getLayoutX() == snake[i].getLayoutX() &&       //�Ӹ��� ������ ������ �Ǵ°��(�������鼭 �Ӹ��� ������ �ٴ´�)
                   snake[0].getLayoutY() == snake[i].getLayoutY() )
                   //num == snake[i].
                {
                   while(done)
                   {
                      if(snake[i].getImage() == vector.get(num))
                      {
                           get_body++;            // ó���� 0���� �ʱ�ġ�� �ְ�(�Ӹ��� 0, ������ 1���� ���� ���������� ���ڰ� 1������) 
                            body[get_body] = i;      // [���� get_body��° ��]���� ������� �������� �����Ѵ�.  ( (get)body�� 1���� ���������� ���� ) ����1�� 3(������ũ�Ҷ��� 3)�� �־���
                            snake[i].setLayoutX(attach_x);      //������ũ 3���� 
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
          move.Combind(); // ����
          
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
      primaryStage.setResizable(false); // â ������ ���� x
     
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