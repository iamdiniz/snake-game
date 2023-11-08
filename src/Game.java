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
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 480;
	
	public SnakeSegment[] snakeSegments = new SnakeSegment[10]; // Tamanho da cobrinha
	
	public boolean left = false, right = false, up = false, down = false;
	public int score = 0;
	public int appleHorizontal = new Random().nextInt(WIDTH-10);
	public int appleVertical = new Random().nextInt(WIDTH-10);
	public int speed = 10;
	public int frameSpeed = 20;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		for (int i = 0; i < snakeSegments.length; i++) {
			snakeSegments[i] = new SnakeSegment(0, 0);
		}
		this.addKeyListener(this);
	}

	private void tick() {
		
		for (int i = snakeSegments.length - 1; i > 0; i--) { // É aqui que ela persegue
			snakeSegments[i].positionHorizontal = snakeSegments[i - 1].positionHorizontal;
			snakeSegments[i].positionVertical = snakeSegments[i - 1].positionVertical;
		}
		
		if (right) {
			snakeSegments[0].positionHorizontal+=speed;
			collision();
		} else if (up) {
			snakeSegments[0].positionVertical-=speed;
			collision();
		} else if (down) {
			snakeSegments[0].positionVertical+=speed;
			collision();
		} else if (left) {
			snakeSegments[0].positionHorizontal-=speed;
			collision();
		}
		
		if (new Rectangle(snakeSegments[0].positionHorizontal, snakeSegments[0]
				.positionVertical, 10, 10)
				.intersects(new Rectangle(appleHorizontal, appleVertical, 10, 10))) {
			
			appleHorizontal = new Random().nextInt(WIDTH-10);
			appleVertical = new Random().nextInt(WIDTH-10);
			score++;
			frameSpeed++;
			System.out.println("Score: " + score);
		}
		
	}
	
	private void collision() {
		for (int i = 0; i < snakeSegments.length; i++) {
			
			if (i == 0) {
				continue;
			}
			
			Rectangle snakeHead = new Rectangle(snakeSegments[0]
					.positionHorizontal, snakeSegments[0].positionVertical, 10, 10);
			
			Rectangle snakeBody = new Rectangle(snakeSegments[i]
					.positionHorizontal, snakeSegments[i].positionVertical, 10, 10);
			
			if (snakeHead.intersects(snakeBody)) {
				System.out.println("Game Over!");
				frameSpeed = 20;
				score = 0;
				right = false;
				up = false;
				left = false;
				down = false;
				
				for (int j = 0; j < snakeSegments.length; j++) {
					snakeSegments[j] = new SnakeSegment(0, 0);
				}
				
			}
		}
	}

	private void render() {
		BufferStrategy bufferStrategy = this.getBufferStrategy(); // Definir estrategia de buffer
		if (bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics gameGraphics = bufferStrategy.getDrawGraphics(); // Classe responsável por desenhar o jogo
		gameGraphics.setColor(Color.black);
		gameGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (int i = 0; i < snakeSegments.length; i++) {
			gameGraphics.setColor(Color.magenta);
			gameGraphics.fillRect(snakeSegments[i].positionHorizontal, snakeSegments[i].positionVertical, 10, 10);
		}
		
		gameGraphics.setColor(Color.red);
		gameGraphics.fillRect(appleHorizontal, appleVertical, 10, 10);
		
		gameGraphics.dispose(); 
		bufferStrategy.show();
	}

	public static void main(String[] args) {
		Game snakeGame = new Game();
		JFrame gameFrame = new JFrame("Snake");
		
		gameFrame.add(snakeGame);
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.pack();
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		
		new Thread(snakeGame).start();
	}
	
	@Override
	public void run() {
		
		while (true) {
			tick();
			render();
			try {
				Thread.sleep(1000/frameSpeed);
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
	public void keyPressed(KeyEvent keyBoardClicked) {
		if (keyBoardClicked.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
			left = false;
			up = false;
			down = false;
		} else if (keyBoardClicked.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
			right = false;
			up = false;
			down = false;
		} else if (keyBoardClicked.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
			right = false;
			left = false;
			down = false;
		} else if (keyBoardClicked.getKeyCode() == KeyEvent.VK_DOWN) {
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
