package br.com.inarigames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.inarigames.main.Game;
import br.com.inarigames.world.Camera;

public class Player extends Entity {
	
	private BufferedImage[] playerSprites;
	private int frames = 0, maxFrames = 3, imageIndex = 0, maxIndex = 2; 
	private boolean fly = false;

	public Player(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		playerSprites = new BufferedImage[3];
		for (int i = 0; i < playerSprites.length; i++) {
			playerSprites[i] = Game.spritesheet.getSprite(32*i, 0, 32, 32);
		}
	}
	
	public void setFly(boolean fly) {
		this.fly = fly;
	}
	
	private void playerAnimation() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			imageIndex++;
			if (imageIndex == maxIndex) {
				imageIndex = 0;
			}
		}
	}
	
	private void fallOrFlight() {
		if (fly) {
			this.y-=3;
			if (this.y < 0) {
				this.y = 0;
			}
		} else {
			this.y+=3;
		}
	}
	
	private void checkHeight() {
		if (this.y-this.height >= Game.HEIGHT) {
			Game.toRemove.add(this);
			Game.setGameState("GAME OVER");
		}
	}
	
	public void update() {
		playerAnimation();
		fallOrFlight();
		checkHeight();
	}
	
	public void render(Graphics graphics) {
		graphics.drawImage(playerSprites[imageIndex], Camera.offsetX(this.getX()), Camera.offsetY(this.getY()), null);
	}
	
}
