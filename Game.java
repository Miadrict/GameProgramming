package com.willajhughes.src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static String NAME = "Untitled Project";
	
	public static int WIDTH = 280;
	public static int HEIGHT = WIDTH / 8 * 5;
	public static int SCALE = 3;
	
	private boolean running = false;
	
	private JFrame window;
	private Thread thread;
	
	public Game() {
		Dimension SIZE = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
			setPreferredSize(SIZE);
			
		window = new JFrame();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void run() {
		while (running) {
			render();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.dispose();
		bs.show();
	}
	
	public void update() {
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		
		game.window.setTitle(NAME);
		game.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.window.add(game);
		game.window.pack();
		game.window.setResizable(false);
		game.window.setLocationRelativeTo(null);
		game.window.setVisible(true);
		
		game.start();
	}
}
