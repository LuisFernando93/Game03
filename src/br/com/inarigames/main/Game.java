package br.com.inarigames.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import br.com.inarigames.entities.Entity;
import br.com.inarigames.entities.Player;
import br.com.inarigames.graphics.Spritesheet;

public class Game extends Canvas implements Runnable, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private Thread thread;
	private boolean isRunning = false;
	private double amountOfUpdates = 60.0;
	public static Random random;
	
	private BufferedImage image;
	
	public static Player player;
	public static List<Entity> entities;
	public static List<Entity> toRemove = new ArrayList<Entity>();
	public static Spritesheet spritesheet =  new Spritesheet("/spritesheet.png");
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 640;
	public static final int SCALE = 1;
	
	private static String gameState = "NORMAL";
	
	private static int score;
	
	private Start start;
	private GameOver gameOver;
	
	public Game() {
		
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		
		//initializing objects
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		player = new Player(0, 0, 32, 32);
		entities.add(player);
		random = new Random();
		start = new Start();
		gameOver = new GameOver();
	}
	
	public static int getScore() {
		return Game.score;
	}
	
	public static void setGameState(String gameState) {
		Game.gameState = gameState;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void initFrame() {
		
		frame = new JFrame("Game #3");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.println("erro na thread");
			e.printStackTrace();
		}
	}
	
	public static void newGame() {
		
		Game.score = 0;
		return;
		
	}
	
	public static void closeGame() {
		frame.setVisible(false);
		frame.dispose();
		System.exit(0);
	}
	
	private void update() {
		switch (Game.gameState) {
		
		case "START":
			start.update();
			break;
		
		case "NORMAL":
			for (Entity entity : entities) {
				entity.update();
			}
			entities.removeAll(toRemove);
			toRemove.clear();
			break;
			
		case "GAME OVER":
			gameOver.update();
			break;
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + Game.gameState);
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics graphics = image.getGraphics();
		graphics.setColor(new Color(0,0,0));
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		for (Entity entity : entities) {
			entity.render(graphics);
		}
		
		graphics.dispose();
		graphics = bs.getDrawGraphics();
		graphics.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		switch (Game.gameState) {
		
		case "START":
			start.render(graphics);
			break;
		
		case "GAME OVER":
			gameOver.render(graphics);
			break;
			
		}
			
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double ns = 1000000000 / amountOfUpdates;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS = " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		stop();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	switch (Game.gameState) {
			
			case "START":
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					start.setStart(true);
				}
				break;
			
			case "NORMAL":
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					
				}
				break;
			
			case "GAME OVER":
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					gameOver.setRestart(true);
				}
				break;
				
			}
		
	}
	

}
