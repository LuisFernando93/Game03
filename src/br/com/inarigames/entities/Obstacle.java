package br.com.inarigames.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.inarigames.main.Game;

public class Obstacle extends Entity {

	public Obstacle(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	private void move() {
		x-=2;
		if (x+width <= 0) {
			Game.toRemove.add(this);
			Game.addScore();
			return;
		}
	}
	
	private void updateMask() {
		this.maskx = this.getX();
		this.masky = this.getY();
	}
	
	public void update() {
		move();
		updateMask();
	}
	
	public void render(Graphics graphics) {
		graphics.setColor(Color.green);
		graphics.fillRect(this.getX(), this.getY(), width, height);
	}
}
