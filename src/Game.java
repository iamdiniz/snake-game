import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener {
	
	public static final int WIDTH = 640;
	
	public static final int HEIGHT = 480;
	
	public Node[] nodeSnake = new Node[20]; // Tamanho da cobrinha
	
	public boolean left, right, up, down;
	
	public int score = 0;
	
	public int appleX = 0;
	
	public int appleY = 0;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		for (int i = 0; i < nodeSnake.length; i++) {
			nodeSnake[i] = new Node(0, 0);
		}
		this.addKeyListener(this);
	}

	private void tick() {
		
		for (int i = nodeSnake.length - 1; i > 0; i--) { // É aqui que ela persegue
			nodeSnake[i].x = nodeSnake[i - 1].x;
			nodeSnake[i].y = nodeSnake[i - 1].y;
		}
		
		if (right) {
			nodeSnake[0].x++;
		} else if (up) {
			nodeSnake[0].y--;
		} else if (down) {
			nodeSnake[0].y++;
		} else if (left) {
			nodeSnake[0].x--;
		}
		
		if (new Rectangle(nodeSnake[0].x, nodeSnake[0].y, 10, 10).intersects(new Rectangle(
				appleX, appleY, 10, 10))) {
			appleX = new Random().nextInt(640-10);
			appleY = new Random().nextInt(640-10);
			score++;
			
			System.out.println("Score: " + score);
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy(); // Definir estrategia de buffer
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics graphics = bs.getDrawGraphics(); // Classe responsável por desenhar o jogo
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (int i = 0; i < nodeSnake.length; i++) {
			graphics.setColor(Color.magenta);
			graphics.fillRect(nodeSnake[i].x, nodeSnake[i].y, 10, 10);
		}
		
		graphics.setColor(Color.red);
		graphics.fillRect(appleX, appleY, 10, 10);
		
		graphics.dispose(); 
		bs.show();
		
	}

	public static void main(String[] args) {
		Game game = new Game();
		JFrame frame = new JFrame("Snake");
		frame.add(game);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		new Thread(game).start();
	}
	
	@Override
	public void run() {
		
		while (true) {
			tick();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			up = false;
			down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
			right = false;
			left = false;
			down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
			up = false;
			right = false;
			left = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
