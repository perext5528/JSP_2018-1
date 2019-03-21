package ProfessorReport;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

class SnakeGameFrame extends JFrame {
	Thread snakeThread;
	GroundPanel p;

	public SnakeGameFrame() {
		this.setTitle("스네이크 움직이기");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p = new GroundPanel();
		this.setContentPane(p);
		this.setSize(400, 400);
		this.setVisible(true);
		p.requestFocus();
		snakeThread = new Thread(p);
		snakeThread.start();
	}

	class GroundPanel extends JPanel implements Runnable {
		static final int LEFT = 0;
		static final int RIGHT = 1;
		static final int UP = 2;
		static final int DOWN = 3;
		static final int delay = 200;
		int direction;
		SnakeBody snakeBody;

		public GroundPanel() {
			this.setLayout(null);
			snakeBody = new SnakeBody();
			snakeBody.addIn(this);
			direction = LEFT; // 초기 방향
			this.addKeyListener(new MyKeyListener());
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("bg1.jpg");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(delay);
					snakeBody.move(direction);
				} catch (InterruptedException e) {
					return;
				}
			}
		}

		class MyKeyListener extends KeyAdapter {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					direction = LEFT;
					break;
				case KeyEvent.VK_RIGHT:
					direction = RIGHT;
					break;
				case KeyEvent.VK_UP:
					direction = UP;
					break;
				case KeyEvent.VK_DOWN:
					direction = DOWN;
					break;
				}
			}
		}
	}

	class SnakeBody {
		Vector<JLabel> v = new Vector<JLabel>();

		public SnakeBody() {
			ImageIcon head = new ImageIcon("head.png");
			JLabel la = new JLabel(head);
			la.setSize(head.getIconWidth(), head.getIconHeight());
			la.setLocation(100, 100);
			v.add(la);
			ImageIcon body = new ImageIcon("head.png");
			for (int i = 1; i < 10; i++) {
				la = new JLabel(body);
				la.setSize(body.getIconWidth(), body.getIconHeight());
				la.setLocation(100 + i * 20, 100);
				v.add(la);
			}
		}

		public void addIn(JPanel p) {
			for (int i = 0; i < v.size(); i++)
				p.add(v.get(i));
		}

		public void move(int direction) {
			// 꼬리 이동
			for (int i = v.size() - 1; i > 0; i--) {
				JLabel b = v.get(i);
				JLabel a = v.get(i - 1);
				b.setLocation(a.getX(), a.getY());
			}
			// 머리 이동
			JLabel head = v.get(0);
			switch (direction) {
			case GroundPanel.LEFT:
				head.setLocation(head.getX() - 20, head.getY());
				break;
			case GroundPanel.RIGHT:
				head.setLocation(head.getX() + 20, head.getY());
				break;
			case GroundPanel.UP:
				head.setLocation(head.getX(), head.getY() - 20);
				break;
			case GroundPanel.DOWN:
				head.setLocation(head.getX(), head.getY() + 20);
				break;
			}
		}
	}
}

public class SnakeGame {
	public static void main(String[] args) {
		new SnakeGameFrame();
	}
}