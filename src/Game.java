import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {
	
	public static final int WIDTH = 640;
	
	public static final int HEIGHT = 480;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	private void tick() {
		// TODO Auto-generated method stub
		
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

}
