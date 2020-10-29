package br.com.inarigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.inarigames.world.Camera;

public class Entity {
	
	protected double x, y;
	protected BufferedImage sprite;
	protected int width, height;
	
	public Entity(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics graphics) {
		graphics.drawImage(sprite, Camera.offsetX(this.getX()), Camera.offsetY(this.getY()), null);
	}
}
